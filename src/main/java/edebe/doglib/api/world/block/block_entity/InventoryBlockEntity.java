package edebe.doglib.api.world.block.block_entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class InventoryBlockEntity extends ModBlockEntity implements Clearable {
    private final SimpleContainer itemHandler = createItemHandler();

    protected InventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        itemHandler.addListener(i -> setChanged());
    }

    protected abstract SimpleContainer createItemHandler();

    private static void copyToInv(NonNullList<ItemStack> src, Container dest) {
        Preconditions.checkArgument(src.size() == dest.getContainerSize());
        for (int i = 0; i < src.size(); i++) {
            dest.setItem(i, src.get(i));
        }
    }

    private static NonNullList<ItemStack> copyFromInv(Container inv) {
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ret.set(i, inv.getItem(i));
        }
        return ret;
    }

    @Override
    public void writePacketNBT(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, copyFromInv(itemHandler));
        writeNBT(tag);
    }

    @Override
    public void readPacketNBT(CompoundTag tag) {
        NonNullList<ItemStack> tmp = NonNullList.withSize(inventorySize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, tmp);
        copyToInv(tmp, itemHandler);
        readNBT(tag);
    }

    protected void writeNBT(CompoundTag tag) {}

    protected void readNBT(CompoundTag tag) {}

    public final int inventorySize() {
        return getItemHandler().getContainerSize();
    }

    @Override
    public void clearContent() {
        getItemHandler().clearContent();
    }

    public final Container getItemHandler() {
        return itemHandler;
    }
}