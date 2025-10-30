package org.example;

import org.example.logica.ConexionFirebase;

import org.example.logica.CRUDFireStore;
import org.example.modelo.Producto;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            ConexionFirebase.conectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CRUDFireStore<Producto> crud = new CRUDFireStore<>("productos", Producto.class);
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== CRUD PRODUCTOS (Firebase) ===");
            System.out.println("1. Crear producto");
            System.out.println("2. Mostrar todos");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Buscar por ID");
            System.out.println("0. Salir");
            System.out.print("👉 Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("ID del producto: ");
                    String id = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Precio: ");
                    double precio = sc.nextDouble();
                    System.out.print("Stock: ");
                    int stock = sc.nextInt();
                    crud.crear(id, new Producto(nombre, precio, stock));
                }
                case 2 -> {
                    System.out.println("\n📦 Lista de productos:");
                    crud.leerTodos().forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("ID del producto a actualizar: ");
                    String id = sc.nextLine();
                    System.out.print("Nuevo nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Nuevo precio: ");
                    double precio = sc.nextDouble();
                    System.out.print("Nuevo stock: ");
                    int stock = sc.nextInt();
                    crud.actualizar(id, new Producto(nombre, precio, stock));
                }
                case 4 -> {
                    System.out.print("ID del producto a eliminar: ");
                    String id = sc.nextLine();
                    crud.eliminar(id);
                }
                case 5 -> {
                    System.out.print("ID del producto: ");
                    String id = sc.nextLine();
                    crud.leerPorId(id).ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("No se encontró el producto.")
                    );
                }
                case 0 -> System.out.println("👋 Saliendo...");
                default -> System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 0);
    }
}


