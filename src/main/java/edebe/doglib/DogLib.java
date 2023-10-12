package edebe.doglib;

import edebe.doglib.api.run.IDistRun;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DogLib.MODID)
public class DogLib {
    private static final String PROTOCOL_VERSION = "1";

    public static final String MODID = "doglib";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public DogLib() {
        IDistRun.run(DogLibCommon::new);
        IDistRun.run(DogLibClient::new);
    }
}