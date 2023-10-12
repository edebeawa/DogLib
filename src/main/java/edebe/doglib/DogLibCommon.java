package edebe.doglib;

import edebe.doglib.api.run.IDistRun;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import software.bernie.geckolib3.GeckoLib;

public class DogLibCommon implements IDistRun {
    @Override
    public void run(IEventBus forgeBus, IEventBus modBus) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DogLibConfig.COMMON_CONFIG);
        GeckoLib.initialize();
    }

    @Override
    public Dist getDist() {
        return null;
    }
}
