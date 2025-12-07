package org.example.config;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.logica.BodegaServlet;
import org.example.modelo.Bodega;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("Pruebas de Integración para BodegaServlet")
class BodegaServletTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock RequestDispatcher dispatcher;

    @Mock Firestore mockFirestore;
    @Mock CollectionReference mockCollection;
    @Mock DocumentReference mockDocument;
    @Mock ApiFuture<QuerySnapshot> mockQueryFuture;
    @Mock QuerySnapshot mockQuerySnapshot;
    @Mock ApiFuture<WriteResult> mockWriteFuture;

    private void setupMockFirebaseListarVacio() throws Exception {
        lenient().when(mockFirestore.collection("bodegas")).thenReturn(mockCollection);
        lenient().when(mockCollection.get()).thenReturn(mockQueryFuture);
        lenient().when(mockQueryFuture.get()).thenReturn(mockQuerySnapshot);
        lenient().when(mockQuerySnapshot.getDocuments()).thenReturn(Collections.emptyList());
    }

    @Test
    @DisplayName("Debe mostrar un error cuando se intenta crear una bodega con campos vacíos")
    void testCrearBodegaFallaPorCamposVacios() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            BodegaServlet servlet = new BodegaServlet();
            when(request.getParameter("accion")).thenReturn("crear");
            when(request.getParameter("id")).thenReturn("");
            when(request.getParameter("nombre")).thenReturn("");
            when(request.getParameter("direccion")).thenReturn("");
            when(request.getParameter("capacidad")).thenReturn("");
            when(request.getRequestDispatcher("bodega.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(request).setAttribute(eq("error"), anyString());
            verify(mockCollection, never()).document(anyString());
            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe crear una bodega exitosamente cuando todos los datos son válidos")
    void testCrearBodegaExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            when(mockCollection.document("123")).thenReturn(mockDocument);
            when(mockDocument.set(any(Bodega.class))).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            BodegaServlet servlet = new BodegaServlet();
            when(request.getParameter("accion")).thenReturn("crear");
            when(request.getParameter("id")).thenReturn("123");
            when(request.getParameter("nombre")).thenReturn("Bodega Norte");
            when(request.getParameter("direccion")).thenReturn("Calle Falsa 123");
            when(request.getParameter("capacidad")).thenReturn("500");
            when(request.getRequestDispatcher("bodega.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(mockDocument).set(any(Bodega.class));
            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
            fail("Error en test create: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe actualizar una bodega correctamente enviando todos los campos requeridos")
    void testActualizarBodegaExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            when(mockCollection.document("123")).thenReturn(mockDocument);
            when(mockDocument.update(any(Map.class))).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            BodegaServlet servlet = new BodegaServlet();
            when(request.getParameter("accion")).thenReturn("actualizar");
            when(request.getParameter("id")).thenReturn("123");
            when(request.getParameter("nombre")).thenReturn("Bodega Sur Editada");
            when(request.getParameter("direccion")).thenReturn("Nueva Dirección 456");
            when(request.getParameter("capacidad")).thenReturn("900");
            when(request.getRequestDispatcher("bodega.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(mockDocument).update(any(Map.class));
            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test update: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe eliminar una bodega llamando al método delete de Firestore")
    void testEliminarBodegaExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            when(mockCollection.document("999")).thenReturn(mockDocument);
            when(mockDocument.delete()).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            BodegaServlet servlet = new BodegaServlet();

            when(request.getParameter("accion")).thenReturn("eliminar");
            when(request.getParameter("id")).thenReturn("999");
            when(request.getRequestDispatcher("bodega.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(mockDocument).delete();
            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test delete: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe listar las bodegas existentes y guardarlas en el atributo del request")
    void testListarBodegasConDatos() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);

            QueryDocumentSnapshot mockDocSnap = mock(QueryDocumentSnapshot.class);
            Bodega bodegaSimulada = new Bodega(1L, "Bodega Test", "Dir Test", 100);
            when(mockDocSnap.toObject(Bodega.class)).thenReturn(bodegaSimulada);

            when(mockFirestore.collection("bodegas")).thenReturn(mockCollection);
            when(mockCollection.get()).thenReturn(mockQueryFuture);
            when(mockQueryFuture.get()).thenReturn(mockQuerySnapshot);
            when(mockQuerySnapshot.getDocuments()).thenReturn(List.of(mockDocSnap));

            BodegaServlet servlet = new BodegaServlet();

            when(request.getParameter("accion")).thenReturn("listar");
            when(request.getRequestDispatcher("bodega.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(request).setAttribute(eq("bodegas"), any(List.class));
            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test listar: " + e.getMessage());
        }
    }
}