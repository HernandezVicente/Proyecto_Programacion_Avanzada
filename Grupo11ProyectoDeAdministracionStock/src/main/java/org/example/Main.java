package org.example;

import org.example.logica.ConexionFirebase;

public class Main {
    public static void main(String[] args) {
        try {
            ConexionFirebase.conectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


