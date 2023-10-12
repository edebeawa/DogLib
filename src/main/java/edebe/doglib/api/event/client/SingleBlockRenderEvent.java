package edebe.doglib.api.event.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Deprecated
@Cancelable
public class SingleBlockRenderEvent extends Event {
    private final ModelBlockRenderer renderer;
    private final RenderShape renderShape;
    private final BlockState blockState;
    private final PoseStack poseStack;
    private final MultiBufferSource bufferSource;
    private final BlockColors blockColors;
    private final int light;
    private final int overlay;
    private final ModelData modelData;
    private final RenderType renderType;
    private final BakedModel bakedModel;

    public SingleBlockRenderEvent(ModelBlockRenderer renderer, RenderShape renderShape, BlockState blockState, PoseStack poseStack, MultiBufferSource bufferSource, BlockColors blockColors, int light, int overlay, ModelData modelData, RenderType renderType, BakedModel bakedModel) {
        this.renderer = renderer;
        this.renderShape = renderShape;
        this.blockState = blockState;
        this.poseStack = poseStack;
        this.bufferSource = bufferSource;
        this.blockColors = blockColors;
        this.light = light;
        this.overlay = overlay;
        this.modelData = modelData;
        this.renderType = renderType;
        this.bakedModel = bakedModel;
    }

    public ModelBlockRenderer getRenderer() {
        return renderer;
    }

    public RenderShape getRenderShape() {
        return renderShape;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public MultiBufferSource getBufferSource() {
        return bufferSource;
    }

    public BlockColors getBlockColors() {
        return blockColors;
    }

    public int getLight() {
        return light;
    }

    public int getOverlay() {
        return overlay;
    }

    public ModelData getModelData() {
        return modelData;
    }

    public RenderType getRenderType() {
        return renderType;
    }

    public BakedModel getBakedModel() {
        return bakedModel;
    }
}
