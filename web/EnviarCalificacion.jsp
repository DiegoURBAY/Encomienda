
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%
HttpSession sesion = request.getSession();
    if(sesion.getAttribute("usuario")==null){
        response.sendRedirect("EnviarCalificacion.jsp");
    }
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="navbarCliente.jsp"/>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>
        <script src="js/validarCalificacion.js" type="text/javascript"></script>
        
    </head>
    <body >
        <input type="hidden" id="usuario" name="txtUsuario" value="<%= sesion.getAttribute("usuario") %>"  >
        <div class="container" id="advanced-search-form">
                    <div class="row">                                                     
                        <div class="col-md-3">               
                                <label >#Encomienda</label>                                                                        
                        </div>
                        <div class="col-md-3">
                            <input id="idencomienda" type="text" class="form-control" placeholder="Ingrese id " >     
                        </div>   
                        <div class="col-md-3">
                            <input type="button" class="btn-info form-control" value="Buscar" id="buscar" name="btnBuscar">     
                        </div> 
                    </div>
             <br>
            <div class="row" id="div2" style="display:none;"  >
                <!-- <form id="formulario_calificacion" class="form-inline" action="SERVEnvio" method="post"> -->
                <form id="formulario_calificacion" class="form-inline"> 
                    
                    <div class="row">
                        <div class="col-xs-3 ">
                            <label for="encomienda">Encomienda</label>                            
                        </div>     
                        <div class="col-xs-6 ">                            
                            <input type="text" class="form-control" readonly="" id="encomienda" name="txtEncomienda">
                        </div>                           
                    </div>
                    <br>
                    <div class="row">       
                        <div class="col-xs-3 ">
                            <label for="origen">Origen</label>                            
                        </div>     
                        <div class="col-xs-6 ">                            
                            <input type="text" class="form-control" readonly="" id="origen" name="txtOrigen">
                        </div>                           
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-xs-3 ">
                            <label for="destino">Destino</label>
                        </div> 
                        <div class="col-xs-6 ">
                            <input type="text" class="form-control" readonly="" id="destino" name="txtDestino">
                        </div>                            
                    </div> 
                    <br>
                    <div class="row">
                        <div class="col-xs-3 ">
                            <label for="destino">Fecha</label>
                        </div> 
                        <div class="col-xs-6 ">
                            <input type="text" class="form-control" readonly="" id="fecha">
                        </div>                            
                    </div>                     
                    <br>
                    <div class="row">
                        <div class="col-xs-5">
                            <label>1.- ¿El producto se perdió en el local?</label>
                        </div>                        
                        <div class="col-xs-6">
                            <label class="radio-inline">
                                <input name="perdida" type="radio" value="si" />
                                    <span class="auto-style4"> 
                                        Sí
                                    </span>
                            </label>           
                            <label class="radio-inline">
                               <input name="perdida" type="radio" value="no"/>
                                    <span class="auto-style4"> 
                                        No
                                    </span>
                            </label>
                            <label class="radio-inline">
                               <input name="perdida" type="radio" value="nunca"/>
                                    <span class="auto-style4"> 
                                        Nunca llegó
                                    </span>
                            </label>                            
                        </div>                     
                    </div>      
                    <br>
                    <div class="row">
                        <div class="col-xs-5">
                            <label>2.- ¿El envío llegó a tiempo?</label>
                        </div>                        
                        <div class="col-xs-6">
                            <label class="radio-inline">
                                <input name="tiempo" type="radio" value="si"/>
                                    <span class="auto-style4"> 
                                        Sí
                                    </span>
                            </label>           
                            <label class="radio-inline">
                               <input name="tiempo" type="radio" value="no"/>
                                    <span class="auto-style4"> 
                                        No
                                    </span>
                            </label>
                        </div>                     
                    </div>    
                    <br>
                    <div class="row">
                        <div class="col-xs-5">
                            <label>3.- ¿El envío sufrió daños?</label>
                        </div>                        
                        <div class="col-xs-6">
                            <label class="radio-inline">
                                <input name="dao" type="radio" value="si"/>
                                    <span class="auto-style4"> 
                                        Sí
                                    </span>
                            </label>           
                            <label class="radio-inline">
                               <input name="dao" type="radio" value="no"/>
                                    <span class="auto-style4"> 
                                        No
                                    </span>
                            </label>
                        </div>                     
                    </div>                     
                    <br>                    
                    <div class="row">
                        <div class="col-xs-3">
                            <label for="comment">Comentario (opcional)</label>
                        </div>
                         
                        <div class="col-xs-9">                           
                            <textarea class="form-control" rows="6" cols="44" id="comment" name="txtComentario"></textarea>    
                            </div>                                   
                    </div>   
                <br>
                    <div class="row">                                        
                        <div class="col-sm-12">
                            <div class="col-xs-6">
                                <input type="submit" class="btn-primary form-control" name="txtInsertar" id="insertar" value="Enviar">
                            </div>              
                            <div class="col-xs-6">
                                <input type="button" class="btn-danger form-control" id="limpiar" name="btnLimpiar" value="Limpiar">
                            </div>                    
                        </div>                        
                    </div>                      
                </form>  
              
            </div>                                     
        </div>  
    </body>
</html>
