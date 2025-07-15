<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="entidad.Movimiento"%>
<%@ page import="entidad.Cuenta"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Cliente</title>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script>
        $(document).ready(function () {
            $('#listadoMovimientos').DataTable();
            "pageLength": 5
        });
    </script>

<style>
/* --- estilos (igual que antes) --- */
body {
	font-family: Arial;
	background-color: #f8f9fa;
	font-size: 14px;
	padding: 0;
}

.listado-wrapper {
	background-image:
		url('<%=request.getContextPath()%>/imagenes/principal.jpg');
	background-size: cover;
	background-position: center;
	background-repeat: no-repeat;
	padding: 20px;
	max-width: 100%;
	margin: auto;
}

.contenedor-flex {
	display: flex;
	gap: 20px;
	max-width: 1400px;
	margin: auto;
	align-items: stretch;
}

.columna-izquierda, .columna-derecha {
	background-color: rgba(200, 230, 255, 0.7);
	padding: 20px;
	border: 1px solid #ccc;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, .2);
	box-sizing: border-box;
	display: flex;
	flex-direction: column;
}

.columna-izquierda {
	flex: 3;
	align-items: inline-block;
	gap: 10px;
}

.columna-derecha {
	flex: 1;
	align-items: center;
	gap: 15px;
}

.acciones {
	margin-bottom: 20px;
	text-align: center;
	margin: 20px auto;
}

select {
	width: 300px;
	padding: 8px;
	font-size: 14px;
	border: 1px solid #ccc;
	border-radius: 5px;
	margin: 8px 0;
}

input[type="submit"] {
	width: 180px;
	padding: 10px 15px;
	font-size: 14px;
	border: none;
	border-radius: 5px;
	background: #3498db;
	color: #fff;
	cursor: pointer;
}

input[type="submit"]:hover {
	background: #2980b9;
}

.botones-mov {
	display: flex;
	justify-content: center;
	gap: 15px;
	margin: 20px 0;
}

h2 {
	margin-bottom: 30px;
	text-align: center;
}

h3 {
	margin: 30px 0 20px;
	text-align: center;
}

.tabla-contenedor {
	overflow-x: auto;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 10px;
	background: #fff;
}

th, td {
	padding: 12px;
	border: 1px solid #ccc;
	white-space: nowrap;
	text-align: center;
}

th {
	background: #2c3e50;
	color: #fff;
}

tr:hover {
	background: #f2f2f2;
}

.saldo-box {
	margin: 10px auto 0;
	padding: 6px 10px;
	width: fit-content;
	font-size: 15px;
	color: #333;
	text-align: center;
	font-weight: bold;
}

fieldset {
	border: 5px solid #2c3e50;
	border-radius: 8px;
	width: 300px;
}

label {
	display: inline-block;
	width: 60px;
}

input[type="date"] {
	width: 200px;
}

.fechas-rango {
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-top: 20px;
	gap: 10px;
}

.acciones label, .acciones select {
	display: inline-block;
}

.error {
	color: red;
	font-weight: bold;
	text-align: center;
	margin-bottom: 10px;
}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/MasterUsuario.jsp" />

	<div class="listado-wrapper">
		<div class="contenedor-flex">

			<div class="columna-izquierda">
				<h2>Bienvenid@, <%= session.getAttribute("nombreUsuario") %></h2>
				<div class="acciones">
					<form
						action="${pageContext.request.contextPath}/UsuarioClienteServlet"
						method="post">
						<input type="hidden" name="accion" value="verMovimientos" />
						<fieldset>
							<legend>
								<strong>Seleccione Número de Cuenta:</strong>
							</legend>
							<select name="cuentaDestino" required
								onchange="this.form.submit()">
								<option value="">-- cuenta --</option>
								<%
								List<Cuenta> listaCuentas = (List<Cuenta>) session.getAttribute("listaCuentas");
								String cuentaSeleccionada = request.getParameter("cuentaDestino");
								if (listaCuentas != null) {
									for (Cuenta cuenta : listaCuentas) {
										String selected = (cuentaSeleccionada != null
										&& cuentaSeleccionada.equals(String.valueOf(cuenta.getNumeroCuenta()))) ? "selected" : "";
								%>
								<option value="<%=cuenta.getNumeroCuenta()%>" <%=selected%>>
									Cuenta
									<%=cuenta.getNumeroCuenta()%> -
									<%=cuenta.getTipoCuenta().getNombreTipo()%> - $<%=cuenta.getSaldo()%>
								</option>
								<%
								}
								}
								%>
							</select>
						</fieldset>
					</form>
					<form
						action="${pageContext.request.contextPath}/UsuarioClienteServlet"
						method="post">
						<input type="hidden" name="accion" value="verMovimientos">

						<% String cuentaSeleccionadaHidden = request.getParameter("cuentaDestino"); %>
						<input type="hidden" name="cuentaDestino" value="<%= cuentaSeleccionadaHidden != null ? cuentaSeleccionadaHidden : "" %>">

						<div class="fechas-rango">
							<div>
								<label for="fecha_desde">Desde:</label> <input type="date"
									id="fecha_desde" name="fecha_desde"
									value="<%=request.getParameter("fecha_desde") != null ? request.getParameter("fecha_desde") : ""%>">
							</div>

							<div>
								<label for="fecha_hasta">Hasta:</label> <input type="date"
									id="fecha_hasta" name="fecha_hasta"
									value="<%=request.getParameter("fecha_hasta") != null ? request.getParameter("fecha_hasta") : ""%>">
							</div>

							<input type="submit" style="margin:5px" value="Filtrar">
						</div>
					</form>
					<!-- LISTAR TODO************************ -->
					<form action="${pageContext.request.contextPath}/UsuarioClienteServlet" method="post">
						<input type="hidden" name="accion" value="listarTodo">
						
						<% String cuentaSelec = request.getParameter("cuentaDestino"); %>
						<input type="hidden" name="cuentaDestino" value="<%= cuentaSelec != null ? cuentaSelec : "" %>">
							<input type="submit" style="margin:5px" value="Listar Todo">
						
					</form>
				</div>

				<h3>Historial de movimientos realizados en cuenta seleccionada:</h3>

				<div class="tabla-contenedor">
					<table  id="listadoMovimientos" class="display">
						<thead>
							<tr>
								<th>Nro Movimiento</th>
								<th>Numero Cuenta</th>
								<th>Fecha</th>
								<th>Detalle</th>
								<th>Importe</th>
								<th>Tipo Movimiento</th>
							</tr>
						</thead>
							<tbody>
							<%
							    List<Movimiento> lista = (List<Movimiento>) request.getAttribute("listaMovimientos");
							    if (lista != null && !lista.isEmpty()) {
							        for (Movimiento m : lista) {
							%>
							<tr>
							    <td><%= m.getIDMovimiento() %></td>
							    <td><%= m.getCuenta() != null ? m.getCuenta().getNumeroCuenta() : "" %></td>
							    <td><%= m.getFecha() %></td>
							    <td><%= m.getDetalle() %></td>
							    <td>$<%= m.getImporte() %></td>
							    <td><%= m.getTipoMovimiento() != null ? m.getTipoMovimiento().getDescripcion() : "" %></td>
							</tr>
							<%
							        }
							    } else {
							%>
							<tr><td colspan="7">No hay movimientos para mostrar</td></tr>
							<%
							    }
							%>
							</tbody>
					</table>
				</div>

				<%
				if (request.getAttribute("error") != null) {
				%>
				<div class="error"><%=request.getAttribute("error")%></div>
				<%
				}
				%>

			</div>

			<div class="columna-derecha">
				<form
					action="${pageContext.request.contextPath}/UsuarioClienteServlet"
					method="get">
					<input type="hidden" name="accion" value="solicitarPrestamo">
					<input type="submit" value="Solicitud Préstamo">
				</form>

				<form action="${pageContext.request.contextPath}/PrestamoServlet" method="get">
				    <input type="hidden" name="accion" value="verCuotasYPagos" />
				    <input type="submit" value="Pagar Préstamo" />
				</form>

				<form
					action="${pageContext.request.contextPath}/UsuarioClienteServlet"
					method="post">
					<input type="hidden" name="accion" value="transferencias" /> <input
						type="submit" value="Transferencias" />
				</form>

				<form
					action="${pageContext.request.contextPath}/UsuarioClienteServlet"
					method="post">
					<input type="hidden" name="accion" value="autotransferencias" /> <input
						type="submit" value="AutoTransferencia" />
				</form>

				<form
					action="${pageContext.request.contextPath}/UsuarioClienteServlet"
					method="post">
					<input type="hidden" name="accion" value="verInformacionPersonal" />
					<input type="submit" value="Datos Personales" />
				</form>
			</div>

		</div>
	</div>

</body>
</html>



