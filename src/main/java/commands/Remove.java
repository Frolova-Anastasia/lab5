package commands;

import data.Product;
import exceptions.EndInputException;
import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;

public class Remove implements Command{
    private final CollectionManager collectionManager;

    public Remove(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "удаляет продукт по id";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 1);
            int id = Integer.parseInt(commandArgs);
            if (id < 0 || !collectionManager.existId(id)) {
                System.out.println("Нет продукта с таким индексом: ");
                collectionManager.printProductIdsAndNames();
            }else{
                collectionManager.remove(id);
                System.out.println("Продукт с ID: " + id + " удален");}
        }catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage() + ": целое число - id продукта, который необходимо обновить");
        }catch (NumberFormatException e){
            System.out.println("Аргумент должен быть целым числом");
        }
    }
}
