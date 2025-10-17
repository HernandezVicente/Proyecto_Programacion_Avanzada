package org.example;

public interface DatabaseConnectionAdapter<I, C> {
    String createConnection(C c);
    I getConnection();
}

