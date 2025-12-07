<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Acceso Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body class="modo-acceso"> <div class="card-glass"> <h2 class="mb-4">🔒 Admin</h2>

  <% if ("credenciales".equals(request.getParameter("error"))) { %>
  <div class="alert alert-danger p-2" style="font-size: 0.9rem;">Contraseña incorrecta</div>
  <% } %>

  <form action="loginAdmin" method="post">
    <div class="mb-3 text-start">
      <label class="form-label">Usuario</label>
      <input type="text" name="usuario" class="form-control" required placeholder="ej: root">
    </div>

    <div class="mb-3 text-start">
      <label class="form-label">Contraseña</label>
      <input type="password" name="password" class="form-control" required>
    </div>

    <button type="submit" class="btn btn-light btn-glass">Entrar</button>
  </form>

  <div class="mt-3">
    <a href="index.jsp" class="text-white text-decoration-none small">Volver al inicio</a>
  </div>
</div>

</body>
</html>