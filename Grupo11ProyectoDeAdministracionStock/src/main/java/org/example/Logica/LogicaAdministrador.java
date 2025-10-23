package org.example.Logica;

import org.example.Modelo.Bodega;
import org.example.Modelo.Estante;
import org.example.Modelo.Producto;

public class LogicaAdministrador {

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
        bodegas.add(new Bodega(funcione.leerLong(), funcione.leerInt()));
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
        System.out.println(bodegas);
    }
    public void calcularCapacidadDisponibleEstante(){}
}
