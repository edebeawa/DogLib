package edebe.doglib.api.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.List;

public interface BlockHelper {
    static List<ItemStack> defaultDrops(List<ItemStack> dropsOriginal, List<ItemStack> items) {
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;

        return items;
    }

    static List<ItemStack> defaultDrop(List<ItemStack> dropsOriginal, ItemLike item) {
        return defaultDrops(dropsOriginal, Collections.singletonList(new ItemStack(item)));
    }

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
}
