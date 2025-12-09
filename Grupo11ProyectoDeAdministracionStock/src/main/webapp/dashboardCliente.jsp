<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.modelo.Usuario" %>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("loginUsuario.jsp?error=acceso");
        return;
    }

    Usuario usuario = (Usuario) session.getAttribute("usuario");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel de Cliente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="container container-admin text-center" style="max-width: 600px; margin-top: 100px;">
    <div style="font-size: 5rem; margin-bottom: 20px;">🚧</div>

    <h1 class="text-primary fw-bold">Hola, <%= usuario.getNombre() %></h1>

    <hr class="my-4">

    <h3 class="text-dark mb-3">Lo sentimos, estamos trabajando en ello.</h3>

    <p class="text-muted lead">
        Estamos construyendo el mejor panel para ti.<br>
        Pronto podrás ver tus compras y editar tu perfil aquí.
    </p>

    <div class="mt-5">
        <a href="logout" class="btn btn-danger btn-custom px-4">Cerrar Sesión</a>
    </div>

</div>

</body>
</html>