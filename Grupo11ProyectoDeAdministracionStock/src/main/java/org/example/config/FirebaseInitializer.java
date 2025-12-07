package org.example.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Inicializador de contexto para la configuración de Firebase en una aplicación Web.
 * <p>
 * Esta clase implementa {@link ServletContextListener} y está anotada con {@link WebListener},
 * lo que garantiza que el método {@code contextInitialized} se ejecute automáticamente
 * al arrancar el servidor (Tomcat/Jetty) y antes de que se sirva cualquier petición.
 * </p>
 * * <p>Su función principal es cargar las credenciales del archivo JSON y establecer
 * la conexión global con Firebase Admin SDK.</p>
 *
 * @author TuNombre (opcional)
 * @version 1.0
 */
@WebListener
public class FirebaseInitializer implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(FirebaseInitializer.class);

    /**
     * Método que se ejecuta al iniciar el contexto del Servlet (arranque de la aplicación).
     * <p>
     * Realiza los siguientes pasos:
     * <ol>
     * <li>Busca el archivo {@code administrarstock.json} en el classpath (resources).</li>
     * <li>Configura las credenciales de Google.</li>
     * <li>Inicializa {@link FirebaseApp} si no existe una instancia previa.</li>
     * </ol>
     * </p>
     *
     * @param sce Evento del contexto del servlet que contiene información sobre el entorno de ejecución.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("administrarstock.json");

            if (serviceAccount == null) {
                throw new IOException("No se encontró el archivo administrarstock.json en resources");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("✅ Firebase inicializado correctamente.");
            }

        } catch (IOException e) {
            logger.error("❌ Error crítico al inicializar Firebase: ", e);
        }
    }

    /**
     * Método que se ejecuta cuando el contexto del Servlet está a punto de cerrarse (apagado del servidor).
     * <p>
     * Se utiliza para tareas de limpieza. En este caso, se registra el cierre en los logs.
     * </p>
     *
     * @param sce Evento del contexto del servlet.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("🧹 Firebase cerrado.");
    }
}