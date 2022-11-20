package cc.express.module.modules.movement;

import cc.express.event.EventTarget;
import cc.express.event.world.EventMove;
import cc.express.module.Category;
import cc.express.module.Module;

public class Speed extends Module {
    public Speed() {
        super("Speed", Category.Movement);
    }

    @EventTarget
    public void onMove(EventMove e) {
        e.setMoveSpeed(1);
    }
}
