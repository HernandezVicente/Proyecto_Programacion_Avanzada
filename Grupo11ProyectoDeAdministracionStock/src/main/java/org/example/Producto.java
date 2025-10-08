package org.example;

public class Producto {
    private long coddigoBarra;
    private String nombre;
    private int precio;
    private String categoria;

    @Override
    public String toString() {
        return "Producto{" +
                "coddigoBarra=" + coddigoBarra +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
