package edebe.doglib.mixin;

import com.mojang.serialization.Codec;
import edebe.doglib.api.world.dimension.IModLevel;
import edebe.doglib.hook.WorldCommonHooks;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mixin(BiomeSources.class)
public class BiomeSourcesMixin {
    /**
     * @author edebe
     * @reason
     */
    @Overwrite
    @SuppressWarnings({"RedundantOperationOnEmptyContainer", "MismatchedQueryAndUpdateOfCollection"})
    public static Codec<? extends BiomeSource> bootstrap(Registry<Codec<? extends BiomeSource>> registry) {
        Map<ResourceLocation, Codec<? extends BiomeSource>> map = new LinkedHashMap<>();
        map.put(new ResourceLocation("fixed"), FixedBiomeSource.CODEC);
        map.put(new ResourceLocation("multi_noise"), MultiNoiseBiomeSource.CODEC);
        map.put(new ResourceLocation("checkerboard"), CheckerboardColumnBiomeSource.CODEC);
        map.put(new ResourceLocation("the_end"), TheEndBiomeSource.CODEC);
        WorldCommonHooks.onBiomeSourceRegister(map);
        List<IModLevel> list = new ArrayList<>();
        WorldCommonHooks.onBiomeSourceRegister(list);
        list.forEach((type) -> map.put(type.getResourceLocation(), type.getBiomeSourceCodec()));
        for (ResourceLocation location : map.keySet()) {
            Registry.register(registry, location, map.get(location));
        }

        List<Codec<? extends BiomeSource>> codecs = new ArrayList<>(map.values());
        return codecs.get(codecs.size() - 1);
    }
}
