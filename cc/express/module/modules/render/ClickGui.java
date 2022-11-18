package cc.express.module.modules.render;

import cc.express.gui.clickgui.express.NormalClickGUI;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.values.Mode;
import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {

    public Mode mode = new Mode("Mode",  (Enum[]) ClickGuiMode.values(), (Enum) ClickGuiMode.Normal);
    public ClickGui() {
        super("ClickGui", Category.Render);
        setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void enable() {
        mc.displayGuiScreen(new NormalClickGUI());

        this.setState(false);
    }
    public static enum ClickGuiMode {
        Normal,
        Novoline,
    }

}
