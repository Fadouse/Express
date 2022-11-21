package cc.express.module.modules.movement;

import cc.express.event.EventTarget;
import cc.express.event.world.EventUpdate;
import cc.express.module.Category;
import cc.express.module.Module;
import cc.express.module.values.Mode;
import cc.express.utils.client.PacketUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class Noslow extends Module {
    public Noslow() {
        super("Noslow", Category.Movement);
    }
    enum noslowmode {
        Vanilla, Hypixel
    }

    Mode mode = new Mode("Mode", noslowmode.values(), noslowmode.Vanilla);

    @EventTarget
    public void onUpdate(EventUpdate e) {
        setSuffix(mode.getValue().toString());
        switch (mode.getValue().toString().toLowerCase()) {
            case "hypixel" : {
                if ((e.isPre() && mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() != null)) {
                    if (mc.thePlayer.isUsingItem() && mc.thePlayer.getItemInUseCount() >= 1) {
                        PacketUtil.sendPacketNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    }
                }
                break;
            }
        }
    }
}
