package edebe.doglib.api.world.dimension;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public interface IModCarver {
    ResourceLocation getResourceLocation();

    WorldCarver<CarverConfiguration> getWorldCarver();

    Holder<ConfiguredWorldCarver<? extends CarverConfiguration>> getConfiguredWorldCarverHolder();
}
