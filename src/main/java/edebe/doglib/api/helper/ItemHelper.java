package edebe.doglib.api.helper;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

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

    static ItemStack getEquippedItem(Predicate<ItemStack> predicate, Player player) {
        if (ModList.get().isLoaded("curios")) {
            Optional<ItemStack> stack = CuriosApi.getCuriosHelper().findEquippedCurio(predicate, player).map(ImmutableTriple::getRight);
            if (stack.isPresent()) return stack.get();
        }

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (!slot.isEmpty() && predicate.test(slot)) return slot;
        }
        return ItemStack.EMPTY;
    }
}