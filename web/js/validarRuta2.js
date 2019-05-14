
    function limpiar_marcadores(lista)
    {
        for(i in lista)
        {
            //QUITAR MARCADOR DEL MAPA
            lista[i].setMap(null);
        }
    }

$(document).ready(function (){
    
    $('#idencomienda').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    });    
        
        //Conseguir locales por departamento segun el origen
    $('#idencomienda').change(function (){
                                      
        var encomienda = $('#idencomienda').val();
        
                if(encomienda.length>=11){              
                   $('#idencomienda').val(null);
                   
                }        
                
        var data = {idEncomienda:encomienda} ;
        $.getJSON(
            "SERVLugar?action=buscar",  
            data,
            function ( res, est, jqXHR ) {
                if(res.estado === "ok"){                      
               var     objeto = JSON.parse(res.mensaje);
                var     objetoOrigen = JSON.parse(res.localOrigen);
                 var     objetoDestino = JSON.parse(res.localDestino);
                    console.log(res);
                        console.log(res.localOrigen);
                            console.log(res.localDestino);
                $("#origen").val(objeto.origenS);
                $("#destino").val(objeto.destinoS);

                $("#origen1").val(objetoOrigen.cx +", "+ objetoOrigen.cy);
                $("#origen2").val(objetoOrigen.direccion);

                 $("#desA").val(objetoDestino.cx +", "+ objetoDestino.cy);
                $("#desB").val(objetoDestino.direccion);

                          
                }
                if(res.estado === "error"){
                    alert("No existe ese id");
                   $('#idencomienda').val(null);
                   
                }
            }
        );

                     
   });     

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


    $("#btn-calcular-tiempo").click(function(event){
         var encomienda = $('#idencomienda').val();
        if (encomienda ===  null || encomienda.length ===  0 || /^\s+$/.test(encomienda) ) {
            alert('[Aviso] Ingrese una identificador(id) válido');
            return false;
        }
   
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
         
         console.log(results);
         addMarker(origen[i], false);
         for (var j = 0; j < results.length; j++) {
            addMarker(destino[j], true);
/*
 
                             $("#div-resultado").append('Desde ' + origen[i] + ' hasta ' + destino[j]
            + ' son <code>' + results[j].distance.text + '</code> y el tiempo estimado es de <code>'
            + results[j].duration.text + '</code><br />');
                 */
     $("#tiempo").val(results[j].duration.text);
     $("#distancia").val(results[j].distance.text);
         }
      }
      
   }
}

 var no_deseado = ["ChIJd9MB_UCStocRKGKxmSyDMzs"

                                         ];
var bounds = new google.maps.LatLngBounds();

        var markersArray = [];
    //   var destinationIcon = 'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=B|00FF00|000000';
   //    var originIcon = 'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld=A|FFFF00|000000';

        function addMarker(location, isDestination) {
/*        var icon;
        if (isDestination) {
        icon = destinationIcon;
        } else {
        icon = originIcon;
        }
        */
        geocoder.geocode({'address': location}, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
             $("#div-resultado").html("listo!!<br />");
    //    console.log(results[0].geometry.location);
    //    console.log(results[0]["place_id"]);
        
         var idLugar =results[0]['place_id'];
         
              //      alert(results[0]['place_id']);
              
        if(!no_deseado.includes(idLugar)){
                 bounds.extend(results[0].geometry.location);
                  map.fitBounds(bounds);
        }
        
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