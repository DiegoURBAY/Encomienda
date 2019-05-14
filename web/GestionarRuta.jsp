
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%
HttpSession sesion = request.getSession();
    if(sesion.getAttribute("idUsuario")==null){             
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>demo ruta</title>
        <jsp:include page="navbar_1.jsp"/> 
        <!--
        <script type='text/javascript' src='https://maps.googleapis.com/maps/api/js?key=AIzaSyC4Pta07pYlzbICVniGLYta4MLCrUrXrHE&v=3.exp&signed_in=true&libraries=places'></script>
        -->
        <script type='text/javascript' src='https://maps.googleapis.com/maps/api/js?key=AIzaSyC4Pta07pYlzbICVniGLYta4MLCrUrXrHE&sensor=false&libraries=geometry&v=3.4'></script>
        <script type="text/javascript" src="http://code.jquery.com/jquery-2.0.3.min.js" ></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>    
        <script src="js/validarRuta2.js" type="text/javascript"></script>
        <link href="css/ruta.css" rel="stylesheet" type="text/css"/>     
    </head>

    <body>
        <form id="formulario">
            <div class="container">
 
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="row">                                                     
                                <div class="col-md-3">               
                                        <label >#Encomienda</label>
                                    <input id="idencomienda" type="text" class="form-control" placeholder="Ingrese id " >                                                 
                                </div>
                                <div class="col-md-3">
                                     <label ></label>
                                    <input id="btn-calcular-tiempo" type="button" class="btn btn-default form-control" value="Calcular">   
                                </div>   
                               
                            </div>
                            <br>
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="origen">Origen</label>
                        <input type="text" class="form-control"  readonly="" placeholder="Origen" id="origen" name="txtOrigen">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="destino">Destino</label>
                                <input type="text" class="form-control"  readonly="" placeholder="Destino" id="destino" name="txtDestino">
                         </div>
                                </div>
                                      <br>                  
                            <div class="row">     
                                
                                   <!-- <label for="origen1">txtOrigen1</label>       -->
                                         
                                   <input id="origen1" type="hidden"  class="form-control" name="txtOrigen1" >
             
                                    <label for="origen2">Dirección de Origen</label>      
                                    <input id="origen2" type="text"   readonly="" class="form-control" placeholder="Dirección de Origen" name="txtOrigen2" >                            
                                
                            </div><br>
                            <div class="row">
                                <!-- <label for="desA">txtDesA</label>       -->
                                <input id="desA" type="hidden" class="form-control"  name="txtDesA" >

                                 <label for="desB">Dirección de Destino</label> 
                                <input id="desB" type="text" readonly="" class="form-control" placeholder="Dirección de Destino"  name="txtDesB" >
                                                                   
                            </div><br>
                            <div class="row">
                                <div id="div-resultado" ></div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <label for="tiempo">Tiempo</label> 
                                    <input id="tiempo" type="text" readonly="" class="form-control" placeholder=""  name="txtTiempo" >
                                </div>   
                                <div class="col-md-3">
                                    <label for="tiempo">Distancia</label> 
                                    <input id="distancia" type="text" readonly="" class="form-control" placeholder=""  name="txtDistancia" >
                                </div>
                            </div>
           
                        </div>


                        <div class="col-md-6">
                                

                            <div id="map-canvas"></div>                        

                        </div>     
                    </div>
                    
                </div>
                
            </div>
        </form>

    </body>
</html>
