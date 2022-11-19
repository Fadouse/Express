package cc.express.module.modules.combat;

import cc.express.event.EventTarget;
import cc.express.event.world.EventPacketReceive;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.values.Numbers;
import cc.express.module.values.Option;
import cc.express.utils.client.ReflectionUtil;
import cc.express.utils.player.MoveUtil;
import cc.express.utils.player.PlayerUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {
    //set the mode value
    //horizontal
    Numbers horizontal = new Numbers("Horizontal",0,-100,100,5);
    //vertical
    Numbers vertical = new Numbers("Vertical",0,0,100,5);
    //chance
    Numbers chance = new Numbers("Chance",100,0,100,5);

    Option jump = new Option("Jump", false);
    //Register module
    public Velocity(){
        super("Velocity", Category.Combat);
    }

    @EventTarget
    @SuppressWarnings("unused")
    public void onPacket(EventPacketReceive e) {
        if (e.getPacket() instanceof S12PacketEntityVelocity) {

            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket();

            if (packet.getEntityID() != mc.thePlayer.getEntityId())
                return;

            if (horizontal.getValue() == 0.0 && vertical.getValue() == 0.0 && chance.getValue()== 100.0) {
                e.setCancelled(true);
            }



            if(jump.getValue()) mc.thePlayer.jump();
            double motionX = packet.getMotionX(),
                    motionY = packet.getMotionY(),
                    motionZ = packet.getMotionZ();

            if (this.chance.getValue() != 100.0D) {
                double ch = Math.random();
                if (ch >= this.chance.getValue() / 100.0D) {
                    return;
                }
            }


            if (this.horizontal.getValue() != 100.0D) {
                motionX *= this.horizontal.getValue() / 100.0D;
                motionZ *= this.horizontal.getValue() / 100.0D;
            }

            if (this.vertical.getValue() != 100.0D) {
                motionY *= this.vertical.getValue() / 100.0D;
            }

            ReflectionUtil.setFieldValue(packet, (int) motionX, "motionX", "field_149415_b");
            ReflectionUtil.setFieldValue(packet, (int) motionY, "motionY", "field_149416_c");
            ReflectionUtil.setFieldValue(packet, (int) motionZ, "motionZ", "field_149414_d");
        }

        if (e.getPacket() instanceof S27PacketExplosion) {

            S27PacketExplosion packet = (S27PacketExplosion) e.getPacket();

            float motionX = packet.func_149149_c(),
                    motionY = packet.func_149144_d(),
                    motionZ = packet.func_149147_e();

            if (this.chance.getValue() != 100.0D) {
                double ch = Math.random();
                if (ch >= this.chance.getValue() / 100.0D) {
                    return;
                }
            }

//            mc.thePlayer.jump();

            if (this.horizontal.getValue() != 100.0D) {
                motionX *= this.horizontal.getValue() / 100.0D;
                motionZ *= this.horizontal.getValue() / 100.0D;
            }

            if (this.vertical.getValue() != 100.0D) {
                motionY *= this.vertical.getValue() / 100.0D;
            }
            ReflectionUtil.setFieldValue(packet, motionX, "field_149152_f");
            ReflectionUtil.setFieldValue(packet, motionY, "field_149153_g");
            ReflectionUtil.setFieldValue(packet, motionZ, "field_149159_h");
        }
    }




}
