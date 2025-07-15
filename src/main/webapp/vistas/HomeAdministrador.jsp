<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Administrador</title>
    
<style>

body, html {
  margin: 0;
  height: 100vh;
  overflow: hidden;
}
.contenedor-principal {
  display: flex;
  height: 100vh;
   height: 100vh;
  margin: 0;
  padding: 0;
}

.lado-izquierdo {
  flex: 1;
  background-image: url('<%=request.getContextPath()%>/imagenes/principal.jpg');
  background-size: contain;
  background-repeat: no-repeat;
  background-position: top left;
  background-color: #fff;
    margin: 0;
  padding: 0;
}

.lado-derecho {

  flex: 1;
  background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: column;
   padding-top: 20vh;  
}

.lado-derecho h2 {
  font-size: 50px;
  color: #34495e;
   
}

        
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/MasterAdmin.jsp" /> 

        <div class="contenedor-principal">
    <div class="lado-izquierdo"></div>
    <div class="lado-derecho">
      <h2>Bienvenido Administrador</h2>
    </div>
  </div>
    

</body>
</html>
