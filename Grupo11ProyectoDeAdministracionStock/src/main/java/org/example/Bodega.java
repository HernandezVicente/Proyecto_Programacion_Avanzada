package org.example;

import java.util.ArrayList;

public class Bodega {
    private long codigo; //(2^63)-1 == 9223372036854775807
    private int capacidadMaxima; //(2^31)-1 == 2147483647
    private int capacidadActual; //(2^31)-1 == 2147483647
    ArrayList<Producto> productos;

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
