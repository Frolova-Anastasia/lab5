package commands;

import data.Product;
import exceptions.EndInputException;
import exceptions.ScriptExitException;
import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;
import utility.Console;
import utility.InputManager;

public class Insert implements Command{
    private final CollectionManager collectionManager;
    private final Console console;
    private final InputManager inputManager;

    public Insert(CollectionManager collectionManager, Console console, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.inputManager = inputManager;
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в заданную позицию";
    }

    @Override
    public void execute(String commandArgs) throws ScriptExitException {
        try {
            NumberArgsChecker.checkArgs(commandArgs, 1);
            int index = Integer.parseInt(commandArgs);
            if (index < 0 || index > collectionManager.getProducts().size()) {
                console.write("Недопустимый индекс: " + collectionManager.getProducts().size() + " - размер коллекции");
            } else {
                Product product = inputManager.getProduct();
                collectionManager.insert(index, product);
                System.out.println("Продукт успешно добавлен в " + index + " позицию");
            }
        } catch (WrongNumberOfArgsException e) {
            System.out.println(e.getMessage() + ": целое число - индекс коллекции, куда добавить продукт");
        } catch (EndInputException e) {
            System.out.println(e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("Аргумент должен быть целым числом");
        } catch (ScriptExitException e) {
            throw e;
        }
    }
}
