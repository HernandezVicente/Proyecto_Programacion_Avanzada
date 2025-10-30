package org.example.logica;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.example.logica.ConexionFirebase;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class CRUDFireStore<T> {
    private final Firestore db;
    private final String coleccion;
    private final Class<T> tipoClase;

    public CRUDFireStore(String coleccion, Class<T> tipoClase) {
        this.db = ConexionFirebase.getDB();
        this.coleccion = coleccion;
        this.tipoClase = tipoClase;
    }

    // CREATE
    public void crear(String id, T objeto) {
        try {
            db.collection(coleccion).document(id).set(objeto).get();
            System.out.println("✅ Documento agregado correctamente (" + coleccion + ")");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al agregar documento: " + e.getMessage());
        }
    }

    // READ ALL
    public List<T> leerTodos() {
        List<T> lista = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future = db.collection(coleccion).get();
            List<QueryDocumentSnapshot> documentos = future.get().getDocuments();
            for (QueryDocumentSnapshot doc : documentos) {
                lista.add(doc.toObject(tipoClase));
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al leer documentos: " + e.getMessage());
        }
        return lista;
    }

    // UPDATE
    public void actualizar(String id, T objeto) {
        try {
            db.collection(coleccion).document(id).set(objeto).get();
            System.out.println("🔁 Documento actualizado correctamente (" + id + ")");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al actualizar documento: " + e.getMessage());
        }
    }

    // DELETE
    public void eliminar(String id) {
        try {
            db.collection(coleccion).document(id).delete().get();
            System.out.println("🗑️ Documento eliminado correctamente (" + id + ")");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al eliminar documento: " + e.getMessage());
        }
    }

    // READ ONE
    public Optional<T> leerPorId(String id) {
        try {
            DocumentSnapshot doc = db.collection(coleccion).document(id).get().get();
            if (doc.exists()) {
                return Optional.of(doc.toObject(tipoClase));
            } else {
                System.out.println("⚠️ No existe un documento con ID: " + id);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al obtener documento: " + e.getMessage());
        }
        return Optional.empty();
    }
}
