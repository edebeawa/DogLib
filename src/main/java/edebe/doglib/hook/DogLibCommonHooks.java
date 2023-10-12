package edebe.doglib.hook;

import edebe.doglib.api.event.EntityInFluidEvent;
import edebe.doglib.api.event.EntityTickEvent;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fluids.FluidType;

public class DogLibCommonHooks {
    public static EntityInFluidEvent onEntityInFluid(Entity entity, Level level, BlockState feetBlockState, BlockPos blockPosition, boolean wasInPowderSnow, boolean isInPowderSnow, boolean firstTick, Object2DoubleMap<FluidType> forgeFluidTypeHeight, float fallDistance) {
        return new EntityInFluidEvent(entity, level, feetBlockState, blockPosition, wasInPowderSnow, isInPowderSnow, firstTick, forgeFluidTypeHeight, fallDistance);
    }

    public static EntityTickEvent onEntityPreTick(Entity entity, Level level, BlockState feetBlockState, int boardingCooldown, float walkDistO, float walkDist, BlockPos blockPosition, float xRotO, float yRotO, boolean wasInPowderSnow, boolean isInPowderSnow, int remainingFireTicks, float fallDistance, boolean firstTick, Object2DoubleMap<FluidType> forgeFluidTypeHeight) {
        return new EntityTickEvent(TickEvent.Phase.START, entity, level, feetBlockState, boardingCooldown, walkDistO, walkDist, blockPosition, xRotO, yRotO, wasInPowderSnow, isInPowderSnow, remainingFireTicks, fallDistance, firstTick, forgeFluidTypeHeight);
    }

    public static void onEntityPostTick(Entity entity, Level level, BlockState feetBlockState, int boardingCooldown, float walkDistO, float walkDist, BlockPos blockPosition, float xRotO, float yRotO, boolean wasInPowderSnow, boolean isInPowderSnow, int remainingFireTicks, float fallDistance, boolean firstTick, Object2DoubleMap<FluidType> forgeFluidTypeHeight) {
        MinecraftForge.EVENT_BUS.post(new EntityTickEvent(TickEvent.Phase.END, entity, level, feetBlockState, boardingCooldown, walkDistO, walkDist, blockPosition, xRotO, yRotO, wasInPowderSnow, isInPowderSnow, remainingFireTicks, fallDistance, firstTick, forgeFluidTypeHeight));
    }
}