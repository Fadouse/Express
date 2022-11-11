package cc.express.command;

import cc.express.command.commands.BindCommand;
import cc.express.command.commands.ToggleCommand;

import java.util.ArrayList;

public enum CommandManager {
    instance;

    final ArrayList<Command> commands = new ArrayList<>();

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void init(){
        addCommand(new BindCommand());
        addCommand(new ToggleCommand());
    }

    public void addCommand(Command command){
        commands.add(command);
    }

    public Command getCommand(String name) {
        for(Command c : commands){
            for(String s : c.getNames()){
                if(s.equals(name))
                    return c;
            }
        }
        return null;
    }
}
