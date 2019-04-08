<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>

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
            <h5 class="card-title text-center">Crear Cuenta</h5>
            <form class="form-signin" method="POST" action="SERVCliente">
                
              <div class="form-label-group">
                <input type="text" id="inputIdentificador" class="form-control" placeholder="Identificador" name="txtIdentificador" >
                <label for="inputIdentificador">Identificador</label>
              </div>
                
              <div class="form-label-group">
                <input type="text" id="inputNombre" class="form-control" placeholder="Nombre" name="txtNombre" >
                <label for="inputNombre">Nombre</label>
              </div>                
                
              <div class="form-label-group">
                <input type="text" id="inputEmail" class="form-control" placeholder="Correo" name="txtEmail" >
                <label for="inputEmail">Correo</label>
              </div>
              
              <div class="form-label-group">
                <input type="text" id="inputTelefono" class="form-control" placeholder="Telefono celular" name="txtTelefono" >
                <label for="inputTelefono">Teléfono celular</label>
              </div>
                
              <hr>
              
              <div class="form-label-group">
                <input type="text" id="inputUserame" class="form-control" placeholder="Username" name="txtUsuario" >
                <label for="inputUserame">Nombre de usuario</label>
              </div>

              <div class="form-label-group">
                <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="txtContraseña" >
                <label for="inputPassword">Contraseña</label>
              </div>
              
              <div class="form-label-group">
                <input type="password" id="inputConfirmPassword" class="form-control" placeholder="Password" >
                <label for="inputConfirmPassword">Confirmar contraseña</label>
              </div>              
              <input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="registrar" name="btnRegistrar">
              <hr class="my-4">
              <a href="SERVLogin?action=adios" class="btn btn-lg btn-secondary btn-block text-uppercase">Atrás</a>                 
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
    
        <%
        HttpSession sesion = request.getSession();
        int nivel = 0;
        if(request.getAttribute("nivel")!=null){
            nivel = (Integer)request.getAttribute("nivel");
            if(!(nivel == 0)){
                sesion.setAttribute("usuario", request.getAttribute("usuario"));
                sesion.setAttribute("nivel", nivel);
                response.sendRedirect("navegador.jsp");
            }
        }
        if(request.getParameter("cerrar")!=null){
            session.invalidate();
        }
    %>      
    
</body>
</html>
