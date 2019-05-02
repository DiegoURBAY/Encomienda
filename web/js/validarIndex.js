
$(document).ready(function(){    
    
    //Busca el email y la contraseña de manera asincrona
    $("#emailLogin,#contraLogin").keyup(function(){
            $email = $('#emailLogin').val();
            $contra = $('#contraLogin').val();
            $.post("SERVLogin", {eemail2:$email, ccontra2:$contra}, function(data) {            
                    $("#ReportarEmailLogin").hide();
                    $("#ReportarEmailLogin").html(data);                   
            });    
    });      

    //Transforma las letras en minuscula
    $("#emailLogin").keyup(function() {
       $(this).val($(this).val().toLowerCase());
    });
  
    $('#ingresar').click(function (){                 

        var contra = $('#contraLogin').val();
        var email = $('#emailLogin').val(); 
        var respuestaEmail = $('#ReportarEmailLogin').text().trim();
        var condicion = 'Ya existe'; 

         if (email ===  null || email.length ===  0 || /^\s+$/.test(email) ) {
          alert('[ERROR] El email no puede quedar vacio');
          return false;
        }        
        else if ( !(email.length <=50) || /^\s+$/.test(email) ) {
          alert('[ERROR] El email debe tener un valor maximo de 50 digitos');
          return false;
        }        
        else if (!(/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i.test(email))) {
          alert('[ERROR] Ingrese un email con formato adecuado');
          return false;
        }
        else if(respuestaEmail !== condicion){
            alert('[ERROR] Usuario o contraseña incorrectos');
            return false;
        }           
        else if (contra ===  null || contra.length ===  0 || /^\s+$/.test(contra) ) {
          alert('[ERROR] La contraseña no puede quedar vacia');
          return false;
        }
        else if ( !(contra.length <=20) || /^\s+$/.test(contra) ) {
          alert('[ERROR] La contraseña debe tener un valor máximo de 20 dígitos');
          return false;
        }

        return true;
                        
    });
  
});
