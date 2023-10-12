package edebe.doglib.hook;

import com.mojang.serialization.Codec;
import edebe.doglib.api.world.dimension.IModLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.List;
import java.util.Map;

public class WorldCommonHooks {
    public static void onWorldPresetCreation(Map<ResourceKey<LevelStem>, LevelStem> map) {}

    public static void onWorldPresetCreation(List<IModLevel> list) {}

    public static void onBiomeSourceRegister(Map<ResourceLocation, Codec<? extends BiomeSource>> map) {}

    public static void onBiomeSourceRegister(List<IModLevel> list) {}
}