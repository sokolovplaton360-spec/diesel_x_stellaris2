package com.dieselxstellaris.integration.stellaris;

import com.dieselxstellaris.integration.create_diesel.CreateDieselCompat;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import dev.architectury.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.Iterator;

/**
 * Fluid tank of the Stellaris Jet Suit chestplate that also accepts Create: Diesel Generators diesel.
 * Incoming CDG diesel is converted 1:1 (1 mB = 1 mB) into Stellaris diesel, so everything else in Stellaris
 * (flight fuel use, tooltips, overlay) keeps working with its own fluid. All other calls go straight to the
 * original tank. Draining is not changed.
 */
public final class JetSuitDieselStorage implements UniversalFluidItemStorage {
    private final UniversalFluidItemStorage delegate;

    public JetSuitDieselStorage(UniversalFluidItemStorage delegate) {
        this.delegate = delegate;
    }

    /** Replaces CDG diesel with the same amount of Stellaris diesel. Any other fluid is returned unchanged. */
    static FluidStack convert(FluidStack stack) {
        if (stack != null && !stack.isEmpty() && CreateDieselCompat.isDieselFluid(stack.getFluid())) {
            Fluid stellarisDiesel = FluidRegistry.DIESEL_STILL.get();
            return FluidStack.create(stellarisDiesel, stack.getAmount());
        }
        return stack;
    }

    @Override
    public ItemStack getContainer() {
        return delegate.getContainer();
    }

    @Override
    public int getTanks() {
        return delegate.getTanks();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return delegate.getFluidInTank(tank);
    }

    @Override
    public long getTankCapacity(int tank) {
        return delegate.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return delegate.isFluidValid(tank, convert(stack));
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        return delegate.fill(convert(stack), simulate);
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        return delegate.drain(stack, simulate);
    }

    @Override
    public Iterator<FluidStack> iterator() {
        return delegate.iterator();
    }
}
