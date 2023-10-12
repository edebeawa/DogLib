package edebe.doglib.mixin.client;

import edebe.doglib.hook.DogLibClientHooks;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;
import java.util.stream.Stream;

@Mixin(ModelLayers.class)
public class ModelLayersMixin {
    @Shadow @Final private static Set<ModelLayerLocation> ALL_MODELS;

    /**
     * @author edebe
     * @reason
     */
    @Overwrite
    public static Stream<ModelLayerLocation> getKnownLocations() {
        return DogLibClientHooks.addModelLayers(ALL_MODELS).stream();
    }
}
