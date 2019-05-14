<%-- 
    Document   : nuevoLocal
    Created on : 13/05/2019, 01:35:12 AM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
<style>
body,
body * {
	margin:0;
	font-family:"Helvetica Neue", Helvetica, Arial, sans-serif;
}

.buscador {
	text-align:center;
	padding:30px 0px;
}

.buscador #direccion {
	margin:10px auto;
	width:100%;
	padding:7px;
	max-width:250px;
}

.buscador #buscar {
	margin:0 auto;
	max-width:250px;
	padding:7px;
	color:#FFFFFF;
	background:#f2777a;
	border:2px solid #f2777a;
	cursor:pointer;
}
</style>        

    </head>
    <body>

<div class="buscador">
	<h2>Ingrese una dirección</h2>
    <input type="text" id="direccion">
    <div id="buscar">Buscar</div>
</div>

<div id="mapa-geocoder" class="mapa" style="width:100%;display:block;position:absolute;background:#f2777a;"></div>

<script src="js/geocoder.js" type="text/javascript"></script>
<script src="js/nuevoLocal.js" type="text/javascript"></script>
    <script>

</script>
     
    </body>

</html>
