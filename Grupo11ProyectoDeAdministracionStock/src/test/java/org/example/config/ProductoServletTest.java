package org.example.config;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.controlador.ProductoServlet; // Asegúrate que este import sea correcto (controlador o logica)
// Si tu Servlet está en 'logica', cambia la línea de arriba a: import org.example.logica.ProductoServlet;
import org.example.modelo.Producto;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de Integración para ProductoServlet")
class ProductoServletTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock RequestDispatcher dispatcher;

    // Mocks de Firestore
    @Mock Firestore mockFirestore;
    @Mock CollectionReference mockCollection;
    @Mock DocumentReference mockDocument;
    @Mock ApiFuture<QuerySnapshot> mockQueryFuture;
    @Mock QuerySnapshot mockQuerySnapshot;
    @Mock ApiFuture<WriteResult> mockWriteFuture;

    // Nombre del JSP correcto (Debe coincidir con tu Servlet)
    private static final String JSP_PAGE = "producto.jsp";

    private void setupMockFirebaseListarVacio() throws Exception {
        lenient().when(mockFirestore.collection("productos")).thenReturn(mockCollection);
        lenient().when(mockCollection.get()).thenReturn(mockQueryFuture);
        lenient().when(mockQueryFuture.get()).thenReturn(mockQuerySnapshot);
        lenient().when(mockQuerySnapshot.getDocuments()).thenReturn(Collections.emptyList());
    }

    @Test
    @DisplayName("Debe capturar error de formato numérico cuando se ingresan letras en precio o cantidad")
    void testErrorFormatoNumerico() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            ProductoServlet servlet = new ProductoServlet();

            when(request.getParameter("accion")).thenReturn("crear");
            // Datos inválidos (letras en precio)
            when(request.getParameter("codigoBarra")).thenReturn("101");
            when(request.getParameter("nombre")).thenReturn("Galletas");
            when(request.getParameter("precio")).thenReturn("mil");
            when(request.getParameter("categoria")).thenReturn("Snacks");
            when(request.getParameter("cantidad")).thenReturn("10");

            // 👇 CAMBIO IMPORTANTE: Nombre del archivo corregido
            when(request.getRequestDispatcher(JSP_PAGE)).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verificamos que se setee el error y se redirija
            verify(request).setAttribute(eq("error"), contains("formato válido"));
            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
            fail("Excepción no esperada: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe mostrar error si se intenta crear un producto con campos vacíos")
    void testCrearProductoCamposVacios() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            ProductoServlet servlet = new ProductoServlet();

            when(request.getParameter("accion")).thenReturn("crear");
            when(request.getParameter("codigoBarra")).thenReturn(""); // Vacío
            when(request.getParameter("nombre")).thenReturn("");
            when(request.getParameter("precio")).thenReturn("");
            when(request.getParameter("categoria")).thenReturn("");
            when(request.getParameter("cantidad")).thenReturn("");

            when(request.getRequestDispatcher(JSP_PAGE)).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(request).setAttribute(eq("error"), contains("obligatorios"));
            verify(mockCollection, never()).document(anyString()); // Aseguramos que NO guardó nada
            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe crear un producto exitosamente en Firebase con datos válidos")
    void testCrearProductoExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            // Simulamos escritura exitosa
            when(mockCollection.document("1001")).thenReturn(mockDocument);
            when(mockDocument.set(any(Producto.class))).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            ProductoServlet servlet = new ProductoServlet();

            when(request.getParameter("accion")).thenReturn("crear");
            when(request.getParameter("codigoBarra")).thenReturn("1001");
            when(request.getParameter("nombre")).thenReturn("Coca Cola");
            when(request.getParameter("precio")).thenReturn("1500");
            when(request.getParameter("categoria")).thenReturn("Bebidas");
            when(request.getParameter("cantidad")).thenReturn("50");

            when(request.getRequestDispatcher(JSP_PAGE)).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(mockDocument).set(any(Producto.class)); // Verificamos que llamó a Firebase
            verify(dispatcher).forward(request, response); // Verificamos que refrescó la lista
        } catch (Exception e) {
            fail("Error en test create: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe actualizar un producto correctamente con todos sus campos")
    void testActualizarProductoExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            when(mockCollection.document("1001")).thenReturn(mockDocument);
            when(mockDocument.update(any(Map.class))).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            ProductoServlet servlet = new ProductoServlet();

            when(request.getParameter("accion")).thenReturn("actualizar");
            when(request.getParameter("codigoBarra")).thenReturn("1001");
            when(request.getParameter("nombre")).thenReturn("Coca Cola Zero");
            when(request.getParameter("precio")).thenReturn("1600");
            when(request.getParameter("categoria")).thenReturn("Bebidas");
            when(request.getParameter("cantidad")).thenReturn("80");

            when(request.getRequestDispatcher(JSP_PAGE)).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(mockDocument).update(any(Map.class));
            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
            fail("Error en test update: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe eliminar un producto de Firebase dado su código de barra")
    void testEliminarProductoExitoso() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);
            setupMockFirebaseListarVacio();

            when(mockCollection.document("1001")).thenReturn(mockDocument);
            when(mockDocument.delete()).thenReturn(mockWriteFuture);
            when(mockWriteFuture.get()).thenReturn(null);

            ProductoServlet servlet = new ProductoServlet();

            when(request.getParameter("accion")).thenReturn("eliminar");
            when(request.getParameter("codigoBarra")).thenReturn("1001");

            when(request.getRequestDispatcher(JSP_PAGE)).thenReturn(dispatcher);

            servlet.doPost(request, response);

            verify(mockDocument).delete();
            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
            fail("Error en test delete: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe listar productos recuperados de la base de datos y enviarlos al JSP")
    void testListarProductosConDatos() {
        try (MockedStatic<FirestoreClient> mockedFirestoreClient = Mockito.mockStatic(FirestoreClient.class)) {
            mockedFirestoreClient.when(FirestoreClient::getFirestore).thenReturn(mockFirestore);

            // Simulamos que Firebase devuelve 1 documento
            QueryDocumentSnapshot mockDocSnap = mock(QueryDocumentSnapshot.class);
            Producto productoSimulado = new Producto(1001L, "Papas Fritas", 2000, "Snacks", 10);
            when(mockDocSnap.toObject(Producto.class)).thenReturn(productoSimulado);

            when(mockFirestore.collection("productos")).thenReturn(mockCollection);
            when(mockCollection.get()).thenReturn(mockQueryFuture);
            when(mockQueryFuture.get()).thenReturn(mockQuerySnapshot);
            when(mockQuerySnapshot.getDocuments()).thenReturn(List.of(mockDocSnap));

            ProductoServlet servlet = new ProductoServlet();

            when(request.getParameter("accion")).thenReturn("listar");
            when(request.getRequestDispatcher(JSP_PAGE)).thenReturn(dispatcher);

            servlet.doPost(request, response);

            // Verificamos que se puso la lista en el request
            verify(request).setAttribute(eq("productos"), any(List.class));
            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
            fail("Error en test listar: " + e.getMessage());
        }
    }
}