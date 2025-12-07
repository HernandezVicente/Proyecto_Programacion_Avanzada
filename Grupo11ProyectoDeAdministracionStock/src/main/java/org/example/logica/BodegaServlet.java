package org.example.logica;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.modelo.Bodega;

import java.io.IOException;
import java.util.List;

@WebServlet("/BodegaServlet")
public class BodegaServlet extends HttpServlet {
    final private LogicaBodega logica;

    public BodegaServlet() {
        this.logica = new LogicaBodega();
    }

    public BodegaServlet(LogicaBodega logica) {
        this.logica = logica;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        refrescarLista(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            switch (accion) {
                case "crear":
                    crearBodega(request);
                    break;
                case "actualizar":
                    actualizarBodega(request);
                    break;
                case "eliminar":
                    eliminarBodega(request);
                    break;
                case "listar":
                    break;
                default:
                    request.setAttribute("error", "Acción no reconocida.");
            }
            refrescarLista(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Error: uno de los campos numéricos no tiene formato válido.");
            try {
                refrescarLista(request, response);
            } catch (Exception ex) {
                request.getRequestDispatcher("bodega.jsp").forward(request, response);
            }
        }
    }

    private void crearBodega(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String capacidadStr = request.getParameter("capacidad");

        if (esInvalido(idStr) || esInvalido(nombre) || esInvalido(direccion) || esInvalido(capacidadStr)) {
            request.setAttribute("error", "Todos los campos son obligatorios para crear una bodega.");
            return;
        }

        long id = Long.parseLong(idStr);
        int capacidad = Integer.parseInt(capacidadStr);

        Bodega nueva = new Bodega(id, nombre, direccion, capacidad);
        logica.crearBodega(nueva);
    }

    private void actualizarBodega(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String capacidadStr = request.getParameter("capacidad");

        if (esInvalido(idStr) || esInvalido(nombre) || esInvalido(direccion) || esInvalido(capacidadStr)) {
            request.setAttribute("error", "Todos los campos son obligatorios para actualizar.");
            return;
        }

        long id = Long.parseLong(idStr);
        int capacidad = Integer.parseInt(capacidadStr);

        Bodega bodegaActualizada = new Bodega(id, nombre, direccion, capacidad);
        logica.actualizarBodega(bodegaActualizada);
    }

    private void eliminarBodega(HttpServletRequest request) {
        String idStr = request.getParameter("id");

        if (esInvalido(idStr)) {
            request.setAttribute("error", "Debe indicar el ID de la bodega a eliminar.");
            return;
        }

        long id = Long.parseLong(idStr);
        logica.eliminarBodega(id);
    }

    private void refrescarLista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Bodega> bodegas = logica.listarBodegas();
        request.setAttribute("bodegas", bodegas);
        request.getRequestDispatcher("bodega.jsp").forward(request, response);
    }

    private boolean esInvalido(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}