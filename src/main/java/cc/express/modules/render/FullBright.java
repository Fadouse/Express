package cc.express.modules.render;

import cc.express.event.events.TickEvent;
import cc.express.event.EventTarget;
import cc.express.modules.Category;
import cc.express.modules.Module;

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
    public void onTick(TickEvent e){
        mc.gameSettings.gammaSetting = 300;
    }
}