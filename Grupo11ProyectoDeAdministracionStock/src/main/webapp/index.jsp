<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicio | Sistema de Administración</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>

<body class="modo-acceso">

<div class="card-glass">
    <h1>Bienvenido</h1>
    <p>Seleccione cómo desea ingresar al sistema o regístrese</p>

    <form action="loginAdmin.jsp" method="get">
        <button type="submit" class="btn btn-light btn-glass">🔑 Iniciar como Administrador</button>
    </form>

    <form action="loginUsuario.jsp" method="get">
        <button type="submit" class="btn btn-outline-light btn-glass">👤 Iniciar como Usuario</button>
    </form>

    <hr style="margin: 20px 0; border-color: rgba(255,255,255,0.3);">

    <form action="registro.jsp" method="get">
        <button type="submit" class="btn btn-success btn-glass">📝 Registrarse</button>
    </form>
</div>

</body>
</html>