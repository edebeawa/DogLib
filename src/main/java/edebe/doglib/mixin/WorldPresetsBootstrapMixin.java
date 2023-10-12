package edebe.doglib.mixin;

import edebe.doglib.api.world.dimension.IModLevel;
import edebe.doglib.hook.WorldCommonHooks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mixin(WorldPresets.Bootstrap.class)
public class WorldPresetsBootstrapMixin {
    @Shadow @Final private LevelStem netherStem;
    @Shadow @Final private LevelStem endStem;

    /**
     * @author edebe
     * @reason
     */
    @Overwrite
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "RedundantOperationOnEmptyContainer"})
    private WorldPreset createPresetWithCustomOverworld(LevelStem levelStem) {
        Map<ResourceKey<LevelStem>, LevelStem> map = new LinkedHashMap<>();
        map.put(LevelStem.OVERWORLD, levelStem);
        map.put(LevelStem.NETHER, this.netherStem);
        map.put(LevelStem.END, this.endStem);
        WorldCommonHooks.onWorldPresetCreation(map);
        List<IModLevel> list = new ArrayList<>();
        WorldCommonHooks.onWorldPresetCreation(list);
        list.forEach((type) -> map.put(type.getLevelStemResourceKey(), type.getLevelStem()));
        return new WorldPreset(map);
    }
}
