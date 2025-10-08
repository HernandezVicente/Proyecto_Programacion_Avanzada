package org.example;

import java.util.ArrayList;

public class Cliente {
    private String rut;
    ArrayList<Producto> productos;

    public void agarrarProducto(){}
    public void verProducto(){}
    public void comprarProducto(){}

    @Override
    public String toString() {
        return "Cliente{" +
                "rut='" + rut + '\'' +
                ", productos=" + productos +
                '}';
    }
}
