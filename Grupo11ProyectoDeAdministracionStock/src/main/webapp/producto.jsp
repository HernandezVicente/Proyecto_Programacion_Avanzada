<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, org.example.modelo.Producto" %>

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
    <title>Gestión de Productos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="container container-admin">
    <div class="d-flex justify-content-between align-items-center mb-4 border-bottom pb-2">
        <h1 class="text-primary mb-0">Administración de Producto</h1>
        <div>
            <a href="dashboard" class="btn btn-secondary btn-custom">Volver al Panel de Control</a>
            <a href="logout" class="btn btn-outline-danger btn-sm ms-2">Cerrar Sesión</a>
        </div>
    </div>

    <div class="form-section">
        <form id="formProducto" action="ProductoServlet" method="post">
            <div class="row">
                <div class="col-md-4 mb-3">
                    <label for="codigoBarra" class="form-label">Código de Barra</label>
                    <input type="number" class="form-control" id="codigoBarra" name="codigoBarra" placeholder="Ej: 1001" required>
                </div>

                <div class="col-md-4 mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ej: Galletas" required>
                </div>

                <div class="col-md-4 mb-3">
                    <label for="categoria" class="form-label">Categoría</label>
                    <input type="text" class="form-control" id="categoria" name="categoria" placeholder="Ej: Alimentos" required>
                </div>

                <div class="col-md-4 mb-3">
                    <label for="precio" class="form-label">Precio</label>
                    <input type="number" class="form-control" id="precio" name="precio" placeholder="Ej: 1200" required>
                </div>

                <div class="col-md-4 mb-3">
                    <label for="cantidad" class="form-label">Cantidad</label>
                    <input type="number" class="form-control" id="cantidad" name="cantidad" placeholder="Ej: 20" required>
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
            Nota: Para eliminar, solo necesitas escribir el Código de Barra.
        </small>
    </div>

    <%
        List<Producto> productos = (List<Producto>) request.getAttribute("productos");
        String error = (String) request.getAttribute("error");
    %>

    <% if (error != null) { %>
    <div class="alert alert-danger text-center"><%= error %></div>
    <% } %>

    <% if (productos != null && !productos.isEmpty()) { %>
    <table class="table table-bordered table-striped text-center align-middle table-custom">
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
    <p class="text-center text-muted">👋 Usa el botón "Listar" para ver los datos.</p>
    <% } %>
</div>

<script>
    function validarEliminar() {
        const codigo = document.getElementById("codigoBarra").value;
        if (!codigo) {
            alert("⚠️ Escribe el Código de Barra para eliminar.");
            document.getElementById("codigoBarra").focus();
            return false;
        }
        return true;
    }
</script>
</body>
</html>