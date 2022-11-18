package cc.express.event.world;

import cc.express.event.api.events.callables.EventCancellable;

public class EventSafeWalk extends EventCancellable {

    public EventSafeWalk(boolean safeWalking) {
        setCancelled(safeWalking);
    }
}
