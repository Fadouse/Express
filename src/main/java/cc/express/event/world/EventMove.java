/*
 * Decompiled with CFR 0_132.
 */
package cc.express.event.world;


import cc.express.event.api.events.Event;
import net.minecraft.client.Minecraft;

public class EventMove
        implements Event {
    public static double x;
    public static double y;
    public static double z;
    private final double motionX;
    private final double motionY;
    private final double motionZ;

    public EventMove(double x, double y, double z) {
        EventMove.x = x;
        EventMove.y = y;
        EventMove.z = z;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }

    public double getX() {
        return x;
    }

    public static void setX(double x) {
        EventMove.x = x;
    }

    public double getY() {
        return y;
    }

    public static void setY(double y) {
        EventMove.y = y;
    }

    public double getZ() {
        return z;
    }

    public static void setZ(double z) {
        EventMove.z = z;
    }

    public void setMoveSpeed(double speed) {
        double forward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        double strafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            setX(0.0);
            setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                forward = ((forward > 0.0) ? 1.0 : -1.0);
            }
            double sin = Math.sin(Math.toRadians(yaw + 90.0f));
            double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            setX(forward * speed * cos + strafe * speed * sin);
            setZ(forward * speed * sin - strafe * speed * cos);
        }
    }

}

