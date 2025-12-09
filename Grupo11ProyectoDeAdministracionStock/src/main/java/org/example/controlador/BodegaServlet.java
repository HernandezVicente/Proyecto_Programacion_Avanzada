package org.example.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.logica.LogicaBodega;
import org.example.modelo.Bodega;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "BodegaServlet", urlPatterns = {"/BodegaServlet"})
public class BodegaServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(BodegaServlet.class);
    private static final String ATTRIBUTE_ERROR = "error";
    private static final String JSP_PAGE = "bodega.jsp";

    private final transient LogicaBodega logica;

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
                    request.setAttribute(ATTRIBUTE_ERROR, "Acción no reconocida.");
            }

            refrescarLista(request, response);

        } catch (NumberFormatException e) {
            logger.warn("Error de formato numérico en BodegaServlet: {}", e.getMessage());
            request.setAttribute(ATTRIBUTE_ERROR, "Error: uno de los campos numéricos no tiene formato válido.");

            try {
                refrescarLista(request, response);
            } catch (Exception _) {
                request.getRequestDispatcher(JSP_PAGE).forward(request, response);
            }

        } catch (ServletException | IOException e) {
            logger.error("Error crítico al procesar la solicitud en BodegaServlet", e);
            request.setAttribute(ATTRIBUTE_ERROR, "Error interno del servidor.");
            request.getRequestDispatcher(JSP_PAGE).forward(request, response);
        }
    }

    private void crearBodega(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String capacidadStr = request.getParameter("capacidad");

        if (esInvalido(idStr) || esInvalido(nombre) || esInvalido(direccion) || esInvalido(capacidadStr)) {
            request.setAttribute(ATTRIBUTE_ERROR, "Todos los campos son obligatorios para crear una bodega.");
            return;
        }

        long id = Long.parseLong(idStr);
        int capacidad = Integer.parseInt(capacidadStr);

        Bodega nueva = new Bodega(id, nombre, direccion, capacidad);
        logica.crearBodega(nueva);
        logger.info("Bodega creada: {}", nombre);
    }

    private void actualizarBodega(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String capacidadStr = request.getParameter("capacidad");

        if (esInvalido(idStr) || esInvalido(nombre) || esInvalido(direccion) || esInvalido(capacidadStr)) {
            request.setAttribute(ATTRIBUTE_ERROR, "Todos los campos son obligatorios para actualizar.");
            return;
        }

        long id = Long.parseLong(idStr);
        int capacidad = Integer.parseInt(capacidadStr);

        Bodega bodegaActualizada = new Bodega(id, nombre, direccion, capacidad);
        logica.actualizarBodega(bodegaActualizada);
        logger.info("Bodega actualizada ID: {}", id);
    }

    private void eliminarBodega(HttpServletRequest request) {
        String idStr = request.getParameter("id");

        if (esInvalido(idStr)) {
            request.setAttribute(ATTRIBUTE_ERROR, "Debe indicar el ID de la bodega a eliminar.");
            return;
        }

        long id = Long.parseLong(idStr);
        logica.eliminarBodega(id);
        logger.info("Bodega eliminada ID: {}", id);
    }

    private void refrescarLista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Bodega> bodegas = logica.listarBodegas();
        request.setAttribute("bodegas", bodegas);
        request.getRequestDispatcher(JSP_PAGE).forward(request, response);
    }

    private boolean esInvalido(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}