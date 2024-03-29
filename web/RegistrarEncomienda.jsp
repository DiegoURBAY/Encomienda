<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%
HttpSession sesion = request.getSession();
    if(sesion.getAttribute("usuario")==null){
        response.sendRedirect("ReportarEncomienda.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Encomienda</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>        
    
        <!--DATAPICKER Y AUTOCOMPLETAR -->       
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        
        <!--BARRA DE NAVEGACIÓN -->   
        
        <!--DATAPICKER -->
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>  
        <!-- VALIDACIONES -->
        <script src="js/validarRegistrarEncomienda.js" type="text/javascript"></script>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>
        
    </head>
    <body>
        <input type="hidden" value="<%= sesion.getAttribute("nivel") %>">
    <div class="container" id="advanced-search-form">
        <h2>Registrar Encomienda</h2>
        <form method="POST" action="SERVEncomienda2" autocomplete="off">
            <input type="hidden" id="cliente" name="txtIdCliente" value="<%= sesion.getAttribute("nivel") %>"  >
            <div class="form-group">
                <label for="origen">Origen</label>
                <input type="text" class="form-control" placeholder="Origen" id="origen" name="txtOrigen">
            </div>
            <div class="form-group">
                <label for="destino">Destino</label>
                <input type="text" class="form-control" placeholder="Destino" id="destino" name="txtDestino">
            </div>
            <div class="form-group">
                <label for="envio">Envio</label>
                <input type="text" class="form-control" placeholder="Envio" id="envio" name="txtEnvio">
            </div>
            <div class="form-group">
                <label for="llegada">Llegada</label>
                <input type="text" class="form-control" placeholder="Llegada" id="llegada" name="txtLlegada">
            </div>                        
            <div class="clearfix"></div>            
            <input type="submit" class="btn btn-info btn-lg btn-responsive" id="registrar" name="btnRegistrar" value="Registrar" onclick="return confirm('¿Seguro que desea registrar?')">
                <a class="btn btn-primary btn-lg" href="SERVEncomienda2?action=refresh&nivel=<c:out value="<%= sesion.getAttribute("nivel") %>"/>" onclick="return confirm('¿Seguro que desea salir del registro?')">Regresar</a>   
        </form>
    </div>
    </body>
</html>
