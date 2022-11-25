package cc.express.module.modules.player;

import cc.express.event.EventTarget;
import cc.express.event.world.EventUpdate;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.values.Mode;

public class Nofall extends Module {
    public Nofall() {
        super("Nofall", Category.Player);
    }

    Mode<modeEnum> mode = new Mode<>("Mode", modeEnum.values(), modeEnum.HypixelSpoof);

    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix(mode.getValue().toString());
        switch (mode.getValue().toString().toLowerCase()) {
            case "hypixelspoof" : {
                if (mc.thePlayer.ticksExisted > 50 && mc.thePlayer.fallDistance > 3) {
                    // 下落距离超过三格发送地面包
                    e.setOnGround(true);
                }
                break;
            }
        }
    }

    enum modeEnum {
        HypixelSpoof
    }
}
