package cc.express.modules.render;

import cc.express.event.events.Render2DEvent;
import cc.express.gui.fontrender.FontManager;
import cc.express.event.EventTarget;
import cc.express.modules.Category;
import cc.express.modules.Module;
import cc.express.modules.ModuleManager;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;

public class HUD extends Module {
    public HUD() {
        super("HUD", Category.Render);
        this.remove = true;
        setState(true);
    }

    @EventTarget
    public void onRender(Render2DEvent e){
        int rainbowTick = 0;
        ScaledResolution s = new ScaledResolution(mc);
        int y = 1;
        ArrayList<Module> enabledModules = new ArrayList<>();
        for (Module m : ModuleManager.instance.getModules()) {
            if (m.state && !m.remove) {
                enabledModules.add(m);
            }
        }
        enabledModules.sort((o1, o2) -> FontManager.F18.getStringWidth(o2.suffix == null ? o2.getName() : o2.getName() + o2.suffix) - FontManager.F18.getStringWidth(o1.suffix == null ? o1.getName() : o1.getName() + o1.suffix));
        for (Module m : enabledModules) {
            if (m != null && m.getState()) {
                String name = m.suffix == null ? m.getName() : m.getName() + m.suffix;
                Color rainbow = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 1.0f,1.0f));
                int moduleWidth = FontManager.F18.getStringWidth(name);
                FontManager.F18.drawStringWithShadow(name, s.getScaledWidth() - moduleWidth - 1, y, rainbow.getRGB());
                y += FontManager.F18.getHeight() + 1;
                if (++rainbowTick > 50) {
                    rainbowTick = 0;
                }
            }
        }
    }
}
