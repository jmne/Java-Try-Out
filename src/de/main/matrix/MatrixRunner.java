package de.main.matrix;

public class MatrixRunner {

    public void run(){
        // Beispielhafte Addition zweier Matrizen und Ausgabe auf der Konsole.
        Matrix m1 = new Matrix(new long[][]{{-1, 3,-4}, { 3, 2, 3}, { 0,-5, 3}});
        Matrix m2 = new Matrix(new long[][]{{-3,-4, 5}, {-1, 5, 0}, { 4, 0,-2}});
        Matrix m3 = m1.addition(m2);
        System.out.println(m1 + " + " + m2);
        System.out.println("= " + m3.toString());
    }

    public static void main(String[] args){
        MatrixRunner r = new MatrixRunner();
        r.run();
    }
}
