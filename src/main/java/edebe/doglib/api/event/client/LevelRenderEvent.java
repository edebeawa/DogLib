package edebe.doglib.api.event.client;

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
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class LevelRenderEvent extends Event {
    public enum Type {
        WEATHER, SKY, CLOUD
    }

    private final Type type;
    private final LevelRenderer renderer;
    private final Minecraft minecraft;
    private final ClientLevel level;

    public LevelRenderEvent(Type type, LevelRenderer renderer, Minecraft minecraft, ClientLevel level) {
        this.type = type;
        this.renderer = renderer;
        this.minecraft = minecraft;
        this.level = level;
    }

    public Type getType() {
        return type;
    }

    public LevelRenderer getRenderer() {
        return renderer;
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public ClientLevel getLevel() {
        return level;
    }

    @Cancelable
    public static class WeatherRenderEvent extends LevelRenderEvent {
        private final int ticks;
        private final float partialTick;
        private final LightTexture lightTexture;
        private final float[] rainSizeX;
        private final float[] rainSizeZ;
        private final double camX;
        private final double camY;
        private final double camZ;

        public WeatherRenderEvent(LevelRenderer renderer, Minecraft minecraft, ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, float[] rainSizeX, float[] rainSizeZ, double camX, double camY, double camZ) {
            super(Type.WEATHER, renderer, minecraft, level);
            this.ticks = ticks;
            this.partialTick = partialTick;
            this.lightTexture = lightTexture;
            this.rainSizeX = rainSizeX;
            this.rainSizeZ = rainSizeZ;
            this.camX = camX;
            this.camY = camY;
            this.camZ = camZ;
        }

        public int getTicks() {
            return ticks;
        }

        public float getPartialTick() {
            return partialTick;
        }

        public LightTexture getLightTexture() {
            return lightTexture;
        }

        public float[] getRainSizeX() {
            return rainSizeX;
        }

        public float[] getRainSizeZ() {
            return rainSizeZ;
        }

        public double getCamX() {
            return camX;
        }

        public double getCamY() {
            return camY;
        }

        public double getCamZ() {
            return camZ;
        }
    }

    @Cancelable
    public static class SkyRenderEvent extends LevelRenderEvent {
        private final int ticks;
        private final float partialTick;
        private final GameRenderer gameRenderer;
        private final VertexBuffer skyBuffer;
        private final VertexBuffer starBuffer;
        private final VertexBuffer darkBuffer;
        private final PoseStack poseStack;
        private final Camera camera;
        private final Matrix4f projectionMatrix;
        private final boolean isFoggy;
        private final Runnable setupFog;

        public SkyRenderEvent(LevelRenderer renderer, Minecraft minecraft, ClientLevel level, int ticks, float partialTick, GameRenderer gameRenderer, VertexBuffer skyBuffer, VertexBuffer starBuffer, VertexBuffer darkBuffer, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
            super(Type.SKY, renderer, minecraft, level);
            this.ticks = ticks;
            this.partialTick = partialTick;
            this.gameRenderer = gameRenderer;
            this.skyBuffer = skyBuffer;
            this.starBuffer = starBuffer;
            this.darkBuffer = darkBuffer;
            this.poseStack = poseStack;
            this.camera = camera;
            this.projectionMatrix = projectionMatrix;
            this.isFoggy = isFoggy;
            this.setupFog = setupFog;
        }

        public int getTicks() {
            return ticks;
        }

        public float getPartialTick() {
            return partialTick;
        }

        public GameRenderer getGameRenderer() {
            return gameRenderer;
        }

        public VertexBuffer getSkyBuffer() {
            return skyBuffer;
        }

        public VertexBuffer getStarBuffer() {
            return starBuffer;
        }

        public VertexBuffer getDarkBuffer() {
            return darkBuffer;
        }

        public PoseStack getPoseStack() {
            return poseStack;
        }

        public Camera getCamera() {
            return camera;
        }

        public Matrix4f getProjectionMatrix() {
            return projectionMatrix;
        }

        public boolean isFoggy() {
            return isFoggy;
        }

        public Runnable getSetupFog() {
            return setupFog;
        }
    }

    @Cancelable
    public static class CloudRenderEvent extends LevelRenderEvent {
        private final int ticks;
        private final float partialTick;
        private final VertexBuffer cloudBuffer;
        private final PoseStack poseStack;
        private final Matrix4f projectionMatrix;
        private final double camX;
        private final double camY;
        private final double camZ;
        private final int prevCloudX;
        private final int prevCloudY;
        private final int prevCloudZ;
        private final Vec3 prevCloudColor;
        private final CloudStatus prevCloudsType;
        private final boolean generateClouds;

        public CloudRenderEvent(LevelRenderer renderer, Minecraft minecraft, ClientLevel level, int ticks, float partialTick, VertexBuffer cloudBuffer, PoseStack poseStack, Matrix4f projectionMatrix, double camX, double camY, double camZ, int prevCloudX, int prevCloudY, int prevCloudZ, Vec3 prevCloudColor, CloudStatus prevCloudsType, boolean generateClouds) {
            super(Type.CLOUD, renderer, minecraft, level);
            this.ticks = ticks;
            this.partialTick = partialTick;
            this.cloudBuffer = cloudBuffer;
            this.poseStack = poseStack;
            this.projectionMatrix = projectionMatrix;
            this.camX = camX;
            this.camY = camY;
            this.camZ = camZ;
            this.prevCloudX = prevCloudX;
            this.prevCloudY = prevCloudY;
            this.prevCloudZ = prevCloudZ;
            this.prevCloudColor = prevCloudColor;
            this.prevCloudsType = prevCloudsType;
            this.generateClouds = generateClouds;
        }

        public int getTicks() {
            return ticks;
        }

        public float getPartialTick() {
            return partialTick;
        }

        public VertexBuffer getCloudBuffer() {
            return cloudBuffer;
        }

        public PoseStack getPoseStack() {
            return poseStack;
        }

        public Matrix4f getProjectionMatrix() {
            return projectionMatrix;
        }

        public double getCamX() {
            return camX;
        }

        public double getCamY() {
            return camY;
        }

        public double getCamZ() {
            return camZ;
        }

        public int getPrevCloudX() {
            return prevCloudX;
        }

        public int getPrevCloudY() {
            return prevCloudY;
        }

        public int getPrevCloudZ() {
            return prevCloudZ;
        }

        public Vec3 getPrevCloudColor() {
            return prevCloudColor;
        }

        public CloudStatus getPrevCloudsType() {
            return prevCloudsType;
        }

        public boolean isGenerateClouds() {
            return generateClouds;
        }
    }
}
