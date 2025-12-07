package org.example.logica;

import org.example.modelo.Producto;
import java.util.List;
import java.util.Map;

public class LogicaProducto {
    private final CRUDFireStore<Producto> crud = new CRUDFireStore<>("productos", Producto.class);

    /**
     * Crea un nuevo producto en la base de datos.
     * Usa el código de barra como ID del documento.
     */
    public void crearProducto(Producto p) {
        crud.guardar(String.valueOf(p.getCodigoBarra()), p);
    }

    /**
     * Obtiene la lista de todos los productos.
     */
    public List<Producto> listarProductos() {
        return crud.obtenerTodos();
    }

    /**
     * Actualiza TODOS los campos de un producto existente.
     * Recibe el objeto producto con los nuevos datos.
     * El ID (codigoBarra) se usa para buscar el documento, pero no se modifica.
     */
    public void actualizarProducto(Producto p) {
        Map<String, Object> datos = Map.of(
                "nombre", p.getNombre(),
                "precio", p.getPrecio(),
                "categoria", p.getCategoria(),
                "cantidad", p.getCantidad()
        );
        crud.actualizarCampos(String.valueOf(p.getCodigoBarra()), datos);
    }

    /**
     * Elimina un producto de la base de datos.
     */
    public void eliminarProducto(long codigo) {
        crud.eliminar(String.valueOf(codigo));
    }
}