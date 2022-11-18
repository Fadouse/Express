package cc.express.module.modules.render;

import cc.express.event.EventTarget;
import cc.express.event.world.EventTick;
import cc.express.module.Category;
import cc.express.module.Module;

public class FullBright extends Module {
    float old;

    public FullBright() {
        super("FullBright", Category.Render);
    }

    @Override
    public void enable() {
        old = mc.gameSettings.gammaSetting;
    }

    @Override
    public void disable() {
        mc.gameSettings.gammaSetting = old;
    }

    @EventTarget
    public void onTick(EventTick e){
        mc.gameSettings.gammaSetting = 300;
    }
}
