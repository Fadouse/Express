package cc.express.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtil {
    public static void sendMessage(String s){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(s));
    }
}
