package edebe.doglib.api.helper;

import com.tterrag.registrate.util.OneTimeEventReceiver;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.IModBusEvent;

import java.util.function.Consumer;

public interface OneTimeEventReceiverHelper {
    static <T extends Event & IModBusEvent> void addModListener(Dist dist, Class<? super T> evtClass, Consumer<? super T> listener) {
        runWithDist(() -> OneTimeEventReceiver.<T>addModListener(evtClass, listener), dist);
    }

    static <T extends Event & IModBusEvent> void addModListener(Dist dist, EventPriority priority, Class<? super T> evtClass, Consumer<? super T> listener) {
        runWithDist(() -> OneTimeEventReceiver.<T>addModListener(priority, evtClass, listener), dist);
    }

    static <T extends Event> void addForgeListener(Dist dist, Class<? super T> evtClass, Consumer<? super T> listener) {
        runWithDist(() -> OneTimeEventReceiver.<T>addForgeListener(evtClass, listener), dist);
    }

    static <T extends Event> void addForgeListener(Dist dist, EventPriority priority, Class<? super T> evtClass, Consumer<? super T> listener) {
        runWithDist(() -> OneTimeEventReceiver.<T>addForgeListener(priority, evtClass, listener), dist);
    }

    static <T extends Event> void addListener(Dist dist, IEventBus bus, Class<? super T> evtClass, Consumer<? super T> listener) {
        runWithDist(() -> OneTimeEventReceiver.<T>addListener(bus, evtClass, listener), dist);
    }

    static <T extends Event> void addListener(Dist dist, IEventBus bus, EventPriority priority, Class<? super T> evtClass, Consumer<? super T> listener) {
        runWithDist(() -> OneTimeEventReceiver.<T>addListener(bus, priority, evtClass, listener), dist);
    }

    private static void runWithDist(Runnable run, Dist dist) {
        if (dist == null) {
            run.run();
        } else {
            DistExecutor.unsafeRunWhenOn(dist, ()-> run);
        }
    }
}
