
$(document).ready(function (){ 
    
    //Pasa el texto de la opcion al input
    //Si no añado trim, mi nivel sale despues de espacios en blanco
    $('#listarNivel').change(function (){
        var nivel = $('#listarNivel option:selected').text().trim();
        $('#nivel_id').val(nivel);
    });       
    
    //Permite ingresar solo letras.
    //Los demás son eliminados segundos de ser escritos
    $('#nom_id').keyup( function () {
        $(this).val($(this).val().toLowerCase());
        if (!/^[a-záéíóúüñ]*$/i.test(this.value)) {
            this.value = this.value.replace(/[^a-záéíóúüñ]+/ig,"");
        }
    });    
    
    //Convierte a minuscula lo que digitas digito por digito
    $("#email_id").keyup(function() {
       $(this).val($(this).val().toLowerCase());
    });
    
    //Busca el nombre de manera asincrona
    $("#nom_id").keyup(function(){
            $nombre = $('#nom_id').val();
            $.post("SERVUsuario", {nnombre:$nombre}, function(data) {               
                    $("#ReportarNombre").html(data);
            });
    });    
    
    //Busca el email de manera asincrona
    $("#email_id").keyup(function(){
            $email = $('#email_id').val();
            $.post("SERVUsuario", {eemail:$email}, function(data) {               
                    $("#ReportarEmail").html(data);
            });
    });      
    
    $('#insertar').click(function (){
        var nombre = $('#nom_id').val();
        var contra = $('#contra_id').val();
        var email = $('#email_id').val(); 
        var indice = $('#listarNivel').val().trim();
        var respuestaNombre = $('#ReportarNombre').text().trim();
        var respuestaEmail = $('#ReportarEmail').text().trim();
        var condicion = 'Ya existe';     
                
        if( nombre === null || nombre.length === 0 || /^\s+$/.test(nombre) ) {
              alert('[ERROR] El campo nombre no puede quedar vacío');
              return false;              
        }else if(!(nombre.length <=25) || !(nombre.length >=3) || /^\s+$/.test(nombre)){
            alert('[ERROR] El nombre debe tener entre 3 a 25 letras');
            return false;
        }
        else if(respuestaNombre === condicion){
            alert('[ERROR] Ingrese un nombre que no este registrado');
            return false;
        }        
        else if (contra ===  null || contra.length ===  0 || /^\s+$/.test(contra) ) {
          alert('[ERROR] La contraseña no puede quedar vacío');
          return false;
        }
        else if ( !(contra.length <=20) || /^\s+$/.test(contra) ) {
          alert('[ERROR] La contraseña debe tener un valor máximo de 20 dígitos');
          return false;
        }
        else if (email ===  null || email.length ===  0 || /^\s+$/.test(email) ) {
          alert('[ERROR] El email no puede quedar vacío');
          return false;
        }        
        else if ( !(email.length <=50) || /^\s+$/.test(email) ) {
          alert('[ERROR] El email debe tener un valor máximo de 50 dígitos');
          return false;
        }        
        else if (!(/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i.test(email))) {
          alert('[ERROR] Ingrese un email con formato adecuado');
          return false;
        }
        else if(respuestaEmail === condicion){
            alert('[ERROR] Ingrese un email que no este registrado');
            return false;
        }          
        else if ( indice === '' ){
          alert('[ERROR] Seleccione una opción');
          return false;
        }        

        return true;
                        
    });
});