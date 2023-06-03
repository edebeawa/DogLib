package edebe.doglib.api.util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DelayedExecution {
    Runnable runnable;
    int delayTick;
    IEventBus bus;

    public DelayedExecution(IEventBus bus, Runnable runnable, int delayTick) {
        this.runnable = runnable;
        this.delayTick = delayTick;
        this.bus = bus;
        bus.register(this);
    }

    public DelayedExecution(Runnable runnable, int delayTick) {
        this(MinecraftForge.EVENT_BUS, runnable, delayTick);
    }

    @SubscribeEvent
    public void tick(TickEvent event) {
        if (this.delayTick-- <= 0) {
            this.runnable.run();
            this.bus.unregister(this);
        }
    }
}
