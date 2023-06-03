package edebe.doglib.api.world.block;

import edebe.doglib.api.helper.BlockHelper;
import edebe.doglib.api.world.block.block_entity.SimpleInventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public abstract class ModEntityBlock extends BaseEntityBlock {
    private final int miningLevel;

    public ModEntityBlock(Properties properties) {
        this(properties, -1, false);
    }

    public ModEntityBlock(Properties properties, boolean translucent) {
        this(properties, -1, translucent);
    }

    public ModEntityBlock(Properties properties, int miningLevel) {
        this(properties, miningLevel, false);
    }

    public ModEntityBlock(Properties properties, int miningLevel, boolean translucent) {
        super(translucent ? properties.noOcclusion().isRedstoneConductor((bs, br, bp) -> false) : properties);
        this.miningLevel = miningLevel;
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        if (this.miningLevel == -1) return true;

        if (player.getInventory().getSelected().getItem() instanceof TieredItem tieredItem)
            return tieredItem.getTier().getLevel() >= this.miningLevel;
        return false;
    }

    @NotNull
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return BlockHelper.defaultDrop(super.getDrops(state, builder), this);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof SimpleInventoryBlockEntity inventoryBlockEntity) {
                Containers.dropContents(world, pos, (Container) inventoryBlockEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }
}
