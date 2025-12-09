package org.example.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FirebaseInitializerTest {

    private FirebaseInitializer initializer;

    @Mock
    private ServletContextEvent mockEvent;

    private MockedStatic<FirebaseApp> mockedFirebaseApp;

    @BeforeEach
    void setUp() {
        initializer = new FirebaseInitializer();
        mockedFirebaseApp = Mockito.mockStatic(FirebaseApp.class);
    }

    @AfterEach
    void tearDown() {
        // Cerrar el mock estático
        mockedFirebaseApp.close();
    }


    @Test
    void testContextInitialized_Success() {
        mockedFirebaseApp.when(FirebaseApp::getApps).thenReturn(Collections.emptyList());
        initializer.contextInitialized(mockEvent);
        mockedFirebaseApp.verify(() -> FirebaseApp.initializeApp(any(FirebaseOptions.class)), times(1));
    }

    @Test
    void testContextInitialized_AlreadyInitialized() {
        mockedFirebaseApp.when(FirebaseApp::getApps).thenReturn(List.of(mock(FirebaseApp.class)));
        initializer.contextInitialized(mockEvent);
        mockedFirebaseApp.verify(() -> FirebaseApp.initializeApp(any(FirebaseOptions.class)), never());
    }

    @Test
    void testContextDestroyed() {
        assertDoesNotThrow(() -> initializer.contextDestroyed(mockEvent));
    }
}