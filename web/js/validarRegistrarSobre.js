$(document).ready(function (){
    
    $('#cantidadSobres').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    });
    
    
    $('#registrar').click(function (){
        
        var cantidadSobres  = $('#cantidadSobres').val();
        var peso = $('#peso').val();
        var precio = $('#precio').val(10);
        var cadena = "^[0]{1}[.]{1}[0-9]{2}"; 
        var re = new RegExp(cadena);
        
        
        if (cantidadSobres === null || cantidadSobres.length === 0 || /^\s+$/.test(cantidadSobres) ) {
            alert('[ERROR] La cantidad no puede quedar vacio');
            return false;
        }    
        if (cantidadSobres >=20 ||  /^\s+$/.test(cantidadSobres) ) {
            alert('[ERROR] La cantidad no puede superar las 20 unidades');
            return false;
        }    
            
    
        
        else if (peso === null || peso.length === 0 || /^\s+$/.test(peso) ) {
            alert('[ERROR] El peso no puede quedar vacio');
            return false;
        } 

        else if (!(peso.match(re)) || /\s+$/.test(peso)){
            alert('[ERROR] Ingrese un formato de peso adecuado, ejemplo: 0.10 ');
            return false;
        }  
        var cambio = Number(peso.toFixed(2));
            alert('Precio: S/. 10.00');
            
       var peso = $('#peso').val(cambio);

        return true;
    });
    
});