package edebe.doglib;

import edebe.doglib.api.run.IDistRun;
import edebe.doglib.event.ForgeClientEvent;
import edebe.doglib.event.ModClientEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;

public class DogLibClient implements IDistRun {
    @Override
    public void run(IEventBus forgeBus, IEventBus modBus) {
        forgeBus.register(new ForgeClientEvent());
        modBus.register(new ModClientEvent());
    }

    @Override
    public Dist getDist() {
        return Dist.CLIENT;
    }
}
