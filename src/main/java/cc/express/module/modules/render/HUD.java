package cc.express.module.modules.render;

import cc.express.event.EventTarget;
import cc.express.event.rendering.EventRender2D;
import cc.express.gui.fontrender.FontManager;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.ModuleManager;
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
    public void onRender(EventRender2D e){
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
                String name =  m.getName();
                Color rainbow = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 1.0f,1.0f));
                if(m.suffix != null)
                    FontManager.F18.drawStringWithShadow(" " + m.suffix,s.getScaledWidth()- FontManager.F18.getStringWidth(m.suffix+" ")-1, y, new Color(160,160,160).getRGB());
                int moduleWidth = m.suffix == null ? FontManager.F18.getStringWidth(name) : FontManager.F18.getStringWidth(m.suffix + name + " ");
                FontManager.F18.drawStringWithShadow(name, s.getScaledWidth() - moduleWidth - 1, y, rainbow.getRGB());
                y += FontManager.F18.getHeight() + 1;
                if (++rainbowTick > 50) {
                    rainbowTick = 0;
                }
            }
        }
    }
}
