package utility;

import data.*;
import exceptions.EndInputException;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class InputManager {
    private final CollectionManager collectionManager;
    private final Console console;
    private Integer nextId = 1;
    private boolean scriptMode = false;
    private Scanner scriptScanner;

    public InputManager(CollectionManager collectionManager, Console console) {
        this.collectionManager = collectionManager;
        this.console = console;
    }
    public void setScriptMode(boolean scriptMode, Scanner scriptScanner) {
        this.scriptMode = scriptMode;
        this.scriptScanner = scriptScanner;
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

    public int getInt(String text, int maxValue) throws EndInputException {
        while (true) {
            try {
                if (!scriptMode) {
                    console.write(text);
                }

                String input;
                if (scriptMode) {
                    if (!scriptScanner.hasNextLine()) {
                        throw new EndInputException("Неожиданный конец скрипта");
                    }
                    input = scriptScanner.nextLine().trim();
                    console.write(text + " " + input);
                } else {
                    input = console.getNextStr();
                }

                if ("exit".equals(input)) {
                    System.exit(0);
                }
                if (input.isEmpty()) {
                    if (scriptMode) {
                        throw new EndInputException("Пустое значение не допускается");
                    }
                    console.write("Поле не может быть пустым");
                    continue;
                }

                int value = Integer.parseInt(input);
                if (value < maxValue) {
                    return value;
                }

                String errorMsg = "Число должно быть меньше " + maxValue;
                if (scriptMode) {
                    throw new EndInputException(errorMsg);
                }
                console.write(errorMsg + ". " + text);

            } catch (NumberFormatException e) {
                String errorMsg = "Неверный ввод: требуется целое число";
                if (scriptMode) {
                    throw new EndInputException(errorMsg);
                }
                console.write(errorMsg + ". " + text);
            }
        }
    }

    public Object getFloatOrNull(String text, float minValue) throws EndInputException {
        while (true) {
            try {
                if (!scriptMode) {
                    console.write(text);
                }
                String input;
                if (scriptMode) {
                    if (!scriptScanner.hasNextLine()) {
                        throw new EndInputException("Неожиданный конец скрипта");
                    }
                    input = scriptScanner.nextLine().trim();
                    console.write(text + " " + input);
                    if (input == null) {
                        return null;
                    }
                } else {
                    input = console.getNextStr();
                }

                if ("exit".equals(input)) {
                    System.exit(0);
                }

                if (input == null || input.isEmpty()) {
                    return null;
                }

                float value = Float.parseFloat(input);
                if (value > minValue) {
                    return value;
                }

                String errorMsg = "Число должно быть больше " + minValue;
                if (scriptMode) {
                    throw new EndInputException(errorMsg);
                }
                console.write(errorMsg + ". " + text);

            } catch (NumberFormatException e) {
                String errorMsg = "Неверный ввод: требуется число с плавающей точкой";
                if (scriptMode) {
                    throw new EndInputException(errorMsg);
                }
                console.write(errorMsg + ". " + text);
            }
        }
    }

    public Long getLong(String text) throws EndInputException {
        while (true) {
            try {
                if (!scriptMode) {
                    console.write(text);
                }
                String input;
                if (scriptMode) {
                    if (!scriptScanner.hasNextLine()) {
                        throw new EndInputException("Неожиданный конец скрипта");
                    }
                    input = scriptScanner.nextLine().trim();
                    console.write(text + " " + input);
                } else {
                    input = console.getNextStr();
                }

                if ("exit".equals(input)) {
                    System.exit(0);
                }

                if (input.isEmpty()) {
                    if (scriptMode) {
                        throw new EndInputException("Пустое значение не допускается");
                    }
                    console.write("Поле не может быть пустым");
                    continue;
                }

                return Long.parseLong(input);

            } catch (NumberFormatException e) {
                String errorMsg = "Неверный ввод: требуется целое число";
                if (scriptMode) {
                    throw new EndInputException(errorMsg);
                }
                console.write(errorMsg + ". " + text);
            }
        }
    }
    public String getRightString(String text, int minLength) throws EndInputException {
        while (true) {
            try {
                if (console.isInteractive()) {
                    console.write(text);
                }
                String input = console.getNextStr();
                if (input == null) {
                    if (console.isInteractive()) {
                        console.write("Это поле не может быть пустым");
                        continue;
                    }
                    throw new EndInputException("Неожиданный конец ввода");
                }
                if (input.equals("exit")) {
                    System.exit(0);
                }
                if (input.length() >= minLength) {
                    return input;
                }
                if (console.isInteractive()) {
                    console.write("Ошибка: минимальная длина - " + minLength);
                } else {
                    throw new EndInputException("Некорректное значение в скрипте");
                }
            } catch (EndInputException e) {
                if (!console.isInteractive()) throw e;
                console.write("Ошибка: " + e.getMessage());
            }
        }
    }
    public OrganizationType getOrganizationType() throws EndInputException {
        StringBuilder text = new StringBuilder("Введите тип организации (");
        for (OrganizationType type : OrganizationType.values()) {
            text.append(type).append(", ");
        }
        text.setLength(text.length() - 2); // Удаляем последнюю запятую и пробел
        text.append("):");

        while (true) {
            try {
                if (!scriptMode) {
                    console.write(text.toString());
                }
                String input;
                if (scriptMode) {
                    if (!scriptScanner.hasNextLine()) {
                        throw new EndInputException("Неожиданный конец скрипта при вводе типа организации");
                    }
                    input = scriptScanner.nextLine().trim();
                    console.write(text + " " + input);
                } else {
                    input = console.getNextStr();
                }

                if ("exit".equals(input)) {
                    System.exit(0);
                }
                if (input == null || input.isEmpty()) {
                    return null;
                }

                try {
                    return OrganizationType.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    // Проверяем ввод без учета регистра, если в интерактивном режиме
                    if (!scriptMode) {
                        for (OrganizationType type : OrganizationType.values()) {
                            if (type.name().equalsIgnoreCase(input)) {
                                return type;
                            }
                        }
                    }
                    throw new IllegalArgumentException("Неизвестный тип организации: " + input);
                }

            } catch (IllegalArgumentException e) {
                if (scriptMode) {
                    throw new EndInputException(e.getMessage());
                }
                console.write("Ошибка: " + e.getMessage());
                console.write("Доступные варианты: " + Arrays.toString(OrganizationType.values()));
            }
        }
    }

    public UnitOfMeasure getUnitOfMeasure() throws EndInputException {
        StringBuilder text = new StringBuilder("Введите единицу измерения (");
        for (UnitOfMeasure unit : UnitOfMeasure.values()) {
            text.append(unit).append(", ");
        }
        text.setLength(text.length() - 2); // Удаляем последнюю запятую и пробел
        text.append("):");

        while (true) {
            try {

                if (!scriptMode) {
                    console.write(text.toString());
                }

                String input;
                if (scriptMode) {
                    if (!scriptScanner.hasNextLine()) {
                        throw new EndInputException("Неожиданный конец скрипта при вводе единицы измерения");
                    }
                    input = scriptScanner.nextLine().trim();
                    console.write(text + " " + input);
                } else {
                    input = console.getNextStr();
                }

                if ("exit".equals(input)) {
                    System.exit(0);
                }

                if (input == null || input.isEmpty()) {
                    return null;
                }

                try {
                    return UnitOfMeasure.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    if (!scriptMode) {
                        for (UnitOfMeasure unit : UnitOfMeasure.values()) {
                            if (unit.name().equalsIgnoreCase(input)) {
                                return unit;
                            }
                        }
                    }
                    throw new IllegalArgumentException("Неизвестная единица измерения: " + input);
                }

            } catch (IllegalArgumentException e) {
                if (scriptMode) {
                    throw new EndInputException(e.getMessage());
                }
                console.write("Ошибка: " + e.getMessage());
                console.write("Доступные варианты: " + Arrays.toString(UnitOfMeasure.values()));
            }
        }
    }
    public Product updateProduct(int existId) throws EndInputException {

        // Получаем существующий продукт
        Product existingProduct = collectionManager.getProductById(existId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Продукт с ID " + existId + " не найден");
        }

        // Создаем копию существующего продукта
        Product updatedProduct = new Product(
                existId, // Сохраняем тот же ID
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
