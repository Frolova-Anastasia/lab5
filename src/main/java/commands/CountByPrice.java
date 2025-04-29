package commands;

import exceptions.WrongNumberOfArgsException;
import utility.CollectionManager;

import java.util.Objects;

public class CountByPrice implements Command{
    private final CollectionManager collectionManager;

    public CountByPrice(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "count_by_price";
    }

    @Override
    public String getDescription() {
        return " выводит количество элементов, значение поля price которых равно заданному";
    }

    @Override
    public void execute(String commandArgs) {
        try{
            NumberArgsChecker.checkArgs(commandArgs, 1);
            if (!Objects.equals(commandArgs, "null")){
                try {
                    float price = Float.parseFloat(commandArgs);
                    collectionManager.countPrice(price);
                }catch (NumberFormatException e){
                    System.out.println("Аргумент должен быть вещественным числом(дробная часть через точку)");
                }
            }
            else {
                collectionManager.countPrice(null);
            }
        }catch (WrongNumberOfArgsException e){
            System.out.println(e.getMessage() + ": вещественное число - цена продуктов, которых необходимо посчитать");
        }
    }
}

