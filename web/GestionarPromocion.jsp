
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="navbar.jsp"/>
     <link href="css/styles.css" rel="stylesheet" type="text/css"/>
        <script src="js/validarPromocion.js" type="text/javascript"></script>
    </head>
    <body>
        <div class="container" id="advanced-search-form">
            <div class="row">
                <div class="col-xs-4">
                    <label >¿Qué promoción desea ver?</label>
                </div>                        
                <div class="col-xs-4">
                    <label class="radio-inline">
                        <input name="pago1" type="radio" value="empresa"/>
                            <span class="auto-style4"> 
                                Empresa
                            </span>
                    </label>           
                    <label class="radio-inline">
                       <input name="pago1" type="radio" value="cliente"/>
                            <span class="auto-style4"> 
                                Cliente
                            </span>
                    </label>
                </div>                     
            </div>          
            <br>
            <div class="row" id="div1" style="display:;">
                <form id="formulario_empresa" class="form-inline">
                    <div class="col-sm-8">
                        <label for="promocionEmpresa" class="col-sm-4">Descuento %</label>
                        <input type="text" class="form-control col-sm-3" name="txtPromocion" id="promocionEmpresa" >
                        <input type="hidden" id="promocionEmpresaOriginal" >
                    </div>  
                    <div class="col-sm-4">
                        <input type="button" class="btn-primary form-control" name="txtEditarEmpresa" id="editarEmpresa" value="Editar">
                    </div>                      
                </form>                     
            </div>

            <div class="row" id="div2" style="display:none;"  >
                <form id="formulario_cliente" class="form-inline">
                    <div class="col-sm-8">
                        <label for="promocionCliente" class="col-sm-4">Descuento %</label>
                        <input type="text" class="form-control col-sm-3" name="txtPromocion" id="promocionCliente" >
                        <input type="hidden" id="promocionClienteOriginal" >
                    </div>  
                    <div class="col-sm-3">
                        <input type="button" class="btn-success form-control" name="txtEditarCliente" id="editarCliente" value="Editar">
                    </div>    
                </form>        
            </div>            
        </div>               
    </body>
</html>
