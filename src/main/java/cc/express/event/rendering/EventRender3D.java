/*
 * Decompiled with CFR 0_132.
 */
package cc.express.event.rendering;

import cc.express.event.api.events.Event;
import net.optifine.shaders.Shaders;

public class EventRender3D
        implements Event {
    public static float ticks;
    public boolean isUsingShaders;

    public EventRender3D() {
        this.isUsingShaders = Shaders.getShaderPackName() != null;
    }

    public EventRender3D(float ticks) {
        EventRender3D.ticks = ticks;
        this.isUsingShaders = Shaders.getShaderPackName() != null;
    }

    public static float getPartialTicks() {
        return ticks;
    }

    public boolean isUsingShaders() {
        return this.isUsingShaders;
    }
}

