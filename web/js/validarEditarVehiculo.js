$(document).ready(function (){

    $('#listarNivel').change(function (){
        var nivel = $('#listarNivel option:selected').text().trim();
        $('#nivel_id').val(nivel);
    });    
    
    $('#con_id').change(function (){
        var nivel = $('#con_id option:selected').text().trim();
        $('#con_nom').val(nivel);
    });         
    
    $('#ayu_id').change(function (){
        var nivel = $('#ayu_id option:selected').text().trim();
        $('#ayu_nom').val(nivel);
    });             
    
    $("#placa_id").keyup(function(){
            $placa = document.getElementById("placa_id").value;
            $.post("SERVVehiculo", {pplaca:$placa}, function(data) {               
                    $("#ReportarPlaca").html(data);
            });
    });               
    
    $('#editar').click(function (){      
        
        var placa = $('#placa_id').val();
        var conductor = $('#con_id').val().trim();
        var ayudante = $('#ayu_id').val().trim();
        var año = $('#añoSelecionado').val();
        var marca = $('#marcaSelecionado').val().trim();
        var modelo = $('#modeloSelecionado').val().trim();
        var serie = $('#serieSelecionada').val().trim();
        var capacidad = $('#cap_id').val();
        var pasajeros = $('#pas_id').val();
        var respuestaPlaca = $('#ReportarPlaca').text().trim();
        var contenedorPlaca = $('#contenedorPlaca').val();
        var condicion = 'Libre';
        cadena = "^[A-DF-Z]{1}[A-Z0-9]{2}[0-9]{3}"; 
        re = new RegExp(cadena);
        
        
        if( placa === null || placa.length === 0 || /^\s+$/.test(placa) ) {
            alert('[ERROR] La placa del vehículo no puede quedar vacia.');
            return false;
        }  
        else if (!(placa.length <=6) || /^\s+$/.test(placa)){
            alert('[ERROR] La placa solo tiene 6 caracteres');
            return false;
        }          
        else if (!(placa.match(re)) || /\s+$/.test(placa)){
            alert('[ERROR] Ingrese una placa con formato adecuado');
            return false;
        }           
        else if((placa !== contenedorPlaca) && (respuestaPlaca !== condicion)){            
            alert('[ERROR] La nueva placa a registrar ya lo tiene otro vehículo');
            return false;
        }            
        else if ( conductor === '' ){
          alert('[ERROR] Confirme el conductor');
          return false;
        }     
        else if ( ayudante === '' ){
          alert('[ERROR] Confirme el ayudante');
          return false;
        }        
         else if ( año === null || año.length === 0 || /^\s+$/.test(año)){
            alert('[ERROR] Seleccione un año');
            return false;
        }      
        else if ( marca === null || marca.length === 0 || /^\s+$/.test(marca)){
            alert('[ERROR] Seleccione una marca');
            return false;
        }              
        else if ( modelo === null || modelo.length === 0 || /^\s+$/.test(modelo)){
            alert('[ERROR] Seleccione un modelo');
            return false;
        }  
        else if ( serie === null || serie.length === 0 || /^\s+$/.test(serie)){
            alert('[ERROR] Seleccione una serie');
            return false;
        }   
        else if ( capacidad === null || capacidad.length === 0 || /^\s+$/.test(capacidad)){
            alert('[ERROR] Seleccione cantidad máxima de capacidad');
            return false;
        }  
        else if ( pasajeros === null || pasajeros.length === 0 || /^\s+$/.test(pasajeros)){
            alert('[ERROR] Seleccione cantidad máxima de pasajeros');
            return false;
        }          
        return true;            
     });
}); 