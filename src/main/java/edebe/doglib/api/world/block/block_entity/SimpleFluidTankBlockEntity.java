package edebe.doglib.api.world.block.block_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public abstract class SimpleFluidTankBlockEntity extends FluidTankBlockEntity {
    public SimpleFluidTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void addFluid(Fluid fluid) {
        addFluid(fluid, 1);
    }

    public void addFluid(Fluid fluid, int amount) {
        if (compareFluid(Fluids.EMPTY))
            setFluid(fluid, amount);
    }

    public void setFluid(Fluid fluid) {
        setFluid(fluid, getAmount());
    }

    public void setFluid(Fluid fluid, int amount) {
        setFluid(new FluidStack(fluid, amount));
    }

    public void setFluid(FluidStack stack) {
        getFluidTank().setFluid(stack);
        updateAmount();
        updateLight();
    }

    public FluidStack getOrCreateFluid(FluidStack stack) {
        if (compareFluid(Fluids.EMPTY)) {
            setFluid(stack.getFluid(), 1);
            stack.shrink(1);
        }
        return getFluid();
    }

    public FluidStack getFluid() {
        return getFluidTank().getFluid();
    }

    public int getCapacity() {
        return getFluidTank().getCapacity();
    }

    public boolean isEmpty() {
        return getFluid().isEmpty();
    }

    public boolean compareFluid(Fluid fluid) {
        return getFluid().getFluid() == fluid;
    }

    public void setAmount(int amount) {
        if (!compareFluid(Fluids.EMPTY)) {
            getFluid().setAmount(amount);
            updateAmount();
            updateLight();
        }
    }

    public int getAmount() {
        return getFluid().getAmount();
    }

    public void growAmount(int amount) {
        if (!compareFluid(Fluids.EMPTY)) {
            getFluid().grow(amount);
            updateAmount();
            updateLight();
        }
    }

    public void shrinkAmount(int amount) {
        if (!compareFluid(Fluids.EMPTY)) {
            getFluid().shrink(amount);
            updateAmount();
            updateLight();
        }
    }

    public void updateAmount() {
        if (getAmount() > getCapacity())
            getFluid().setAmount(getCapacity());
    }
}
