package edebe.doglib.register.builder;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import edebe.doglib.register.entry.BiomeEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BiomeBuilder<T extends Biome, P> extends AbstractBuilder<Biome, T, P, BiomeBuilder<T, P>> {
    private final NonNullSupplier<T> factory;

    public static <T extends Biome, P> BiomeBuilder<T, P> create(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, NonNullSupplier<T> factory) {
        return new BiomeBuilder<>(owner, parent, name, callback, factory);
    }

    protected BiomeBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, NonNullSupplier<T> factory) {
        super(owner, parent, name, callback, ForgeRegistries.Keys.BIOMES);
        this.factory = factory;
    }

    @Override
    protected T createEntry() {
        return factory.get();
    }

    @Override
    protected RegistryEntry<T> createEntryWrapper(RegistryObject<T> delegate) {
        return new BiomeEntry<>(getOwner(), delegate);
    }

    @Override
    public BiomeEntry<T> register() {
        return (BiomeEntry<T>) super.register();
    }
}
