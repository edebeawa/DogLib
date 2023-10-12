package edebe.doglib.api.world.dimension;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IModBaseBlock {
    ResourceLocation getResourceLocation();

    Block getBlock();

    default BlockState getDefaultBlockState() {
        return this.getBlock().defaultBlockState();
    }

    default <P extends AbstractRegistrate<P>> ItemBuilder<BlockItem, P> getBuilder(P registry) {
        return registry.item(this.getResourceLocation().getPath(), (properties) -> new BlockItem(this.getBlock(), properties));
    }
}