package edebe.doglib.api.event.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.eventbus.api.Event;

import java.awt.*;

public class FluidRenderEvent extends Event {
    private final BlockAndTintGetter getter;
    private final BlockPos pos;
    private final VertexConsumer renderer;
    private final BlockState blockState;
    private final FluidState fluidState;
    private final Color color;
    private final int light;
    private final TextureAtlasSprite waterOverlay;
    private final TextureAtlasSprite[] textureAtlasSprites;

    public FluidRenderEvent(BlockAndTintGetter getter, BlockPos pos, VertexConsumer renderer, BlockState blockState, FluidState fluidState, Color color, int light, TextureAtlasSprite waterOverlay, TextureAtlasSprite[] textureAtlasSprites) {
        this.getter = getter;
        this.pos = pos;
        this.renderer = renderer;
        this.blockState = blockState;
        this.fluidState = fluidState;
        this.color = color;
        this.light = light;
        this.waterOverlay = waterOverlay;
        this.textureAtlasSprites = textureAtlasSprites;
    }

    public BlockAndTintGetter getBlockAndTintGetter() {
        return getter;
    }

    public BlockPos getBlockPos() {
        return pos;
    }

    public VertexConsumer getVertexConsumer() {
        return renderer;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public FluidState getFluidState() {
        return fluidState;
    }

    public Color getColor() {
        return color;
    }

    public int getLight() {
        return light;
    }

    public TextureAtlasSprite getWaterOverlay() {
        return waterOverlay;
    }

    public TextureAtlasSprite[] getTextureAtlasSprites() {
        return textureAtlasSprites;
    }
}
