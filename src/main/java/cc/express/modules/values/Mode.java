package cc.express.modules.values;


public class Mode<V extends Enum<?>> extends Value<V> {
    private final V[] modes;

    public Mode(String name, V[] modes, V value) {
        super(name);
        this.modes = modes;
        this.setValue(value);
    }

    public V[] getModes() {
        return this.modes;
    }

    public void setMode(String mode) {
        V[] arrV = this.modes;
        int n = arrV.length;
        int n2 = 0;
        while (n2 < n) {
            V e = arrV[n2];
            if (e.name().equalsIgnoreCase(mode)) {
                this.setValue(e);
            }
            ++n2;
        }
    }

    public boolean isValid(String name) {
        V[] arrV = this.modes;
        int n = arrV.length;
        int n2 = 0;
        while (n2 < n) {
            V e = arrV[n2];
            if (e.name().equalsIgnoreCase(name)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

}

