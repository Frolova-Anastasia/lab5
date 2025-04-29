package commands;

import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;

public class Save implements Command{
    private final CollectionManager collectionManager;

    public Save(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохраняет коллекцию в файл";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 0);
            collectionManager.saveToFile();
        } catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage());
        }
    }
}
