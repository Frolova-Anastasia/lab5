package commands;

import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;

public class Clear implements Command{
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очищает коллекцию";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 0);
            collectionManager.clear();
            System.out.println("Коллекция очищена");
        } catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage());
        }
    }
}
