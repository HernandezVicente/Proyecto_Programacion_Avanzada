<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, org.example.modelo.Bodega" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Bodegas</title>
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
    <h1 class="text-center mb-4">Administración de Bodegas</h1>

    <div class="form-section">
        <form id="formBodega" action="BodegaServlet" method="post">
            <div class="row">
                <div class="col-md-3 mb-3">
                    <label for="id" class="form-label">ID Bodega</label>
                    <input type="number" class="form-control" id="id" name="id" placeholder="Ej: 10">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ej: Central">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="direccion" class="form-label">Dirección</label>
                    <input type="text" class="form-control" id="direccion" name="direccion" placeholder="Ej: Av. Principal 123">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="capacidad" class="form-label">Capacidad</label>
                    <input type="number" class="form-control" id="capacidad" name="capacidad" placeholder="Ej: 500">
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
            Eliminar requiere solo el <b>ID Bodega</b>.<br>
            Listar no requiere llenar ningún campo.
        </small>
    </div>

    <%
        List<Bodega> bodegas = (List<Bodega>) request.getAttribute("bodegas");
        String error = (String) request.getAttribute("error");

        if (error != null) {
    %>
    <div class="alert alert-danger text-center"><%= error %></div>
    <% } %>

    <% if (bodegas != null && !bodegas.isEmpty()) { %>
    <table class="table table-bordered table-striped text-center align-middle">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Dirección</th>
            <th>Capacidad</th>
        </tr>
        </thead>
        <tbody>
        <% for (Bodega b : bodegas) { %>
        <tr>
            <td><%= b.getId() %></td>
            <td><%= b.getNombre() %></td>
            <td><%= b.getDireccion() %></td>
            <td><%= b.getCapacidad() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p class="text-center text-muted">No hay bodegas registradas o presiona "Listar".</p>
    <% } %>
</div>

<script>
    const campos = ["id", "nombre", "direccion", "capacidad"];
    document.querySelectorAll("button[name='accion']").forEach(btn => {
        btn.addEventListener("click", () => {
            campos.forEach(c => {
                const input = document.getElementById(c);
                if(input) input.removeAttribute("required");
            });

            if (btn.value === "crear" || btn.value === "actualizar") {
                campos.forEach(c => {
                    const input = document.getElementById(c);
                    if(input) input.setAttribute("required", "required");
                });
            } else if (btn.value === "eliminar") {
                document.getElementById("id").setAttribute("required", "required");
            }
        });
    });
</script>
</body>
</html>