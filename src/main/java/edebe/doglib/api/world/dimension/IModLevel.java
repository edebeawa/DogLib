package edebe.doglib.api.world.dimension;

import com.mojang.serialization.Codec;
import edebe.doglib.api.helper.LevelHelper;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IModLevel {
    ResourceLocation getResourceLocation();

    ResourceKey<DimensionType> getDimensionTypeResourceKey();

    ResourceKey<NoiseGeneratorSettings> getNoiseGeneratorSettingsResourceKey();

    ResourceKey<LevelStem> getLevelStemResourceKey();

    ResourceKey<Level> getLevelResourceKey();

    DimensionType getDimensionType();

    Holder<DimensionType> getDimensionTypeHolder();

    @OnlyIn(Dist.CLIENT)
    DimensionSpecialEffects getDimensionSpecialEffect();

    NoiseGeneratorSettings getNoiseGeneratorSettings();

    Holder<NoiseGeneratorSettings> getNoiseGeneratorSettingsHolder();

    Codec<? extends BiomeSource> getBiomeSourceCodec();

    LevelStem getLevelStem();

    default void goToThis(ServerPlayer player) {
        LevelHelper.goToLevel(player, this.getServerLevel());
    }

    default void goToThis(ServerPlayer player, Vec3 pos, float yRot, float xRot) {
        this.goToThis(player, pos.x, pos.y, pos.z, yRot, xRot);
    }

    default void goToThis(ServerPlayer player, double x, double y, double z, float yRot, float xRot) {
        LevelHelper.goToLevel(player, this.getServerLevel(), x, y, z, yRot, xRot);
    }

    default ServerLevel getServerLevel() {
        return LevelHelper.getServerLevel(this.getLevelResourceKey());
    }

    default boolean isTypeEquals(Level level) {
        return LevelHelper.isTypeEquals(level, this.getLevelResourceKey());
    }
}
