package com.dieselxstellaris.integration.create_diesel;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

/**
 * Create: Diesel Generators side.
 * Matched by registry id only — no compile-time dependency on CDG classes.
 *
 * Fluid conversion ratios for the Stellaris diesel generator:
 *   CDG diesel   → Stellaris diesel  1 : 1
 *   CDG biodiesel → Stellaris diesel  1 : 0.5
 */
public final class CreateDieselCompat {
    public static final String MOD_ID = "createdieselgenerators";

    // --- item ids ---
    public static final ResourceLocation GASOLINE_BUCKET =
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "gasoline_bucket");
    public static final ResourceLocation DIESEL_BUCKET =
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "diesel_bucket");
    public static final ResourceLocation BIODIESEL_BUCKET =
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "biodiesel_bucket");

    // --- fluid ids ---
    public static final ResourceLocation DIESEL_FLUID =
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "diesel");
    public static final ResourceLocation FLOWING_DIESEL_FLUID =
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "flowing_diesel");
    public static final ResourceLocation BIODIESEL_FLUID =
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "biodiesel");
    public static final ResourceLocation FLOWING_BIODIESEL_FLUID =
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "flowing_biodiesel");

    /** mB of Stellaris diesel that 1 mB of a CDG fluid equals. -1 if the fluid is not supported. */
    public static long toStellarisMillibuckets(Fluid fluid, long amountMb) {
        if (fluid == null || amountMb <= 0) return -1;
        ResourceLocation key = BuiltInRegistries.FLUID.getKey(fluid);
        if (DIESEL_FLUID.equals(key) || FLOWING_DIESEL_FLUID.equals(key)) {
            return amountMb;           // 1 : 1
        }
        if (BIODIESEL_FLUID.equals(key) || FLOWING_BIODIESEL_FLUID.equals(key)) {
            return amountMb / 2;       // 1 : 0.5  (integer, floor)
        }
        return -1;
    }

    /** True for CDG diesel and biodiesel fluids. Used by the jet-suit mixin. */
    public static boolean isDieselFluid(Fluid fluid) {
        if (fluid == null) return false;
        ResourceLocation key = BuiltInRegistries.FLUID.getKey(fluid);
        return DIESEL_FLUID.equals(key) || FLOWING_DIESEL_FLUID.equals(key);
    }

    /** Buckets that the Alternative Motor (rocket) can burn. */
    public static boolean isAlternativeFuel(Item item) {
        if (item == null) return false;
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
        return GASOLINE_BUCKET.equals(key) || DIESEL_BUCKET.equals(key);
    }

    private CreateDieselCompat() {}
}
