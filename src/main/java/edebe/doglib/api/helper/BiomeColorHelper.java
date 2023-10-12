package edebe.doglib.api.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;

public interface BiomeColorHelper {
    static int getAverageColor(BlockAndTintGetter getter, BlockPos pos, ColorResolver resolver) {
        return getAverageColorOrElse(getter, pos, resolver, -1);
    }

    static int getAverageColorOrElse(BlockAndTintGetter getter, BlockPos pos, ColorResolver resolver, int other) {
        return getter != null && pos != null ? getter.getBlockTint(pos, resolver) : other;
    }
}
