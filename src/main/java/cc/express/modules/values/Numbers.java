package cc.express.modules.values;

public class Numbers<N extends Number> extends Value<N> {
    N max,min,inc;
    public Numbers(String name, N val, N min, N max, N inc) {
        super(name);
        this.setValue(val);
        this.max = max;
        this.min = min;
        this.inc = inc;
    }

    public N getMax(){
        return this.max;
    }
    public N getMin(){
        return this.min;
    }
    public N getInc(){return this.inc; }
}
