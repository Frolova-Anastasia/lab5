package commands;

import exceptions.ScriptExitException;

public interface Command {
    String getName();
    String getDescription();
    void execute(String commandArgs) throws ScriptExitException;
}
