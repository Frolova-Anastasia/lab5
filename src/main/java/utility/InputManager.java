package utility;

import data.*;
import exceptions.EndInputException;

import java.time.ZonedDateTime;

public class InputManager {
    private final CollectionManager collectionManager;
    private final Console console;
    private Integer nextId = 1;


    public InputManager(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.console = console;
    }


    public Integer generateNextId() {
        while(collectionManager.existId(nextId)){
            nextId++;
        }
        return nextId++;
    }


    public Product getProduct() throws EndInputException {

        Integer id = generateNextId();

        String name = getRightString("Введите название продукта >", 1);

        int x = getInt("Введите координату x >", 931);
        Long y = getLong("Введите координату y >");
        Coordinates coordinates = new Coordinates(x, y);

        Float price = (Float)getFloatOrNull("Введите цену в виде вещественного числа через точку >", 0);

        UnitOfMeasure unitOfMeasure = getUnitOfMeasure();

        String nameOrg = getRightString("Введите название организации >", 1);
        String nameFullOrg = getRightString("Введите полное название организации >", 0);
        OrganizationType orgType = getOrganizationType();
        int orgId = generateNextId();
        Organization organization = new Organization(nameOrg, nameFullOrg, orgType, orgId);

        ZonedDateTime time = ZonedDateTime.now();

        return new Product(id, name, coordinates, price, unitOfMeasure, organization, time);
    }

    public int getInt(String prompt, int maxValue) throws EndInputException {
        while (true) {
            try {
                // Шаг 1: Выводим приглашение и получаем ввод
                console.write(prompt);
                String input = console.getNextStr();

                // Шаг 2: Проверка на команду exit (безопасная)
                if ("exit".equalsIgnoreCase(input)) {
                    System.exit(0);
                }

                // Шаг 3: Проверка на пустую строку
                if (input.isEmpty()) {
                    console.write("Ошибка: поле не может быть пустым");
                    continue;
                }

                // Шаг 4: Парсинг числа
                int value = Integer.parseInt(input);

                // Шаг 5: Проверка максимального значения
                if (value >= maxValue) {
                    console.write("Число должно быть меньше " + maxValue);
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                console.write("Неверный ввод: требуется целое число");
            }
            // Цикл продолжается, пока не получим корректное число
        }
    }


    public Object getFloatOrNull(String text, int val) throws EndInputException {
        console.write(text);
        while(console.hasNext()){
            String str = console.getNextStr();
            if(str.equals("exit")){
                System.exit(0);
            }
            if(str.isEmpty()) return null;
            try {
                float numb = Float.parseFloat(str);
                if (numb > val) return numb;
                console.write("Число должно быть больше " + val + ". " + text);
            }catch (NumberFormatException e){
                console.write("Неверный ввод: введите число. " + text);
            }
        }
        throw new EndInputException("");
    }

    public Long getLong(String text) throws EndInputException {
        console.write(text);
        while(console.hasNext()){
            String str = console.getNextStr();
            if(str.equals("exit")){
                System.exit(0);}
            try {
                return Long.parseLong(str);
            }catch (NumberFormatException e){
                console.write("Неверный ввод: введите целое число. " + text);
            }
        }
        throw new EndInputException("");
    }

    private String getRightString(String text, int minLength) throws EndInputException{
        console.write(text);
        while(console.hasNext()){
            String str = console.getNextStr();
            if(str.equals("exit")) {
                System.exit(0);
            }
            if(!str.isBlank() && str.length() >= minLength){
                return str;
            }else {console.write("Значение не может быть пустым");}
            console.write(text);
        }
        throw new EndInputException("");
    }

    public OrganizationType getOrganizationType() throws EndInputException{
        StringBuilder text = new StringBuilder("Введите тип организации из доступных вариантов: ");
        for(OrganizationType element : OrganizationType.values()){
            text.append(element).append(" ");
        }
        console.write(String.valueOf(text));
        String str = "";
        console.write(str);
        while(console.hasNext()){
            str = console.getNextStr();
            if(str.equals("exit")){
                System.exit(0);
            }
            if(str.isEmpty()) return null;
            try{
                return OrganizationType.valueOf(str);
            }catch (IllegalArgumentException e){
                console.write("Неизвестный тип организации");
            }
        }
        throw new EndInputException("");
    }

    public UnitOfMeasure getUnitOfMeasure() throws EndInputException{
        StringBuilder text = new StringBuilder("Введите единицу измерения из доступных вариантов: ");
        for(UnitOfMeasure element : UnitOfMeasure.values()){
            text.append(element).append(" ");
        }
        console.write(String.valueOf(text));
        String str = "";
        console.write(str);
        while(console.hasNext()){
            str = console.getNextStr();
            if(str.equals("exit")){
                System.exit(0);
            }
            if(str.isEmpty()) return null;
            try{
                return UnitOfMeasure.valueOf(str);
            }catch (IllegalArgumentException e){
                console.write("Неизвестная мера измерения");
            }
        }
        throw new EndInputException("");
    }

    public Product updateProduct(int existId) throws EndInputException {

        // Получаем существующий продукт
        Product existingProduct = collectionManager.getProductById(existId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Продукт с ID " + existId + " не найден");
        }


        Product updatedProduct = new Product(
                existId,
                existingProduct.getName(),
                existingProduct.getCoordinates(),
                existingProduct.getPrice(),
                existingProduct.getUnitOfMeasure(),
                existingProduct.getManufacturer(),
                existingProduct.getCreationDate()
        );

        String name = getRightString("Обновите название продукта >", 1);
        updatedProduct.setName(name);

        int x = getInt("Обновите координату x >", 931);
        Long y = getLong("Обновите координату y >");
        Coordinates coordinates = new Coordinates(x, y);
        updatedProduct.setCoordinates(coordinates);

        Float price = (Float)getFloatOrNull("Обновите цену в виде вещественного числа через точку >", 0);
        updatedProduct.setPrice(price);

        UnitOfMeasure unitOfMeasure = getUnitOfMeasure();
        updatedProduct.setUnitOfMeasure(unitOfMeasure);

        String nameOrg = getRightString("Обновите название организации >", 1);
        String nameFullOrg = getRightString("Обновите полное название организации >", 0);
        OrganizationType orgType = getOrganizationType();
        int orgId = generateNextId();
        Organization organization = new Organization(nameOrg, nameFullOrg, orgType, orgId);
        updatedProduct.setOrganization(organization);

        ZonedDateTime time = ZonedDateTime.now();
        updatedProduct.setCreationDate(time);

        return updatedProduct;
    }

    public Organization getOrganization() throws EndInputException{
        String nameOrg = getRightString("Введите название организации >", 1);
        String nameFullOrg = getRightString("Введите полное название организации >", 0);
        OrganizationType orgType = getOrganizationType();
        int orgId = generateNextId();
        return new Organization(nameOrg, nameFullOrg, orgType, orgId);
    }
}
