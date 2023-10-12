package edebe.doglib.api.world.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IModBiomeColor {
    ColorResolver getColorResolver();

    default int getAverageColor(BlockAndTintGetter getter, BlockPos pos) {
        return this.getAverageColorOrElse(getter, pos, -1);
    }

    default int getAverageColorOrElse(BlockAndTintGetter getter, BlockPos pos, int other) {
        return getter != null && pos != null ? getter.getBlockTint(pos, this.getColorResolver()) : other;
    }
}
