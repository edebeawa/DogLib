package edebe.doglib.hook;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import edebe.doglib.api.event.client.*;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class DogLibClientHooks {
    public static boolean onLevelSkyRender(LevelRenderer renderer, Minecraft minecraft, ClientLevel level, int ticks, float partialTick, GameRenderer gameRenderer, VertexBuffer skyBuffer, VertexBuffer starBuffer, VertexBuffer darkBuffer, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return MinecraftForge.EVENT_BUS.post(new LevelRenderEvent.SkyRenderEvent(renderer, minecraft, level, ticks, partialTick, gameRenderer, skyBuffer, starBuffer, darkBuffer, poseStack, camera, projectionMatrix, isFoggy, setupFog));
    }

    public static boolean onLevelCloudRender(LevelRenderer renderer, Minecraft minecraft, ClientLevel level, int ticks, float partialTick, VertexBuffer cloudBuffer, PoseStack poseStack, Matrix4f projectionMatrix, double camX, double camY, double camZ, int prevCloudX, int prevCloudY, int prevCloudZ, Vec3 prevCloudColor, CloudStatus prevCloudsType, boolean generateClouds) {
        return MinecraftForge.EVENT_BUS.post(new LevelRenderEvent.CloudRenderEvent(renderer, minecraft, level, ticks, partialTick, cloudBuffer, poseStack, projectionMatrix, camX, camY, camZ, prevCloudX, prevCloudY, prevCloudZ, prevCloudColor, prevCloudsType, generateClouds));
    }

    public static boolean onLevelWeatherRender(LevelRenderer renderer, Minecraft minecraft, ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, float[] rainSizeX, float[] rainSizeZ, double camX, double camY, double camZ) {
        return MinecraftForge.EVENT_BUS.post(new LevelRenderEvent.WeatherRenderEvent(renderer, minecraft, level, ticks, partialTick, lightTexture, rainSizeX, rainSizeZ, camX, camY, camZ));
    }

    public static MutableComponent modifyFullname(String descriptionId, int level, int maxLevel, boolean isCurse, MutableComponent fullname) {
        EnchantmentFullnameModifyEvent event = new EnchantmentFullnameModifyEvent(descriptionId, level, maxLevel, isCurse, fullname);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getFullname();
    }

    public static boolean onFluidRender(BlockAndTintGetter getter, BlockPos pos, VertexConsumer renderer, BlockState blockState, FluidState fluidState, Color color, int light, TextureAtlasSprite waterOverlay, TextureAtlasSprite[] textureAtlasSprites) {
        return MinecraftForge.EVENT_BUS.post(new FluidRenderEvent(getter, pos, renderer, blockState, fluidState, color, light, waterOverlay, textureAtlasSprites));
    }

    public static boolean onItemInHandRender(ItemRenderer renderer, Minecraft minecraft, AbstractClientPlayer player, EntityRenderDispatcher dispatcher, float partialTick, float pitch, InteractionHand hand, HumanoidArm arm, float swingProcess, ItemStack itemStack, float equipProcess, PoseStack matrixStack, MultiBufferSource bufferSource, int light) {
        return MinecraftForge.EVENT_BUS.post(new ItemInHandRenderEvent(renderer, minecraft, player, dispatcher, partialTick, pitch, hand, arm, swingProcess, itemStack, equipProcess, matrixStack, bufferSource, light));
    }

    public static boolean onItemRender(ItemRenderer renderer, BlockEntityWithoutLevelRenderer getter, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean isLeftHand, PoseStack matrixStack, MultiBufferSource bufferSource, ItemColors itemColors, int light, int overlay, TextureManager textureManager, ItemModelShaper modelShaper, BakedModel bakedModel) {
        return MinecraftForge.EVENT_BUS.post(new ItemRenderEvent(renderer, getter, itemStack, transformType, isLeftHand, matrixStack, bufferSource, itemColors, light, overlay, textureManager, modelShaper, bakedModel));
    }

    public static Set<ModelLayerLocation> addModelLayers(Set<ModelLayerLocation> set) {
        ModelLayersCreationEvent event = new ModelLayersCreationEvent(set);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getModelLayerLocations();
    }
}
