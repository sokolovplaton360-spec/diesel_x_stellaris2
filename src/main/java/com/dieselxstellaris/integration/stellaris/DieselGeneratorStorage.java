package com.dieselxstellaris.integration.stellaris;

import com.dieselxstellaris.integration.create_diesel.CreateDieselCompat;
import com.st0x0ef.stellaris.common.registry.FluidRegistry;
import com.st0x0ef.stellaris.common.utils.capabilities.fluid.SingleFluidStorage;
import dev.architectury.fluid.FluidStack;
import net.minecraft.world.level.material.Fluid;
import java.util.Iterator;

/**
 * Wraps the Stellaris diesel generator's SingleFluidStorage so that CDG diesel (1:1)
 * and CDG biodiesel (1:0.5) are also accepted.
 *
 * The generator's tick, save and load code reads from this.dieselTank directly.
 * Our wrapper is placed in that field by DieselGeneratorMixin after construction, so all
 * fill/isFluidValid calls pass through us, while drain, save, load and the real onChange
 * stay on the original $1 delegate.
 *
 * onChange() in SingleFluidStorage is protected abstract. The generator never calls it on
 * our wrapper (it calls it on the inner $1 it creates itself). We provide an empty override
 * only to satisfy the compiler.
 */
public final class DieselGeneratorStorage extends SingleFluidStorage {

    private final SingleFluidStorage delegate;

    public DieselGeneratorStorage(SingleFluidStorage delegate) {
        super(0L);           // capacity unused – we forward everything to the delegate
        this.delegate = delegate;
    }

    /** Called only by subclass contract; the generator never invokes this on our wrapper. */
    @Override
    protected void onChange() {
        // intentionally empty: the real onChange lives on the delegate ($1 anonymous class)
        // and is called by Stellaris through the original reference it stored internally.
    }

    /* ---- conversion helpers ---- */

    /** True if 1 mB of the fluid converts to 0.5 mB of Stellaris diesel (biodiesel). */
    private static boolean isBiodiesel(Fluid f) {
        return CreateDieselCompat.toStellarisMillibuckets(f, 1000L) == 500L;
    }

    /* ---- forwarded methods ---- */

    @Override public int getTanks()                         { return delegate.getTanks(); }
    @Override public FluidStack getFluidInTank(int tank)    { return delegate.getFluidInTank(tank); }
    @Override public long getTankCapacity(int tank)         { return delegate.getTankCapacity(tank); }
    @Override public Iterator<FluidStack> iterator()        { return delegate.iterator(); }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        if (stack != null && !stack.isEmpty()) {
            long conv = CreateDieselCompat.toStellarisMillibuckets(stack.getFluid(), 1000L);
            if (conv > 0) return true;
        }
        return delegate.isFluidValid(tank, stack);
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        if (stack == null || stack.isEmpty()) return 0;
        long conv = CreateDieselCompat.toStellarisMillibuckets(stack.getFluid(), stack.getAmount());
        if (conv > 0) {
            // Fill the delegate with Stellaris diesel
            FluidStack stellaris = FluidStack.create(FluidRegistry.DIESEL_STILL.get(), conv);
            long filled = delegate.fill(stellaris, simulate);
            if (filled <= 0) return 0;
            // Report consumed CDG mB back to the caller (Spout / bucket handler)
            // Biodiesel: 2 CDG mB → 1 Stellaris mB, so scale back up
            return isBiodiesel(stack.getFluid()) ? filled * 2L : filled;
        }
        return delegate.fill(stack, simulate);
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        return delegate.drain(stack, simulate);
    }
}
