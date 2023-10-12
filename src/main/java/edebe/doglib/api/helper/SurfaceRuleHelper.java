package edebe.doglib.api.helper;

import edebe.doglib.api.world.dimension.IModBaseBlock;
import edebe.doglib.api.world.dimension.IModBiome;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public interface SurfaceRuleHelper {
    static RuleSource preliminary(Block groundBlock, Block undergroundBlock, Block underwaterBlock) {
        return preliminary(groundBlock.defaultBlockState(), undergroundBlock.defaultBlockState(), underwaterBlock.defaultBlockState());
    }

    static RuleSource preliminary(IModBaseBlock groundBlock, IModBaseBlock undergroundBlock, IModBaseBlock underwaterBlock) {
        return preliminary(groundBlock.getDefaultBlockState(), undergroundBlock.getDefaultBlockState(), underwaterBlock.getDefaultBlockState());
    }

    static RuleSource preliminary(BlockState groundBlock, BlockState undergroundBlock, BlockState underwaterBlock) {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0),
                                        SurfaceRules.state(groundBlock)), SurfaceRules.state(underwaterBlock)
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
                        SurfaceRules.state(undergroundBlock)
                )
        );
    }

    static RuleSource biome(IModBiome biome, RuleSource preliminary, RuleSource other) {
        return biome(biome.getBiomeResourceKey(), preliminary, other);
    }

    static RuleSource biome(IModBiome biome, RuleSource source) {
        return biome(biome.getBiomeResourceKey(), source);
    }

    static RuleSource biome(ResourceKey<Biome> key, RuleSource preliminary, RuleSource other) {
        return biome(key, SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), preliminary), other));
    }

    static RuleSource biome(ResourceKey<Biome> key, RuleSource source) {
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(key), source);
    }
}
