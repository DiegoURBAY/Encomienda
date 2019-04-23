
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

        $('#mensaje_error').hide(); 
       
        $( function() {
          var availableTags = new Array();

          var availableTags = [
            "amazonas",
            "ancash",
            "apurimac",
            "arequipa",
            "ayacucho",
            "cajamarca",
            "callao",
            "lima"
          ];
          $( "#origen, #destino" ).autocomplete({
            source: availableTags,
            minLength: 1
          });
        } );                    
        
    $('#origen, #destino').keyup( function () {
        $(this).val($(this).val().toLowerCase());
        if (!/^[a-záéíóúüñ]*$/i.test(this.value)) {
            this.value = this.value.replace(/[^a-záéíóúüñ]+/ig,"");
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

        var costoPesoKilo = 0.20;
        
        var pesoVolumetrico = 0;
        var pesoPaquete_float = 0;
        var peso ;     
 
        try{
        //Calculamos el número escrito:
        if(cantidadPaquetes >25){
            cantidadPaquetes = $("#cantidadPaquetes").val(0);
        }
        
        if(altura < 10 || altura > 25){
           altura = $('#altura').val(0);
       
        }       
        if(anchura < 5 || anchura > 31){
           anchura = $('#anchura').val(0);
           
        }
        if(largo < 14 || largo > 75){
            largo =  $('#largo').val(0);          
            
        }


        var dimensiones = altura*anchura*largo;
        var operacion = parseFloat(dimensiones/10).toFixed(2);
        
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
         $("#precioPaquete").val(parseFloat(cantidadPaquetes*costoPesoKilo*peso).toFixed(2));
         
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
        var opciones = document.getElementsByName("pago1"); 
        var cantidadSobre = $('#cantidadSobres').val();
        var pesoSobre = $('#pesoSobre').val();    
        var pesoSobre_decimal = $('#pesoSobre').val();
        var altura = $('#altura').val();
        var anchura = $('#anchura').val();
        var largo = $('#largo').val();
        var cantidadPaquete = $('#cantidadPaquetes').val();
        var pesoPaquete = $('#pesoPaquete').val();
        
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
        
        for(x = 0; x < opciones.length; x++){
            if(opciones[x].checked){
                if( opciones[x].value === 'sobre' ){

                    if( cantidadSobre === null || cantidadSobre.length === 0 || /^\s+$/.test(cantidadSobre) ) {
                          alert('[ERROR] La cantidad del sobre no puede quedar vacío');
                          return false;              
                    }
                    cantidadSobre = parseInt(cantidadSobre);

                    if( cantidadSobre < 1 ) {
                          alert('[ERROR] La cantidad no puede ser cero');
                          return false;              
                    }       

                    else if( pesoSobre === null || pesoSobre.length === 0 ) {
                          alert('[ERROR] El peso del sobre no puede quedar vacío');
                          return false;              
                    }   
    
                    pesoSobre = parseFloat(pesoSobre).toFixed(2);
                    if(pesoSobre < 0.01) {
                          alert('[ERROR] El peso no puede ser cero');
                          return false;              
                    }   
                }  
                else if( opciones[x].value === 'paquete'){
                    if( altura === null || altura.length === 0 || /^\s+$/.test(altura) ) {
                          alert('[ERROR] La altura no puede quedar vacío');
                          return false;              
                    }
                    altura = parseFloat(altura).toFixed(2);
                    if(altura < 0.01) {
                          alert('[ERROR] El altura no puede ser cero');
                          return false;              
                    }
                    if( anchura === null || anchura.length === 0 || /^\s+$/.test(anchura) ) {
                          alert('[ERROR] La anchura no puede quedar vacío');
                          return false;              
                    }                    
                    anchura = parseFloat(anchura).toFixed(2);
                    if(anchura < 0.01) {
                          alert('[ERROR] La anchura no puede ser cero');
                          return false;              
                    }                         
                    if( largo === null || largo.length === 0 || /^\s+$/.test(largo) ) {
                          alert('[ERROR] El largo no puede quedar vacío');
                          return false;              
                    }     
                    largo = parseFloat(largo).toFixed(2);
                    if(largo < 0.01) {
                          alert('[ERROR] El largo no puede ser cero');
                          return false;              
                    }        
                    if( cantidadPaquete === null || cantidadPaquete.length === 0 || /^\s+$/.test(cantidadPaquete) ) {
                          alert('[ERROR] La cantidad del paquete no puede quedar vacío');
                          return false;              
                    }                    
                    cantidadPaquete = parseInt(cantidadPaquete);       
                    if(cantidadPaquete < 1) {
                          alert('[ERROR] El largo no puede ser cero');
                          return false;              
                    } 
                    if( pesoPaquete === null || pesoPaquete.length === 0 || /^\s+$/.test(pesoPaquete) ) {
                          alert('[ERROR] La peso del paquete no puede quedar vacío');
                          return false;              
                    }         
                    pesoPaquete = parseFloat(pesoPaquete).toFixed(2);
                    if(pesoPaquete < 0.01) {
                          alert('[ERROR] El peso del paquete no puede ser cero');
                          return false;              
                    }               
                }                
            } 
        }           
        
        confirm('¿Seguro que desea registrar?');
        alert('Recibirá en su email los datos de su encomienda');
        return ;
    });        
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
