package edebe.doglib.api.helper;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.server.ServerLifecycleHooks;

public interface ServerHelper {
    static <T> Registry<T> getRegistry(ResourceKey<? extends Registry<? extends T>> key) {
        return ServerLifecycleHooks.getCurrentServer().registryAccess().registryOrThrow(key);
    }
}