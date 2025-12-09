package org.example.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.logica.CRUDFireStore;
import org.example.logica.LogicaBodega;
import org.example.logica.LogicaProducto;
import org.example.modelo.Administrador;
import org.example.modelo.Bodega;
import org.example.modelo.Producto;
import org.example.modelo.Usuario;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReporteServlet", urlPatterns = {"/reporteGeneral"})
public class ReporteServlet extends HttpServlet {

    private final transient LogicaProducto logicaProducto = new LogicaProducto();
    private final transient LogicaBodega logicaBodega = new LogicaBodega();
    private final transient CRUDFireStore<Usuario> crudUsuarios = new CRUDFireStore<>("usuarios", Usuario.class);
    private final transient CRUDFireStore<Administrador> crudAdmins = new CRUDFireStore<>("administradores", Administrador.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            resp.sendRedirect("loginAdmin.jsp?error=acceso");
            return;
        }

        List<Producto> listaProductos = logicaProducto.listarProductos();
        List<Bodega> listaBodegas = logicaBodega.listarBodegas();
        List<Usuario> listaUsuarios = crudUsuarios.obtenerTodos();
        List<Administrador> listaAdmins = crudAdmins.obtenerTodos();

        req.setAttribute("productos", listaProductos);
        req.setAttribute("bodegas", listaBodegas);
        req.setAttribute("usuarios", listaUsuarios);
        req.setAttribute("admins", listaAdmins);

        req.getRequestDispatcher("reporteGeneral.jsp").forward(req, resp);
    }
}