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
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendRedirect("index.jsp");
            return;
        }

        if (session.getAttribute("admin") != null) {
            req.getRequestDispatcher("dashboardAdmin.jsp").forward(req, resp);
            return;
        }

        if (session.getAttribute("usuario") != null) {
            req.getRequestDispatcher("dashboardCliente.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("index.jsp");
    }
}