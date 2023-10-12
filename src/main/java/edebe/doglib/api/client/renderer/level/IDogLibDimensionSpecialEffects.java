package edebe.doglib.api.client.renderer.level;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;

public interface IDogLibDimensionSpecialEffects {
    default boolean renderWeather(LevelRenderer renderer, Minecraft minecraft, ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, float[] rainSizeX, float[] rainSizeZ, double camX, double camY, double camZ) {
        return false;
    }

    default boolean renderSky(LevelRenderer renderer, Minecraft minecraft, ClientLevel level, int ticks, float partialTick, GameRenderer gameRenderer, VertexBuffer skyBuffer, VertexBuffer starBuffer, VertexBuffer darkBuffer, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return false;
    }

    default boolean renderClouds(LevelRenderer renderer, Minecraft minecraft, ClientLevel level, int ticks, float partialTick, VertexBuffer cloudBuffer, PoseStack poseStack, Matrix4f projectionMatrix, double camX, double camY, double camZ, int prevCloudX, int prevCloudY, int prevCloudZ, Vec3 prevCloudColor, CloudStatus prevCloudsType, boolean generateClouds) {
        return false;
    }
}