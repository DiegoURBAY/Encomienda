<%-- 
    Document   : Locales
    Created on : 05/05/2019, 04:42:35 PM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Locales</title>   
  <style>
      #map {
        width: 100%;
        height: 400px;
        background-color: grey;
      }

    </style>

    </head>
    <body>
    <h3>My Google Maps Demo</h3>
    <!--The div element for the map -->
    <div id="map"></div>
    <script>
// Initialize and add the map
function initMap() {
  // The location of Uluru
  var pasco = {lat: -10.427011, lng:  -75.526225};
  // The map, centered at Uluru
  var map = new google.maps.Map(
      document.getElementById('map'), {zoom: 5, center: pasco});
  // The marker, positioned at Uluru
  var marker = new google.maps.Marker({position: pasco, map: map});
}
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC5T3l2CQRwr74godFSrpaOXED1kYpoCRQ&callback=initMap">
    </script>


  </body>

</html>
