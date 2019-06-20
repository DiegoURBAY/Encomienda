jQuery(function ($) {
    
    $('#nombre').keyup( function () {
        $(this).val($(this).val().toLowerCase());
        if (!/^[ a-záéíóúüñ]*$/i.test(this.value)) {
            this.value = this.value.replace(/[^ a-záéíóúüñ]+/ig,"");
        }
    });

    $('#telefono, #dni').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    });

    $('#email').keyup(function() {
       $(this).val($(this).val().toLowerCase());
    });
    
    
    $("#dni").change(function(){        
        var palabra_buscar = $("#dni").val();
        var mensaje = "[AVISO] Dni a registrar ya existe. Se le devolverá el valor original";
        var tipo = 1;
        verificar_ajax(tipo,palabra_buscar,mensaje);
    });     
    
    $("#email").change(function(){
        var palabra_buscar = $("#email").val();
        var mensaje = "[AVISO] Email a registrar ya existe. Se le devolverá el valor original";
        var tipo = 2;        
         verificar_ajax(tipo,palabra_buscar,mensaje);
    });
    
    
    $("#licencia").change(function(){        
       var palabra_buscar = $("#licencia").val();
       var mensaje = "[AVISO] Licencia a registrar ya existe. Se le devolverá el valor original";
       var tipo = 3;
       verificar_ajax(tipo,palabra_buscar,mensaje);
    });         
    
    function verificar_ajax(tipo, palabra_buscar, mensaje){
        var palabra  = "";

        if(tipo === 1){
            palabra = "dni";
        }  
        if(tipo === 2){
            palabra = "email";
        }      
        if(tipo === 3){
            palabra = "licencia";
        }        
        $.ajax({
            type:"POST",
            dataType:"JSON",
            url:"SERVVerificar",
            data:"&action=verificarConductor&"+palabra+"="+palabra_buscar,
            success: function(data){
              if(data.estado === "ok")
              {                     
                console.log(data.mensaje);
                    if(data.mensaje === "existe"){
                    alert(mensaje);                 
                    if(tipo === 1){
                        $("#dni").val(null);
                    }
                    if(tipo === 2){
                        $("#email").val(null);
                    }                    
                    if(tipo === 3){
                        $("#licencia").val(null);
                    }                    
                   }
              }
            },
            beforeSend: function(){
           
            },
            complete: function(){

            }
        });
    }    
    
    $('#registrar').click(function (){
        var dni = $('#dni').val();
        var nombre = $('#nombre').val();
        var apellido = $('#apellido').val();        
        var licencia = $('#licencia').val();   
        var email = $('#email').val();                 
        var celular = $('#telefono').val();  
        cadena = "^[A-DF-Z]{1}[A-Z0-9]{2}[0-9]{3}"; 
        re = new RegExp(cadena);        
        
        if(  dni === null || dni.length === 0 || /^\s+$/.test(dni)  ){
            alert('[AVISO] El dni no puede quedar vacío');
            $('#dni').css("border", "1px solid red");
            $("#dni").focus();   
            return false;
        }
        if(  !(dni.length === 8)|| /^\s+$/.test(dni) ){
            alert('[AVISO] El dni debe tener un valor de 8 dígitos'); 
            $('#dni').css("border", "1px solid red");
            $("#dni").focus();               
            return false;
        }          
        else{
             $('#dni').css("border", "");
        }        
        if (nombre === null || nombre.length === 0 || /^\s+$/.test(nombre) ) {
            alert('[AVISO] El nombre no puede quedar vacio');
            $('#nombre').css("border", "1px solid red");
            $("#nombre").focus();               
            return false;
        }
        if (!isNaN(nombre) || /^\s+$/.test(nombre) ) {
            alert('[AVISO] El nombre no puede tener números');
            $('#nombre').css("border", "1px solid red");
            $("#nombre").focus();                
            return false;
        }        
        if(!(nombre.length <=50) || /^\s+$/.test(nombre)){
            alert('[AVISO] El nombre no puede exceder los 50 caracteres');
            $('#nombre').css("border", "1px solid red");
            $("#nombre").focus();                   
            return false;
        }         
        else{            
            $('#nombre').css("border", "");
        }        
        if (apellido === null || apellido.length === 0 || /^\s+$/.test(apellido) ) {
            alert('[AVISO] El apellido no puede quedar vacio');
            $('#apellido').css("border", "1px solid red");
            $("#apellido").focus();               
            return false;
        }
        if (!isNaN(apellido) || /^\s+$/.test(apellido) ) {
            alert('[AVISO] El apellido no puede tener números');
            $('#apellido').css("border", "1px solid red");
            $("#apellido").focus();                
            return false;
        }        
        if(!(apellido.length <=50) || /^\s+$/.test(apellido)){
            alert('[AVISO] El apellido no puede exceder los 50 caracteres');
            $('#apellido').css("border", "1px solid red");
            $("#apellido").focus();                   
            return false;
        }         
        else{            
            $('#apellido').css("border", "");
        }
        if (!(licencia.match(re)) || /\s+$/.test(licencia)){
            alert('[AVISO] Ingrese una licencia con formato adecuado');
            $('#licencia').css("border", "1px solid red");
            $("#licencia").focus();                   
            return false;
        }
        else{            
            $('#licencia').css("border", "");
        }        
        if (email ===  null || email.length ===  0 || /^\s+$/.test(email) ) {
            alert('[AVISO] El email no puede quedar vacío');
            $('#email').css("border", "1px solid red");
            $("#email").focus();               
            return false;
        }               
        if (!(/^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i.test(email))) {
            alert('[AVISO] Ingrese un email con formato adecuado');
            $('#email').css("border", "1px solid red");
            $("#email").focus();                  
            return false;
        }
        if(!(email.length <=30) || /^\s+$/.test(email)){
            alert('[AVISO] El email no puede exceder los 50 caracteres');
            $('#email').css("border", "1px solid red");
            $("#email").focus();                  
            return false;
        }
        else{            
            $('#email').css("border", "");
        }          
        if (celular ===  null || celular.length ===  0 || /^\s+$/.test(celular) ) {
            alert('[AVISO] El teléfono celular no puede quedar vacío');
            $('#telefono').css("border", "1px solid red");
            $("#telefono").focus();                     
            return false;
        }           
        if (!(/^\d{9}$/.test(celular)) ) {        
            alert('[AVISO] El telefono celular debe tener 9 digitos');
            $('#telefono').css("border", "1px solid red");
            $("#telefono").focus();               
            return false;
        }
        else{            
            $('#telefono').css("border", "");
        }         
        var answer = confirm('¿Seguro que desea registrar?');
        if (answer)
        {
           console.log('yes');          
           return true;
        }
        else
        {
           console.log('cancel');
           alert('Ha evitadoregistrar');
           return false;
        }          
    }); 
    $('#limpiar').click(function (){      
        var answer = confirm('¿Seguro que desea limpiar los datos del registro?');
        if (answer)
        {
           console.log('yes');
           return true;
        }
        else
        {
           console.log('cancel');
           alert('Ha evitado limpiar');
           return false;
        }    
    });     
    $('#regresar').click(function (){      
        var answer = confirm('¿Seguro que desea salir del registro?');
        if (answer)
        {
           console.log('yes');
           return true;
        }
        else
        {
           console.log('cancel');
           alert('Ha evitado regresar');
           return false;
        }    
    });   
});