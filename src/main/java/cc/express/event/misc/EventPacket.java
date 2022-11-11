package cc.express.event.misc;

import cc.express.event.api.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacket extends EventCancellable {

    private final boolean outGoing;
    private Packet packet;

    public EventPacket(Packet packet, boolean outGoing) {
        this.packet = packet;
        this.outGoing = outGoing;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public boolean isOutGoing() {
        return outGoing;
    }

    public boolean isInComing() {
        return !outGoing;
    }
}
