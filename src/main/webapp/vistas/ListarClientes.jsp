<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidad.Cliente"%>
<%@ page import="entidad.Provincia"%>
<%@ page import="entidad.Localidad"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Clientes</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script>
	    $(document).ready(function () {
	        $('#listadoClientes').DataTable({
	            "pageLength": 5
	        });
	        $('#buscadorNombre').on('keyup', function () {
	            var table = $('#listadoClientes').DataTable();
	            table.column(2).search(this.value).draw();
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
            <a href="<%=request.getContextPath()%>/CargarProvincias">Nuevo Cliente</a>
            <br><br><br><br><br>
		    <a href="<%=request.getContextPath()%>/ClienteServlet?accion=listar">Clientes Activos</a>
		    <a href="<%=request.getContextPath()%>/ClienteServlet?accion=listarInactivos">Clientes Dados de Baja</a>
        </div>

		<!--  MENSAJES EXITOS O ERROR  -->
		<% String error = (String) request.getAttribute("errorMensaje"); %>
		<% String exito = (String) request.getAttribute("mensajeExito"); %>
			
		<% if (exito != null) { %>
		    <div class="alert success"><%= exito %></div>
		<% } %>
		<% if (error != null) { %>
		    <div class="alert error"><%= error %></div>
		<% } %>
		
		<label for="buscadorNombre">Buscar por Nombre:</label>
		<input type="text" id="buscadorNombre" placeholder="Ingrese Nombre" style="margin-left:10px; padding:4px;">

        <table id="listadoClientes" class="display">
            <thead>
                <tr>
                    <th>DNI</th>
                    <th>CUIL</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Sexo</th>
                    <th>Nacionalidad</th>
                    <th>Fecha de Nacimiento</th>
                    <th>Dirección</th>
                    <th>Provincia</th>
                    <th>Localidad</th>
                    <th>Correo Electrónico</th>
                    <th>Teléfonos</th>
                    <th>Usuario</th>
                    <th>Contraseña</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
             		// OBTENEMOS LOS DATOS DE LA BD DESDE EL SERVELT
                    String editarDni = (String) request.getAttribute("editarDni");
                    List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
                    List<Provincia> provincias = (List<Provincia>) request.getAttribute("provincias");
                    List<Localidad> localidades = (List<Localidad>) request.getAttribute("localidades");
                    Boolean clientesInactivos = (Boolean) request.getAttribute("clientesInactivos");
                    if (listaClientes != null) {
                        for (Cliente cli : listaClientes) {
                            boolean enEdicion = editarDni != null && editarDni.equals(cli.getDni());
                %>
                <tr>
                    <% if (enEdicion) { %>
                    <!-- FILA EN MODO EDICIÓN -->
                    <form method="post" action="ClienteServlet" onsubmit="return confirm('¿Está seguro que desea guardar los cambios del cliente?');">
                        <input type="hidden" name="accion" value="modificar">
                        <input type="hidden" name="dni" value="<%=cli.getDni()%>">
                        <td><%=cli.getDni()%></td>
                        <td><input type="text" name="cuil" value="<%=cli.getCuil()%>"></td>
                        <td><input type="text" name="nombre" value="<%=cli.getNombre()%>"></td>
                        <td><input type="text" name="apellido" value="<%=cli.getApellido()%>"></td>
						<td>
						    <select name="sexo">
						        <option value="F" <%= cli.getSexo().equals("F") ? "selected" : "" %>>Femenino</option>
						        <option value="M" <%= cli.getSexo().equals("M") ? "selected" : "" %>>Masculino</option>
						        <option value="O" <%= cli.getSexo().equals("O") ? "selected" : "" %>>Otro</option>
						    </select>
						</td>
                        <td><input type="text" name="nacionalidad" value="<%=cli.getNacionalidad()%>"></td>
                        <td><input type="date" name="fechaNacimiento" value="<%=cli.getFechaNacimiento()%>"></td>
                        <td><input type="text" name="direccion" value="<%=cli.getDireccion()%>"></td>
                        <!-- SELECT DE PROVINCIA Y ACTUALIZACION DE LAS LOCALIDADES CUANDO SE CAMBIA LA PROVINCIA -->
                        <td>
                            <select name="provincia" id="provincia_<%=cli.getDni()%>" onchange="actualizarLocalidades('<%=cli.getDni()%>')">
								<%
								    for (Provincia prov : provincias) {
								        String selected = cli.getProvincia().getId().equals(prov.getId()) ? "selected" : "";
								%>
								<option value="<%=prov.getId()%>" <%=selected%>><%=prov.getDescripcion()%></option>
								<% } %>
                            </select>
                        </td>
                        <!-- SELECT DE LOCALIDADES SEGUN LA PROVINCIA -->
                        <td>
                            <select name="localidad" id="localidad_<%=cli.getDni()%>">
                                <%
                                    for (Localidad loc : localidades) {
                                    	if (loc.getIdProvincia_Loc().equals(cli.getProvincia().getId())) {
                                    	    String selected = cli.getLocalidad().getIdLocalidad_Loc().equals(loc.getIdLocalidad_Loc()) ? "selected" : "";
                                %>
                                <option value="<%=loc.getIdLocalidad_Loc()%>" <%=selected%>><%=loc.getDescripcion_Loc()%></option>
                                <%      } } %>
                            </select>
                        </td>
                        <td><input type="email" name="email" value="<%=cli.getCorreoElectronico()%>"></td>
                        <td><input type="text" name="telefonos" value="<%=cli.getTelefonos()%>"></td>
						<td><%= cli.getUsuario().getNombreUsu() %></td>
						<td><input type="text" name="password" value="<%= cli.getUsuario().getContraseniaUsu() %>"></td>
                        <td>
                            <button type="submit" class="btn-accion btn-modificar">Guardar</button>
                            <a href="ClienteServlet?accion=listar" class="btn-accion btn-eliminar">Cancelar</a>
                        </td>
                    </form>
                    <% } else { %>
                    <!-- FILA NORMAL -->
                    <td><%=cli.getDni()%></td>
                    <td><%=cli.getCuil()%></td>
                    <td><%=cli.getNombre()%></td>
                    <td><%=cli.getApellido()%></td>
                    <td><%=cli.getSexo()%></td>
                    <td><%=cli.getNacionalidad()%></td>
                    <td><%=cli.getFechaNacimiento()%></td>
                    <td><%=cli.getDireccion()%></td>
					<td><%=cli.getProvincia().getDescripcion()%></td>
					<td><%=cli.getLocalidad().getDescripcion_Loc()%></td>
                    <td><%=cli.getCorreoElectronico()%></td>
                    <td><%=cli.getTelefonos()%></td>
					<td><%= cli.getUsuario().getNombreUsu() %></td>
					<td><%= cli.getUsuario().getContraseniaUsu() %></td>
                    <td>
                    <% if (clientesInactivos == null || !clientesInactivos) { %>
                        <div class="acciones-btn">
                            <form method="get" action="ClienteServlet">
                                <input type="hidden" name="accion" value="editar">
                                <input type="hidden" name="dni" value="<%=cli.getDni()%>">
                                <button type="submit" class="btn-accion btn-modificar">Modificar</button>
                            </form>
                            <form method="post" action="ClienteServlet"
                                onsubmit="return confirm('¿Está seguro que desea eliminar este cliente?');">
                                <input type="hidden" name="accion" value="eliminar">
                                <input type="hidden" name="dni" value="<%=cli.getDni()%>">
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

    <script>
        // CON ESTE SCRIPT CARGAMOS LAS LOCALIDADES DE CADA PROVINCIA
        var localidadesPorProvincia = {};
        <% 
            List<entidad.Localidad> todasLasLocs = (List<entidad.Localidad>) request.getAttribute("localidades");
            for (entidad.Localidad loc : todasLasLocs) {
        %>
            if (!localidadesPorProvincia['<%=loc.getIdProvincia_Loc()%>']) {
                localidadesPorProvincia['<%=loc.getIdProvincia_Loc()%>'] = [];
            }
            localidadesPorProvincia['<%=loc.getIdProvincia_Loc()%>'].push({
                id: '<%=loc.getIdLocalidad_Loc()%>',
                nombre: '<%=loc.getDescripcion_Loc()%>'
            });
        <% } %>
    </script>

    <script>
 		// CON ESTE SCRIPT ACTUALIZAMOS LAS LOCALIDADES CUANDO SE CAMBIA LA PROVINCIA
        function actualizarLocalidades(dni) {
            var provinciaSelect = document.getElementById("provincia_" + dni);
            var localidadSelect = document.getElementById("localidad_" + dni);
            var idProvincia = provinciaSelect.value;

            var localidades = localidadesPorProvincia[idProvincia] || [];
            localidadSelect.innerHTML = "";

            localidades.forEach(function (loc) {
                var option = document.createElement("option");
                option.value = loc.id;
                option.text = loc.nombre;
                localidadSelect.appendChild(option);
            });
        }
    </script>
</body>
</html>
