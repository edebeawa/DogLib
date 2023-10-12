package edebe.doglib.api.helper;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Collections;
import java.util.List;

public interface BlockHelper {
    static List<ItemStack> defaultDrops(List<ItemStack> dropsOriginal, List<ItemStack> items) {
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;

        return items;
    }

    static List<ItemStack> defaultDrop(List<ItemStack> dropsOriginal, ItemLike item) {
        return defaultDrops(dropsOriginal, Collections.singletonList(new ItemStack(item)));
    }
}
