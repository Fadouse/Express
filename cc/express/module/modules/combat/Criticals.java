package cc.express.module.modules.combat;

import cc.express.event.EventTarget;
import cc.express.event.attack.EventFight;
import cc.express.event.world.EventStep;
import cc.express.event.world.EventUpdate;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.values.Mode;
import cc.express.module.values.Numbers;
import cc.express.utils.client.MathUtil;
import cc.express.utils.client.TimeUtil;
import cc.express.utils.player.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Criticals extends Module {
    private final TimeUtil timer = new TimeUtil(), prevent = new TimeUtil();

    private final Mode<modeEnums> modeValue = new Mode<>("Mode", modeEnums.values(),modeEnums.Hypixel);

    private final Numbers hurtTimeValue = new Numbers("HurtTime",15.0, 0.0, 20.0, 1.0),

    delayValue = new Numbers("Delay", 3.0, 0.5, 10.0, 0.5);

    private int groundTicks;

    public Criticals() {
        super("Criticals", Category.Combat);
    }

    @Override
    public void enable() {
        timer.reset();
        prevent.reset();
        groundTicks = 0;
    }

    @EventTarget
    void onUpdate(EventUpdate event) {
        setSuffix(modeValue.getValue().toString());

        if (PlayerUtil.isOnGround(.01)) groundTicks++;
        else groundTicks = 0;

        if (groundTicks > 20) groundTicks = 20;
        switch (modeValue.getValue().toString().toLowerCase()) {
            case "noground" : {
                event.setOnGround(false);
            }
        }
    }

    @EventTarget
    void onStep(EventStep event) {
        if (!event.isPre()) prevent.reset();
    }


    @EventTarget
    void onAttack(EventFight event) {
        boolean canCrit =
                groundTicks > 3 &&
                        mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock().isFullBlock() &&

                        !PlayerUtil.isInLiquid() &&
                        !PlayerUtil.isOnLiquid() &&
                        !mc.thePlayer.isOnLadder() &&
                        mc.thePlayer.ridingEntity == null &&
                        !PlayerUtil.isOnGround(-2);

        if (event.isPre() && canCrit && event.getEntity().hurtResistantTime <= hurtTimeValue.getValue().intValue() && prevent.hasPassed(300) && timer.hasPassed(delayValue.getValue().intValue() * 100L)) {

            switch (modeValue.getValue().toString().toLowerCase()) {
                case "hypixel" : {
                    double[] values = {
                            0.0625 + Math.random() / 100,0.03125 + Math.random() / 100
                    };
                    for (double value : values) {
                        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + value, mc.thePlayer.posZ, false));
                    }
                    break;
                }
                case "packet" : {
                    double[] values = {
                            0.0425, .0015, MathUtil.getRandom().nextBoolean() ? .012 : .014
                    };
                    if (mc.thePlayer.ticksExisted % 2 == 0)
                        for (double value : values) {
                            double random = MathUtil.getRandom().nextBoolean() ? MathUtil.getRandom(-1E-8, -1E-7) : MathUtil.getRandom(1E-7, 1E-8);
                            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + value + random, mc.thePlayer.posZ, false));
                        }
                    break;
                }
                case "visual" : {
                    mc.thePlayer.onCriticalHit(event.getEntity());
                    break;
                }
                case "jump" : {
                    mc.thePlayer.motionY = .41999998688697815;
                    break;
                }
                case "hop" : {
                    mc.thePlayer.motionY = .1;
                    mc.thePlayer.fallDistance = .1F;
                    mc.thePlayer.onGround = false;
                    break;
                }
            }
        }
    }

    private enum modeEnums {
        Packet, Hypixel, Hypixel2, Hop, Jump, Visual, NoGround
    }
}
