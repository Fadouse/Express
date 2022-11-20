package cc.express.module.modules.misc;

import cc.express.event.EventTarget;
import cc.express.event.misc.EventPacket;
import cc.express.event.world.EventMove;
import cc.express.event.world.EventUpdate;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.utils.exploit.DelayedPacketThread;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.util.BlockPos;

public class Disabler extends Module {
    public Disabler() {
        super("Disabler", Category.Misc);
    }

    boolean gameStarted;

    public void enable() {
        gameStarted = true;
    }

    @EventTarget
    public void onPacket(EventPacket event) {
        if (event.getPacket() instanceof C0FPacketConfirmTransaction || event.getPacket() instanceof C00PacketKeepAlive) {
            new DelayedPacketThread(event.getPacket(), mc.thePlayer.ticksExisted < 150 ? 1000 : 450).start();
            event.setCancelled(true);
        }
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();

            if(mc.thePlayer.ticksExisted <= 30) {
                gameStarted = false;
            }

            if (!gameStarted) {
                event.setCancelled(true);
            } else if (!packet.isMoving() && !packet.getRotating() && !mc.thePlayer.isUsingItem()) {
                event.setCancelled(true);
            }
        } else if (event.getPacket() instanceof C0BPacketEntityAction) {
            C0BPacketEntityAction packet = (C0BPacketEntityAction) event.getPacket();

            if (packet.getAction() == C0BPacketEntityAction.Action.START_SPRINTING || packet.getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onMove(EventMove event) {
        if(!gameStarted) {
            event.setCancelled(true);
            mc.thePlayer.motionX = mc.thePlayer.motionY = mc.thePlayer.motionZ = 0;
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.ticksExisted > 50) {
            boolean glassUnder = false;

            for(double y = event.getY() + 4; y >= event.getY() - 6; y--) {
                Block block = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, y, mc.thePlayer.posZ)).getBlock();
                if(block instanceof BlockGlass || block instanceof BlockStainedGlass) {
                    glassUnder = true;
                }
            }

            if(!glassUnder) {
                gameStarted = true;
            }
        }
    }
}
