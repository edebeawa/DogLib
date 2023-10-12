package edebe.doglib.api.event;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.LogicalSide;

public class EntityTickEvent extends TickEvent {
    private final Entity entity;
    private final Level level;
    private final BlockState feetBlockState;
    private final float walkDistO;
    private final float walkDist;
    private final BlockPos blockPosition;
    private final float xRotO;
    private final float yRotO;
    private final boolean wasInPowderSnow;
    private final boolean isInPowderSnow;
    private final boolean firstTick;

    private int boardingCooldown;
    private int remainingFireTicks;
    private float fallDistance;
    private Object2DoubleMap<FluidType> forgeFluidTypeHeight;

    public EntityTickEvent(Phase phase, Entity entity, Level level, BlockState feetBlockState, int boardingCooldown, float walkDistO, float walkDist, BlockPos blockPosition, float xRotO, float yRotO, boolean wasInPowderSnow, boolean isInPowderSnow, int remainingFireTicks, float fallDistance, boolean firstTick, Object2DoubleMap<FluidType> forgeFluidTypeHeight) {
        super(Type.PLAYER, level.isClientSide ? LogicalSide.CLIENT : LogicalSide.SERVER, phase);
        this.entity = entity;
        this.level = level;
        this.feetBlockState = feetBlockState;
        this.boardingCooldown = boardingCooldown;
        this.walkDistO = walkDistO;
        this.walkDist = walkDist;
        this.blockPosition = blockPosition;
        this.xRotO = xRotO;
        this.yRotO = yRotO;
        this.wasInPowderSnow = wasInPowderSnow;
        this.isInPowderSnow = isInPowderSnow;
        this.remainingFireTicks = remainingFireTicks;
        this.fallDistance = fallDistance;
        this.firstTick = firstTick;
        this.forgeFluidTypeHeight = forgeFluidTypeHeight;
    }

    public void setBoardingCooldown(int boardingCooldown) {
        this.boardingCooldown = boardingCooldown;
    }

    public void setRemainingFireTicks(int remainingFireTicks) {
        this.remainingFireTicks = remainingFireTicks;
    }

    public void setFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }

    public void setForgeFluidTypeHeight(Object2DoubleMap<FluidType> forgeFluidTypeHeight) {
        this.forgeFluidTypeHeight = forgeFluidTypeHeight;
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

    public int getBoardingCooldown() {
        return boardingCooldown;
    }

    public float getWalkDistOld() {
        return walkDistO;
    }

    public float getWalkDist() {
        return walkDist;
    }

    public BlockPos getBlockPos() {
        return blockPosition;
    }

    public float getXRotOld() {
        return xRotO;
    }

    public float getYRotOld() {
        return yRotO;
    }

    public boolean wasInPowderSnow() {
        return wasInPowderSnow;
    }

    public boolean isInPowderSnow() {
        return isInPowderSnow;
    }

    public int getRemainingFireTicks() {
        return remainingFireTicks;
    }

    public float getFallDistance() {
        return fallDistance;
    }

    public boolean isFirstTick() {
        return firstTick;
    }

    public Object2DoubleMap<FluidType> getForgeFluidTypeHeight() {
        return forgeFluidTypeHeight;
    }
}
