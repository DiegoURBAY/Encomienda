<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>Editar Encomienda</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/styles.css" rel="stylesheet" type="text/css"/>
      
    </head>
    <body>
    <div class="container" id="advanced-search-form">
        <h2>Advanced Search</h2>
        <form method="POST" action="SERVEncomienda">
            <input type="hidden" id="cliente" name="txtIdCliente" value="<%= sesion.getAttribute("nivel") %>"  >
            <input type="hidden" id="encomienda" name="txtId" value="<c:out value="${encomienda.id}" />" >
            <div class="form-group">
                <label for="origen">Origen</label>
                <input type="text" class="form-control" placeholder="Origen" id="origen" name="txtOrigen" value="<c:out value="${encomienda.origen}" />" >
            </div>
            <div class="form-group">
                <label for="destino">Destino</label>
                <input type="text" class="form-control" placeholder="Destino" id="destino" name="txtDestino" value="<c:out value="${encomienda.destino}" />" >
            </div>
            <div class="form-group">
                <label for="envio">Envio</label>
                <input type="text" class="form-control" placeholder="Envio" id="envio" name="txtEnvio"  value="<c:out value="${encomienda.envio}" />" >
            </div>
            <div class="form-group">
                <label for="llegada">Llegada</label>
                <input type="text" class="form-control" placeholder="Llegada" id="llegada" name="txtLlegada" value="<c:out value="${encomienda.llegada}" />" >
            </div>                        
            <div class="clearfix"></div>            
            <input type="submit" class="btn btn-info btn-lg btn-responsive" id="editar" name="btnEditar">
                                <a class="btn btn-primary btn-lg" href="SERVEncomienda?action=refresh&nivel=<c:out value="<%= sesion.getAttribute("nivel") %>"/>">Regresar</a>               
        </form>
    </div>
    </body>
</html>
