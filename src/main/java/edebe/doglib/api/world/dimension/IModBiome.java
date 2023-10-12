package edebe.doglib.api.world.dimension;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public interface IModBiome {
    ResourceLocation getResourceLocation();

    ResourceKey<Biome> getBiomeResourceKey();

    Biome getBiome();

    @Deprecated
    Holder<Biome> getBiomeHolder();

    default Holder<Biome> getBiomeHolder(Registry<Biome> registry) {
        return registry.getOrCreateHolderOrThrow(this.getBiomeResourceKey());
    }
}
