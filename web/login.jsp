
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.bundle.min.js" type="text/javascript"></script>
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <link href="css/all.css" rel="stylesheet" type="text/css"/>
    </head>

<body>
  <div class="container">
    <div class="row">
      <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card card-signin my-5">
          <div class="card-body">
            <h5 class="card-title text-center">Iniciar Sesión</h5>
            <form class="form-signin" method="POST" action="SERVLogin">
              <div class="form-label-group">
                  <input type="email" id="inputEmail" class="form-control" placeholder="correo" name="txtEmail" >
                <label for="inputEmail">Correo</label>
              </div>

              <div class="form-label-group">
                <input type="password" id="inputPassword" class="form-control" placeholder="contraseña" name="txtContra" >
                <label for="inputPassword">Contraseña</label>
              </div>

              <div class="custom-control custom-checkbox mb-3">
                <input type="checkbox" class="custom-control-input" id="customCheck1">
                <label class="custom-control-label" for="customCheck1">Recordar contraseña</label>
              </div>
                <input class="btn btn-lg btn-primary btn-block text-uppercase" type="submit" value="ingresar" name="btnIniciar">
              <hr class="my-4">
              <a class="d-block text-center mt-2 small" href="RegistrarCliente.jsp">¿Crear nueva cuenta?</a>
                <br>
                <a href="index.jsp" class="btn btn-lg btn-secondary btn-block text-uppercase">Atrás</a>   
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
                sesion.setAttribute("email", request.getAttribute("email"));
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
