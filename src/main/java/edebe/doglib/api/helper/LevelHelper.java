package edebe.doglib.api.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;

public interface LevelHelper {
    static void goToLevel(ServerPlayer player, ServerLevel level) {
        goToLevel(player, level, player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
    }

    static void goToLevel(ServerPlayer player, ServerLevel level, double x, double y, double z, float yRot, float xRot) {
        player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0));
        player.teleportTo(level, x, y, z, yRot, xRot);
        player.connection.send(new ClientboundPlayerAbilitiesPacket(player.getAbilities()));
        for (MobEffectInstance instance : player.getActiveEffects())
            player.connection.send(new ClientboundUpdateMobEffectPacket(player.getId(), instance));
        player.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
    }

    static ServerLevel getServerLevel(ResourceKey<Level> key) {
        return ServerLifecycleHooks.getCurrentServer().getLevel(key);
    }

    static boolean isTypeEquals(Level level, ResourceKey<Level> key) {
        return level.dimension().equals(key);
    }
}