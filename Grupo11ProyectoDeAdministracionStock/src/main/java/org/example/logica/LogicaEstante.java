package org.example.logica;

import org.example.modelo.Estante;

public class LogicaEstante {
    //private final CRUDFireStore<Estante> crud = new CRUDFireStore<>(Estante.class);
    private static final String COLECCION = "estantes";

    public void agregarEstante(Estante estante) throws Exception {
    //    crud.crear(COLECCION, String.valueOf(estante.getCodigo()), estante);
    }

    //public Estante obtenerEstante(long codigo) throws Exception {return crud.leer(COLECCION, String.valueOf(codigo));}

    //public java.util.List<Estante> listarEstantes() throws Exception {return crud.listar(COLECCION);}

    public void actualizarEstante(Estante estante) throws Exception {
     //   crud.actualizar(COLECCION, String.valueOf(estante.getCodigo()), estante);
    }

    public void eliminarEstante(long codigo) throws Exception {
      //  crud.eliminar(COLECCION, String.valueOf(codigo));
    }
}
