package cc.express.event.events;

import cc.express.event.Event;

public class KeyInputEvent extends Event {

    int keyCode;

    public KeyInputEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKey() {
        return keyCode;
    }
}
