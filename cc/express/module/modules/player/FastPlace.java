package cc.express.module.modules.player;

import cc.express.event.EventTarget;
import cc.express.event.world.EventTick;
import cc.express.module.Category;
import cc.express.module.Module;
import net.minecraft.item.ItemBlock;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", Category.Player);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
            this.mc.rightClickDelayTimer = 0;
        }
    }
}
