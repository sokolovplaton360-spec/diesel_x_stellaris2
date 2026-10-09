package com.dieselxstellaris.integration.stellaris.mixin;

import com.dieselxstellaris.integration.stellaris.JetSuitDieselStorage;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.st0x0ef.stellaris.common.items.armors.AbstractSpaceArmor;
import com.st0x0ef.stellaris.common.items.armors.JetSuit;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The Jet Suit chestplate exposes its tank through Stellaris' getFluidTank. Potentials turns that tank into the
 * NeoForge item fluid capability, which is what the Create Spout fills. Wrapping the returned tank here lets the
 * Spout pour Create: Diesel Generators diesel into the suit, with no capability ordering issues.
 */
@Mixin(AbstractSpaceArmor.Chestplate.class)
public abstract class JetSuitChestplateMixin {
    @Inject(method = "getFluidTank", at = @At("RETURN"), cancellable = true, remap = false)
    private void dxs$acceptCreateDiesel(ItemStack stack, CallbackInfoReturnable<UniversalFluidItemStorage> cir) {
        if ((Object) this instanceof JetSuit.Suit) {
            UniversalFluidItemStorage original = cir.getReturnValue();
            if (original != null && !(original instanceof JetSuitDieselStorage)) {
                cir.setReturnValue(new JetSuitDieselStorage(original));
            }
        }
    }
}
