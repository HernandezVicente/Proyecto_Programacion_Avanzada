package org.example.Modelo;

import java.util.ArrayList;

public class Cliente {
    private String rut;
    private int dinero;
    private ArrayList<Producto> carrito;


    public Cliente(){
        this.rut = null;
        this.dinero = dineroAleatorio();
        this.carrito = new ArrayList<>();
    }
    public Cliente(String rut){
        this.rut = rut;
        this.dinero = dineroAleatorio();
        this.carrito = new ArrayList<>();
    }

    public void agarrarProducto(){

    }
    public void verProducto(){}
    public void comprarProducto(){}

    public int dineroAleatorio(){
        int numeroTemporal;
        numeroTemporal = (int)(Math.random()*9999999);
        return numeroTemporal;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "rut='" + rut + '\'' +
                ", dinero=" + dinero +
                ", carrito=" + carrito +
                '}';
    }
}
