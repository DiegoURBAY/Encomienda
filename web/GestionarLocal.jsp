
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Demo Local</title>
        <jsp:include page="navbar_1.jsp"/> 
        <script type='text/javascript' src='https://maps.googleapis.com/maps/api/js?key=AIzaSyC4Pta07pYlzbICVniGLYta4MLCrUrXrHE'></script>
        <script type="text/javascript" src="http://code.jquery.com/jquery-2.0.3.min.js" ></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <link href="css/ubicacion.css" rel="stylesheet" type="text/css"/>
        <script src="js/validarLocal2.js" type="text/javascript"></script>
       
    </head>
    <body  style="background-color: #BDBDBD">
        <br><br>
        <div class="container" >
            
<!--Section: Contact v.1-->
<section class="section pb-5"  >

  <div class="row" style="background: white;  padding: 10px" >

    <!--Grid column-->
    <div class="col-lg-5 mb-4" style="border-style: solid;" >

      <!--Form with header-->
      <div class="card" >
          
        <div class="accordion-group">
            <div class="accordion-heading">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">
                    <h3> Agregar</h3>
                </a>
            </div>
            <div id="collapseOne" class="accordion-body collapse in">
               <div class="accordion-inner">
                    <form id="formulario">                          
                        <table class="table">
                            <tr>
                                <td>Ingrese una dirección</td>
                                <td><input type="button" class="form-control btn-info" id="buscar" value="Buscar"></td>
                            </tr>
                            <tr >
                                <td colspan=2 ><input type="text" class="form-control" id="direccion_buscar"></td>                                
                            </tr>
                            <tr>
                                <td>Título</td>
                                <td><input type="text" class="form-control"  name="titulo" id="titulo_insertar" autocomplete="off"/></td>
                            </tr>                           
                            <tr>
                                <td>Coordenada X</td>
                                <td><input type="text" class="form-control" readonly  name="cx" autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td>Coordenada Y</td>
                                <td><input type="text" class="form-control"  readonly name="cy" autocomplete="off"/></td>
                            </tr>
                            <tr>
                                <td>Departamento</td>
                                <td><input type="text" class="form-control"  readonly name="departamento" autocomplete="off"/></td>
                            </tr>                            
                            <tr>
                                <td>Dirección</td>
                                <td><input type="text" class="form-control" readonly  name="direc" autocomplete="off"/></td>
                            </tr> 
                            <tr>
                                <td>Teléfono</td>
                                <td><input type="text" class="form-control"  name="tel" autocomplete="off"/></td>
                            </tr>  
                            <!-- Aqui estar� se colocaran los mensajes para el usuario -->
                            <tr>
                                <td></td>
                                <td><span id="loader_grabar" class=""></span></td>
                            </tr>
                        </table>  
                        <div class="row">
                            <div class="col-xs-6">
                                <input type="button" id="btn_grabar" class="btn-success form-control" value="Grabar">
                            </div>  
                            <div class="col-xs-6">
                                <input type="reset" class="btn-danger form-control" value="Limpiar">
                            </div>   
                        </div>                  
                   </form>
               </div>
            </div>
        </div>    
          
    <hr class="my-4" >      
                    <div class="accordion-group">
                      <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
                        <h3> Editar / Eliminar</h3>
                        </a>
                      </div>
                      <div id="collapseTwo" class="accordion-body collapse">
                        <div class="accordion-inner">
                          <form id="formulario_eliminar">
                              <input type="hidden"  class="form-control"  name="id"/>
                                <table class="table">
                                    <tr>
                                        <td>Título</td>
                                        <td><input type="text" class="form-control"  name="titulo" id="titulo_edi_eli" autocomplete="off"/></td>
                                    </tr>
                                    <tr>
                                        <td>Coordenada X</td>
                                        <td><input type="text" class="form-control" readonly  name="cx" autocomplete="off"/></td>
                                    </tr>
                                    <tr>
                                        <td>Coordenada Y</td>
                                        <td><input type="text" class="form-control"  readonly name="cy" autocomplete="off"/></td>
                                    </tr>
                                    <tr>
                                        <td>Departamento</td>
                                        <td><input type="text" class="form-control"  readonly name="departamento" autocomplete="off"/></td>
                                    </tr>                                      
                                    <tr>
                                        <td>Direccion</td>
                                        <td><input type="text" class="form-control" readonly  name="direc" autocomplete="off"/></td>
                                    </tr> 
                                    <tr>
                                        <td>Teléfono</td>
                                        <td><input type="text" class="form-control"  name="tel" autocomplete="off"/></td>
                                    </tr>                                     
                                    <!-- Aqui estar� se colocaran los mensajes para el usuario -->
                                    <tr>
                                        <td></td>
                                        <td><span id="loader_grabar" class=""></span></td>
                                    </tr>                                 
                                </table>
                              <div class="row">
                                  <div class="col-xs-6">
                                        <label>
                                            <input id="opc_edicion" type="checkbox"> Habilitar Edición
                                        </label>
                                  </div>                                  
                              </div>
                              <br>
                                <div class="row">
                                    <div class="col-xs-6">
                                        <input type="button" id="btn_actualizar" class="btn-success form-control" value="Actualizar">
                                    </div>  
                                    <div class="col-xs-6">
                                        <input type="button" id="btn_borrar" class="btn-danger form-control" value="Borrar">
                                    </div>   
                                </div>                                  
                            </form>
                        </div>
                      </div>
                    </div>

                    <hr class="my-4" >
                    <div class="accordion-group">
                      <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseThree">
                          <h3> Buscar</h3>
                        </a>
                      </div>
                      <div id="collapseThree" class="accordion-body collapse">
                        <div class="accordion-inner">
                          <form id="formulario_buscar">
                              <table class="table">
                              <TR>
                                <td>
                                  <input type="text" id="palabra_buscar"  class="form-control" autocomplete="off" />
                                </td>
                                <td>
                                  <button type="button" id="btn_buscar"  class="btn-success form-control" >Buscar</button>
                                </td>
                              </TR>

                              <TR>
                                <td>
                                    <select id="select_resultados" class="form-control">
                                    <option value="uno">Busque</option>
                                  </select>
                                </td>
                                <td></td>
                              </TR>

                            </table>
                          </form>
                        </div>
                      </div>
                    </div>          
          
      </div>
      <!--Form with header-->

    </div>
    <!--Grid column-->

    <!--Grid column-->
    <div class="col-lg-7">

      <!--Google map
      <div id="map-container-google-11" class="z-depth-1-half map-container-6" style="height: 400px">
        <iframe src="https://maps.google.com/maps?q=new%20delphi&t=&z=13&ie=UTF8&iwloc=&output=embed"
          frameborder="0" style="border:0" allowfullscreen></iframe>
      </div>     
      -->
      <div id="mapa" class="form-control" >
            <h2>Aquí ira el mapa!</h2>
        </div>      

      <br>
      <!--Buttons
      
            <div class="row text-center">
        <div class="col-md-4">
          <a class="btn-floating blue accent-1"><i class="fas fa-map-marker-alt"></i></a>
          <p>San Francisco, CA 94126</p>
          <p>United States</p>
        </div>

        <div class="col-md-4">
          <a class="btn-floating blue accent-1"><i class="fas fa-phone"></i></a>
          <p>+ 01 234 567 89</p>
          <p>Mon - Fri, 8:00-22:00</p>
        </div>

        <div class="col-md-4">
          <a class="btn-floating blue accent-1"><i class="fas fa-envelope"></i></a>
          <p>info@gmail.com</p>
          <p>sale@gmail.com</p>
        </div>
      </div>
      -->


    </div>
    <!--Grid column-->

  </div>

</section>
<!--Section: Contact v.1-->
            
        </div>
   
    </body>
</html>
