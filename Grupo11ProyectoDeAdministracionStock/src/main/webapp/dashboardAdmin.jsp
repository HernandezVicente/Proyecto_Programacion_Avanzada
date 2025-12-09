<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.modelo.Administrador" %>

<%
    Administrador admin = (Administrador) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("loginAdmin.jsp?error=acceso");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel de Administración</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>

<div class="container container-admin">

    <div class="d-flex justify-content-between align-items-center mb-5 border-bottom pb-3">
        <div>
            <h1 class="mb-0 text-primary">Panel de Control</h1>
            <p class="text-muted mb-0">Bienvenido, <strong><%= admin.getNombreUsuario() %></strong></p>
        </div>
        <a href="logout.jsp" class="btn btn-danger">Cerrar Sesión</a>
    </div>

    <div class="row g-4">

        <div class="col-md-6 col-lg-4">
            <a href="ProductoServlet?accion=listar" class="text-decoration-none text-dark">
                <div class="card card-menu p-4 text-center h-100">
                    <div class="card-icon">📦</div>
                    <h3 class="fw-bold">Productos</h3>
                    <p class="text-muted">Gestionar inventario, precios y stock disponible.</p>
                    <button class="btn btn-primary btn-sm btn-custom mt-2">Ir a Productos</button>
                </div>
            </a>
        </div>

        <div class="col-md-6 col-lg-4">
            <a href="BodegaServlet?accion=listar" class="text-decoration-none text-dark">
                <div class="card card-menu p-4 text-center h-100">
                    <div class="card-icon">🏭</div>
                    <h3 class="fw-bold">Bodegas</h3>
                    <p class="text-muted">Administrar sucursales y ubicaciones de almacenamiento.</p>
                    <button class="btn btn-primary btn-sm btn-custom mt-2">Ir a Bodegas</button>
                </div>
            </a>
        </div>

        <div class="col-md-6 col-lg-4">
            <a href="reporteGeneral" class="text-decoration-none text-dark">
                <div class="card card-menu p-4 text-center h-100">
                    <div class="card-icon">📊</div>
                    <h3 class="fw-bold">Reporte Total</h3>
                    <p class="text-muted">Ver todos los datos del sistema en una sola vista.</p>
                    <button class="btn btn-info btn-sm btn-custom mt-2 text-white">Ver Todo</button>
                </div>
            </a>
        </div>

        <div class="col-md-6 col-lg-4">
            <a href="#" class="text-decoration-none text-dark">
                <div class="card card-menu p-4 text-center h-100 opacity-50">
                    <div class="card-icon">👥</div>
                    <h3 class="fw-bold">Clientes</h3>
                    <p class="text-muted">Visualizar usuarios registrados (Próximamente).</p>
                    <button class="btn btn-secondary btn-sm btn-custom mt-2" disabled>En construcción</button>
                </div>
            </a>
        </div>

    </div>
</div>

</body>
</html>