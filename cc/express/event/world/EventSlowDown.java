package cc.express.event.world;

import cc.express.event.api.events.callables.EventCancellable;

public class EventSlowDown extends EventCancellable {
    private final Type type;

    public EventSlowDown(final Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        Item, Sprinting, SoulSand, Water
    }
}
