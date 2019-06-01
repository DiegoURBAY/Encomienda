jQuery(function ($) {
   
    $("#eliminar").click(function (){   
        alert("qwe");
        
        var email = $("#email_eliminar").val();
        
        var answer = confirm('¿Seguro que desea eliminar de la edición?');
        if (answer)
        {
           console.log('yes');
        $.ajax({
            type:"GET",
            dataType:"JSON",
            url:"SERVCliente2",
            data:"&action=delete&email="+email,
            success: function(data){

            },
            beforeSend: function(){

            },
            complete: function(){

           }
       });       
           return true;
        }
        else
        {
           console.log('cancel');
           alert('Ha cancelado la eliminación');
           return false;
        }    
    }); 
});