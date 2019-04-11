<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%
HttpSession sesion = request.getSession();
    if(sesion.getAttribute("nivel")==null){
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Tipo Encomienda</title>
         <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <link href="css/stylesTE.css" rel="stylesheet" type="text/css"/>
        <script src="js/validarRegistrarPaquete.js" type="text/javascript"></script>
    </head>
    <body>
<div class="container">
    <input type="hidden" value="<%= sesion.getAttribute("idEncomienda") %>">
    <input type="hidden" value="<%= sesion.getAttribute("nivel") %>">
    <h1 class="well">Registrar Paquete</h1>
	<div class="col-lg-12 well">
	<div class="row">
            <form method="POST" action="SERVTipoEncomienda" autocomplete="off">
                <input type="hidden" name="txtidEncomienda"  value="<%= sesion.getAttribute("idEncomienda") %>">
                <div class="col-sm-12">
                    <div class="row">                                                
                            <div class="col-sm-4 form-group">
                                    <label>Altura</label>
                                    <input type="text" class="form-control" name="txtAltura" id="altura"> 
                            </div>
                        
                            <div class="col-sm-4 form-group">
                                    <label>Anchura</label>
                                    <input type="text"  class="form-control" name="txtAnchura" id="anchura"> 
                            </div>
                        
                            <div class="col-sm-4 form-group">
                                    <label>Largo</label>
                                    <input type="text"  class="form-control" name="txtLargo" id="largo"> 
                            </div>
                    </div> 
                    <div class="row">                                                
                            <div class="col-sm-4 form-group">
                                    <label>Cantidad</label>
                                    <input type="text" class="form-control" name="txtCantidad" id="cantidadSobres">
                            </div>
                        
                            <div class="col-sm-4 form-group">
                                    <label>Peso por Paquete (kg)</label>
                                    <input type="text"  class="form-control" name="txtPeso"  id="peso" > 
                            </div>
                        
                            <div class="col-sm-4 form-group">
                                    <label>Precio</label>
                                    <input type="text"  class="form-control" readonly="" name="txtPrecio" id="precio">
                            </div>
                    </div>                     
                        
                    <div class="row">
                        <div class="col-sm-3 form-group">
                            <input type="submit" class="form-control btn btn-info  btn-responsive" id="registrar" name="btnAceptarPaquete" value="Aceptar">		                
                            
                        </div>
                        
                        <div class="col-sm-3 form-group">
                            <a class="btn btn-primary btn-sm" href="SERVTipoEncomienda?action=refresh2&idEncomienda=<c:out value="<%= sesion.getAttribute("idEncomienda") %>"/>">Regresar</a>
                            
                        </div>                                               
                        
                    </div>                     
            </div>
            </form> 
        </div>
	</div>
	</div>
    </body>
</html>
