
$(document).ready(function(){ 

    $('#cantidadSobres').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    });
    
    $('#cantidadSobres').change( function () {
        var ingreso = $("#cantidadSobres").val();
        var precio_unitario = 10;
        try{
            //Calculamos el número escrito:
             if(ingreso >20){
                   var ingreso = $("#cantidadSobres").val(0);
             }
             ingreso = (isNaN(parseFloat(ingreso)))? 0 : parseFloat(ingreso*precio_unitario);
             ingreso = parseFloat(ingreso).toFixed(2);

            $("#precioSobre").val(ingreso);
         }
        //Si se produce un error no hacemos nada
        catch(e) {
            throw e;
        }
      });    
      
      $("#limpiar").click(function() {
          
       var answer = confirm('¿Seguro que desea limpiar el registrar?');
       if (answer)
        {
          console.log('yes');
          
          return true;
        }
        else
        {
          console.log('cancel');
          alert('Ha cancelado la limpieza');
          return false;
        }
      });


          $('#cantidadSobres').change( function () {
               var cantidadSobre = $('#cantidadSobres').val();
            if( cantidadSobre === null || cantidadSobre.length === 0 || /^\s+$/.test(cantidadSobre) ) {
             alert('[Aviso] La cantidad del sobre no puede quedar vacío');
            $('#cantidadSobres').css("border", "1px solid red");
            $("#cantidadSobres").focus();   
              return false;              
        }
        else {
             $('#cantidadSobres').css("border", "");
             return false;   
        }
          });



        $("#editarSobre").click(function() {

        //var origen = $('#origen').val();
      //  var destino = $('#destino').val();
                           
        var answer = confirm('¿Seguro que desea registrar?');
        if (answer)
         {
           console.log('yes');
           return true;
         }
         else
         {
           console.log('cancel');
           alert('Ha cancelado el registro');
           return false;
         }
             
        });
});