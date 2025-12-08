<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    // 1. Verificar si existe una sesión
    if (session != null) {
        // 2. Destruir la sesión por completo
        // Esto borra los atributos "admin", "usuario", "carrito", TODO.
        session.invalidate();
    }

    // 3. Redirigir a la página de inicio (Login/Bienvenida)
    // Al no poner "/" al principio, buscará index.jsp en la misma carpeta (FireBase)
    response.sendRedirect("index.jsp");
%>