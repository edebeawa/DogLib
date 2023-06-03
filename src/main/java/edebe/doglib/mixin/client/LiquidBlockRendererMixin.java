package edebe.doglib.mixin.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import edebe.doglib.api.client.renderer.fluid.ICustomFluidRender;
import edebe.doglib.api.event.client.FluidRenderEvent;
import edebe.doglib.api.helper.ColorHelper;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LiquidBlockRenderer.class)
public abstract class LiquidBlockRendererMixin {
    @Shadow private TextureAtlasSprite f_110942_;

    @Invoker("m_203185_")
    abstract boolean isNeighborSameFluid(FluidState p_203186_, FluidState p_203187_);

    @Invoker("m_203166_")
    abstract boolean shouldRenderFace(BlockAndTintGetter p_203167_, BlockPos p_203168_, FluidState p_203169_, BlockState p_203170_, Direction p_203171_, FluidState p_203172_);

    @Invoker("m_203179_")
    abstract boolean isFaceOccludedByNeighbor(BlockGetter p_203180_, BlockPos p_203181_, Direction p_203182_, float p_203183_, BlockState p_203184_);

    @Invoker("m_203160_")
    abstract float getHeight(BlockAndTintGetter p_203161_, Fluid p_203162_, BlockPos p_203163_, BlockState p_203164_, FluidState p_203165_);

    @Invoker("m_203149_")
    abstract float calculateAverageHeight(BlockAndTintGetter p_203150_, Fluid p_203151_, float p_203152_, float p_203153_, float p_203154_, BlockPos p_203155_);

    @Invoker("m_110945_")
    abstract int getLightColor(BlockAndTintGetter p_110946_, BlockPos p_110947_);

    @Invoker("vertex")
    abstract void vertex(VertexConsumer p_110985_, double p_110986_, double p_110987_, double p_110988_, float p_110989_, float p_110990_, float p_110991_, float alpha, float p_110992_, float p_110993_, int p_110994_);

    /**
     * @author
     */
    @Overwrite//tesselate
    public void m_234369_(BlockAndTintGetter getter, BlockPos pos, VertexConsumer renderer, BlockState blockState, FluidState fluidState) {
        boolean flag = fluidState.is(FluidTags.LAVA);
        TextureAtlasSprite[] textureAtlasSprites = ForgeHooksClient.getFluidSprites(getter, pos, fluidState);
        int color = IClientFluidTypeExtensions.of(fluidState).getTintColor(fluidState, getter, pos);
        if (!MinecraftForge.EVENT_BUS.post(new FluidRenderEvent(getter, pos, renderer, blockState, fluidState, ColorHelper.parseToColor(color), this.getLightColor(getter, pos), this.f_110942_, textureAtlasSprites))) {
            if (fluidState.getType() instanceof ICustomFluidRender render && render.render(fluidState)) {
                render.getRenderer().render(getter, pos, renderer, blockState, fluidState, ColorHelper.parseToColor(color), this.getLightColor(getter, pos), textureAtlasSprites);
            } else {
                float alpha = (float) (color >> 24 & 255) / 255.0F;
                float f = (float) (color >> 16 & 255) / 255.0F;
                float f1 = (float) (color >> 8 & 255) / 255.0F;
                float f2 = (float) (color & 255) / 255.0F;
                BlockState blockstateDown = getter.getBlockState(pos.relative(Direction.DOWN));
                FluidState fluidstateDown = blockstateDown.getFluidState();
                BlockState blockstateUp = getter.getBlockState(pos.relative(Direction.UP));
                FluidState fluidstateUp = blockstateUp.getFluidState();
                BlockState blockstateNorth = getter.getBlockState(pos.relative(Direction.NORTH));
                FluidState fluidstateNorth = blockstateNorth.getFluidState();
                BlockState blockstateSouth = getter.getBlockState(pos.relative(Direction.SOUTH));
                FluidState fluidstateSouth = blockstateSouth.getFluidState();
                BlockState blockstateWest = getter.getBlockState(pos.relative(Direction.WEST));
                FluidState fluidstateWest = blockstateWest.getFluidState();
                BlockState blockstateEast = getter.getBlockState(pos.relative(Direction.EAST));
                FluidState fluidstateEast = blockstateEast.getFluidState();
                boolean flag1 = !isNeighborSameFluid(fluidState, fluidstateUp);
                boolean flag2 = shouldRenderFace(getter, pos, fluidState, blockState, Direction.DOWN, fluidstateDown) && !isFaceOccludedByNeighbor(getter, pos, Direction.DOWN, 0.8888889F, blockstateDown);
                boolean flag3 = shouldRenderFace(getter, pos, fluidState, blockState, Direction.NORTH, fluidstateNorth);
                boolean flag4 = shouldRenderFace(getter, pos, fluidState, blockState, Direction.SOUTH, fluidstateSouth);
                boolean flag5 = shouldRenderFace(getter, pos, fluidState, blockState, Direction.WEST, fluidstateWest);
                boolean flag6 = shouldRenderFace(getter, pos, fluidState, blockState, Direction.EAST, fluidstateEast);
                if (flag1 || flag2 || flag6 || flag5 || flag3 || flag4) {
                    float f3 = getter.getShade(Direction.DOWN, true);
                    float f4 = getter.getShade(Direction.UP, true);
                    float f5 = getter.getShade(Direction.NORTH, true);
                    float f6 = getter.getShade(Direction.WEST, true);
                    Fluid fluid = fluidState.getType();
                    float f11 = this.getHeight(getter, fluid, pos, blockState, fluidState);
                    float f7;
                    float f8;
                    float f9;
                    float f10;
                    if (f11 >= 1.0F) {
                        f7 = 1.0F;
                        f8 = 1.0F;
                        f9 = 1.0F;
                        f10 = 1.0F;
                    } else {
                        float f12 = this.getHeight(getter, fluid, pos.north(), blockstateNorth, fluidstateNorth);
                        float f13 = this.getHeight(getter, fluid, pos.south(), blockstateSouth, fluidstateSouth);
                        float f14 = this.getHeight(getter, fluid, pos.east(), blockstateEast, fluidstateEast);
                        float f15 = this.getHeight(getter, fluid, pos.west(), blockstateWest, fluidstateWest);
                        f7 = this.calculateAverageHeight(getter, fluid, f11, f12, f14, pos.relative(Direction.NORTH).relative(Direction.EAST));
                        f8 = this.calculateAverageHeight(getter, fluid, f11, f12, f15, pos.relative(Direction.NORTH).relative(Direction.WEST));
                        f9 = this.calculateAverageHeight(getter, fluid, f11, f13, f14, pos.relative(Direction.SOUTH).relative(Direction.EAST));
                        f10 = this.calculateAverageHeight(getter, fluid, f11, f13, f15, pos.relative(Direction.SOUTH).relative(Direction.WEST));
                    }

                    double d1 = pos.getX() & 15;
                    double d2 = pos.getY() & 15;
                    double d0 = pos.getZ() & 15;
                    float f16 = 0.001F;
                    float f17 = flag2 ? 0.001F : 0.0F;
                    if (flag1 && !isFaceOccludedByNeighbor(getter, pos, Direction.UP, Math.min(Math.min(f8, f10), Math.min(f9, f7)), blockstateUp)) {
                        f8 -= 0.001F;
                        f10 -= 0.001F;
                        f9 -= 0.001F;
                        f7 -= 0.001F;
                        Vec3 vec3 = fluidState.getFlow(getter, pos);
                        float f18;
                        float f19;
                        float f20;
                        float f21;
                        float f22;
                        float f23;
                        float f24;
                        float f25;
                        if (vec3.x == 0.0D && vec3.z == 0.0D) {
                            TextureAtlasSprite textureatlassprite1 = textureAtlasSprites[0];
                            f18 = textureatlassprite1.getU(0.0D);
                            f22 = textureatlassprite1.getV(0.0D);
                            f19 = f18;
                            f23 = textureatlassprite1.getV(16.0D);
                            f20 = textureatlassprite1.getU(16.0D);
                            f24 = f23;
                            f21 = f20;
                            f25 = f22;
                        } else {
                            TextureAtlasSprite textureatlassprite = textureAtlasSprites[1];
                            float f26 = (float) Mth.atan2(vec3.z, vec3.x) - ((float) Math.PI / 2F);
                            float f27 = Mth.sin(f26) * 0.25F;
                            float f28 = Mth.cos(f26) * 0.25F;
                            float f29 = 8.0F;
                            f18 = textureatlassprite.getU(8.0F + (-f28 - f27) * 16.0F);
                            f22 = textureatlassprite.getV(8.0F + (-f28 + f27) * 16.0F);
                            f19 = textureatlassprite.getU(8.0F + (-f28 + f27) * 16.0F);
                            f23 = textureatlassprite.getV(8.0F + (f28 + f27) * 16.0F);
                            f20 = textureatlassprite.getU(8.0F + (f28 + f27) * 16.0F);
                            f24 = textureatlassprite.getV(8.0F + (f28 - f27) * 16.0F);
                            f21 = textureatlassprite.getU(8.0F + (f28 - f27) * 16.0F);
                            f25 = textureatlassprite.getV(8.0F + (-f28 - f27) * 16.0F);
                        }

                        float f49 = (f18 + f19 + f20 + f21) / 4.0F;
                        float f50 = (f22 + f23 + f24 + f25) / 4.0F;
                        float f51 = (float) textureAtlasSprites[0].getWidth() / (textureAtlasSprites[0].getU1() - textureAtlasSprites[0].getU0());
                        float f52 = (float) textureAtlasSprites[0].getHeight() / (textureAtlasSprites[0].getV1() - textureAtlasSprites[0].getV0());
                        float f53 = 4.0F / Math.max(f52, f51);
                        f18 = Mth.lerp(f53, f18, f49);
                        f19 = Mth.lerp(f53, f19, f49);
                        f20 = Mth.lerp(f53, f20, f49);
                        f21 = Mth.lerp(f53, f21, f49);
                        f22 = Mth.lerp(f53, f22, f50);
                        f23 = Mth.lerp(f53, f23, f50);
                        f24 = Mth.lerp(f53, f24, f50);
                        f25 = Mth.lerp(f53, f25, f50);
                        int j = this.getLightColor(getter, pos);
                        float f30 = f4 * f;
                        float f31 = f4 * f1;
                        float f32 = f4 * f2;

                        this.vertex(renderer, d1 + 0.0D, d2 + (double) f8, d0 + 0.0D, f30, f31, f32, alpha, f18, f22, j);
                        this.vertex(renderer, d1 + 0.0D, d2 + (double) f10, d0 + 1.0D, f30, f31, f32, alpha, f19, f23, j);
                        this.vertex(renderer, d1 + 1.0D, d2 + (double) f9, d0 + 1.0D, f30, f31, f32, alpha, f20, f24, j);
                        this.vertex(renderer, d1 + 1.0D, d2 + (double) f7, d0 + 0.0D, f30, f31, f32, alpha, f21, f25, j);
                        if (fluidState.shouldRenderBackwardUpFace(getter, pos.above())) {
                            this.vertex(renderer, d1 + 0.0D, d2 + (double) f8, d0 + 0.0D, f30, f31, f32, alpha, f18, f22, j);
                            this.vertex(renderer, d1 + 1.0D, d2 + (double) f7, d0 + 0.0D, f30, f31, f32, alpha, f21, f25, j);
                            this.vertex(renderer, d1 + 1.0D, d2 + (double) f9, d0 + 1.0D, f30, f31, f32, alpha, f20, f24, j);
                            this.vertex(renderer, d1 + 0.0D, d2 + (double) f10, d0 + 1.0D, f30, f31, f32, alpha, f19, f23, j);
                        }
                    }

                    if (flag2) {
                        float f40 = textureAtlasSprites[0].getU0();
                        float f41 = textureAtlasSprites[0].getU1();
                        float f42 = textureAtlasSprites[0].getV0();
                        float f43 = textureAtlasSprites[0].getV1();
                        int l = this.getLightColor(getter, pos.below());
                        float f46 = f3 * f;
                        float f47 = f3 * f1;
                        float f48 = f3 * f2;

                        this.vertex(renderer, d1, d2 + (double) f17, d0 + 1.0D, f46, f47, f48, alpha, f40, f43, l);
                        this.vertex(renderer, d1, d2 + (double) f17, d0, f46, f47, f48, alpha, f40, f42, l);
                        this.vertex(renderer, d1 + 1.0D, d2 + (double) f17, d0, f46, f47, f48, alpha, f41, f42, l);
                        this.vertex(renderer, d1 + 1.0D, d2 + (double) f17, d0 + 1.0D, f46, f47, f48, alpha, f41, f43, l);
                    }

                    int k = this.getLightColor(getter, pos);

                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        float f44;
                        float f45;
                        double d3;
                        double d4;
                        double d5;
                        double d6;
                        boolean flag7;
                        switch (direction) {
                            case NORTH -> {
                                f44 = f8;
                                f45 = f7;
                                d3 = d1;
                                d5 = d1 + 1.0D;
                                d4 = d0 + (double) 0.001F;
                                d6 = d0 + (double) 0.001F;
                                flag7 = flag3;
                            }
                            case SOUTH -> {
                                f44 = f9;
                                f45 = f10;
                                d3 = d1 + 1.0D;
                                d5 = d1;
                                d4 = d0 + 1.0D - (double) 0.001F;
                                d6 = d0 + 1.0D - (double) 0.001F;
                                flag7 = flag4;
                            }
                            case WEST -> {
                                f44 = f10;
                                f45 = f8;
                                d3 = d1 + (double) 0.001F;
                                d5 = d1 + (double) 0.001F;
                                d4 = d0 + 1.0D;
                                d6 = d0;
                                flag7 = flag5;
                            }
                            default -> {
                                f44 = f7;
                                f45 = f9;
                                d3 = d1 + 1.0D - (double) 0.001F;
                                d5 = d1 + 1.0D - (double) 0.001F;
                                d4 = d0;
                                d6 = d0 + 1.0D;
                                flag7 = flag6;
                            }
                        }

                        if (flag7 && !isFaceOccludedByNeighbor(getter, pos, direction, Math.max(f44, f45), getter.getBlockState(pos.relative(direction)))) {
                            BlockPos blockpos = pos.relative(direction);
                            TextureAtlasSprite textureatlassprite2 = textureAtlasSprites[1];
                            if (textureAtlasSprites[2] != null) {
                                if (getter.getBlockState(blockpos).shouldDisplayFluidOverlay(getter, blockpos, fluidState))
                                    textureatlassprite2 = textureAtlasSprites[2];
                            }

                            float f54 = textureatlassprite2.getU(0.0D);
                            float f55 = textureatlassprite2.getU(8.0D);
                            float f33 = textureatlassprite2.getV((1.0F - f44) * 16.0F * 0.5F);
                            float f34 = textureatlassprite2.getV((1.0F - f45) * 16.0F * 0.5F);
                            float f35 = textureatlassprite2.getV(8.0D);
                            float f36 = direction.getAxis() == Direction.Axis.Z ? f5 : f6;
                            float f37 = f4 * f36 * f;
                            float f38 = f4 * f36 * f1;
                            float f39 = f4 * f36 * f2;

                            this.vertex(renderer, d3, d2 + (double) f44, d4, f37, f38, f39, alpha, f54, f33, k);
                            this.vertex(renderer, d5, d2 + (double) f45, d6, f37, f38, f39, alpha, f55, f34, k);
                            this.vertex(renderer, d5, d2 + (double) f17, d6, f37, f38, f39, alpha, f55, f35, k);
                            this.vertex(renderer, d3, d2 + (double) f17, d4, f37, f38, f39, alpha, f54, f35, k);
                            if (textureatlassprite2 != this.f_110942_) {
                                this.vertex(renderer, d3, d2 + (double) f17, d4, f37, f38, f39, alpha, f54, f35, k);
                                this.vertex(renderer, d5, d2 + (double) f17, d6, f37, f38, f39, alpha, f55, f35, k);
                                this.vertex(renderer, d5, d2 + (double) f45, d6, f37, f38, f39, alpha, f55, f34, k);
                                this.vertex(renderer, d3, d2 + (double) f44, d4, f37, f38, f39, alpha, f54, f33, k);
                            }
                        }
                    }
                }
            }
        }
    }
}