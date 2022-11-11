package cc.express.modules.render;

import cc.express.gui.clickgui.express.NormalClickGUI;
import cc.express.modules.Category;
import cc.express.modules.Module;
import cc.express.modules.values.Mode;

public class ClickGui extends Module {

    public Mode mode = new Mode("Mode",  (Enum[]) ClickGuiMode.values(), (Enum) ClickGuiMode.Normal);
    public ClickGui() {
        super("ClickGui", Category.Render);
    }

    @Override
    public void enable() {
        mc.displayGuiScreen(new NormalClickGUI());

        this.setState(false);
    }
    public static enum ClickGuiMode {
        New,
        Normal,
        Novoline,
    }

}
