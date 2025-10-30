package org.example.modelo;

public class Administrador {
    private String nombreUsuario;
    private String contraseña;

    public Administrador(String nombreUsuario, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public String getNombreUsuario() { return nombreUsuario; }
    public String getContraseña() { return contraseña; }
}