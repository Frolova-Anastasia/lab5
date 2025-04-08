package utility;

import exceptions.EndInputException;
import java.util.Scanner;

public class ScriptConsole extends Console {
    private final Console mainConsole;

    public ScriptConsole(Scanner scanner, Console mainConsole) {
        super(scanner, false);
        this.mainConsole = mainConsole;
    }

    @Override
    public String getNextStr() throws EndInputException {
        String input = super.getNextStr();
        mainConsole.write(input);
        return input;
    }

    @Override
    public void write(String text) {
        mainConsole.write(text);
    }
}