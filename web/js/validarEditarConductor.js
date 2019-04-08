
$(document).ready(function (){

    $('#listarNivel').change(function (){
        var nivel = $('#listarNivel option:selected').text().trim();               
        $('#nivel_id').val(nivel);       
    });    
    
    //Permite ingresar solo letras y espacio vacios
    //Transforma las letras en minuscula
    // Los demás son eliminados segundos de ser escritos
    $('#nom_id, #ape_id').keyup( function () {
        $(this).val($(this).val().toLowerCase());
        if (!/^[ a-záéíóúüñ]*$/i.test(this.value)) {
            this.value = this.value.replace(/[^ a-záéíóúüñ]+/ig,"");
        }
    });

    //Permite ingresar solo numeros
    //Los demás son eliminados segundos de ser escritos, incluyendo espacios vacios
    $('#tel_id, #dni_id').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    });
    
    //Transforma las letras en minuscula
    $("#email_id").keyup(function() {
       $(this).val($(this).val().toLowerCase());
    });       

    //Busca el dni de manera asincrona
    $("#dni_id").keyup(function(){
            $dni = $('#dni_id').val();
            $.post("SERVConductor", {ddni:$dni}, function(data) {               
                    $("#ReportarDni").html(data);
            });
    });              
    
    //Busca la licencia de manera asincrona
    $("#email_id").keyup(function(){
            $email = $('#email_id').val();
            $.post("SERVConductor", {eemail:$email}, function(data) {               
                    $("#ReportarEmail").html(data);
            });
    }); 
    
    //Busca el correo de manera asincrona
    $("#lic_id").keyup(function(){
            $lic = $('#lic_id').val();
            $.post("SERVConductor", {llic:$lic}, function(data) {               
                    $("#ReportarLicencia").html(data);
            });
    });      
    
    $('#editar').click(function (){
        var dni = $('#dni_id').val();
        var licencia = $('#lic_id').val();
        var nombre = $('#nom_id').val();       
        var apellido = $('#ape_id').val();
        var email = $('#email_id').val();
        var telefono = $('#tel_id').val();
        var direcion = $('#direc_id').val();
        var indice = $('#listarNivel').val().trim();        
        var respuestaDni = $('#ReportarDni').text().trim();
        var respuestaLicencia = $('#ReportarLicencia').text().trim();
        var respuestaEmail = $('#ReportarEmail').text().trim();
        var contenedorDni = $('#contenedorDni').val();
        var contenedorLicencia = $('#contenedorLicencia').val();
        var contenedorEmail = $('#contenedorEmail').val();
        var condicion = 'Libre';      

        if( nombre === null || nombre.length === 0 || /^\s+$/.test(nombre) ) {
            alert('[ERROR] El nombre no puede quedar vacío.');
            return false;              
        }
        else if(!(nombre.length <=25) || /^\s+$/.test(nombre)){
            alert('[ERROR] El nombre no puede exceder los 25 dígitos.');
            return false;
        }   
        else if (!isNaN(nombre) || /^\s+$/.test(nombre) ) {
            alert('[ERROR] El nombre no puede tener números');
            return false;
        }          
        else if (apellido === null || apellido.length === 0 || /^\s+$/.test(apellido) ) {
            alert('[ERROR] El apellido no puede quedar vacio.');
            return false;
        }
        else if (!(apellido.length <=60) || /^\s+$/.test(apellido) ) {
            alert('[ERROR] El apellido debe tener un valor máximo de 60 dígitos.');
            return false;
        } 
        else if (!isNaN(apellido) || /^\s+$/.test(apellido) ) {
            alert('[ERROR] El apellido no puede tener números');
            return false;
        }            
        else if( dni === null || dni.length === 0 || /^\s+$/.test(dni) ) {
            alert('[ERROR] El dni no puede quedar vacio.');
            return false;
        }
        else if(  !(dni.length === 8) || /^\s+$/.test(dni) ) {
            alert('[ERROR] El dni debe tener un valor de 8 dígitos.');
            return false;
        }   
        else if((dni !== contenedorDni) && (respuestaDni !== condicion)){            
            alert('[ERROR] El nuevo dni a registrar ya lo tiene otro conductor');
            return false;
        }         
        else if( licencia === null || licencia.length === 0 || /^\s+$/.test(licencia) ) {
            alert('[ERROR] La licencia no puede quedar vacío.');
            return false;              
        }
        else if((licencia !== contenedorLicencia) && (respuestaLicencia !== condicion)){            
            alert('[ERROR] La nueva licencia a registrar ya lo tiene otro conductor');
            return false;
        }          
        else if (email === null || email.length === 0 || /^\s+$/.test(email) ) {
          alert('[ERROR] El email no puede quedar vacio.');
          return false;
        }        
        else if (!(/^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i.test(email))) {
            alert('[ERROR] Ingrese un email con formato adecuado');
            return false;
        }
        else if((email !== contenedorEmail) && (respuestaEmail !== condicion)){            
            alert('[ERROR] El nuevo email a registrar ya lo tiene otro conductor');
            return false;
        }         
        else if (telefono === null || telefono.length === 0 || /^\s+$/.test(telefono) ) {
            alert('[ERROR] El telefono no puede quedar vacio.');
            return false;
        }        
        else if (!(/^\d{9}$/.test(telefono)) ) {
          alert('[ERROR] El telefono celular debe tener 9 digitos');
          return false;
        }            
        else if (direcion === null || direcion.length === 0 || /^\s+$/.test(direcion)) {
          alert('[ERROR] La direccion no puede quedar vacia.');
          return false;
        }
        else if (!(direcion.length <=100) || /^\s+$/.test(direcion)) {
          alert('[ERROR] La direccion debe tener un valor de máximo de 100 dígitos');
          return false;
        }
        else if ( indice === ''){
          alert('[ERROR] Seleccione una opcion');
          return false;
        }          

        return true;             
     });
});