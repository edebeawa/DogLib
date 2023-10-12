package edebe.doglib.event;

import edebe.doglib.api.handler.ClientTickHandler;
import edebe.doglib.mixin.client.MinecraftAccessor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeClientEvent {
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !Minecraft.getInstance().isPaused()) {
            ClientTickHandler.addTick();
            ClientTickHandler.setPartialTick(0);
        }
    }

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.START) {
            ClientTickHandler.setPartialTick(event.renderTickTime);
            if (minecraft.isPaused()) {
                ClientTickHandler.setPartialTick(((MinecraftAccessor) minecraft).getPausePartialTick());
            }
        }
    }
}
