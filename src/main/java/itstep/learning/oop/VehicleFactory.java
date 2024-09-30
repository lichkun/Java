package itstep.learning.oop;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import itstep.learning.oop.annotations.Product;
import itstep.learning.oop.annotations.Required;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Stream;

public class VehicleFactory {

    private Map<Class<?>, Map<String, Field>> productClasses = null;

    public List<Vehicle> loadFromJson(String resourceName) throws Exception {
        URL resourceUrl = this.getClass().getClassLoader().getResource(resourceName);
        if (resourceUrl == null) {
            throw new Exception("Resource not found: " + resourceName);
        }
        String decodedPath = (URLDecoder.decode(resourceUrl.getPath(), "UTF-8"));
        try (InputStream stream = Objects.requireNonNull(
                new FileInputStream(decodedPath)
        )) {
            String json = readAsString(stream);
            Gson gson = new Gson();
            JsonArray arr = gson.fromJson(json, JsonArray.class);
            List<Vehicle> vehicles = new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                vehicles.add(fromJsonObject(
                                arr.get(i).getAsJsonObject()
                        )
                );
            }
            return vehicles;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * Збирає всі файли-класи, що є у даному пакеті
     * getProductClasses( "itstep.learning.oop" )
     * [ Bike -> ["type"] ]
     * [ Bus -> ["seats"] ]
     */
    public Map<Class<?>, Map<String, Field>> getProductClasses(String packageName) {
        if (productClasses != null) {
            return productClasses;
        }
        URL classLocation = this.getClass().getClassLoader().getResource(".");
        if (classLocation == null) {
            throw new RuntimeException("Error resource locating");
        }
        File classRoot = null;
        File[] files;
        try {
            classRoot = new File(
                    URLDecoder.decode(classLocation.getPath(), "UTF-8"),
                    packageName.replace('.', '/')
            );
        } catch (Exception ignored) {
        }
        if (classRoot == null || (files = classRoot.listFiles()) == null) {
            throw new RuntimeException("Error resource traversing");
        }
        List<String> classNames = new ArrayList<>();
        scanClassesInDirectory(classRoot, packageName, classNames);

        // Знаходимо класи з анотацією @Product
        Map<Class<?>, Map<String, Field>> classes = new HashMap<>();
        for (String className : classNames) {
            try {
                Class<?> cls = Class.forName(className);
                if (cls.isAnnotationPresent(Product.class)) {
                    classes.put(cls, getRequired(cls));
                }
            } catch (ClassNotFoundException ignored) {
            }
        }

        productClasses = classes;
        return classes;
    }

    // Рекурсивна функція для сканування всіх класів у поточній директорії та піддиректоріях
    private void scanClassesInDirectory(File directory, String packageName, List<String> classNames) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                // Рекурсивно скануємо підпакети
                scanClassesInDirectory(file, packageName + "." + file.getName(), classNames);
            } else if (file.getName().endsWith(".class")) {
                // Додаємо файл класу до списку
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                classNames.add(className);
            }
        }
    }

    private Vehicle fromJsonObject(JsonObject obj) {
        Map.Entry<Class<?>, Map<String, Field>> vehicleClassEntry = null;
        for (Map.Entry<Class<?>, Map<String, Field>> entry : getProductClasses("itstep.learning.oop").entrySet()) {
            boolean isMatch = true;
            for (String fieldName : entry.getValue().keySet()) {
                if (!obj.has(fieldName)) {
                    isMatch = false;
                }
            }
            if (isMatch) {
                if (vehicleClassEntry != null) {
                    throw new RuntimeException(
                            String.format(
                                    Locale.ROOT,
                                    "Ambiguous classes: '%s' and '%s' for data %s",
                                    vehicleClassEntry.getKey().getName(),
                                    entry.getKey().getName(),
                                    obj.toString()
                            ));
                }
                vehicleClassEntry = entry;
                break;
            }
        }
        if (vehicleClassEntry == null) {
            throw new RuntimeException("No class found for data " + obj.toString());
        }
        Vehicle vehicle;
        try {
            vehicle = (Vehicle) vehicleClassEntry.getKey().getConstructor().newInstance();
        } catch (Exception ignored) {
            throw new RuntimeException(
                    "Unable to instantiate class " +
                            vehicleClassEntry.getKey().getName() +
                            ". Possible there is no default constructor");
        }

        try {
            for (String fieldName : vehicleClassEntry.getValue().keySet()) {
                Field filed = vehicleClassEntry.getValue().get(fieldName);
                filed.setAccessible(true);
                switch (filed.getType().getName()) {
                    case "int":
                        filed.set(vehicle, obj.get(fieldName).getAsInt());
                        break;
                    case "double":
                        filed.set(vehicle, obj.get(fieldName).getAsDouble());
                        break;
                    case "boolean":
                        filed.set(vehicle, obj.get(fieldName).getAsBoolean());
                        break;
                    default:
                        filed.set(vehicle, obj.get(fieldName).getAsString());
                }
            }

            return vehicle;
        } catch (Exception ignored) {
            throw new RuntimeException(
                    "Unable to fill object of class " +
                            vehicleClassEntry.getKey().getName() +
                            ". Type mismatch or access denied");
        }
    }

    /**
     * Знайти всі поля класу, помічені анотацією @Required
     */
    private Map<String, Field> getRequired(Class<?> cls) {
        Map<String, Field> res = new HashMap<>();
        Class<?> superclass = cls.getSuperclass();
        Field[] fields = superclass == null ? new Field[0] : superclass.getDeclaredFields();
        Stream.concat(
                        Arrays.stream(cls.getDeclaredFields()),
                        Arrays.stream(fields))
                .forEach((field) -> {
                    if (field.isAnnotationPresent(Required.class)) {
                        Required annotation = field.getAnnotation(Required.class);
                        String requiredName = annotation.value();
                        // boolean isAlter = annotation.isAlternate();
                        res.put(
                                "".equals(requiredName)
                                        ? field.getName()
                                        : requiredName,
                                field);
                    }
                });
        return res;
    }

    private String readAsString(InputStream stream) throws IOException {
        byte[] buffer = new byte[4096];
        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
        int length;
        while ((length = stream.read(buffer)) != -1) {
            byteBuilder.write(buffer, 0, length);
        }
        return byteBuilder.toString();
    }

}