package edebe.doglib.api.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.function.Function;

public interface BlockEntityHelper {
    @SuppressWarnings("unchecked")
    static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTicker(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }

    static MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof MenuProvider ? (MenuProvider)blockEntity : null;
    }

    static boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(id, param);
    }

    static boolean getBlockEntitiesInArea(LevelAccessor level, BlockPos pos, int radius, Function<BlockEntity, Boolean> consumer) {
        for (int x = pos.getX() - radius >> 4; x <= pos.getX() + radius >> 4; x++) {
            for (int z = pos.getZ() - radius >> 4; z <= pos.getZ() + radius >> 4; z++) {
                ChunkAccess chunk = ChunkHelper.getLoadedChunk(level, x, z);
                if (chunk != null) {
                    for (BlockPos tilePos : chunk.getBlockEntitiesPos()) {
                        if (tilePos.distSqr(pos) <= radius * radius)
                            if (consumer.apply(chunk.getBlockEntity(tilePos))) return true;
                    }
                }
            }
        }
        return false;
    }
}
