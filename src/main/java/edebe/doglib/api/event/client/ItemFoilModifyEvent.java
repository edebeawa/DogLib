package edebe.doglib.api.event.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class ItemFoilModifyEvent extends Event {
    private final ItemStack stack;
    private ResourceLocation location;

    public ItemFoilModifyEvent(ItemStack stack, ResourceLocation location) {
        this.stack = stack;
        this.location = location;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public ResourceLocation getResourceLocation() {
        return location;
    }

    public void setResourceLocation(ResourceLocation location) {
        this.location = location;
    }
}
