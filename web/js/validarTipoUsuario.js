  
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
            $.post("SERVTipoUsuario", {nnombre:$nombre}, function(data) {               
                    $("#ReportarNombre").html(data);
            });
    });      
    
    $('#insertar').click(function (){
        var nombre = $('#nom_id').val();
        var respuestaNombre = $('#ReportarNombre').text().trim();
        var condicion = 'Ya existe';             
        
        if( nombre === null || nombre.length === 0 || /^\s+$/.test(nombre) ) {
              alert('[ERROR] El tipo de usuario no puede quedar vacío.');
              return false;
        }
        else if( !(nombre.length <=25) || /^\s+$/.test(nombre) ) {
              alert('[ERROR] El tipo de usuario debe tener máximo 25 dígitos.');
              return false;
        }
        else if(respuestaNombre === condicion){
            alert('[ERROR] Ingrese un nombre que no este registrado');
            return false;
        }            
        
          // Si el script ha llegado a este punto, todas las condiciones
          // se han cumplido, por lo que se devuelve el valor true
          return true;
                 
    });    
});
    