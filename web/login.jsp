<%-- 
    Document   : login2
    Created on : 08/04/2019, 10:59:23 AM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>

<style type="text/css">
	.login-form {
		width: 340px;
    	margin: 50px auto;
	}
    .login-form form {
    	margin-bottom: 15px;
        background: #f7f7f7;
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        padding: 30px;
    }
    .login-form h2 {
        margin: 0 0 15px;
    }
    .form-control, .btn {
        min-height: 38px;
        border-radius: 2px;
    }
    .btn {        
        font-size: 15px;
        font-weight: bold;
    }
</style>        
    </head>
    <body>
<div class="login-form">
    <form action="SERVLogin" method="post">
        <h2 class="text-center">Log in</h2>       
        <div class="form-group">
            <input type="text" class="form-control" placeholder="correo" name="txtEmail" >
        </div>
        <div class="form-group">
            <input type="password" class="form-control" placeholder="contraseña" name="txtContra" >
        </div>
        <div class="form-group">
            <input type="submit" class="btn btn-primary btn-block" value="ingresar" name="btnIniciar">
        </div>
        <div class="clearfix">
            <label class="pull-left checkbox-inline"><input type="checkbox"> Remember me</label>
            <a href="#" class="pull-right">Forgot Password?</a>
        </div>    
        <div class="clearfix">
<a href="index.jsp" class="btn btn-lg btn-secondary btn-block text-uppercase">Atrás</a>   
        </div> 
    </form>
    <p class="text-center"><a href="#">Create an Account</a></p>
</div>
                <%
        HttpSession sesion = request.getSession();
        int nivel = 0;
        if(request.getAttribute("nivel")!=null){
            nivel = (Integer)request.getAttribute("nivel");
            if(!(nivel == 0)){
                sesion.setAttribute("usuario", request.getAttribute("usuario"));
                sesion.setAttribute("nivel", nivel);
                response.sendRedirect("ListarEncomienda.jsp");

            }
        }
        if(request.getParameter("cerrar")!=null){
            session.invalidate();
        }
    %>  
    </body>
</html>
