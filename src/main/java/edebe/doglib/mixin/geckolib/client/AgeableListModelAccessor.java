package edebe.doglib.mixin.geckolib.client;

import net.minecraft.client.model.AgeableListModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AgeableListModel.class)
public interface AgeableListModelAccessor {
    @Accessor @Mutable
    boolean isScaleHead();

    @Accessor @Mutable
    float getBabyYHeadOffset();

    @Accessor @Mutable
    float getBabyZHeadOffset();

    @Accessor @Mutable
    float getBabyHeadScale();

    @Accessor @Mutable
    float getBabyBodyScale();
}