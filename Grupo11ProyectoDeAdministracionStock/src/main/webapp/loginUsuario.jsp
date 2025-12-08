<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Acceso Clientes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>

<body class="modo-acceso">

<div class="card-glass">

  <div style="font-size: 3rem; margin-bottom: 10px;">👤</div>
  <h2 class="mb-4">Acceso Clientes</h2>

  <%
    String error = request.getParameter("error");
    if ("credenciales".equals(error)) {
  %>
  <div class="alert alert-danger p-2 small">Contraseña incorrecta.</div>
  <% } else if ("nouser".equals(error)) { %>
  <div class="alert alert-warning p-2 small">Este correo no está registrado.</div>
  <% } else if ("servidor".equals(error)) { %>
  <div class="alert alert-dark p-2 small">Error de conexión. Intenta luego.</div>
  <% } %>

  <form action="loginUsuario" method="post">
    <div class="mb-3 text-start">
      <label class="form-label text-white fw-bold">Correo Electrónico</label>
      <input type="email" name="email" class="form-control" required placeholder="nombre@ejemplo.com">
    </div>

    <div class="mb-3 text-start">
      <label class="form-label text-white fw-bold">Contraseña</label>
      <input type="password" name="password" class="form-control" required>
    </div>

    <div class="d-grid mt-4">
      <button type="submit" class="btn btn-light btn-glass">Ingresar</button>
    </div>
  </form>

  <hr style="border-color: rgba(255,255,255,0.3);">

  <div class="mt-3">
    <p class="small text-white mb-1">¿No tienes cuenta?</p>
    <a href="registro.jsp" class="btn btn-outline-light btn-sm w-100 mb-2">Crear Cuenta Nueva</a>
    <a href="index.jsp" class="text-white text-decoration-none small">Volver al inicio</a>
  </div>
</div>

</body>
</html>