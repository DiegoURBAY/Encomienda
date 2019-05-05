
   $(document).ready(function(){              
     
     /*  
       $('#pesoSobre, #pesoSobreOriginal').change(function (){
      var pesoSobreOriginal = $("#pesoSobreOriginal").val();
      var pesoSobre = $("#pesoSobre").val();
      
      var pesoSobreOriginal_float = parseFloat(pesoSobreOriginal).toFixed(2);
      var pesoSobre_float = parseFloat(pesoSobre).toFixed(2);
      if(pesoSobre_float > pesoSobreOriginal_float ){
        alert('[Aviso] El peso nuevo no puede exceder al original');
        $('#pesoSobre').val(0);     
      }
});
*/

/*
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
        
        $('#origen, #destino').change( function () {
            var origen = $('#origen').val();
            var destino = $('#destino').val();
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
                }

        });          
          
        } );                    
        
    $('#origen, #destino').keyup( function () {
        $(this).val($(this).val().toLowerCase());
        if (!/^[a-záéíóúüñ]*$/i.test(this.value)) {
            this.value = this.value.replace(/[^a-záéíóúüñ]+/ig,"");
        }
    });
 */ 
/*
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
    */
    $('#cantidadSobres').keyup(function () {
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


/*
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
    */
    $("#editarSobre1").click(function() {

        //var origen = $('#origen').val();
      //  var destino = $('#destino').val();
        var cantidadSobre = $('#cantidadSobres').val();
    /*                    
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
      */  
        if( cantidadSobre === null || cantidadSobre.length === 0 || /^\s+$/.test(cantidadSobre) ) {
             alert('[Aviso] La cantidad del sobre no puede quedar vacío');
            $('#cantidadSobres').css("border", "1px solid red");
            $("#cantidadSobres").focus();   
              return false;              
        }
        else {
             $('#cantidadSobres').css("border", "");
             return false;   
        }
        cantidadSobre = parseInt(cantidadSobre);       
        if(cantidadSobre < 1) {
            alert('[Aviso] La cantidad del sobre no puede ser cero');
            $('#cantidadSobres').css("border", "1px solid red");
            $("#cantidadSobres").focus();                               
            return false;              
        } 
        else if(cantidadSobre >= 1){
            $('#cantidadSobre').css("border", "");
             return false; 
        }         
        
       var answer = confirm('¿Seguro que desea registrar?');
       if (answer)
        {
          console.log('yes');
          alert('Recibirá en su email los datos de su encomienda');
          return true;
        }
        else
        {
          console.log('cancel');
          alert('Ha cancelado la edición');
          return false;
        }
      
    });        
    

});    
