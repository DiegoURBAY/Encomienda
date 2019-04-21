<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listar Pedido</title>
         <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
       
        <link href="css/stylesTE.css" rel="stylesheet" type="text/css"/>
      
    </head>
    <body>
<div class="container">
    <h1 class="well">Codigo de Encomienda</h1>
	<div class="col-lg-12 well">
	<div class="row">
            <form method="POST" action="SERVPedido">
                <input type="hidden" name="txtidEncomienda"  value="<%= request.getAttribute("idEncomienda") %>">               
                <input type="hidden" name="txtidCliente" value="<%= request.getAttribute("idCliente") %>">
                <input type="hidden" name="txtidVehiculo" value="<%= request.getAttribute("idVehiculo") %>">                
                <input type="hidden" name="txtplacaVehiculo" value="<%= request.getAttribute("placaVehiculo") %>"> 
                <div class="col-sm-12">
                    <div class="row">                                                
                            <div class="col-sm-4 form-group">
                                    <label >Codigo</label>                                    
                                    <input type="text" class="form-control" name="txtCodigo" value="<%= request.getAttribute("ticket") %>" readonly >
                                    <input type="hidden"  name="txtIdcliente" value="<%= request.getAttribute("idCliente") %>">
                            </div>                                           
                    </div> 
                            
                    <div class="row">
                        <div class="col-sm-4 form-group">
                            <input type="submit" class="form-control btn btn-info  btn-responsive" id="registrar" name="btnIngresar" value="Enviar a mi correo">		                
                        </div>  
                        
                         <div class="col-sm-4 form-group">
                            <a class="orm-control btn btn-danger  btn-responsive" href="SERVVehiculo?action=refresh&peso=<c:out value="<%= request.getAttribute("peso") %>"/>"> No enviar y salir</a>
                        </div>     
                       

                    </div>                     
            </div>
            </form> 
        </div>
	</div>
	</div>
    </body>
</html>
