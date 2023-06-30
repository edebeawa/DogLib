package edebe.doglib.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import edebe.doglib.api.client.renderer.item.ICustomItemRender;
import edebe.doglib.api.event.client.ItemInHandRenderEvent;
import edebe.doglib.api.helper.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Shadow @Final private Minecraft f_109299_;//minecraft
    @Shadow private ItemStack f_109301_;//offHandItem
    @Shadow @Final private EntityRenderDispatcher f_109306_;//entityRenderDispatcher
    @Shadow @Final private ItemRenderer f_109307_;//itemRenderer

    @Invoker("m_109346_")
    abstract void renderPlayerArm(PoseStack p_109347_, MultiBufferSource p_109348_, int p_109349_, float p_109350_, float p_109351_, HumanoidArm p_109352_);

    @Invoker("m_109353_")
    abstract void renderOneHandedMap(PoseStack p_109354_, MultiBufferSource p_109355_, int p_109356_, float p_109357_, HumanoidArm p_109358_, float p_109359_, ItemStack p_109360_);

    @Invoker("m_109339_")
    abstract void renderTwoHandedMap(PoseStack p_109340_, MultiBufferSource p_109341_, int p_109342_, float p_109343_, float p_109344_, float p_109345_);

    @Invoker("m_109382_")
    abstract void applyItemArmTransform(PoseStack p_109383_, HumanoidArm p_109384_, float p_109385_);

    @Invoker("m_109335_")
    abstract void applyItemArmAttackTransform(PoseStack p_109336_, HumanoidArm p_109337_, float p_109338_);

    @Invoker("m_109330_")
    abstract void applyEatTransform(PoseStack p_109331_, float p_109332_, HumanoidArm p_109333_, ItemStack p_109334_);

    @Invoker("m_109322_")
    abstract void renderItem(LivingEntity p_109323_, ItemStack p_109324_, ItemTransforms.TransformType p_109325_, boolean p_109326_, PoseStack p_109327_, MultiBufferSource p_109328_, int p_109329_);

    /**
     * @author
     */
    @Overwrite//renderArmWithItem
    private void m_109371_(AbstractClientPlayer player, float partialTick, float pitch, InteractionHand hand, float swingProcess, ItemStack stack, float equipProcess, PoseStack matrixStack, MultiBufferSource buffers, int light) {
        if (!player.isScoping()) if (!MinecraftForge.EVENT_BUS.post(new ItemInHandRenderEvent(this.f_109307_, this.f_109299_, player, this.f_109306_, partialTick, pitch, hand, hand == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite(), swingProcess, stack, equipProcess, matrixStack, buffers, light))) {
            boolean flag = hand == InteractionHand.MAIN_HAND;
            HumanoidArm arm = flag ? player.getMainArm() : player.getMainArm().getOpposite();
            matrixStack.pushPose();
            if (stack.isEmpty()) {
                if (flag && !player.isInvisible())
                    this.renderPlayerArm(matrixStack, buffers, light, equipProcess, swingProcess, arm);
            } else if (stack.is(Items.FILLED_MAP)) {
                if (flag && this.f_109301_.isEmpty())
                    this.renderTwoHandedMap(matrixStack, buffers, light, pitch, equipProcess, swingProcess);
                else
                    this.renderOneHandedMap(matrixStack, buffers, light, equipProcess, arm, swingProcess, stack);
            } else if (stack.getItem() instanceof CrossbowItem) {
                    boolean flag1 = CrossbowItem.isCharged(stack);
                    int i = isRightArm(arm) ? 1 : -1;
                    if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == hand) {
                        this.applyItemArmTransform(matrixStack, arm, equipProcess);
                        matrixStack.translate((float) i * -0.4785682F, -0.094387F, 0.05731531F);
                        matrixStack.mulPose(Vector3f.XP.rotationDegrees(-11.935F));
                        matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) i * 65.3F));
                        matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) i * -9.785F));
                        float f9 = (float) stack.getUseDuration() - ((float) this.f_109299_.player.getUseItemRemainingTicks() - partialTick + 1.0F);
                        float f13 = f9 / (float) CrossbowItem.getChargeDuration(stack);
                        if (f13 > 1.0F) f13 = 1.0F;
                        if (f13 > 0.1F) {
                            float f16 = Mth.sin((f9 - 0.1F) * 1.3F);
                            float f3 = f13 - 0.1F;
                            float f4 = f16 * f3;
                            matrixStack.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                        }

                        matrixStack.translate(f13 * 0.0F, f13 * 0.0F, f13 * 0.04F);
                        matrixStack.scale(1.0F, 1.0F, 1.0F + f13 * 0.2F);
                        matrixStack.mulPose(Vector3f.YN.rotationDegrees((float) i * 45.0F));
                    } else {
                        float f = -0.4F * Mth.sin(Mth.sqrt(swingProcess) * (float) Math.PI);
                        float f1 = 0.2F * Mth.sin(Mth.sqrt(swingProcess) * ((float) Math.PI * 2F));
                        float f2 = -0.2F * Mth.sin(swingProcess * (float) Math.PI);
                        matrixStack.translate((float) i * f, f1, f2);
                        this.applyItemArmTransform(matrixStack, arm, equipProcess);
                        this.applyItemArmAttackTransform(matrixStack, arm, swingProcess);
                        if (flag1 && swingProcess < 0.001F && flag) {
                            matrixStack.translate((float) i * -0.641864F, 0.0D, 0.0D);
                            matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) i * 10.0F));
                        }
                    }

                    this.renderItem(player, stack, isRightArm(arm) ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !isRightArm(arm), matrixStack, buffers, light);
                } else if (stack.getItem() instanceof ICustomItemRender render && ReflectionHelper.hasMethod(render.getItemStackRenderer().getClass(), "renderHand", ItemRenderer.class, Minecraft.class, AbstractClientPlayer.class, EntityRenderDispatcher.class, float.class, float.class, InteractionHand.class, HumanoidArm.class, float.class, ItemStack.class, float.class, PoseStack.class, MultiBufferSource.class, int.class)) {
                    render.getItemStackRenderer().renderHand(this.f_109307_, this.f_109299_, player, this.f_109306_, partialTick, pitch, hand, arm, swingProcess, stack, equipProcess, matrixStack, buffers, light);
                } else {
                    if (!IClientItemExtensions.of(stack).applyForgeHandTransform(matrixStack, f_109299_.player, arm, stack, partialTick, equipProcess, swingProcess))
                        if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == hand) {
                            int k = isRightArm(arm) ? 1 : -1;
                            switch (stack.getUseAnimation()) {
                                case NONE, BLOCK -> this.applyItemArmTransform(matrixStack, arm, equipProcess);
                                case EAT, DRINK -> {
                                    this.applyEatTransform(matrixStack, partialTick, arm, stack);
                                    this.applyItemArmTransform(matrixStack, arm, equipProcess);
                                }
                                case BOW -> {
                                    this.applyItemArmTransform(matrixStack, arm, equipProcess);
                                    matrixStack.translate((float) k * -0.2785682F, 0.18344387F, 0.15731531F);
                                    matrixStack.mulPose(Vector3f.XP.rotationDegrees(-13.935F));
                                    matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) k * 35.3F));
                                    matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) k * -9.785F));
                                    float f8 = (float) stack.getUseDuration() - ((float) this.f_109299_.player.getUseItemRemainingTicks() - partialTick + 1.0F);
                                    float f12 = f8 / 20.0F;
                                    f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
                                    if (f12 > 1.0F) {
                                        f12 = 1.0F;
                                    }
                                    if (f12 > 0.1F) {
                                        float f15 = Mth.sin((f8 - 0.1F) * 1.3F);
                                        float f18 = f12 - 0.1F;
                                        float f20 = f15 * f18;
                                        matrixStack.translate(f20 * 0.0F, f20 * 0.004F, f20 * 0.0F);
                                    }
                                    matrixStack.translate(f12 * 0.0F, f12 * 0.0F, f12 * 0.04F);
                                    matrixStack.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
                                    matrixStack.mulPose(Vector3f.YN.rotationDegrees((float) k * 45.0F));
                                }
                                case SPEAR -> {
                                    this.applyItemArmTransform(matrixStack, arm, equipProcess);
                                    matrixStack.translate((float) k * -0.5F, 0.7F, 0.1F);
                                    matrixStack.mulPose(Vector3f.XP.rotationDegrees(-55.0F));
                                    matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) k * 35.3F));
                                    matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) k * -9.785F));
                                    float f7 = (float) stack.getUseDuration() - ((float) this.f_109299_.player.getUseItemRemainingTicks() - partialTick + 1.0F);
                                    float f11 = f7 / 10.0F;
                                    if (f11 > 1.0F) {
                                        f11 = 1.0F;
                                    }
                                    if (f11 > 0.1F) {
                                        float f14 = Mth.sin((f7 - 0.1F) * 1.3F);
                                        float f17 = f11 - 0.1F;
                                        float f19 = f14 * f17;
                                        matrixStack.translate(f19 * 0.0F, f19 * 0.004F, f19 * 0.0F);
                                    }
                                    matrixStack.translate(0.0D, 0.0D, f11 * 0.2F);
                                    matrixStack.scale(1.0F, 1.0F, 1.0F + f11 * 0.2F);
                                    matrixStack.mulPose(Vector3f.YN.rotationDegrees((float) k * 45.0F));
                                }
                            }
                        } else if (player.isAutoSpinAttack()) {
                            this.applyItemArmTransform(matrixStack, arm, equipProcess);
                            int j = isRightArm(arm) ? 1 : -1;
                            matrixStack.translate((float) j * -0.4F, 0.8F, 0.3F);
                            matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) j * 65.0F));
                            matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) j * -85.0F));
                        } else {
                            float f5 = -0.4F * Mth.sin(Mth.sqrt(swingProcess) * (float) Math.PI);
                            float f6 = 0.2F * Mth.sin(Mth.sqrt(swingProcess) * ((float) Math.PI * 2F));
                            float f10 = -0.2F * Mth.sin(swingProcess * (float) Math.PI);
                            int l = isRightArm(arm) ? 1 : -1;
                            matrixStack.translate((float) l * f5, f6, f10);
                            this.applyItemArmTransform(matrixStack, arm, equipProcess);
                            this.applyItemArmAttackTransform(matrixStack, arm, swingProcess);
                        }

                    this.renderItem(player, stack, isRightArm(arm) ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !isRightArm(arm), matrixStack, buffers, light);
                }

            matrixStack.popPose();
        }
    }

    private static boolean isRightArm(HumanoidArm arm) {
        return arm == HumanoidArm.RIGHT;
    }
}
