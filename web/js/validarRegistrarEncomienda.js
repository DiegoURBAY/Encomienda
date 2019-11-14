
      $(function() {
        $.datepicker.regional['es'] = {
            closeText: 'Cerrar',
            currentText: 'Hoy',
            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
            'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
            'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié;', 'Juv', 'Vie', 'Sáb'],
            dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'],
            weekHeader: 'Sm',
            dateFormat: 'dd/mm/yy',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: ''
            };
        $.datepicker.setDefaults($.datepicker.regional["es"]);
        $("#envio, #llegada").datepicker({ 
            numberOfMonths: 1, 
            showButtonPanel: true,
            minDate: 0

        });                                
    });      
    
$(document).ready(function(){          
       
    setTimeout(promotion(), 2);
    
    var descuento = Array();

    function promotion(){        
        var usuario = $("#cliente3").val();    
        $.ajax({
            type:"GET",
            url:"SERVPromocion",
            dataType:"JSON",
            data:"&action=buscarCliente&usuario="+usuario,
            success:function(data){
            if(data.estado === "ok")
            {
                var objeto = JSON.parse(data.mensaje);  
                var mensaje_completo = objeto.split(',');
                if( mensaje_completo[0] === "1"){
                     alert("hola, "+usuario+". Tiene "+ mensaje_completo[0]+ " descuento de "+mensaje_completo[1] +"%");                                     
                    console.log(objeto);
                    descuento.push(mensaje_completo[1]);                
                    $("#descuento").text(mensaje_completo[1]);
                    console.log(descuento);                     
                    }
                else{
                     $("#descuento").text(0);
                }    
                
            }
            else{

            }
        },
        beforeSend:function(){

        },
        complete:function(){

        }
        });       
    }    
       
  /*     
          	
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
                 $("#recorrido").val(results[j].distance.text);
                 $("#tiempo").val(results[j].duration.text);
            //    $("#div-resultado").append('Desde ' + origen[i] + ' hasta ' + destino[j]
             //   + ' son <code>' + results[j].distance.text + '</code> y el tiempo estimado es de <code>'
             //   + results[j].duration.text + '</code><br />');
             }
          }
       }
    }
    
    var bounds = new google.maps.LatLngBounds();

        var markersArray = [];
        function addMarker(location) {
            
            geocoder.geocode({'address': location}, function(results, status) {
                if (status === google.maps.GeocoderStatus.OK) {
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
                             
*/
    $('#mensaje_error').hide(); 
    
    //Array de departamentos
    var availableTags = new Array();

    //Conseguir locales por departamento segun el origen
    $('#origen').change(function (){                
        var origen = $('#origen').val();
        var data = {nombre:$("#origen").val()} ;
        $.getJSON(
            "SERVLugar?action=ubicacion",  
            data,
            function ( res, est, jqXHR ) {
                if(res.estado === "ok"){                      
               //     availableTags.length = 0;
                    if(availableTags.includes(origen) ) {
                        var objeto = JSON.parse(res.mensaje);
                        console.log(res);
                        $("#origen1").val(objeto.cx +", "+ objeto.cy);
                        $("#origen2").val(objeto.direccion);
                    }
                    /*
                    $.each(objeto, function (i, item){
                       // availableTags[i] = item.nombre;
                  //  console.log(objeto);
                        $("#origen1").val(item.nombre);
                    });
                    */
                }
                if(res.estado === "error"){
                    alert("No hay departamentos disponibles");
                }
            }
        );
    });
    
    //Conseguir locales por departamento segun el destino
        $('#destino').change(function (){
        var destino = $('#destino').val();
        var data = {nombre:$("#destino").val()} ;
        $.getJSON(
            "SERVLugar?action=ubicacion",  
            data,
            function ( res, est, jqXHR ) {
                if(res.estado === "ok"){                      
              //      availableTags.length = 0;   
              //evita que busce con la letra que se introduce antes de ser eliminada por no estar en el array
                    if(availableTags.includes(destino) ) {
                        var objeto = JSON.parse(res.mensaje);
                    console.log(objeto);
                        $("#desA").val(objeto.cx +", "+ objeto.cy);
                        $("#desB").val(objeto.direccion);
                    }

           /*         var objeto = JSON.parse(res.mensaje);
                    $.each(objeto, function (i, item){
                        availableTags[i] = item.nombre;
                    });               
                    */
                }                   
                if(res.estado === "error"){
                    alert("No hay departamentos disponibles");
                }
            }
        );
    });

      
        
        $('#origen').bind("keydown",function (event){
           var data = {nombre:$("#origen").val()} ;
                $.getJSON(
                    "SERVLugar?action=completar",  
                    data,
                    function ( res, est, jqXHR ) {
                        if(res.estado === "ok"){        
                            console.log(res);
                            availableTags.length = 0;
                            var objeto = JSON.parse(res.mensaje);
                            $.each(objeto, function (i, item){
                                availableTags[i] = item.nombre;
                            });
                        }
                        if(res.estado === "error"){
                            alert("No hay departamentos disponibles");
                        }
                    }
                );
        });
        
        $('#destino').bind("keydown",function (event){
           var data = {nombre:$("#destino").val()} ;
                $.getJSON(
                    "SERVLugar?action=completar",  
                    data,
                    function ( res, est, jqXHR ) {
                        if(res.estado === "ok"){                      
                            availableTags.length = 0;
                            var objeto = JSON.parse(res.mensaje);
                            $.each(objeto, function (i, item){
                                availableTags[i] = item.nombre;
                            });
                        }
                        if(res.estado === "error"){
                            alert("No hay departamentos disponibles");
                        }
                    }
                );
        });        
        
        
         $('#origen, #destino').autocomplete({
            source: availableTags           
         });
  
        $('#origen, #destino').change( function () {
            var origen = $('#origen').val();
            var destino = $('#destino').val(); 
             $('#tiempo').val(null);
            $('#recorrido').val(null); 
            
         
                if( origen === destino){
                    alert('Origen y Destino deben ser diferentes');
                     $('#origen').val(null);
                     $('#destino').val(null);
                     $('#origen').css("border", "1px solid red");
                     $('#destino').css("border", "1px solid red");                   
                }
                if(origen !== destino){
                    $('#origen').css("border", "");
                    $('#destino').css("border", "");
                }
                if(!availableTags.includes(origen)){                    
                    $('#origen').val(null);                 
                }
                if(!availableTags.includes(destino)){
                    $('#destino').val(null);                   
                }   /*
                if(origen !== destino && origen.length !== 0 && destino.length !== 0 && sobres.length !== 0){
                    datos();
                    $("#cantidadSobres").val(sobres);
                }
            */
              //   setTimeout(datos(),10000);
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
        
    /*    
          $('#origen, #destino').change( function () {
                   
            var origen2 = $('#origen2').val();
            var destino = $('#desB').val();

            if( origen2 !== destino){
                //$("#btn-calcular-tiempo").click();
                alert("bien");
                setTimeout(datos(),10000);
            }                                  
  
        });
  */
 $('#calcularTR').click(function (){
        var origen = $('#origen').val();
        var destino = $('#destino').val();
                                
        if( origen === null || origen.length === 0 || /^\s+$/.test(origen) ) {
            alert('[Aviso] El origen no puede quedar vacío');
            $('#origen').css("border", "1px solid red");
            $("#origen").focus();
            return false;              
        }   
        else if(!(origen.length <=30) || /^\s+$/.test(origen)){
            alert('[Aviso] El origen no puede exceder los 30 dígitos');
            $('#origen').css("border", "1px solid red");
            $("#origen").focus();
            return false; 
        }   
        else if( destino === null || destino.length === 0 || /^\s+$/.test(destino) ) {
            alert('[Aviso] El destino no puede quedar vacío');
            $('#destino').css("border", "1px solid red");
            $("#destino").focus();
            return false;              
        }
     datos();
 });
         
   function datos (){
               
          // $('#desB').prop('readonly',false);

        var origin1 = $('input:hidden#origen1').val();
        var destinationB =  $('#desB').val();
        
        var coordenadas = origin1.toString();
        var lista = coordenadas.split(",");
        var coordenadas2 = destinationB.toString();
        var lista2 = coordenadas2.split(",");
                                        
        var origin1_corde = new google.maps.LatLng(lista[0],  lista[1]);
        var destinationB_corde = new google.maps.LatLng(lista2[0], lista2[1]);
        
       $("#div-resultado").html("Calculando..<br />");
        var service = new google.maps.DistanceMatrixService();

        service.getDistanceMatrix({
            origins: [origin1_corde, $("#origen2").val()],
            destinations: [$("#desA").val(),destinationB_corde],
            travelMode: google.maps.TravelMode.DRIVING,
            unitSystem: google.maps.UnitSystem.METRIC
        },callback);

            calculateAndDisplayRoute(directionsService, directionsDisplay);
              
 };

    function callback(respuesta, status) {
        if (status !== google.maps.DistanceMatrixStatus.OK) {
          $("#div-resultado").append('Error: ' + status);
        }
        else {
          var origen = respuesta.originAddresses;
          var destino = respuesta.destinationAddresses;
          deleteOverlays();

            for (var i = 0; i < origen.length; i++) {
                 var results = respuesta.rows[i].elements;

                console.log(results);
                addMarker(origen[i], false);
                for (var j = 0; j < results.length; j++) {
                   addMarker(destino[j], true);
                   console.log(results[j]);
                    $("#tiempo").val(results[j].duration.text);
                    $("#recorrido").val(results[j].distance.text);                
                    var dur =results[j].duration.text;
                    
                    var dura = dur.match(/\d+/g); // take all "grouped" number
                    // dura ["2", "10"]

                    dura = dura[0] * 60 + (+dura[1]); 
                    $("#tiempoConvertido").val(dura);
                 //   alert("dura:"+dura);

                }
            }
       }
    }

        var no_deseado = ["ChIJd9MB_UCStocRKGKxmSyDMzs" ];
        
        var bounds = new google.maps.LatLngBounds();

        var markersArray = [];

        function addMarker(location) {
            geocoder.geocode({'address': location}, function(results, status) {
                if (status === google.maps.GeocoderStatus.OK) {
                     $("#div-resultado").html("listo!!<br />");

                 var idLugar =results[0]['place_id'];

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
 //$('#origen2').prop('readonly',false);

        directionsService.route({
           
          origin: $('#origen2').val(),
          destination:  $('input:hidden#desA').val(),
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


    $('#origen, #destino').keyup( function () {
        $(this).val($(this).val());
        if (!/^[ a-zA-ZáéíóúüñÁÉÍÓÚ]*$/i.test(this.value)) {
            this.value = this.value.replace(/[^ a-zA-ZáéíóúüñÁÉÍÓÚ]+/ig,"");
        }
    });

    $("input[type=radio]").click(function(event){
        var valor = $(event.target).val();
        if(valor === "sobre"){
            $("#div1").show();
            $("#div2").hide();
            $("#altura").val(0);
            $("#anchura").val(0);
            $("#largo").val(0);
            $("#cantidadPaquetes").val(0);
            $("#pesoPaquete").val(0);
            $("#precioPaquete").val(0);
            $("#pesoVolumen").val('');
            $('#mensaje_error').hide();
        } else if (valor === "paquete") {
            $("#div1").hide();
            $("#cantidadSobres").val(0);
            $("#pesoSobre").val(0);
            $("#precioSobre").val(0);
            $("#div2").show();
        } else { 
            // Otra cosa
        }
    });    
    
    $('#cantidadSobres, #cantidadPaquetes, #largo,#altura,#anchura ').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    });
    
    $('#cantidadSobres').change( function () {
        var ingreso = $("#cantidadSobres").val();
        var precio_unitario = 10;
        try{
            //Calculamos el número escrito:
             if(ingreso >20){
                   var ingreso = $("#cantidadSobres").val(0);
             }
             ingreso = (isNaN(parseFloat(ingreso)))? 0 : parseFloat(ingreso*precio_unitario);
             ingreso = parseFloat(ingreso).toFixed(2);

            $("#precioSobre").val(ingreso);
         }
        //Si se produce un error no hacemos nada
        catch(e) {}
      });    



     $('#cantidadPaquetes, #largo, #altura, #anchura ,#pesoPaquete, #precioPaquete').change( function () {
        var cantidadPaquetes = $('#cantidadPaquetes').val();
        var largo = $('#largo').val();
        var altura = $('#altura').val();
        var anchura = $('#anchura').val();
        var pesoPaquete = $('#pesoPaquete').val();
        var volumen = $('#volumen').val();

        var costoPesoKilo = 0.20;
        
        var pesoVolumetrico = 0;
        var pesoPaquete_float = 0;
        var peso ;     
 
        try{
        //Calculamos el número escrito:
        if(cantidadPaquetes >25){
            cantidadPaquetes = $("#cantidadPaquetes").val(0);
        }
        
        if(altura < 15 || altura > 75){
           altura = $('#altura').val(0);
       
        }       
        if(anchura < 15 || anchura > 65){
           anchura = $('#anchura').val(0);
           
        }
        if(largo < 25 || largo > 85){
            largo =  $('#largo').val(0);          
            
        }


        var dimensiones = altura*anchura*largo;
        
        var volumen =  Math.round(parseFloat(dimensiones*parseInt(cantidadPaquetes)/1000000)*100)/100;
       
        if(volumen > 0.01){
            $('#volumen').val(volumen);
        }
        else if(volumen <= 0.01){
            $('#volumen').val(0.01);
        }        
        
        var operacion = parseFloat(dimensiones/1000).toFixed(2);
        
       if(!isNaN(operacion) ){
           pesoVolumetrico = $('#pesoVolumen').val(operacion);
       }
       else {
           pesoVolumetrico = $('#pesoVolumen').val('Altura*Anchura*Largo');
       }
       
       console.log(pesoVolumetrico);
        
           
        pesoPaquete_float = parseFloat(pesoPaquete).toFixed(2);
        console.log(pesoPaquete_float); 
        if(pesoPaquete_float < 1.14 || pesoPaquete_float > 200){
           pesoPaquete_float = $('#pesoPaquete').val(0);           
        }
        if(pesoPaquete_float >= 1.14 || pesoPaquete_float <= 200){
            if (pesoPaquete % 1 !== 0){
                var pesoPaquete_split = pesoPaquete.split(".");
                var pesoPaquete_decimal = pesoPaquete_split[1];

                if(pesoPaquete_decimal.length > 2){
                    $('#pesoPaquete').val(0);
                }            
            }               
        }

        
        if (parseFloat( $('#pesoVolumen').val()) > parseFloat($('#pesoPaquete').val())){
          peso = $('#pesoVolumen').val();

        }else  {
           peso = $('#pesoPaquete').val();

        }   
        
        console.log(peso);
        //console.log(pesoPaquete*parseFloat(costoPesoKilo).toFixed(2));
                
        var precio = Math.round(parseFloat(cantidadPaquetes*costoPesoKilo*peso))*100/100;
        if(precio < 10){
            precio = 10*cantidadPaquetes;
        }

        $("#precioPaquete").val(precio);         
         
         
         if( parseFloat($("#precioPaquete").val()) > 0){
                 if (parseFloat( $('#pesoVolumen').val()) > parseFloat($('#pesoPaquete').val())){
                     alert('El peso volumen se usará en el precio');
                 }
                 else{
                     alert('El peso volumen NO se usará en el precio');
                 }
         }
        
        }
        //Si se produce un error no hacemos nada
        catch(e) {}

    });
      
       $('#pesoSobre, #pesoPaquete').change( function () {
            var pesoSobre = $('#pesoSobre').val();           
            var pesoPaquete = $('#pesoPaquete').val();
           
           // console.log(parseInt(pesoSobre));
            var pesoSobre_float = parseFloat(pesoSobre).toFixed(2);
         //   console.log(pesoSobre_float);
          //  var pesoPaquete_float = parseFloat(pesoPaquete).toFixed(2);
                
            if(pesoSobre_float < 0.01 || pesoSobre_float > 1.13){
                
                $('#pesoSobre').val(0);
            }
            if(pesoSobre_float >= 0.01 || pesoSobre_float <= 1.13){
                if(pesoSobre % 1 !== 0){
                    var pesoSobre_split = pesoSobre.split(".");
                    var pesoSobre_decimal = pesoSobre_split[1];
                    if(pesoSobre_decimal.length > 2){                    
                        $('#pesoSobre').val(0);
                    } 
                }
            }
            /*
            if(pesoPaquete_float < 1.13 || pesoPaquete_float > 200){
                $('#pesoPaquete').val(0);
            }    
            */
            console.log(pesoSobre);


            /*
            if(!(pesoPaquete % 1 === 0)){
                if(pesoPaquete.split(".") !== null || pesoPaquete !== 0){
                    var pesoPaquete_split = pesoPaquete.split(".");
                    var pesoPaquete_decimal = pesoPaquete_split[1];

                    if(pesoPaquete_decimal.length > 2){
                        $('#pesoPaquete').val(0);
                    }                    
                }    
          
            }
          */  
    /*         
        //console.log(decimal);        
        if(entero.length > 1){
              $('#pesoSobre').val(0);
        }
         entero = parseInt(entero);    
        if(entero > 1){
              $('#pesoSobre').val(0);
        }        
        //decimal string
        if(decimal.length > 2){
            $('#pesoSobre').val(0);
        }
        //decimal como numero     
        decimal = parseInt(decimal);        
        if(decimal > 13 && entero >=1){
            $('#pesoSobre').val(0);
        }
*/
        regexp = /.{1}[0-9]{2}$/;
        regexp3 = /[a-zA-ZÑáéíóúüñ]$/;
        re = new RegExp(regexp);
        re3 = new RegExp(regexp3);
        if (!(pesoSobre.match(re)) || /\s+$/.test(pesoSobre)|| pesoSobre.match(re3 )){           
            $('#pesoSobre').val(0);        
        }  
             
        regexp2 = /.{1}[0-9]{2}$/;
       
 
        re2 = new RegExp(regexp2);
        if (!(pesoPaquete.match(re2)) || /\s+$/.test(pesoPaquete)){           
            $('#pesoPaquete').val(0);        
        }               
             
      }); 

    /* 
   $('#pesoPaquete, #altura, #anchura, #largo').change( function () {
        var placa = $('#pesoPaquete').val();           
        var x = placa.split(".");

        var entero = x[0];
        var decimal = x[1];
        console.log(parseInt(placa));

        console.log(parseFloat(placa).toFixed(2));
        if(parseFloat(placa).toFixed(2) < 0.01){
            $('#pesoSobre').val(0);
        }
        if(parseFloat(placa).toFixed(2) > 1.13){
            $('#pesoSobre').val(0);
        }  

        regexp = /.{1}[0-9]{2}$/;

        re = new RegExp(regexp);
        if (!(placa.match(re)) || /\s+$/.test(placa)){           
            $('#pesoSobre').val(0);        
        }              
  }); 
      
*/
    $("#registrar").click(function() {

        var origen = $('#origen').val();
        var destino = $('#destino').val();
        var envio = $("#envio").val();
        var llegada = $("#llegada").val();
        
        if( origen === null || origen.length === 0 || /^\s+$/.test(origen) ) {
              alert('[ERROR] El campo origen no puede quedar vacío');
              return false;              
        }   
        else if(!(origen.length <=30) || /^\s+$/.test(origen)){
            alert('[ERROR] El origen no puede exceder los 30 dígitos');
            return false; 
        }   
        else if( destino === null || destino.length === 0 || /^\s+$/.test(destino) ) {
              alert('[ERROR] El campo origen no puede quedar vacío');
              return false;              
        }   
        else if(!(destino.length <=30) || /^\s+$/.test(destino)){
            alert('[ERROR] El origen no puede exceder los 30 dígitos');
            return false; 
        }        
        else if( envio === null || envio.length === 0 || /^\s+$/.test(envio) ) {
              alert('[ERROR] El campo envio no puede quedar vacío');
              return false;              
        }   
        else if( llegada === null || llegada.length === 0 || /^\s+$/.test(llegada) ) {
              alert('[ERROR] El campo llegada no puede quedar vacío');
              return false;              
        }               

        //Split de las fechas recibidas para separarlas
        var x = envio.split("/");
        var z = llegada.split("/");

        //Cambiamos el orden al formato americano, de esto dd/mm/yyyy a esto mm/dd/yyyy
        envio = x[2] + "-" + x[1] + "-" + x[0];
        llegada = z[2] + "-" + z[1] + "-" + z[0];

        //Comparamos las fechas
        if (Date.parse(envio) > Date.parse(llegada)){
            alert("[ERROR] La fecha de envio no puede superar la fecha de llegada");
            return false;   
        }
   
        return true;
    });    
    
    $("#registrar1").click(function() {

        var origen = $('#origen').val();
        var destino = $('#destino').val();
        var tiempo = $('#tiempo').val();
        var recorrido = $('#recorrido').val();
        var opciones = document.getElementsByName("pago1"); 
        var cantidadSobre = $('#cantidadSobres').val();
        var pesoSobre = $('#pesoSobre').val();    
        var altura = $('#altura').val();
        var anchura = $('#anchura').val();
        var largo = $('#largo').val();
        var cantidadPaquete = $('#cantidadPaquetes').val();
        var pesoPaquete = $('#pesoPaquete').val();
                        
        if( origen === null || origen.length === 0 || /^\s+$/.test(origen) ) {
            alert('[Aviso] El origen no puede quedar vacío');
            $('#origen').css("border", "1px solid red");
            $("#origen").focus();
            return false;              
        }   
        else if(!(origen.length <=30) || /^\s+$/.test(origen)){
            alert('[Aviso] El origen no puede exceder los 30 dígitos');
            $('#origen').css("border", "1px solid red");
            $("#origen").focus();
            return false; 
        }   
        else if( destino === null || destino.length === 0 || /^\s+$/.test(destino) ) {
            alert('[Aviso] El destino no puede quedar vacío');
            $('#destino').css("border", "1px solid red");
            $("#destino").focus();
            return false;              
        }   
        else if(!(destino.length <=30) || /^\s+$/.test(destino)){
            alert('[Aviso] El destino no puede exceder los 30 dígitos');
            $('#destino').css("border", "1px solid red");
            $("#destino").focus();            
            return false; 
        }  
        else if( tiempo === null || tiempo.length === 0 || /^\s+$/.test(tiempo) ) {
            alert('[Aviso] El tiempo no puede quedar vacío');
            $('#tiempo').css("border", "1px solid red");
            $("#tiempo").focus();
            return false;              
        }           
        else if( recorrido === null || tiempo.recorrido === 0 || /^\s+$/.test(recorrido) ) {
            alert('[Aviso] El recorrido no puede quedar vacío');
            $('#recorrido').css("border", "1px solid red");
            $("#recorrido").focus();
            return false;              
        }          
        
        for(x = 0; x < opciones.length; x++){
            if(opciones[x].checked){
                if( opciones[x].value === 'sobre' ){

                    if( cantidadSobre === null || cantidadSobre.length === 0 || /^\s+$/.test(cantidadSobre) ) {
                         alert('[Aviso] La cantidad del sobre no puede quedar vacío');
                        $('#cantidadSobres').css("border", "1px solid red");
                        $("#cantidadSobres").focus();   
                          return false;              
                    }
                    else{
                         $('#cantidadSobres').css("border", "");
                    }
                    cantidadSobre = parseInt(cantidadSobre);

                    if( cantidadSobre < 1 ) {
                        alert('[Aviso] La cantidad no puede ser cero');
                        $('#cantidadSobres').css("border", "1px solid red");
                        $("#cantidadSobres").focus();   
                        return false;              
                    }       
                    else{
                         $('#cantidadSobres').css("border", "");
                    }
                    
                    if( pesoSobre === null || pesoSobre.length === 0 ) {
                        alert('[Aviso] El peso del sobre no puede quedar vacío');                        
                        $('#pesoSobre').css("border", "1px solid red");
                        $("#pesoSobre").focus();                          
                        return false;              
                    }
                    else{
                        $('#pesoSobre').css("border", "");
                    }                    
    
                    pesoSobre = parseFloat(pesoSobre).toFixed(2);
                    if(pesoSobre < 0.01) {
                        alert('[Aviso] El peso no puede ser cero');
                        $('#pesoSobre').css("border", "1px solid red");
                        $("#pesoSobre").focus();                            
                        return false;              
                    }
                    else{
                        $('#pesoSobre').css("border", "");
                    }                    
                }  
                else if( opciones[x].value === 'paquete'){
                    if( altura === null || altura.length === 0 || /^\s+$/.test(altura) ) {
                        alert('[Aviso] La altura no puede quedar vacío');
                        $('#altura').css("border", "1px solid red");
                        $("#altura").focus();   
                        return false;              
                    }
                    else{
                        $('#altura').css("border", "");
                    }
                    altura = parseFloat(altura).toFixed(2);
                    if(altura < 0.01) {
                        alert('[Aviso] La altura no puede ser cero');
                        $('#altura').css("border", "1px solid red");
                        $("#altura").focus();  
                        return false;              
                    }
                    else{
                        $('#altura').css("border", "");
                    }                    
                    if( anchura === null || anchura.length === 0 || /^\s+$/.test(anchura) ) {
                        alert('[Aviso] La anchura no puede quedar vacío');
                        $('#anchura').css("border", "1px solid red");
                        $("#anchura").focus();                          
                        return false;              
                    }        
                    else{
                        $('#anchura').css("border", "");
                    }                          
                    anchura = parseFloat(anchura).toFixed(2);
                    if(anchura < 0.01) {
                        alert('[Aviso] La anchura no puede ser cero');
                        $('#anchura').css("border", "1px solid red");
                        $("#anchura").focus();                               
                        return false;              
                    }      
                    else{
                        $('#anchura').css("border", "");
                    }                      
                    if( largo === null || largo.length === 0 || /^\s+$/.test(largo) ) {
                        alert('[Aviso] El largo no puede quedar vacío');
                        $('#largo').css("border", "1px solid red");
                        $("#largo").focus();                           
                        return false;              
                    }
                    else{
                        $('#largo').css("border", "");
                    }                       
                    largo = parseFloat(largo).toFixed(2);
                    if(largo < 0.01) {
                        alert('[Aviso] El largo no puede ser cero');
                        $('#largo').css("border", "1px solid red");
                        $("#largo").focus();                            
                        return false;
                    }
                    else{
                        $('#largo').css("border", "");
                    }                      
                    if( cantidadPaquete === null || cantidadPaquete.length === 0 || /^\s+$/.test(cantidadPaquete) ) {
                        alert('[Aviso] La cantidad del paquete no puede quedar vacío');
                        $('#cantidadPaquetes').css("border", "1px solid red");
                        $("#cantidadPaquetes").focus();                         
                        return false;              
                    }           
                    else{
                        $('#cantidadPaquetes').css("border", "");
                    }                     
                    cantidadPaquete = parseInt(cantidadPaquete);       
                    if(cantidadPaquete < 1) {
                        alert('[Aviso] La cantidad del paquete no puede ser cero');
                        $('#cantidadPaquetes').css("border", "1px solid red");
                        $("#cantidadPaquetes").focus();                               
                        return false;              
                    } 
                    else{
                        $('#cantidadPaquetes').css("border", "");
                    }                     
                    if( pesoPaquete === null || pesoPaquete.length === 0 || /^\s+$/.test(pesoPaquete) ) {
                        alert('[Aviso] El peso del paquete no puede quedar vacío');
                        $('#pesoPaquete').css("border", "1px solid red");
                        $("#pesoPaquete").focus();                             
                        return false;              
                    }       
                    else{
                        $('#pesoPaquete').css("border", "");
                    }                       
                    pesoPaquete = parseFloat(pesoPaquete).toFixed(2);
                    if(pesoPaquete < 0.01) {
                        alert('[Aviso] El peso del paquete no puede ser cero');
                        $('#pesoPaquete').css("border", "1px solid red");
                        $("#pesoPaquete").focus();                               
                        return false;              
                    }
                    else{
                        $('#pesoPaquete').css("border", "");
                    }                     
                }                
            } 
        }                      
        
       var answer = confirm('El tiempo estimado es '+ $("#tiempo").val()+'. ¿Seguro que desea registrar?');    
       if (answer)
        {
            aplicar_descuento();
            console.log('yes');
            alert('Recibirá en su email los datos de su encomienda');
            return true;
        }
        else
        {
            console.log('cancel');          
            alert('Ha cancelado el registro');
            return false;
        }       
        
    });        
    function aplicar_descuento(){
        
        var precioSobre = $("#precioSobre").val();
        var precioPaquete = $("#precioPaquete").val();
        if(precioSobre > 0 && descuento > 0){
        //    alert("con sobre: "+precioSobre+""+descuento);
            var precio_descuento = parseFloat((100-descuento)*precioSobre/100);
            $("#precioSobre").val(precio_descuento);
            alert("Precio con descuento: S/. "+precio_descuento);
        }
        if(precioPaquete > 0 && descuento > 0){
          //  alert("con paquete: "+precioPaquete+""+descuento);
            var precio_descuento = parseFloat((100-descuento)*precioPaquete/100);
            $("#precioPaquete").val(precio_descuento);
            alert("Precio con descuento: S/. "+precio_descuento);            
        }               
    }
    /*    
    $("#calcular").click(function() {
        var largo = 0;
        var altura = 0;
        var anchura = 0;
        
         
         altura = $('#altura').val();
         anchura = $('#anchura').val();   
         largo = $('#largo').val();
         var dimensiones = altura*anchura;

       $('#pesoVolumen').val(dimensiones);
       
       $('#pesoPaquete').val(dimensiones*largo);
    });        
        
    $("#limpiar").click(function() {
         return confirm('¿Seguro que desea limpiar todos los campos?');   
    });
    */
});    
