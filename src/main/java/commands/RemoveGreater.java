package commands;

import data.Product;
import exceptions.EndInputException;
import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;
import utility.Console;
import utility.InputManager;

public class RemoveGreater implements Command{
    private final CollectionManager collectionManager;
    private final Console console;

    public RemoveGreater(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "удаляет из коллекции все элементы, превышающие заданный";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 0);
            InputManager inputManager = new InputManager(collectionManager, console);
            Product product = inputManager.getProduct();
            collectionManager.removeGreater(product);
        } catch (WrongNumberOfArgsException | EndInputException e){
            System.out.println(e.getMessage());
        }
    }
}
