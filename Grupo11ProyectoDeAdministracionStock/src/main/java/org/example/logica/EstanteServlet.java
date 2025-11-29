package org.example.logica;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.modelo.Estante;
import java.io.IOException;

@WebServlet("/estantes")
public class EstanteServlet extends HttpServlet {
    private final LogicaEstante logica = new LogicaEstante();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long codigo = Long.parseLong(req.getParameter("codigo"));
            int capacidad = Integer.parseInt(req.getParameter("capacidadMaxima"));

            Estante estante = new Estante(codigo, capacidad);
            logica.agregarEstante(estante);

            resp.sendRedirect("listarEstantes.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            //req.setAttribute("estantes", logica.listarEstantes());
            req.getRequestDispatcher("listarEstantes.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}