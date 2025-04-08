package utility;

import data.*;
import exceptions.NotUniqueIdException;

import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager {
    private final LinkedList<Product> products;
    ZonedDateTime LastInitTime;
    private final HashMap<Integer, Product> idProduct = new HashMap<>(); //ключ - id
    private final FileManager fileManager;

    public CollectionManager() {
        this.fileManager = new FileManager();
        this.products = new LinkedList<>();
        loadFromFile();
    }

    public LinkedList<Product> getProducts() {
        return products;
    }

    public void loadFromFile() {
        Products productsContainer = fileManager.readXml();
        if (productsContainer != null) {
            for (Product product : productsContainer.getProducts()) {
                try {
                    this.add(product);
                    LastInitTime = ZonedDateTime.now();
                } catch (NotUniqueIdException e) {
                    System.out.println(e.getMessage());
                }
            }
            validateCollection();
        }
    }

    public void add(Product product) throws NotUniqueIdException {
        if (existId(product.getId())) {
            throw new NotUniqueIdException("Товар с ID " + product.getId() + " уже существует.");
        }
        idProduct.put(product.getId(), product);
        products.add(product);
    }

    public boolean existId(Integer id) {
        return idProduct.containsKey(id);
    }

    public void validateCollection() {
        List<String> validationErrors = new ArrayList<>();

        for (Product product : products) {
            List<String> productErrors = new ArrayList<>();
            Integer id = product.getId();

            if (id == null || id <= 0) {
                productErrors.add("ID должен быть положительным и не null");
            }

            try {
                product.setName(product.getName());
            } catch (Exception e) {
                productErrors.add("Название: " + e.getMessage());
            }

            try {
                product.setPrice(product.getPrice());
            } catch (Exception e) {
                productErrors.add("Цена: " + e.getMessage());
            }

            try {
                if (product.getCoordinates() == null) {
                    productErrors.add("Координаты не могут быть null");
                } else {
                    product.getCoordinates().validate();
                }
            } catch (Exception e) {
                productErrors.add("Координаты: " + e.getMessage());
            }

            try {
                Organization org = product.getManufacturer();
                if (org != null) {
                    org.validate();
                }
            } catch (Exception e) {
                productErrors.add("Организация: " + e.getMessage());
            }

            // Добавляем ошибки по продукту в общий список
            if (!productErrors.isEmpty()) {
                validationErrors.add("Ошибки в продукте с ID " + id + ":\n - " + String.join("\n - ", productErrors));
            }
        }

        if (!validationErrors.isEmpty()) {
            System.err.println("Обнаружены ошибки при валидации:\n");
            for (String err : validationErrors) {
                System.out.println(err);
                System.out.println();
            }
            System.exit(1);
        } else {
            System.out.println("Валидация успешно пройдена!");
        }
    }


    @Override
    public String toString() {
        return "CollectionManager{" +
                "products=" + products +
                ", idProduct=" + idProduct +
                '}';
    }

    public void printProducts() {
        if (products.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        System.out.println("Содержание коллекции:");
        for (Product product : products) {
            System.out.println(product.toString());
        }
    }


    public void printInfo() {
        System.out.println("Тип данных " + products.getClass().getName());
        System.out.println("Дата инициализации: " + LastInitTime);
        System.out.println("Количество элементов: " + products.size());
    }

    public void clear(){
        idProduct.clear();
        products.clear();
    }

    public void shuffle(LinkedList<Product> products){
        Collections.shuffle(products);
    }

    public Product getProductById(int id) {
        return idProduct.get(id);
    }

    public void update(int id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Продукт с ID " + id + " не найден");
        }

        existingProduct.setName(updatedProduct.getName());

        existingProduct.setCoordinates(updatedProduct.getCoordinates());

        existingProduct.setPrice(updatedProduct.getPrice());

        existingProduct.setOrganization(updatedProduct.getManufacturer());

        existingProduct.setUnitOfMeasure(updatedProduct.getUnitOfMeasure());

        idProduct.put(id, existingProduct);
    }

    public void insert(int index, Product product) {
        try {
            products.add(index, product);
            idProduct.put(product.getId(), product);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Недопустимый индекс");
        }
    }




    public void printProductIdsAndNames() {
        for (Map.Entry<Integer, Product> entry : idProduct.entrySet()) {
            Integer id = entry.getKey(); // Получаем ID
            Product product = entry.getValue(); // Получаем продукт
            System.out.println("ID: " + id + ", Название: " + product.getName());
        }
    }

    public void remove(int id) {
        try{
        Product product1 = idProduct.get(id);
        idProduct.remove(id);
        products.remove(product1);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Недопустимый индекс");
        }
    }

    public void saveToFile() {
        Products productsContainer = new Products();
        productsContainer.setProducts(products);
        fileManager.loadToFile(productsContainer);
        System.out.println("Коллекция успешно сохранена");
    }

    public void removeGreater(Product product) {
        if (product == null) {
            System.out.println("Продукт не может быть null.");
            return;
        }

        Iterator<Product> iterator = products.iterator(); //Создается итератор для обхода коллекции products
        while (iterator.hasNext()) {
            Product currentProduct = iterator.next();
            if (currentProduct.compareTo(product) > 0) {
                iterator.remove();
                idProduct.remove(currentProduct.getId());
            }
        }
        System.out.println("Продукты успешно удалены");
    }

    public void countPrice(float price) {
        int count = 0;
        for (Product product : products) {
            if (product.getPrice() != null && product.getPrice() == price) {
                count = count + 1;
            }
        }
        System.out.println(count);
    }
    public void countPrice(Object price) {
        int count = 0;
        for (Product product : products) {
            if (product.getPrice() == price) {
                count = count + 1;
            }
        }
        System.out.println(count);
    }


    public void filterGreaterThanManufacture(Organization compareManufacturer) {
        if (compareManufacturer == null) {
            products.stream()
                    .filter(product -> product.getManufacturer() != null)
                    .forEach(System.out::println);
            return;
        }

        boolean found = false;
        for (Product product : products) {
            if (product.getManufacturer() != null &&
                    product.getManufacturer().compareTo(compareManufacturer) > 0) {
                System.out.println(product);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Нет продуктов с большим полем manufacturer");
        }
    }

    public void print_unique_manufacturer() {
        if (products.isEmpty()) {
            System.out.println("Коллекция пуста - нет manufacturers для вывода.");
            return;
        }
        Set<Organization> uniqueManufacturers = new HashSet<>();
        for (Product product : products) {
            if (product.getManufacturer() != null) {
                uniqueManufacturers.add(product.getManufacturer());
            }
        }
        if (uniqueManufacturers.isEmpty()) {
            System.out.println("В коллекции нет элементов с заданным manufacturer (все null).");
            return;
        }

        System.out.println("Уникальные manufacturers в коллекции:");
        int counter = 1;
        for (Organization manufacturer : uniqueManufacturers) {
            System.out.println(counter++ + ". " + manufacturer);
        }
    }
}




