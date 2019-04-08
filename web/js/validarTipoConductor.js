
$(document).ready(function (){
    
    //Permite ingresar letras y numeros.
    //Los demás son eliminados segundos de ser escritos
    $('#nom_id').keyup( function () {
        $(this).val($(this).val().toLowerCase());
        if (!/^[a-z0-9]*$/i.test(this.value)) {
            this.value = this.value.replace(/[^a-z0-9]+/ig,"");
        }
    });         
        
    //Convierte a minuscula lo que digitas digito por digito        
    $("#nom_id").keyup(function() {
       $(this).val($(this).val().toLowerCase());
    });    
    
    //Busca el nombre de manera asincrona
    $("#nom_id").keyup(function(){
            $nombre = nombre = $('#nom_id').val();
            $.post("SERVTipoConductor", {nnombre:$nombre}, function(data) {               
                    $("#ReportarNombre").html(data);
            });
    });      
    
    $('#insertar').click(function (){
        var nombre = $('#nom_id').val();
        var respuestaNombre = $('#ReportarNombre').text().trim();
        var condicion = 'Ya existe';             
        cadena = "^[a-z][0-9]"; 
        re = new RegExp(cadena);
        
        if( nombre === null || nombre.length === 0 || /^\s+$/.test(nombre) ) {
            alert('[ERROR] El tipo de conductor no puede quedar vacío');
            return false;              
        }else if(!(nombre.length <=2) || /^\s+$/.test(nombre)){
            alert('[ERROR] El tipo de conductor no puede exceder de los 2 caracteres');
            return false;
        }
        else if (!(nombre.match(re))){
            alert('[ERROR] Sólo se permite una letra seguido de un número');
            return false;
        } 
        else if(respuestaNombre === condicion){
            alert('[ERROR] Ingrese un nombre que no este registrado');
            return false;
        }            
        
          // Si el script ha llegado a este punto, todas las condiciones
          // se han cumplido, por lo que se devuelve el valor true
          return true;     
          
    });    
});
    