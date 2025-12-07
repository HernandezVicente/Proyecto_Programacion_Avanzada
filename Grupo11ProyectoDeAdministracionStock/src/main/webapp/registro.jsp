<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Personas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>

<body class="bg-light d-flex align-items-center justify-content-center min-vh-100">

<div class="card shadow-lg p-4" style="width: 400px; border-radius: 15px; border: none;">

    <div class="card-body">
        <h2 class="text-center mb-4 fw-bold text-dark">Crear Cuenta</h2>

        <% if ("servidor".equals(request.getParameter("error"))) { %>
        <div class="alert alert-danger p-2 small text-center">Error en el servidor. Intenta de nuevo.</div>
        <% } %>

        <form action="registro" method="post">

            <div class="mb-3">
                <label for="nombre" class="form-label text-muted fw-semibold">Nombre de Usuario</label>
                <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Tu usuario" required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label text-muted fw-semibold">Correo Electrónico</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="nombre@correo.com" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label text-muted fw-semibold">Contraseña</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="******" required>
            </div>

            <hr>

            <div class="mb-3">
                <label for="codigoAdmin" class="form-label text-danger fw-semibold small">¿Eres Administrador?</label>
                <input type="password" class="form-control form-control-sm" id="codigoAdmin" name="codigoAdmin" placeholder="Ingresa el código secreto (Opcional)">
            </div>

            <div class="d-grid gap-2 mt-4">
                <button type="submit" class="btn btn-success btn-lg fw-bold" style="border-radius: 10px;">Registrarse</button>
            </div>
        </form>

        <div class="mt-4 text-center">
            <p class="small text-muted mb-0">¿Ya tienes cuenta?</p>
            <a href="index.jsp" class="text-primary text-decoration-none fw-bold">Inicia sesión aquí</a>
        </div>
    </div>
</div>

</body>
</html>