package org.example;

import io.netty.handler.codec.MessageToByteEncoder;

import java.util.ArrayList;

public class Administrador {
    private String surname;
    private String password;
    private ArrayList<Producto> productos;
    private ArrayList<Bodega> bodegas;
    private ArrayList<Estante> estantes;
    Funcione funcione = new Funcione();

    public void agregarProducto(){
        //agregar un producto a la lista
        productos.add(new Producto(funcione.leerLong(), funcione.leerString(), funcione.leerInt(), funcione.leerString()));
    }
    public void verProducto(){
        System.out.println(productos.toString());

    }
    public void acualizarProducto(){
        //actualizar un producto, pero teniendo su codigo de barras
    }
    public void eliminarProducto(){
        //eliminar el producto de la lista

    }

    public void agregarBodega(){
        bodegas.add(new Bodega(funcione.leerLong(), funcione.leerInt(), funcione.leerInt()));
    }
    public void verBodega(){
        System.out.println(bodegas.toString());
    }
    public void acualizarBodega(){}
    public void eliminarBodega(){}

    public void agregarEstante(){
        estantes.add(new Estante(funcione.leerLong(), funcione.leerInt(), funcione.leerInt()));
    }
    public void verEstante(){
        System.out.println(estantes.toString());
    }
    public void acualizarEstante(){}
    public void eliminarEstante(){}

    public void pedirMasProducto(){}
    public void calcularCapacidadDisponibleBodega(){

    }
    public void calcularCapacidadDisponibleEstante(){}

    Administrador(){
        this.surname = "Vicente";
        this.password = "secreto123";
        this.productos = new ArrayList<>();
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Administrador{"+
                "productos=" + productos +
                '}';
    }


}
