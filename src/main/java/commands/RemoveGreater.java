package commands;

import data.Product;
import exceptions.EndInputException;
import exceptions.ScriptExitException;
import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;
import utility.Console;
import utility.InputManager;

public class RemoveGreater implements Command{
    private final CollectionManager collectionManager;
    private final Console console;
    private final InputManager inputManager;

    public RemoveGreater(CollectionManager collectionManager, Console console, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.inputManager = inputManager;
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
    public void execute(String commandArgs) throws ScriptExitException {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 0);
            Product product = inputManager.getProduct();
            collectionManager.removeGreater(product);
        } catch (WrongNumberOfArgsException | EndInputException e){
            System.out.println(e.getMessage());
        } catch (ScriptExitException e) {
            throw e;
        }
    }
}
