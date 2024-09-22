package itstep.learning.dz;

public class Matrix {
    public static void Multiple(int[][] A, int[][] B) {
        if (A[0].length != B.length) {
            System.out.println("Умножение невозможно, так как количество столбцов первой матрицы не равно количеству строк второй матрицы.");
            return;
        }
        int[][] C = new int[A.length][B[0].length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }


        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[0].length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }
}
