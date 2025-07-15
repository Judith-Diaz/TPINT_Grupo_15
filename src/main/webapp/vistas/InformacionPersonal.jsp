<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="entidad.Cliente" %>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Información Personal</title>

    <style>
        body {
            font-family: Arial;
            background-color: #f8f9fa;
            font-size: 14px;
        }
        .form-wrapper {
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .info {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
        }
        h2 {
            text-align: center;
            color: #2c3e50;
        }
        label {
            display: block;
            margin-top: 12px;
            font-weight: bold;
        }
        p {
            margin: 4px 0 12px 0;
        }
    </style>

</head>
<body>
<jsp:include page="/WEB-INF/MasterUsuario.jsp" />

<div class="form-wrapper">
    <div class="info">
        <h2>Información Personal</h2><br>

        <label>DNI:</label>
        <p><%= cliente != null ? cliente.getDni() : "" %></p>

        <label>CUIL:</label>
        <p><%= cliente != null ? cliente.getCuil() : "" %></p>

        <label>Nombre:</label>
        <p><%= cliente != null ? cliente.getNombre() : "" %></p>

        <label>Apellido:</label>
        <p><%= cliente != null ? cliente.getApellido() : "" %></p>

        <label>Sexo:</label>
        <p><%= cliente != null ? cliente.getSexo() : "" %></p>

        <label>Nacionalidad:</label>
        <p><%= cliente != null ? cliente.getNacionalidad() : "" %></p>

        <label>Fecha de Nacimiento:</label>
        <p><%= cliente != null ? cliente.getFechaNacimiento() : "" %></p>

        <label>Dirección:</label>
        <p><%= cliente != null ? cliente.getDireccion() : "" %></p>

		<label>Localidad:</label>
		<p><%= cliente != null && cliente.getLocalidad() != null ? cliente.getLocalidad().getDescripcion_Loc() : "" %></p>
		
		<label>Provincia:</label>
		<p><%= cliente != null && cliente.getProvincia() != null ? cliente.getProvincia().getDescripcion() : "" %></p>
		
		<label>Correo Electrónico:</label>
		<p><%= cliente != null ? cliente.getCorreoElectronico() : "" %></p>
		
		<label>Teléfonos:</label>
		<p><%= cliente != null ? cliente.getTelefonos() : "" %></p>
		
		<label>Usuario:</label>
		<p><%= cliente != null && cliente.getUsuario() != null ? cliente.getUsuario().getNombreUsu() : "" %></p>
    </div>
</div>
</body>
</html>