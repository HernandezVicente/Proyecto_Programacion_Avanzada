package org.example.logica;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 🔥 CRUDFireStore
 * Clase genérica para manejar operaciones CRUD (Create, Read, Update, Delete)
 * en Firestore, de forma flexible y reutilizable.
 *
 * Permite trabajar con cualquier clase de modelo (Producto, Cliente, etc.)
 * usando reflexión y genéricos.
 *
 * @param <T> Tipo de objeto (por ejemplo, Producto, Administrador, etc.)
 */
public class CRUDFireStore<T> {

    private final Firestore db;
    private final String collectionName;
    private final Class<T> typeClass;

    /**
     * Constructor
     * @param collectionName nombre de la colección en Firestore
     * @param typeClass clase del tipo de objeto que se guardará
     */
    public CRUDFireStore(String collectionName, Class<T> typeClass) {
        this.db = FirestoreClient.getFirestore();
        this.collectionName = collectionName;
        this.typeClass = typeClass;
    }

    /**
     * 🟢 Crea o actualiza un documento usando un ID específico
     * Si ya existe, se sobrescribe (update completo).
     *
     * @param id identificador del documento (por ejemplo, código de barra)
     * @param objeto objeto a guardar en Firestore
     */
    public void guardar(String id, T objeto) {
        try {
            db.collection(collectionName)
                    .document(id)
                    .set(objeto)
                    .get(); // Espera la operación
            System.out.println("✅ Guardado correctamente en " + collectionName + " con ID: " + id);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al guardar en Firestore: " + e.getMessage());
        }
    }

    /**
     * 🟡 Obtiene un documento específico por ID.
     *
     * @param id identificador del documento
     * @return objeto T o null si no existe
     */
    public T obtenerPorId(String id) {
        try {
            DocumentSnapshot snapshot = db.collection(collectionName)
                    .document(id)
                    .get()
                    .get();

            if (snapshot.exists()) {
                return snapshot.toObject(typeClass);
            } else {
                System.out.println("⚠️ Documento no encontrado: " + id);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al obtener documento: " + e.getMessage());
        }
        return null;
    }

    /**
     * 🔵 Obtiene todos los documentos de la colección.
     *
     * @return lista con todos los objetos T
     */
    public List<T> obtenerTodos() {
        List<T> lista = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
            List<QueryDocumentSnapshot> documentos = query.get().getDocuments();

            for (QueryDocumentSnapshot doc : documentos) {
                T objeto = doc.toObject(typeClass);
                lista.add(objeto);
            }

            System.out.println("📄 Se obtuvieron " + lista.size() + " documentos de " + collectionName);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al obtener documentos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * 🟠 Actualiza un documento existente.
     * (Solo reemplaza los campos indicados, no todo el documento)
     *
     * @param id identificador del documento
     * @param datos mapa con los campos a actualizar
     */
    public void actualizarCampos(String id, Map<String, Object> datos) {
        try {
            db.collection(collectionName)
                    .document(id)
                    .update(datos)
                    .get();
            System.out.println("✏️ Documento " + id + " actualizado correctamente.");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al actualizar documento: " + e.getMessage());
        }
    }

    /**
     * 🔴 Elimina un documento por ID.
     *
     * @param id identificador del documento
     */
    public void eliminar(String id) {
        try {
            db.collection(collectionName)
                    .document(id)
                    .delete()
                    .get();
            System.out.println("🗑️ Documento " + id + " eliminado correctamente.");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al eliminar documento: " + e.getMessage());
        }
    }
}
