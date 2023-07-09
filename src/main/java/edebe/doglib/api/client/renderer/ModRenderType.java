package edebe.doglib.api.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import edebe.doglib.DogLib;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;

public class ModRenderType extends RenderType {
    private ModRenderType(String name, VertexFormat format, VertexFormat.Mode glMode, int size, boolean doCrumbling, boolean depthSorting, Runnable onEnable, Runnable onDisable) {
        super(name, format, glMode, size, doCrumbling, depthSorting, onEnable, onDisable);
        throw new IllegalStateException("This class must not be instantiated");
    }

    private static String getModRenderTypeName(String name) {
        return DogLib.MODID + "_" + name;
    }

    public static RenderType fillColor() {
        return RenderType.create(getModRenderTypeName("fill_color"), DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder()
                .setShaderState(POSITION_COLOR_SHADER)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .createCompositeState(true)
        );
    }

    public static RenderType glint(ResourceLocation locationIn) {
        return RenderType.create(getModRenderTypeName("glint"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_GLINT_SHADER)
                .setTextureState(new TextureStateShard(locationIn, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                .createCompositeState(false)
        );
    }

    public static RenderType glintTranslucent(ResourceLocation locationIn) {
        return RenderType.create(getModRenderTypeName("glint_translucent"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_GLINT_TRANSLUCENT_SHADER)
                .setTextureState(new TextureStateShard(locationIn, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                .createCompositeState(false)
        );
    }

    public static RenderType entityGlint(ResourceLocation locationIn) {
        return RenderType.create(getModRenderTypeName("entity_glint"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_GLINT_SHADER)
                .setTextureState(new TextureStateShard(locationIn, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                .createCompositeState(false)
        );
    }

    public static RenderType glintDirect(ResourceLocation locationIn) {
        return RenderType.create(getModRenderTypeName("glint_direct"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_GLINT_DIRECT_SHADER)
                .setTextureState(new TextureStateShard(locationIn, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                .createCompositeState(false)
        );
    }

    public static RenderType entityGlintDirect(ResourceLocation locationIn) {
        return RenderType.create(getModRenderTypeName("entity_glint_direct"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER)
                .setTextureState(new TextureStateShard(locationIn, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                .createCompositeState(false)
        );
    }

    public static RenderType armorGlint(ResourceLocation locationIn) {
        return RenderType.create(getModRenderTypeName("armor_glint"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ARMOR_GLINT_SHADER)
                .setTextureState(new TextureStateShard(locationIn, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                .createCompositeState(false)
        );
    }

    public static RenderType armorEntityGlint(ResourceLocation locationIn) {
        return RenderType.create(getModRenderTypeName("armor_entity_glint"), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER)
                .setTextureState(new TextureStateShard(locationIn, true, false))
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setCullState(RenderStateShard.NO_CULL)
                .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                .setTexturingState(RenderStateShard.ENTITY_GLINT_TEXTURING)
                .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                .createCompositeState(false)
        );
    }
}
