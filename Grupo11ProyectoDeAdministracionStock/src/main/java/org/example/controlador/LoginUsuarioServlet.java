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
import org.example.modelo.Usuario;

import java.io.IOException;

@WebServlet(name = "LoginUsuarioServlet", urlPatterns = {"/loginUsuario"})
public class LoginUsuarioServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LoginUsuarioServlet.class);
    private final transient CRUDFireStore<Usuario> usuarioCRUD = new CRUDFireStore<>("usuarios", Usuario.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String emailInput = req.getParameter("email");
        String passInput = req.getParameter("password");

        try {
            logger.info("Intento de login de usuario: {}", emailInput);
            Usuario usuarioEncontrado = usuarioCRUD.obtenerPorId(emailInput);

            if (usuarioEncontrado != null) {
                String passHash = Encriptador.encriptar(passInput);
                if (passHash.equals(usuarioEncontrado.getPassword())) {
                    logger.info("Usuario autenticado correctamente: {}", emailInput);
                    HttpSession session = req.getSession();
                    session.setAttribute("usuario", usuarioEncontrado);
                    session.setAttribute("rol", "CLIENTE");
                    resp.sendRedirect("dashboard");

                } else {
                    logger.warn("Contraseña incorrecta para usuario: {}", emailInput);
                    resp.sendRedirect("loginUsuario.jsp?error=credenciales");
                }
            } else {
                logger.warn("Usuario no encontrado en la base de datos: {}", emailInput);
                resp.sendRedirect("loginUsuario.jsp?error=nouser");
            }

        } catch (Exception e) {
            logger.error("Error crítico en login de usuario", e);
            resp.sendRedirect("loginUsuario.jsp?error=servidor");
        }
    }
}