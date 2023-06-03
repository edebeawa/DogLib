package edebe.doglib.api.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import edebe.doglib.DogLib;
import edebe.doglib.api.client.renderer.CubeRenderInfo;
import edebe.doglib.api.client.renderer.IVertexInfo;
import edebe.doglib.api.client.renderer.ModRenderType;
import edebe.doglib.api.client.renderer.DrawInfo;
import edebe.doglib.api.client.renderer.item.ICustomItemFoil;
import edebe.doglib.api.client.renderer.item.IItemRenderColor;
import edebe.doglib.api.event.client.ItemFoilModifyEvent;
import edebe.doglib.api.client.renderer.fluid.IFluidRenderColor;
import edebe.doglib.api.world.block.block_entity.SimpleFluidTankBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class RenderHelper {
    public static final ResourceLocation SOLID_COLOR_FILL = new ResourceLocation(DogLib.MODID, "textures/misc/solid_color_fill.png");
    public static final int MAX_LIGHT = 15728880;

    public void renderSingleBlockModel(BlockState blockState, PoseStack.Pose matrix, VertexConsumer vertex, Color color, int light, int overlay, ModelData modelData, RenderType renderType, @Nullable BakedModel bakedModel) {
        RandomSource randomSource = RandomSource.create();
        long seed = 42L;

        for(Direction direction : Direction.values()) {
            randomSource.setSeed(seed);
            renderQuadList(matrix, vertex, bakedModel.getQuads(blockState, direction, randomSource, modelData, renderType), color, light, overlay);
        }

        randomSource.setSeed(seed);
        renderQuadList(matrix, vertex, bakedModel.getQuads(blockState, null, randomSource, modelData, renderType), color, light, overlay);
    }

    public static void renderQuadList(PoseStack.Pose matrix, VertexConsumer vertex, List<BakedQuad> quads, Color color, int light, int overlay) {
        for(BakedQuad quad : quads) {
            float[] rgba = new float[]{1, 1, 1, 1};
            if (quad.isTinted())
                rgba = new float[]{color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f};
            vertex.putBulkData(matrix, quad, new float[]{1.0F, 1.0F, 1.0F, 1.0F}, rgba[0], rgba[1], rgba[2], rgba[3], new int[]{light, light, light, light}, overlay, false);
        }
    }

    public static void renderBaseItemModel(ItemRenderer renderer, ItemStack itemStack, PoseStack matrixStack, MultiBufferSource bufferIn, int light, int overlay, BakedModel bakedModel) {
        for (BakedModel model : bakedModel.getRenderPasses(itemStack, true)) {
            for (RenderType renderType : model.getRenderTypes(itemStack, true)) {
                VertexConsumer vertex = getFoilBufferDirect(bufferIn, renderType, true, itemStack);
                renderer.renderModelLists(model, itemStack, light, overlay, matrixStack, vertex);
            }
        }
    }

    public static void renderItemModel(IVertexInfo vertexInfo, ItemStack itemStack, PoseStack matrixStack, MultiBufferSource bufferIn, IItemRenderColor itemColor, int light, int overlay, BakedModel bakedModel) {
        for (BakedModel model : bakedModel.getRenderPasses(itemStack, true)) {
            for (RenderType renderType : model.getRenderTypes(itemStack, true)) {
                VertexConsumer vertex = getFoilBufferDirect(bufferIn, renderType, true, itemStack);
                renderModelLists(model, itemStack, itemColor, light, overlay, matrixStack, vertexInfo.info(vertex));
            }
        }
    }

    private static VertexConsumer getFoilBufferDirect(MultiBufferSource buffers, RenderType type, boolean noEntity, ItemStack stack) {
        ItemFoilModifyEvent event = new ItemFoilModifyEvent(stack, stack.getItem() instanceof ICustomItemFoil customFoil ? customFoil.getFoilResourceLocation(stack) : ItemRenderer.ENCHANT_GLINT_LOCATION);
        MinecraftForge.EVENT_BUS.post(event);
        return stack.hasFoil() ? VertexMultiConsumer.create(buffers.getBuffer(noEntity ? ModRenderType.glintDirect(event.getResourceLocation()) : ModRenderType.entityGlintDirect(event.getResourceLocation())), buffers.getBuffer(type)) : buffers.getBuffer(type);
    }

    public static void renderModelLists(BakedModel bakedModel, ItemStack itemStack, IItemRenderColor itemColor, int light, int overlay, PoseStack matrixStack, VertexConsumer renderer) {
        RandomSource randomSource = RandomSource.create();
        long seed = 42L;

        for(Direction direction : Direction.values()) {
            randomSource.setSeed(seed);
            renderQuadList(matrixStack, renderer, bakedModel.getQuads(null, direction, randomSource), itemStack, itemColor, light, overlay);
        }

        randomSource.setSeed(seed);
        renderQuadList(matrixStack, renderer, bakedModel.getQuads(null, null, randomSource), itemStack, itemColor, light, overlay);
    }

    public static void renderQuadList(PoseStack matrixStack, VertexConsumer renderer, List<BakedQuad> quads, ItemStack itemStack, IItemRenderColor itemColor, int light, int overlay) {
        for(BakedQuad quad : quads) {
            int color = -1;
            if (!itemStack.isEmpty() && quad.isTinted())
                color = itemColor.getColor(itemStack, quad.getTintIndex());
            int[] rgba = MathHelper.parseToColor(color);
            renderer.putBulkData(matrixStack.last(), quad, rgba[0] / 255f, rgba[1] / 255f, rgba[2] / 255f, rgba[3] / 255f, light, overlay, true);
        }
    }

    public static void renderFluidTankFluid(@Nonnull SimpleFluidTankBlockEntity fluidTank, PoseStack matrixStack, MultiBufferSource bufferIn, int light, CubeRenderInfo... renders) {
        renderFluidTankFluid(fluidTank, matrixStack, bufferIn, (blockEntity, poseStack, fluidStack, buffers, info) -> IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack), light, renders);
    }

    public static void renderFluidTankFluid(@Nonnull SimpleFluidTankBlockEntity fluidTank, PoseStack matrixStack, MultiBufferSource bufferIn, IFluidRenderColor fluidColor, int light, CubeRenderInfo... renders) {
        if (fluidTank.isRemoved() || fluidTank.isEmpty()) return;
        FluidStack stack = fluidTank.getFluid();
        renderFluid(stack, matrixStack, bufferIn, 16, 16, ColorHelper.parseToColor(fluidColor.getColor(fluidTank, matrixStack, stack, bufferIn, renders)), light, renders);
    }

    public static void renderFluid(FluidStack stack, PoseStack matrixStack, MultiBufferSource bufferIn, double width, double height, Color color, int light, CubeRenderInfo... renders) {
        renderFluid(stack, matrixStack, bufferIn, width, height, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f, light, renders);
    }

    public static void renderFluid(FluidStack stack, PoseStack matrixStack, MultiBufferSource bufferIn, double width, double height, float r, float g, float b, float a, int light, CubeRenderInfo... renders) {
        for (CubeRenderInfo render : renders)
            renderPlane(render, getFluidTexture(stack, isFlowing(render)), matrixStack, bufferIn, 0, 0, getFluidTextureSize(width, isFlowing(render)), getFluidTextureSize(height, isFlowing(render)), r, g, b, a, light);
    }

    private static ResourceLocation getFluidTexture(FluidStack stack, boolean flowing) {
        IClientFluidTypeExtensions type = IClientFluidTypeExtensions.of(stack.getFluid());
        return flowing ? type.getFlowingTexture(stack) : type.getStillTexture(stack);
    }

    private static double getFluidTextureSize(double value, boolean flowing) {
        if (flowing)
            return value / 2D;
        else
            return value;
    }

    private static boolean isFlowing(CubeRenderInfo render) {
        return !(render.getDirection().equals(Direction.UP) || render.getDirection().equals(Direction.DOWN));
    }

    public static void renderCube(PoseStack matrixStack, MultiBufferSource bufferIn, Vector3f scale, Color color) {
        renderCube(matrixStack, bufferIn, scale, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
    }

    public static void renderCube(PoseStack matrixStack, MultiBufferSource bufferIn, Vector3f scale, float r, float g, float b, float a) {
        for (CubeRenderInfo render : CubeRenderInfo.create())
            renderPlane(render, matrixStack, bufferIn, scale, r, g, b, a);
    }

    public static void renderCube(ResourceLocation texture, PoseStack matrixStack, MultiBufferSource bufferIn, double width, double height, Color color, int light) {
        renderCube(texture, matrixStack, bufferIn, width, height, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f, light);
    }

    public static void renderCube(ResourceLocation texture, PoseStack matrixStack, MultiBufferSource bufferIn, double width, double height, float r, float g, float b, float a, int light) {
        for (CubeRenderInfo render : CubeRenderInfo.create())
            renderPlane(render, texture, matrixStack, bufferIn, 0, 0, width, height, r, g, b, a, light);
    }

    public static void renderPlane(CubeRenderInfo render, PoseStack matrixStack, MultiBufferSource bufferIn, Vector3f scale, float r, float g, float b, float a) {
        VertexConsumer builder = bufferIn.getBuffer(ModRenderType.fillColor());
        render.render(builder, matrixStack.last().pose(), scale,
                vertex -> vertex.color(r, g, b, a),
                vertex -> vertex.color(r, g, b, a),
                vertex -> vertex.color(r, g, b, a),
                vertex -> vertex.color(r, g, b, a)
        );
    }

    public static void renderPlane(CubeRenderInfo render, ResourceLocation texture, PoseStack matrixStack, MultiBufferSource bufferIn, double u, double v, double w, double h, float r, float g, float b, float a, int light) {
        renderPlane(render, Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture), matrixStack, bufferIn, u, v, w, h, r, g, b, a, light);
    }

    public static void renderPlane(CubeRenderInfo render, TextureAtlasSprite sprite, PoseStack matrixStack, MultiBufferSource bufferIn, double u, double v, double w, double h, float r, float g, float b, float a, int light) {
        if (sprite == null) return;
        VertexConsumer builder = bufferIn.getBuffer(RenderType.text(sprite.atlas().location()));
        float u1 = sprite.getU(u);
        float v1 = sprite.getV(v);
        float u2 = sprite.getU(w);
        float v2 = sprite.getV(h);
        int bl = light & 0xFFFF;
        int sl = (light >> 16) & 0xFFFF;
        int lightmap = bl | (sl << 16);
        render.render(builder, matrixStack.last().pose(),
                new Vector3f(1, 1, 1),
                vertex -> vertex.color(r, g, b, a).uv(u1, v1).uv2(lightmap),
                vertex -> vertex.color(r, g, b, a).uv(u1, v2).uv2(lightmap),
                vertex -> vertex.color(r, g, b, a).uv(u2, v2).uv2(lightmap),
                vertex -> vertex.color(r, g, b, a).uv(u2, v1).uv2(lightmap)
        );
    }

    public static void renderBar(int xOffset, int yOffset, float blitOffset, int width, int height, Color color) {
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.disableBlend();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        drawQuad(bufferBuilder, xOffset, yOffset, blitOffset, width, height, color);
        RenderSystem.enableBlend();
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
    }

    public static void drawQuad(BufferBuilder renderer, double x, double y, double z, double width, double height, Color color) {
        DrawInfo[] info = DrawInfo.create(
                new Vector3d(x, y, z),
                new Vector3d(x, y + height, z),
                new Vector3d(x + width, y + height, z),
                new Vector3d(x + width, y, z)
        );
        draw(renderer, GameRenderer::getPositionColorShader, VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f, info);
    }

    public static void draw(BufferBuilder renderer, Supplier<ShaderInstance> instance, VertexFormat.Mode glMode, VertexFormat format, float r, float g, float b, float a, DrawInfo... vertexes) {
        RenderSystem.setShader(instance);
        renderer.begin(glMode, format);
        for (DrawInfo draw : vertexes)
            draw.addVertex(
                    renderer, vertex -> vertex.color(r, g, b, a)
            );
        BufferUploader.drawWithShader(renderer.end());
    }

    public static int getItemBarFillRatio(float fillRatio) {
        return Math.round(fillRatio * 13F);
    }
}