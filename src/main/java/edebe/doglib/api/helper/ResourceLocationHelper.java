package edebe.doglib.api.helper;

import com.tterrag.registrate.builders.Builder;
import net.minecraft.resources.ResourceLocation;

public interface ResourceLocationHelper {
    static ResourceLocation getResource(Builder<?, ?, ?, ?> builder) {
        return new ResourceLocation(builder.getOwner().getModid(), builder.getName());
    }
}
