package org.example.modelo;

public class Administrador {
    private String nombreUsuario;
    private String contrasenha; // Usando "nh" como tú quieres

    // 1. Constructor Vacío (OBLIGATORIO para Firebase)
    public Administrador() {
    }

    // 2. Constructor Lleno
    public Administrador(String nombreUsuario, String contrasenha) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenha = contrasenha;
    }

    // 3. GETTERS Y SETTERS
    // ¡OJO! Deben llamarse igual que la variable (getContrasenha)

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }
}