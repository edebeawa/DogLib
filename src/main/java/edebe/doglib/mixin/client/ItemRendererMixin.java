package edebe.doglib.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import edebe.doglib.api.client.renderer.item.ICustomItemFoil;
import edebe.doglib.api.client.renderer.item.IItemRenderColor;
import edebe.doglib.api.event.client.ItemFoilModifyEvent;
import edebe.doglib.api.helper.ReflectionHelper;
import edebe.doglib.api.helper.RenderHelper;
import edebe.doglib.api.register.ItemBuilderTransformers;
import edebe.doglib.hook.DogLibClientHooks;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow @Final private ItemModelShaper itemModelShaper;
    @Shadow @Final private TextureManager textureManager;
    @Shadow @Final private ItemColors itemColors;
    @Shadow @Final private BlockEntityWithoutLevelRenderer blockEntityRenderer;

    @Shadow
    public abstract void renderModelLists(BakedModel p_115190_, ItemStack p_115191_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_);

    /**
     * @author edebe
     * @reason
     */
    @Overwrite
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, boolean isLeftHand, PoseStack matrixStack, MultiBufferSource bufferSource, int light, int overlay, BakedModel bakedModel) {
        if (!stack.isEmpty()) if (!DogLibClientHooks.onItemRender((ItemRenderer) (Object)this, this.blockEntityRenderer, stack, transformType, isLeftHand, matrixStack, bufferSource, this.itemColors, light, overlay, textureManager, this.itemModelShaper, bakedModel)) {
            matrixStack.pushPose();
            boolean flag = transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.GROUND || transformType == ItemTransforms.TransformType.FIXED;
            if (flag)
                if (stack.is(Items.TRIDENT))
                    bakedModel = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
                else if (stack.is(Items.SPYGLASS))
                    bakedModel = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));

            bakedModel = ForgeHooksClient.handleCameraTransforms(matrixStack, bakedModel, transformType, isLeftHand);
            matrixStack.translate(-0.5D, -0.5D, -0.5D);
            if (ItemBuilderTransformers.hasItemStackRenderer(stack.getItem()) && ReflectionHelper.hasMethod(ItemBuilderTransformers.getItemStackRenderer(stack.getItem()).getClass(), "renderItem", ItemRenderer.class, BlockEntityWithoutLevelRenderer.class, ItemStack.class, ItemTransforms.TransformType.class, boolean.class, PoseStack.class, MultiBufferSource.class, ItemColors.class, int.class, int.class, TextureManager.class, ItemModelShaper.class, BakedModel.class)) {
                ItemBuilderTransformers.getItemStackRenderer(stack.getItem()).renderItem((ItemRenderer) (Object)this, this.blockEntityRenderer, stack, transformType, isLeftHand, matrixStack, bufferSource, this.itemColors, light, overlay, textureManager, this.itemModelShaper, bakedModel);
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

                            if (flag1) vertexConsumer = getCompassFoilBufferDirect(bufferSource, rendertype, matrix, stack);
                            else vertexConsumer = getCompassFoilBuffer(bufferSource, rendertype, matrix, stack);

                            matrixStack.popPose();
                        } else if (flag1) vertexConsumer = getFoilBufferDirect(bufferSource, rendertype, true, stack);
                        else vertexConsumer = getFoilBuffer(bufferSource, rendertype, true, stack);

                        if (stack.getItem() instanceof IItemRenderColor itemRenderColor)
                            RenderHelper.renderModelLists(model, stack, itemRenderColor, light, overlay, matrixStack, vertexConsumer);
                        else this.renderModelLists(model, stack, light, overlay, matrixStack, vertexConsumer);
                    }
            } else IClientItemExtensions.of(stack).getCustomRenderer().renderByItem(stack, transformType, matrixStack, bufferSource, light, overlay);

            matrixStack.popPose();
        }
    }

    private static VertexConsumer getCompassFoilBuffer(MultiBufferSource bufferSource, RenderType type, PoseStack.Pose matrix, ItemStack stack) {
        //return VertexMultiConsumer.create(new SheetedDecalTextureGenerator(bufferSource.getBuffer(DogLibRenderType.glint(getFoilResourceLocation(stack))), matrix.pose(), matrix.normal()), bufferSource.getBuffer(type));
        return ItemRenderer.getCompassFoilBuffer(bufferSource, type, matrix);
    }

    private static VertexConsumer getCompassFoilBufferDirect(MultiBufferSource bufferSource, RenderType type, PoseStack.Pose matrix, ItemStack stack) {
        //return VertexMultiConsumer.create(new SheetedDecalTextureGenerator(bufferSource.getBuffer(DogLibRenderType.glintDirect(getFoilResourceLocation(stack))), matrix.pose(), matrix.normal()), bufferSource.getBuffer(type));
        return ItemRenderer.getCompassFoilBufferDirect(bufferSource, type, matrix);
    }

    private static VertexConsumer getFoilBuffer(MultiBufferSource bufferSource, RenderType type, boolean noEntity, ItemStack stack) {
        //return stack.hasFoil() ? (Minecraft.useShaderTransparency() && type == Sheets.translucentItemSheet() ? VertexMultiConsumer.create(bufferSource.getBuffer(DogLibRenderType.glintTranslucent(getFoilResourceLocation(stack))), bufferSource.getBuffer(type)) : VertexMultiConsumer.create(bufferSource.getBuffer(noEntity ? DogLibRenderType.glint(getFoilResourceLocation(stack)) : DogLibRenderType.entityGlint(getFoilResourceLocation(stack))), bufferSource.getBuffer(type))) : bufferSource.getBuffer(type);
        return ItemRenderer.getFoilBuffer(bufferSource, type, noEntity, stack.hasFoil());
    }

    private static VertexConsumer getFoilBufferDirect(MultiBufferSource bufferSource, RenderType type, boolean noEntity, ItemStack stack) {
        //return stack.hasFoil() ? VertexMultiConsumer.create(bufferSource.getBuffer(noEntity ? DogLibRenderType.glintDirect(getFoilResourceLocation(stack)) : DogLibRenderType.entityGlintDirect(getFoilResourceLocation(stack))), bufferSource.getBuffer(type)) : bufferSource.getBuffer(type);
        return ItemRenderer.getFoilBufferDirect(bufferSource, type, noEntity, stack.hasFoil());
    }

    @SuppressWarnings("removal")
    private static ResourceLocation getFoilResourceLocation(ItemStack stack) {
        ItemFoilModifyEvent event = new ItemFoilModifyEvent(stack, stack.getItem() instanceof ICustomItemFoil customFoil ? customFoil.getFoilResourceLocation(stack) : ItemRenderer.ENCHANT_GLINT_LOCATION);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getResourceLocation();
    }
}