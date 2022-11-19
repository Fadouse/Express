package cc.express.command.commands;

import cc.express.command.Command;
import cc.express.module.Module;
import cc.express.module.ModuleManager;
import cc.express.utils.ChatUtil;
import cc.express.utils.client.HelperUtil;
import net.minecraft.util.EnumChatFormatting;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super(new String[]{"toggle","t"});
    }

    @Override
    public void run(String[] args) {
        if(args.length == 1){
            Module m = ModuleManager.instance.getModule(args[0]);
            if(m != null){
                m.toggle();
            }else {
                HelperUtil.sendMessage(EnumChatFormatting.RED + "Module not found!");
            }
        }else {
            HelperUtil.sendMessage(EnumChatFormatting.RED + "Usage: t <Module>");
        }
    }
}
