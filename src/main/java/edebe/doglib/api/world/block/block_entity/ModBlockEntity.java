package edebe.doglib.api.world.block.block_entity;

import edebe.doglib.api.helper.WorldHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
public class ModBlockEntity extends BlockEntity {
    public ModBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        writePacketNBT(tag);
    }

    @Override
    public final CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        writePacketNBT(tag);
        return tag;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void load(CompoundTag tag) {
        super.load(tag);
        readPacketNBT(tag);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void writePacketNBT(CompoundTag tag) {}

    public void readPacketNBT(CompoundTag tag) {}

    public void notifyUpdate() {
        setChanged();
        sendData();
    }

    public void sendData() {
        if (level != null && !level.isClientSide)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2 | 4 | 16);
    }

    public void updateLight() {
        if (level != null)
            WorldHelper.updateLight(level, worldPosition);
    }
}
