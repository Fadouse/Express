package cc.express.utils.client;

import cc.express.Client;
import cc.express.utils.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class HelperUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void sendMessage(String message) {
        new ChatUtil.ChatMessageBuilder(true, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }

    public static void sendMessageWithoutPrefix(String message) {
        new ChatUtil.ChatMessageBuilder(false, true).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
    }

    public static boolean onServer(String server) {
        if (!mc.isSingleplayer() && HelperUtil.mc.getCurrentServerData().serverIP.toLowerCase().contains(server)) {
            return true;
        }
        return false;
    }
}
