import exceptions.EndInputException;
import utility.CollectionManager;
import utility.Console;
import utility.InputManager;
import utility.Invoker;

import java.util.Scanner;

public class App {
    public static void main( String[] args ) throws EndInputException {
        CollectionManager collectionManager = new CollectionManager();
        Scanner sc = new Scanner(System.in);
        Console console = new Console(sc);
        InputManager inputManager = new InputManager(collectionManager, console);
        Invoker invoker = new Invoker(console, collectionManager, inputManager);
        invoker.start();
    }
}
