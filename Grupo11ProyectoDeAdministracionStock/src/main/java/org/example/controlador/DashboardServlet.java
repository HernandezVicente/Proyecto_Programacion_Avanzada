package org.example.controlador; // O package org.example.logica;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet que gestiona la visualización del Panel Principal (Dashboard).
 * Se encarga de verificar la seguridad antes de mostrar el menú.
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Obtener la sesión actual (no creamos una nueva si no existe)
        HttpSession session = req.getSession(false);

        // 2. Verificar Seguridad: ¿Hay un administrador logueado?
        if (session == null || session.getAttribute("admin") == null) {
            // Si no hay sesión o no es admin, redirigir al login
            resp.sendRedirect("loginAdmin.jsp?error=acceso");
            return;
        }

        // 3. (Opcional) Aquí podrías cargar datos extras para el dashboard
        // Por ejemplo: int totalProductos = logicaProducto.contar();
        // req.setAttribute("totalProductos", totalProductos);

        // 4. Si todo está bien, mostramos el JSP
        req.getRequestDispatcher("dashboardAdmin.jsp").forward(req, resp);
    }
}