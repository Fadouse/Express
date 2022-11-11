package cc.express.module.values;


public class Option<B> extends Value<B> {
    public Option(String name, B value){
        super(name);
        this.setValue(value);
    }
}
