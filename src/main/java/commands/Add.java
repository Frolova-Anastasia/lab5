package commands;

import data.Product;
import exceptions.EndInputException;
import exceptions.NotUniqueIdException;
import exceptions.ScriptExitException;
import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;
import utility.Console;
import utility.InputManager;

public class Add implements Command{
    private final CollectionManager collectionManager;
    private final Console console;
    private final InputManager inputManager;

    public Add(CollectionManager collectionManager, Console console, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.console = console;
        this.inputManager = inputManager;
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
    public void execute(String commandArgs) throws ScriptExitException {
        try {
            NumberArgsChecker.checkArgs(commandArgs, 0);

            Product product = inputManager.getProduct();
            collectionManager.add(product);
            console.write("Продукт успешно добавлен с ID: " + product.getId());
        } catch (ScriptExitException e) {
            throw e;
        } catch (EndInputException e) {
            console.write("Выход выполнен");
            if (inputManager.isScriptMode()) {
                throw new ScriptExitException("Прервано из-за ошибки ввода");
        }
        } catch (WrongNumberOfArgsException | NotUniqueIdException e) {
            console.write("Ошибка: " + e.getMessage());
        }
        }
    }

