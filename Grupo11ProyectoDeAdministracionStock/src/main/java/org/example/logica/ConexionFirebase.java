package org.example.logica;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class ConexionFirebase {

    private static Firestore db;

    public static void conectar() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            String path = System.getProperty("user.dir") + "/administrarstock.json";
            FileInputStream serviceAccount = new FileInputStream(path);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase inicializado correctamente.");
        }

        db = FirestoreClient.getFirestore();
        System.out.println("Conectado a Firestore con éxito.");
    }

    public static Firestore getDB() {
        return db;
    }
}
