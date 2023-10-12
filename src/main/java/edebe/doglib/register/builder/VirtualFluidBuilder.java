package edebe.doglib.register.builder;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

/**
 * For registering fluids with no buckets/blocks
 */
public class VirtualFluidBuilder<T extends ForgeFlowingFluid, P> extends FluidBuilder<T, P> {
    @NotNull
    @ParametersAreNonnullByDefault
    public static <T extends ForgeFlowingFluid, P> VirtualFluidBuilder<T, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture, ResourceLocation flowingTexture, NonNullFunction<Properties, T> factory) {
        return new VirtualFluidBuilder<>(owner, parent, name, callback, stillTexture, flowingTexture, VirtualFluidBuilder::defaultFluidType, factory);
    }

    @NotNull
    @ParametersAreNonnullByDefault
    public static <T extends ForgeFlowingFluid, P> VirtualFluidBuilder<T, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture, ResourceLocation flowingTexture, FluidBuilder.FluidTypeFactory typeFactory, NonNullFunction<Properties, T> factory) {
        return new VirtualFluidBuilder<>(owner, parent, name, callback, stillTexture, flowingTexture, typeFactory, factory);
    }

    public VirtualFluidBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture, ResourceLocation flowingTexture, FluidBuilder.FluidTypeFactory typeFactory, NonNullFunction<Properties, T> factory) {
        super(owner, parent, name, callback, stillTexture, flowingTexture, typeFactory, factory);
        source(factory);
    }

    @NotNull
    @Override
    public NonNullSupplier<T> asSupplier() {
        return this::getEntry;
    }

    @NotNull
    private static FluidType defaultFluidType(FluidType.Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        return new FluidType(properties) {
            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    @Override
                    public ResourceLocation getStillTexture() {
                        return stillTexture;
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return flowingTexture;
                    }
                });
            }
        };
    }
}