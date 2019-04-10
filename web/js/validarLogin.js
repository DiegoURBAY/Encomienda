$(document).ready(function (){
    
    //Transforma las letras en minuscula
    $("#email_id").keyup(function() {
       $(this).val($(this).val().toLowerCase());
    });
    
    
 $('#ingresar').click(function (){

        var email = $('#email_id').val();  
        var contra = $('#contraseña').val();  
        var respuestaEmail = $('#ReportarEmail').text().trim();
        var condicion = 'Ya existe';   
                                                
        if (email ===  null || email.length ===  0 || /^\s+$/.test(email) ) {
            alert('[ERROR] El email no puede quedar vacío');
            return false;
        }               
        else if (!(/^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i.test(email))) {
            alert('[ERROR] Ingrese un email con formato adecuado');
            return false;
        }
        else if(!(email.length <=30) || /^\s+$/.test(email)){
            alert('[ERROR] El email no puede exceder los 30 caracteres');
            return false;
        }
        else if(respuestaEmail === condicion){
            alert('[ERROR] Ingrese un email que no este registrado');
            return false;
        }      
        if (contra ===  null || contra.length ===  0 || /^\s+$/.test(contra) ) {
            alert('[ERROR] La Contraseña no puede quedar vacío');
            return false;
        }         
        
        return true;           
     });    
    
});