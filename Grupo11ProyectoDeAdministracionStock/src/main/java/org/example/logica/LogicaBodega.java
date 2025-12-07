package org.example.logica;

import org.example.modelo.Bodega;
import java.util.List;
import java.util.Map;

public class LogicaBodega {
    private final CRUDFireStore<Bodega> crud = new CRUDFireStore<>("bodegas", Bodega.class);

    public void crearBodega(Bodega b) {
        crud.guardar(String.valueOf(b.getId()), b);
    }

    public List<Bodega> listarBodegas() {
        return crud.obtenerTodos();
    }

    public void actualizarBodega(Bodega b) {
        Map<String, Object> datos = Map.of(
                "nombre", b.getNombre(),
                "direccion", b.getDireccion(),
                "capacidad", b.getCapacidad()
        );
        crud.actualizarCampos(String.valueOf(b.getId()), datos);
    }

    public void eliminarBodega(long id) {
        crud.eliminar(String.valueOf(id));
    }
}