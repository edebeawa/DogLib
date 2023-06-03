package edebe.doglib.api.client.renderer.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface ICustomItemFoil {
    ResourceLocation getFoilResourceLocation(ItemStack stack);
}
