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
    
        <!--DATAPICKER Y AUTOCOMPLETAR -->       
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        
        <!--BARRA DE NAVEGACIÓN -->   
        
        <!--DATAPICKER -->
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>  
        <!-- VALIDACIONES -->
        <script src="js/validarEditarEncomienda.js" type="text/javascript"></script>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>        
      
    </head>
    <body>
    <div class="container" id="advanced-search-form">
        <h2>Editar Encomienda <input type="text" readonly="" value="<c:out value="${encomienda.id}" />" class="sinbordefondo"  ></h2>
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
            <div class="clearfix"></div>            
            <input type="submit" class="btn btn-info btn-lg btn-responsive" id="editar" name="btnEditar" value="Editar" onclick="return confirm('¿Seguro que desea editar el registro?')">
            <a class="btn btn-primary btn-lg" href="SERVEncomienda?action=refresh&nivel=<c:out value="<%= sesion.getAttribute("nivel") %>"/>" onclick="return confirm('¿Seguro que desea salir de la edición?')">Regresar</a>               
        </form>
    </div>
    </body>
</html>
