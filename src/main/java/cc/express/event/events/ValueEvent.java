package cc.express.event.events;

import cc.express.event.Event;
import cc.express.modules.values.Value;

public class ValueEvent extends Event {
    private final Value<?> value;
    public ValueEvent(Value<?> value){
        this.value = value;
    }

    public Value<?> getValue() {
        return value;
    }
}
