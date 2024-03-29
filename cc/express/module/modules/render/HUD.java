package cc.express.module.modules.render;

import cc.express.event.EventTarget;
import cc.express.event.rendering.EventRender2D;
import cc.express.gui.clickgui.express.RenderUtil;
import cc.express.gui.fontrender.FontManager;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.ModuleManager;
import cc.express.module.values.Mode;
import cc.express.module.values.Numbers;
import cc.express.module.values.Option;
import cc.express.utils.render.BlurUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;

public class HUD extends Module {
    Option arraylist = new Option("Arraylist", true);
    Option titleBar = new Option("TitleBar", true);

    Numbers saturation = new Numbers("Saturation", 0.7, 0, 1, 0.1);
    Numbers widget = new Numbers("Widget", 1, 0, 22, 1);

    public HUD() {
        super("HUD", Category.Render);
        this.remove = true;
        setState(true);

    }

    @EventTarget
    public void onRender(EventRender2D e){
        if (titleBar.getValue()) {
            FontManager.F20.drawStringWithShadow("Express", 5, 5, new Color(255, 255, 255).getRGB());
        }
        if (arraylist.getValue()) {
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
                    Color rainbow = new Color(Color.HSBtoRGB((float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, saturation.getValue().floatValue(),1.0f));
                    int moduleWidth = m.suffix == null ? FontManager.F18.getStringWidth(name) : FontManager.F18.getStringWidth(m.suffix + name + " ");

                    RenderUtil.drawRect(s.getScaledWidth() - moduleWidth - 3, y, s.getScaledWidth(), y + 9, new Color(50, 50, 50, 100).getRGB());
                    BlurUtil.blur(s.getScaledWidth() - moduleWidth - 3, y, moduleWidth + 3, 9, 3);
                    FontManager.F18.drawStringWithShadow(name, s.getScaledWidth() - moduleWidth - 1, y, rainbow.getRGB());
                    if(m.suffix != null) {
                        FontManager.F18.drawStringWithShadow(" " + m.suffix, s.getScaledWidth() - FontManager.F18.getStringWidth(m.suffix + " ") - 1, y, new Color(160, 160, 160).getRGB());
                    }

                    y += FontManager.F18.getHeight() + 1;
                    if (++rainbowTick > 50) {
                        rainbowTick = 0;
                    }
                }
            }
        }
        if (widget.getValue() != 0) {
            Gui.drawRect(0,0,0,0,0);
            RenderUtil.drawImage(new ResourceLocation("express/anime/Widget_" + String.valueOf(widget.getValue().intValue()) +".png"), RenderUtil.width()/4, RenderUtil.height() - 100, 100, 100);
        }
    }
}
