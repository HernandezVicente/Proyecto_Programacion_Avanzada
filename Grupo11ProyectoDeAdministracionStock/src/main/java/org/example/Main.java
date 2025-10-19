package org.example;

public class Main {
    public static void main(String[] args) {
        //En Firestore Database de inicia una coleción, donde se puede agregar un documento, y dento de documento se puede iniciar otra colección, aparentemente se puede hacer n veces
        /*
        FuncionEcencial operaciones = new FuncionEcencial();
        FirebaseCRUD crud = new FirebaseCRUD(operaciones.connetarBaseDeDatos());
        operaciones.crear(crud);
        operaciones.leer(crud);
        operaciones.actualizar(crud);
        operaciones.borrar(crud);
         */


        Funcione funcione = new Funcione();
        Administrador administrador = new Administrador();
       // administrador.agregarProducto();//System.out.println(administrador);


        System.out.println("Iniando Seción");
        if(funcione.leerString().equals(administrador.getSurname())){
            if (funcione.leerString().equals(administrador.getPassword())){
                System.out.println("Administrado iniciado");
                administrador.agregarProducto();
                administrador.agregarProducto();
                administrador.verProducto();
            }
        }

    }


}