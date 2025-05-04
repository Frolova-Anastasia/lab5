package commands;


import utility.*;


import java.io.BufferedReader;
import java.io.FileReader;

import java.util.Stack;

public class ExecuteScript implements Command {
    private final Console console;
    private final Invoker invoker;
    private final InputManager inputManager;
    private final CollectionManager collectionManager;
    private static final Stack<String> fileStack = new Stack<>();
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

        // Проверка рекурсии
        if (fileStack.contains(fileName)) {
            console.write("Ошибка: Обнаружена рекурсия в файле " + fileName);
            return;
        }

        if (currentRecursionDepth >= MAX_RECURSION_DEPTH) {
            console.write("Ошибка: Превышена максимальная глубина вложенности (" + MAX_RECURSION_DEPTH + ")");
            return;
        }

        fileStack.push(fileName);
        currentRecursionDepth++;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            inputManager.setScriptReader(reader);

            console.write("Начало выполнения скрипта: " + fileName);

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                try {
                    console.write(">" + line);
                    invoker.executeCommand(line);
                } catch (Exception e) {
                    console.write("Ошибка при выполнении команды '" + line + "': " + e.getMessage());
                    // Продолжаем выполнение скрипта после ошибки
                }
            }
            console.write("Скрипт " + fileName + " выполнен успешно");
            inputManager.setScriptReader(null);
        } catch (Exception e) {
            console.write("Ошибка выполнения скрипта: " + e.getMessage());
        } finally {
            inputManager.setScriptReader(null);
            fileStack.pop();
            currentRecursionDepth--;
        }
    }
}


