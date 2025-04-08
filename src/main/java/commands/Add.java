package commands;

import data.Product;
import exceptions.EndInputException;
import exceptions.NotUniqueIdException;
import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;
import utility.Console;
import utility.InputManager;
import utility.ScriptConsole;

public class Add implements Command{
    private final CollectionManager collectionManager;
    private final Console console;
    private final InputManager inputManager;

    public Add(CollectionManager collectionManager, Console console, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.inputManager = new InputManager(collectionManager, console);
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавляет элемент в коллекцию";
    }

    @Override
    public void execute(String commandArgs) {
        try {
            NumberArgsChecker.checkArgs(commandArgs, 0);

            // Устанавливаем режим скрипта, если консоль является ScriptConsole
            if (console instanceof ScriptConsole) {
                inputManager.setScriptMode(true, ((ScriptConsole)console).getScanner());
            }

            Product product = inputManager.getProduct();
            collectionManager.add(product);
            console.write("Продукт успешно добавлен с ID: " + product.getId());
        } catch (WrongNumberOfArgsException | NotUniqueIdException e) {
            console.write("Ошибка: " + e.getMessage());
        } catch (EndInputException e) {
            console.write("Выход выполнен");
        } finally {
            inputManager.setScriptMode(false, null);
        }
    }
}
