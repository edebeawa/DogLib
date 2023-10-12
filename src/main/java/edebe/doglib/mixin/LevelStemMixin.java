package edebe.doglib.mixin;

import net.minecraft.core.Registry;
import net.minecraft.world.level.dimension.LevelStem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LevelStem.class)
public class LevelStemMixin {
    /**
     * @author edebe
     * @reason
     */
    @Overwrite
    public static boolean stable(Registry<LevelStem> registry) {
        return true;
    }
}
