package edebe.doglib.register.entry;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import edebe.doglib.api.helper.ServerHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.ServerLifecycleHooks;

public class BiomeEntry<T extends Biome> extends RegistryEntry<T> {
    public BiomeEntry(AbstractRegistrate<?> owner, RegistryObject<T> delegate) {
        super(owner, delegate);
    }

    @SuppressWarnings("unchecked")
    public Holder<Biome> asHolder() {
        return ServerHelper.getRegistry(Registry.BIOME_REGISTRY).getOrCreateHolderOrThrow((ResourceKey<Biome>) this.getKey());
    }

    public static <T extends Biome> BiomeEntry<T> cast(RegistryEntry<T> entry) {
        return RegistryEntry.cast(BiomeEntry.class, entry);
    }
}