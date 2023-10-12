package edebe.doglib.api.helper;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public interface PlayerHelper {
    static void addAdvancement(Player player, ResourceLocation advancement, String criterion) {
        if (!(player instanceof ServerPlayer playerMp)) return;
        Advancement adv = playerMp.getLevel().getServer().getAdvancements().getAdvancement(advancement);
        if (adv != null) playerMp.getAdvancements().award(adv, criterion);
    }
}
