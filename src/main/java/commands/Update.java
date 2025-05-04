package commands;

import data.Product;
import exceptions.EndInputException;
import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;
import utility.Console;
import utility.InputManager;

public class Update implements Command {
    private final CollectionManager collectionManager;
    private final Console console;
    private final InputManager inputManager;

    public Update(CollectionManager collectionManager, Console console, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.inputManager = inputManager;
    }

    @Override
    public String getName() {
        return "update id";
    }

    @Override
    public String getDescription() {
        return "обновляет значения продукта по id";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 1);
            int id = Integer.parseInt(commandArgs);
            if (id < 0 || !collectionManager.existId(id)) {
                console.write("Нет продукта с таким id: ");
                collectionManager.printProductIdsAndNames();
            }else{
            Product product = inputManager.updateProduct(id);
            collectionManager.update(id, product);
            System.out.println("Продукт с ID: " + product.getId() + " обновлен");}
        }catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage() + ": целое число - id продукта, который необходимо обновить");
        }catch (NumberFormatException e){
        System.out.println("Аргумент должен быть целым числом");
        }catch (EndInputException e){
            System.out.println(e.getMessage());
        }
    }
}


