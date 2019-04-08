
$(document).ready(function (){
    
    //Permite ingresar solo letras.
    //Los demás son eliminados segundos de ser escritos
    $('#nom_id').keyup( function () {
        $(this).val($(this).val().toLowerCase());
        if (!/^[a-záéíóúüñ]*$/i.test(this.value)) {
            this.value = this.value.replace(/[^a-záéíóúüñ]+/ig,"");
        }
    }); 

    //Convierte a minuscula lo que digitas digito por digito
    $("#nom_id").keyup(function() {
       $(this).val($(this).val().toLowerCase());
    });
    
     $("#nom_id").keyup(function(){
        $nombre = $('#nom_id').val(); 
        $.post("SERVTipoUsuario", {
            nnombre: $nombre
        }, function(data) {               
                $("#ReportarNombre").html(data);
        });
    });        
    
    $('#editar').click(function (){
        var nombre = $('#nom_id').val();        
        var respuestaNombre = $('#ReportarNombre').text().trim();
        var contenedorNombre = $('#contenedorNombre').val();
        var condicion = 'Libre';
        
        if( nombre === null || nombre.length === 0 || /^\s+$/.test(nombre) ) {
              alert('[ERROR] El tipo de usuario no puede quedar vacío.');
              return false;
        }
        else if( !(nombre.length <=25) || /^\s+$/.test(nombre) ) {
              alert('[ERROR] El tipo de usuario debe tener máximo 25 dígitos.');
              return false;
        }
        else if((nombre !== contenedorNombre) && (respuestaNombre !== condicion)){            
            alert('[ERROR] El nuevo nombre a registrar ya lo tiene otro tipo de usuario');
            return false;
        }            
        else             
        
          // Si el script ha llegado a este punto, todas las condiciones
          // se han cumplido, por lo que se devuelve el valor true
          return true;
                 
    });    
});
