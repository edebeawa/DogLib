package edebe.doglib.api.helper;

import net.minecraft.resources.ResourceLocation;

public interface ResourceLocationHelper {
    static ResourceLocation getResourceLocation(String namespace, String path, String name) {
        return new ResourceLocation(namespace, path + "/" + name);
    }
}
