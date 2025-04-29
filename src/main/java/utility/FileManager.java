package utility;

import data.Products;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;


import java.io.*;

public class FileManager {
    private final String filePath;

    public FileManager() {
        this.filePath = System.getenv("FILE_NAME");
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalStateException("Переменная окружения FILE_NAME не установлена.");
        }
    }

    public Products readXml(){
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
            JAXBContext context = JAXBContext.newInstance(Products.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Products) unmarshaller.unmarshal(bufferedInputStream);
        }catch (IOException e){
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (JAXBException e) {
            System.out.println("Ошибка при разборе XML: " + e.getMessage());
        }
        return null;
    }

    public void loadToFile(Products products) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            JAXBContext context = JAXBContext.newInstance(Products.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(products, fileWriter);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        } catch (JAXBException e) {
            System.err.println("Ошибка при сериализации в XML: " + e.getMessage());
        }
    }
}


