<%-- 
    Document   : PruebaExcel
    Created on : 04/06/2019, 02:39:37 AM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script src="https://www.amcharts.com/lib/3/serial.js"></script>
<script src="https://www.amcharts.com/lib/3/themes/light.js"></script>
<script src="https://www.amcharts.com/lib/3/plugins/export/export.js"></script>        
<script src="js/pruebaReporte.js" type="text/javascript"></script>
<style>

#chartdiv {
    width       : 100%;
    height      : 500px;
    font-size   : 11px;
}     
#chartdiv2{
    width       : 100%;
    height      : 500px;
    font-size   : 11px;
}        
</style>
<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script src="https://www.amcharts.com/lib/3/serial.js"></script>
<script src="https://www.amcharts.com/lib/3/plugins/export/export.js"></script>
<link href="https://www.amcharts.com/lib/3/plugins/export/export.css" rel="stylesheet" />
    </head>
    <body>

<input type="button" value="XLSX" onclick="exportXLSX();" />

<div id="chartdiv"></div>
<br>
<div id="chartdiv2"></div>


    </body>
</html>
