
$(document).ready(function (){
            
    //Permite ingresar solo letras y espacio vacios
    //Transforma las letras en minuscula
    // Los demás son eliminados segundos de ser escritos
    $('#inputNombre').keyup( function () {
        $(this).val($(this).val().toLowerCase());
        if (!/^[ a-záéíóúüñ]*$/i.test(this.value)) {
            this.value = this.value.replace(/[^ a-záéíóúüñ]+/ig,"");
        }
    });

    //Permite ingresar solo numeros
    //Los demás son eliminados segundos de ser escritos, incluyendo espacios vacios
    $('#inputTelefono, #inputIdentificador').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    });

    //Transforma las letras en minuscula
    $("#inputEmail").keyup(function() {
       $(this).val($(this).val().toLowerCase());
    });

    //Busca el dni de manera asincrona
    $("#inputIdentificador").keyup(function(){
            $identificador = $('#inputIdentificador').val();
            $.post("SERVCliente", {iidentificador:$identificador}, function(data) {               
                    $("#ReportarIdentificador").html(data);
            });
    }); 
    
    //Busca el correo de manera asincrona
    $("#inputEmail").keyup(function(){
            $email = $('#inputEmail').val();
            $.post("SERVCliente", {eemail:$email}, function(data) {               
                    $("#ReportarEmail").html(data);
            });
    });     
    
    //Busca el usuario de manera asincrona
    $("#inputUserame").keyup(function(){
            $usuario = $('#inputUserame').val();
            $.post("SERVCliente", {uusuario:$usuario}, function(data) {               
                    $("#ReportarUsuario").html(data);
            });
    });        
        
   
    $('#registrar').click(function (){
        var opciones = document.getElementsByName("optradio"); 
        var ruc_dni = $('#inputIdentificador').val();
        var nombre = $('#inputNombre').val();
        var usuario = $('#inputUserame').val();
        var email = $('#inputEmail').val();                 
        var celular = $('#inputTelefono').val();
        var contra = $('#inputPassword').val();  
        var respuestaIdentificador = $('#ReportarIdentificador').text().trim();
        var respuestaEmail = $('#ReportarEmail').text().trim();
        var respuestaUsuario = $('#ReportarUsuario').text().trim();
        var condicion = 'Ya existe';   
                        
        var seleccionado = false;
        for(var i=0; i<opciones.length; i++) {    
          if(opciones[i].checked) {              
            seleccionado = true;     
            break;
          }
        }

        if(!seleccionado) {
          alert('[ERROR] Eliga entre RUC o DNI ');
          return false;
        }  
        
        for(x = 0; x < opciones.length; x++){
            if(opciones[x].checked){
                if( opciones[x].value === '1' ){
                    if(  ruc_dni === null || ruc_dni.length === 0 || /^\s+$/.test(ruc_dni)  ){
                        alert('[ERROR] El RUC no puede quedar vacío ');
                                return false;
                    }             
                    else if(  !(ruc_dni.length === 11)|| /^\s+$/.test(ruc_dni) ){
                        alert('[ERROR] El RUC debe tener 11 digitos');
                        return false;
                    }  
                }  
                else if( opciones[x].value === '2'){
                    if(  ruc_dni === null || ruc_dni.length === 0 || /^\s+$/.test(ruc_dni)  ){
                        alert('[ERROR] El DNI no puede quedar vacío');
                        return false;
                    }             
                    else if(  !(ruc_dni.length === 8)|| /^\s+$/.test(ruc_dni) ){
                        alert('[ERROR] El dni debe tener un valor de 8 dígitos.');   
                                return false;
                    }  
                }                
            } 
        }           
        
        if(respuestaIdentificador === condicion){
            alert('[ERROR] Ingrese un Ruc o DNI que no este registrado');
            return false;
        }      
        else if (nombre === null || nombre.length === 0 || /^\s+$/.test(nombre) ) {
            alert('[ERROR] El nombre no puede quedar vacio');
            return false;
        }
        else if (!isNaN(nombre) || /^\s+$/.test(nombre) ) {
            alert('[ERROR] El nombre no puede tener números');
            return false;
        }
        else if(!(nombre.length <=50) || /^\s+$/.test(nombre)){
            alert('[ERROR] El nombre no puede exceder los 50 caracteres');
            return false;
        }        
        else if (email ===  null || email.length ===  0 || /^\s+$/.test(email) ) {
            alert('[ERROR] El email no puede quedar vacío');
            return false;
        }               
        else if (!(/^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i.test(email))) {
            alert('[ERROR] Ingrese un email con formato adecuado');
            return false;
        }
        else if(!(email.length <=30) || /^\s+$/.test(email)){
            alert('[ERROR] El email no puede exceder los 50 caracteres');
            return false;
        }
        else if(respuestaEmail === condicion){
            alert('[ERROR] Ingrese un email que no este registrado');
            return false;
        }        
        else if (celular ===  null || celular.length ===  0 || /^\s+$/.test(celular) ) {
            alert('[ERROR] El teléfono celular no puede quedar vacío');
            return false;
        }           
        else if (!(/^\d{9}$/.test(celular)) ) {        
            alert('[ERROR] El telefono celular debe tener 9 digitos');
            return false;
        }        
        
        if( usuario === null || usuario.length === 0 || /^\s+$/.test(usuario) ) {
              alert('[ERROR] El campo usuario no puede quedar vacío');
              return false; 
              
        }else if(!(usuario.length <=25) || !(usuario.length >=3) || /^\s+$/.test(usuario)){
            alert('[ERROR] El usuario debe tener entre 3 a 25 letras');
            return false;
        }
        
        else if(respuestaUsuario === condicion){
            alert('[ERROR] Ingrese un usuario que no este registrado');
            return false;
        }
        if (contra ===  null || contra.length ===  0 || /^\s+$/.test(contra) ) {
            alert('[ERROR] La Contraseña no puede quedar vacío');
            return false;
        }
        
        confirm('¿Seguro que desea registrarse?');
        alert('Recibirá en su email un aviso de su registro');
        
        return true;           
     });
     
     $('#regresar').click(function (){
        confirm('¿¿Seguro que desea salir del registro?');
        return true; 
     });
});
