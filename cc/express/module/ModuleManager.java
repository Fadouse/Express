package cc.express.module;


import cc.express.event.EventManager;
import cc.express.event.EventTarget;
import cc.express.event.misc.EventKey;
import cc.express.module.modules.combat.*;
import cc.express.module.modules.movement.Sprint;
import cc.express.module.modules.player.Nofall;
import cc.express.module.modules.render.BlockHit;
import cc.express.module.modules.render.ClickGui;
import cc.express.module.modules.render.FullBright;
import cc.express.module.modules.render.HUD;
import cc.express.module.values.Value;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public enum ModuleManager {
    instance;

    public void init(){
        EventManager.register(this);
        System.out.println("init modules...");
        //Combat
        addModule(new AutoClicker());
        addModule(new Antibot());
        addModule(new KillAura());
        addModule(new Criticals());
        addModule(new Reach());
        addModule(new Velocity());
        //Movement
        addModule(new Sprint());
        //Render
        addModule(new BlockHit());
        addModule(new FullBright());
        addModule(new ClickGui());
        addModule(new HUD());
        //Misc

        //Player
        addModule(new Nofall());

    }

    @EventTarget
    public void onKey(EventKey e){
        for (Module m : modules){
            if(m.getKey() == e.getKey()){
                m.toggle();
            }
        }
    }

    public void addModule(Module module){
        for (final Field field : module.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                final Object obj = field.get(module);
                if (obj instanceof Value) module.getValues().add((Value) obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        modules.add(module);
    }

    static final ArrayList<Module> modules = new ArrayList<Module>();

    public ArrayList<Module> getModules() {
        return modules;
    }

    public static Module getModule(Class<?> cls) {
        for (Module m : modules) {
            if (m.getClass() == cls)
                return m;
        }
        return null;
    }

    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name))
                return m;
        }
        return null;
    }

    public static List<Module> getModulesInType(Category t) {
        ArrayList<Module> output = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.getCategory() != t) continue;
            output.add(m);
        }
        return output;
    }

    public List<Module> getModsByCategory(Category m) {
        List<Module> findList = new ArrayList<>();
        for(Module mod: modules){
            if(mod.getCategory() == m){
                findList.add(mod);
            }
        }
        return findList;
    }
}
