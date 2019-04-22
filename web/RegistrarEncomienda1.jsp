<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%
HttpSession sesion = request.getSession();
    if(session.getAttribute("idCliente")==null){             
        response.sendRedirect("indexPrueba.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Encomienda</title>
        <jsp:include page="navbarCliente.jsp"/>
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
    </head>
    <body>

    <div class="container" id="advanced-search-form">
        <h2>Registrar Encomienda</h2>
        <form method="POST" action="SERVEncomienda" autocomplete="off">
   
            <input type="text" id="cliente2" name="txtIdCliente2" value="<%= sesion.getAttribute("idCliente") %>"  >
            <input type="text" id="cliente" name="txtIdCliente" value="<%= request.getAttribute("idCliente") %>"  >
            <input type="text" id="cliente3" name="txtIdCliente3" value="<%= sesion.getAttribute("usuario_de_login") %>"  >
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
            </div>               
                        
            <hr class="my-4" >
            <div class="row">
                <div class="col-sm-12">
                    
                    <div class="row">
                        <div class="col-sm-4 form-group">
                            <label >¿Qué desea registar?</label>
                        </div>                        
                        <div class="col-sm-4 form-group">
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
                    </div>                    
                </div>   
            </div>                                   
                  
           <!-- SOBRE-->
            <hr class="my-4">
            <div class="row" id="div1" style="display:;">
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-sm-3 form-group">
                            <h4>Cantidad máxima: 20</h4>
                        </div>
                        <div class="col-sm-3 form-group">
                            <h4>Peso máximo: 1.13 kg</h4>
                        </div>                      
                        <div class="col-sm-3 form-group">
                            <h4>Costo unitario: S/. 10.00</h4>
                        </div>                        
                    </div>
                    <div class="row">
                      
                        
                            <div class="col-sm-4 form-group">
                                    <label>Cantidad</label>
                                    <input type="text" class="form-control" name="txtCantidadSobre" id="cantidadSobres" >
                            </div>
                        
                            <div class="col-sm-4 form-group">
                                    <label>Peso (kg)</label>
                                    <input type="text" class="form-control "  name="txtPesoSobre" id="pesoSobre">

                                         
                            </div>
                        
                            <div class="col-sm-4 form-group">
                                    <label>Precio</label>
                                    <input type="number"  class="form-control" readonly name="txtPrecioSobre"  id="precioSobre"> 
                            </div>
                    </div>                                       
            </div>

            </div>   
                        
            <!-- PAQUETE-->            
            <div class="row" id="div2" style="display:none;"  >
                        <div class="col-sm-12">
                    <div class="row">
                        <div class="col-sm-3 form-group">
                            <h4>Cantidad máxima: 8</h4>
                        </div>
                        <div class="col-sm-3 form-group">
                            <h4>Peso máximo: 200 kg</h4>
                        </div>                      
                        <div class="col-sm-3 form-group">
                            <h4>Costo unitario: S/. 10.00</h4>
                        </div>                        
                    </div>                            
                    <div class="row">                                                
                            <div class="col-sm-4 form-group">
                                    <label>Altura</label>
                                    <input type="text" class="form-control" name="txtAltura" id="altura"  value="0"> 
                            </div>
                        
                            <div class="col-sm-4 form-group">
                                    <label>Anchura</label>
                                    <input type="text"  class="form-control" name="txtAnchura" id="anchura"  value="0"> 
                            </div>
                        
                            <div class="col-sm-4 form-group">
                                    <label>Largo</label>
                                    <input type="text"  class="form-control" name="txtLargo" id="largo"  value="0"> 
                            </div>
                    </div> 
                    <div class="row">                                                
                            <div class="col-sm-4 form-group">
                                    <label>Cantidad</label>
                                    <input type="text" class="form-control" name="txtCantidadPaquete" id="cantidadPaquetes" >
                            </div>
                        
                            <div class="col-sm-4 form-group">
                                    <label>Peso (kg)</label>
                                    <input type="text"  class="form-control" name="txtPesoPaquete"  id="pesoPaquete" > 
                            </div>
                        
                            <div class="col-sm-4 form-group">
                                    <label>Precio</label>
                                    <input type="number"  class="form-control" readonly name="txtPrecioPaquete" id="precioPaquete">
                            </div>
                    </div>    
                            <div class="row">                                                
                            <div class="col-sm-4 form-group">
                                    <label>Peso Volumétrico</label>
                                    <input type="text" class="form-control" name="txtPesoVolumen" id="pesoVolumen" >
                            </div>
                                </div>
                                            
            </div>                
                
            </div>
            <div class="clearfix"></div>            
            <div class="row">
                
                <div class="col-sm-12">
                    <div class="col-sm-3">
                         <input type="submit" class="btn btn-primary  btn-responsive" id="registrar1" name="btnRegistrarPrueba" value="Registrar" >                
                    </div>
                    <div class="col-sm-3">
                        <input type="button" class="btn btn-warning btn-responsive" id="calcular" name="btnCalcular" value="Calcular">
                    </div>                     
                    <div class="col-sm-3">
                        <input type="reset" class="btn btn-danger btn-responsive" id="limpiar" name="btnLimpiar" value="Limpiar">
                    </div>                    
                </div>
            </div>
        </form>   
                                  
    </div>
        
    </body>
</html>