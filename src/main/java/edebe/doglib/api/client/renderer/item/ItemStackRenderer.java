package edebe.doglib.api.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ItemStackRenderer {
    interface Item extends ItemStackRenderer {
        void renderItem(ItemRenderer renderer, BlockEntityWithoutLevelRenderer getter, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean isLeftHand, PoseStack matrixStack, MultiBufferSource bufferSource, ItemColors itemColors, int light, int overlay, TextureManager textureManager, ItemModelShaper modelShaper, BakedModel bakedModel);
    }

    interface Hand extends ItemStackRenderer {
        void renderHand(ItemRenderer renderer, Minecraft minecraft, AbstractClientPlayer player, EntityRenderDispatcher dispatcher, float partialTick, float pitch, InteractionHand hand, HumanoidArm arm, float swingProcess, ItemStack itemStack, float equipProcess, PoseStack matrixStack, MultiBufferSource bufferSource, int light);
    }
}