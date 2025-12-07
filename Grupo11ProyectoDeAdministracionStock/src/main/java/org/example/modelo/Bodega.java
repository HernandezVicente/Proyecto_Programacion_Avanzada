package org.example.modelo;

public class Bodega {
    private long id;
    private String nombre;
    private String direccion;
    private int capacidad;

    public Bodega() {}

    public Bodega(long id, String nombre, String direccion, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
    }

    public long getId() {return id;}
    public String getNombre() {return nombre;}
    public String getDireccion() {return direccion;}
    public int getCapacidad() {return capacidad;}

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}