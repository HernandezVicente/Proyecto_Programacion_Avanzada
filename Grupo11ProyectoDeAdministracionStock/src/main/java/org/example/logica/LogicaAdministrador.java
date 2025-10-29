package org.example.logica;

import org.example.modelo.Administrador;
import java.util.ArrayList;
import java.util.List;

public class LogicaAdministrador {
    private List<Administrador> administradores = new ArrayList<>();

    public void registrar(Administrador admin) {
        administradores.add(admin);
    }

    public Administrador login(String user, String password) {
        return administradores.stream()
                .filter(a -> a.getNombreUsuario().equals(user)
                        && a.getContraseña().equals(password))
                .findFirst()
                .orElse(null);
    }
}
