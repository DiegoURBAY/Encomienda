$(document).ready(function (){
    	
    var map;
    var geocoder;
 var directionsService;
   var directionsDisplay ;
 
    function initialize() {
        
        //extras
        directionsService = new google.maps.DirectionsService;
        directionsDisplay = new google.maps.DirectionsRenderer;
                
        var opts = {
            center: new google.maps.LatLng(-10.1560366,-75.127738),
            zoom: 10
        };
        map = new google.maps.Map(document.getElementById('map-canvas'), opts);
        
        //extra       
        directionsDisplay.setMap(map);
        
        geocoder = new google.maps.Geocoder();
    }

    google.maps.event.addDomListener(window, 'load', initialize);


//autocompletar
    var inputa = document.getElementById("input-ubicacion-a");
    autocomplete = new google.maps.places.Autocomplete(inputa);

    var inputb = document.getElementById("input-ubicacion-b");
    autocomplete = new google.maps.places.Autocomplete(inputb);
    
  //calcular el tiempo estimado   
    $("#btn-calcular-tiempo").click(function(event){
        
        
   $("#div-resultado").html("Calculando..<br />");
   var ubicaciona = $("#input-ubicacion-a").val();
   var ubicacionb = $("#input-ubicacion-b").val();
   var service = new google.maps.DistanceMatrixService();
   
   service.getDistanceMatrix({
     origins: [ubicaciona],
     destinations: [ubicacionb],
     travelMode: google.maps.TravelMode.DRIVING,
     unitSystem: google.maps.UnitSystem.METRIC
   },callback);
   
   //extra
        calculateAndDisplayRoute(directionsService, directionsDisplay);
        
});
 
//Dentro de la funcion callback simplemente 
//se muestra al usuario la respuesta de la consulta 
//mediante el objeto respuesta regresado por la places api.
function callback(respuesta, status) {
   if (status != google.maps.DistanceMatrixStatus.OK) {
      $("#div-resultado").append('Error: ' + status);
   } else {
      var origen = respuesta.originAddresses;
      var destino = respuesta.destinationAddresses;
      deleteOverlays();
 
      for (var i = 0; i < origen.length; i++) {
         var results = respuesta.rows[i].elements;
         addMarker(origen[i], false);
         for (var j = 0; j < results.length; j++) {
            addMarker(destino[j], true);
            $("#div-resultado").append('Desde ' + origen[i] + ' hasta ' + destino[j]
            + ' son <code>' + results[j].distance.text + '</code> y el tiempo estimado es de <code>'
            + results[j].duration.text + '</code><br />');
         }
      }
   }
}
var bounds = new google.maps.LatLngBounds();

        var markersArray = [];
   //     var destinationIcon = 'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=B|00FF00|000000';
   //     var originIcon = 'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=A|FFFF00|000000';

        function addMarker(location, isDestination) {
/*        var icon;
        if (isDestination) {
        icon = destinationIcon;
        } else {
        icon = originIcon;
        }
        */
        geocoder.geocode({'address': location}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
        bounds.extend(results[0].geometry.location);
        map.fitBounds(bounds);
        var marker = new google.maps.Marker({
        map: map,
        position: results[0].geometry.location,
        icon: icon
        });
        markersArray.push(marker);
        } else {
        $("#div-resultado").append('Error al obtener el Geocode: ' + status);
        }
        });
        }

        function deleteOverlays() {
        for (var i = 0; i < markersArray.length; i++) {
        markersArray[i].setMap(null);
        }
        markersArray = [];
        }
        
        
        
     function calculateAndDisplayRoute(directionsService, directionsDisplay) {

        directionsService.route({
          origin: document.getElementById('input-ubicacion-a').value,
          destination: document.getElementById('input-ubicacion-b').value,
          optimizeWaypoints: true,
          travelMode: 'DRIVING'
          
        }, function(response, status) {
          if (status === 'OK') {
            directionsDisplay.setDirections(response);         
           
          } else {
            window.alert('Solicitud de indicaciones fallida debido a ' + status);
          }
        });        
        }
});