package org.example;

import org.example.BaseDeDatos.FirebaseCRUD;
import org.example.Logica.FuncionEcencial;
import org.example.Logica.Funcione;
import org.example.Modelo.Administrador;
import org.example.Modelo.Cliente;

public class Main {
    public static void main(String[] args) {
        //modelos vista controlador
        //capa modelo
        //capa logica
        //capa de vistas

        //JavaServer Pages (JSP)

        //inversión de dependencia


        //En Firestore Database de inicia una coleción, donde se puede agregar un documento, y dento de documento se puede iniciar otra colección, aparentemente se puede hacer n veces
        FuncionEcencial operaciones = new FuncionEcencial();
        FirebaseCRUD crud = new FirebaseCRUD(operaciones.connetarBaseDeDatos());
        operaciones.crear(crud);
        operaciones.leer(crud);
        operaciones.actualizar(crud);
        operaciones.borrar(crud);


        Funcione funcione = new Funcione();
        Administrador administrador = new Administrador();
        System.out.println("Iniando Seción");
        if(funcione.leerString().equals(administrador.getSurname())){
            if (funcione.leerString().equals(administrador.getPassword())){
                System.out.println("Administrado iniciado");
            }
        }

        administrador.agregarBodega();
        administrador.agregarEstante();
        administrador.agregarBodega();
        administrador.verProducto();
        administrador.verBodega();
        administrador.verEstante();
        administrador.acualizarBodega();
        administrador.acualizarEstante();
        administrador.acualizarProducto();
        administrador.eliminarBodega();
        administrador.eliminarEstante();
        administrador.eliminarProducto();
        administrador.calcularCapacidadDisponibleBodega();
        administrador.calcularCapacidadDisponibleEstante();

        Cliente cliente = new Cliente();
        System.out.println(cliente);

        Cliente cliente2 = new Cliente("21.847.674-0");
        System.out.println(cliente2);
    }
}