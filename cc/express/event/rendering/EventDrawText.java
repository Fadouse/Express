package cc.express.event.rendering;

import cc.express.event.api.events.Event;

public class EventDrawText implements Event {
    public String text;

    public EventDrawText(String text) {
        this.text = text;
    }
}
