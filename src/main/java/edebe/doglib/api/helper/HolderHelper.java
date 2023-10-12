package edebe.doglib.api.helper;

import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface HolderHelper {
    static <T> Holder<T> getOrCreateHolderAndBindOrThrow(Registry<T> registry, ResourceKey<T> key, T value) {
        Reference<T> reference = (Reference<T>) registry.getOrCreateHolderOrThrow(key);
        reference.bind(key, value);
        return reference;
    }
}
