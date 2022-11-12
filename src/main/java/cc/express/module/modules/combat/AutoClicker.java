package cc.express.module.modules.combat;


import cc.express.event.EventTarget;
import cc.express.event.world.EventTick;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.values.Numbers;
import cc.express.module.values.Option;
import cc.express.utils.client.ReflectionUtil;
import cc.express.utils.client.TimeUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("all")
public class AutoClicker extends Module {
    private final Option left = new Option("Left Clicker",  true);
    private final Option right = new Option("Right Clicker",  false);
    private final Numbers maxCps = new Numbers("Left MaxCPS",  10.0, 1.0, 20.0, 1.0);
    private final Numbers minCps = new Numbers("Left MinCPS", 10.0, 1.0, 20.0, 1.0);
    private final Numbers RmaxCps = new Numbers("Right MaxCPS", 14.0, 1.0, 20.0, 1.0);
    private final Numbers RminCps = new Numbers("Right MinCPS", 10.0, 1.0, 20.0, 1.0) ;
    private final Numbers jitter = new Numbers("Jitter", 0.0, 0.0, 3.0, 0.1);
    private final Option blockHit = new Option("BlockHit", false);
    private final Option autoUnBlock = new Option("AutoUnblock", false);
    private final Option weaponOnly = new Option("Weapons Only", false);
    private final Random random = new Random();
    private final TimeUtil timeUtils = new TimeUtil();

    public AutoClicker() {
        super("AutoClicker", Category.Combat);
    }

    @EventTarget
    public void Tick(EventTick event) {
        if (minCps.getValue() >= maxCps.getValue()) {
            maxCps.setValue(minCps.getValue());
        }
        if (maxCps.getValue() <= minCps.getValue()) {
            minCps.setValue(maxCps.getValue());
        }
        if (RminCps.getValue() >= RmaxCps.getValue()) {
            RmaxCps.setValue(RminCps.getValue());
        }
        if (RmaxCps.getValue() <= RminCps.getValue()) {
            RminCps.setValue(RmaxCps.getValue());
        }
    }
    public static void clickMouse() {
        int leftClickCounter = (int) ReflectionUtil.getFieldValue(Minecraft.getMinecraft(), "leftClickCounter", "field_71429_W");
        if (leftClickCounter <= 0) {
            Minecraft.getMinecraft().thePlayer.swingItem();
            if (Minecraft.getMinecraft().objectMouseOver == null) {
                if (Minecraft.getMinecraft().playerController.isNotCreative()) {
                    ReflectionUtil.setFieldValue(Minecraft.getMinecraft(), 10, "leftClickCounter", "field_71429_W");
                }
            } else {
                switch (Minecraft.getMinecraft().objectMouseOver.typeOfHit) {
                    case ENTITY:
                        try {
                            Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().objectMouseOver.entityHit);
                        } catch (NullPointerException exception) {
                            exception.printStackTrace();
                        }
                        break;

                    case BLOCK:
                        BlockPos blockpos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();

                        try {
                            if (Minecraft.getMinecraft().theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
                                Minecraft.getMinecraft().playerController.clickBlock(blockpos, Minecraft.getMinecraft().objectMouseOver.sideHit);
                                break;
                            }
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }

                    case MISS:
                    default:
                        if (Minecraft.getMinecraft().playerController.isNotCreative()) {
                            ReflectionUtil.setFieldValue(Minecraft.getMinecraft(), 10, "leftClickCounter", "field_71429_W");
                        }
                }
            }
        }
    }

    @EventTarget
    public void onTick(EventTick e) {
        setSuffix(String.format("%s - %s", minCps.getValue(), maxCps.getValue()));
        if (!state)
            return;
        if (mc.currentScreen == null && Mouse.isButtonDown(0)) {
            if (!left.getValue())
                return;
            if (this.weaponOnly.getValue()) {
                if (mc.thePlayer.getCurrentEquippedItem() == null) {
                    return;
                }
                if (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) && !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
                    return;
                }
            }
            if (!blockHit.getValue() && mc.thePlayer.isUsingItem()) return;

            if (shouldAttack(Objects.equals(minCps.getValue().intValue(), maxCps.getValue().intValue()) ? maxCps.getValue().intValue() : ThreadLocalRandom.current().nextInt(minCps.getValue().intValue(), maxCps.getValue().intValue()))) {
                ReflectionUtil.setFieldValue(Minecraft.getMinecraft(), 0, "leftClickCounter", "field_71429_W");
                clickMouse();

                if (autoUnBlock.getValue()) {
                    if (Mouse.isButtonDown(1)) {
                        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                            if (mc.thePlayer.isBlocking()) {
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                                mc.playerController.onStoppedUsingItem(mc.thePlayer);
                                mc.thePlayer.setItemInUse(mc.thePlayer.getItemInUse(), 0);
                            } else {
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
                            }
                        }
                    }
                }
            }
            if (jitter.getValue() > 0.0) {
                final double a = jitter.getValue() * 0.45;
                if (this.random.nextBoolean()) {
                    final EntityPlayerSP thePlayer = AutoClicker.mc.thePlayer;
//                Minecraft.getMinecraft().thePlayer.rotationYawHead +=(float) (this.random.nextFloat() * a);
//                Minecraft.getMinecraft().thePlayer.renderYawOffset += (float) (this.random.nextFloat() * a);
//                eventUpdate.setYaw(eventUpdate.getYaw() + (float) (this.random.nextFloat() * a));
                    thePlayer.rotationYaw += (float) (this.random.nextFloat() * a);
                } else {
                    final EntityPlayerSP thePlayer2 = AutoClicker.mc.thePlayer;
                    thePlayer2.rotationYaw -= (float) (this.random.nextFloat() * a);
//                Minecraft.getMinecraft().thePlayer.rotationYawHead -=(float) (this.random.nextFloat() * a);
//                Minecraft.getMinecraft().thePlayer.renderYawOffset -= (float) (this.random.nextFloat() * a);
//                eventUpdate.setYaw(eventUpdate.getYaw() - (float) (this.random.nextFloat() * a));
                }
                if (this.random.nextBoolean()) {
                    final EntityPlayerSP thePlayer3 = AutoClicker.mc.thePlayer;
                    thePlayer3.rotationPitch += (float) (this.random.nextFloat() * (a * 0.45));
//                eventUpdate.setPitch(eventUpdate.getPitch() +(float) (this.random.nextFloat() * (a * 0.45)));
                } else {
                    final EntityPlayerSP thePlayer4 = AutoClicker.mc.thePlayer;
                    thePlayer4.rotationPitch -= (float) (this.random.nextFloat() * (a * 0.45));
//                eventUpdate.setPitch(eventUpdate.getPitch() -(float) (this.random.nextFloat() * (a * 0.45)));
                }
            }

        }

        if (mc.currentScreen == null && Mouse.isButtonDown(1)) {
            if (!right.getValue())
                return;
            if (shouldAttack(RminCps.getValue().intValue() == RmaxCps.getValue().intValue() ? RmaxCps.getValue().intValue() : ThreadLocalRandom.current().nextInt(RminCps.getValue().intValue(), RmaxCps.getValue().intValue() + 1))) {
                try {
                    final Field rightClickDelay = Minecraft.class.getDeclaredField("field_71467_ac");
                    rightClickDelay.setAccessible(true);
                    rightClickDelay.set(mc, 0);
                } catch (Exception d) {
                    try {
                        final Field ex = Minecraft.class.getDeclaredField("rightClickDelayTimer");
                        ex.setAccessible(true);
                        ex.set(mc, 0);
                    } catch (Exception f) {
                        this.disable();
                    }
                }
            }
        }
    }


    public boolean shouldAttack(int cps) {
        int aps = 20 / cps;
        return timeUtils.hasTimeElapsed(50 * aps,true);
    }

    public void reset() {
        timeUtils.reset();
    }
}
