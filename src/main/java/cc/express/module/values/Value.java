package cc.express.module.values;

import cc.express.event.events.ValueEvent;
import cc.express.event.EventBus;

public class Value<V> {
    String name;
    public V value;

    public Value(String name){
        this.name = name;

    }

    public String getName(){
        return this.name;
    }

    public V getValue(){
        return this.value;
    }

    public void setValue(V val){
        ValueEvent e = new ValueEvent(this);
        EventBus.getInstance().call(e);

        if(!e.isCancelled()){
            this.value = val;
        }
    }

    public String getDisplayName() {
        return this.name;
    }
}
