<%-- 
    Document   : RegistrarEncomienda2
    Created on : 08/04/2019, 06:44:47 PM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

<link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">



<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
<script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<style type="text/css">
	body{
		color: #fff;
		background: #63738a;
		font-family: 'Roboto', sans-serif;
	}
    .form-control{
		height: 40px;
		box-shadow: none;
		color: #969fa4;
	}
	.form-control:focus{
		border-color: #5cb85c;
	}
    .form-control, .btn{        
        border-radius: 3px;
    }
	.signup-form{
		width: 400px;
		margin: 0 auto;
		padding: 30px 0;
	}
	.signup-form h2{
		color: #636363;
        margin: 0 0 15px;
		position: relative;
		text-align: center;
    }
	.signup-form h2:before, .signup-form h2:after{
		content: "";
		height: 2px;
		width: 30%;
		background: #d4d4d4;
		position: absolute;
		top: 50%;
		z-index: 2;
	}	
	.signup-form h2:before{
		left: 0;
	}
	.signup-form h2:after{
		right: 0;
	}
    .signup-form .hint-text{
		color: #999;
		margin-bottom: 30px;
		text-align: center;
	}
    .signup-form form{
		color: #999;
		border-radius: 3px;
    	margin-bottom: 15px;
        background: #f2f3f7;
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        padding: 30px;
    }
	.signup-form .form-group{
		margin-bottom: 20px;
	}
	.signup-form input[type="checkbox"]{
		margin-top: 3px;
	}
	.signup-form .btn{        
        font-size: 16px;
        font-weight: bold;		
		min-width: 140px;
        outline: none !important;
    }
	.signup-form .row div:first-child{
		padding-right: 10px;
	}
	.signup-form .row div:last-child{
		padding-left: 10px;
	}    	
    .signup-form a{
		color: #fff;
		text-decoration: underline;
	}
    .signup-form a:hover{
		text-decoration: none;
	}
	.signup-form form a{
		color: #5cb85c;
		text-decoration: none;
	}	
	.signup-form form a:hover{
		text-decoration: underline;
	}  
</style>        
        
    </head>
    <body>
<div class="signup-form">
    <form action="SERVCliente" method="post">
		<h2>Registrar</h2>
		<p class="hint-text">Solo le tomará unos segundos</p>
                
        <div class="form-group">
        	<input type="text" id="inputIdentificador" class="form-control" placeholder="Identificador" name="txtIdentificador" >
        </div>                
                
        <div class="form-group">
        	<input type="text" id="inputNombre" class="form-control" placeholder="Nombre" name="txtNombre" >
        </div>
        <div class="form-group">
            <input type="text" id="inputEmail" class="form-control" placeholder="Correo" name="txtEmail" >
        </div>
	<div class="form-group">
            <input type="text" id="inputTelefono" class="form-control" placeholder="Telefono celular" name="txtTelefono" >
        </div>        
         <hr class="my-4">
        <div class="form-group">
                <div class="row">
                        <div class="col-xs-6">
                            <input type="text" id="inputUserame" class="form-control" placeholder="Usuario" name="txtUsuario" >
                        </div>
                        <div class="col-xs-6">
                            <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="txtContraseña" >
                        </div>
                </div>        	
        </div>                
        <div class="form-group">
        <input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="registrar" name="btnRegistrar">
            <a href="index.jsp?cerrar=true" class="btn btn-sm btn-secondary btn-block text-uppercase">Atrás</a>                 
        </div>
    </form>
</div>
    </body>
</html>
