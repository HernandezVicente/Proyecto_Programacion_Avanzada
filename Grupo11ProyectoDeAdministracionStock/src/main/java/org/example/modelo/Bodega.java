package org.example.modelo;

public class Bodega {
    private long id;
    private String nombre;
    private String direccion;
    private int capacidad;

    // 🔹 Constructor vacío (requerido por Firestore)
    public Bodega() {}

    // 🔹 Constructor completo
    public Bodega(long id, String nombre, String direccion, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
    }

    // 🔹 Getters
    public long getId() {return id;}

    public String getNombre() {return nombre;}

    public String getDireccion() {return direccion;}

    public int getCapacidad() {return capacidad;}
}
