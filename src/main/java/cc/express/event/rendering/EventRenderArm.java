package cc.express.event.rendering;

import cc.express.event.api.events.callables.EventCancellable;
import net.minecraft.entity.Entity;

public class EventRenderArm extends EventCancellable {

    private final Entity entity;
    private final boolean pre;

    public EventRenderArm(Entity entity, boolean pre) {
        this.entity = entity;
        this.pre = pre;
    }

    public boolean isPre() {
        return pre;
    }

    public boolean isPost() {
        return !pre;
    }

    public Entity getEntity() {
        return entity;
    }
}
