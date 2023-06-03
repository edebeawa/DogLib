package edebe.doglib.api.client.renderer.item;

import net.minecraft.world.item.ItemStack;

public interface ICustomItemRender {
    interface Item extends ICustomItemRender {
        ItemStackRenderer.Item getItemRenderer();

        boolean renderItem(ItemStack stack);
    }

    interface Hand extends ICustomItemRender {
        ItemStackRenderer.Hand getHandRenderer();

        boolean renderHand(ItemStack stack);
    }
}