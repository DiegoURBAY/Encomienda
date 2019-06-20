<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>
<%
HttpSession sesion = request.getSession();
    if(sesion.getAttribute("usuario")==null){
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte</title>
        

        <!-- Barra, Pastel -->
        <script src="amcharts/amcharts.js" type="text/javascript"></script>
        <script src="amcharts/serial.js" type="text/javascript"></script>
        <script src="amcharts/pie.js" type="text/javascript"></script>
        <script src="amcharts/themes/light.js" type="text/javascript"></script>

        <script src="amcharts/plugins/export/export.js" type="text/javascript"></script>
        <link href="amcharts/plugins/export/export.css" rel="stylesheet" type="text/css"/>

        <script src="amcharts/plugins/animate/animate.js" type="text/javascript"></script>

                             
        <!--DATAPICKER -->
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>        
        <!--BARRA DE NAVEGACIÓN-->      
        <jsp:include page="navbar.jsp"/>
        <!--DATAPICKER -->
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>  
        <!--VALIDACIÓN -->         
        <script src="js/validarReportarEncomienda.js" type="text/javascript"></script>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>   

    </head>
    <body>

    <div class="container" id="advanced-search-form">
        <h2>Reporte Encomienda</h2>
        <form method="POST" action="SERVReporte" autocomplete="off">
    
            <div class="row" id="eleccion">
                <div class="col-xs-4">
                    <label >¿Qué desea ver?</label>
                </div>                        
                <div class="col-xs-8">
                    <label class="radio-inline">
                        <input name="pago1" type="radio" value="todo" checked=""/>
                            <span class="auto-style4"> 
                                Todas 
                            </span>
                    </label>           
                    <label class="radio-inline">
                       <input name="pago1" type="radio" value="cliente"/>
                            <span class="auto-style4"> 
                                Por cliente
                            </span>
                    </label>
                    <label class="radio-inline">
                       <input name="pago1" type="radio" value="empresa"/>
                            <span class="auto-style4"> 
                                Por Empresa
                            </span>
                    </label>                    
                </div>                     
            </div>          
            <br>            
            <div class="row">
                <div class="col-sm-12"> <br>
                    <div class="col-xs-3 ">
                        <label for="from">Desde</label>
                        <input type="text" id="from" name="txtFechaEnvio" class="form-control mask_date" placeholder="dd/mm/yyyy" autocomplete="off" maxlength="10" />
                    </div>
                    <div class="col-xs-3 ">
                        <label for="to">Hasta</label>
                        <input type="text" id="to" name="txtFechaFinal" class="form-control mask_date" placeholder="dd/mm/yyyy" autocomplete="off" maxlength="10" />
                    </div>      
                    <div class="col-xs-3 " id="div1" style="display:none;">
                        <label for="to">Ingrese DNI</label>
                        <input type="text" placeholder="75214258" id="dni" class=" form-control" >
                        <div id="ReportarIdentificador" class="outputTextArea"></div> 
                    </div>                   
                    <div class="col-xs-3 " id="div3" style="display:none;">
                        <label for="to">Ingrese RUC</label>
                        <input type="text" id="ruc" placeholder="20467534026" class=" form-control" >
                        <div id="ReportarIdentificadorRuc" class="outputTextArea"></div> 
                    </div>                         
                    <div class="col-xs-3">                        
                        <label for="btnGraficoFecha"></label>
                        <input type="button" class="btn-primary form-control" id="btnGraficoFecha"  value="Ver Por Fecha" >                                                        
                    </div>                      
                </div>
                <br>
            </div>
 
            <hr class="my-4" >                         
                    
            <!-- PAQUETE-->            
            <div class="row" id="div2">
                
                <div class="col-sm-12"> <br>

                    <div class="row">            
                        <div class="col-xs-3">
                            <label >¿Cómo desea exportar?</label>
                        </div>                         
                        <div class="col-xs-4 ">                                                      
                            <input type="button" id="btnExportChartsPDF" class="btn-danger form-control" value="PDF">
                        </div>
                        <div class="col-xs-4 ">
                           <input type="button" id="btnExportChartsXSXL" class="btn-success form-control" value="Excel">                          
                        </div>                              
                    </div>
                </div>
                <br>           
                <br>
<br> 
<br> 
<br> 
                <div class="col-sm-12">
                    <div class="row">
                        <div class="box box-primary">
                            <div class="box-body">
                                <h2>Primer Reporte</h2>
                                <div id="chartdiv1" class="chartdiv"></div><br>                                                                                   
                            </div>
                        </div>                       
                    </div>
                    
                    <div class="row"  id="div4">
                        <div class="box box-primary">
                            <div class="box-body">
                                <div id="chartdiv4" class="chartdiv"></div><br>                                                                                   
                            </div>
                        </div>                       
                    </div>                    
                    <div class="row">
                        <h2>Segundo Reporte</h2>
                        <div id="chartdiv2" class="chartdiv"></div>                                                
                    </div>
                </div>
                
            </div>   

        </form>   
                                  
    </div>
        
    </body>
</html>
