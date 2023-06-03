package edebe.doglib.api.world.block.block_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class FluidTankBlockEntity extends ModBlockEntity {
    private final FluidTank fluidTank = createFluidTank();

    public FluidTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected abstract FluidTank createFluidTank();

    @Override
    public void writePacketNBT(CompoundTag tag) {
        fluidTank.writeToNBT(tag);
        writeNBT(tag);
    }

    @Override
    public void readPacketNBT(CompoundTag tag) {
        fluidTank.readFromNBT(tag);
        readNBT(tag);
    }

    protected void writeNBT(CompoundTag tag) {}

    protected void readNBT(CompoundTag tag) {}

    @NotNull
    @Override
    @ParametersAreNonnullByDefault
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && capability == ForgeCapabilities.FLUID_HANDLER)
            return LazyOptional.of(() -> fluidTank).cast();
        return super.getCapability(capability, facing);
    }

    public final FluidTank getFluidTank() {
        return fluidTank;
    }
}
