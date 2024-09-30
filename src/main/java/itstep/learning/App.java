package itstep.learning;

import com.google.inject.Injector;
import itstep.learning.async.AsyncDemo;
import itstep.learning.dz.Matrix;
import itstep.learning.ioc.IocDemo;
import itstep.learning.ioc.Resolver;
import itstep.learning.oop.OopDemo;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // new Basics().run();
        // new OopDemo().run();
        // int[][] A = {
        //         {1, 2, 3},
        //         {4, 5, 6}
        // };
        // int[][] B = {
        //         {7, 8},
        //         {9, 10},
        //         {11, 12}
        // };
        // Matrix.Multiple(A, B);
        //new AsyncDemo().run();
        Resolver resolver = new Resolver();
        Injector injector = resolver.getInjector();
        IocDemo iocDemo = injector.getInstance(IocDemo.class); // ~ new IocDemo()
        iocDemo.run();
    }
}
/*
 У Java есть привязка к файловой системе
 -package (аналог namespace) = диектория(папка) : itstep.learning ->itstep/learning
 -public клас = файл
 -casing:
  =Type: CapitalCamelCase
  =fields/methods: loweCamelCase
  =package: snake_case
 */