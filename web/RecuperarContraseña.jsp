
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>

<%
HttpSession sesion = request.getSession();
    if(sesion.getAttribute("usuario")==null){
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recuperar</title>
        <link href="css/recuperarcontra.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
<div class="login-form">
    <form  method="POST" action="SERVLogin" autocomplete="off" >
        <h2 class="text-center">Recuperar Contraseña</h2>       
        <div class="form-group">
            <input type="text" class="form-control" placeholder="correo" name="txtEmail" >
        </div>
        <div class="form-group">
            <input type="submit" class="btn btn-primary btn-block" value="Recuperar" name="btnRecuperar">
        </div>
  
        <div class="clearfix">
            <a href="index.jsp" class="btn btn-lg btn-secondary btn-block text-uppercase">Atrás</a>   
        </div> 
    </form>

</div>
 
    </body>
</html>
