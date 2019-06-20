
$(document).ready(function(){    
    
    //Transforma las letras en minuscula
    $("#emailLogin").keyup(function() {
       $(this).val($(this).val().toLowerCase());
    });
    
    //Busca el corero y contraseña de manera asincrona
    $("#emailLogin, #contraLogin").keyup(function(){            
        var email = $('#emailLogin').val();
        var contra = $('#contraLogin').val();       
        verificar_ajax(email,contra);             
        
            $("#ReportarEmail").text(null);
            $('#ReportarEmail').css("color", null);
    }); 

    // si el valor es 1 entonces se puede ingresar
    function verificar_ajax(email,contra){             
        $.ajax({
            type:"POST",
            dataType:"JSON",
            url:"SERVVerificar",
            data:"&action=verificarLogin&email="+email+"&contra="+contra,
            success: function(data){

                if(data.estado === "ok")
                {                                        
                    if(data.mensaje === "existe"){
                  //                      console.log(data.mensaje);
                   //         console.log(data.estado);
                        $("#ReportarEmailLogin").val(1);  
                      //  console.log( $("#ReportarEmailLogin").val());
                    }
                    else {
                        $("#ReportarEmailLogin").val(2);
                       //   console.log("ñibre "+ $("#ReportarEmailLogin").val());
                    }
                }
                else{
                     $("#ReportarEmailLogin").val(2);
                     console.log( $("#ReportarEmailLogin").val());
               //     alert("Usuario o contraseña incorrectos");
                }
            },
            beforeSend: function(){
           
            },
            complete: function(){

            }
        });        
    }    
  
    $('#ingresar').click(function (){                 

        var contra = $('#contraLogin').val();
        var email = $('#emailLogin').val(); 
        var repuesta2 = $("#ReportarEmailLogin").val();  
        var color = "#FF0000";

        if (email ===  null || email.length ===  0 || /^\s+$/.test(email) ) {
            $("#ReportarEmail").text("Email vacío");
            $('#ReportarEmail').css("color", color);
            return false;
        }        
        else if ( !(email.length <=50) || /^\s+$/.test(email) ) {
            $("#ReportarEmail").text("Email supera los 50 caracteres permitidos");
            $('#ReportarEmail').css("color", color);
            return false;
        }        
        else if (!(/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i.test(email))) {           
            $("#ReportarEmail").text("Email con formato inadecuado");
            $('#ReportarEmail').css("color", color);          
            return false;
        }
        else if(repuesta2 !== "1"){
            $("#ReportarEmail").text("Usuario o contraseña incorrectos");
            $('#ReportarEmail').css("color", color);                     
            return false;
        }           
        else if (contra ===  null || contra.length ===  0 || /^\s+$/.test(contra) ) {         
            $("#ReportarEmail").text("Contraseña vacía");
            $('#ReportarEmail').css("color", color);                
            return false;
        }
        else if ( !(contra.length <=20) || /^\s+$/.test(contra) ) {
            $("#ReportarEmail").text("Email supera los 20 caracteres permitidos");
            $('#ReportarEmail').css("color", color);                 
            return false;
        }

        return true;
                        
    });
  
});
