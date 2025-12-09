package org.example.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.logica.LogicaProducto;
import org.example.modelo.Producto;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ProductoServlet.class);
    private static final String ATTRIBUTE_ERROR = "error";
    private static final String JSP_PAGE = "producto.jsp";
    private final transient LogicaProducto logica;

    public ProductoServlet() {
        this.logica = new LogicaProducto();
    }

    public ProductoServlet(LogicaProducto logica) {
        this.logica = logica;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
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
                    crearProducto(request);
                    break;
                case "actualizar":
                    actualizarProducto(request);
                    break;
                case "eliminar":
                    eliminarProducto(request);
                    break;
                case "listar":
                    break;
                default:
                    request.setAttribute(ATTRIBUTE_ERROR, "Acción no reconocida.");
            }

            refrescarLista(request, response);

        } catch (NumberFormatException e) {
            logger.warn("Error de formato en ProductoServlet: {}", e.getMessage());
            request.setAttribute(ATTRIBUTE_ERROR, "Error: uno de los campos numéricos no tiene un formato válido.");

            try {
                refrescarLista(request, response);
            } catch (Exception _) {
                request.getRequestDispatcher(JSP_PAGE).forward(request, response);
            }

        } catch (ServletException | IOException e) {
            logger.error("Error crítico al procesar la solicitud", e);
            request.setAttribute(ATTRIBUTE_ERROR, "Error interno del servidor.");
            request.getRequestDispatcher(JSP_PAGE).forward(request, response);
        }
    }

    private void crearProducto(HttpServletRequest request) {
        String codigoStr = request.getParameter("codigoBarra");
        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String categoria = request.getParameter("categoria");
        String cantidadStr = request.getParameter("cantidad");

        if (esInvalido(codigoStr) || esInvalido(nombre) || esInvalido(precioStr) ||
                esInvalido(categoria) || esInvalido(cantidadStr)) {
            request.setAttribute(ATTRIBUTE_ERROR, "Todos los campos son obligatorios para crear un producto.");
            return;
        }

        long codigo = Long.parseLong(codigoStr);
        int precio = Integer.parseInt(precioStr);
        int cantidad = Integer.parseInt(cantidadStr);

        Producto nuevo = new Producto(codigo, nombre, precio, categoria, cantidad);
        logica.crearProducto(nuevo);
        logger.info("Producto creado: {}", nombre);
    }

    private void actualizarProducto(HttpServletRequest request) {
        String codigoStr = request.getParameter("codigoBarra");
        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String categoria = request.getParameter("categoria");
        String cantidadStr = request.getParameter("cantidad");

        if (esInvalido(codigoStr) || esInvalido(nombre) || esInvalido(precioStr) ||
                esInvalido(categoria) || esInvalido(cantidadStr)) {
            request.setAttribute(ATTRIBUTE_ERROR, "Todos los campos son obligatorios para actualizar.");
            return;
        }

        long codigo = Long.parseLong(codigoStr);
        int precio = Integer.parseInt(precioStr);
        int cantidad = Integer.parseInt(cantidadStr);

        Producto productoActualizado = new Producto(codigo, nombre, precio, categoria, cantidad);
        logica.actualizarProducto(productoActualizado);
        logger.info("Producto actualizado ID: {}", codigo);
    }

    private void eliminarProducto(HttpServletRequest request) {
        String codigoStr = request.getParameter("codigoBarra");

        if (esInvalido(codigoStr)) {
            request.setAttribute(ATTRIBUTE_ERROR, "Debe indicar el código del producto a eliminar.");
            return;
        }

        long codigo = Long.parseLong(codigoStr);
        logica.eliminarProducto(codigo);
        logger.info("Producto eliminado ID: {}", codigo);
    }

    private void refrescarLista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Producto> productos = logica.listarProductos();
        request.setAttribute("productos", productos);
        request.getRequestDispatcher(JSP_PAGE).forward(request, response);
    }

    private boolean esInvalido(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}