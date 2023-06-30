package edebe.doglib.api.helper;

import net.minecraftforge.items.IItemHandler;

public interface ContainerHelper {
    static boolean isItemContainerEmpty(IItemHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) if (!handler.getStackInSlot(i).isEmpty()) return false;
        return true;
    }
}