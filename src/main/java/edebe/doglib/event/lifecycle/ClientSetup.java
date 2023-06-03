package edebe.doglib.event.lifecycle;

import edebe.doglib.api.client.renderer.block.IBlockRenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ClientSetup {
    public static void init(FMLClientSetupEvent event) {
        registerBlockRenderTypes();
    }

    @SuppressWarnings("removal")
    private static void registerBlockRenderTypes() {
        for (Block block : ForgeRegistries.BLOCKS.getValues()) if (block instanceof IBlockRenderType blockRenderType)
            ItemBlockRenderTypes.setRenderLayer(block, blockRenderType.getRenderType());
    }
}