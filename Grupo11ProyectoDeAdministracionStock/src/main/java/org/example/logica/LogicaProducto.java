package org.example.logica;

import org.example.modelo.Producto;
import java.util.List;
import java.util.Map;


public class LogicaProducto {
    private final CRUDFireStore<Producto> crud = new CRUDFireStore<>("productos", Producto.class);

    public void crearProducto(Producto p) {
        crud.guardar(String.valueOf(p.getCodigoBarra()), p);
    }

    public Producto obtenerProducto(long codigo) {
        return crud.obtenerPorId(String.valueOf(codigo));
    }

    public List<Producto> listarProductos() {
        return crud.obtenerTodos();
    }

    public void actualizarProducto(long codigo, int nuevaCantidad) {
        crud.actualizarCampos(String.valueOf(codigo), Map.of("cantidad", nuevaCantidad));
    }

    public void eliminarProducto(long codigo) {
        crud.eliminar(String.valueOf(codigo));
    }
}
