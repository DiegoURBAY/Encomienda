   
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
            
       
});    
   


  
