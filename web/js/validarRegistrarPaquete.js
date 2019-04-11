$(document).ready(function (){
    
    $('#cantidadSobres').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    });
    
  $('#registrar, #editar').click(function (){
        
        var altura = $('#altura').val();
        var anchura = $('#anchura').val();
        var largo = $('#largo').val();        
        var cantidadSobres  = $('#cantidadSobres').val();
        var peso = $('#peso').val();
        var precio = $('#precio').val();
        var cadena_peso = "^[0-9][0-9]{1}[.]{1}[0-9][0-9]";         
        var cadena = "^[0-2]{1}[.]{1}[0-9][0-9]";         
        var re = new RegExp(cadena);
        var re_peso = new RegExp(cadena_peso);
        var costoPesoKilo = 0.2;              
        
        if (altura === null || altura.length === 0 || /^\s+$/.test(altura) ) {
            alert('[ERROR] La altura no puede quedar vacio');
            return false;
        }          
        else if (!(altura.match(re)) || /\s+$/.test(altura)){
            alert('[ERROR] Ingrese un formato de altura adecuado, formato: 0.00 ');
            return false;
        }              
        else if (anchura === null || anchura.length === 0 || /^\s+$/.test(anchura) ) {
            alert('[ERROR] La anchura no puede quedar vacio');
            return false;
        }        
        else if (!(anchura.match(re)) || /\s+$/.test(anchura)){
            alert('[ERROR] Ingrese un formato de anchura adecuado, formato: 0.00 ');
            return false;
        }  
                
        else if (largo === null || largo.length === 0 || /^\s+$/.test(largo) ) {
            alert('[ERROR] El largo no puede quedar vacio');
            return false;
        }       
        
        else if (!(largo.match(re)) || /\s+$/.test(largo)){
            alert('[ERROR] Ingrese un formato de largo adecuado, formato: 0.00 ');
            return false;
        }                
        else if (cantidadSobres === null || cantidadSobres.length === 0 || /^\s+$/.test(cantidadSobres) ) {
            alert('[ERROR] La cantidad no puede quedar vacio');
            return false;
        }    
        else if (cantidadSobres >=20 ||  /^\s+$/.test(cantidadSobres) ) {
            alert('[ERROR] La cantidad no puede superar las 20 unidades');
            return false;
        }                            
        else if (peso === null || peso.length === 0 || /^\s+$/.test(peso) ) {
            alert('[ERROR] El peso no puede quedar vacio');
            return false;
        }    
        else if (!(peso.match(re_peso)) || /\s+$/.test(re_peso)){
            alert('[ERROR] Ingrese un formato de peso adecuado, formato: 00.00 ');
            return false;
        }          
    
        var pesoVolumetrico = Math.ceil(parseFloat((parseFloat(anchura) * parseFloat(altura) * parseFloat(largo))/10));                          
       
        if(pesoVolumetrico > peso){
            precio = pesoVolumetrico*costoPesoKilo*cantidadSobres;
            alert('Costo Volumétrico');
            $('#result').html('S/.' + precio.toFixed(2));
            $('#precio').val(precio.toFixed(2));
        }
        else{
            precio = peso*costoPesoKilo*cantidadSobres;
            alert('Costo por Peso');           
            $('#result').html('S/.' + precio.toFixed(2));    
            $('#precio').val(precio.toFixed(2));
        }       

        return true;
    });
    
});