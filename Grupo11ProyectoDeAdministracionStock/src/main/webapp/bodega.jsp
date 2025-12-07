<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, org.example.modelo.Bodega" %>

<%-- 🛡️ SEGURIDAD: Solo Admins pueden entrar --%>
<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("loginAdmin.jsp?error=acceso");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Bodegas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="container container-admin">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="mb-0">Administración de Bodegas</h1>
        <a href="logout.jsp" class="btn btn-outline-danger btn-sm">Cerrar Sesión</a>
    </div>

    <div class="form-section">
        <form id="formBodega" action="BodegaServlet" method="post">
            <div class="row">
                <div class="col-md-3 mb-3">
                    <label for="id" class="form-label">ID Bodega</label>
                    <input type="number" class="form-control" id="id" name="id" placeholder="Ej: 10" required>
                </div>
                <div class="col-md-3 mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ej: Central" required>
                </div>
                <div class="col-md-3 mb-3">
                    <label for="direccion" class="form-label">Dirección</label>
                    <input type="text" class="form-control" id="direccion" name="direccion" placeholder="Ej: Av. Principal 123" required>
                </div>
                <div class="col-md-3 mb-3">
                    <label for="capacidad" class="form-label">Capacidad</label>
                    <input type="number" class="form-control" id="capacidad" name="capacidad" placeholder="Ej: 500" required>
                </div>
            </div>

            <div class="text-center btn-group">
                <button type="submit" name="accion" value="crear" class="btn btn-success btn-custom">➕ Agregar</button>
                <button type="submit" name="accion" value="actualizar" class="btn btn-primary btn-custom">✏️ Actualizar</button>

                <button type="submit" name="accion" value="eliminar" class="btn btn-danger btn-custom" formnovalidate onclick="return validarEliminar()">🗑️ Eliminar</button>

                <button type="submit" name="accion" value="listar" class="btn btn-secondary btn-custom" formnovalidate>🔄 Listar</button>
            </div>
        </form>

        <small class="text-muted d-block mt-2">
            Nota: Para eliminar, solo necesitas ingresar el ID de la Bodega.
        </small>
    </div>

    <%
        List<Bodega> bodegas = (List<Bodega>) request.getAttribute("bodegas");
        String error = (String) request.getAttribute("error");
    %>

    <% if (error != null) { %>
    <div class="alert alert-danger text-center"><%= error %></div>
    <% } %>

    <% if (bodegas != null && !bodegas.isEmpty()) { %>
    <table class="table table-bordered table-striped text-center align-middle table-custom">
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
    function validarEliminar() {
        const id = document.getElementById("id").value;
        if (!id) {
            alert("⚠️ Por favor, ingresa el ID de la Bodega para eliminar.");
            document.getElementById("id").focus();
            return false; // Detiene el envío
        }
        return true; // Permite el envío
    }
</script>
</body>
</html>