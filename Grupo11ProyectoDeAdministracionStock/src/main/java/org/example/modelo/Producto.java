package org.example.modelo;

public class Producto {
    private final long codigoBarra;
    private final String nombre;
    private int precio;
    private final String categoria;

    public Producto(long codigoBarra, String nombre, int precio, String categoria) {
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public long getCodigoBarra() {
        return codigoBarra;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }
}
