<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidad.Cliente"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="entidad.Cuota"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Préstamo Cliente</title>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#listadoPrestamos').DataTable();
		language: {
			emptyTable: "¡No hay préstamos pendientes!"
		}
	});
</script>
<style>
body {
	font-family: Arial;
	background-color: #f8f9fa;
	font-size: 14px;
}

.listado-wrapper {
	padding: 20px;
	max-width: 100%;
	margin: auto;
	text-align: center;
}

h2 {
	text-align: center;
	color: #2c3e50;
}

.acciones {
	margin-bottom: 20px;
	text-align: center;
}

.acciones a {
	background-color: #3498db;
	color: white;
	padding: 10px 15px;
	border-radius: 5px;
	text-decoration: none;
	transition: background-color 0.3s ease;
}

.acciones a:hover {
	background-color: #2980b9;
}

table {
	width: 100%;
	max-width: 1400px;
	border-collapse: collapse;
	background-color: white;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	border: 1px solid #ccc;
	margin: auto;
}

th, td {
	padding: 12px;
	border: 1px solid #ccc;
	text-align: center;
	white-space: nowrap;
}

th {
	background-color: #2c3e50;
	color: white;
	text-align: center;
}

tr:hover {
	background-color: #f2f2f2;
}

.acciones-btn {
	display: flex;
	gap: 10px;
}

.btn-accion {
	border: none;
	padding: 7px 12px;
	border-radius: 5px;
	color: white;
	cursor: pointer;
	font-size: 14px;
	transition: background-color 0.3s ease;
}

.btn-pagar {
	background-color: #48ab31;
	color: #fff;
}

.btn-pagar:hover {
	background-color: #31811e;
}

.alert {
	padding: 10px 20px;
	margin: 15px auto;
	width: 80%;
	max-width: 600px;
	border-radius: 6px;
	font-weight: bold;
	text-align: center;
	font-size: 15px;
}

.alert.success {
	background-color: #d4edda;
	color: #155724;
	border: 1px solid #c3e6cb;
}

.alert.error {
	background-color: #f8d7da;
	color: #721c24;
	border: 1px solid #f5c6cb;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/MasterUsuario.jsp" />

	<div class="listado-wrapper">
		<h2>Prestamos pendientes de Pago</h2>
		<br> <br>

		<!--  MENSAJES EXITOS O ERROR  -->
		<% String error = (String) request.getAttribute("errorMensaje"); %>
		<% String exito = (String) request.getAttribute("mensajeExito"); %>
			
		<% if (exito != null) { %>
		    <div class="alert success"><%= exito %></div>
		<% } %>
		<% if (error != null) { %>
		    <div class="alert error"><%= error %></div>
		<% } %>
		
	
		<table id="listadoPrestamos" class="display">
			<thead>
				<tr>
					<th>Fecha vencimiento</th>
					<th>Num Cuenta</th>
					<th>ID prestamo</th>
					<th>Cuota</th>
					<th>Monto Cuota</th>
					<th>Cuenta a debitar</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<!-- EJEMPLO DE COMO SE VERIA EL LISTADO de prestamos pendientes -->
				<%
				List<Cuota> listaPagarPrestamos = (List<Cuota>) request.getAttribute("listaPagarPrestamos");
				if (listaPagarPrestamos != null) {
					for (Cuota cu : listaPagarPrestamos) {
				%>
				<tr>
					<form action="PrestamoServlet" method="post">
					    <input type="hidden" name="accion" value="pagarCuota">
					    <input type="hidden" name="idPrestamo" value="<%= cu.getPrestamo().getIdPrestamo() %>">
					    <input type="hidden" name="nroCuota" value="<%= cu.getNroCuota() %>">
					    <input type="hidden" name="montoCuota" value="<%= cu.getMontoCuota() %>">
					    <input type="hidden" name="dniCliente" value="<%= session.getAttribute("dniUsuario") %>">
					    

						<td><%= cu.getFechaVencimiento() %></td>
						<td><%= cu.getCuenta().getNumeroCuenta() %></td>
						<td><%= cu.getPrestamo().getIdPrestamo() %></td>
						<td><%= cu.getNroCuota() %></td>
						<td><%= cu.getMontoCuota() %></td>

						<td>
						<select name="cuentaDebitar" required>
						    <option value="">-- cuenta a debitar --</option>
						<%
						    List<Cuenta> listaCuentas = (List<Cuenta>) request.getAttribute("cuentas");
						    if (listaCuentas != null) {
						        for (Cuenta c : listaCuentas) {
						%>
						    <option value="<%= c.getNumeroCuenta() %>">
						        cuenta: <%= c.getNumeroCuenta() %> - saldo: $<%= c.getSaldo() %>
						    </option>
						<%
						        }
						    }
						%>
						</select>
						</td>

						<td>
							<button type="submit" class="btn-accion btn-pagar">Pagar</button>
						</td>
					</form>
				</tr>
				<%
				} // ← cierra for
				} // ← cierra if
				%>
				<% if (request.getAttribute("error") != null) { %>
				<script>
				    window.onload = function() {
				        alert("<%= request.getAttribute("error") %>");
				    };
				</script>
				<% } %>
			</tbody>
		</table>
	</div>
</body>
</html>