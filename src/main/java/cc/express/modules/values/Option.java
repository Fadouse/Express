package cc.express.modules.values;


public class Option<B> extends Value<B> {
    public Option(String name, B value){
        super(name);
        this.setValue(value);
    }
}
