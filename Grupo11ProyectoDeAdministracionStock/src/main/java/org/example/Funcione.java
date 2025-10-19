package org.example;

import java.util.Scanner;

public class Funcione {
    public Scanner teclado = new Scanner(System.in);

    public long leerLong() {
        System.out.println("Escriba un número de tipo Long");
        String input = teclado.nextLine().trim();
        return Long.parseLong(input);
    }

    public String leerString() {
        System.out.println("Escriba una palabra de tipo String");
        return teclado.nextLine().trim();
    }

    public int leerInt() {
        System.out.println("Escriba un número de tipo Integer");
        String input = teclado.nextLine().trim();
        return Integer.parseInt(input);
    }
}
