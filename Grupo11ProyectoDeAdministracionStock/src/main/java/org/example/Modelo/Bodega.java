package org.example.Modelo;

import java.util.ArrayList;

public class Bodega {
    private final long codigo; //(2^63)-1 == 9223372036854775807
    private final int capacidadMaxima; //(2^31)-1 == 2147483647
    private int capacidadActual; //(2^31)-1 == 2147483647
    ArrayList<Producto> productos;

    public Bodega(long codigo, int capacidadMaxima) {
        this.codigo = codigo;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = 0;
        this.productos = new ArrayList<>();
    }

    public void calcularCapacidadDisponibleBodega(){
        if(capacidadMaxima == capacidadActual){
            System.out.println("No hay capacidad disponible");
            System.out.println(productos.toString());
        } else if (capacidadMaxima>capacidadActual ) {
            int nuemeorTemporal =  capacidadMaxima - capacidadActual;
            System.out.println("La capacidad disponible es: "+nuemeorTemporal);
            System.out.println(productos.toString());
        }
    }

    @Override
    public String toString() {
        return "Bodega{" +
                "codigo=" + codigo +
                ", capacidadMaxima=" + capacidadMaxima +
                ", capacidadActual=" + capacidadActual +
                ", productos=" + productos +
                '}';
    }
}
