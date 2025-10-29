package org.example.modelo;

public class Bodega {
    private final long codigo;
    private final int capacidadMAxima;
    private int capacidadActual;

    public Bodega(long codigo, int capacidadMAxima) {
        this.codigo = codigo;
        this.capacidadMAxima = capacidadMAxima;
        this.capacidadActual = 0;
    }

    public long getCodigo() {
        return codigo;
    }

    public int getCapacidadMAxima() {
        return capacidadMAxima;
    }

    public int getCapacidadActual() {
        return capacidadActual;
    }
}
