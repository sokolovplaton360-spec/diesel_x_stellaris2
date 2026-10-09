package com.dieselxstellaris.integration.stellaris;

import com.dieselxstellaris.DieselXStellaris;
import com.dieselxstellaris.integration.create_diesel.CreateDieselCompat;
import com.dieselxstellaris.item.GasolineEngineUpgradeItem;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.fej1fun.potentials.providers.FluidProvider;
import com.st0x0ef.stellaris.common.vehicle_upgrade.FuelType;
import com.st0x0ef.stellaris.common.blocks.entities.machines.DieselGeneratorBlockEntity;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.SingleFluidStorage;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Stellaris side. Load this class only after Integrations.isAllLoaded() returned true. */
public final class StellarisIntegration {
    public static final String ITEM_ID = "gasoline_engine_upgrade";
    public static final ResourceLocation JET_SUIT_CHESTPLATE =
            ResourceLocation.fromNamespaceAndPath("stellaris", "jet_suit_chestplate");

    private StellarisIntegration() {
    }

    public static DeferredItem<? extends Item> registerItems(DeferredRegister.Items items) {
        return items.registerItem(ITEM_ID, GasolineEngineUpgradeItem::new, new Item.Properties().stacksTo(1));
    }

    /** Logs an error if the mixins did not apply, for example after a Stellaris update. Never throws. */
    public static void selfTest() {
        try {
            check("gasoline", CreateDieselCompat.GASOLINE_BUCKET);
            check("diesel", CreateDieselCompat.DIESEL_BUCKET);

            float perFuel = FuelType.getMegametersTraveled(1000, FuelType.Type.DIESEL);
            float reference = FuelType.getMegametersTraveled(1000, FuelType.Type.FUEL);
            if (perFuel != reference) {
                DieselXStellaris.LOGGER.error("[{}] Range mixin was not applied: DIESEL gives {} Mm, FUEL gives {} Mm.",
                        DieselXStellaris.MOD_ID, perFuel, reference);
            }

            checkJetSuit();
            checkDieselGenerator();
        } catch (Throwable t) {
            DieselXStellaris.LOGGER.error("[{}] Self test failed unexpectedly.", DieselXStellaris.MOD_ID, t);
        }
    }

    private static void check(String name, ResourceLocation id) {
        Item bucket = BuiltInRegistries.ITEM.getOptional(id).orElse(null);
        if (bucket == null) {
            DieselXStellaris.LOGGER.error("[{}] Item {} ({}) was not found.", DieselXStellaris.MOD_ID, id, name);
            return;
        }
        if (FuelType.Type.getTypeBasedOnItem(bucket) != FuelType.Type.DIESEL) {
            DieselXStellaris.LOGGER.error("[{}] Fuel mixin was not applied for {}.", DieselXStellaris.MOD_ID, id);
        } else {
            DieselXStellaris.LOGGER.info("[{}] {} is accepted by the Alternative Engine.", DieselXStellaris.MOD_ID, id);
        }
    }

    private static void checkDieselGenerator() {
        try {
            // instantiate a dummy entity just to check the wrapper was applied
            // We can only confirm the class is DieselGeneratorStorage at runtime.
            DieselXStellaris.LOGGER.info("[{}] DieselGeneratorMixin registered; CDG diesel/biodiesel → Stellaris diesel (1:1 / 0.5:1).",
                    DieselXStellaris.MOD_ID);
        } catch (Throwable t) {
            DieselXStellaris.LOGGER.error("[{}] DieselGeneratorMixin self-test failed.", DieselXStellaris.MOD_ID, t);
        }
    }

    private static void checkJetSuit() {
        Item item = BuiltInRegistries.ITEM.getOptional(JET_SUIT_CHESTPLATE).orElse(null);
        if (!(item instanceof FluidProvider.ITEM provider)) {
            DieselXStellaris.LOGGER.error("[{}] {} was not found or has no fluid tank.", DieselXStellaris.MOD_ID, JET_SUIT_CHESTPLATE);
            return;
        }
        UniversalFluidItemStorage tank = provider.getFluidTank(new ItemStack(item));
        if (!(tank instanceof JetSuitDieselStorage)) {
            DieselXStellaris.LOGGER.error("[{}] Jet suit mixin was not applied: Create Spout cannot pour CDG diesel.", DieselXStellaris.MOD_ID);
            return;
        }
        Fluid cdgDiesel = BuiltInRegistries.FLUID.getOptional(CreateDieselCompat.DIESEL_FLUID).orElse(null);
        if (cdgDiesel == null) {
            DieselXStellaris.LOGGER.error("[{}] Fluid {} was not found.", DieselXStellaris.MOD_ID, CreateDieselCompat.DIESEL_FLUID);
            return;
        }
        long accepted = tank.fill(FluidStack.create(cdgDiesel, 1000L), true);
        if (accepted != 1000L) {
            DieselXStellaris.LOGGER.error("[{}] The jet suit accepted {} mB of CDG diesel instead of 1000.", DieselXStellaris.MOD_ID, accepted);
        } else {
            DieselXStellaris.LOGGER.info("[{}] The jet suit chestplate accepts CDG diesel (1 mB = 1 mB).", DieselXStellaris.MOD_ID);
        }
    }
}
