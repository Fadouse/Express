/*
 * Decompiled with CFR 0_132.
 */
package cc.express.event.world;

import cc.express.event.api.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacketSend
        extends EventCancellable {
    public Packet packet;

    public EventPacketSend(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}

