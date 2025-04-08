package commands;

import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;

public class Show implements Command{
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "выводит элементы коллекции";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 0);
            collectionManager.printProducts();
        } catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage());
        }
    }
}
