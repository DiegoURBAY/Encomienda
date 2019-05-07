$(document).ready(function (){
    
    
    
    	
   var map;
   var geocoder;
   var directionsService;
   var directionsDisplay ;
 
    function initialize() {
        
        //extras
        directionsService = new google.maps.DirectionsService;
        directionsDisplay = new google.maps.DirectionsRenderer;
                
        var punto = new google.maps.LatLng(-10.156036, -75.127738);        
        var opts = {
            center: punto,
            zoom: 5,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map($("#map-canvas")[0], opts);
        
        //extra       
        directionsDisplay.setMap(map);
        
        geocoder = new google.maps.Geocoder();
    }

    google.maps.event.addDomListener(window, 'load', initialize);


//autocompletar

/*
    var inputa = document.getElementById("input-ubicacion-a");
    autocomplete = new google.maps.places.Autocomplete(inputa);

    var inputb = document.getElementById("input-ubicacion-b");
    autocomplete = new google.maps.places.Autocomplete(inputb);
    */
  //calcular el tiempo estimado   
    $("#btn-calcular-tiempo").click(function(event){
 /*       
        var origin1 = new google.maps.LatLng(-9.931171250440583,  -76.23922254118077);
var origin2 = 'Jirón Hermilio Valdizan 545, Huánuco 10001, Perú';
var destinationA = 'Los Cedros, Pucallpa 25002, Perú';
var destinationB = new google.maps.LatLng(-8.395239029464815, -74.57373714535942);

*/
 var origin1 = $("#origen1").val();
  var destinationB =  $("#desB").val();
                                        var coordenadas = origin1.toString();
                                        var lista = coordenadas.split(",");
                                         var coordenadas2 = destinationB.toString();
                                        var lista2 = coordenadas2.split(",");
                                        
     var origin1_corde = new google.maps.LatLng(lista[0],  lista[1]);
      var destinationB_corde = new google.maps.LatLng(lista2[0], lista2[1]);                                  
   $("#div-resultado").html("Calculando..<br />");
  // var ubicaciona = $("#input-ubicacion-a").val();
  // var ubicacionb = $("#input-ubicacion-b").val();
   var service = new google.maps.DistanceMatrixService();
   
   
   service.getDistanceMatrix({
    origins: [origin1_corde, $("#origen2").val()],
    destinations: [$("#desA").val(),destinationB_corde],
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
   if (status !== google.maps.DistanceMatrixStatus.OK) {
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
        position: results[0].geometry.location
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

var origin2 = 'Jirón Hermilio Valdizan 545, Huánuco 10001, Perú';
var destinationA = 'Los Cedros, Pucallpa 25002, Perú';
        directionsService.route({
          origin: $("#origen2").val(),
          destination: $("#desA").val(),
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