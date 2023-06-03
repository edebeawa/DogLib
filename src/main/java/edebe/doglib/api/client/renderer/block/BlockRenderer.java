package edebe.doglib.api.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public interface BlockRenderer {
    void render(ModelBlockRenderer renderer, BlockAndTintGetter getter, BlockState blockState, BlockPos blockPos, PoseStack matrixStack, VertexConsumer vertexConsumer, boolean checkSides, RandomSource randomSource, long seed, BlockColors blockColors, int light, int overlay, ModelData modelData, RenderType renderType, BakedModel bakedModel);
}
