package org.example.logica;

import org.example.modelo.Bodega;
import java.util.List;
import java.util.Map;

public class LogicaBodega {
    private final CRUDFireStore<Bodega> crud = new CRUDFireStore<>("bodegas", Bodega.class);

    public void crearBodega(Bodega b) {
        crud.guardar(String.valueOf(b.getId()), b);
    }

    public Bodega obtenerBodega(long id) {
        return crud.obtenerPorId(String.valueOf(id));
    }

    public List<Bodega> listarBodegas() {
        return crud.obtenerTodos();
    }

    public void actualizarCapacidad(long id, int nuevaCapacidad) {
        crud.actualizarCampos(String.valueOf(id), Map.of("capacidad", nuevaCapacidad));
    }

    public void eliminarBodega(long id) {
        crud.eliminar(String.valueOf(id));
    }
}
