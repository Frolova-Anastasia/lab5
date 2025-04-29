package commands;

import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;
import utility.Console;
import utility.Invoker;

import java.util.Scanner;

public class Info implements Command {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "показывает информацию о коллекции";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 0);
            collectionManager.printInfo();
        } catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage());
        }
    }
}
