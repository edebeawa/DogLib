package edebe.doglib.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import edebe.doglib.api.client.renderer.ModRenderType;
import edebe.doglib.api.client.renderer.item.ICustomItemFoil;
import edebe.doglib.api.client.renderer.item.ICustomItemRender;
import edebe.doglib.api.client.renderer.item.IItemRenderColor;
import edebe.doglib.api.event.client.ItemFoilModifyEvent;
import edebe.doglib.api.event.client.ItemRenderEvent;
import edebe.doglib.api.helper.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow @Final private ItemModelShaper f_115095_;//itemModelShaper
    @Shadow @Final private TextureManager f_115096_;//textureManager
    @Shadow @Final private ItemColors f_115097_;//itemColors
    @Shadow @Final private BlockEntityWithoutLevelRenderer f_174223_;//blockEntityRenderer

    @Invoker("m_115189_")
    abstract void renderModelLists(BakedModel p_115190_, ItemStack p_115191_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_);

    /**
     * @author
     */
    @Overwrite//render
    public void m_115143_(ItemStack stack, ItemTransforms.TransformType transformType, boolean isLeftHand, PoseStack matrixStack, MultiBufferSource buffers, int light, int overlay, BakedModel bakedModel) {
        if (!stack.isEmpty()) if (!MinecraftForge.EVENT_BUS.post(new ItemRenderEvent((ItemRenderer) (Object)this, this.f_174223_, stack, transformType, isLeftHand, matrixStack, buffers, this.f_115097_, light, overlay, f_115096_, this.f_115095_, bakedModel))) {
            matrixStack.pushPose();
            boolean flag = transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.GROUND || transformType == ItemTransforms.TransformType.FIXED;
            if (flag)
                if (stack.is(Items.TRIDENT))
                    bakedModel = this.f_115095_.getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
                else if (stack.is(Items.SPYGLASS))
                    bakedModel = this.f_115095_.getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));

            bakedModel = ForgeHooksClient.handleCameraTransforms(matrixStack, bakedModel, transformType, isLeftHand);
            matrixStack.translate(-0.5D, -0.5D, -0.5D);
            if (stack.getItem() instanceof ICustomItemRender.Item render && render.renderItem(stack)) {
                render.getItemRenderer().renderItem((ItemRenderer) (Object)this, this.f_174223_, stack, transformType, isLeftHand, matrixStack, buffers, this.f_115097_, light, overlay, f_115096_, this.f_115095_, bakedModel);
            } else if (!bakedModel.isCustomRenderer() && (!stack.is(Items.TRIDENT) || flag)) {
                boolean flag1;
                if (transformType != ItemTransforms.TransformType.GUI && !transformType.firstPerson() && stack.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem) stack.getItem()).getBlock();
                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else flag1 = true;
                for (var model : bakedModel.getRenderPasses(stack, flag1))
                    for (var rendertype : model.getRenderTypes(stack, flag1)) {
                        VertexConsumer vertexConsumer;
                        if (stack.is(ItemTags.COMPASSES) && stack.hasFoil()) {
                            matrixStack.pushPose();
                            PoseStack.Pose matrix = matrixStack.last();
                            if (transformType == ItemTransforms.TransformType.GUI)
                                matrix.pose().multiply(0.5F);
                            else if (transformType.firstPerson())
                                matrix.pose().multiply(0.75F);

                            if (flag1)
                                vertexConsumer = getCompassFoilBufferDirect(buffers, rendertype, matrix, stack);
                            else
                                vertexConsumer = getCompassFoilBuffer(buffers, rendertype, matrix, stack);

                            matrixStack.popPose();
                        } else if (flag1)
                            vertexConsumer = getFoilBufferDirect(buffers, rendertype, true, stack);
                        else
                            vertexConsumer = getFoilBuffer(buffers, rendertype, true, stack);

                        if (stack.getItem() instanceof IItemRenderColor itemRenderColor)
                            RenderHelper.renderModelLists(model, stack, itemRenderColor, light, overlay, matrixStack, vertexConsumer);
                        else this.renderModelLists(model, stack, light, overlay, matrixStack, vertexConsumer);
                    }
            } else IClientItemExtensions.of(stack).getCustomRenderer().renderByItem(stack, transformType, matrixStack, buffers, light, overlay);

            matrixStack.popPose();
        }
    }

    private static VertexConsumer getCompassFoilBuffer(MultiBufferSource buffers, RenderType type, PoseStack.Pose matrix, ItemStack stack) {
        return VertexMultiConsumer.create(new SheetedDecalTextureGenerator(buffers.getBuffer(ModRenderType.glint(getFoilResourceLocation(stack))), matrix.pose(), matrix.normal()), buffers.getBuffer(type));
    }

    private static VertexConsumer getCompassFoilBufferDirect(MultiBufferSource buffers, RenderType type, PoseStack.Pose matrix, ItemStack stack) {
        return VertexMultiConsumer.create(new SheetedDecalTextureGenerator(buffers.getBuffer(ModRenderType.glintDirect(getFoilResourceLocation(stack))), matrix.pose(), matrix.normal()), buffers.getBuffer(type));
    }

    private static VertexConsumer getFoilBuffer(MultiBufferSource buffers, RenderType type, boolean noEntity, ItemStack stack) {
        if (stack.hasFoil()) return Minecraft.useShaderTransparency() && type == Sheets.translucentItemSheet() ? VertexMultiConsumer.create(buffers.getBuffer(ModRenderType.glintTranslucent(getFoilResourceLocation(stack))), buffers.getBuffer(type)) : VertexMultiConsumer.create(buffers.getBuffer(noEntity ? ModRenderType.glint(getFoilResourceLocation(stack)) : ModRenderType.entityGlint(getFoilResourceLocation(stack))), buffers.getBuffer(type));
        else return buffers.getBuffer(type);
    }

    private static VertexConsumer getFoilBufferDirect(MultiBufferSource buffers, RenderType type, boolean noEntity, ItemStack stack) {
        return stack.hasFoil() ? VertexMultiConsumer.create(buffers.getBuffer(noEntity ? ModRenderType.glintDirect(getFoilResourceLocation(stack)) : ModRenderType.entityGlintDirect(getFoilResourceLocation(stack))), buffers.getBuffer(type)) : buffers.getBuffer(type);
    }

    private static ResourceLocation getFoilResourceLocation(ItemStack stack) {
        ItemFoilModifyEvent event = new ItemFoilModifyEvent(stack, stack.getItem() instanceof ICustomItemFoil customFoil ? customFoil.getFoilResourceLocation(stack) : ItemRenderer.ENCHANT_GLINT_LOCATION);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getResourceLocation();
    }
}
