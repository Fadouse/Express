package cc.express;

import cc.express.gui.altmanager.AltManager;
import cc.express.gui.fontrender.FontManager;
import cc.express.command.CommandManager;
import cc.express.event.EventBus;
import cc.express.module.ModuleManager;
import cc.express.plugin.PluginAPI;
import cc.express.plugin.PluginLoader;
import cc.express.utils.FileManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.io.File;

public class Client {
    public static Client instance;

    public String NAME = "Express";
    public String VERSION = "1.0";

    public String commandPrefix = ".";
    public AltManager altManager = new AltManager();
    public PluginAPI api = PluginAPI.getApi(pluginLoader -> new PluginLoader(), "PluginAPI");

    public Client(){
        instance = this;
        EventBus.getInstance().register(this);
        Display.setTitle("Client Loading...");
        api.loadAll(new File(Minecraft.getMinecraft().mcDataDir,"plugins"));
        FontManager.init();
        AltManager.init();
        FileManager.init();
        ModuleManager.instance.init();
        CommandManager.instance.init();
        Display.setTitle(NAME + " " + VERSION);
    }

    public AltManager getAltManager() {
        return altManager;
    }
}
