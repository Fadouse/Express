package cc.express.event.world;


import cc.express.event.api.events.Event;

public class EventStep implements Event {
    private double stepHeight;
    private final boolean pre;

    public EventStep(double stepHeight, boolean pre) {
        this.stepHeight = stepHeight;
        this.pre = pre;
    }

    public double getStepHeight() {
        return stepHeight;
    }

    public void setStepHeight(double stepHeight) {
        this.stepHeight = stepHeight;
    }

    public boolean isPre() {
        return pre;
    }

    public boolean isPost() {
        return !pre;
    }
}
