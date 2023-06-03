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
public class EntityTickEvent extends Event {
    private final Entity entity;
    private Level level;
    private BlockState feetBlockState;
    private int boardingCooldown;
    private float walkDistO;
    private float walkDist;
    private BlockPos blockPosition;
    private float xRotO;
    private float yRotO;
    private boolean wasInPowderSnow;
    private boolean isInPowderSnow;
    private int remainingFireTicks;
    private float fallDistance;
    private boolean firstTick;
    private Object2DoubleMap<FluidType> forgeFluidTypeHeight;

    public EntityTickEvent(Entity entity, Level level, BlockState feetBlockState, int boardingCooldown, float walkDistO, float walkDist, BlockPos blockPosition, float xRotO, float yRotO, boolean wasInPowderSnow, boolean isInPowderSnow, int remainingFireTicks, float fallDistance, boolean firstTick, Object2DoubleMap<FluidType> forgeFluidTypeHeight) {
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

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setFeetBlockState(BlockState feetBlockState) {
        this.feetBlockState = feetBlockState;
    }

    public void setBoardingCooldown(int boardingCooldown) {
        this.boardingCooldown = boardingCooldown;
    }

    public void setWalkDistOld(float walkDistOld) {
        this.walkDistO = walkDistOld;
    }

    public void setWalkDist(float walkDist) {
        this.walkDist = walkDist;
    }

    public void setBlockPos(BlockPos pos) {
        this.blockPosition = pos;
    }

    public void setXRotOld(float xRotOld) {
        this.xRotO = xRotOld;
    }

    public void setYRotOld(float yRotOld) {
        this.yRotO = yRotOld;
    }

    public void setWasInPowderSnow(boolean wasInPowderSnow) {
        this.wasInPowderSnow = wasInPowderSnow;
    }

    public void setInPowderSnow(boolean inPowderSnow) {
        isInPowderSnow = inPowderSnow;
    }

    public void setRemainingFireTicks(int remainingFireTicks) {
        this.remainingFireTicks = remainingFireTicks;
    }

    public void setFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }

    public void setFirstTick(boolean firstTick) {
        this.firstTick = firstTick;
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
