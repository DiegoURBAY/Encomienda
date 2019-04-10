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
        <title>Registrar Encomienda</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/styles.css" rel="stylesheet" type="text/css"/>
      
    </head>
    <body>
        <input type="hidden" value="<%= sesion.getAttribute("nivel") %>">
    <div class="container" id="advanced-search-form">
        <h2>Registrar Encomienda</h2>
        <form method="POST" action="SERVEncomienda">
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
            <input type="submit" class="btn btn-info btn-lg btn-responsive" id="registrar" name="btnRegistrar">
                                <a class="btn btn-primary btn-lg" href="SERVEncomienda?action=refresh&nivel=<c:out value="<%= sesion.getAttribute("nivel") %>"/>">Regresar</a>   
        </form>
    </div>
    </body>
</html>
