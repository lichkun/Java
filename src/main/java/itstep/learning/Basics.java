package itstep.learning;

import java.util.*;

/**
 * Основы языка программирования
 */
public class Basics {
    public void run() {
        System.out.println("Basic run");
        // типи данних та переменные
        // primitives - Value types
        byte b = 1; //8 bit !!unsigned вариаций нет
        short s = 1; //16 bit   byte b =-1   1111 1111
        int i = 1; //32 bit     [short] b      0000 0000 1111 1111 = 255
        long l = 1L; //64 bit    (short) b      1111 1111 1111 1111 = -1
        float f = 1.0f;
        double d = 1.0;
        char c = 'c';
        boolean bool = true;
        System.out.println(c);

        //reference type
        //boxing
        Byte bb = b;
        Short ss = new Short(s);

        // Arrays, Collections, Loops
        int[] arr = {1, 2, 3, 4};
        int[] arr2 = new int[]{1, 2, 3, 4};
        int[] arr3 = new int[10]; //default 0
        for (int j = 0; j < arr2.length; j++) {
            System.out.print(arr2[j] + " ");
        }
        System.out.println();

        for (int elem : arr2) {  //foreach
            System.out.print(elem + " ");
        }
        System.out.println();

        int[][] arr2d = {
                {1, 2, 3},
                {4, 5, 6, 7},
                {8, 9}};
        for (int j = 0; j < arr2d.length; j++) {
            for (int j1 = 0; j1 < arr2d[j].length; j1++) {
                System.out.print(arr2d[j][j1] + " ");
            }
            System.out.println();
        }

        //Одномерная коллекция List
        //List - interface / ArrayList, LindeList - class
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Java");
        System.out.println(list.get(1));

        for (String str : list) {
            System.out.print(str + " ");
        }
        System.out.println();
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");  //Сравнение строк всегда по ссылке
        if (str1 == str2) {                        //тоесть сравниваются только две ссылки на один объект
            System.out.println("str1 == str2");    //По скольку операторы в Java не перегружаются
        } else {                                   //это ж правило работает и для String
            System.out.println("str1 != str2");    //Но первые два объекты показывают равенство, а третий - нет
        }                                          //String Pool/ Immutable
        if (str1 == str3) {                        //
            System.out.println("str1 == str3");    //
        } else {                                   //
            System.out.println("str1 != str3");
        }
        str1 = "!";  // Immutable ... = new String(str1+ "!")
        System.out.println(str1 + ", " + str2);
        if (str1 == str2) {
            System.out.println("str1 == str2");
        } else {
            System.out.println("str1 != str2");
        }
        // сравнение по значению - .equals
        if (str2.equals(str3)) {
            System.out.println("str2 == str3");
        } else {
            System.out.println("str2 != str3");
        }
        //... или Objects.equals, если нет гарантии,что объект не null
        if (Objects.equals(str2, str3)) {
            System.out.println("str2 == str3");
        } else {
            System.out.println("str2 != str3");
        }

        //Ассоциативные коллекции(~Dictionary)
        Map<String, String> map = new HashMap<>();
        map.put("cat", "кот");
        map.put("map", "карта");
        map.put("equal", "равно");
        map.put("print", "печать");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("------------------------");
        for (String key : map.keySet()) {
            System.out.println(key + " " + map.get(key));
        }
    }
}

