<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prestamo confirmado</title>
</head>
<body>
 <h2 style="color:green;">¡Tu préstamo se solicitó con éxito!</h2>
  <p>En breve recibirás la aprobación.</p>
  <a href="<%= request.getContextPath() %>/vistas/HomeCliente.jsp">Volver al inicio</a>
</body>
</html>