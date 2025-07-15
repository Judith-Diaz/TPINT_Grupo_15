<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu Préstamos</title>
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
	background-color: #3498db;
	color: #2c3e50;
}

.btn-pagar:hover {
	background-color: #2980b9;
}

</style>
</head>
<body>
	<jsp:include page="/WEB-INF/MasterUsuario.jsp" />

	<div class="listado-wrapper">
		<h2>Prestamos pendientes</h2><br><br>
		<table>
			<thead>
				<tr>
					
				<th>Tipo de cuenta</th>
				<th>Cuotas Pendientes</th>
				<th>Cuota Actual</th>
                <th>Próximo pago </th>
                <th>Prestamo Total</th>      
                <th>Abonado</th>  
                <th>Estado</th>   
                <th>Acciones</th>
                
				</tr>
		</thead>
			<tbody>
		        <tr>
		            <td><select name="cuentaOrigen" id="cuentaOrigen">
				<option value="314-629309/1">314-629309/1 - Caja de Ahorro</option>
				<option value="214-789654/2">214-789654/2 - Cuenta Corriente</option>
			</select></td>
		            <td>7</td>
		            <td>$10,000</td>
		            <td>2025-08-01</td>
		            <td>$100,000</td>
		            <td>$30,000</td>     
		            <td>Activo</td>    
		            <td>
		                <div class="acciones-btn">
		                    <button type="submit" class="btn-accion btn-pagar">Pagar</button>
		                </div>
		            </td>
		        </tr>
    	</tbody>
	</table>
	</div>
</body>
</html>