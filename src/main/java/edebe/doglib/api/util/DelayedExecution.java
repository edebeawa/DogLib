package edebe.doglib.api.util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DelayedExecution {
    private final Runnable run;

    private IEventBus bus;
    private int delayTick;

    public DelayedExecution(Runnable runnable, int delayTick) {
        this.run = runnable;
        this.delayTick = delayTick;
    }

    public void start() {
        this.start(MinecraftForge.EVENT_BUS);
    }

    public void start(IEventBus bus) {
        this.bus = bus;
        this.bus.register(this);
    }

    public void end() {
        this.run.run();
        this.canceled();
    }

    public void canceled() {
        this.bus.unregister(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (event.phase == Phase.END && this.delayTick-- <= 0) this.end();
    }
}
