<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>master</title>
 <!--agrego para ver si anda-->
<style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }
        
        .header {
            background-color: #2c3e50;
            position: relative;
            display: flex;
            color: white;
            padding: 1rem 0;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            align-items: center;
    		justify-content: space-between;
        }
        
        .container {
       		position: relative;
       		display: flex;
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            align-items: center;
    		justify-content: space-between;
        }
		
	
        
        .nav-menu {
		    list-style: none;
		    display: flex;
		    gap: 1rem;
		    flex-wrap: wrap;
		    align-items: center;
		    padding: 0;
		    margin: 0;
		}
        
        .nav-menu li {
            display: inline;
        }
        
        .nav-menu a {
            color: white;
            text-decoration: none;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        
        .nav-menu a:hover {
            background-color: #34495e;
        }
        
        .main-content {
            padding: 2rem 0;
        }
        
        @media (max-width: 768px) {
            .nav-menu {
                flex-direction: column;
                gap: 0.3rem;
            }
            
            .nav-menu a {
                display: block;
                text-align: center;
                margin: 0.2rem 0;
            }
        }
        .logout-item {
		    margin-left: auto; /* empuja este <li> al extremo derecho */
		}
		
		.logout-item form {
		    margin: 0;
		}
		
		.logout-item input[type="submit"] {
		    background-color: #e74c3c;
		    color: white;
		    border: none;
		    padding: 8px 14px;
		    font-size: 13px;
		    border-radius: 5px;
		    cursor: pointer;
		    transition: background-color 0.3s ease;
		}
		.logout-item input[type="submit"]:hover {
			background-color: #c0392b;
		}
		.user-info {
		  color: white;
		    font-size: 1.2rem;
		    font-weight: bold;
		     margin-right: 40px; 
		}
			
		
    </style>
</head>
<body>

  <header class="header">
        <div class="container">
          
            
            <!-- Mostrar el nombre del usuario desde la sesión -->
        <div class="user-info">
          <%= session.getAttribute("nombreUsuario")  %>
        </div>
        
            <nav>
                <ul class="nav-menu">
					<li><a href="<%= request.getContextPath() %>/ClienteServlet?Param=1&accion=listar">ABML Cliente</a></li>
					<li><a href="<%= request.getContextPath() %>/CuentaServlet?accion=listar">ABML Cuentas</a></li>
                    <li><a href="<%= request.getContextPath() %>/PrestamoServlet?accion=listarPrestamoP">Préstamos</a></li>
                    <li><a href="<%= request.getContextPath() %>/vistas/Reportes.jsp">Informes</a></li>
					 <li class="logout-item">
				        <form action="<%= request.getContextPath() %>/LogoutServlet" method="post">
				            <input type="submit" value="Cerrar sesión" name="btnCerrarSesion">
				        </form>
				    </li>
                </ul>
            </nav>
        </div>
    </header>
    
    
 
</body>
</html>