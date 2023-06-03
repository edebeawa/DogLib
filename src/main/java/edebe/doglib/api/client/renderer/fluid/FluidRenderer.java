package edebe.doglib.api.client.renderer.fluid;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.awt.*;

public interface FluidRenderer {
    void render(BlockAndTintGetter getter, BlockPos pos, VertexConsumer renderer, BlockState blockState, FluidState fluidState, Color color, int light, TextureAtlasSprite[] textureAtlasSprites);
}
