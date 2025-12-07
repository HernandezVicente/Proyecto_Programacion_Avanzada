package org.example.logica;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.modelo.Producto;

import java.io.IOException;
import java.util.List;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {
    private LogicaProducto logica;

    public ProductoServlet() {
        this.logica = new LogicaProducto();
    }

    public ProductoServlet(LogicaProducto logica) {
        this.logica = logica;
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
                    request.setAttribute("error", "Acción no reconocida.");
            }
            refrescarLista(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Error: uno de los campos numéricos no tiene un formato válido.");
            try {
                refrescarLista(request, response);
            } catch (Exception ex) {
                request.getRequestDispatcher("producto.jsp").forward(request, response);
            }
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
            request.setAttribute("error", "Todos los campos son obligatorios para crear un producto.");
            return;
        }

        long codigo = Long.parseLong(codigoStr);
        int precio = Integer.parseInt(precioStr);
        int cantidad = Integer.parseInt(cantidadStr);

        Producto nuevo = new Producto(codigo, nombre, precio, categoria, cantidad);
        logica.crearProducto(nuevo);
    }

    private void actualizarProducto(HttpServletRequest request) {
        String codigoStr = request.getParameter("codigoBarra");
        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String categoria = request.getParameter("categoria");
        String cantidadStr = request.getParameter("cantidad");

        if (esInvalido(codigoStr) || esInvalido(nombre) || esInvalido(precioStr) ||
                esInvalido(categoria) || esInvalido(cantidadStr)) {
            request.setAttribute("error", "Todos los campos son obligatorios para actualizar.");
            return;
        }

        long codigo = Long.parseLong(codigoStr);
        int precio = Integer.parseInt(precioStr);
        int cantidad = Integer.parseInt(cantidadStr);

        Producto productoActualizado = new Producto(codigo, nombre, precio, categoria, cantidad);
        logica.actualizarProducto(productoActualizado);
    }

    private void eliminarProducto(HttpServletRequest request) {
        String codigoStr = request.getParameter("codigoBarra");

        if (esInvalido(codigoStr)) {
            request.setAttribute("error", "Debe indicar el código del producto a eliminar.");
            return;
        }

        long codigo = Long.parseLong(codigoStr);
        logica.eliminarProducto(codigo);
    }

    private void refrescarLista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Producto> productos = logica.listarProductos();
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("producto.jsp").forward(request, response);
    }

    private boolean esInvalido(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}