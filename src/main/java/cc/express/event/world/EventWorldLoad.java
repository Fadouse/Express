package cc.express.event.world;

import cc.express.event.api.events.Event;
import net.minecraft.client.multiplayer.WorldClient;

public class EventWorldLoad implements Event {

    private final WorldClient world;

    public EventWorldLoad(WorldClient world) {
        this.world = world;
    }

    public WorldClient getWorld() {
        return world;
    }
}
