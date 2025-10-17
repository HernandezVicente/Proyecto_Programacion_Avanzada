package org.example;

import com.google.cloud.firestore.Firestore;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FuncionEcencial {
    protected Firestore connetarBaseDeDatos() {
        try {
            String json = new String(Files.readAllBytes(
                    Paths.get("administrarstock-firebase-adminsdk-fbsvc-57dc97087c.json")));

            String url = "https://administrarstock.firebaseio.com/";

            ConnectionInfoFirebase connectionInfo = new ConnectionInfoFirebase(json, url);

            FirebaseAdapter adapter = new FirebaseAdapter();
            String resultado = adapter.createConnection(connectionInfo);
            System.out.println("Resultado conexión: " + resultado);

            Firestore db = adapter.getConnection();
            System.out.println("Instancia de Firestore obtenida: " + db);
            return db;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void crear(FirebaseCRUD crud) {
        mostrarQueIngresar(1);
        String collection = escribir();
        mostrarQueIngresar(2);
        String domumentId = escribir();

        String clave;
        String valor;
        Map<String, Object> data = new HashMap<>();
        do {
            mostrarQueIngresar(3);
            clave = escribir();
            mostrarQueIngresar(4);
            valor = escribir();
            data.put(clave, valor);
        }while (!clave.equals("fin")&&!valor.equals("fin"));


        String createRes = crud.create(collection, domumentId, data);
        System.out.println(createRes);

    }

    protected void leer(FirebaseCRUD crud) {
        mostrarQueIngresar(1);
        String collection = escribir();
        mostrarQueIngresar(2);
        String domumentId = escribir();

        Map<String, Object> leido = crud.read(collection, domumentId);
        System.out.println("Documento leído: " + leido);


    }

    protected void actualizar(FirebaseCRUD crud) {
        mostrarQueIngresar(1);
        String collection = escribir();
        mostrarQueIngresar(2);
        String domumentId = escribir();

        mostrarQueIngresar(3);
        String clave = escribir();
        mostrarQueIngresar(4);
        String valor = escribir();

        Map<String, Object> cambios = new HashMap<>();
        cambios.put(clave, valor);

        String updateRes = crud.update(collection, domumentId, cambios);
        System.out.println(updateRes);


    }

    protected void borrar(FirebaseCRUD crud) {
        mostrarQueIngresar(1);
        String collection = escribir();
        mostrarQueIngresar(2);
        String domumentId = escribir();

        String deleteRes = crud.delete(collection, domumentId);
        System.out.println(deleteRes);
    }

    protected String escribir(){
        Scanner teclado = new Scanner(System.in);
        String cadena = "";
        cadena = teclado.nextLine().trim();
        return cadena;

    }

    protected void mostrarQueIngresar(int n){
        if (n == 1){
            System.out.println("Ingrese el nombre de la colleción");
        } else if (n==2) {
            System.out.println("Ingrese el nombre del documento");
        } else if (n==3) {
            System.out.println("Ingrese de la clave");
        } else if (n==4) {
            System.out.println("Ingrese el valor");
        }
    }
}
