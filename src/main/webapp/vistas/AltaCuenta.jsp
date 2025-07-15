<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Alta de Cuenta</title>
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
		select, input[type="text"], input[type="number"], input[type="date"] {
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
	<jsp:include page="/WEB-INF/MasterAdmin.jsp" />
	<div class="contenedor">
		
		<form action="<%=request.getContextPath()%>/CuentaServlet?accion=alta" method="post">
			<h2>Alta de Cuenta Bancaria</h2><br>
		    <label> Dni del Cliente: </label><input type="text" name="cliente"><br>
<!-- 		    <label>Fecha de Creación: </label><input type="date" name="fechaCreacion"><br> -->
		    <label>Tipo de Cuenta: </label>
		    <select name="tipoCuenta">
		        <option value="1">Caja de ahorro</option>
		        <option value="2">Cuenta corriente</option>
		    </select><br>
		    <!-- lo voy a ocmentar ya que el numero de cuenta es automatico en la bd y el cbu es ramdom  -->
		    <!--  <label>Número de Cuenta: </label><input type="text" name="numeroCuenta"><br> -->
		    <!-- <label>CBU: </label><input type="text" name="cbu"><br> -->
		   
		    
		    <label>Saldo: <b>10.000$</b></label> <!-- <input type="number" name="saldo"><br> -->
		    <input type="submit" value="Crear Cuenta">
		    <br>
			
		</form>
		    <br>
		<!--Mensaje segun la validacion de si existe o no el dni yla cantidad de cuentas scriplerr-->
				 <% if (request.getAttribute("mensaje") != null) { %>
   				 <label style="color: red;"><%= request.getAttribute("mensaje") %></label>
				<% } %>
		    
	</div>
	
	
</body>