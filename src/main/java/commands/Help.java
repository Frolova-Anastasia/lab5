package commands;

import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;
import utility.Console;
import utility.InputManager;
import utility.Invoker;

import java.util.Scanner;

public class Help implements Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    public Help(Console console, CollectionManager collectionManager, InputManager inputManager) {
        this.console = console;
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }


    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "справка по всем командам";
    }

    @Override
    public void execute(String commandArgs) {
     try{
      NumberArgsChecker.checkArgs(commandArgs, 0);
      Invoker invoker = new Invoker(console, collectionManager, inputManager);
      for (Command c : invoker.getCommands().values()) {
          System.out.println(c.getName() + " " + c.getDescription());
      }
     } catch (WrongNumberOfArgsException e){
      System.out.println(e.getMessage());
     }
    }
}
