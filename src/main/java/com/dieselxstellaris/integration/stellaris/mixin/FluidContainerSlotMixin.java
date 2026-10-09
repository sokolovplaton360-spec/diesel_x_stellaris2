package com.dieselxstellaris.integration.stellaris.mixin;

import com.dieselxstellaris.integration.create_diesel.CreateDieselCompat;
import com.st0x0ef.stellaris.common.menus.slot.FluidContainerSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The diesel generator's bucket slot uses FluidContainerSlot.mayPlace.
 * It already accepts any ItemStack that has a fluid capability; what it actually
 * pours is controlled by DieselGeneratorStorage.isFluidValid.
 * However, CDG buckets are plain Item instances with no fluid capability that
 * Stellaris recognises, so we also need to greenlight them at the slot level.
 */
@Mixin(FluidContainerSlot.class)
public abstract class FluidContainerSlotMixin {

    @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true, remap = false)
    private void dxs$allowCdgBuckets(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack != null && !stack.isEmpty()) {
            if (CreateDieselCompat.isAlternativeFuel(stack.getItem())
                    || CreateDieselCompat.BIODIESEL_BUCKET.equals(
                            net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()))) {
                cir.setReturnValue(true);
            }
        }
    }
}
