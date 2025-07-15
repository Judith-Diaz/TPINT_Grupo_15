<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Historial de Movimientos</title>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#listadoHistorial').DataTable();
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

select {
	padding: 8px;
	font-size: 14px;
	border-radius: 5px;
	border: 1px solid #ccc;
	margin: 8px 0;
	width: 300px;
}

input[type="submit"] {
	padding: 8px 12px;
	border: none;
	border-radius: 5px;
	background-color: #3498db;
	color: white;
	cursor: pointer;
	font-size: 14px;
	margin-top: 10px;
}

input[type="submit"]:hover {
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
</style>
</head>
<body>

<jsp:include page="/WEB-INF/MasterUsuario.jsp" />

<div class="listado-wrapper">

	<div class="acciones">
		<form>
			<!-- Selección de Numero de Cuenta -->
			<label for="cbu"><strong>Seleccione Número de Cuenta:</strong></label><br>
			<select name="cbu" id="cbu" required>
				<option value="">-- Seleccione una cuenta --</option>
				<option value="42623741521">42623741521</option>
				<option value="120584923">120584923</option>
				<option value="82719302">82719302</option>
			</select><br>

			<!-- Selección de tipo de cuenta -->
			<label for="cuentaSeleccionada"><strong>Seleccione tipo de cuenta:</strong></label><br>
			<select name="cuentaSeleccionada" id="cuentaSeleccionada" required>
				<option value="">-- Seleccione una cuenta --</option>
				<option value="315-629344/1">315-629344/1 - Caja de Ahorro</option>
				<option value="214-789654/2">214-789654/2 - Cuenta Corriente</option>
			</select><br>

			<input type="submit" value="Ver movimientos">
		</form>
	</div>

	<h3>Historial de movimientos realizados en cuenta seleccionada:</h3>

	<table id="listadoHistorial" class="display">
		<thead>
			<tr>
				<th>Número de Movimiento</th>
				<th>Cuenta Origen</th>
				<th>Cuenta Destino</th>
				<th>Monto</th>
				<th>Fecha</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>001</td>
				<td>315-629344/1</td>
				<td>001-000001/0</td>
				<td>$5,000</td>
				<td>2025-06-01</td>
			</tr>
			<tr>
				<td>002</td>
				<td>315-629344/1</td>
				<td>214-789654/2</td>
				<td>$7,200</td>
				<td>2025-06-03</td>
			</tr>
			<tr>
				<td>003</td>
				<td>214-789654/2</td>
				<td>314-629309/1</td>
				<td>$2,500</td>
				<td>2025-06-05</td>
			</tr>
			<tr>
				<td>004</td>
				<td>314-552301/7</td>
				<td>314-629309/1</td>
				<td>$9,000</td>
				<td>2025-06-06</td>
			</tr>
			<tr>
				<td>005</td>
				<td>214-789654/2</td>
				<td>314-552301/7</td>
				<td>$4,400</td>
				<td>2025-06-07</td>
			</tr>
			<tr>
				<td>006</td>
				<td>314-552301/7</td>
				<td>214-789654/2</td>
				<td>$6,800</td>
				<td>2025-06-09</td>
			</tr>
		</tbody>
	</table>

</div>

</body>
</html>