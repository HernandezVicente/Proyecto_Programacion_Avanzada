package org.example.controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.logica.CRUDFireStore;
import org.example.modelo.Administrador;
import org.example.modelo.Usuario;

import java.io.IOException;

/**
 * Servlet encargado del registro de nuevos usuarios y administradores.
 * <p>
 * Recibe los datos del formulario JSP, encripta la contraseña y decide
 * en qué colección de Firebase guardar la información basándose en una
 * clave secreta de administrador.
 * </p>
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/registro"})
public class RegistroServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(RegistroServlet.class);
    private final transient CRUDFireStore<Usuario> usuarioCRUD = new CRUDFireStore<>("usuarios", Usuario.class);
    private final transient CRUDFireStore<Administrador> adminCRUD = new CRUDFireStore<>("administradores", Administrador.class);
    private static final String CLAVE_SECRETA_ADMIN = "PROYECTO_2025";

    /**
     * Procesa la solicitud POST del formulario de registro.
     *
     * @param req  Solicitud HTTP con los parámetros (nombre, email, password, codigoAdmin).
     * @param resp Respuesta HTTP para redirigir al usuario.
     * @throws ServletException Si ocurre un error en el Servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String nombre = req.getParameter("nombre");
            String email = req.getParameter("email");
            String passwordRaw = req.getParameter("password");
            String codigoAdmin = req.getParameter("codigoAdmin");

            logger.info("--- INTENTO DE REGISTRO ---");
            logger.info("Nombre: {}", nombre);
            logger.info("Email: {}", email);
            logger.info("Código Admin recibido: '{}'", codigoAdmin);

            String passwordHash = Encriptador.encriptar(passwordRaw);

            if (codigoAdmin != null && codigoAdmin.trim().equals(CLAVE_SECRETA_ADMIN)) {
                logger.info("✅ El código coincide. Registrando como ADMIN...");
                Administrador nuevoAdmin = new Administrador(nombre, passwordHash);
                boolean exito = adminCRUD.guardar(nombre, nuevoAdmin);

                if (exito) {
                    logger.info("🚀 Administrador guardado exitosamente en Firebase.");
                    resp.sendRedirect("index.jsp?registro=admin_creado");
                } else {
                    logger.error("⚠️ Falló la conexión con Firebase al guardar Admin.");
                    resp.sendRedirect("registro.jsp?error=firebase_error");
                }

            } else {
                logger.info("ℹ️ El código NO coincide o está vacío. Registrando como CLIENTE...");
                Usuario nuevoUsuario = new Usuario(email, nombre, email, passwordHash, "CLIENTE");
                boolean exito = usuarioCRUD.guardar(email, nuevoUsuario);

                if (exito) {
                    logger.info("🚀 Usuario guardado exitosamente en Firebase.");
                    resp.sendRedirect("index.jsp?registro=usuario_creado");
                } else {
                    logger.error("⚠️ Falló la conexión con Firebase al guardar Usuario.");
                    resp.sendRedirect("registro.jsp?error=firebase_error");
                }
            }

        } catch (Exception e) {
            logger.error("❌ ERROR CRÍTICO EN REGISTRO:", e);
            resp.sendRedirect("registro.jsp?error=servidor");
        }
    }
}