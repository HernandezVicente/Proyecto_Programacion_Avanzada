<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.modelo.*" %>

<%-- 🛡️ SEGURIDAD --%>
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
  <title>Reporte General del Sistema</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="container container-admin">

  <div class="d-flex justify-content-between align-items-center mb-4 border-bottom pb-2">
    <h1 class="text-primary mb-0">📊 Reporte General de Base de Datos</h1>
    <div>
      <a href="dashboard" class="btn btn-secondary btn-custom">Volver al Dashboard</a>
      <a href="logout" class="btn btn-outline-danger btn-sm ms-2">Salir</a>
    </div>
  </div>

  <%-- SECCIÓN 1: PRODUCTOS --%>
  <h3 class="mt-4 text-dark">📦 Inventario de Productos</h3>
  <% List<Producto> productos = (List<Producto>) request.getAttribute("productos"); %>

  <div class="table-responsive mb-5">
    <table class="table table-bordered table-hover text-center align-middle table-custom">
      <thead>
      <tr>
        <th>Código</th>
        <th>Nombre</th>
        <th>Categoría</th>
        <th>Precio</th>
        <th>Stock</th>
      </tr>
      </thead>
      <tbody>
      <% if (productos != null && !productos.isEmpty()) {
        for (Producto p : productos) { %>
      <tr>
        <td><%= p.getCodigoBarra() %></td>
        <td><%= p.getNombre() %></td>
        <td><span class="badge bg-info text-dark"><%= p.getCategoria() %></span></td>
        <td>$<%= p.getPrecio() %></td>
        <td class="<%= p.getCantidad() < 5 ? "text-danger fw-bold" : "text-success" %>">
          <%= p.getCantidad() %>
        </td>
      </tr>
      <% } } else { %>
      <tr><td colspan="5" class="text-muted">No hay productos registrados.</td></tr>
      <% } %>
      </tbody>
    </table>
  </div>

  <%-- SECCIÓN 2: BODEGAS --%>
  <h3 class="text-dark">🏭 Sucursales y Bodegas</h3>
  <% List<Bodega> bodegas = (List<Bodega>) request.getAttribute("bodegas"); %>

  <div class="table-responsive mb-5">
    <table class="table table-bordered table-hover text-center align-middle table-custom">
      <thead>
      <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Dirección</th>
        <th>Capacidad</th>
      </tr>
      </thead>
      <tbody>
      <% if (bodegas != null && !bodegas.isEmpty()) {
        for (Bodega b : bodegas) { %>
      <tr>
        <td><%= b.getId() %></td>
        <td><%= b.getNombre() %></td>
        <td><%= b.getDireccion() %></td>
        <td><%= b.getCapacidad() %> m³</td>
      </tr>
      <% } } else { %>
      <tr><td colspan="4" class="text-muted">No hay bodegas registradas.</td></tr>
      <% } %>
      </tbody>
    </table>
  </div>

  <%-- SECCIÓN 3: USUARIOS (CLIENTES) --%>
  <h3 class="text-dark">👤 Clientes Registrados</h3>
  <% List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios"); %>

  <div class="table-responsive mb-5">
    <table class="table table-bordered table-hover text-center align-middle table-custom">
      <thead>
      <tr>
        <th>Nombre</th>
        <th>Email (ID)</th>
        <th>Rol</th>
      </tr>
      </thead>
      <tbody>
      <% if (usuarios != null && !usuarios.isEmpty()) {
        for (Usuario u : usuarios) { %>
      <tr>
        <td><%= u.getNombre() %></td>
        <td><%= u.getEmail() %></td>
        <td><span class="badge bg-secondary"><%= u.getRol() %></span></td>
      </tr>
      <% } } else { %>
      <tr><td colspan="3" class="text-muted">No hay clientes registrados.</td></tr>
      <% } %>
      </tbody>
    </table>
  </div>

  <%-- SECCIÓN 4: ADMINISTRADORES --%>
  <h3 class="text-dark">🛡️ Administradores del Sistema</h3>
  <% List<Administrador> admins = (List<Administrador>) request.getAttribute("admins"); %>

  <div class="table-responsive mb-4">
    <table class="table table-bordered table-hover text-center align-middle table-custom">
      <thead>
      <tr>
        <th>Usuario</th>
        <th>Estado</th>
      </tr>
      </thead>
      <tbody>
      <% if (admins != null && !admins.isEmpty()) {
        for (Administrador a : admins) { %>
      <tr>
        <td><%= a.getNombreUsuario() %></td>
        <td><span class="badge bg-success">Activo</span></td>
      </tr>
      <% } } else { %>
      <tr><td colspan="2" class="text-muted">No hay administradores visibles.</td></tr>
      <% } %>
      </tbody>
    </table>
  </div>

</div>

</body>
</html>