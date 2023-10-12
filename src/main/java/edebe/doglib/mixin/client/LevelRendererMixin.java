package edebe.doglib.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Matrix4f;
import edebe.doglib.api.client.renderer.level.IDogLibDimensionSpecialEffects;
import edebe.doglib.hook.DogLibClientHooks;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow @Nullable private ClientLevel level;
    @Shadow @Final private Minecraft minecraft;
    @Shadow private int ticks;

    @Shadow @Nullable private VertexBuffer skyBuffer;
    @Shadow @Nullable private VertexBuffer starBuffer;
    @Shadow @Nullable private VertexBuffer darkBuffer;

    @Shadow @Final private float[] rainSizeX;
    @Shadow @Final private float[] rainSizeZ;

    @Shadow private int prevCloudX;
    @Shadow private int prevCloudY;
    @Shadow private int prevCloudZ;
    @Shadow private Vec3 prevCloudColor;
    @Shadow private boolean generateClouds;

    @Shadow @Nullable private CloudStatus prevCloudsType;
    @Shadow @Nullable private VertexBuffer cloudBuffer;

    @Inject(at = @At("HEAD"), method = "renderSnowAndRain", cancellable = true)
    private void renderSnowAndRain(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo info) {
        if (DogLibClientHooks.onLevelWeatherRender((LevelRenderer) (Object) this, this.minecraft, this.level, this.ticks, partialTick, lightTexture, this.rainSizeX, this.rainSizeZ, camX, camY, camZ)) {
            info.cancel();
        }

        if (level.effects() instanceof IDogLibDimensionSpecialEffects dogLibEffects && dogLibEffects.renderWeather((LevelRenderer) (Object) this, this.minecraft, this.level, this.ticks, partialTick, lightTexture, this.rainSizeX, this.rainSizeZ, camX, camY, camZ)) {
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "renderSky", cancellable = true)
    public void renderSky(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable setupFog, CallbackInfo info) {
        if (DogLibClientHooks.onLevelSkyRender((LevelRenderer) (Object) this, this.minecraft, this.level, this.ticks, partialTick, this.minecraft.gameRenderer, this.skyBuffer, this.starBuffer, this.darkBuffer, poseStack, camera, projectionMatrix, isFoggy, setupFog)) {
            info.cancel();
        }

        if (level.effects() instanceof IDogLibDimensionSpecialEffects dogLibEffects && dogLibEffects.renderSky((LevelRenderer) (Object) this, this.minecraft, this.level, this.ticks, partialTick, this.minecraft.gameRenderer, this.skyBuffer, this.starBuffer, this.darkBuffer, poseStack, camera, projectionMatrix, isFoggy, setupFog)) {
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "renderClouds", cancellable = true)
    public void renderClouds(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ, CallbackInfo info) {
        if (DogLibClientHooks.onLevelCloudRender((LevelRenderer) (Object) this, this.minecraft, this.level, this.ticks, partialTick, this.cloudBuffer, poseStack, projectionMatrix, camX, camY, camZ, this.prevCloudX, this.prevCloudY, this.prevCloudZ, this.prevCloudColor, this.prevCloudsType, this.generateClouds)) {
            info.cancel();
        }

        if (level.effects() instanceof IDogLibDimensionSpecialEffects dogLibEffects && dogLibEffects.renderClouds((LevelRenderer) (Object) this, this.minecraft, this.level, this.ticks, partialTick, this.cloudBuffer, poseStack, projectionMatrix, camX, camY, camZ, this.prevCloudX, this.prevCloudY, this.prevCloudZ, this.prevCloudColor, this.prevCloudsType, this.generateClouds)) {
            info.cancel();
        }
    }
}
