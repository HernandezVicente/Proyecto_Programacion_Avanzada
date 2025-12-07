package org.example.modelo;

/**
 * Clase Producto
 * Representa un producto dentro del inventario del sistema.

 * Mapeada directamente con los documentos de Firestore.
 * El campo 'codigoBarra' será el ID del documento en la colección "productos".
 */
public class Producto {
    private long codigoBarra;
    private String nombre;
    private int precio;
    private String categoria;
    private int cantidad;

    public Producto() {
    }

    public Producto(long codigoBarra, String nombre, int precio, String categoria, int cantidad) {
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.cantidad = cantidad;
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
    public int getCantidad() {
        return cantidad;
    }

    public void setCodigoBarra(long codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}