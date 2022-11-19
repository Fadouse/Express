package cc.express.module.modules.combat;

import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.utils.client.HelperUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class Antibot extends Module {
    public Antibot() {
        super("Antibot", Category.Combat);
    }
    public boolean isServerBot(Entity entity) {
        if (this.isEnabled()) {
            if (HelperUtil.onServer("hypixel")) {
                if (entity.getDisplayName().getFormattedText().startsWith("\u00a7") && !entity.isInvisible() && !entity.getDisplayName().getFormattedText().toLowerCase().contains("npc")) {
                    return false;
                }
                return true;
            }
            if (HelperUtil.onServer("mineplex")) {
                for (Object object : this.mc.theWorld.playerEntities) {
                    EntityPlayer entityPlayer = (EntityPlayer)object;
                    if (entityPlayer == null || entityPlayer == this.mc.thePlayer || !entityPlayer.getName().startsWith("Body #") && entityPlayer.getMaxHealth() != 20.0f) continue;
                    return true;
                }
            }
        }
        return false;
    }
}
