package edebe.doglib.mixin;

import edebe.doglib.api.event.EntityInFluidEvent;
import edebe.doglib.api.event.EntityTickEvent;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IForgeEntity {
    @Shadow public Level level;
    @Shadow @Nullable private BlockState feetBlockState;
    @Shadow protected int boardingCooldown;
    @Shadow public float walkDistO;
    @Shadow public float walkDist;
    @Shadow private BlockPos blockPosition;
    @Shadow public float xRotO;
    @Shadow public float yRotO;
    @Shadow public boolean wasInPowderSnow;
    @Shadow public boolean isInPowderSnow;
    @Shadow private int remainingFireTicks;
    @Shadow public float fallDistance;
    @Shadow protected boolean firstTick;
    @Shadow(remap = false) protected Object2DoubleMap<FluidType> forgeFluidTypeHeight;

    @Shadow
    @Nullable
    public abstract Entity getVehicle();

    @Shadow
    public abstract boolean isPassenger();

    @Shadow
    public abstract void stopRiding();

    @Shadow
    public abstract float getXRot();

    @Shadow
    public abstract float getYRot();

    @Shadow
    protected abstract void handleNetherPortal();

    @Shadow
    public abstract boolean canSpawnSprintParticle();

    @Shadow
    protected abstract void spawnSprintParticle();

    @Shadow
    protected abstract boolean updateInWaterStateAndDoFluidPushing();

    @Shadow
    protected abstract void updateFluidOnEyes();

    @Shadow
    public abstract void updateSwimming();

    @Shadow
    public abstract void clearFire();

    @Shadow
    public abstract boolean fireImmune();

    @Shadow
    public abstract void setRemainingFireTicks(int p_20269_);

    @Shadow
    public abstract boolean isInLava();

    @Shadow
    public abstract boolean hurt(DamageSource p_19946_, float p_19947_);

    @Shadow
    public abstract int getTicksFrozen();

    @Shadow
    public abstract void setTicksFrozen(int p_146918_);

    @Shadow
    public abstract void lavaHurt();

    @Shadow
    public abstract void checkOutOfWorld();

    @Shadow
    public abstract void setSharedFlagOnFire(boolean p_146869_);

    /**
     * @author edebe
     * @reason
     */
    @Overwrite
    public void baseTick() {
        EntityTickEvent event = new EntityTickEvent((Entity) (Object)this, level, feetBlockState, boardingCooldown, walkDistO, walkDist, blockPosition, xRotO, yRotO, wasInPowderSnow, isInPowderSnow, remainingFireTicks, fallDistance, firstTick, forgeFluidTypeHeight);
        level = event.getLevel();
        feetBlockState = event.getFeetBlockState();
        boardingCooldown = event.getBoardingCooldown();
        walkDistO = event.getWalkDistOld();
        walkDist = event.getWalkDist();
        blockPosition = event.getBlockPos();
        xRotO = event.getXRotOld();
        yRotO = event.getYRotOld();
        wasInPowderSnow = event.wasInPowderSnow();
        isInPowderSnow = event.isInPowderSnow();
        remainingFireTicks = event.getRemainingFireTicks();
        fallDistance = event.getFallDistance();
        firstTick = event.isFirstTick();
        forgeFluidTypeHeight = event.getForgeFluidTypeHeight();
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            this.level.getProfiler().push("entityBaseTick");
            this.feetBlockState = null;
            if (this.isPassenger() && this.getVehicle().isRemoved()) {
                this.stopRiding();
            }

            if (this.boardingCooldown > 0) {
                --this.boardingCooldown;
            }

            this.walkDistO = this.walkDist;
            this.xRotO = this.getXRot();
            this.yRotO = this.getYRot();
            this.handleNetherPortal();
            if (this.canSpawnSprintParticle()) {
                this.spawnSprintParticle();
            }

            this.wasInPowderSnow = this.isInPowderSnow;
            this.isInPowderSnow = false;
            this.updateInWaterStateAndDoFluidPushing();
            this.updateFluidOnEyes();
            this.updateSwimming();
            if (this.level.isClientSide) {
                this.clearFire();
            } else if (this.remainingFireTicks > 0) {
                if (this.fireImmune()) {
                    this.setRemainingFireTicks(this.remainingFireTicks - 4);
                    if (this.remainingFireTicks < 0) {
                        this.clearFire();
                    }
                } else {
                    if (this.remainingFireTicks % 20 == 0 && !this.isInLava()) {
                        this.hurt(DamageSource.ON_FIRE, 1.0F);
                    }

                    this.setRemainingFireTicks(this.remainingFireTicks - 1);
                }

                if (this.getTicksFrozen() > 0) {
                    this.setTicksFrozen(0);
                    this.level.levelEvent((Player) null, 1009, this.blockPosition, 1);
                }
            }

            EntityInFluidEvent entityInFluidEvent = new EntityInFluidEvent((Entity) (Object)this, level, feetBlockState, blockPosition, wasInPowderSnow, isInPowderSnow, firstTick, forgeFluidTypeHeight, fallDistance);
            this.fallDistance = entityInFluidEvent.getFallDistance();
            if (!MinecraftForge.EVENT_BUS.post(entityInFluidEvent)) {
                if (this.isInLava()) {
                    this.lavaHurt();
                    this.fallDistance *= this.getFluidFallDistanceModifier(net.minecraftforge.common.ForgeMod.LAVA_TYPE.get());
                }
            }

            this.checkOutOfWorld();
            if (!this.level.isClientSide) {
                this.setSharedFlagOnFire(this.remainingFireTicks > 0);
            }

            this.firstTick = false;
            this.level.getProfiler().pop();
        }
    }
}
