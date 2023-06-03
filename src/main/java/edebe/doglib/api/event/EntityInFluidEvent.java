package edebe.doglib.api.event;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fluids.FluidType;

@Cancelable
public class EntityInFluidEvent extends Event {
    private final Entity entity;
    private final Level level;
    private final BlockState feetBlockState;
    private final BlockPos blockPosition;
    private final boolean wasInPowderSnow;
    private final boolean isInPowderSnow;
    private final boolean firstTick;
    private final Object2DoubleMap<FluidType> forgeFluidTypeHeight;
    private float fallDistance;

    public EntityInFluidEvent(Entity entity, Level level, BlockState feetBlockState, BlockPos blockPosition, boolean wasInPowderSnow, boolean isInPowderSnow, boolean firstTick, Object2DoubleMap<FluidType> forgeFluidTypeHeight, float fallDistance) {
        this.entity = entity;
        this.level = level;
        this.feetBlockState = feetBlockState;
        this.blockPosition = blockPosition;
        this.wasInPowderSnow = wasInPowderSnow;
        this.isInPowderSnow = isInPowderSnow;
        this.firstTick = firstTick;
        this.forgeFluidTypeHeight = forgeFluidTypeHeight;
        this.fallDistance = fallDistance;
    }

    public void setFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }

    public Entity getEntity() {
        return entity;
    }

    public Level getLevel() {
        return level;
    }

    public BlockState getFeetBlockState() {
        return feetBlockState;
    }

    public BlockPos getBlockPos() {
        return blockPosition;
    }

    public boolean wasInPowderSnow() {
        return wasInPowderSnow;
    }

    public boolean isInPowderSnow() {
        return isInPowderSnow;
    }

    public boolean isFirstTick() {
        return firstTick;
    }

    public Object2DoubleMap<FluidType> getForgeFluidTypeHeight() {
        return forgeFluidTypeHeight;
    }

    public float getFallDistance() {
        return fallDistance;
    }
}
