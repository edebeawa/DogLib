package edebe.doglib.api.helper;

import net.minecraftforge.fluids.FluidStack;

public interface FluidHelper {
    static int getFluidScaled(int pixels, FluidStack fluid, int maxLiquidAmount) {
        if(maxLiquidAmount == 0) return pixels;

        long currentLiquidAmount = fluid.getAmount();
        long x = currentLiquidAmount * pixels / maxLiquidAmount;
        return pixels - (int) x;
    }
}
