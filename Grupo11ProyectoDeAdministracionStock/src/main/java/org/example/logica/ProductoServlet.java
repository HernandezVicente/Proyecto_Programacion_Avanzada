package org.example.logica;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.modelo.Producto;

import java.io.IOException;
import java.util.List;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {
    private final LogicaProducto logica = new LogicaProducto();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            switch (accion) {
                case "crear": {
                    // ✅ Validamos campos
                    String codigoStr = request.getParameter("codigoBarra");
                    String nombre = request.getParameter("nombre");
                    String precioStr = request.getParameter("precio");
                    String categoria = request.getParameter("categoria");
                    String cantidadStr = request.getParameter("cantidad");

                    if (codigoStr == null || codigoStr.isEmpty() ||
                            nombre == null || nombre.isEmpty() ||
                            precioStr == null || precioStr.isEmpty() ||
                            categoria == null || categoria.isEmpty() ||
                            cantidadStr == null || cantidadStr.isEmpty()) {
                        request.setAttribute("error", "Todos los campos son obligatorios para crear un producto.");
                        break;
                    }

                    long codigo = Long.parseLong(codigoStr);
                    int precio = Integer.parseInt(precioStr);
                    int cantidad = Integer.parseInt(cantidadStr);

                    Producto nuevo = new Producto(codigo, nombre, precio, categoria, cantidad);
                    logica.crearProducto(nuevo);
                    break;
                }

                case "actualizar": {
                    String codigoStr = request.getParameter("codigoBarra");
                    String cantidadStr = request.getParameter("cantidad");

                    if (codigoStr == null || codigoStr.isEmpty() ||
                            cantidadStr == null || cantidadStr.isEmpty()) {
                        request.setAttribute("error", "Debe indicar código y cantidad para actualizar.");
                        break;
                    }

                    long codigo = Long.parseLong(codigoStr);
                    int cantidad = Integer.parseInt(cantidadStr);

                    logica.actualizarProducto(codigo, cantidad);
                    break;
                }

                case "eliminar": {
                    String codigoStr = request.getParameter("codigoBarra");
                    if (codigoStr == null || codigoStr.isEmpty()) {
                        request.setAttribute("error", "Debe indicar el código del producto a eliminar.");
                        break;
                    }

                    long codigo = Long.parseLong(codigoStr);
                    logica.eliminarProducto(codigo);
                    break;
                }

                case "listar": {
                    // No requiere parámetros
                    break;
                }

                default:
                    request.setAttribute("error", "Acción no reconocida.");
            }

            // 🧩 Refresca la lista después de cualquier acción
            List<Producto> productos = logica.listarProductos();
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("productos.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Error: uno de los campos numéricos no tiene un formato válido.");
            request.getRequestDispatcher("productos.jsp").forward(request, response);
        }
    }
}
