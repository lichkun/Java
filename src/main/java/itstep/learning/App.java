package itstep.learning;

import itstep.learning.dz.Matrix;
import itstep.learning.oop.OopDemo;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // new Basics().run();
        //new OopDemo().run();
        int[][] A = {
                {1, 2, 3},
                {4, 5, 6}
        };

        int[][] B = {
                {7, 8},
                {9, 10},
                {11, 12}
        };
        Matrix.Multiple(A, B);
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