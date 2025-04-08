package commands;

import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;

public class PrintUniqueManufacturer implements Command{
    private final CollectionManager collectionManager;

    public PrintUniqueManufacturer(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "print_unique_manufacturer";
    }

    @Override
    public String getDescription() {
        return "выводит уникальные значения поля manufacturer всех элементов в коллекции";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 0);
            collectionManager.print_unique_manufacturer();
        } catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage() + "ов");
        }
    }
}
