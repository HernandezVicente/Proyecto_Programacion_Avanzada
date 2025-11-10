package org.example.logica;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.modelo.Bodega;

import java.io.IOException;
import java.util.List;

@WebServlet("/BodegaServlet")
public class BodegaServlet extends HttpServlet {
    private final LogicaBodega logica = new LogicaBodega();

    //este es un comentario en java

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            switch (accion) {
                case "crear": {
                    String idStr = request.getParameter("id");
                    String nombre = request.getParameter("nombre");
                    String direccion = request.getParameter("direccion");
                    String capacidadStr = request.getParameter("capacidad");

                    if (idStr == null || idStr.isEmpty() ||
                            nombre == null || nombre.isEmpty() ||
                            direccion == null || direccion.isEmpty() ||
                            capacidadStr == null || capacidadStr.isEmpty()) {
                        request.setAttribute("error", "Todos los campos son obligatorios para crear una bodega.");
                        break;
                    }

                    long id = Long.parseLong(idStr);
                    int capacidad = Integer.parseInt(capacidadStr);

                    Bodega nueva = new Bodega(id, nombre, direccion, capacidad);
                    logica.crearBodega(nueva);
                    break;
                }

                case "actualizar": {
                    String idStr = request.getParameter("id");
                    String capacidadStr = request.getParameter("capacidad");

                    if (idStr == null || idStr.isEmpty() ||
                            capacidadStr == null || capacidadStr.isEmpty()) {
                        request.setAttribute("error", "Debe indicar ID y nueva capacidad para actualizar.");
                        break;
                    }

                    long id = Long.parseLong(idStr);
                    int capacidad = Integer.parseInt(capacidadStr);

                    logica.actualizarCapacidad(id, capacidad);
                    break;
                }

                case "eliminar": {
                    String idStr = request.getParameter("id");
                    if (idStr == null || idStr.isEmpty()) {
                        request.setAttribute("error", "Debe indicar el ID de la bodega a eliminar.");
                        break;
                    }

                    long id = Long.parseLong(idStr);
                    logica.eliminarBodega(id);
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
            List<Bodega> bodegas = logica.listarBodegas();
            request.setAttribute("bodegas", bodegas);
            request.getRequestDispatcher("bodegas.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Error: uno de los campos numéricos no tiene formato válido.");
            request.getRequestDispatcher("bodegas.jsp").forward(request, response);
        }
    }
}
