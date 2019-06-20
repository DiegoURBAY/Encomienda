<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%
HttpSession sesion = request.getSession();
    if(session.getAttribute("usuario")==null){             
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Paquete</title>              
        
        <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>        
       
        
        <!--DATAPICKER Y AUTOCOMPLETAR
         <script src="js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        -->       
        
        
        <!--BARRA DE NAVEGACIÓN -->   
        
        <!--DATAPICKER 
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> 
        -->         
    <script data-require="jquery@2.2.4" data-semver="2.2.4" src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>        
        <!-- VALIDACIONES -->
        <script src="js/validarEditarPaquete.js" type="text/javascript"></script>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>     
        
    </head>
    <body>

    <div class="container" id="advanced-search-form">
        <h2>Editar Paquete</h2>
        <form method="POST" action="SERVTipoEncomienda" autocomplete="off">
   
            <input type="hidden" id="cliente3" name="txtIdCliente3" value="<%= sesion.getAttribute("usuario_de_login") %>"  >
            <input type="text" id="idUsuario" name="txtUsuario" value="<%= sesion.getAttribute("usuario") %>"  >
            <input type="hidden" id="idEncomienda" name="txtTipoEncomienda" value="<%= request.getAttribute("idTipoEncomienda") %>"  >
            <div class="row">
                <div class="col-sm-12">
                    <div class="row">
                    <div class="col-xs-4">
                        <label for="origen">Origen</label>
                        <input type="hidden" class="form-control" id="origen" name="txtOrigen" value="<c:out value="${encomienda.origen}" />" > 
                        <input type="text" class="form-control" readonly="" id="origen2" name="txtOrigen2" value="<c:out value="${encomienda.origenS}" />">
                    </div>
                    <div class="col-xs-4">
                        <label for="destino">Destino</label>
                        <input type="hidden" class="form-control" id="destino" name="txtDestino" value="<c:out value="${encomienda.destino}" />" > 
                        <input type="text" class="form-control" readonly="" id="destino2" name="txtDestino2" value="<c:out value="${encomienda.destinoS}" />" > 
                    </div>
                    <div class="checkbox-inline col-xs-4">                                                 
                        <label>      
                        <input type="checkbox" name="txtDelicado" id="status"
                         value="1" ${tipoEncomienda.delicado== 1 ?'checked':''}>
                        Delicado
                        </label>
                    </div>                          
                    </div>
                </div>
            </div>               
                        
            <hr class="my-4" >      
            <div class="row"  >
                <div class="col-sm-12">                
                    <div class="row">                                                
                        <div class="col-xs-3">
                                <label>Altura cm</label>
                                <input type="text" class="form-control" readonly="" name="txtAlturaOriginal" id="alturaOriginal"   value="<c:out value="${tipoEncomienda.altura}" />" >  
                        </div>

                        <div class="col-xs-3">
                                <label>Ancho cm</label>
                                <input type="text"  class="form-control" readonly="" name="txtAnchuraOriginal" id="anchuraOriginal"   value="<c:out value="${tipoEncomienda.anchura}" />" > 
                        </div>

                        <div class="col-xs-3">
                                <label>Largo cm</label>
                                <input type="text"  class="form-control" readonly="" name="txtLargoOriginal" id="largoOriginal"  value="<c:out value="${tipoEncomienda.largo}" />" > 
                        </div>
                        <div class="col-xs-3">
                             
                                <input type="hidden"  class="form-control "  name="txtVolumenOriginal" id="volumenOriginal" value="<c:out value="${volumen}" />" > 
                        </div>                                
                                     
                    </div>   
                    <br>        
                    <div class="row">        
                        <div class="col-xs-3">
                                <label>Peso kg</label>
                                <input type="text" class="form-control" readonly="" name="txtPesoPaqueteOriginal" id="pesoPaqueteOriginal"  value="<c:out value="${tipoEncomienda.peso}" />" > 
                        </div>                          
                        <div class="col-xs-3">
                                <label>Cantidad </label>
                                <input type="text" class="form-control" readonly="" name="txtCantidadPaqueteOriginal" id="cantidadPaquetesOriginal"  value="<c:out value="${tipoEncomienda.cantidad}" />" > 
                        </div>      
                        <div class="col-xs-3">
                                <label>Precio S/.</label>
                                <input type="text"  class="form-control " readonly="" name="txtPrecioPaqueteOriginal" id="precioPaqueteOriginal" value="<c:out value="${tipoEncomienda.precio}" />" > 
                        </div>                           
                    </div>
                </div>                             
            </div>                                                                
             <hr class="my-4" >
            <!-- PAQUETE-->            
            <div class="row"  >
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-xs-3">
                            <h5>CostoxKilo: S/0.20</h5>
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
                    <br>
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
                                <label>Peso Volumen</label>
                                <input type="text" class="form-control" readonly name="txtPesoVolumen" id="pesoVolumen" >  
                        </div>  
                        <div class="col-xs-3">
                                <label>Cantidad [1 - 8]</label>
                                <input type="text" class="form-control" name="txtCantidadPaquete" id="cantidadPaquetes" >                              
                        </div>                        
                        

                        <div class="col-xs-3">
                                <label>Precio S/.</label>
                                <input type="text"  class="form-control" readonly name="txtPrecioPaquete" id="precioPaquete">                            
                        </div>

                    </div>    
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
                <div class="col-xs-6">
                     <input type="submit" class="btn-primary form-control" id="editarPaquete" name="btnEditarPaquete" value="Editar" >                
                </div>              
                <div class="col-xs-6">
                    <input type="reset" class="btn-danger form-control" id="limpiar" name="btnLimpiar" value="Limpiar">
                </div>                                 
            </div>
        </form>   
                                  
    </div>
        
    </body>
</html>
