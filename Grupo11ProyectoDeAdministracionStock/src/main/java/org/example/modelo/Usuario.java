package org.example.modelo;

public class Usuario {

    // 👇 ESTOS NOMBRES DEBEN SER IDÉNTICOS A FIREBASE
    private String id;
    private String nombre;  // Antes era nombreUsuario
    private String email;   // Antes era correo
    private String password;
    private String rol;

    // 1. Constructor Vacío (Obligatorio)
    public Usuario() {
    }

    // 2. Constructor Lleno
    public Usuario(String id, String nombre, String email, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // 3. GETTERS Y SETTERS (Actualizados)

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; } // Ahora es getNombre()
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }   // Ahora es getEmail()
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}