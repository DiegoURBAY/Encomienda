$(document).ready(function (){
    
    $('#cantidadSobres').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    });
    
   
    $('#registrar, #editar ').click(function (){
        
        var cantidadSobres  = $('#cantidadSobres').val();
        var peso = $('#peso').val();
        var precio = $('#precio').val(10);
        var cadena = "^[0]{1}[.]{1}[0-4][0-9]"; 
        var re = new RegExp(cadena);               
        
        if (cantidadSobres === null || cantidadSobres.length === 0 || /^\s+$/.test(cantidadSobres) ) {
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

        else if (!(peso.match(re)) || /\s+$/.test(peso)){
            alert('[ERROR] El peso debe ser menor que 0.50, formato: 0.00 ');
            return false;
        }  

        alert('Precio: S/. 10.00');
            


        return true;
    });
    
});