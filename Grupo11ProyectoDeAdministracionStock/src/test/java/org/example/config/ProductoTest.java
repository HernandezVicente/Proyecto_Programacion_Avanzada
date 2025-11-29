package org.example.config;

import org.example.modelo.Producto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    @DisplayName("Debe crear un producto correctamente usando el constructor completo")
    void testConstructorCompletoYGetters() {
        // Arrange (Preparar)
        long codigo = 101L;
        String nombre = "Coca Cola";
        int precio = 1500;
        String categoria = "Bebidas";
        int cantidad = 50;

        // Act (Ejecutar) [cite: 137]
        Producto producto = new Producto(codigo, nombre, precio, categoria, cantidad);

        // Assert (Verificar) [cite: 139, 141, 143, 145, 147]
        assertAll("Verificando todas las propiedades del constructor completo",
                () -> assertEquals(codigo, producto.getCodigoBarra()),
                () -> assertEquals(nombre, producto.getNombre()),
                () -> assertEquals(precio, producto.getPrecio()),
                () -> assertEquals(categoria, producto.getCategoria()),
                () -> assertEquals(cantidad, producto.getCantidad())
        );
    }

    @Test
    @DisplayName("Debe instanciar un objeto vacío (Requerido por Firestore)")
    void testConstructorVacio() {
        // Act [cite: 136]
        Producto producto = new Producto();

        // Assert
        assertNotNull(producto, "El objeto no debería ser nulo");
        // Verificamos que los valores primitivos numéricos inician en 0 por defecto
        assertEquals(0, producto.getCodigoBarra());
        assertEquals(0, producto.getPrecio());
    }

    @Test
    @DisplayName("Debe permitir asignar y leer valores usando Setters y Getters")
    void testSettersYGetters() {
        // Arrange
        Producto producto = new Producto();

        // Act (Usamos los Setters) [cite: 140, 142, 144, 146, 148]
        producto.setCodigoBarra(999L);
        producto.setNombre("Papas Fritas");
        producto.setPrecio(2000);
        producto.setCategoria("Snacks");
        producto.setCantidad(10);

        // Assert (Verificamos con los Getters)
        assertEquals(999L, producto.getCodigoBarra());
        assertEquals("Papas Fritas", producto.getNombre());
        assertEquals(2000, producto.getPrecio());
        assertEquals("Snacks", producto.getCategoria());
        assertEquals(10, producto.getCantidad());
    }

    @Test
    @DisplayName("Debe actualizar los valores de un producto existente")
    void testModificarProducto() {
        // Arrange: Creamos un producto inicial
        Producto producto = new Producto(1L, "Agua", 500, "Bebidas", 100);

        // Act: Cambiamos el precio y la cantidad
        producto.setPrecio(600); // Subió de precio
        producto.setCantidad(99); // Se vendió una

        // Assert
        assertEquals(600, producto.getPrecio());
        assertEquals(99, producto.getCantidad());
        // Verificamos que el nombre NO cambió
        assertEquals("Agua", producto.getNombre());
    }

    @Test
    @DisplayName("Debe generar el String correcto con toString()")
    void testToString() {
        // Arrange
        Producto producto = new Producto(55L, "Test", 100, "TestCat", 5);

        // Act
        String resultado = producto.toString();

        // Assert: Verificamos que el string contenga los datos clave
        // Esto es útil para asegurarnos de que los logs muestren info real
        assertTrue(resultado.contains("codigoBarra=55"));
        assertTrue(resultado.contains("nombre='Test'"));
        assertTrue(resultado.contains("precio=100"));
    }
}