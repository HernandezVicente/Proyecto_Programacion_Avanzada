package org.example.controlador; // O package org.example.logica;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet encargado exclusivamente de cerrar la sesión del usuario.
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Obtener la sesión actual (sin crear una nueva si no existe)
        HttpSession session = req.getSession(false);

        // 2. Si existe, la destruimos
        if (session != null) {
            session.invalidate();
            System.out.println("✅ Sesión cerrada correctamente.");
        }

        // 3. Redirigir al inicio (Login)
        resp.sendRedirect("index.jsp");
    }
}