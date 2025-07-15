<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, entidad.Cuenta"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AutoTransferencias</title>
<style>
body {
	font-family: Arial;
	background-color: #f8f9fa;
	font-size: 14px;
}

.contenedor {
	padding: 20px;
	max-width: 400px;
	margin: auto;
	background-color: white;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	border-radius: 10px;
}

h2 {
	text-align: center;
	color: #2c3e50;
}

.form-bloque {
	margin-bottom: 25px;
}

label {
	display: block;
	margin-bottom: 8px;
	font-weight: bold;
}

select, input[type="text"], input[type="number"] {
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
		<h2>Transferencias Hacia Cuentas Propias</h2>
		<form
			action="${pageContext.request.contextPath}/UsuarioClienteServlet"
			method="post">
			<div class="form-bloque">
				<label for="cuentaOrigen">Cuenta Origen:</label>
				<%
				List<Cuenta> listaCuentas = (List<Cuenta>) session.getAttribute("listaCuentas");
				%>
				<select name="cuentaDebitar" id="cuentaDebitar" required>
					<option value="">-- cuenta a debitar --</option>
					<%
					if (listaCuentas != null) {
						for (Cuenta c : listaCuentas) {
					%>
					<option value="<%=c.getNumeroCuenta() + "-" + c.getSaldo()%>">
						cuenta:
						<%=c.getNumeroCuenta()%> - saldo: $<%=c.getSaldo()%>
					</option>
					<%
					}
					}
					%>
				</select>
			</div>
			<div class="form-bloque">
				<label for="cuentaOrigen">Cuenta Destino:</label> <select
					name="cuentaDestino" id="cuentaDestino" required>
					<option value="">-- cuenta destino --</option>
					<%
					if (listaCuentas != null) {
						for (Cuenta c : listaCuentas) {
					%>
					<option value="<%=c.getNumeroCuenta() + "-" + c.getSaldo()%>">
						cuenta:
						<%=c.getNumeroCuenta()%> - saldo: $<%=c.getSaldo()%>
					</option>
					<%
					}
					}
					%>
				</select>
			</div>


			<div class="form-bloque">
				<label>Monto a transferir:</label> <input type="number" name="monto"
					id="monto" step="0.01" min="0.01" placeholder="Ej: 5000.00"
					required>
			</div>


			<input type="hidden" name="accion" value="transferirAcuentaPropia" />
			<input type="submit" value="Realizar Transferencia">
		</form>


		<%
		String error = (String) request.getAttribute("error");
		%>
		<%
		if (error != null) {
		%>
		<div style="color: red; margin: 10px 0;">
			<strong><%=error%></strong>
		</div>
		<%
		}
		%>

	</div>

</body>
</html>