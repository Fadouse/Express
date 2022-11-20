package cc.express.module.modules.combat;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import cc.express.event.EventManager;
import cc.express.event.EventTarget;
import cc.express.event.attack.EventFight;
import cc.express.event.misc.EventPacket;
import cc.express.event.rendering.EventRender3D;
import cc.express.event.world.EventPacketSend;
import cc.express.event.world.EventUpdate;
import cc.express.gui.fontrender.CFont;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.ModuleManager;
import cc.express.module.values.Mode;
import cc.express.module.values.Numbers;
import cc.express.module.values.Option;
import cc.express.utils.client.MathUtil;
import cc.express.utils.client.TimeUtil;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;


import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.MathHelper;
import sun.security.pkcs11.Secmod;

public class KillAura
        extends Module {

    private static final Color COLOR = new Color(0, 0, 0, 180);
    public static EntityLivingBase target;
    public static List<Entity> targets = new ArrayList(0);
    private int index;
    private final TimeUtil timer = new TimeUtil();
    public static Boolean executingblock;
    private final TimeUtil lossTimer = new TimeUtil();
    public static Boolean blockingAnim = false;
    private final Numbers Cps = new Numbers("CPS", 10.0, 1.0, 20.0, 0.5);
    public static Numbers Range = new Numbers("Range", 4.5, 1.0, 6.0, 0.1);
    public static Numbers Blockreach = new Numbers("Blockreach", 4.5, 1.0, 6.0, 0.1);
    public static Numbers SwitchDelay = new Numbers("SwitchDelay", 500.0, 1.0, 5000.0, 1.0);
    private final Option Autoblock = new Option("Autoblock", true);
    public static Option Players = new Option("Players", true);
    public static Option Animals = new Option("Animals", true);
    public static Option Mobs = new Option("Mobs", false);
    private static final Option Invis = new Option("Invisibles", false);
    private final Option Esp = new Option("ShowTarget", true);
    public static cc.express.module.values.Mode Mode = new Mode("Mode", AuraMode.values(), AuraMode.Single);
    public static boolean isBlocking;
    private final Comparator<Entity> angleComparator = Comparator.comparingDouble(e2 -> e2.getDistanceToEntity(Minecraft.getMinecraft().thePlayer));

    private final TimeUtil AttackTimer = new TimeUtil();

    private final TimeUtil SwitchTimer = new TimeUtil();

    public KillAura() {
        super("KillAura", Category.Combat);
    }

    public void disable() {
        target = null;
        this.targets.clear();
        if (isBlocking) stopBlock();
    }

    public void enable() {
        lossTimer.reset();
        target = null;
        this.index = 0;
    }

    public boolean serverLag() {
        return lossTimer.getLastDelay() >= 100;
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        if (this.Esp.getValue()) {
            switch ((AuraMode) Mode.getValue()) {
                case Single:
                case Switch: {
                    if (target != null) {
                        drawShadow(target, 0.67, new Color(255, 255, 255).getRGB(), true);
                    }
                    break;
                }
            }
        }
    }

    private boolean hasSword() {
        if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null) {
            return Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
        } else {
            return false;
        }
    }

    private boolean shouldAttack() {
        return this.AttackTimer.hasReached(1000.0 / (this.Cps.getValue() + MathUtil.getRandom(-1.0, 1.0)));
    }

    @EventTarget
    private void onUpdate(EventUpdate event) {
        if(event.isPre()) {
            this.setSuffix(this.Mode.getValue());

            this.targets = getTargets(Range.getValue());

            if(target == null && isBlocking){
                stopBlock();
            }

            targets.sort(this.angleComparator);

            if (this.targets.size() > 1 && (this.Mode.getValue() == AuraMode.Switch || this.Mode.getValue() == AuraMode.Multiple)) {
                if (SwitchTimer.delay(SwitchDelay.getValue().longValue()) || Mode.getValue().equals(AuraMode.Multiple)) {
                    ++this.index;
                    SwitchTimer.reset();
                }
            }

            if (Minecraft.getMinecraft().thePlayer.ticksExisted % SwitchDelay.getValue().intValue() == 0 && this.targets.size() > 1 && this.Mode.getValue() == AuraMode.Single) {

                if (target.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) > Range.getValue()) {
                    ++index;
                } else if (target.isDead) {
                    ++index;
                }
            }

            if (target != null) {
                target = null;
            }

            if (!this.targets.isEmpty()) {
                if (this.index >= this.targets.size()) {
                    this.index = 0;
                }
                target = (EntityLivingBase) this.targets.get(this.index);
                if (this.shouldAttack()) {
                    EventManager.call(new EventFight(target, true));
                    attackEntity(target);
                    EventManager.call(new EventFight(target, false));
                    this.AttackTimer.reset();
                    event.setYaw(getRotation1(target)[0]);
                    event.setPitch(getRotation1(target)[1]);
                }
            }
        }
    }

    private void attackEntity(Entity target) {
        Minecraft.getMinecraft().thePlayer.swingItem();
        Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
        if (shouldBlock()) {
            doBlock();
        }
        this.timer.reset();
    }

    public boolean shouldBlock() {
        return Autoblock.getValue() && this.hasSword() && target != null && target.isEntityAlive() && isEnabled() && !mc.playerController.getIsHittingBlock();
    }

    //Blocking
    private void doBlock() {
        if (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            if (Minecraft.getMinecraft().playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem())) {
                mc.getItemRenderer().resetEquippedProgress();
            }
            isBlocking = true;
        }
    }

    private void stopBlock(){
        blockingAnim = false;
        isBlocking = false;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        Minecraft.getMinecraft().playerController.onStoppedUsingItem(mc.thePlayer);
    }


    public static List<Entity> getTargets(Double value) {
        return Minecraft.getMinecraft().theWorld.loadedEntityList.stream().filter(e -> (double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e) <= value && CanAttack(e)).collect(Collectors.toList());
    }

    @EventTarget
    public void onSend(EventPacket event) {
        if(event.isInComing())
        lossTimer.reset();
    }

    @EventTarget
    public void onPacketSend(EventPacketSend e){
        Packet packet = e.getPacket();
        if ((packet instanceof C07PacketPlayerDigging || packet instanceof C09PacketHeldItemChange)) {
            executingblock = false;
        } else if (packet instanceof C08PacketPlayerBlockPlacement && target != null && !executingblock) {
//                Helper.sendMessage("KaBlockTrue");
            executingblock = true;
        }
    }

    private static boolean CanAttack(Entity e2) {
        if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e2) > Range.getValue())
            return false;
        if (e2.isInvisible() && !Invis.getValue())
            return false;
        if (!e2.isEntityAlive())
            return false;
        if (e2 == Minecraft.getMinecraft().thePlayer || e2.isDead || Minecraft.getMinecraft().thePlayer.getHealth() == 0F)
            return false;
        if ((e2 instanceof EntityMob || e2 instanceof EntityGhast || e2 instanceof EntityGolem
                || e2 instanceof EntityDragon || e2 instanceof EntitySlime) && Mobs.getValue())
            return true;
        if ((e2 instanceof EntitySquid || e2 instanceof EntityBat || e2 instanceof EntityVillager)
                && Animals.getValue())
            return true;
        if (e2 instanceof EntityAnimal && Animals.getValue())
            return true;

        Antibot ab2 = (Antibot) ModuleManager.getModule(Antibot.class);
        if (ab2.isServerBot(e2))
            return false;

        return e2 instanceof EntityPlayer && Players.getValue();
    }

    public static float[] getRotationFromPositionSlow(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;

        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180 / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180 / 3.141592653589793D);
        return new float[]{yaw, pitch};
    }

    public static float[] getRotation3(EntityLivingBase ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + ent.getEyeHeight() / 2f;
        return getRotationFromPositionSlow(x, z, y);
    }

    public static float[] getRotation2(EntityLivingBase target) {
        Minecraft.getMinecraft();
        double xDiff = target.posX - Minecraft.getMinecraft().thePlayer.posX;
        double yDiff = target.posY - Minecraft.getMinecraft().thePlayer.posY-0.2;
        double zDiff = target.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        Minecraft.getMinecraft();

        Minecraft.getMinecraft();

        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180 / Math.PI) - 90f;
        float pitch = (float)((- Math.atan2(yDiff, dist)) * 180 / Math.PI);
        float[] array = new float[2];
        int n = 0;
        Minecraft.getMinecraft();
        float rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float n2 = yaw;
        Minecraft.getMinecraft();
        array[n] = rotationYaw + MathHelper.wrapAngleTo180_float(n2 - Minecraft.getMinecraft().thePlayer.rotationYaw);
        int n3 = 1;
        Minecraft.getMinecraft();
        float rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
        float n4 = pitch;
        Minecraft.getMinecraft();
        array[n3] = rotationPitch + MathHelper.wrapAngleTo180_float(n4 - Minecraft.getMinecraft().thePlayer.rotationPitch);
        return array;
    }

    public static float[] getRotation1(EntityLivingBase entity) {
        EntityLivingBase entityLivingBase = entity;
        double diffX = entityLivingBase.posX - Minecraft.getMinecraft().thePlayer.posX;
        double diffZ = entityLivingBase.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY = entityLivingBase.posY + (double) entity.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.posY + (double) Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double X = diffX;
        double Z = diffZ;
        double dist = MathHelper.sqrt_double(X * X + Z * Z);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180 / 3.141592653589) - 90.0f;
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180 / 3.141592653589));
        return new float[]{yaw, pitch};
    }

    private void drawShadow(final Entity entity, final double rad, final int color, final boolean shade) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
        if (shade) GL11.glShadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableCull();
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - RenderManager.renderPosX;
        final double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - RenderManager.renderPosY) + Math.sin(System.currentTimeMillis() / 2E+2) + 1;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - RenderManager.renderPosZ;

        Color c;

        for (float i = 0; i < Math.PI * 2; i += Math.PI * 2 / 64.F) {
            final double vecX = x + rad * Math.cos(i);
            final double vecZ = z + rad * Math.sin(i);

            c = new Color(255,255,255);

            if (shade) {
                GL11.glColor4f(c.getRed() / 255.F,
                        c.getGreen() / 255.F,
                        c.getBlue() / 255.F,
                        0
                );
                GL11.glVertex3d(vecX, y - Math.cos(System.currentTimeMillis() / 2E+2) / 2.0F, vecZ);
                GL11.glColor4f(c.getRed() / 255.F,
                        c.getGreen() / 255.F,
                        c.getBlue() / 255.F,
                        0.85F
                );
            }
            GL11.glVertex3d(vecX, y, vecZ);
        }

        GL11.glEnd();
        if (shade) GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
        GlStateManager.enableCull();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(2848);
        GL11.glDisable(2848);
        GL11.glEnable(2832);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 255) / 255.0f;
        float red = (float)(hex >> 16 & 255) / 255.0f;
        float green = (float)(hex >> 8 & 255) / 255.0f;
        float blue = (float)(hex & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha == 0.0f ? 1.0f : alpha);
    }

    public static EntityLivingBase getTarget() {
        return target;
    }

    public enum AuraMode {
        Switch,
        Single,
        Multiple
    }
}
