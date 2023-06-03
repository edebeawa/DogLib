package edebe.doglib.mixin;

import edebe.doglib.api.event.client.EnchantmentFullnameModifyEvent;
import edebe.doglib.api.helper.TextHelper;
import edebe.doglib.api.helper.TranslationHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Invoker("m_6586_")
    abstract int getMaxLevel();

    @Invoker("m_44704_")
    abstract String getDescriptionId();

    @Invoker("m_6589_")
    abstract boolean isCurse();

    /**
     * @author
     */
    @Overwrite//getFullname
    public Component m_44700_(int level) {
        MutableComponent component = TranslationHelper.getTranslationProcessed(this.getDescriptionId());
        component.withStyle(this.isCurse() ? ChatFormatting.RED : ChatFormatting.GRAY);
        if (level != 1 || this.getMaxLevel() != 1)
            component.append(" ").append(Component.literal(TextHelper.numberToRoman(level)));
        EnchantmentFullnameModifyEvent event = new EnchantmentFullnameModifyEvent(this.getDescriptionId(), level, this.getMaxLevel(), this.isCurse(), component);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getFullname();
    }
}
