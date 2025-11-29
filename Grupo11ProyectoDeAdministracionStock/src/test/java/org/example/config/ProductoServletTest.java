package org.example.config;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.logica.ProductoServlet;
import org.example.modelo.Producto;
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
class ProductoServletTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock RequestDispatcher dispatcher;

    // Mocks de Firebase
    @Mock Firestore mockFirestore;
    @Mock CollectionReference mockCollection;
    @Mock DocumentReference mockDocument;
    @Mock ApiFuture<QuerySnapshot> mockQueryFuture;
    @Mock QuerySnapshot mockQuerySnapshot;
    @Mock ApiFuture<WriteResult> mockWriteFuture;

    /**
     * Helper para simular que la base de datos está vacía al listar.
     * Evita NullPointerException porque el Servlet siempre llama a listarProductos() al final.
     */
    private void setupMockFirebaseListarVacio() throws Exception {
        // OJO: Aquí la colección se llama "productos"
        lenient().when(mockFirestore.collection("productos")).thenReturn(mockCollection);
        lenient().when(mockCollection.get()).thenReturn(mockQueryFuture);
        lenient().when(mockQueryFuture.get()).thenReturn(mockQuerySnapshot);
        lenient().when(mockQuerySnapshot.getDocuments()).thenReturn(Collections.emptyList());
    }

    // =================================================================================
    // 1. TESTS DE VALIDACIÓN (Formatos y Vacíos)
    // =================================================================================

    @Test
    void testErrorFormatoNumerico() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            ProductoServlet servlet = new ProductoServlet();

            when(request.getParameter("accion")).thenReturn("crear");
            when(request.getParameter("codigoBarra")).thenReturn("101");
            when(request.getParameter("nombre")).thenReturn("Galletas");
            // ❌ Error provocado: Texto en campo numérico
            when(request.getParameter("precio")).thenReturn("mil");
            when(request.getParameter("categoria")).thenReturn("Snacks");
            when(request.getParameter("cantidad")).thenReturn("10");
            when(request.getRequestDispatcher("productos.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(request).setAttribute(startsWith("error"), contains("formato válido"));
            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
            fail("Excepción no esperada: " + e.getMessage());
        }
    }

    @Test
    void testCrearProductoCamposVacios() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            ProductoServlet servlet = new ProductoServlet();

            // Datos Vacíos
            when(request.getParameter("accion")).thenReturn("crear");
            when(request.getParameter("codigoBarra")).thenReturn("");
            when(request.getParameter("nombre")).thenReturn("");
            when(request.getParameter("precio")).thenReturn("");
            when(request.getParameter("categoria")).thenReturn("");
            when(request.getParameter("cantidad")).thenReturn("");
            when(request.getRequestDispatcher("productos.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verifica validación de campos obligatorios [cite: 100]
            verify(request).setAttribute(eq("error"), contains("Todos los campos son obligatorios"));
            verify(mockCollection, never()).document(anyString()); // Asegura que no guardó
            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    // =================================================================================
    // 2. TEST CREATE (Crear)
    // =================================================================================
    @Test
    void testCrearProductoExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            // Simulamos GUARDADO (set)
            when(mockCollection.document("1001")).thenReturn(mockDocument);
            when(mockDocument.set(any(Producto.class))).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            ProductoServlet servlet = new ProductoServlet();

            // Datos VÁLIDOS
            when(request.getParameter("accion")).thenReturn("crear");
            when(request.getParameter("codigoBarra")).thenReturn("1001");
            when(request.getParameter("nombre")).thenReturn("Coca Cola");
            when(request.getParameter("precio")).thenReturn("1500");
            when(request.getParameter("categoria")).thenReturn("Bebidas");
            when(request.getParameter("cantidad")).thenReturn("50");
            when(request.getRequestDispatcher("productos.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verificamos que se guardó un objeto Producto
            verify(mockDocument).set(any(Producto.class));
            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test create: " + e.getMessage());
        }
    }

    // =================================================================================
    // 3. TEST UPDATE (Actualizar)
    // =================================================================================
    @Test
    void testActualizarProductoExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            // Simulamos ACTUALIZACIÓN (update)
            // La lógica de producto actualiza 'cantidad' [cite: 92]
            when(mockCollection.document("1001")).thenReturn(mockDocument);
            when(mockDocument.update(any(Map.class))).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            ProductoServlet servlet = new ProductoServlet();

            // Datos para actualizar (según tu servlet: codigo y cantidad son obligatorios)
            when(request.getParameter("accion")).thenReturn("actualizar");
            when(request.getParameter("codigoBarra")).thenReturn("1001");
            when(request.getParameter("cantidad")).thenReturn("80");
            when(request.getRequestDispatcher("productos.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verificamos que llamó a update con un Mapa
            verify(mockDocument).update(any(Map.class));
            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test update: " + e.getMessage());
        }
    }

    // =================================================================================
    // 4. TEST DELETE (Eliminar)
    // =================================================================================
    @Test
    void testEliminarProductoExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            // Simulamos ELIMINACIÓN (delete)
            when(mockCollection.document("1001")).thenReturn(mockDocument);
            when(mockDocument.delete()).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            ProductoServlet servlet = new ProductoServlet();

            when(request.getParameter("accion")).thenReturn("eliminar");
            when(request.getParameter("codigoBarra")).thenReturn("1001");
            when(request.getRequestDispatcher("productos.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(mockDocument).delete();
            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test delete: " + e.getMessage());
        }
    }

    // =================================================================================
    // 5. TEST READ (Listar)
    // =================================================================================
    @Test
    void testListarProductosConDatos() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);

            // --- Configuración para devolver DATOS REALES ---
            QueryDocumentSnapshot mockDocSnap = mock(QueryDocumentSnapshot.class);
            Producto productoSimulado = new Producto(1001L, "Papas Fritas", 2000, "Snacks", 10);

            when(mockDocSnap.toObject(Producto.class)).thenReturn(productoSimulado);

            when(mockFirestore.collection("productos")).thenReturn(mockCollection);
            when(mockCollection.get()).thenReturn(mockQueryFuture);
            when(mockQueryFuture.get()).thenReturn(mockQuerySnapshot);
            when(mockQuerySnapshot.getDocuments()).thenReturn(List.of(mockDocSnap));
            // --------------------------------------------------------

            ProductoServlet servlet = new ProductoServlet();

            when(request.getParameter("accion")).thenReturn("listar");
            when(request.getRequestDispatcher("productos.jsp")).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verificamos que guardó la lista en el request [cite: 112]
            verify(request).setAttribute(eq("productos"), any(List.class));
            verify(dispatcher).forward(request, response);

        } catch (Exception e) {
            fail("Error en test listar: " + e.getMessage());
        }
    }
}