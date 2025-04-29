package commands;


import utility.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ExecuteScript implements Command {
    private final Console console;
    private final Invoker invoker;
    private final InputManager inputManager;
    private final CollectionManager collectionManager;
    private static final Set<String> callSet = new HashSet<>();
    private static final int MAX_RECURSION_DEPTH = 10;
    private static int currentRecursionDepth = 0;

    public ExecuteScript(Console console, Invoker invoker, InputManager inputManager, CollectionManager collectionManager) {
        this.console = console;
        this.invoker = invoker;
        this.inputManager = inputManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "Считывает и исполняет скрипт из указанного файла(Команды должны содержаться в таком же виде, в котором их вводит пользователь в интерактивном режиме)";
    }

    @Override
    public void execute(String args) {
        if (args == null || args.trim().isEmpty()) {
            console.write("Ошибка: Не указано имя файла скрипта");
            return;
        }
        String fileName = args.trim();
        if (currentRecursionDepth >= MAX_RECURSION_DEPTH) {
            console.write("Ошибка: Превышена максимальная глубина вложенности скриптов (" + MAX_RECURSION_DEPTH + ")");
            return;
        }
        if (callSet.contains(fileName)) {
            console.write("Обнаружена рекурсия! Файл " + fileName + " уже выполняется");
            return;
        }
        try {
            callSet.add(fileName);
            currentRecursionDepth++;

            File scriptFile = new File(fileName);
            if (!scriptFile.exists()) {
                console.write("Файл скрипта не существует: " + fileName);
                return;
            }
            try (Scanner fileScanner = new Scanner(new File(fileName))) {
                //ScriptConsole scriptConsole = new ScriptConsole(fileScanner, console);
                //Invoker scriptInvoker = new Invoker(scriptConsole, collectionManager, inputManager);
                //scriptInvoker.getCommands().putAll(invoker.getCommands()); //копирование команд из интерактивного режима
                //scriptInvoker.consoleReader();

            } catch (FileNotFoundException e) {
                console.write("Файл скрипта не найден");
            } catch (Exception e) {
                console.write("Ошибка выполнения скрипта: " + e.getMessage());
            }

        } finally {
            callSet.remove(fileName);
            currentRecursionDepth--;
        }
    }
}
