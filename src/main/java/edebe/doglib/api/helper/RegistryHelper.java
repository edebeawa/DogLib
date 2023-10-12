package edebe.doglib.api.helper;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public interface RegistryHelper {
    static <T> T getValueOrElse(IForgeRegistry<T> registry, ResourceLocation location, T other) {
        T value = registry.getValue(location);
        if (value != null) {
            return value;
        } else {
            return other;
        }
    }
}
