package cc.express.module.modules.movement;

import cc.express.event.EventTarget;
import cc.express.event.world.EventTick;
import cc.express.module.Category;
import cc.express.module.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.Movement);
        setState(true);
    }

    @EventTarget
    public void onUpdate(EventTick event) {
        if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0) {
            mc.thePlayer.setSprinting(true);
        }
    }
}
