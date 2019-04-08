
$(document).ready(function (){
            
    //Permite ingresar solo letras y espacio vacios
    //Transforma las letras en minuscula
    // Los demás son eliminados segundos de ser escritos
    $('#nom_id, #ape_id').keyup( function () {
        $(this).val($(this).val().toLowerCase());
        if (!/^[ a-záéíóúüñ]*$/i.test(this.value)) {
            this.value = this.value.replace(/[^ a-záéíóúüñ]+/ig,"");
        }
    });

    //Permite ingresar solo numeros.
    // Los demás son eliminados segundos de ser escritos, incluyendo espacios vacios
    $('#tel_id, #dni_id').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    });

    //Transforma las letras en minuscula
    $("#email_id, #direc_id").keyup(function() {
       $(this).val($(this).val().toLowerCase());
    });

    //Busca el dni de manera asincrona
    $("#dni_id").keyup(function(){
            $dni = $('#dni_id').val();
            $.post("SERVAyudante", {ddni:$dni}, function(data) {               
                    $("#ReportarDni").html(data);
            });
    }); 
    
    //Busca el correo de manera asincrona
    $("#email_id").keyup(function(){
            $email = $('#email_id').val();
            $.post("SERVAyudante", {eemail:$email}, function(data) {               
                    $("#ReportarEmail").html(data);
            });
    });      
    
    var availableTags = [
        "ancón",
        "ate",
        "barranco",
        "breña",
        "carabayllo",
        "chaclacayo",
        "chorrillos",
        "cieneguilla",
        "comas",
        "el agustino",
        "huaycán",
        "independencia",
        "jesús maría",
        "la molina",
        "la victoria",
        "lima",
        "lince",
        "los olivos",
        "lurigancho",
        "lurín",
        "magdalena del mar",
        "miraflores",
        "pachacamac",
        "pucusana",
        "pueblo libre",
        "puente piedra",
        "punta hermosa",
        "punta negra",
        "rímac",
        "san bartolo",
        "san borja",
        "san isidro",
        "san juan de lurigancho",      
        "san juan de miraflores",
        "san luis",
        "san martín de porres",
        "san miguel",
        "santa anita",      
        "santa maría del mar",
        "santa rosa",
        "santiago de surco",
        "surquillo",
        "villa el salvador"   
      ];
    $( "#dist_id" ).autocomplete({
      source: availableTags
    });    
    
    $('#editar').click(function (){                             
        var dni = $('#dni_id').val(); 
        var nombre = $('#nom_id').val(); 
        var ape = $('#ape_id').val();
        var email = $('#email_id').val();           
        var celular = $('#tel_id').val();                  
        var direcion = $('#direc_id').val();
        var distrito = $('#dist_id').val();
        var respuestaDni = $('#ReportarDni').text().trim();
        var respuestaEmail = $('#ReportarEmail').text().trim();        
        var contenedorDni = $('#contenedorDni').val();
        var contenedorEmail = $('#contenedorEmail').val();
        var condicion = 'Libre';   
                    
       if (nombre === null || nombre.length === 0 || /^\s+$/.test(nombre) ) {
            alert('[ERROR] El nombre no puede quedar vacio');
            return false;
            }
        else if (!isNaN(nombre) || /^\s+$/.test(nombre) ) {
            alert('[ERROR] El nombre no puede tener números');
            return false;
            }        
        else if (ape === null || ape.length === 0 || /^\s+$/.test(ape) ) {
            alert('[ERROR] El apellido no puede quedar vacio');
            return false;
        }
        else if (!isNaN(ape) || /^\s+$/.test(ape) ) {
            alert('[ERROR] El apellido no puede tener números');
            return false;
        }                  
        else if( dni === null || dni.length === 0 || /^\s+$/.test(dni) ) {
            alert('[ERROR] El dni no puede quedar vacio.');
            return false;
        }          
        else if(  !(dni.length === 8) || /^\s+$/.test(dni) ) {
            alert('[ERROR] El dni debe tener un valor máximo de 8 dígitos.');
            return false;
        }
        else if((dni !== contenedorDni) && (respuestaDni !== condicion)){            
            alert('[ERROR] El nuevo dni a registrar ya lo tiene otro ayudante');
            return false;
        }           
        else if (email ===  null || email.length ===  0 || /^\s+$/.test(email) ) {
            alert('[ERROR] El email no puede quedar vacío');
            return false;
        }               
        else if (!(/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i.test(email))) {
            alert('[ERROR] Ingrese un email con formato adecuado');
            return false;
        }
        else if((email !== contenedorEmail) && (respuestaEmail !== condicion)){            
            alert('[ERROR] El nuevo email a registrar ya lo tiene otro ayudante');
            return false;
        }         
        else if (!(/^\d{9}$/.test(celular)) ) {
            alert('[ERROR] El celular debe tener 9 digitos');
            return false;
        }      
        else if (direcion === null || direcion.length === 0 || /^\s+$/.test(direcion)) {
            alert('[ERROR] La direccion no puede quedar vacío');
            return false;
        }  
        else if (!(direcion.length <=50)|| /^\s+$/.test(direcion) ) {
            alert('[ERROR] La dirección no puede tener más de 50 dígitos');
            return false;
        }                
        else if (distrito === null || distrito.length === 0 || /^\s+$/.test(distrito)) {
            alert('[ERROR] El distrito no puede quedar vacío');
            return false;
        }  
        return true;        
     });
});
