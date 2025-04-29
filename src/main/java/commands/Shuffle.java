package commands;

import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;

public class Shuffle implements Command{
    private final CollectionManager collectionManager;

    public Shuffle(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getDescription() {
        return "перемешивает элементы коллекции в случайном порядке";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 0);
            collectionManager.shuffle(collectionManager.getProducts());
        } catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage());
        }
    }
}
