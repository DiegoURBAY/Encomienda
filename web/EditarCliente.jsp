<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        <meta name="description" content="Colorlib Templates">
        <meta name="author" content="Colorlib">
        <meta name="keywords" content="Colorlib Templates">

        <title>Registro</title>
        
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <link href="css/registro.css" rel="stylesheet" type="text/css"/>

    </head>
<body>
  <div class="container">
    <div class="row">
      <div class="col-lg-10 col-xl-9 mx-auto">
        <div class="card card-signin flex-row my-5">
          <div class="card-img-left d-none d-md-flex">
             <!-- Background image for card set in CSS! -->
          </div>
          <div class="card-body">
            <h5 class="card-title text-center">Editar Cuenta</h5>
            <form class="form-signin" method="POST" action="SERVCliente">
                
                <input type="hidden" class="form-control" id="cliente_id" name="txtId" value="<c:out value="${cliente.id}" />" >  
                
              <div class="form-label-group">
                <input type="text" id="inputIdentificador" class="form-control" placeholder="Identificador" name="txtIdentificador" value="<c:out value="${cliente.identificador}" />">
                <label for="inputIdentificador">Identificador</label>
              </div>
                
              <div class="form-label-group">
                <input type="text" id="inputNombre" class="form-control" placeholder="Nombre" name="txtNombre" value="<c:out value="${cliente.nombre}" />">
                <label for="inputNombre">Nombre</label>
              </div>                
                
              <div class="form-label-group">
                <input type="text" id="inputEmail" class="form-control" placeholder="Correo" name="txtEmail" value="<c:out value="${cliente.email}" />">
                <label for="inputEmail">Correo</label>
              </div>
              
              <div class="form-label-group">
                <input type="text" id="inputTelefono" class="form-control" placeholder="Telefono celular" name="txtTelefono" value="<c:out value="${cliente.telefono}" />">
                <label for="inputTelefono">Teléfono celular</label>
              </div>
                
              <hr>
              
              <div class="form-label-group">
                <input type="text" id="inputUserame" class="form-control" placeholder="Username" name="txtUsuario" value="<c:out value="${cliente.usuario}" />">
                <label for="inputUserame">Nombre de usuario</label>
              </div>

              <div class="form-label-group">
                <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="txtContraseña" value="<c:out value="${cliente.contraseña}" />">
                <label for="inputPassword">Contraseña</label>
              </div>
              
              <div class="form-label-group">
                <input type="password" id="inputConfirmPassword" class="form-control" placeholder="Password" >
                <label for="inputConfirmPassword">Confirmar contraseña</label>
              </div>              
              <input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="Editar" name="btnEditar">
              <hr class="my-4">
    <a class="btn btn-info btn-lg" href="SERVEncomienda?action=refresh&nivel=<c:out value="<%= sesion.getAttribute("nivel") %>"/>">Atras</a>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
