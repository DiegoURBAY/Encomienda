
$(document).ready(function (){
        $('#insertar').attr("disabled", true);
                    
        var envio = $('input[name=txtTipoEnvio]:checked').val();

        if (envio === 'sobre') {
            $('#contenedorEnvio').val(envio);
            $('#unit').hide();
            $('#measures').hide();
            $('#convertido').hide();
            $('#letreroConvertir').hide();
            
        }        
        
        //Las imagenes seran interacticas
        $('input[name=txtTipoEnvio]').change(function () {
            var envio = $('input[name=txtTipoEnvio]:checked').val();
            if (envio === 'paquete') {
                alert('Las operaciones se harán tomando el kilogramo');
                $('#contenedorEnvio').val(envio);
                $('#convertido').show(); 
                $('#letreroConvertir').show();
                $('#unit').show();
                $('#measures').show(); 
                $('#result').html('S/.0.00');
                $('#peso').val(null);
                $('#gr-type').hide();
                

            } else if (envio === 'sobre') {
                $('#contenedorEnvio').val(envio);
                $('#convertido').hide();
                $('#unit').hide();
                $('#measures').hide(); 
                $('#convertido').val(null);
                $('#gr-type').show();
            }
        });    
        
    $( "#cantidadSobres" ).blur(function() {
        var val = $('#cantidadSobres').val();
        var tam = val.length;
        for(i = 0; i < tam; i++) {
            if(isNaN(val[i]))
                document.getElementById("cantidadSobres").value = '';
        }
    });       
        
    $('#unit').change(function (){        
        var unidad = $('#unit option:selected').val();        
        var peso = $('#peso').val();
        if(unidad === 'KG'){
            $('#convertido').val(peso);
        }
        else if(unidad === 'GR'){
            peso = peso /1000;
            $('#convertido').val(peso);
        }
       
    }); 
    
     $('#limpiar').click(function () {
          $('#result').html('S/.0.00');    
     });
         
         
        $('#doAction').click(function (e) {
            e.preventDefault();

            var ancho  = $('#width').val();
            var altura = $('#height').val();            
            var largo  = $('#large').val();            
            var unidad = $('#unit').val();
            var peso = $('#peso').val();
            var cantidadSobres  = $('#cantidadSobres').val();
            var pesolimiteSobre = 501;
            var precio;
            var costoSobre = 10;
            var costoPesoKilo = 0.2;
            var envio = $('input[name=txtTipoEnvio]:checked').val();
            var pesoVolumetrico = Math.ceil(parseFloat((parseFloat(ancho) * parseFloat(altura) * parseFloat(largo))/10));
            
    
            if(envio === 'sobre' ){
                if (cantidadSobres === null || cantidadSobres.length === 0 || /^\s+$/.test(cantidadSobres) ) {
                    alert('[ERROR] El cantidad no puede quedar vacio');
                    return false;
                }    
                else if (peso === null || peso.length === 0 || /^\s+$/.test(peso) ) {
                    alert('[ERROR] El peso no puede quedar vacio');
                    return false;
                } 
                
                if(peso < pesolimiteSobre){
                    precio = cantidadSobres*costoSobre;
                    $('#result').html('S/.' + precio.toFixed(2));        
                    $('#resultado').val(precio.toFixed(2));
                }
                else{
                    alert('[ERROR] Si el peso es mayor a 500 gramos entonces no cuenta como sobre');
                    return false;
                }
            }       
            if(envio === 'paquete' ){
                if (cantidadSobres === null || cantidadSobres.length === 0 || /^\s+$/.test(cantidadSobres) ) {
                    alert('[ERROR] El cantidad no puede quedar vacio');
                    return false;
                }                  
                if (peso === null || peso.length === 0 || /^\s+$/.test(peso) ) {
                    alert('[ERROR] El peso no puede quedar vacio');
                    return false;
                }                
                if (ancho === null || ancho.length === 0 || /^\s+$/.test(ancho) ) {
                    alert('[ERROR] El ancho no puede quedar vacio');
                    return false;
                }
                else if (largo === null || largo.length === 0 || /^\s+$/.test(largo) ) {
                    alert('[ERROR] El largo no puede quedar vacio');
                    return false;
                }               
                else if (altura === null || altura.length === 0 || /^\s+$/.test(altura) ) {
                    alert('[ERROR] La altura no puede quedar vacio');
                    return false;
                } 
                if(unidad === 'KG'){
                    alert('Has elegido Kilogramo');
                    alert('Peso Usado: '+peso +' kg.');
                    if(pesoVolumetrico > peso){
                        precio = pesoVolumetrico*costoPesoKilo*cantidadSobres;
                        alert('Costo Volumétrico');
                        $('#result').html('S/.' + precio.toFixed(2));
                        $('#resultado').val(precio.toFixed(2));
                    }
                    else{
                        precio = peso*costoPesoKilo*cantidadSobres;
                        alert('Costo por Peso');           
                        $('#result').html('S/.' + precio.toFixed(2));    
                        $('#resultado').val(precio.toFixed(2));
                    }
                    
                }
                else if(unidad === 'GR'){
                    alert('Has elegido Gramo');
                    peso = peso/1000;
                    alert('Peso Usado: '+peso +' kg.');
                    if(pesoVolumetrico > peso){
                        precio = pesoVolumetrico*costoPesoKilo*cantidadSobres;
                        alert('Costo Volumétrico');
                        $('#result').html('S/.' + precio.toFixed(2));                    
                        $('#resultado').val(precio.toFixed(2));
                    }
                    else{
                        precio = peso*costoPesoKilo*cantidadSobres;
                        alert('Costo por Peso');           
                        $('#result').html('S/.' + precio.toFixed(2));    
                        $('#resultado').val(precio.toFixed(2));
                    }                                       
 
                }  
 
            }                                           
            $('#insertar').attr("disabled", false);
            return true;
        });
    
});
