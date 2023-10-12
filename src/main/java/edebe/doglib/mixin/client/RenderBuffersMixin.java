package edebe.doglib.mixin.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import edebe.doglib.api.client.renderer.ModRenderType;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public class RenderBuffersMixin {
    @Inject(method = "put", at = @At("HEAD"))
    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> builders, RenderType type, CallbackInfo info) {
        put(builders, ModRenderType.glint(ItemRenderer.ENCHANT_GLINT_LOCATION));
        put(builders, ModRenderType.glintTranslucent(ItemRenderer.ENCHANT_GLINT_LOCATION));
        put(builders, ModRenderType.entityGlint(ItemRenderer.ENCHANT_GLINT_LOCATION));
        put(builders, ModRenderType.glintDirect(ItemRenderer.ENCHANT_GLINT_LOCATION));
        put(builders, ModRenderType.entityGlintDirect(ItemRenderer.ENCHANT_GLINT_LOCATION));
        put(builders, ModRenderType.armorGlint(ItemRenderer.ENCHANT_GLINT_LOCATION));
        put(builders, ModRenderType.armorEntityGlint(ItemRenderer.ENCHANT_GLINT_LOCATION));
    }

    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> builders, RenderType type) {
        if (!builders.containsKey(type)) builders.put(type, new BufferBuilder(type.bufferSize()));
    }
}
