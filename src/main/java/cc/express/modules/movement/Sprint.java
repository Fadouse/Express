package cc.express.modules.movement;

import cc.express.event.events.TickEvent;
import cc.express.event.EventTarget;
import cc.express.modules.Category;
import cc.express.modules.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.Movement);
        setState(true);
    }

    @EventTarget
    public void onUpdate(TickEvent event) {
        if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0) {
            mc.thePlayer.setSprinting(true);
        }
    }
}
