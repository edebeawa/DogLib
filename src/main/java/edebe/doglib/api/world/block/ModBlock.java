package edebe.doglib.api.world.block;

import edebe.doglib.api.helper.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
public class ModBlock extends Block {
    private final int miningLevel;

    public ModBlock(Properties properties) {
        this(properties, -1, false);
    }

    public ModBlock(Properties properties, boolean translucent) {
        this(properties, -1, translucent);
    }

    public ModBlock(Properties properties, int miningLevel) {
        this(properties, miningLevel, false);
    }

    public ModBlock(Properties properties, int miningLevel, boolean translucent) {
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
}
