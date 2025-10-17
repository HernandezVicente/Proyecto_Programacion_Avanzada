package org.example;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.Map;

public class FirebaseCRUD {
    private final Firestore db;

    public FirebaseCRUD(Firestore db) {
        this.db = db;
    }

    public String create(String collection, String documentId, Map<String, Object> data) {
        try {
            ApiFuture<WriteResult> result = db.collection(collection).document(documentId).set(data);
            return "Documento creado en: " + result.get().getUpdateTime();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al crear documento";
        }
    }

    public Map<String, Object> read(String collection, String documentId) {
        try {
            DocumentReference docRef = db.collection(collection).document(documentId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                return document.getData();
            } else {
                System.out.println("Documento no encontrado.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String update(String collection, String documentId, Map<String, Object> updates) {
        try {
            ApiFuture<WriteResult> result = db.collection(collection).document(documentId).update(updates);
            return "Documento actualizado en: " + result.get().getUpdateTime();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar documento";
        }
    }

    public String delete(String collection, String documentId) {
        try {
            ApiFuture<WriteResult> result = db.collection(collection).document(documentId).delete();
            return "Documento eliminado en: " + result.get().getUpdateTime();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar documento";
        }
    }
}

