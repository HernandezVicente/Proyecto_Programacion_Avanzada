package org.example;

public class Main {
    public static void main(String[] args) {
        Producto producto = new Producto();
        Bodega bodega = new Bodega();
        Estante estante = new Estante();
        Administrador administrador = new Administrador();
        Cliente cliente = new Cliente();

        System.out.println(producto);
        System.out.println(bodega);
        System.out.println(estante);
        System.out.println(administrador);
        System.out.println(cliente);


    }
}