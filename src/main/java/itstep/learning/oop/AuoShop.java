package itstep.learning.oop;

import itstep.learning.oop.annotations.Required;

import java.lang.reflect.Field;
import java.util.List;

public class AuoShop {
    private List<Vehicle> vehicles;

    public AuoShop() {
        try {
            vehicles = new VehicleFactory().loadFromJson("shop.json");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void run() {
        printAll();
        System.out.println("-----------LARGE SIZED------------");
        printLargeSized();
        System.out.println("-----------NON-LARGE SIZED------------");
        printNonLargeSized();
        System.out.println("-----------TRAILER-ABLE------------");
        printTrailers();
        System.out.println("-----------VEHICLE-WITH-TYPE-OF-DRIVE------------");
        printWithTypeOfDrive();
//        List<Class<?>> classes = getProductClasses("itstep.learning.oop");
//        for (Class<?> clazz : classes) {
//            System.out.println(clazz.getName());
//            printRequired(clazz);
//        }

    }

    public void printAll() {
        for (Vehicle v : vehicles) {
            System.out.println(v.getInfo());
        }
    }

    public void printLargeSized() {
        for (Vehicle v : vehicles) {
            if (v instanceof LargeSized) System.out.println(v.getInfo());
        }
    }

    public void printNonLargeSized() {
        for (Vehicle v : vehicles) {
            if (!(v instanceof LargeSized)) System.out.println(v.getInfo());
        }
    }

    private void printTrailers() {
        for (Vehicle v : vehicles) {
            if (v instanceof Trailer) {
                System.out.print(v.getInfo());
                System.out.println(" could have a trailer of type " + ((Trailer) v).trailerInfo());
            }
        }
    }

    public void printWithTypeOfDrive() {
        for (Vehicle v : vehicles) {
            if (v instanceof TypeOfDrive) System.out.println(v.getInfo());
        }
    }

    private void printRequired(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Required.class)) {
                Required annotation = field.getAnnotation(Required.class);
                boolean isAlter = annotation.isAlternate();
                String requiredName = field.getAnnotation(Required.class).value();
                System.out.println(
                        "".equals(requiredName) ? field.getName() : requiredName + (isAlter ? " or " + field.getName() : ""));
            }
        }
    }
}
