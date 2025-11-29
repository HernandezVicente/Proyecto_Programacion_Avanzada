<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicio | Sistema de Administración</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #2c3e50, #3498db);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', sans-serif;
            color: white;
        }

        .card {
            background-color: rgba(255, 255, 255, 0.1);
            border: none;
            border-radius: 20px;
            backdrop-filter: blur(8px);
            box-shadow: 0 0 15px rgba(0,0,0,0.3);
            padding: 2rem;
            text-align: center;
            width: 350px;
        }

        .btn-custom {
            width: 100%;
            margin-top: 10px;
            border-radius: 10px;
            transition: all 0.3s ease;
            font-weight: bold;
        }

        .btn-custom:hover {
            transform: scale(1.05);
        }

        h1 {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="card">
    <h1>Bienvenido</h1>
    <p>Seleccione cómo desea ingresar al sistema o registrese</p>

    <form action="loginAdmin.jsp" method="get">
        <button type="submit" class="btn btn-light btn-custom">🔑 Iniciar como Administrador</button>
    </form>

    <form action="loginUsuario.jsp" method="get">
        <button type="submit" class="btn btn-outline-light btn-custom">👤 Iniciar como Usuario</button>
    </form>

    <hr style="margin: 20px 0; border-color: rgba(255,255,255,0.3);">

    <form action="registro.jsp" method="get">
        <button type="submit" class="btn btn-success btn-custom">📝 Registrarse</button>
    </form>
</div>

</body>
</html>