<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<style>

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  font-family: 'Segoe UI', sans-serif;
}

html, body {

  height: 100%;
  margin: 0;
}

body {
  display: flex; 
 background-image: url('<%=request.getContextPath()%>/imagenes/imagenLogin.png');
  height: 100vh;
  overflow: hidden;
  }
 /*  background-size: left;        ocupa todo el fondo */
  /*background-position: center;    centrada */

/*     display: flex; */
/*   /*justify-content: center;*/ 
/*   align-items: center; */

 
 


 .contenedor-formulario { 
 flex: 1;
   background:#fff;
    display: flex; 
     align-items: center;   
    justify-content: center; 
    padding: 100px 150px;          
 box-shadow: 0 8px 25px rgba(0, 0, 0, 0.3); 
  padding: 0 300px;
      height: 100vh;
    
 } 

h2 {
  text-align: center;
  color: #34495e;
  margin-bottom:10px;
    font-size: 40px;
    padding:  50px;
}

.grupo-entrada {
  margin-bottom: 30px;
}

.grupo-entrada label {
  display: block;
  font-size: 20px;
  color: #555;
  margin-bottom: 6px;
  padding:50px;
}

.grupo-entrada input {
margin-top: 40px;
  width: 100%;
  max-width: 400px;
  border: none; 
  border-bottom: 2px solid #ccc; 
   background: transparent; */
   padding: 10px 0; 
  font-size: 25px; 
   outline: none; 
   transition: border-color 0.3s ease; 
 } 

.grupo-entrada input:focus {
  border-bottom: 2px solid #34495e;
}

.enlace {
  display: block;
  text-align: center;
  margin-top: 20px;
  font-size: 20px;
  color: #34495e;
  text-decoration: none;
}

.enlace:hover {
  text-decoration: underline;
}



.btn {
margin-top: 40px;
  width: 95%;
  background-color: #34495e; /* color azul */
  color: #f5f5f5; /* letras blancas */
  padding:10px;
  border-radius: 30px;
  font-size: 30px;
  font-weight: 600;
  cursor: pointer;
  border: none;
  transition: background-color 0.3s ease;
}

.btn:hover {
  background-color: #2e4155; /* más oscuro al pasar el mouse */
}


.col-illu {

  max-width: 350px;
  width: 100%;
}
.col-illu img {

  width:200%;
  height: auto;
}
body, html {
  overflow-x: hidden;
}
</style>
</head>
<body>

<div style="display: flex; align-items: center;">
  <div class="contenedor-formulario">
	<form class="form" action="/TPINT_GRUPO_15_LAB4/ServletLogin" method="post">
	
	     <h2>Iniciar sesión</h2><br>
	     
	  <div class="grupo-entrada">
	  	     
	        <input type="text" id="usuario" name="usuario" placeholder="Usuario" required>
	   	          </div>
	          
	        <div class="grupo-entrada">
	      
	        <input type="password" id="contrasenia" name="contrasenia"   placeholder="Contraseña" required >
	           </div>
	           
			 <button type="submit" class="btn" name="btnIngresar" value="Ingresar" >Iniciar sesión</button>
	            <%-- Mostrar mensaje si existe --%>
    <% if (request.getAttribute("mensaje") != null) { %>
        <div style="background-color:  #f8d7da; color: #721c24; padding:10px; border: 1px solid #f5c6cb; border-radius: 5px; margin-top: 30px;text-align: center;">
            <%= request.getAttribute("mensaje") %>
        </div>
    <% } %>
	    </form>
	
	    
</div>

    
   
</body>
</html>