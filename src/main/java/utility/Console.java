package utility;

import exceptions.EndInputException;
import java.util.Scanner;

public class Console {
    protected final Scanner scanner;
    private final boolean isInteractive;

    public Console(Scanner scanner, boolean isInteractive) {
        this.scanner = scanner;
        this.isInteractive = isInteractive;
    }

    public boolean hasNext() {
        return scanner.hasNextLine();
    }

    public String getNextStr() throws EndInputException {
        try {
            if (!hasNext()) {
                throw new EndInputException(isInteractive ?
                        "Обнаружен конец ввода " : "Достигнут конец файла скрипта");
            }
            String input = scanner.nextLine().strip();
            if (isInteractive && input.isEmpty()) {
                return null;
            }
            return input;
        } catch (IllegalStateException e) {
            throw new EndInputException("Ошибка чтения ввода: " + e.getMessage());
        }
    }

    public void write(String text) {
        System.out.println(text);
    }

    public boolean isInteractive() {
        return isInteractive;
    }

    public Scanner getScanner() {
        return scanner;
    }
}