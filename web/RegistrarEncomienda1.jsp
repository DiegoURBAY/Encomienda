<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%
HttpSession sesion = request.getSession();
    if(session.getAttribute("idUsuario")==null){             
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Encomienda</title>
        <jsp:include page="navbarCliente.jsp"/>
        <script type='text/javascript' src='https://maps.googleapis.com/maps/api/js?key=AIzaSyC4Pta07pYlzbICVniGLYta4MLCrUrXrHE&sensor=false&libraries=geometry&v=3.4'></script>
        <script type="text/javascript" src="http://code.jquery.com/jquery-2.0.3.min.js" ></script>        
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>        
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        
        <!--DATAPICKER Y AUTOCOMPLETAR -->       
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        
        <!--BARRA DE NAVEGACIÓN -->   
        
        <!--DATAPICKER -->
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>  
        <!-- VALIDACIONES -->
        <script src="js/validarRegistrarEncomienda.js" type="text/javascript"></script>       
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>    
        <style>
                #map-canvas{
    width: 600px;
    height: 500px;
    float:left;
    background: green;
    }
        </style>
    </head>
    <body>

    <div class="container" id="advanced-search-form">
        <h2>Registrar Encomienda</h2>
        <form method="POST" action="SERVEncomienda" autocomplete="off">
   
            <input type="hidden" id="cliente2" name="txtIdCliente2" value="<%= sesion.getAttribute("idUsuario") %>"  >
            <input type="hidden" id="cliente" name="txtIdCliente" value="<%= request.getParameter("idUsuario") %>"  >
            <input type="hidden" id="cliente3" name="txtIdCliente3" value="<%= sesion.getAttribute("usuario_de_login") %>"  >
            <div class="row">
                <div class="col-sm-12">
                    <div class="col-sm-6 form-group">
                        <label for="origen">Origen</label>
                        <input type="text" class="form-control" placeholder="Origen" id="origen" name="txtOrigen">
                    </div>
                    <div class="col-sm-6 form-group">
                        <label for="destino">Destino</label>
                        <input type="text" class="form-control" placeholder="Destino" id="destino" name="txtDestino">
                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="col-xs-12">
                        <input id="origen1" type="hidden" class="form-control">
                        <label for="origen2">Dirección de Origen</label>
                        <input id="origen2" type="text" readonly=""  class="form-control" placeholder="Dirección de Origen" >
                    </div>                    
                </div>
                <br>
                <div class="col-sm-12">
                    <div class="col-xs-12">                        
                        <input id="desA" type="hidden" class="form-control" >
                        <label for="desB">Dirección de Destino</label>
                        <input id="desB" type="text" readonly="" class="form-control" placeholder="Dirección de Destino" >
                    </div>                    
                </div>       
               
                <div class="col-sm-12"> <br>
                    <div class="col-xs-3 ">
                        <label for="tiempo">Tiempo</label>
                        <input type="text" class="form-control" readonly="" placeholder="Tiempo" id="tiempo" name="txtTiempo">
                        <input type="text" class="form-control" id="tiempoConvertido" name="txtTiempoConvertido">
                    </div>
                    <div class="col-xs-3 ">
                        <label for="recorrido">Kilometros</label>
                        <input type="text" class="form-control" readonly="" placeholder="Recorrido" id="recorrido" name="txtRecorrido">
                    </div>          
                    <div class="col-xs-3">
                         <label for="calcularTR"></label>
                        <input type="button" class="btn-primary form-control" id="calcularTR" name="txtCalcularTR" value="Calcular">
                    </div>                      
                </div>
                <br>
            </div>
            <hr class="my-4" >
            <div class="row">
                <div class="col-sm-12">
                  <div id="div-resultado"></div>
                  <div id="map-canvas"></div>
                </div>
            </div>

            <hr class="my-4" >
            <div class="row">
                <div class="col-sm-12">
                    
                    <div class="row">
                        <div class="col-xs-4">
                            <label >¿Qué desea registar?</label>
                        </div>                        
                        <div class="col-xs-4">
                            <label class="radio-inline">
                                <input checked="checked" name="pago1" type="radio" value="sobre"/>
                                    <span class="auto-style4"> 
                                        Sobre
                                    </span>
                            </label>           
                            <label class="radio-inline">
                               <input name="pago1" type="radio" value="paquete"/>
                                    <span class="auto-style4"> 
                                        Paquete
                                    </span>
                            </label>
                        </div>
                        <div class="checkbox-inline col-xs-4">
                            <label><input type="checkbox" name="txtDelicado" value="1">Delicado</label>
                        </div>                        
                    </div>                    
                </div>   
            </div>                                   
                  
           <!-- SOBRE-->
            <hr class="my-4">
            <div class="row" id="div1" style="display:;">
                <div class="col-sm-12">
                    <div class="row">
                   
                        <div class="col-sm-12 form-group">
                            <h4>Costo x Sobre S/. 10.00</h4>
                        </div>                        
                    </div>
                    <div class="row">
                                              
                        <div class="col-sm-4 form-group">
                                <label>Cantidad (min:1, max: 20)</label>
                                <input type="text" class="form-control" name="txtCantidadSobre" id="cantidadSobres" >
                        </div>

                        <div class="col-sm-4 form-group">
                                <label>Peso(kg)(min:0.01, max:1.13)</label>
                                <input type="text" class="form-control "  name="txtPesoSobre" id="pesoSobre">                                        
                        </div>

                        <div class="col-sm-4 form-group">
                                <label>Precio S/.</label>
                                <input type="number"  class="form-control" readonly name="txtPrecioSobre"  id="precioSobre"> 
                        </div>
                    </div>                                       
            </div>

            </div>   
                        
            <!-- PAQUETE-->            
            <div class="row" id="div2" style="display:none;"  >
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-xs-6">
                            <h5>CostoxKilo: S/. 0.20</h5>
                        </div>
                        <div class="col-xs-6">
                            <h5>Costo min: S/. 0.10</h5>
                        </div>                        
                    </div>                     
                    <div class="row">
                        <div class="col-xs-6">
                             <h5>Precio=(peso*cantidad*costoxkilo)</h5>
                        </div>                      
                        <div class="col-xs-6">
                            <h5>PesoVolumen=(alto*ancho*largo)/300</h5>
                        </div>                        
                    </div>                            
                    <div class="row">                                                
                        <div class="col-xs-3">
                                <label>Altura cm [10 - 25]</label>
                                <input type="text" class="form-control" name="txtAltura" id="altura"  value="0"> 
                        </div>
                        <div class="col-xs-3">
                                <label>Anchura cm [5 - 31]</label>
                                <input type="text"  class="form-control" name="txtAnchura" id="anchura"  value="0"> 
                        </div>
                        <div class="col-xs-3">
                                <label>Largo cm [14 - 75]</label>
                                <input type="text"  class="form-control" name="txtLargo" id="largo"  value="0"> 
                        </div>
                        <div class="col-xs-3">
                            <label>Volumen </label>
                            <input type="text"  class="form-control " readonly="" name="txtVolumen" id="volumen" > 
                        </div>                         
                    </div> 
                    <br>
                    <div class="row">                        
                        <div class="col-xs-3">
                                <label>Peso Volumétrico</label>
                                <input type="text" class="form-control" readonly name="txtPesoVolumen" id="pesoVolumen" >  
                        </div>
                        <div class="col-xs-3">
                                <label>Cantidad [1 - 8]</label>
                                <input type="text" class="form-control" name="txtCantidadPaquete" id="cantidadPaquetes" >                              
                        </div>                        
                        
                        <div class="col-xs-3">
                                <label>Peso(kg) [1.14 - 200]</label>
                                <input type="text"  class="form-control" name="txtPesoPaquete"  id="pesoPaquete" > 
                        </div>
                        <div class="col-xs-3">
                                <label>Precio S/.</label>
                                <input type="text"  class="form-control" readonly name="txtPrecioPaquete" id="precioPaquete">                            
                        </div>
                    </div>    
                    <br>
                    <div class="row">                                                                        
                        <div class="col-sm-8 form-group">
                            <br>
                            <label id="mensaje_error" class="control-label text-danger" style="display: block;">
                                Se usa peso volumétrico en el precio</label>
                        </div>                            
                    </div>
                                            
            </div>                
                
            </div>
            <div class="clearfix"></div>            
            <div class="row">
                
                <div class="col-sm-12">
                    <div class="col-xs-6">
                         <input type="submit" class="btn-primary form-control" id="registrar1" name="btnRegistrarPrueba" value="Registrar" >                
                    </div>              
                    <div class="col-xs-6">
                        <input type="reset" class="btn-danger form-control" id="limpiar" name="btnLimpiar" value="Limpiar">
                    </div>                    
                </div>
            </div>
        </form>   
                                  
    </div>
        
    </body>
</html>
