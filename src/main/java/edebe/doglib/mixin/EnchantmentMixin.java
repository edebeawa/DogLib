package edebe.doglib.mixin;

import edebe.doglib.api.helper.TextHelper;
import edebe.doglib.api.helper.TranslationHelper;
import edebe.doglib.hook.DogLibClientHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Shadow
    public abstract int getMaxLevel();

    @Shadow
    public abstract String getDescriptionId();

    @Shadow
    public abstract boolean isCurse();

    /**
     * @author edebe
     * @reason
     */
    @Overwrite
    public Component getFullname(int level) {
        MutableComponent component = TranslationHelper.getTranslationProcessed(this.getDescriptionId());
        component.withStyle(this.isCurse() ? ChatFormatting.RED : ChatFormatting.GRAY);
        if (level != 1 || this.getMaxLevel() != 1)
            component.append(" ").append(Component.literal(TextHelper.numberToRoman(level)));
        return DogLibClientHooks.modifyFullname(this.getDescriptionId(), level, this.getMaxLevel(), this.isCurse(), component);
    }
}
