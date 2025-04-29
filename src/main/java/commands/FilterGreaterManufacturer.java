package commands;

import data.Organization;
import exceptions.EndInputException;
import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;
import utility.Console;
import utility.InputManager;

public class FilterGreaterManufacturer implements Command{
    private final CollectionManager collectionManager;
    private final Console console;

    public FilterGreaterManufacturer(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public String getName() {
        return "filter_greater_than_manufacturer";
    }

    @Override
    public String getDescription() {
        return "выводит элементы, значение поля manufacturer которых больше заданного";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 0);
            InputManager inputManager = new InputManager(collectionManager, console);
            Organization manufacturer = inputManager.getOrganization();
            collectionManager.filterGreaterThanManufacture(manufacturer);
        } catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage() + "ов" + "\nВвод необходимых поле для Organization будет предложен отдельно");
        }catch (EndInputException e){
            System.out.println(e.getMessage());
        }

    }
}
