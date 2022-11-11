package cc.express.command;

public abstract class Command {
    String[] name;

    public Command(String[] name){
        this.name = name;
    }

    public String[] getNames() {
        return name;
    }

    public abstract void run(String[] args);
}
