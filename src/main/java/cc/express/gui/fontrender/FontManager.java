package cc.express.gui.fontrender;

import cc.express.Client;

import java.awt.*;
import java.io.InputStream;

public class FontManager {
    public static FontObject F14;
    public static FontObject F16;
    public static FontObject F18;
    public static FontObject F20;
    public static FontObject F22;
    public static FontObject F23;
    public static FontObject F24;
    public static FontObject F26;
    
    public static void init(){
        F14 = new FontObject(fontFromTTF(14, Font.PLAIN), true, true, true, 0, 0);
        F16 = new FontObject(fontFromTTF(16, Font.PLAIN), true, true, true, 0, 0);
        F18 = new FontObject(fontFromTTF(18, Font.PLAIN), true, true, true, 0, 0);
        F20 = new FontObject(fontFromTTF(20, Font.PLAIN), true, true, true, 0, 0);
        F22 = new FontObject(fontFromTTF(22, Font.PLAIN), true, true, true, 0, 0);
        F23 = new FontObject(fontFromTTF(23, Font.PLAIN), true, true, true, 0, 0);
        F24 = new FontObject(fontFromTTF(24, Font.PLAIN), true, true, true, 0, 0);
        F26 = new FontObject(fontFromTTF(26, Font.PLAIN), true, true, true, 0, 0);
    }

    public static Font fontFromTTF(float fontSize, int fontType) {
        Font output = null;
        try {
            InputStream inputStream = Client.class.getResourceAsStream("/assets/minecraft/czfbase/alisans.ttf");
            output = Font.createFont(fontType, inputStream);
            output = output.deriveFont(fontSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
