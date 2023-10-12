package edebe.doglib.api.event.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ItemRenderEvent extends Event {
    private final ItemRenderer renderer;
    private final BlockEntityWithoutLevelRenderer getter;
    private final ItemStack itemStack;
    private final ItemTransforms.TransformType transformType;
    private final boolean isLeftHand;
    private final PoseStack poseStack;
    private final MultiBufferSource bufferSource;
    private final ItemColors itemColors;
    private final int light;
    private final int overlay;
    private final TextureManager textureManager;
    private final ItemModelShaper modelShaper;
    private final BakedModel bakedModel;

    public ItemRenderEvent(ItemRenderer renderer, BlockEntityWithoutLevelRenderer getter, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean isLeftHand, PoseStack poseStack, MultiBufferSource bufferSource, ItemColors itemColors, int light, int overlay, TextureManager textureManager, ItemModelShaper modelShaper, BakedModel bakedModel) {
        this.renderer = renderer;
        this.getter = getter;
        this.itemStack = itemStack;
        this.transformType = transformType;
        this.isLeftHand = isLeftHand;
        this.poseStack = poseStack;
        this.bufferSource = bufferSource;
        this.itemColors = itemColors;
        this.light = light;
        this.overlay = overlay;
        this.textureManager = textureManager;
        this.modelShaper = modelShaper;
        this.bakedModel = bakedModel;
    }

    public ItemRenderer getRenderer() {
        return renderer;
    }

    public BlockEntityWithoutLevelRenderer getWorld() {
        return getter;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ItemTransforms.TransformType getTransformType() {
        return transformType;
    }

    public boolean isLeftHand() {
        return isLeftHand;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public MultiBufferSource getBufferSource() {
        return bufferSource;
    }

    public ItemColors getItemColors() {
        return itemColors;
    }

    public int getLight() {
        return light;
    }

    public int getOverlay() {
        return overlay;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public ItemModelShaper getItemModelShaper() {
        return modelShaper;
    }

    public BakedModel getBakedModel() {
        return bakedModel;
    }
}
