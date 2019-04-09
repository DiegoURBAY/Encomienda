<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
        <input type="text" value="<%= sesion.getAttribute("nivel") %>">
    <div class="container" id="advanced-search-form">
        <h2>Advanced Search</h2>
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
        </form>
    </div>
    </body>
</html>
