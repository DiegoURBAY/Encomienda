<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%
HttpSession sesion = request.getSession();    
    if(sesion.getAttribute("usuario")==null){
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
        <title>Barra de Navegación</title>
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <!-- Versión compilada y comprimida del CSS de Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <!-- Tema opcional -->
        <link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
        <!-- Versión compilada y comprimida del JavaScript de Bootstrap -->
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        
        <!-- Referencias para el DataTable         -->
        <link href="css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>  
        <link href="css/responsive.dataTables.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery.dataTables.min.js" type="text/javascript"></script>   
        <script src="js/dataTables.responsive.min.js" type="text/javascript"></script>
        <script src="js/dataTable.js" type="text/javascript"></script>    
        
      <style id="antiClickjack" type="text/css">body{display:none !important;}</style>
     <script type="text/javascript">
         if (self === top) {
             var antiClickjack = document.getElementById("antiClickjack");
             antiClickjack.parentNode.removeChild(antiClickjack);
         } else {
             top.location = self.location;
         }
     </script>           
        
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
          <!-- El logotipo y el icono que despliega el menú se agrupan
               para mostrarlos mejor en los dispositivos móviles -->
          <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target=".navbar-ex1-collapse">
              <span class="sr-only">Desplegar navegación</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">EncomiendasSac</a>
          </div>

          <!-- Agrupar los enlaces de navegación, los formularios y cualquier
               otro elemento que se pueda ocultar al minimizar la barra -->
          <div class="collapse navbar-collapse navbar-ex1-collapse">

                <ul class="nav navbar-nav">
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Reportes <b class="caret"></b>
                      </a>
                      <ul class="dropdown-menu">                    
                          <li><a href="ReportarEncomienda1.jsp">Encomiendas</a></li>
                      </ul>
                    </li>                  
                </ul>
              
            <ul class="nav navbar-nav navbar-right">

              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  ¡Hola, <%= sesion.getAttribute("usuario") %>!<b class="caret"></b>                  
                </a>
                <ul class="dropdown-menu">                    
                    <li><a href="SERVCliente2?action=edit" >¿Editar cuenta? </a></li> 
                    <li><a href="SERVEncomienda2?action=insert">¿Nueva Encomienda? </a></li>
                    <li><a href="SERVEncomienda2?action=refresh" >¿Buscar encomienda? </a></li> 
                    <li><a href="SERVEnvio?action=sent" >¿Calificar Encomienda? </a></li> 
                    <li><a href="SERVCliente2?action=cerrar">Cerrar Sesión</a></li> 
                </ul>
              </li>              
            </ul>
          </div>
        </nav>
    </body>
</html>
