package cc.express.command.commands;

import cc.express.command.Command;
import cc.express.module.Module;
import cc.express.module.ModuleManager;
import cc.express.utils.ChatUtil;
import cc.express.utils.client.HelperUtil;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {
    public BindCommand() {
        super(new String[]{"bind","b"});
    }

    @Override
    public void run(String[] args) {
        if(args.length == 2){
            Module m = ModuleManager.instance.getModule(args[0]);
            if(m != null){
                int key = Keyboard.getKeyIndex(args[1].toUpperCase());
                m.setKey(key);
                HelperUtil.sendMessage(EnumChatFormatting.GREEN + "Success bound " + m.getName() + " to " + Keyboard.getKeyName(m.getKey()) + "!");
            }else {
                HelperUtil.sendMessage(EnumChatFormatting.RED + "Module not found!");
            }
        }else {
            HelperUtil.sendMessage(EnumChatFormatting.RED + "Usage: bind <Module> <Key>");
        }
    }
}
