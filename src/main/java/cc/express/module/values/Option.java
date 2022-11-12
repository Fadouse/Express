package cc.express.module.values;


public class Option extends Value<Boolean> {
    public Option(String name, Boolean value){
        super(name);
        this.setValue(value);
    }
}
