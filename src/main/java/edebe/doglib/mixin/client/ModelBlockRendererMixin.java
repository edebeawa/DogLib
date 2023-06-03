package edebe.doglib.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import edebe.doglib.api.client.renderer.block.ICustomBlockRender;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ModelBlockRenderer.class)
public abstract class ModelBlockRendererMixin {
    @Shadow @Final private BlockColors f_110995_;

    @Invoker("tesselateWithAO")
    abstract void tesselateWithAO(BlockAndTintGetter p_111079_, BakedModel p_111080_, BlockState p_111081_, BlockPos p_111082_, PoseStack p_111083_, VertexConsumer p_111084_, boolean p_111085_, RandomSource p_111086_, long p_111087_, int p_111088_, ModelData modelData, RenderType renderType);

    @Invoker("tesselateWithoutAO")
    abstract void tesselateWithoutAO(BlockAndTintGetter p_111091_, BakedModel p_111092_, BlockState p_111093_, BlockPos p_111094_, PoseStack p_111095_, VertexConsumer p_111096_, boolean p_111097_, RandomSource p_111098_, long p_111099_, int p_111100_, ModelData modelData, RenderType renderType);

    /**
     * @author
     */
    @Overwrite
    public void tesselateBlock(BlockAndTintGetter getter, BakedModel bakedModel, BlockState state, BlockPos pos, PoseStack matrixStack, VertexConsumer vertex, boolean checkSides, RandomSource randomSource, long seed, int overlay, ModelData modelData, RenderType renderType) {
        if (state.getBlock() instanceof ICustomBlockRender render && render.renderBlock(getter, state, pos)) {
            render.getBlockRenderer().render((ModelBlockRenderer) (Object) this, getter, state, pos, matrixStack, vertex, checkSides, randomSource, seed, this.f_110995_, state.getLightEmission(getter, pos), overlay, modelData, renderType, bakedModel);
        } else {
            boolean flag = Minecraft.useAmbientOcclusion() && state.getLightEmission(getter, pos) == 0 && bakedModel.useAmbientOcclusion(state, renderType);
            Vec3 vec3 = state.getOffset(getter, pos);
            matrixStack.translate(vec3.x, vec3.y, vec3.z);
            modelData = bakedModel.getModelData(getter, pos, state, modelData);

            try {
                if (flag) this.tesselateWithAO(getter, bakedModel, state, pos, matrixStack, vertex, checkSides, randomSource, seed, overlay, modelData, renderType);
                else this.tesselateWithoutAO(getter, bakedModel, state, pos, matrixStack, vertex, checkSides, randomSource, seed, overlay, modelData, renderType);
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.forThrowable(throwable, "Tesselating block model");
                CrashReportCategory crashreportcategory = crashreport.addCategory("Block model being tesselated");
                CrashReportCategory.populateBlockDetails(crashreportcategory, getter, pos, state);
                crashreportcategory.setDetail("Using AO", flag);
                throw new ReportedException(crashreport);
            }
        }
    }
}