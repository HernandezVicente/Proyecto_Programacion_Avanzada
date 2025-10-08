package org.example;

import java.util.ArrayList;

public class Administrador {
    private String surname;
    private String password;
    ArrayList<Producto> productos;

    public void agregarProducto(){}
    public void verProducto(){}
    public void acualizarProducto(){}
    public void eliminarProducto(){}

    public void agregarBodega(){}
    public void verBodega(){}
    public void acualizarBodega(){}
    public void eliminarBodega(){}

    public void agregarEstante(){}
    public void verEstante(){}
    public void acualizarEstante(){}
    public void eliminarEstante(){}

    public void pedirMasProducto(){}
    public void calcularCapacidadDisponibleBodega(){}
    public void calcularCapacidadDisponibleEstante(){}

    @Override
    public String toString() {
        return "Administrador{" +
                "surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", productos=" + productos +
                '}';
    }
}
