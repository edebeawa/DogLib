package edebe.doglib.api.helper;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public interface ItemHelper {
    static boolean isHoldingItem(Player player, Item item) {
        return isHoldingItem(player, (itemInHand) -> itemInHand.getItem() == item);
    }

    static boolean isHoldingItem(Player player, Function<ItemStack, Boolean> function) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemInHand = player.getItemInHand(hand);
            if (!itemInHand.isEmpty() && function.apply(itemInHand)) return true;
        }
        return false;
    }
}