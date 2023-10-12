package edebe.doglib.api.register;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import edebe.doglib.api.helper.ResourceLocationHelper;
import edebe.doglib.register.RegisteredObjects;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class BlockBuilderTransformers {
    public static final Map<ResourceLocation, BlockColor> COLORS = new HashMap<>();

    public static boolean hasBlockColor(Block block) {
        return COLORS.containsKey(RegisteredObjects.getKeyOrThrow(block));
    }

    public static BlockColor getBlockColor(Block block) {
        return COLORS.get(RegisteredObjects.getKeyOrThrow(block));
    }

    public static void setBlockColor(ResourceLocation location, BlockColor color) {
        COLORS.put(location, color);
    }

    public static <T extends Block, P> NonNullUnaryOperator<BlockBuilder<T, P>> setColor(BlockColor color) {
        return builder -> {
            setBlockColor(ResourceLocationHelper.getResource(builder), color);
            return builder;
        };
    }
}
