jQuery(function ($) {
    
    $('#idencomienda').keyup(function () {
        var encomienda = $('#idencomienda').val();
        if(encomienda.length>=11){              
                $('#idencomienda').val(null);                   
        }                  
        this.value = this.value.replace(/[^0-9]/g,''); 
    });
    
    $('#buscar').click(function (){
        var idEncomienda = $("#idencomienda").val();
        var usuario = $("#usuario").val();
        
            $.ajax({
                type:"GET",
                url:"SERVEnvio",
                dataType:"JSON",
                data:"&action=buscar&encomienda="+idEncomienda+"&usuario="+usuario,
                success:function(data){
                if(data.estado === "ok")
                {
                 //   alert(data.mensaje);
                    console.log(data.mensaje);
                    var objeto = JSON.parse(data.mensaje);  
                    var mensaje_completo = objeto.split(',');
                    $("#div2").show();
                    $("#encomienda").val(mensaje_completo[0]);
                    $("#origen").val(mensaje_completo[1]);
                    $("#destino").val(mensaje_completo[2]);
                    $("#fecha").val(mensaje_completo[3]);
                }
                else{
                    alert(data.mensaje);
             //       $("#div2").hide();    
             //       $("#formulario_empresa")[0].reset();
                }
            },
            beforeSend:function(){

            },
            complete:function(){

            }
           });           
    });
    
    $('#limpiar').click(function (){   
        var answer = confirm('¿Seguro que desea limpiar sus respuestas?');
        if (answer)
        {
            $('input[name="perdida"]').prop('checked', false);       
            $('input[name="tiempo"]').prop('checked', false);      
            $('input[name="daño"]').prop('checked', false);       
            $("#comment").val(null);   
        }
        else
        {
           console.log('cancel');         
           alert('Ha evidato limpiar');
           return false;
        }                  
    });

    $("#insertar").click(function (){
//        alert("clien");

        var formulario = $("#formulario_calificacion");
      
        var opciones1 = document.getElementsByName("perdida");
        var opciones2 = document.getElementsByName("tiempo");
        var opciones3 = document.getElementsByName("daño");
        if(!$("input:radio[name=perdida]").is(':checked')) {  
            alert("[Aviso] Responda la pregunta 1");  
             return false;
        }  
     
        if(!$("input:radio[name=tiempo]").is(':checked')) {  
            alert("[Aviso] Responda la pregunta 2");  
             return false;
        }  
 
        if(!$("input:radio[name=dao]").is(':checked')) {  
            alert("[Aviso] Responda la pregunta 3");  
             return false;
        }  
      
        if($("input:text[name=txtComentario]").val() === null) {  
            alert("[Aviso] Deje un comentario");  
             return false;
        }    
        if(formulario.find('input:radio[name=perdida]:checked').val() === ""){
            alert("[Aviso] La perdida no puede quedar vacío título");
            $('input:radio[name=perdida]:checked').css("border", "1px solid red");
            $('input:radio[name=perdida]:checked').focus();                
            return false;
        }
        if(formulario.find("input[name='dao']").val().trim() === "")
        {
            alert("[Aviso] El descuento no puede quedar vacío");
            $("input[name='dao']").css("border", "1px solid red");
            $("input[name='dao']").focus();                
            return false;
        }
        else{
            $("input[name='dao']").css("border", "");
        }        
      
        var answer = confirm('¿Seguro que desea enviar?');
        if (answer)
        {
           console.log('yes');  
          
           alert('Gracias por enviarnos su opinión');         
           editar (formulario);
        }
        else
        {
           console.log('cancel');
           alert('Ha evitado editar');
           return false;
        }  
    });
    
    function editar (formulario){
        
            $.ajax({
                type:"POST",
                url:"SERVEnvio",
                dataType:"JSON",
                data:formulario.serialize()+"&action=insert",
                success:function(data){
                if(data.estado === "ok")
                {
                    alert(data.mensaje);

                }
                else{
                    alert(data.mensaje);
                }
            },
            beforeSend:function(){

            },
            complete:function(){

            }
            });            
           return true;     
    }
   
});