<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="entidad.TipoCuenta"%>
<%@ page import="entidad.Cliente" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Listado de Cuentas</title>
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
	<script>
	    $(document).ready(function () {
	        $('#listadoCuentas').DataTable({
	            "pageLength": 5
	        });
	        $('#buscadorDNI').on('keyup', function() {
	            var table = $('#listadoCuentas').DataTable();
	            table.column(1).search(this.value).draw();
	        });
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
			width: 95%;
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
		}

		tr:hover {
			background-color: #f2f2f2;
		}

		.acciones-btn {
			display: flex;
			gap: 10px;
			justify-content: center;
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

		.btn-modificar {
			background-color: #f1c40f;
			color: #2c3e50;
		}

		.btn-modificar:hover {
			background-color: #d4ac0d;
		}

		.btn-eliminar {
			background-color: #e74c3c;
		}

		.btn-eliminar:hover {
			background-color: #c0392b;
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
	<jsp:include page="/WEB-INF/MasterAdmin.jsp" />

	<div class="listado-wrapper">
		<div class="acciones">
			<a href="<%= request.getContextPath() %>/vistas/AltaCuenta.jsp">Nueva Cuenta</a>
			<br><br><br>
			<a href="<%=request.getContextPath()%>/CuentaServlet?accion=listar">Cuentas Activas</a>
    		<a href="<%=request.getContextPath()%>/CuentaServlet?accion=listarInactivas">Cuentas Inactivas</a>
    		<br><br><br>
    		<form method="get" action="CuentaServlet" style="display:inline-block; margin-left: 20px;">
	    		<label><strong>Filtrar por rango de saldo:</strong></label><br>
		        <input type="hidden" name="accion" value="filtrarMonto">
		        Desde: <input type="number" name="min" step="0.01" required style="width: 80px;">
		        Hasta: <input type="number" name="max" step="0.01" required style="width: 80px;">
		        <button type="submit" class="btn-accion btn-modificar">Filtrar</button>
		    </form>
		</div>
		
		<% 
		    String error = (String) request.getAttribute("errorMensaje");
		    String exito = (String) request.getAttribute("mensajeExito");
		%>
		
		<% if (exito != null) { %>
		    <div class="alert success"><%= exito %></div>
		<% } %>
		<% if (error != null) { %>
		    <div class="alert error"><%= error %></div>
		<% } %>
		
		<label for="buscadorDNI">Buscar por DNI:</label>
  		<input type="text" id="buscadorDNI" placeholder="Ingrese DNI" style="margin-left:10px; padding:4px;">	
  			
		<table id="listadoCuentas" class="display">
			<thead>
				<tr>
					<th>Número Cuenta</th>
					<th>DNI Cliente</th>
					<th>Fecha de Creación</th>
					<th>Tipo de Cuenta</th>
					<th>CBU</th>
					<th>Saldo</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<%
				String editarCuenta = (String) request.getAttribute("editarCuenta");
				List<TipoCuenta> listaTiposCuenta = (List<TipoCuenta>) request.getAttribute("listaTiposCuenta");
				List<Cuenta> listaCuentas = (List<Cuenta>) request.getAttribute("listaCuentas");
				Boolean cuentasInactivas = (Boolean) request.getAttribute("cuentasInactivas");
			
				if (listaCuentas != null) {
					for (Cuenta cu : listaCuentas) {
						boolean enEdicion = editarCuenta != null && Integer.parseInt(editarCuenta) == cu.getNumeroCuenta();
				%>
				<tr>
					<% if (enEdicion) { %>
					<!-- FILA EN MODO EDICIÓN -->
					<form action="CuentaServlet" method="post" onsubmit="return confirm('¿Está seguro que desea guardar los cambios de la cuenta?');">
						<input type="hidden" name="accion" value="modificar">
						<input type="hidden" name="numeroCuenta" value="<%= cu.getNumeroCuenta() %>">
						<input type="hidden" name="saldo" value="<%= cu.getSaldo() %>">
			
						<td><%= cu.getNumeroCuenta() %></td>
						<td><%= cu.getCliente().getDni() %></td>
						<td><%= cu.getFechaCreacion() %></td>
						<td>
							<select name="tipoCuenta">
								<%
								for (TipoCuenta tipo : listaTiposCuenta) {
									boolean selected = cu.getTipoCuenta().getIdTipoCuenta() == tipo.getIdTipoCuenta();
								%>
								<option value="<%= tipo.getIdTipoCuenta() %>" <%= selected ? "selected" : "" %>>
									<%= tipo.getNombreTipo() %>
								</option>
								<% } %>
							</select>
						</td>
						<td><%= cu.getCbu() %></td>
						<td><%= cu.getSaldo() %></td>
						<td>
							<button type="submit" class="btn-accion btn-modificar">Guardar</button>
							<a href="CuentaServlet?accion=listar" class="btn-accion btn-eliminar">Cancelar</a>
						</td>
					</form>
					<% } else { %>
					<!-- FILA NORMAL -->
					<td><%= cu.getNumeroCuenta() %></td>
					<td><%= cu.getCliente().getDni() %></td>
					<td><%= cu.getFechaCreacion() %></td>
					<td><%= cu.getTipoCuenta().getNombreTipo() %></td>
					<td><%= cu.getCbu() %></td>
					<td><%= cu.getSaldo() %></td>
					
					<td>
						<% if (cuentasInactivas == null || !cuentasInactivas) { %>
							<div class="acciones-btn">
								<form action="CuentaServlet" method="get">
									<input type="hidden" name="accion" value="editar">
									<input type="hidden" name="numeroCuenta" value="<%= cu.getNumeroCuenta() %>">
									<button type="submit" class="btn-accion btn-modificar">Modificar</button>
								</form>
								<form method="post" action="CuentaServlet" onsubmit="return confirm('¿Está seguro que desea eliminar esta cuenta?');">
									<input type="hidden" name="accion" value="eliminar">
									<input type="hidden" name="numeroCuenta" value="<%= cu.getNumeroCuenta() %>">
									<button type="submit" class="btn-accion btn-eliminar">Eliminar</button>
								</form>
							</div>
						<% } else { %>
							<span style="color: gray;">Sin acciones</span>
						<% } %>
					</td>
					<% } %>
				</tr>
				<% } } %>
			</tbody>
		</table>
	</div>
</body>
</html>
