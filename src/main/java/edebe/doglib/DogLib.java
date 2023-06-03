package edebe.doglib;

import edebe.doglib.api.client.renderer.item.ICustomItemFoil;
import edebe.doglib.api.helper.ResourceLocationHelper;
import edebe.doglib.api.world.block.ModBlock;
import edebe.doglib.event.lifecycle.ClientSetup;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;

@Mod(DogLib.MODID)
public class DogLib {
    private static final String PROTOCOL_VERSION = "1";

    public static final String MODID = "doglib";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public DogLib() {
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.register(this);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->modBus.addListener(ClientSetup::init));
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DogLibConfig.COMMON_CONFIG);
        KillerWhale.register(modBus);
    }

    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    private static class KillerWhale {
        private static final DeferredRegister<Block> BLOCK = DeferredRegister.create(Registry.BLOCK_REGISTRY, MODID);
        private static final RegistryObject<Block> KILLER_WHALE_BLOCK = BLOCK.register("killer_whale", KillerWhaleBlock::new);
        private static final DeferredRegister<Item> ITEM = DeferredRegister.create(Registry.ITEM_REGISTRY, MODID);
        private static final RegistryObject<Item> KILLER_WHALE_ITEM = ITEM.register("killer_whale", KillerWhaleItem::new);

        private static void register(IEventBus modBus) {
            BLOCK.register(modBus);
            ITEM.register(modBus);
        }

        private static class KillerWhaleBlock extends ModBlock {
            public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

            public KillerWhaleBlock() {
                super(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(2.5f, Float.MAX_VALUE).noLootTable().noOcclusion());
                this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
            }

            @Override
            public BlockState rotate(BlockState state, Rotation rotation) {
                return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
            }

            @Override
            public BlockState mirror(BlockState state, Mirror mirror) {
                return state.rotate(mirror.getRotation(state.getValue(FACING)));
            }

            @Override
            public BlockState getStateForPlacement(BlockPlaceContext context) {
                return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
            }

            @Override
            protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
                builder.add(FACING);
            }
        }

        private static class KillerWhaleItem extends BlockItem implements ICustomItemFoil {
            public KillerWhaleItem() {
                super(KILLER_WHALE_BLOCK.get(), new Item.Properties().stacksTo(1).setNoRepair());
            }

            @Override
            public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
                return hasSpecificEnchantments(player.getItemInHand(hand)) ? ItemUtils.startUsingInstantly(world, player, hand) : super.use(world, player, hand);
            }

            @Override
            public InteractionResult useOn(UseOnContext context) {
                return hasSpecificEnchantments(context.getItemInHand()) ? InteractionResult.PASS : super.useOn(context);
            }

            @Override
            public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
                if (hasSpecificEnchantments(stack)) {
                    Vec3 pos = new Vec3(entity.getX(), entity.getY() + 1, entity.getZ());
                    AbstractFish fish = new TropicalFish(EntityType.TROPICAL_FISH, world);
                    if (world instanceof ServerLevel server)
                        fish = (AbstractFish) EntityType.TROPICAL_FISH.spawn(server, null, null, new BlockPos(pos.x, pos.y, pos.z), MobSpawnType.SPAWNER, true, false);
                    if (fish != null) {
                        fish.moveTo(pos);

                        float f = 0.4F;
                        float rotationYaw = (-entity.getYRot()) % 360.0F;
                        float rotationPitch = (entity.getXRot() + 180) % 360.0F;

                        double mx = -(Mth.sin(rotationYaw / 180.0F * (float) Math.PI) * Mth.cos(rotationPitch / 180.0F * (float) Math.PI) * f / 2D);
                        double mz = -(Mth.cos(rotationYaw / 180.0F * (float) Math.PI) * Mth.cos(rotationPitch / 180.0F * (float) Math.PI) * f) / 2D;
                        double my = Mth.sin(rotationPitch / 180.0F * (float) Math.PI) * f / 2D;
                        fish.setDeltaMovement(mx * 10, my * 10, mz * 10);

                        world.addFreshEntity(fish);
                    }
                }
            }

            @Override
            public int getUseDuration(ItemStack stack) {
                return Integer.MAX_VALUE;
            }

            @Override
            public UseAnim getUseAnimation(ItemStack stack) {
                return hasSpecificEnchantments(stack) ? UseAnim.BOW : super.getUseAnimation(stack);
            }

            @Override
            public ResourceLocation getFoilResourceLocation(ItemStack stack) {
                return hasSpecificEnchantments(stack) ? ResourceLocationHelper.getResourceLocation(MODID, "textures/misc", "killer_whale_item_glint.png") : ItemRenderer.ENCHANT_GLINT_LOCATION;
            }

            private static boolean hasSpecificEnchantments(ItemStack stack) {
                return stack.getEnchantmentLevel(Enchantments.RESPIRATION) == 3 && stack.getEnchantmentLevel(Enchantments.AQUA_AFFINITY) == 1 && stack.getEnchantmentLevel(Enchantments.DEPTH_STRIDER) == 3;
            }
        }
    }
}