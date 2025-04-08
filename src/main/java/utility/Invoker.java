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
        commands.put("update", new Update(collectionManager, console));
        commands.put("remove", new Remove(collectionManager));
        commands.put("save", new Save(collectionManager));
        commands.put("insert", new Insert(collectionManager, console));
        commands.put("remove_greater", new RemoveGreater(collectionManager, console));
        commands.put("count_by_price", new CountByPrice(collectionManager));
        commands.put("filter_greater_than_manufacturer", new FilterGreaterManufacturer(collectionManager, console));
        commands.put("print_unique_manufacturer", new PrintUniqueManufacturer(collectionManager));
        commands.put("execute_script", new ExecuteScript(console, this, inputManager, collectionManager));
    }

    public void consoleReader() {
        console.write("Напишите help для получения справки по командам >");
        while (true) {
            try {
                String line = console.getNextStr();
                if (line == null) {
                    if (console.isInteractive()) continue;
                    break;
                }
                String[] tokens = line.split("\\s+", 2);
                String commandName = tokens[0];
                String commandArg = (tokens.length > 1) ? tokens[1] : null;
                executeCommand(commandName, commandArg);
            } catch (EndInputException e) {
                if (console.isInteractive()) {
                    console.write(e.getMessage());
                    console.write("Завершение работы...");
                    System.exit(0);
                } else {
                    console.write(e.getMessage());
                    break;
                }
            }
        }
    }

    public void executeCommand(String CommandName, String CommandArgs){
        if (CommandName == null || CommandName.isEmpty()) {
            return;
        }
        Command command = commands.get(CommandName);
        if(command == null){
            console.write("Неизвестная команда: " + CommandName);
       }else{
            command.execute(CommandArgs);
        }
    }


    public HashMap<String, Command> getCommands(){
        return new HashMap<>(commands);
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
