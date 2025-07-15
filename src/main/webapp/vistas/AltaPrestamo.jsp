<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidad.Cuenta" %> 
<%@ page import="entidad.TipoCuenta" %>


<!DOCTYPE html>
<html>
<head>
	<title>Alta de Préstamo</title>
	<style>
		body {
			font-family: Arial;
			background-color: #f8f9fa;
			font-size: 14px;
		}

		.contenedor {
			padding: 20px;
			max-width: 600px;
			margin: auto;
			background-color: white;
			box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
			border-radius: 10px;
		}

		h2 {
			text-align: center;
			color: #2c3e50;
		}

		label {
			display: block;
			margin-bottom: 8px;
			font-weight: bold;
		}

		select, input[type="number"] {
			width: 100%;
			padding: 10px;
			margin-bottom: 15px;
			border: 1px solid #ccc;
			border-radius: 5px;
		}

		input[type="submit"] {
			background-color: #3498db;
			color: white;
			padding: 10px 20px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
			font-size: 14px;
		}

		input[type="submit"]:hover {
			background-color: #2980b9;
		}
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/MasterUsuario.jsp" />

	<div class="contenedor">
		<!-- // PRIMER FORMULARIO: solo para calcular el préstamo (accion = VerPrestamo) -->
		<form class="altaPrestamo" action="<%= request.getContextPath() %>/UsuarioClienteServlet" method="post">
			<h2>Solicitud de Préstamo</h2><br>

			<label>Importe solicitado:</label>
			<input type="number" name="importe" min="1" required><br>

			<label>Cantidad de cuotas:</label>
			<select name="cuotas" required>
				<option value="3">3 Cuotas (+20%)</option>
				<option value="6">6 Cuotas (+50%)</option>
				<option value="12">12 Cuotas (+75%)</option>
				<option value="24">24 Cuotas(+100%)</option>
			</select><br> 

			<h2>Cuenta para depósito del préstamo:</h2>
			<select name="cuentaDestino" required>
				<option value="">-- cuenta destino --</option>
				<%
					List<Cuenta> listaCuentas = (List<Cuenta>) session.getAttribute("listaCuentas");
					if (listaCuentas != null) {
						for (Cuenta cuenta : listaCuentas) {
				%>
					<option value="<%= cuenta.getNumeroCuenta() %>">
						Cuenta <%= cuenta.getNumeroCuenta() %> - <%= cuenta.getTipoCuenta().getNombreTipo() %>
					</option>
				<%
						}
					}
				%>
			</select><br>

			<input type="hidden" name="accion" value="VerPrestamo" />
			<input type="submit" value="Ver Prestamo" />
		</form>

		<!-- // SEGUNDO FORMULARIO: si se hizo click en "Ver Prestamo", se muestra el resumen con los hidden listos -->
		<%
			if (request.getAttribute("total") != null) { // muestro solo si tengo valores calculados
		%>
			<div style="margin-top: 30px; padding-top: 10px; border-top: 1px solid #ccc;">
				<form action="<%= request.getContextPath() %>/UsuarioClienteServlet" method="post">
					<!-- //esto me permite que se vea en el jsp -->
					<p>Cuenta destino: <%= request.getAttribute("cuentaDestino") %></p>
					<input type="hidden" name="cuentaDestino-VerPrestamo" value="<%= request.getAttribute("cuentaDestino") %>" />

					<p>Importe solicitado: $<%= request.getAttribute("importe") %></p>
					<input type="hidden" name="importe-VerPrestamo" value="<%= request.getAttribute("importe") %>" />

					<p>Cantidad de cuotas: <%= request.getAttribute("cuotas") %></p>
					<input type="hidden" name="cuotas-VerPrestamo" value="<%= request.getAttribute("cuotas") %>" />

					<p>Valor por cuota: $<%= request.getAttribute("cuotaMensual") %></p>
					<input type="hidden" name="cuotaMensual-VerPrestamo" value="<%= request.getAttribute("cuotaMensual") %>" />

					<p>Total a pagar: $<%= request.getAttribute("total") %></p>
					<input type="hidden" name="total-VerPrestamo" value="<%= request.getAttribute("total") %>" />

					<input type="hidden" name="accion" value="SolicitarPrestamo" />
					<input type="submit" value="Solicitar Prestamo" />
				</form>
			</div>
		<%
			}
		%>

		<!-- // mensaje de error -->
		<%
			String error = (String) request.getAttribute("error");
			if (error != null) {
		%>
			<div style="color: red; font-weight: bold;">
				<%= error %>
			</div>
		<%
			}
		%>

	</div>
</body>
</html>
