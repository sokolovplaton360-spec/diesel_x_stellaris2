package com.dieselxstellaris.integration.stellaris.mixin;

import com.dieselxstellaris.integration.stellaris.DieselGeneratorStorage;
import com.st0x0ef.stellaris.common.blocks.entities.machines.DieselGeneratorBlockEntity;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.SingleFluidStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * After the constructor finishes, replaces the raw SingleFluidStorage with our wrapper.
 * This is safe because no other code runs between super() and the end of <init>.
 */
@Mixin(DieselGeneratorBlockEntity.class)
public abstract class DieselGeneratorMixin {

    @Shadow
    protected SingleFluidStorage dieselTank;

    @Inject(method = "<init>(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
            at = @At("TAIL"), remap = false)
    private void dxs$wrapDieselTank(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!(dieselTank instanceof DieselGeneratorStorage)) {
            dieselTank = new DieselGeneratorStorage(dieselTank);
        }
    }
}
