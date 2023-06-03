package edebe.doglib.api.helper;

import edebe.doglib.api.register.RegisterManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface RegistryHelper {
    static <T> void register(BiConsumer<T, ResourceLocation> registry, RegisterManager<T> manager) {
        manager.getValues().forEach((object) -> registry.accept(object.getObject(), object.getResourceLocation()));
    }

    static <T> void bind(IEventBus modBus, ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        modBus.addListener((RegisterEvent event) -> source.accept((type, resourceLocation) -> event.register(registry, resourceLocation, () -> type)));
    }
}