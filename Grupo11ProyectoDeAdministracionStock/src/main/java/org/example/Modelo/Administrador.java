package org.example.Modelo;

import org.example.Logica.Funcione;

import java.util.ArrayList;

public class Administrador {
    private final String surname;
    private final String password;
    private ArrayList<Producto> productos;
    private ArrayList<Bodega> bodegas;
    private ArrayList<Estante> estantes;
    private final Funcione funcione = new Funcione();

    Administrador(){
        //buscar una mejor alternativa pra poder hacer in login de administrador
        this.surname = "Vicente";
        this.password = "secreto123";
        this.productos = new ArrayList<>();
        this.bodegas = new ArrayList<>();
        this.estantes = new ArrayList<>();
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
