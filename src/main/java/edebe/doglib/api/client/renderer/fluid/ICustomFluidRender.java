package edebe.doglib.api.client.renderer.fluid;

import net.minecraft.world.level.material.FluidState;

public interface ICustomFluidRender {
    FluidRenderer getRenderer();
    boolean render(FluidState state);
}
