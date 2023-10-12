package edebe.doglib.event;

import edebe.doglib.api.register.BlockBuilderTransformers;
import edebe.doglib.api.register.ItemBuilderTransformers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ModClientEvent {
    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (ItemBuilderTransformers.hasItemProperty(item)) {
                Object[] property = ItemBuilderTransformers.getItemProperty(item);
                event.enqueueWork(() -> ItemProperties.register(item, ((ResourceLocation) property[0]), ((ItemPropertyFunction) property[1])));
            }

            if (ItemBuilderTransformers.hasCuriosItemRenderer(item)) {
                CuriosRendererRegistry.register(item, () -> ItemBuilderTransformers.getCuriosItemRenderer(item));
            }
        }
    }

    @SubscribeEvent
    public void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            if (BlockBuilderTransformers.hasBlockColor(block)) {
                event.register(BlockBuilderTransformers.getBlockColor(block), block);
            }
        }
    }

    @SubscribeEvent
    public void onRegisterItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (ItemBuilderTransformers.hasItemColor(item)) {
                event.register(ItemBuilderTransformers.getItemColor(item), item);
            }
        }
    }

    @SubscribeEvent
    public void onRegisterItemDecorators(RegisterItemDecorationsEvent event) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (ItemBuilderTransformers.hasItemDecorator(item)) {
                event.register(item, ItemBuilderTransformers.getItemDecorator(item));
            }
        }
    }
}
