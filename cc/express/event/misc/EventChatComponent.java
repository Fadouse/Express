package cc.express.event.misc;

import cc.express.event.api.events.callables.EventCancellable;
import net.minecraft.util.IChatComponent;

public class EventChatComponent extends EventCancellable {

    private final IChatComponent chatComponent;

    public EventChatComponent(IChatComponent icc) {
        chatComponent = icc;
    }

    public IChatComponent getChatComponent() {
        return chatComponent;
    }
}
