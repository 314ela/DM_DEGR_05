package com.upiiz.dm_degr_05.util;

public class basicas {

    public static double suma(double n1, double n2) {
        return n1 + n2;
    }

    public static double resta(double n1, double n2) {
        return n1 - n2;
    }

    public static double multiplicacion(double n1, double n2) {
        return n1 * n2;
    }

    public static double division(double n1, double n2) {
        if (n2 == 0) {
            throw new ArithmeticException("No se puede dividir entre cero");
        }
        return n1 / n2;
    }
}
