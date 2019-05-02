<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <!-- VALIDACIONES 
         <script src="js/validarEditarEncomienda.js" type="text/javascript"></script>
        -->     
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>        
      
    </head>
    <body>
    <div class="container" id="advanced-search-form">
        <h2>Buscar Encomienda   </h2>
        <form method="POST" action="SERVEncomienda">            
            <div class="form-group">
                <label for="origen">Codigo</label>
                <input type="text" class="form-control" placeholder="Codigo" id="origen" name="txtCodigo" >
            </div>
            <div class="clearfix"></div>            
            <input type="submit" class="btn btn-info btn-lg btn-responsive" id="buscar" name="btnBuscar" value="Buscar" onclick="return confirm('¿Seguro que desea editar el registro?')">            
        </form>
    </div>
    </body>
</html>
