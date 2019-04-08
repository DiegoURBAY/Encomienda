$(document).ready(function (){      
    
    $('#listarEmisor').change(function (){
        var nivel = $('#listarEmisor option:selected').val();
        $('#contenedor_emisor_id').val(nivel);
    });      
    
    $('#listarReceptor').change(function (){
        var nivel = $('#listarReceptor option:selected').text().trim();
        $('#contenedor_receptor_id').val(nivel);
    });  
    
//    $('#calcular_precio').click(function (){
//       window.open("SERVPrecio?action=insert", "InsertarPrecio", "width=400, height=200");
//    });       
     
    var dateToday = new Date(); 
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
            dateFormat: 'yy-mm-dd',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: ''
            };
        $.datepicker.setDefaults($.datepicker.regional["es"]);
        $("#from").datepicker({ 
            numberOfMonths: 3, 
            showButtonPanel: true, 
            minDate: dateToday 
        }); 
    }); 
   
    $('#insertar').click(function (){      
        
        var emisor = $('#listarEmisor').val().trim();
        var receptor = $('#listarReceptor').val().trim();
        var usuario = $('#listarUsuario').val().trim();
        var vehiculo = $('#listarVehiculo').val().trim();
        var descipcion = $('#des_id').val();        
          
        if ( emisor === '' ){
          alert('[ERROR] Seleccione un emisor');
          return false;
        }     
        else if ( receptor === '' ){
          alert('[ERROR] Seleccione un receptor');
          return false;
        }             
        else if ( usuario === '' ){
          alert('[ERROR] Seleccione un usuario');
          return false;
        }        
        else if ( vehiculo === '' ){
          alert('[ERROR] Seleccione un vehiculo');
          return false;
        }        
        else if ( descipcion === null || descipcion.length === 0 || /^\s+$/.test(descipcion)){
            alert('[ERROR] La descripción no puede quedar vacio');
            return false;
        }          
               
          return true;
     });
});      