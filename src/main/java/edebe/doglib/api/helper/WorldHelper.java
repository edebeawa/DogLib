package edebe.doglib.api.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface WorldHelper {
    static void updateLight(Level world, BlockPos pos) {
        world.getProfiler().push("queueCheckLight");
        world.getChunkSource().getLightEngine().checkBlock(pos);
        world.getProfiler().pop();
    }
}
