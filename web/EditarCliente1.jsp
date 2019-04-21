<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="true"%>
<%
HttpSession sesion = request.getSession();
    if(sesion.getAttribute("idCliente")==null){
        response.sendRedirect("indexPrueba.jsp");
    }
    if(sesion.getAttribute("idCliente")!=null){
        int idCliente = (Integer) sesion.getAttribute("idCliente");
        String usuario_de_login = null;
        String usuario_actual = String.valueOf(sesion.getAttribute("usuarioPrueba"));
        if(sesion.getAttribute("usuario_de_login")!=null){
            usuario_de_login = String.valueOf(sesion.getAttribute("usuario_de_login"));
                if(!(usuario_actual.equals(usuario_de_login))){
                   response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=cerrar");                                      
                }       
            } 
    }    
      
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Cliente1</title>       
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,700" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <link href="css/registrarcliente.css" rel="stylesheet" type="text/css"/>
        <script src="js/validarEditarCliente.js" type="text/javascript"></script>

    </head>
    <body>
<div class="signup-form">
    <form action="SERVCliente" method="POST"  autocomplete="off">
         <input type="text" id="cliente2" name="txtIdCliente2" value="<%= sesion.getAttribute("idCliente") %>"  >
         <input type="text" id="cliente3" name="txtIdCliente3" value="<%= sesion.getAttribute("usuarioPrueba") %>"  >
         <input type="text" id="cliente4" name="txtIdCliente4" value="<%= sesion.getAttribute("usuario_de_login") %>"  >
           <input type="text" class="form-control" id="cliente_id" name="txtId" value="<c:out value="${cliente.id}" />" > 
		<h2>Editar</h2>
		<p class="hint-text">Solo le tomará unos segundos</p>
                                                             
        <div class="form-group">
            <div class="col-xs-6">
                <label class="radio-inline"><input type="radio" name="optradio" value="1" > RUC</label>                          
            </div>
            
            <div class="col-xs-6">
                <label class="radio-inline"><input type="radio" name="optradio" value="2" > DNI</label>
            </div>
        </div>                      
                
        <div class="form-group">
        	<input type="text" id="inputIdentificador" class="form-control" placeholder="Identificador" name="txtIdentificador" value="<c:out value="${cliente.identificador}" />">
        </div>                
                
        <div class="form-group">
        	<input type="text" id="inputNombre" class="form-control" placeholder="Nombre" name="txtNombre" value="<c:out value="${cliente.nombre}" />">
        </div>
        <div class="form-group">
            <input type="text" id="inputEmail" class="form-control" placeholder="Correo" name="txtEmail" value="<c:out value="${cliente.email}" />">
        </div>
	<div class="form-group">
            <input type="text" id="inputTelefono" class="form-control" placeholder="Telefono celular" name="txtTelefono" value="<c:out value="${cliente.telefono}" />">
        </div>        
         <hr class="my-4">
        <div class="form-group">
                <div class="row">
                    <div class="col-xs-6">
                        <input type="text" id="inputUserame" class="form-control" placeholder="Usuario" name="txtUsuario" value="<c:out value="${cliente.usuario}" />">
                    </div>
                    <div class="col-xs-6">
                        <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="txtContrase"  value="<c:out value="${cliente.contraseña}" />">
                    </div>
                </div>        	
        </div>                
        <div class="form-group">
        <input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="Editar" name="btnEditarPrueba" id="editar" onclick="return confirm('¿Seguro que desea editar')">
        <a class="btn btn-sm btn-secondary btn-block text-uppercase" href="SERVEncomienda?action=refreshPrueba&idCliente=<c:out value="<%= sesion.getAttribute("idCliente") %>"/>" onclick="return confirm('¿Seguro que desea salir de la edición?')" >Atras</a>
        </div>
                    
                     
    </form>
</div>
    </body>
</html>
