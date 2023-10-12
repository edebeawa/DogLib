package edebe.doglib.api.registry;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.BlockEntityBuilder.BlockEntityFactory;
import com.tterrag.registrate.builders.EntityBuilder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import edebe.doglib.register.builder.BiomeBuilder;
import edebe.doglib.register.builder.VirtualFluidBuilder;
import edebe.doglib.world.fluid.VirtualFluid;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DogLibRegistrate extends AbstractRegistrate<DogLibRegistrate> {
    protected DogLibRegistrate(String modid) {
        super(modid);
    }

    public static DogLibRegistrate create(String modid) {
        return new DogLibRegistrate(modid);
    }

    @Override
    public DogLibRegistrate registerEventListeners(IEventBus bus) {
        return super.registerEventListeners(bus);
    }

    public <T extends BlockEntity> BlockEntityBuilder<T, DogLibRegistrate> tileEntity(String name, BlockEntityFactory<T> factory) {
        return this.tileEntity(this.self(), name, factory);
    }

    public <T extends BlockEntity, P> BlockEntityBuilder<T, P> tileEntity(P parent, String name, BlockEntityFactory<T> factory) {
        return this.entry(name, (callback) -> BlockEntityBuilder.create(this, parent, name, callback, factory));
    }

    @Override
    public <T extends Entity> EntityBuilder<T, DogLibRegistrate> entity(String name, EntityType.EntityFactory<T> factory, MobCategory classification) {
        return this.entity(self(), name, factory, classification);
    }

    @Override
    public <T extends Entity, P> EntityBuilder<T, P> entity(P parent, String name, EntityType.EntityFactory<T> factory, MobCategory classification) {
        return this.entry(name, (callback) -> EntityBuilder.create(this, parent, name, callback, factory, classification));
    }

    public <T extends ForgeFlowingFluid> FluidBuilder<T, DogLibRegistrate> virtualFluid(String name, FluidBuilder.FluidTypeFactory typeFactory, NonNullFunction<ForgeFlowingFluid.Properties, T> factory) {
        return entry(name, callback -> VirtualFluidBuilder.create(self(), self(), name, callback, this.asResource("fluid/" + name + "_still"), this.asResource("fluid/" + name + "_flow"), typeFactory, factory));
    }

    public <T extends ForgeFlowingFluid> FluidBuilder<T, DogLibRegistrate> virtualFluid(String name, ResourceLocation still, ResourceLocation flow, FluidBuilder.FluidTypeFactory typeFactory, NonNullFunction<ForgeFlowingFluid.Properties, T> factory) {
        return entry(name, callback -> VirtualFluidBuilder.create(self(), self(), name, callback, still, flow, typeFactory, factory));
    }

    public FluidBuilder<VirtualFluid, DogLibRegistrate> virtualFluid(String name) {
        return entry(name, callback -> VirtualFluidBuilder.create(self(), self(), name, callback, this.asResource("fluid/" + name + "_still"), this.asResource("fluid/" + name + "_flow"), VirtualFluid::new));
    }

    public FluidBuilder<VirtualFluid, DogLibRegistrate> virtualFluid(String name, ResourceLocation still, ResourceLocation flow) {
        return entry(name, callback -> VirtualFluidBuilder.create(self(), self(), name, callback, still, flow, VirtualFluid::new));
    }

    public FluidBuilder<ForgeFlowingFluid.Flowing, DogLibRegistrate> standardFluid(String name) {
        return fluid(name, this.asResource("fluid/" + name + "_still"), this.asResource("fluid/" + name + "_flow"));
    }

    public FluidBuilder<ForgeFlowingFluid.Flowing, DogLibRegistrate> standardFluid(String name, FluidBuilder.FluidTypeFactory typeFactory) {
        return fluid(name, this.asResource("fluid/" + name + "_still"), this.asResource("fluid/" + name + "_flow"), typeFactory);
    }

    public <T extends Biome> BiomeBuilder<T, DogLibRegistrate> biome(String name, NonNullSupplier<T> factory) {
        return this.biome(this.self(), name, factory);
    }

    public <T extends Biome, P> BiomeBuilder<T, P> biome(P parent, String name, NonNullSupplier<T> factory) {
        return this.entry(name, (callback) -> BiomeBuilder.create(this, parent, name, callback, factory));
    }

    private ResourceLocation asResource(String path) {
        return new ResourceLocation(this.getModid(), path);
    }
}