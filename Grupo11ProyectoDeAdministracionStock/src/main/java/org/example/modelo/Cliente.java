package org.example.modelo;

public class Cliente {
    private String rut;
    private int dinero;

    public Cliente(String rut, int dinero) {
        this.rut = rut;
        this.dinero = dinero;
    }

    public Cliente(int dinero){
        this.dinero = dinero;
        this.rut = null;
    }

    public String getRut() {
        return rut;
    }

    public int getDinero() {
        return dinero;
    }

}