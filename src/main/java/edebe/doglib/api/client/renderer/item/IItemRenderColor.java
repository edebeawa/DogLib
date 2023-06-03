package edebe.doglib.api.client.renderer.item;

import net.minecraft.world.item.ItemStack;

public interface IItemRenderColor {
    int getColor(ItemStack stack, int layer);
}
