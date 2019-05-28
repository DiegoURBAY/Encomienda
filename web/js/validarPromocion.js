jQuery(function ($) {
    
    //solo numeros
    $('#promocionCliente, #promocionEmpresa').keypress(function(e) {    
        
	if(isNaN(this.value + String.fromCharCode(e.charCode))) 
            return false;
        })
        .on("cut copy paste",function(e){
            e.preventDefault();
    });
    
    
    $('#promocionCliente, #promocionEmpresa').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    }); 
    
    $('#promocionCliente, #promocionEmpresa').change(function() {
        var cliente = $("#promocionCliente").val();
        var empresa = $("#promocionEmpresa").val();
        if(cliente > 100){            
            alert("[Aviso] El descuento no puede ser mayor que 100");
            $("#promocionCliente").val($("#promocionClienteOriginal").val());                
            return false;
        }
        else{
            $("#promocionCliente").css("border", "");
        }  
        if(empresa > 100){            
            alert("[Aviso] El descuento no puede ser mayor que 100");
            $("#promocionEmpresa").val($("#promocionEmpresaOriginal").val());                
            return false;
        }
        else{
            $("#promocionCliente").css("border", "");
        }          
    });
    
    //$("#promocionEmpresa").prop('disabled', true);
    //$("#promocionCliente").prop('disabled', true);
    $("#formulario_empresa").hide();
    $("#formulario_cliente").hide();

        $("input[type=radio]").click(function(event){
        var valor = $(event.target).val();
        if(valor === "empresa"){
            $("#div1").show();
            $("#div2").hide();       
            buscar_empresa();
        } else if (valor === "cliente") {
            $("#div2").show();
            $("#div1").hide();    
            buscar_cliente();
        } 
    });
    
    function buscar_empresa(){
        $("#formulario_empresa").show();
        $("#formulario_cliente").hide();             
        var tipo = "empresa"; 
        buscar(tipo);               
    };
    
    function buscar_cliente(){
        $("#formulario_cliente").show();              
        $("#formulario_empresa").hide();            
        var tipo = "cliente"; 
        buscar(tipo);
    };
        
    function buscar (tipo){
        $.ajax({
            type:"GET",
            url:"SERVPromocion",
            dataType:"JSON",
            data:"&action=buscar&tipo="+tipo,
            success:function(data){
            if(data.estado === "ok")
            {
                var objeto = JSON.parse(data.mensaje);  
      //          console.log(objeto);
                if(tipo === "empresa"){
                    $("#promocionEmpresa").val(objeto.porcentaje);
                    $("#promocionEmpresaOriginal").val(objeto.porcentaje);
                }
                else{
                    $("#promocionCliente").val(objeto.porcentaje);
                    $("#promocionClienteOriginal").val(objeto.porcentaje);
                }
            }
            else{

            }
        },
        beforeSend:function(){

        },
        complete:function(){

        }
        });        
    }

    $("#editarEmpresa").click(function (){
  //      alert("empresa");
        var tipo = "empresa"; 
        
        var formulario = $("#formulario_empresa");
        if(formulario.find("input[name='txtPromocion']").val().trim() === "")
        {
            alert("[Aviso] El descuento no puede quedar vacío");
            $("input[name='txtPromocion']").css("border", "1px solid red");
            $("input[name='txtPromocion']").focus();                
            return false;
        }
        else{
            $("input[name='txtPromocion']").css("border", "");
        }   
        
        var answer = confirm('¿Seguro que desea editar el descuento?');
        if (answer)
        {
           console.log('yes');        
            $("#promocionEmpresaOriginal").val($("#promocionEmpresa").val());
            editar (tipo, formulario);
        }
        else
        {
           console.log('cancel');
           $("#promocionEmpresa").val($("#promocionEmpresaOriginal").val());
           alert('Ha evitado editar');
           return false;
        }          
    });    
    
    $("#editarCliente").click(function (){
//        alert("clien");
        var tipo = "cliente";         
        var formulario = $("#formulario_cliente");
        
        if(formulario.find("input[name='txtPromocion']").val().trim() === "")
        {
            alert("[Aviso] El descuento no puede quedar vacío título");
            $("input[name='txtPromocion']").css("border", "1px solid red");
            $("input[name='txtPromocion']").focus();                
            return false;
        }
        else{
            $("input[name='txtPromocion']").css("border", "");
        }       
        
        var answer = confirm('¿Seguro que desea editar el descuento?');
        if (answer)
        {
           console.log('yes');        
           $("#promocionClienteOriginal").val($("#promocionCliente").val());
           editar (tipo, formulario);
        }
        else
        {
           console.log('cancel');
           $("#promocionCliente").val($("#promocionClienteOriginal").val());
           alert('Ha evitado editar');
           return false;
        }  
    });
    
    function editar (tipo, formulario){
        
            $.ajax({
                type:"POST",
                url:"SERVPromocion",
                dataType:"JSON",
                data:formulario.serialize()+"&action=edit&tipo="+tipo,
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

    