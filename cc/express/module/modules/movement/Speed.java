package cc.express.module.modules.movement;

import cc.express.event.EventTarget;
import cc.express.event.world.EventMove;
import cc.express.event.world.EventUpdate;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.values.Numbers;
import cc.express.module.values.Option;
import cc.express.utils.client.PacketUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Speed extends Module {
    public Speed() {
        super("Speed", Category.Movement);
    }

    Numbers speed = new Numbers("Speed", 1, 1, 3, 1);
    Option bypass = new Option("Bypass", false);

    @EventTarget
    public void onMove(EventUpdate event) {
        setSuffix("Hypixel");
        if(mc.thePlayer.onGround) {
            double distX = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
            double distZ = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;

            for(int i = 0; i < speed.getValue().intValue(); i++) {
                if (bypass.getValue())
                PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, event.getY(), mc.thePlayer.posZ, true));
                if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + distX * 3, mc.thePlayer.posY, mc.thePlayer.posZ + distZ * 3)).getBlock() instanceof BlockAir) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX + distX, mc.thePlayer.posY, mc.thePlayer.posZ + distZ);
                    event.setX(mc.thePlayer.posX);
                    event.setZ(mc.thePlayer.posZ);
                }
            }
        }
    }
}
