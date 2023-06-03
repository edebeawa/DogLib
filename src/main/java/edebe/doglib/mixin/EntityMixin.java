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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IForgeEntity {
    @Shadow public Level f_19853_;//level
    @Shadow @Nullable private BlockState f_185934_;//feetBlockState
    @Shadow protected int f_19851_;//boardingCooldown
    @Shadow public float f_19867_;//walkDistO
    @Shadow public float f_19787_;//walkDist
    @Shadow private BlockPos f_19826_;//blockPosition
    @Shadow public float f_19860_;//xRotO
    @Shadow public float f_19859_;//yRotO
    @Shadow public boolean f_146809_;//wasInPowderSnow
    @Shadow public boolean f_146808_;//isInPowderSnow
    @Shadow private int f_19831_;//remainingFireTicks
    @Shadow public float f_19789_;//fallDistance
    @Shadow protected boolean f_19803_;//firstTick
    @Shadow protected Object2DoubleMap<FluidType> forgeFluidTypeHeight;

    @Nullable
    @Invoker("m_20202_")
    abstract Entity getVehicle();

    @Invoker("m_20159_")
    abstract boolean isPassenger();

    @Invoker("m_8127_")
    abstract void stopRiding();

    @Invoker("m_146909_")
    abstract float getXRot();

    @Invoker("m_146908_")
    abstract float getYRot();

    @Invoker("m_20157_")
    protected abstract void handleNetherPortal();

    @Invoker("m_5843_")
    abstract boolean canSpawnSprintParticle();

    @Invoker("m_20076_")
    protected abstract void spawnSprintParticle();

    @Invoker("m_20073_")
    protected abstract boolean updateInWaterStateAndDoFluidPushing();

    @Invoker("m_20323_")
    protected abstract void updateFluidOnEyes();

    @Invoker("m_5844_")
    abstract void updateSwimming();

    @Invoker("m_20095_")
    abstract void clearFire();

    @Invoker("m_5825_")
    abstract boolean fireImmune();

    @Invoker("m_7311_")
    abstract void setRemainingFireTicks(int p_20269_);

    @Invoker("m_20077_")
    abstract boolean isInLava();

    @Invoker("m_6469_")
    abstract boolean hurt(DamageSource p_19946_, float p_19947_);

    @Invoker("m_146888_")
    abstract int getTicksFrozen();

    @Invoker("m_146917_")
    abstract void setTicksFrozen(int p_146918_);

    @Invoker("m_20093_")
    abstract void lavaHurt();

    @Invoker("m_146871_")
    abstract void checkOutOfWorld();

    @Invoker("m_146868_")
    abstract void setSharedFlagOnFire(boolean p_146869_);

    /**
     * @author
     */
    @Overwrite//baseTick
    public void m_6075_() {
        EntityTickEvent event = new EntityTickEvent((Entity) (Object)this, f_19853_, f_185934_, f_19851_, f_19867_, f_19787_, f_19826_, f_19860_, f_19859_, f_146809_, f_146808_, f_19831_, f_19789_, f_19803_, forgeFluidTypeHeight);
        f_19853_ = event.getLevel();
        f_185934_ = event.getFeetBlockState();
        f_19851_ = event.getBoardingCooldown();
        f_19867_ = event.getWalkDistOld();
        f_19787_ = event.getWalkDist();
        f_19826_ = event.getBlockPos();
        f_19860_ = event.getXRotOld();
        f_19859_ = event.getYRotOld();
        f_146809_ = event.wasInPowderSnow();
        f_146808_ = event.isInPowderSnow();
        f_19831_ = event.getRemainingFireTicks();
        f_19789_ = event.getFallDistance();
        f_19803_ = event.isFirstTick();
        forgeFluidTypeHeight = event.getForgeFluidTypeHeight();
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            this.f_19853_.getProfiler().push("entityBaseTick");
            this.f_185934_ = null;
            if (this.isPassenger() && this.getVehicle().isRemoved()) {
                this.stopRiding();
            }

            if (this.f_19851_ > 0) {
                --this.f_19851_;
            }

            this.f_19867_ = this.f_19787_;
            this.f_19860_ = this.getXRot();
            this.f_19859_ = this.getYRot();
            this.handleNetherPortal();
            if (this.canSpawnSprintParticle()) {
                this.spawnSprintParticle();
            }

            this.f_146809_ = this.f_146808_;
            this.f_146808_ = false;
            this.updateInWaterStateAndDoFluidPushing();
            this.updateFluidOnEyes();
            this.updateSwimming();
            if (this.f_19853_.isClientSide) {
                this.clearFire();
            } else if (this.f_19831_ > 0) {
                if (this.fireImmune()) {
                    this.setRemainingFireTicks(this.f_19831_ - 4);
                    if (this.f_19831_ < 0) {
                        this.clearFire();
                    }
                } else {
                    if (this.f_19831_ % 20 == 0 && !this.isInLava()) {
                        this.hurt(DamageSource.ON_FIRE, 1.0F);
                    }

                    this.setRemainingFireTicks(this.f_19831_ - 1);
                }

                if (this.getTicksFrozen() > 0) {
                    this.setTicksFrozen(0);
                    this.f_19853_.levelEvent((Player) null, 1009, this.f_19826_, 1);
                }
            }

            EntityInFluidEvent entityInFluidEvent = new EntityInFluidEvent((Entity) (Object)this, f_19853_, f_185934_, f_19826_, f_146809_, f_146808_, f_19803_, forgeFluidTypeHeight, f_19789_);
            this.f_19789_ = entityInFluidEvent.getFallDistance();
            if (!MinecraftForge.EVENT_BUS.post(entityInFluidEvent)) {
                if (this.isInLava()) {
                    this.lavaHurt();
                    this.f_19789_ *= this.getFluidFallDistanceModifier(net.minecraftforge.common.ForgeMod.LAVA_TYPE.get());
                }
            }

            this.checkOutOfWorld();
            if (!this.f_19853_.isClientSide) {
                this.setSharedFlagOnFire(this.f_19831_ > 0);
            }

            this.f_19803_ = false;
            this.f_19853_.getProfiler().pop();
        }
    }
}
