package org.example.Modelo;

public class Producto {
    private long codigoBarra;
    private String nombre;
    private int precio;
    private String categoria;

    Producto(long codigoBarra, String nombre, int precio, String categoria){
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "coddigoBarra=" + codigoBarra +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
