package cc.express.module.modules.combat;

import cc.express.event.EventTarget;
import cc.express.event.world.EventUpdate;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.ModuleManager;
import cc.express.module.values.Mode;
import cc.express.module.values.Numbers;
import cc.express.module.values.Option;
import cc.express.utils.client.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

public class KillAura extends Module {
    public KillAura() {
        super("KillAura", Category.Combat);
    }

    Numbers cps = new Numbers("CPS", 10, 1, 20, 0.5);
    Numbers delay = new Numbers("SwitchDelay", 400, 0, 1000,50);
    private static Numbers range = new Numbers("Range", 4.2, 3, 6, 0.1);
    private static Option autoblock = new Option("AutoBlock", true);
    private static Option invis = new Option("Invis", true);
    private static Option mobs = new Option("Mobs", true);
    private static Option animals = new Option("Animals", false);
    private static Option players = new Option("Players", true);
    Mode<modeEnums> mode = new Mode<>("Mode", modeEnums.values(), modeEnums.Single);

    int index;
    List<Entity> targets = new ArrayList<>();
    private final Comparator<Entity> angleComparator = Comparator.comparingDouble(e2 -> e2.getDistanceToEntity(Minecraft.getMinecraft().thePlayer));
    TimeUtil switchTimer = new TimeUtil();

    public void onEnable() {
        index = 0;
    }


    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix(mode.getValue());

        this.targets = getTargets(range.getValue());
        targets.sort(this.angleComparator);

        if (this.targets.size() > 1 && (this.mode.getValue() == modeEnums.Switch)) {
            if (switchTimer.delay(delay.getValue().longValue())) {
                ++this.index;
                switchTimer.reset();
            }
        }
    }

    /**
     * 获取周围目标
     */
    public static List<Entity> getTargets(Double value) {
        return Minecraft.getMinecraft().theWorld.loadedEntityList.stream().filter(e -> (double) Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e) <= value && CanAttack(e)).collect(Collectors.toList());
    }

    /**
     * 检测周围生物是否符合选项
     */
    private static boolean CanAttack(Entity e2) {
        if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e2) > range.getValue())
            return false;
        if (e2.isInvisible() && !invis.getValue())
            return false;
        if (!e2.isEntityAlive())
            return false;
        if (e2 == Minecraft.getMinecraft().thePlayer || e2.isDead || Minecraft.getMinecraft().thePlayer.getHealth() == 0F)
            return false;
        if ((e2 instanceof EntityMob || e2 instanceof EntityGhast || e2 instanceof EntityGolem
                || e2 instanceof EntityDragon || e2 instanceof EntitySlime) && mobs.getValue())
            return true;
        if ((e2 instanceof EntitySquid || e2 instanceof EntityBat || e2 instanceof EntityVillager)
                && animals.getValue())
            return true;
        if (e2 instanceof EntityAnimal && animals.getValue())
            return true;

        Antibot ab2 = (Antibot) ModuleManager.getModule(Antibot.class);
        if (ab2.isServerBot(e2))
            return false;

        return e2 instanceof EntityPlayer && players.getValue();
    }

    enum modeEnums {
        Single, Switch
    }
}
