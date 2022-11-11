package cc.express.module;

import cc.express.event.EventManager;
import cc.express.module.values.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;

public class Module {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public boolean state = false;
    public int key = -1;
    public boolean remove = false;
    public String name;
    public Category category;
    ArrayList<Value<?>> values;
    public String suffix = null;

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
        values = new ArrayList<>();
    }

    public void setSuffix(Object obj) {
        String suffix = obj.toString();
        if (suffix.isEmpty()) {
            this.suffix = suffix;
        } else {
            this.suffix = String.format("§f %s§7", EnumChatFormatting.GRAY + suffix);
        }
    }


    public ArrayList<Value<?>> getValues() {
        return values;
    }

    public void toggle() {
        this.setState(!this.state);
    }

    public void setState(boolean state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        if (state) {
            EventManager.register(this);
            enable();
        } else {
            EventManager.unregister(this);
            disable();
        }
    }

    public void enable() {

    }

    public void disable() {

    }

    public boolean isEnabled(){
        return this.state;
    }
    public boolean isDisabled(){
        return this.state;
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getState() {
        return state;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
