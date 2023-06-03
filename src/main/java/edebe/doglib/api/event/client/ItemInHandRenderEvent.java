package edebe.doglib.api.event.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ItemInHandRenderEvent extends Event {
    private final Minecraft minecraft;
    private final AbstractClientPlayer player;
    private final EntityRenderDispatcher dispatcher;
    private final float partialTick;
    private final float pitch;
    private final InteractionHand hand;
    private final HumanoidArm arm;
    private final float swingProcess;
    private final ItemRenderer renderer;
    private final ItemStack itemStack;
    private final float equipProcess;
    private final PoseStack matrixStack;
    private final MultiBufferSource bufferSource;
    private final int light;

    public ItemInHandRenderEvent(ItemRenderer renderer, Minecraft minecraft, AbstractClientPlayer player, EntityRenderDispatcher dispatcher, float partialTick, float pitch, InteractionHand hand, HumanoidArm arm, float swingProcess, ItemStack itemStack, float equipProcess, PoseStack matrixStack, MultiBufferSource bufferSource, int light) {
        this.minecraft = minecraft;
        this.player = player;
        this.dispatcher = dispatcher;
        this.partialTick = partialTick;
        this.pitch = pitch;
        this.hand = hand;
        this.arm = arm;
        this.swingProcess = swingProcess;
        this.renderer = renderer;
        this.itemStack = itemStack;
        this.equipProcess = equipProcess;
        this.matrixStack = matrixStack;
        this.bufferSource = bufferSource;
        this.light = light;
    }

    public ItemRenderer getItemRenderer() {
        return renderer;
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public AbstractClientPlayer getPlayer() {
        return player;
    }

    public EntityRenderDispatcher getEntityRenderDispatcher() {
        return dispatcher;
    }

    public float getPartialTick() {
        return partialTick;
    }

    public float getPitch() {
        return pitch;
    }

    public InteractionHand getInteractionHand() {
        return hand;
    }

    public HumanoidArm getHumanoidArm() {
        return arm;
    }

    public float getSwingProcess() {
        return swingProcess;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public float getEquipProcess() {
        return equipProcess;
    }

    public PoseStack getPoseStack() {
        return matrixStack;
    }

    public MultiBufferSource getBufferSource() {
        return bufferSource;
    }

    public int getLight() {
        return light;
    }
}
