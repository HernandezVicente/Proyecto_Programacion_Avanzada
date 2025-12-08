package org.example.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.logica.CRUDFireStore;
import org.example.modelo.Administrador;

import java.io.IOException;

@WebServlet(name = "LoginAdminServlet", urlPatterns = {"/loginAdmin"})
public class LoginAdminServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LoginAdminServlet.class);

    // Conectamos con la colección "administradores" en Firebase
    private final CRUDFireStore<Administrador> adminCRUD = new CRUDFireStore<>("administradores", Administrador.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usuarioInput = req.getParameter("usuario");
        String passInput = req.getParameter("password");

        try {
            // 1. Buscamos al administrador por su nombre de usuario (ID)
            Administrador adminEncontrado = adminCRUD.obtenerPorId(usuarioInput);

            if (adminEncontrado != null) {
                // 2. Encriptamos lo que escribió para comparar
                String passHash = Encriptador.encriptar(passInput);

                // 3. Verificamos coincidencia
                if (passHash.equals(adminEncontrado.getContrasenha())) {

                    // LOGIN EXITOSO
                    logger.info("Administrador inició sesión: {}", usuarioInput);

                    HttpSession session = req.getSession();
                    session.setAttribute("admin", adminEncontrado); // Guardamos objeto admin en sesión
                    session.setAttribute("rol", "ADMIN"); // Etiqueta útil para los JSP

                    // Redirigir al panel de control (Dashboard)
                    resp.sendRedirect("dashboardAdmin.jsp");

                } else {
                    logger.warn("Intento fallido de admin: Contraseña incorrecta para {}", usuarioInput);
                    resp.sendRedirect("loginAdmin.jsp?error=credenciales");
                }
            } else {
                logger.warn("Intento fallido: Administrador no encontrado - {}", usuarioInput);
                resp.sendRedirect("loginAdmin.jsp?error=nouser");
            }
        } catch (Exception e) {
            logger.error("Error en login de administrador", e);
            resp.sendRedirect("loginAdmin.jsp?error=servidor");
        }
    }
}