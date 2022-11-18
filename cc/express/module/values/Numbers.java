package cc.express.module.values;

public class Numbers extends Value<Double> {
    double max,min,inc;
    public Numbers(String name, double val, double min, double max, double inc) {
        super(name);
        this.setValue(val);
        this.max = max;
        this.min = min;
        this.inc = inc;
    }

    public Double getMax(){
        return this.max;
    }
    public Double getMin(){return this.min;}
    public Double getInc(){return this.inc;}
}
