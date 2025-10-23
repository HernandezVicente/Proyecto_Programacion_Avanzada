package org.example.BaseDeDatos;

public interface DatabaseConnectionAdapter<I, C> {
    String createConnection(C c);
    I getConnection();
}

