<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Administración de Inventario</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f3f4f6;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #2563eb;
            color: white;
            padding: 1rem;
            text-align: center;
        }
        main {
            margin: 40px auto;
            width: 80%;
            text-align: center;
        }
        h1 {
            margin-bottom: 20px;
        }
        .menu {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 1rem;
        }
        .card {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
            width: 220px;
            transition: all 0.2s;
            cursor: pointer;
        }
        .card:hover {
            transform: scale(1.05);
            background-color: #f0f9ff;
        }
        footer {
            background-color: #2563eb;
            color: white;
            padding: 1rem;
            text-align: center;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
        a {
            text-decoration: none;
            color: inherit;
        }
    </style>
</head>
<body>
<header>
    <h1>🛒 Sistema de Administración de Inventario</h1>
</header>

<main>
    <h2>Bienvenido al panel principal</h2>
    <p>Selecciona una opción para comenzar</p>

    <div class="menu">
        <a href="listarProductos.jsp">
            <div class="card">
                <h3>📦 Listar Productos</h3>
                <p>Ver el inventario completo</p>
            </div>
        </a>
        <a href="agregarProducto.jsp">
            <div class="card">
                <h3>➕ Agregar Producto</h3>
                <p>Registrar un nuevo producto</p>
            </div>
        </a>
        <a href="reportes.jsp">
            <div class="card">
                <h3>📊 Reportes</h3>
                <p>Ver estadísticas del inventario</p>
            </div>
        </a>
    </div>
</main>

<footer>
    <p>Desarrollado por Vicente &amp; Equipo © 2025</p>
</footer>
</body>
</html>
