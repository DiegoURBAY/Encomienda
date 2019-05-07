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
        <title>Editar Sobre</title>
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
        <!-- VALIDACIONES -->
        
        <script src="js/validarEditarSobre2.js" type="text/javascript"></script>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>        
    </head>
    <body>

    <div class="container" id="advanced-search-form">
        <h2>Editar Sobre</h2>
        <form method="POST" action="SERVTipoEncomienda" autocomplete="off">
   
            
            <input type="text" id="idUsuario" name="txtUsuario" value="<%= sesion.getAttribute("usuario") %>"  >
            <input type="text" id="idEncomienda" name="txtTipoEncomienda" value="<%= request.getAttribute("idTipoEncomienda") %>"  >
            <div class="row">
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-xs-4">
                            <label for="origen">Origen</label>
                            <input type="text" class="form-control" placeholder="Origen" readonly="" id="origen" name="txtOrigen" value="<c:out value="${encomienda.origen}" />" > 
                        </div>
                        <div class="col-xs-4">
                            <label for="destino">Destino</label>
                            <input type="text" class="form-control" placeholder="Destino" readonly="" id="destino" name="txtDestino" value="<c:out value="${encomienda.destino}" />" > 
                        </div>
                        <div class="checkbox-inline col-xs-4">                                                 
                            <label for="status">      
                            <input type="checkbox" name="txtDelicado" id="status"
                             value="1" ${tipoEncomienda.delicado== 1 ?'checked':''}>
                            Delicado
                            </label>
                        </div>  
                    </div> 
                </div>
            </div>       
           
           <!-- Data antigua-->
           <hr class="my-4">
                    <div class="row">                                              
                        <div class="col-sm-4 form-group">
                                <label>Cantidad (min:1, max: 20)</label>
                                <input type="text" class="form-control" readonly="" name="txtCantidadSobreOriginal" id="cantidadSobresOriginal" value="<c:out value="${tipoEncomienda.cantidad}" />" > 
                        </div>

                        <div class="col-sm-4 form-group">
                                <label>Peso(kg)(min:0.01, max:1.13)</label>
                                <input type="text" class="form-control" readonly=""  name="txtPesoSobreOriginal" id="pesoSobreOriginal" value="<c:out value="${tipoEncomienda.peso}" />" >                                         
                        </div>

                        <div class="col-sm-4 form-group">
                                <label>Precio S/.</label>
                                <input type="text"  class="form-control" readonly="" name="txtPrecioSobreOriginal"  id="precioSobreOriginal" value="<c:out value="${tipoEncomienda.precio}" />" > 
                        </div>
                    </div>            
           <!-- SOBRE-->
            <hr class="my-4">
            <div class="row" >
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
                                <label>Precio S/.</label>
                                <input type="number"  class="form-control" readonly="" name="txtPrecioSobre"  id="precioSobre"> 
                        </div>
                    </div>                                       
                </div>
            </div>   
                        
            <div class="clearfix"></div>            
            <div class="row">
                <div class="col-xs-6">
                    <input type="submit" class="btn-primary form-control" id="editarSobre" name="btnEditarSobre" value="EditarSobre" >
                </div>              
                <div class="col-xs-6">
                    <input type="reset" class="btn-danger form-control" id="limpiar" name="btnLimpiar" value="Limpiar">
                </div>                    
            </div>
        </form>   
                                  
    </div>
        
    </body>
</html>
