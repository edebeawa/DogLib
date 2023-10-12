package edebe.doglib.api.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public interface WorldHelper {
    static void updateLight(Level world, BlockPos pos) {
        world.getProfiler().push("queueCheckLight");
        world.getChunkSource().getLightEngine().checkBlock(pos);
        world.getProfiler().pop();
    }

    static List<ItemFrame> getAttachedItemFrames(Level world, BlockPos pos) {
        List<ItemFrame> frames = world.getEntitiesOfClass(ItemFrame.class, new AABB(pos).inflate(0.25));
        for (int i = frames.size() - 1; i >= 0; i--) {
            ItemFrame frame = frames.get(i);
            BlockPos framePos = frame.getPos().relative(frame.getDirection().getOpposite());
            if (!pos.equals(framePos)) frames.remove(i);
        }
        return frames;
    }
}
