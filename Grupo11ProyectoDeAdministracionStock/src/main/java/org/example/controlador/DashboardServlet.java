package org.example.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet Maestro para el Dashboard.
 * Decide si mostrar la vista de ADMIN o de CLIENTE según la sesión.
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Obtener la sesión actual
        HttpSession session = req.getSession(false);

        // 2. Si no hay sesión, al lobby (index)
        if (session == null) {
            resp.sendRedirect("index.jsp");
            return;
        }

        // 3. DECISIÓN: ¿Quién es?

        // CASO A: Es Administrador
        if (session.getAttribute("admin") != null) {
            // Lo mandamos al panel de control completo
            req.getRequestDispatcher("dashboardAdmin.jsp").forward(req, resp);
            return;
        }

        // CASO B: Es Usuario (Cliente)
        if (session.getAttribute("usuario") != null) {
            // Lo mandamos a la pantalla de "En Construcción"
            req.getRequestDispatcher("dashboardCliente.jsp").forward(req, resp);
            return;
        }

        // CASO C: Sesión existe pero está vacía (Error raro) -> Al inicio
        resp.sendRedirect("index.jsp");
    }
}