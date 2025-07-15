<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="entidad.Provincia"%>

<%
    List<Provincia> provincias = (List<Provincia>) request.getAttribute("provincias");
    if (provincias == null) provincias = new ArrayList<>();

    String provinciaSeleccionada = (String) request.getAttribute("provinciaSeleccionada");
    String localidadSeleccionada = (String) request.getAttribute("localidadSeleccionada");
    String mensajeError = (String) request.getAttribute("mensajeError");
%>

<!DOCTYPE html>
<html>
<head>
<title>Alta de Cliente</title>
<meta charset="UTF-8">
<style>
/* Estilos simplificados */
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

input, select {
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

input[type="submit"] {
	background-color: #3498db;
	color: white;
	font-size: 14px;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #2980b9;
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

<script>
window.onload = function () {
    // Limitar fecha para mayores de 18
    const fechaInput = document.getElementById("fechaNacimiento");
    const hoy = new Date();
    hoy.setFullYear(hoy.getFullYear() - 18);
    fechaInput.max = hoy.toISOString().split("T")[0];

    // Exito o no
    const params = new URLSearchParams(window.location.search);
    if (params.get("exito") === "true") {
        mostrarModal("Cliente agregado con éxito", "success");
    } else if (params.get("exito") === "false") {
        mostrarModal("Error al agregar cliente", "error");
    }
};

// Validar edad mínima antes de enviar
document.addEventListener("DOMContentLoaded", () => {
    document.querySelector("form.altaCliente").addEventListener("submit", function (e) {
        const fechaNacimiento = document.getElementById("fechaNacimiento").value;
        const fechaNac = new Date(fechaNacimiento);
        const hoy = new Date();
        hoy.setFullYear(hoy.getFullYear() - 18);
        if (fechaNac > hoy) {
            e.preventDefault();
            alert("Debe ser mayor de 18 años.");
        }
    });
});

function cargarLocalidades() {
    const provinciaId = document.getElementById("ddlProvincia").value;
    const ddlLocalidad = document.getElementById("ddlLocalidad");
    ddlLocalidad.innerHTML = '<option value="">Cargando...</option>';
    ddlLocalidad.disabled = true;

    if (provinciaId === "") return;

    fetch('<%= request.getContextPath() %>/CargarLocalidades?provinciaId=' + encodeURIComponent(provinciaId))
        .then(resp => resp.json())
        .then(localidades => {
            ddlLocalidad.disabled = false;
            ddlLocalidad.innerHTML = '<option value="">Seleccione una localidad</option>';
            localidades.forEach(loc => {
                const option = document.createElement("option");
                option.value = loc.id;
                option.text = loc.descripcion;
                if (option.value === "<%= localidadSeleccionada != null ? localidadSeleccionada : "" %>") {
                    option.selected = true;
                }
                ddlLocalidad.appendChild(option);
            });
        })
        .catch(() => {
            ddlLocalidad.innerHTML = '<option value="">Error cargando localidades</option>';
        });
}

function mostrarModal(texto, tipo) {
    const overlay = document.createElement("div");
    overlay.style.position = "fixed";
    overlay.style.top = 0; overlay.style.left = 0;
    overlay.style.width = "100%"; overlay.style.height = "100%";
    overlay.style.backgroundColor = "rgba(0,0,0,0.5)";
    overlay.style.display = "flex";
    overlay.style.alignItems = "center";
    overlay.style.justifyContent = "center";
    overlay.style.zIndex = 10000;

    const modal = document.createElement("div");
    modal.style.backgroundColor = "white";
    modal.style.padding = "20px";
    modal.style.borderRadius = "8px";
    modal.style.maxWidth = "300px";
    modal.style.textAlign = "center";

    const msg = document.createElement("p");
    msg.textContent = texto;
    msg.style.color = tipo === "success" ? "#28a745" : "#c0392b";
    msg.style.marginBottom = "20px";
    modal.appendChild(msg);

    const btn = document.createElement("button");
    btn.textContent = "Aceptar";
    btn.style.backgroundColor = tipo === "success" ? "#28a745" : "#c0392b";
    btn.style.color = "white"; btn.style.border = "none";
    btn.style.borderRadius = "4px"; btn.style.padding = "8px 16px";
    btn.onclick = () => {
        document.body.removeChild(overlay);
        history.replaceState(null, "", window.location.pathname);
    };
    modal.appendChild(btn);
    overlay.appendChild(modal);
    document.body.appendChild(overlay);
}
</script>
</head>

<body>
	<jsp:include page="/WEB-INF/MasterAdmin.jsp" />

	<div class="contenedor">
		<form class="altaCliente" action="ClienteServlet" method="post">
			<input type="hidden" name="accion" value="alta" />
			<h2>Alta de Cliente</h2>
			
			<!--  MENSAJES EXITOS O ERROR  -->
			<% String error = (String) request.getAttribute("errorMensaje"); %>
			<% String exito = (String) request.getAttribute("mensajeExito"); %>
				
			<% if (exito != null) { %>
			    <div class="alert success"><%= exito %></div>
			<% } %>
			<% if (error != null) { %>
			    <div class="alert error"><%= error %></div>
			<% } %>
			
			<label>DNI:</label> <input type="text" name="dni" required
				pattern="^\d{8}$" title="Ingrese un DNI válido de 8 dígitos">

			<label>CUIL:</label> <input type="text" name="cuil" required
				pattern="^\d{11}$" title="Ingrese un CUIL válido de 11 dígitos">

			<label>Nombre:</label> <input type="text" name="nombre" required
				pattern="^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$"
				title="Ingrese un nombre válido (solo letras)"> <label>Apellido:</label>
			<input type="text" name="apellido" required
				pattern="^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$"
				title="Ingrese un apellido válido (solo letras)"> <label>Sexo:</label>
			<select name="sexo" required>
				<option value="">Seleccione</option>
				<option value="M">Masculino</option>
				<option value="F">Femenino</option>
			</select> <label>Nacionalidad:</label> <input type="text" name="nacionalidad"
				required pattern="^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$"
				title="Ingrese una nacionalidad válida"> <label>Fecha
				de Nacimiento:</label> <input type="date" name="fechaNacimiento"
				id="fechaNacimiento" required title="Debe ser mayor de 18 años">

			<label>Dirección:</label> <input type="text" name="direccion"
				required pattern="^[a-zA-Z0-9\s.,º°#-]+$"
				title="Ingrese una dirección válida"> <label>Provincia:</label>
			<select name="provincia" id="ddlProvincia"
				onchange="cargarLocalidades()" required>
				<option value="">Seleccione una provincia</option>
				<% for (Provincia prov : provincias) { %>
				<option value="<%= prov.getId() %>"
					<%= (provinciaSeleccionada != null && provinciaSeleccionada.equals(String.valueOf(prov.getId()))) ? "selected" : "" %>>
					<%= prov.getDescripcion() %>
				</option>
				<% } %>
			</select> <label>Localidad:</label> <select name="localidad" id="ddlLocalidad"
				required>
				<option value="">Seleccione una localidad</option>
			</select> <label>Correo Electrónico:</label> <input type="email" name="email">

			<label>Teléfonos:</label> <input type="text" name="telefonos">

			<label>Usuario:</label> <input type="text" name="usuario" required>

			<label>Contraseña:</label> <input type="password" name="password"
				required> <label>Confirmar Contraseña:</label> <input
				type="password" name="confirmPassword" required>

			<input type="submit" name="btnAgregar" value="Registrar">
		</form>
	</div>
</body>
</html>