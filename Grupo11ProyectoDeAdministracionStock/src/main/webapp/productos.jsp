<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, org.example.modelo.Producto" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Productos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: #f4f6f8;
            font-family: 'Segoe UI', sans-serif;
        }
        .container {
            margin-top: 40px;
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .form-section {
            background: #e9f0fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        h1 {
            color: #2c3e50;
            font-weight: bold;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        .btn-group button {
            margin: 3px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center mb-4"> Administración de Productos</h1>

    <div class="form-section">
        <form id="formProducto" action="ProductoServlet" method="post">
            <div class="row">
                <div class="col-md-4 mb-3">
                    <label for="codigoBarra" class="form-label">Código de Barra</label>
                    <input type="number" class="form-control" id="codigoBarra" name="codigoBarra" placeholder="Ej: 1001">
                </div>

                <div class="col-md-4 mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ej: Galletas">
                </div>

                <div class="col-md-4 mb-3">
                    <label for="categoria" class="form-label">Categoría</label>
                    <input type="text" class="form-control" id="categoria" name="categoria" placeholder="Ej: Alimentos">
                </div>

                <div class="col-md-4 mb-3">
                    <label for="precio" class="form-label">Precio</label>
                    <input type="number" class="form-control" id="precio" name="precio" placeholder="Ej: 1200">
                </div>

                <div class="col-md-4 mb-3">
                    <label for="cantidad" class="form-label">Cantidad</label>
                    <input type="number" class="form-control" id="cantidad" name="cantidad" placeholder="Ej: 20">
                </div>
            </div>

            <div class="text-center btn-group">
                <button type="submit" name="accion" value="crear" class="btn btn-success">➕ Agregar</button>
                <button type="submit" name="accion" value="actualizar" class="btn btn-primary">✏️ Actualizar</button>
                <button type="submit" name="accion" value="eliminar" class="btn btn-danger">🗑️ Eliminar</button>
                <button type="submit" name="accion" value="listar" class="btn btn-secondary">🔄 Listar</button>
            </div>
        </form>

        <small class="text-muted d-block mt-2">
            Agregar o actualizar requiere todos los campos.<br>
            Eliminar requiere solo el <b>Código de Barra</b>.<br>
            Listar no requiere llenar ningún campo.
        </small>
    </div>

    <%
        List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    %>

    <% if (productos != null && !productos.isEmpty()) { %>
    <table class="table table-bordered table-striped text-center align-middle">
        <thead>
        <tr>
            <th>Código Barra</th>
            <th>Nombre</th>
            <th>Categoría</th>
            <th>Precio</th>
            <th>Cantidad</th>
        </tr>
        </thead>
        <tbody>
        <% for (Producto p : productos) { %>
        <tr>
            <td><%= p.getCodigoBarra() %></td>
            <td><%= p.getNombre() %></td>
            <td><%= p.getCategoria() %></td>
            <td>$<%= p.getPrecio() %></td>
            <td><%= p.getCantidad() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else if (productos != null) { %>
    <p class="text-center text-muted">⚠️ No hay productos registrados en la base de datos.</p>
    <% } else { %>
    <p class="text-center text-muted">👋 Usa los botones de arriba para comenzar.</p>
    <% } %>
</div>
<script>
    const form = document.getElementById("formProducto");
    const campos = ["codigoBarra", "nombre", "categoria", "precio", "cantidad"];

    document.querySelectorAll("button[name='accion']").forEach(btn => {
        btn.addEventListener("click", () => {
            campos.forEach(c => document.getElementById(c).removeAttribute("required"));

            if (btn.value === "crear" || btn.value === "actualizar") {
                campos.forEach(c => document.getElementById(c).setAttribute("required", "required"));
            } else if (btn.value === "eliminar") {
                document.getElementById("codigoBarra").setAttribute("required", "required");
            }
        });
    });
</script>
</body>
</html>