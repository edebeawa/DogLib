package edebe.doglib.api.helper;

import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;

public interface ChunkHelper {
    static ChunkAccess getLoadedChunk(LevelAccessor level, int x, int z) {
        if (level.getChunkSource() instanceof ServerChunkCache cache)
            return cache.isPositionTicking(ChunkPos.asLong(x, z)) ? cache.getChunk(x, z, ChunkStatus.FULL, false) : null;
        else return level.getChunk(x, z, ChunkStatus.FULL, false);
    }
}
