
jQuery(function ($) {
    
    /*
            
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
            $dni = $('#inputIdentificador').val();
            $.post("SERVCliente", {ddni:$dni}, function(data) {               
                    $("#ReportarRucDni").html(data);
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
            $nombre = $('#inputUserame').val();
            $.post("SERVUsuario", {nnombre:$nombre}, function(data) {               
                    $("#ReportarNombre").html(data);
            });
    });        
    
   */
  
  
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
    $("#inputIdentificador").change(function(){            
        var palabra_buscar = $("#inputIdentificador").val();
        var palabra_original = $("#inputIdentificadorOriginal").val();
        var mensaje = "[AVISO] Identificador a registrar ya existe. Se le devolverá el valor original";        
        var tipo = 1;
        verificar_ajax(tipo,palabra_buscar, palabra_original, mensaje);             
    }); 
    
    //Busca el correo de manera asincrona
    $("#inputEmail").change(function(){
        var palabra_buscar = $("#inputEmail").val();
        var palabra_original = $("#inputEmailOriginal").val();
        var mensaje = "[AVISO] Email a registrar ya existe. Se le devolverá el valor original";                
        var tipo = 2;   
        verificar_ajax(tipo,palabra_buscar, palabra_original, mensaje);
    });     
    
    //Busca el usuario de manera asincrona
    $("#inputUserame").change(function(){
        var palabra_buscar = $("#inputUserame").val();
        var palabra_original = $("#inputUserameOriginal").val();
        var mensaje = "[AVISO] Nombre de usuario a registrar ya existe. Se le devolverá el valor original";                        
        var tipo = 3;            
        verificar_ajax(tipo,palabra_buscar, palabra_original, mensaje);                          
    });
    
    function verificar_ajax(tipo,palabra_buscar, palabra_original, mensaje){
        var palabra  = "";

        if(tipo === 1){
            palabra = "identificador";
        }  
        if(tipo === 2){
            palabra = "email";
        }      
        if(tipo === 3){
            palabra = "usuario";
        }   
        $.ajax({
            type:"GET",
            dataType:"JSON",
            url:"SERVVerificar",
            data:"&action=verificarCliente&"+palabra+"="+palabra_buscar,
            success: function(data){
              if(data.estado === "ok")
              {                     
                console.log(data.mensaje);
                  if(palabra_buscar !== palabra_original){
                       // alert(mensaje);        
                        var color = "";
                        var estado;

                        //    alert(mensaje);   
                        console.log(" buscar: "+palabra_buscar);
                        console.log(" original: "+palabra_original);
                            if(data.mensaje === "existe"){
                                var color = "#FF0000";    
                                estado = 1;
  
                            }
                            else if(data.mensaje === "libre"){
                                var color = "";      
                                estado = 0;
                            }                        
                        
                            if(tipo === 1){
                                $("#ReportarIdentificador").text("[Aviso] Indentificador "+data.mensaje);
                                $("#ReportarIdentificador").val(estado);
                                $('#ReportarIdentificador').css("color", color);
                            }
                            if(tipo === 2){
                                $("#ReportarEmail").text("[Aviso] Email "+data.mensaje);
                                $("#ReportarEmail").val(estado);
                                $('#ReportarEmail').css("color", color);
                            }                    
                            if(tipo === 3){
                                $("#ReportarUsuario").text("[Aviso] Usuario "+data.mensaje);
                                $("#ReportarUsuario").val(estado);
                                $('#ReportarUsuario').css("color", color);
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
    
    /*
    $('#editar').click(function (){
        var opciones = document.getElementsByName("optradio"); 
        var ruc_dni = $('#inputIdentificador').val();
        var nombre = $('#inputNombre').val();
        var usuario = $('#inputUserame').val();
        var email = $('#inputEmail').val();                 
        var celular = $('#inputTelefono').val();
        var contra = $('#inputPassword').val();  
        var respuestaRucDni = $('#ReportarRucDni').text().trim();
        var respuestaEmail = $('#ReportarEmail').text().trim();
         var respuestaUsuario = $('#ReportarNombre').text().trim();
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
        
        if(respuestaRucDni === condicion){
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
        return true;           
     });
     
     */
    
    
    $("#editar").click(function (){
        

        var ruc_dni = $('#inputIdentificador').val();
        var nombre = $('#inputNombre').val();
        var usuario = $('#inputUserame').val();
        var email = $('#inputEmail').val();                 
        var celular = $('#inputTelefono').val();
        var contra = $('#inputPassword').val();  
        var respuestaIdentificador = parseInt($('#ReportarIdentificador').val());
        var respuestaEmail = parseInt($('#ReportarEmail').val());
        var respuestaUsuario = parseInt($('#ReportarUsuario').val());
                             
        
        if(  ruc_dni === null || ruc_dni.length === 0 || /^\s+$/.test(ruc_dni)  ){
            alert('[ERROR] El DNI no puede quedar vacío');
            $("#inputIdentificador").focus();   
            return false;
        }             
        else if(  !(ruc_dni.length === 8)|| /^\s+$/.test(ruc_dni) ){
            alert('[ERROR] El dni debe tener un valor de 8 dígitos.');   
            $("#inputIdentificador").focus();   
            return false;
        }         
        if(respuestaIdentificador === 1){
            alert('[ERROR] Ingrese DNI que no este registrado');
            $("#inputIdentificador").focus();
            return false;
        }      
        else if (nombre === null || nombre.length === 0 || /^\s+$/.test(nombre) ) {
            alert('[ERROR] El nombre no puede quedar vacio');
            $("#inputNombre").focus();
            return false;
        }
        else if (!isNaN(nombre) || /^\s+$/.test(nombre) ) {
            alert('[ERROR] El nombre no puede tener números');
            $("#inputNombre").focus();
            return false;
        }
        else if(!(nombre.length <=50) || /^\s+$/.test(nombre)){
            alert('[ERROR] El nombre no puede exceder los 50 caracteres');
            $("#inputNombre").focus();
            return false;
        }        
        else if (email ===  null || email.length ===  0 || /^\s+$/.test(email) ) {
            alert('[ERROR] El email no puede quedar vacío');
            $("#inputEmail").focus();
            return false;
        }               
        else if (!(/^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i.test(email))) {
            alert('[ERROR] Ingrese un email con formato adecuado');
            $("#inputEmail").focus();
            return false;
        }
        else if(!(email.length <=30) || /^\s+$/.test(email)){
            alert('[ERROR] El email no puede exceder los 50 caracteres');
            $("#inputEmail").focus();
            return false;
        }
        else if(respuestaEmail === 1){
            alert('[ERROR] Ingrese un email que no este registrado');
            $("#inputEmail").focus();
            return false;
        }        
        else if (celular ===  null || celular.length ===  0 || /^\s+$/.test(celular) ) {
            alert('[ERROR] El teléfono celular no puede quedar vacío');
            $("#inputTelefono").focus();
            return false;
        }           
        else if (!(/^\d{9}$/.test(celular)) ) {        
            alert('[ERROR] El telefono celular debe tener 9 digitos');
            $("#inputTelefono").focus();
            return false;
        }        
        
        if( usuario === null || usuario.length === 0 || /^\s+$/.test(usuario) ) {
            alert('[ERROR] El campo usuario no puede quedar vacío');
            $("#inputUserame").focus();
            return false; 
              
        }else if(!(usuario.length <=25) || !(usuario.length >=3) || /^\s+$/.test(usuario)){
            alert('[ERROR] El usuario debe tener entre 3 a 25 letras');
            $("#inputUserame").focus();
            return false;
        }
        
        else if(respuestaUsuario === 1){
            alert('[ERROR] Ingrese un usuario que no este registrado');
            $("#inputUserame").focus();
            return false;
        }
        if (contra ===  null || contra.length ===  0 || /^\s+$/.test(contra) ) {
            alert('[ERROR] La Contraseña no puede quedar vacío');
            $("#inputPassword").focus();
            return false;
        }
        
        var answer = confirm('¿Seguro que desea registrar?');
        if (answer)
        {                             
            console.log('yes');
            var f = $("#formulario_editar_cliente");
            $.ajax({
                type:"POST",
                //url:"EJEMPLO",
                url:"SERVCliente2",
                dataType:"JSON",
                data:f.serialize()+"&action=edit",
            //    data: {ruc_dni: ruc_dni, nombre: nombre, usuario:usuario, email:email, celular:celular, contra:contra },
                success:function(data){
                   
                    if(data.estado === "ok")
                    {
                        //alert(data.mensaje);
                        alert("Éxito al editar.");
                        console.log(data.mensaje);
                        correo(f);
                    }

                    else if(data.estado === "error"){
                         alert(data.mensaje);
                    }

                },
                beforeSend:function(){

                },
                complete:function(){

                }
            });
             return false;
        }
        else
        {
            console.log('cancel');
            alert('Ha cancelado el registro');
            return false;
        }
    });
    
    function correo(f)
    {        
       var email = f.find("input[name='txtEmail']").val();
       $.ajax({
            type:"POST",
            url:"SERVCliente2",
            dataType:"JSON",
            data:f.serialize()+"&action=edito&email="+email,
            success:function(data){
                if(data.mensaje !==null){
                    alert("Se ha enviado a su correo un aviso de edición. Regresará a la página de inicio");
                  //  $(location).attr('href',"http://localhost:9090/Encomienda/SERVCliente2&action=cerrar");
                    location.href = "http://localhost:9090/Encomienda/SERVCliente2?action=cerrar";
                }
            },
            beforeSend:function(){
            },
            complete:function(){
               
            }
        });        
    }    
    
   function index()
    {     
        $.ajax({
            type:"GET",
            url:"SERVCliente2",
            dataType:"JSON",
            data:"&action=cerrar"
        }); 
    }        
    
    $('#regresar').click(function (){   
        var answer = confirm('¿Seguro que desea salir de la edición?');
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
    
    
    //onclick="return confirm('¿Seguro que desea salir de la edición?')"
});
