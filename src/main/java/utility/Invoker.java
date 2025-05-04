package utility;

import commands.*;
import exceptions.EndInputException;

import java.util.HashMap;
import java.util.Map;


public class Invoker {
    private final Console console;
    private final CollectionManager collectionManager;
    private final InputManager inputManager;
    Map<String, Command> commands = new HashMap<>(); //ключ-имя команды, значение-объект команды

    public Invoker(Console console, CollectionManager collectionManager, InputManager inputManager) {
        this.console = console;
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
        registerCommands();
    }

    public void registerCommands(){
        commands.put("help", new Help(console, collectionManager, inputManager));
        commands.put("add", new Add(collectionManager, console, inputManager));
        commands.put("info", new Info(collectionManager));
        commands.put("show", new Show(collectionManager));
        commands.put("clear", new Clear(collectionManager));
        commands.put("exit", new Exit());
        commands.put("shuffle", new Shuffle(collectionManager));
        commands.put("update", new Update(collectionManager, console, inputManager));
        commands.put("remove", new Remove(collectionManager));
        commands.put("save", new Save(collectionManager));
        commands.put("insert", new Insert(collectionManager, console, inputManager));
        commands.put("remove_greater", new RemoveGreater(collectionManager, console, inputManager));
        commands.put("count_by_price", new CountByPrice(collectionManager));
        commands.put("filter_greater_than_manufacturer", new FilterGreaterManufacturer(collectionManager, console));
        commands.put("print_unique_manufacturer", new PrintUniqueManufacturer(collectionManager));
        commands.put("execute_script", new ExecuteScript(console, this, inputManager, collectionManager));
    }

    public void start() {
        console.write("Напишите help для получения справки по командам >");
        while (true) {
            try {
                String line = console.getNextStr();
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
                executeCommand(line);
            } catch (EndInputException e) {
                console.write(e.getMessage());
                break;
            }
        }
    }

    public void executeCommand(String commandLine) {
        String[] tokens = commandLine.split("\\s+", 2);
        String commandName = tokens[0];
        String commandArg = (tokens.length > 1) ? tokens[1] : null;

        Command command = commands.get(commandName);
        if (command == null) {
            console.write("Неизвестная команда: " + commandName);
            return;
        }

        try {
            command.execute(commandArg);
        } catch (Exception e) {
            console.write("Ошибка при выполнении команды: " + e.getMessage());
        }
    }


    public HashMap<String, Command> getCommands(){
        return new HashMap<>(commands);
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
