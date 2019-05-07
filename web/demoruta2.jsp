
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>demo ruta</title>
        <script type='text/javascript' src='https://maps.googleapis.com/maps/api/js?key=AIzaSyC4Pta07pYlzbICVniGLYta4MLCrUrXrHE&v=3.exp&signed_in=true&libraries=places'></script>
        <script type="text/javascript" src="http://code.jquery.com/jquery-2.0.3.min.js" ></script>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>    
        <script src="js/validarRuta2.js" type="text/javascript"></script>
    <style>

	
#map-canvas{
height:600px;
}

    </style>        
    </head>

    <body>
        <input id="origen1" type="text" readonly="" class="form-control" placeholder="origen1" >
<input id="origen2" type="text" class="form-control" placeholder="origen2" >
<input id="desA" type="text" class="form-control" placeholder="desA" >
<input id="desB" type="text" readonly="" class="form-control" placeholder="desB" >

<button id="btn-calcular-tiempo" type="button" class="btn btn-default">
Calcular
</button>


<div id="div-resultado"></div>

<div id="map-canvas"></div>


    </body>
</html>
