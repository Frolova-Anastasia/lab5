package utility;

import exceptions.EndInputException;
import java.util.Scanner;

public class Console {
    protected final Scanner scanner;

    public Console(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean hasNext() {
        return scanner.hasNextLine();
    }

    public String getNextStr() throws EndInputException {
        try {
            if (!hasNext()) {
                throw new EndInputException("Обнаружен конец ввода ");
            }
            return scanner.nextLine().strip();
        } catch (IllegalStateException e) {
            throw new EndInputException("Ошибка чтения ввода: " + e.getMessage());
        }
    }

    public void write(String text) {
        System.out.println(text);
    }


}