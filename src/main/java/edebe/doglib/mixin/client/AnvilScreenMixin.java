package edebe.doglib.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import edebe.doglib.DogLibConfig;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin extends ItemCombinerScreen<AnvilMenu> {
    private AnvilScreenMixin(AnvilMenu p_98901_, Inventory p_98902_, Component p_98903_, ResourceLocation p_98904_) {
        super(p_98901_, p_98902_, p_98903_, p_98904_);
        throw new IllegalStateException("This class must not be instantiated");
    }

    @Shadow @Final private static Component TOO_EXPENSIVE_TEXT;
    @Shadow @Final private final Player player;

    /**
     * @author edebe
     * @reason
     */
    @Overwrite
    protected void renderLabels(PoseStack p_97890_, int p_97891_, int p_97892_) {
        RenderSystem.disableBlend();
        super.renderLabels(p_97890_, p_97891_, p_97892_);
        int i = this.menu.getCost();
        if (i > 0) {
            int j = 8453920;
            int level = DogLibConfig.TOO_EXPENSIVE_LEVEL.get();
            Component component = null;
            if (level != -1 && i >= level && !this.minecraft.player.getAbilities().instabuild) {
                component = TOO_EXPENSIVE_TEXT;
                j = 16736352;
            } else if (this.menu.getSlot(2).hasItem()) {
                component = Component.translatable("container.repair.cost", i);
                if (!this.menu.getSlot(2).mayPickup(this.player)) {
                    j = 16736352;
                }
            }

            if (component != null) {
                int k = this.imageWidth - 8 - this.font.width(component) - 2;
                int l = 69;
                fill(p_97890_, k - 2, 67, this.imageWidth - 8, 79, 1325400064);
                this.font.drawShadow(p_97890_, component, (float)k, 69.0F, j);
            }
        }
    }
}
