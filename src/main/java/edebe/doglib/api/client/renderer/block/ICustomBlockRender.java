package edebe.doglib.api.client.renderer.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public interface ICustomBlockRender {
    BlockRenderer getBlockRenderer();

    boolean renderBlock(BlockAndTintGetter getter, BlockState state, BlockPos pos);
}
