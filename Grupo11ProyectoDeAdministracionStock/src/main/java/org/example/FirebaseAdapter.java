package org.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FirebaseAdapter implements DatabaseConnectionAdapter<Firestore, ConnectionInfoFirebase> {

    @Override
    public String createConnection(ConnectionInfoFirebase c) {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ByteArrayInputStream(c.getCredetials().getBytes(StandardCharsets.UTF_8))))
                    .setDatabaseUrl(c.getPath())
                    .build();

            FirebaseApp.initializeApp(options);
            return "Success Connection";
        } catch (IOException e) {
            e.printStackTrace();
            return "Fail Connection";
        }
    }

    @Override
    public Firestore getConnection() {
        return FirestoreClient.getFirestore();
    }
}


