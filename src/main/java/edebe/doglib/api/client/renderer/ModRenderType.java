package edebe.doglib.api.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import edebe.doglib.DogLib;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ModRenderType extends RenderType {
    private ModRenderType(String name, VertexFormat format, VertexFormat.Mode glMode, int size, boolean doCrumbling, boolean depthSorting, Runnable onEnable, Runnable onDisable) {
        super(name, format, glMode, size, doCrumbling, depthSorting, onEnable, onDisable);
        throw new IllegalStateException("This class must not be instantiated");
    }

    private static final RenderType FILL_COLOR = RenderType.create(getModRenderTypeName("fill_color"), DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder()
            .setShaderState(POSITION_COLOR_SHADER)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .createCompositeState(true)
    );

    public static RenderType fillColor() {
        return FILL_COLOR;
    }

    public static RenderType glintTranslucent(ResourceLocation resourceLocation) {
        return RenderType.create(getModRenderTypeName("glint_translucent"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_GLINT_TRANSLUCENT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, true, false))
                .setWriteMaskState(COLOR_WRITE)
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setTransparencyState(GLINT_TRANSPARENCY)
                .setTexturingState(GLINT_TEXTURING)
                .setOutputState(ITEM_ENTITY_TARGET)
                .createCompositeState(false));
    }

    public static RenderType glint(ResourceLocation resourceLocation) {
        return RenderType.create(getModRenderTypeName("glint"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_GLINT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, true, false))
                .setWriteMaskState(COLOR_WRITE)
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setTransparencyState(GLINT_TRANSPARENCY)
                .setTexturingState(GLINT_TEXTURING)
                .createCompositeState(false)
        );
    }

    public static RenderType glintDirect(ResourceLocation resourceLocation) {
        return RenderType.create(getModRenderTypeName("glint_direct"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_GLINT_DIRECT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, true, false))
                .setWriteMaskState(COLOR_WRITE)
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setTransparencyState(GLINT_TRANSPARENCY)
                .setTexturingState(GLINT_TEXTURING)
                .createCompositeState(false)
        );
    }

    public static RenderType entityGlint(ResourceLocation resourceLocation) {
        return RenderType.create(getModRenderTypeName("entity_glint"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, true, false))
                .setWriteMaskState(COLOR_WRITE)
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setTransparencyState(GLINT_TRANSPARENCY)
                .setOutputState(ITEM_ENTITY_TARGET)
                .setTexturingState(ENTITY_GLINT_TEXTURING)
                .createCompositeState(false)
        );
    }

    public static RenderType entityGlintDirect(ResourceLocation resourceLocation) {
        return RenderType.create(getModRenderTypeName("entity_glint_direct"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, true, false))
                .setWriteMaskState(COLOR_WRITE)
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setTransparencyState(GLINT_TRANSPARENCY)
                .setTexturingState(ENTITY_GLINT_TEXTURING)
                .createCompositeState(false)
        );
    }

    private static String getModRenderTypeName(String name) {
        return DogLib.MODID + ":" + name;
    }
}
