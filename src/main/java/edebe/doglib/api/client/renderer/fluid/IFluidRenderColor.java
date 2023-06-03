package edebe.doglib.api.client.renderer.fluid;

import com.mojang.blaze3d.vertex.PoseStack;
import edebe.doglib.api.world.block.block_entity.FluidTankBlockEntity;
import edebe.doglib.api.client.renderer.CubeRenderInfo;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public interface IFluidRenderColor {
    int getColor(@Nonnull FluidTankBlockEntity fluidTank, PoseStack matrixStack, FluidStack fluidStack, MultiBufferSource renderTypeBuffer, CubeRenderInfo[] cubeRenderInfo);
}
