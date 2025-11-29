package org.example.config;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.logica.BodegaServlet;
import org.example.modelo.Bodega;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BodegaServletTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock RequestDispatcher dispatcher;

    // Mocks para Firebase
    @Mock Firestore mockFirestore;
    @Mock CollectionReference mockCollection;
    @Mock DocumentReference mockDocument;
    @Mock ApiFuture<QuerySnapshot> mockQueryFuture;
    @Mock QuerySnapshot mockQuerySnapshot;
    @Mock ApiFuture<WriteResult> mockWriteFuture;

    /**
     * Helper para configurar el Mock de "Listar Bodegas" cuando está VACÍO.
     * Se usa en Create, Update y Delete para que no falle al refrescar la tabla.
     */
    private void setupMockFirebaseListarVacio() throws Exception {
        lenient().when(mockFirestore.collection("bodegas")).thenReturn(mockCollection);
        lenient().when(mockCollection.get()).thenReturn(mockQueryFuture);
        lenient().when(mockQueryFuture.get()).thenReturn(mockQuerySnapshot);
        lenient().when(mockQuerySnapshot.getDocuments()).thenReturn(Collections.emptyList());
    }

    // =================================================================================
    // 1. TEST CREATE (Crear)
    // =================================================================================
    @Test
    void testCrearBodegaExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            // Simulamos GUARDADO (set)
            when(mockCollection.document("123")).thenReturn(mockDocument);
            when(mockDocument.set(any(Bodega.class))).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            BodegaServlet servlet = new BodegaServlet();

            // Datos del Request
            when(request.getParameter("accion")).thenReturn("crear");
            when(request.getParameter("id")).thenReturn("123");
            when(request.getParameter("nombre")).thenReturn("Bodega Norte");
            when(request.getParameter("direccion")).thenReturn("Av. Siempre Viva 123");
            when(request.getParameter("capacidad")).thenReturn("500");
            when(request.getRequestDispatcher("bodegas.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verificación: Se llamó a set() con un objeto Bodega
            verify(mockDocument).set(any(Bodega.class));
            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test create: " + e.getMessage());
        }
    }

    @Test
    void testCrearBodegaFallaPorCamposVacios() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            BodegaServlet servlet = new BodegaServlet();

            // Datos Vacíos
            when(request.getParameter("accion")).thenReturn("crear");
            when(request.getParameter("id")).thenReturn("");
            when(request.getParameter("nombre")).thenReturn("");
            when(request.getParameter("direccion")).thenReturn("");
            when(request.getParameter("capacidad")).thenReturn("");
            when(request.getRequestDispatcher("bodegas.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verificación: Se seteó el error y NO se guardó
            verify(request).setAttribute(eq("error"), anyString());
            verify(mockCollection, never()).document(anyString());
            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    // =================================================================================
    // 2. TEST UPDATE (Actualizar)
    // =================================================================================
    @Test
    void testActualizarBodegaExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            // Simulamos ACTUALIZACIÓN (update)
            // Tu lógica usa: crud.actualizarCampos(id, Map.of("capacidad", nuevaCapacidad));
            when(mockCollection.document("123")).thenReturn(mockDocument);

            // update recibe un Map y devuelve un Future
            when(mockDocument.update(any(Map.class))).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            BodegaServlet servlet = new BodegaServlet();

            // Datos del Request (Solo ID y Capacidad son obligatorios para update según tu Servlet)
            when(request.getParameter("accion")).thenReturn("actualizar");
            when(request.getParameter("id")).thenReturn("123");
            when(request.getParameter("capacidad")).thenReturn("900");
            when(request.getRequestDispatcher("bodegas.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verificación: Se llamó a update() con un Mapa
            verify(mockDocument).update(any(Map.class));
            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test update: " + e.getMessage());
        }
    }

    // =================================================================================
    // 3. TEST DELETE (Eliminar)
    // =================================================================================
    @Test
    void testEliminarBodegaExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            // Simulamos ELIMINACIÓN (delete)
            when(mockCollection.document("999")).thenReturn(mockDocument);
            when(mockDocument.delete()).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            BodegaServlet servlet = new BodegaServlet();

            when(request.getParameter("accion")).thenReturn("eliminar");
            when(request.getParameter("id")).thenReturn("999");
            when(request.getRequestDispatcher("bodegas.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verificación: Se llamó a delete()
            verify(mockDocument).delete();
            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test delete: " + e.getMessage());
        }
    }

    // =================================================================================
    // 4. TEST READ (Listar / Leer)
    // =================================================================================
    @Test
    void testListarBodegasConDatos() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);

            // --- Configuración especial para devolver DATOS REALES ---
            // 1. Mockear un documento individual (QueryDocumentSnapshot)
            QueryDocumentSnapshot mockDocSnap = mock(QueryDocumentSnapshot.class);
            Bodega bodegaSimulada = new Bodega(1L, "Bodega Test", "Dir Test", 100);

            // Cuando le pidan convertir a objeto, devuelve nuestra bodega simulada
            when(mockDocSnap.toObject(Bodega.class)).thenReturn(bodegaSimulada);

            // 2. Configurar la cadena para devolver una lista que contiene ese documento
            when(mockFirestore.collection("bodegas")).thenReturn(mockCollection);
            when(mockCollection.get()).thenReturn(mockQueryFuture);
            when(mockQueryFuture.get()).thenReturn(mockQuerySnapshot);

            // IMPORTANTE: Aquí devolvemos la lista con el documento mockeado
            when(mockQuerySnapshot.getDocuments()).thenReturn(List.of(mockDocSnap));
            // --------------------------------------------------------

            BodegaServlet servlet = new BodegaServlet();

            // Acción listar
            when(request.getParameter("accion")).thenReturn("listar");
            when(request.getRequestDispatcher("bodegas.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verificación:
            // 1. Verificamos que se guardó una lista en el atributo "bodegas"
            verify(request).setAttribute(eq("bodegas"), any(List.class));

            // 2. (Opcional) Podríamos capturar el argumento para ver si la lista tiene tamaño 1
            // pero con verificar que se llamó al setAttribute es suficiente por ahora.

            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test listar: " + e.getMessage());
        }
    }
}