<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Reportes</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" 
    integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
    <style>
        body {
            font-family: Arial;
            background-color: #f8f9fa;
            font-size: 14px;
        }

        .contenedor-principal {
            max-width: 1000px;
            margin: auto;
            padding: 20px;
        }

        .botones-reporte {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            justify-content: center;
            margin-bottom: 20px;
        }

        button {
            background-color: #3498db;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #2980b9;
        }

        .contenedor {
            display: none;
            padding: 20px;
            margin-bottom: 20px;
            background-color: white;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        .contenedor.activo {
            display: block;
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

        input[type="date"],
        input[type="submit"],
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input[type="submit"] {
            background-color: #3498db;
            color: white;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #2980b9;
        }

        .resultados {
            margin-top: 20px;
            background-color: #ecf0f1;
            padding: 15px;
            border-radius: 5px;
        }
    </style>

    <!-- 
    <script>
        function mostrarReporte(id) {
            let secciones = document.querySelectorAll('.contenedor');
            secciones.forEach(sec => sec.classList.remove('activo'));
            document.getElementById(id).classList.add('activo');
        }
    </script>
     -->
</head>
<body>
     <jsp:include page="/WEB-INF/MasterAdmin.jsp" />

    <div class="contenedor-principal">
	    <div class="botones-reporte">
	    	
	    	<form method="post" action="${pageContext.request.contextPath}/ReportesServlet">
				<input type="hidden" name="accion" value="generarPorcentaje">
				<button type="submit" class="btn-accion btn-generarPorcentaje">Cantidad de cuentas por tipo</button>
			</form>
			<form method="post" action="${pageContext.request.contextPath}/ReportesServlet">
				<input type="hidden" name="accion" value="generarCantPrestamos">
				<button type="submit" class="btn-accion btn-generarCantMovimientos">Cantidad de préstamos</button>
			</form>
			<form method="post" action="${pageContext.request.contextPath}/ReportesServlet">
				<input type="hidden" name="accion" value="generarCantClientes">
				<button type="submit" class="btn-accion btn-generarCantClientes">Cantidad de clientes con préstamos</button>
			</form>
	    </div>
        

        <!-- CANTIDAD DE PLATA POR MOVIMIENTOS -->
        <div class="contenedor" id="reporteMovimientos">
            <h2>Reporte de Movimientos</h2>
            <form action="ReporteMovimientosServlet" method="post">
                <label>Fecha desde:</label>
                <input type="date" name="fechaInicio" required>

                <label>Fecha hasta:</label>
                <input type="date" name="fechaFin" required>

                <input type="submit" value="Generar Reporte">
            </form>
            <div class="resultados">
                <p><strong>Total Ingresos:</strong> $</p>
                <p><strong>Total Egresos:</strong> $</p>
                <p><strong>Monto Neto:</strong> $</p>
                <p><strong>Cantidad de Movimientos:</strong> </p>
            </div>
        </div>

       <!-- CANTIDAD CUENTAS POR TIPO -->
		<div class="contenedor <%= (request.getAttribute("porcentajeAhorro") != null ? "activo" : "") %>" id="reporteCuentas">
		    <h2>Porcentaje de Cuentas por Tipo</h2>
		    
		    <%if (request.getAttribute("porcentajeAhorro") != null && request.getAttribute("porcentajeCorriente") != null) { %>
		    <div class="resultados">
		        <p><strong>Caja de Ahorro: </strong><%= request.getAttribute("porcentajeAhorro") %>%</p>
		        <div class="progress" role="progressbar" aria-label="Caja de Ahorro"
		             aria-valuenow="<%= request.getAttribute("porcentajeAhorro") %>" aria-valuemin="0" aria-valuemax="100">
		            <div class="progress-bar progress-bar-striped bg-info progress-bar-animated"
		                 style="width: <%= request.getAttribute("porcentajeAhorro") %>%"></div>
		        </div>
		        <br><br>
		
		        <p><strong>Cuenta Corriente: </strong><%= request.getAttribute("porcentajeCorriente") %>%</p>
		        <div class="progress" role="progressbar" aria-label="Cuenta Corriente"
		             aria-valuenow="<%= request.getAttribute("porcentajeCorriente") %>" aria-valuemin="0" aria-valuemax="100">
		            <div class="progress-bar progress-bar-striped bg-info progress-bar-animated"
		                 style="width: <%= request.getAttribute("porcentajeCorriente") %>%"></div>
		        </div>
		    </div>
		    <% } %>
		</div>

        <!-- CANTIDAD DE PRESTAMOS  -->
        <div class="contenedor <%= (request.getAttribute("totalPrestamosSolicitados") != null ? "activo" : "") %>" id="reportePrestamos">
    <h2>Estado de Préstamos</h2>

    <% if (request.getAttribute("totalPrestamosSolicitados") != null) { %>
        <div class="resultados">
            <%
                Object totalSolicitados = request.getAttribute("totalPrestamosSolicitados");
                Object pendientes = request.getAttribute("prestamosPendientes");
                Object aprobados = request.getAttribute("prestamosAprobados");
                Object rechazados = request.getAttribute("prestamosRechazados");
                Object porcentajePendientes = request.getAttribute("porcentajePendientes");
                Object porcentajeAprobados = request.getAttribute("porcentajeAprobados");
                Object porcentajeRechazados = request.getAttribute("porcentajeRechazados");

                boolean hayDatos = totalSolicitados != null && !totalSolicitados.toString().equals("0");
            %>

            <% if (hayDatos) { %>
                <!-- Total -->
                <div class="estadistica-item">
                    <strong>Total Préstamos Solicitados:</strong>
                    <span class="estadistica-valor"><%= totalSolicitados %></span>
                </div>

                <!-- Pendientes -->
                <div class="estadistica-item">
                    <strong>Préstamos Pendientes:</strong>
                    <span class="estadistica-valor"><%= pendientes != null ? pendientes : "0" %></span>
                    <% if (porcentajePendientes != null) { %>
                        <span class="estadistica-porcentaje">(<%= porcentajePendientes %>%)</span>
                    <% } %>
                </div>
                <div class="progress mb-3" role="progressbar"
                     aria-valuenow="<%= porcentajePendientes != null ? porcentajePendientes : "0" %>" 
                     aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar bg-warning progress-bar-striped progress-bar-animated"
                         style="width: <%= porcentajePendientes != null ? porcentajePendientes : "0" %>%"></div>
                </div>

                <!-- Aprobados -->
                <div class="estadistica-item">
                    <strong>Préstamos Aprobados:</strong>
                    <span class="estadistica-valor"><%= aprobados != null ? aprobados : "0" %></span>
                    <% if (porcentajeAprobados != null) { %>
                        <span class="estadistica-porcentaje">(<%= porcentajeAprobados %>%)</span>
                    <% } %>
                </div>
                <div class="progress mb-3" role="progressbar"
                     aria-valuenow="<%= porcentajeAprobados != null ? porcentajeAprobados : "0" %>" 
                     aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar bg-success progress-bar-striped progress-bar-animated"
                         style="width: <%= porcentajeAprobados != null ? porcentajeAprobados : "0" %>%"></div>
                </div>

                <!-- Rechazados -->
                <div class="estadistica-item">
                    <strong>Préstamos Rechazados:</strong>
                    <span class="estadistica-valor"><%= rechazados != null ? rechazados : "0" %></span>
                    <% if (porcentajeRechazados != null) { %>
                        <span class="estadistica-porcentaje">(<%= porcentajeRechazados %>%)</span>
                    <% } %>
                </div>
                <div class="progress mb-3" role="progressbar"
                     aria-valuenow="<%= porcentajeRechazados != null ? porcentajeRechazados : "0" %>" 
                     aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar bg-danger progress-bar-striped progress-bar-animated"
                         style="width: <%= porcentajeRechazados != null ? porcentajeRechazados : "0" %>%"></div>
                </div>

                <!-- Fecha -->
                <div class="estadistica-item">
                    <small class="text-muted">
                        <strong>Fecha de generación:</strong> <%= new java.util.Date() %>
                    </small>
                </div>
            <% } else { %>
                <div class="sin-datos">
                    <p>No hay préstamos registrados en el sistema.</p>
                </div>
            <% } %>
        </div>
    <% } else { %>
        <div class="sin-datos">
            <p>Haz clic en "Cantidad de préstamos" para generar el reporte.</p>
        </div>
    <% } %>
</div>

        <!-- CANTIDAD DE CLIENTES CON PRESTAMOS -->
        <%
   		 String accion = request.getParameter("accion");
   	 boolean mostrarReporteClientes = "generarCantClientes".equals(accion) 
        || request.getAttribute("cantidadClientesConPrestamo") != null;
%>
        <div class="contenedor <%= mostrarReporteClientes ? "activo" : "" %>" id="reporteClientes">
    <h2>Clientes que Solicitaron Préstamos</h2>

    <form action="<%= request.getContextPath() %>/ReportesServlet" method="post">
        <input type="hidden" name="accion" value="generarCantClientes" />

        <label>Fecha desde:</label>
        <input type="date" name="fechaInicio" required value="<%= request.getAttribute("fechaDesde") != null ? request.getAttribute("fechaDesde") : "" %>">

        <label>Fecha hasta:</label>
        <input type="date" name="fechaFin" required value="<%= request.getAttribute("fechaHasta") != null ? request.getAttribute("fechaHasta") : "" %>">

        <input type="submit" value="Mostrar Clientes">
    </form>

    <div class="resultados">
        <% Integer cantidad = (Integer) request.getAttribute("cantidadClientesConPrestamo"); %>
        <% if (cantidad != null) { %>
            <p><strong>Total de Clientes que solicitaron préstamos:</strong> <%= cantidad %></p>
            <p><strong>Período:</strong> Desde <%= request.getAttribute("fechaDesde") %> hasta <%= request.getAttribute("fechaHasta") %></p>
        <% } else { %>
            <p>Haz clic en "Mostrar Clientes" para generar el reporte.</p>
        <% } %>
    </div>
</div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" 
    integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
  </body>
</body>
</html>
