package edebe.doglib.api.world.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import edebe.doglib.api.minecraft.Azimuth;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate.*;
import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public abstract class AbstractAzimuthBiomeSource extends BiomeSource {
    protected final ParameterList<Holder<Biome>> north;
    protected final ParameterList<Holder<Biome>> south;
    protected final ParameterList<Holder<Biome>> west;
    protected final ParameterList<Holder<Biome>> east;
    protected final ParameterList<Holder<Biome>> none;
    protected final int range;

    protected AbstractAzimuthBiomeSource(ImmutableList<Pair<ParameterPoint, Holder<Biome>>> north, ImmutableList<Pair<ParameterPoint, Holder<Biome>>> south, ImmutableList<Pair<ParameterPoint, Holder<Biome>>> west, ImmutableList<Pair<ParameterPoint, Holder<Biome>>> east, ImmutableList<Pair<ParameterPoint, Holder<Biome>>> none, int range) {
        this(new ParameterList<>(north), new ParameterList<>(south), new ParameterList<>(west), new ParameterList<>(east), new ParameterList<>(none), range);
    }

    protected AbstractAzimuthBiomeSource(ParameterList<Holder<Biome>> north, ParameterList<Holder<Biome>> south, ParameterList<Holder<Biome>> west, ParameterList<Holder<Biome>> east, ParameterList<Holder<Biome>> none, int range) {
        super(build(north, south, west, east, none));
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
        this.none = none;
        this.range = range;
    }

    @Override
    protected abstract Codec<? extends BiomeSource> codec();

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, @NotNull Sampler sampler) {
        int i = QuartPos.toBlock(x);
        int k = QuartPos.toBlock(z);
        int xA = SectionPos.blockToSectionCoord(i);
        int zA = SectionPos.blockToSectionCoord(k);
        Azimuth azimuth = Azimuth.getHorizontalAzimuth(xA, zA);

        ParameterList<Holder<Biome>> list;
        if (azimuth == Azimuth.NORTHEAST || azimuth == Azimuth.NORTH) {
            list = this.north;
        } else if (azimuth == Azimuth.SOUTHWEST || azimuth == Azimuth.SOUTH) {
            list = this.south;
        } else if (azimuth == Azimuth.NORTHWEST || azimuth == Azimuth.WEST) {
            list = this.west;
        } else if (azimuth == Azimuth.SOUTHEAST || azimuth == Azimuth.EAST) {
            list = this.east;
        } else {
            list = this.none;
        }
        return list.findValue(sampler.sample(x, y, z));
    }

    private static ImmutableList<Holder<Biome>> build(ParameterList<Holder<Biome>> north, ParameterList<Holder<Biome>> south, ParameterList<Holder<Biome>> west, ParameterList<Holder<Biome>> east, ParameterList<Holder<Biome>> none) {
        ImmutableList.Builder<Holder<Biome>> builder = new ImmutableList.Builder<>();
        north.values().stream().map(Pair::getSecond).forEach(builder::add);
        south.values().stream().map(Pair::getSecond).forEach(builder::add);
        west.values().stream().map(Pair::getSecond).forEach(builder::add);
        east.values().stream().map(Pair::getSecond).forEach(builder::add);
        none.values().stream().map(Pair::getSecond).forEach(builder::add);
        return builder.build();
    }
}