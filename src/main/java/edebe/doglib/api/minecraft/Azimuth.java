package edebe.doglib.api.minecraft;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.function.BiFunction;

public enum Azimuth {
    NORTHWEST(null, (pos, range) -> pos.getZ() < range && pos.getX() < range),
    NORTHEAST(null, (pos, range) -> pos.getZ() < range && pos.getX() > range),
    SOUTHWEST(null, (pos, range) -> pos.getZ() > range && pos.getX() < range),
    SOUTHEAST(null, (pos, range) -> pos.getZ() > range && pos.getX() > range),
    NORTH(Direction.NORTH, (pos, range) -> pos.getZ() < range),
    SOUTH(Direction.SOUTH, (pos, range) -> pos.getZ() > range),
    WEST(Direction.WEST, (pos, range) -> pos.getX() < range),
    EAST(Direction.EAST, (pos, range) -> pos.getX() > range),
    DOWN(Direction.DOWN, (pos, range) -> pos.getY() < range),
    UP(Direction.UP, (pos, range) -> pos.getY() > range),
    NONE(null, (pos, range) -> false);

    private final Direction direction;
    private final BiFunction<BlockPos, Integer, Boolean> function;

    Azimuth(Direction direction, BiFunction<BlockPos, Integer, Boolean> function) {
        this.direction = direction;
        this.function = function;
    }

    public Direction getDirection() {
        return direction;
    }

    public static Azimuth getHorizontalAzimuth(int x, int z) {
        return getHorizontalAzimuth(x, z, 0);
    }

    public static Azimuth getVerticalAzimuth(int y) {
        return getVerticalAzimuth(y, 0);
    }

    public static Azimuth getAzimuth(int x, int y, int z) {
        return getAzimuth(x, y, z, 0);
    }

    public static Azimuth getAzimuth(BlockPos pos) {
        return getAzimuth(pos, 0);
    }

    public static Azimuth getHorizontalAzimuth(int x, int z, int range) {
        return getAzimuth(new BlockPos(x, 0, z), range);
    }

    public static Azimuth getVerticalAzimuth(int y, int range) {
        return getAzimuth(new BlockPos(0, y, 0), range);
    }

    public static Azimuth getAzimuth(int x, int y, int z, int range) {
        return getAzimuth(new BlockPos(x, y, z), range);
    }

    public static Azimuth getAzimuth(BlockPos pos, int range) {
        for (Azimuth azimuth : Azimuth.values()) {
            if (azimuth.function.apply(pos, Math.max(range, 0))) {
                return azimuth;
            }
        }
        return Azimuth.NONE;
    }
}
