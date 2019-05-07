  //VARIABLES GENERALES
		//declaras fuera del ready de jquery
    var nuevos_marcadores = [];
    var marcadores_bd= [];
    var mapa = null; //VARIABLE GENERAL PARA EL MAPA
    //FUNCION PARA QUITAR MARCADORES DE MAPA
    function limpiar_marcadores(lista)
    {
        for(i in lista)
        {
            //QUITAR MARCADOR DEL MAPA
            lista[i].setMap(null);
        }
    }
    
     var departamento_array = new Array();
    var departamento_array = [
        "Amazonas",
        "Áncash",
        "Apurímac",
        "Arequipa",
        "Ayacucho",
        "Cajamarca",
        "Callao",
        "Cuzco",
        "Huancavelica",
        "Huánuco",
        "Ica",
        "Junín",
        "La Libertad",
        "Lambayeque",
        "Lima",
        "Loreto",
        "Madre de Dios",
        "Moquegua",
        "Pasco",
        "Piura",
        "Puno",
       "San Martín",
       "Tacna",
       "Tumbes",
       "Ucayali",
       //Incluidos
       "Provincia de Lima",
       "Gobierno Regional de Lima",
       "Departamento de Lima",
       "Municipalidad Metropolitana de Lima"
        ];    
    
$(document).on("ready", function(){
        
        //VARIABLE DE FORMULARIO
        var formulario = $("#formulario");
        //-10.295025, -75.486465
        //-10.156036, -75.127738
        var punto = new google.maps.LatLng(-10.156036, -75.127738);
        var config = {
            zoom:5,
            center:punto,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        mapa = new google.maps.Map( $("#mapa")[0], config );

        google.maps.event.addListener(mapa, "click", function(event){
           var coordenadas = event.latLng.toString();
           
           coordenadas = coordenadas.replace("(", "");
           coordenadas = coordenadas.replace(")", "");
           
           var lista = coordenadas.split(",");
           
           var direccion = new google.maps.LatLng(lista[0], lista[1]);
           //PASAR LA INFORMACI�N AL FORMULARIO
           formulario.find("input[name='titulo']").focus();
           formulario.find("input[name='cx']").val(lista[0]);
           formulario.find("input[name='cy']").val(lista[1]);
           
           var marcador = new google.maps.Marker({
               //titulo:prompt("Titulo del marcador?"),
               position:direccion,
               map: mapa, 
               animation:google.maps.Animation.DROP,
               draggable:false
           });
                   
           //VIDEO 15
           $("#collapseOne").collapse('show');
           $("#collapseTwo").collapse('hide');
           //ALMACENAR UN MARCADOR EN EL ARRAY nuevos_marcadores
           nuevos_marcadores.push(marcador);
           
           var geocoder = new google.maps.Geocoder();
            
            //Con eso borro la dirección que quedó en el anterior clic
            formulario.find("input[name='direc']").val('');
            formulario.find("input[name='departamento']").val('');
            
            geocoder.geocode({'latLng': marcador.getPosition()}, function(results, status) {
               if (status === google.maps.GeocoderStatus.OK) {
                    console.clear();
                    if(results[0]!==null){
                        console.log(results[0]);  
                        if(results[0]['address_components']!==null){
                            var address_components=results[0]['address_components'];
                        //    console.log(address_components);
                            for(var i = 0; i < address_components.length; i++) {
                                 // console.log(address_components[i]);
                                  var departamento= address_components[i].long_name;
     //                               console.log(departamento);
                                    if(departamento_array.includes(departamento)){
                                        
                                         var departamento_lima = new Array();
                                         var departamento_lima = [
                                                    "Provincia de Lima",
                                                    "Departamento de Lima",
                                                    "Gobierno Regional de Lima",
                                                    "Municipalidad Metropolitana de Lima",
                                                    "Callao"
                                         ];
                                             
                                        if(departamento_lima.includes(departamento)){
                                            departamento ="Lima";
                                        }
                                        
                                       formulario.find("input[name='departamento']").val(departamento);
                                       break;
                                    }                                     
                            }
                        }
                        if(results[0]['formatted_address']!==null){
                            var address=results[0]['formatted_address'];
      //                      console.log(address);
                            formulario.find("input[name='direc']").val(address);
                        }                                                       
                    }
               }
            });            
           
           google.maps.event.addListener(marcador, "click", function(){

           });
           
           //BORRAR MARCADORES NUEVOS
           limpiar_marcadores(nuevos_marcadores);
           marcador.setMap(mapa);
           
 
 
 
        });
        $("#btn_grabar").on("click", function(){
            //INSTANCIAR EL FORMULARIO
            var f = $("#formulario");
            
            //VALIDAR CAMPO TITULO
            if(f.find("input[name='titulo']").val().trim() === "")
            {
                alert("[Aviso] Falta título");
                $("input[name='titulo']").css("border", "1px solid red");
                $("input[name='titulo']").focus();                
                return false;
            }
            else{
                $("input[name='titulo']").css("border", "");
            }
            //VALIDAR CAMPO CX
            if(f.find("input[name='cx']").val().trim() === "")
            {
                alert("[Aviso] Falta Coordenada X");
                $("input[name='cx']").css("border", "1px solid red");
                $("input[name='cx']").focus();                     
                return false;
            }
            else{
                $("input[name='cx']").css("border", "");
            }            
            //VALIDAR CAMPO CY
            if(f.find("input[name='cy']").val().trim() === "")
            {
                alert("[Aviso] Falta Coordenada Y");
                $("input[name='cy']").css("border", "1px solid red");
                $("input[name='cy']").focus();                    
                return false;
            }
            else{
                $("input[name='cy']").css("border", "");
            }               
            //VALIDAR DIRECCION
            if(f.find("input[name='direc']").val().trim() === "")
            {
                alert("[Aviso] Falta dirección");
                $("input[name='direc']").css("border", "1px solid red");
                $("input[name='direc']").focus();                
                return false;
            }  
            else{
                $("input[name='direc']").css("border", "");
            }                
            //VALIDAR TELEFONO
            if(f.find("input[name='tel']").val().trim() === "")
            {
                alert("[Aviso] Falta teléfono");
                $("input[name='tel']").css("border", "1px solid red");
                $("input[name='tel']").focus();                
                return false;
            }  
            else{
                $("input[name='tel']").css("border", "");
            }            
                        
            //FIN VALIDACIONES
                        
            var answer = confirm('¿Seguro que desea registrar?');
            if (answer)
             {
               console.log('yes');
                  if(f.hasClass("busy"))
                 {
                     //Cuando se haga clic en el boton grabar
                     //se le marcar� con una clase 'busy' indicando
                     //que ya se ha presionado, y no permitir que se
                     //realiCe la misma operaci�n hasta que esta termine
                     //SI TIENE LA CLASE BUSY, YA NO HARA NADA
                     return false;
                 }
                 //SI NO TIENE LA CLASE BUSY, SE LA PONDREMOS AHORA
                 f.addClass("busy");
                 //Y CUANDO QUITAR LA CLASE BUSY?
                 //CUANDO SE TERMINE DE PROCESAR ESTA SOLICITUD
                 //ES DECIR EN EL EVENTO COMPLETE

                 var loader_grabar = $("#loader_grabar");
                $.ajax({
                    type:"POST",
                    url:"SERVLocal",
                    dataType:"JSON",
                    data:f.serialize()+"&action=insert",
                    success:function(data){
                     if(data.estado === "ok")
                         {
                             loader_grabar.removeClass("label-warning").addClass("label-success")
                             .text("Grabado OK").delay(4000).slideUp();
                             alert(data.mensaje);
                             listar();
                         }
                         else if(data.estado === "error"){
                              alert(data.mensaje);
                         }
                    },
                    beforeSend:function(){
                        //Notificar al usuario mientras que se procesa su solicitud
                        loader_grabar.removeClass("label-success").addClass("label label-warning")
                        .text("Procesando...").slideDown();
                    },
                    complete:function(){
                        //QUITAR LA CLASE BUSY
                        f.removeClass("busy");
                        f[0].reset();
                        //[0] jquery trabaja con array de elementos javascript no
                        //asi que se debe especificar cual elemento se har� reset
                        //capricho de javascript
                        //AHORA PERMITIR� OTRA VEZ QUE SE REALICE LA ACCION
                        //Notificar que se ha terminado de procesar
                    }
                });
                return false;
             }
             else
             {
               console.log('cancel');
               alert('Ha cancelado el registro');
               return false;
             }
           
        });
        //BORRAR
        $("#btn_borrar").on("click", function(){
            var f_eliminar = $("#formulario_eliminar");
            $.ajax({
               type:"GET",
               url:"SERVLocal",
               data:"id="+f_eliminar.find("input[name='id']").val()+"&action=delete",
               dataType:"JSON",
               success:function(data){
                   if(data.estado === "ok")
                    {
                        limpiar_marcadores(nuevos_marcadores);
                        alert(data.mensaje);
                        f_eliminar[0].reset();
                        listar();
                    }
                    else
                    {
                        alert(data.mensaje);
                    }
               },
               beforeSend:function(){
                   
               },
               complete:function(){
                   
               }
            });
        });

        //ACTUALIZAR
        $("#btn_actualizar").on("click", function(){
            
            var answer = confirm('¿Seguro que desea actualizar?');
            if (answer)
             {
                console.log('yes');  
                
                $("#opc_edicion").prop('checked', false); 
                
                var f_eliminar = $("#formulario_eliminar");
                var id = $("input[name='id']").val();
                $.ajax({
                   type:"POST",
                   url:"SERVLocal",
                   data:f_eliminar.serialize()+"&action=update&id="+id,
                   dataType:"JSON",
                   success:function(data){
                       if(data.estado === "ok")
                        {
                            limpiar_marcadores(nuevos_marcadores);
                            alert(data.mensaje);
                            f_eliminar[0].reset();
                            listar();
                        }
                        else
                        {
                            alert(data.mensaje);
                        }
                   },
                   beforeSend:function(){

                   },
                   complete:function(){

                   }
                });              
                             
             }
             else
             {
               console.log('cancel');
               alert('Ha cancelado la actualización');
               return false;
             }                        
        });

        //BUSCAR
        $("#btn_buscar").on("click", function(){
          var palabra_buscar = $("#palabra_buscar").val();
          var select_resultados = $("#select_resultados");

          $.ajax({
            type:"GET",
            dataType:"JSON",
            url:"SERVLocal",
            // data:"palabra_buscar="+palabra_buscar+"&tipo=buscar",
            data:"&action=edit&titulo="+palabra_buscar,
            success: function(data){
              if(data.estado === "ok")
              {
                var objeto = JSON.parse(data.mensaje);
                $.each(objeto, function(i, item){
                  $("<option data-cx='"+item.cx+"' data-cy='"+item.cy+"' value='"+item.id+"'>"+item.titulo+"</option>")
                  .appendTo(select_resultados);
                });
              }

            },
            beforeSend: function(){
              select_resultados.empty();//limpiar ComboBox
            },
            complete: function(){

            }
          });
          return false;
        });

        //CENTRAR EL MARCADOR AL SELECCIONARLO
        $("#select_resultados").on("click, change", function(){
          //PEQUEÑA VALIDACION
          if($(this).children().length<1)
          {
            return false;//NO HACER NADA, AL NO TENER ITEMS
          }
          var cx = $("#select_resultados option:selected").data("cx");
          var cy = $("#select_resultados option:selected").data("cy");
          //Crear variable coordenada
          var myLatLng = new google.maps.LatLng(cx, cy);
          //VARIABLE MAPA
          mapa.setCenter(myLatLng);
        });


        //CARGAR PUNTOS AL TERMINAR DE CARGAR LA P�GINA
        listar();//FUNCIONA, AHORA A GRAFICAR LOS PUNTOS EN EL MAPA
    });
    //FUERA DE READY DE JQUERY
    //FUNCTION PARA RECUPERAR PUNTOS DE LA BD
    function listar()
    {
        //ANTES DE LISTAR MARCADORES
        //SE DEBEN QUITAR LOS ANTERIORES DEL MAPA
       limpiar_marcadores(marcadores_bd);
       var f_eliminar = $("#formulario_eliminar");
       $.ajax({
               type:"GET",
               url:"SERVLocal",
               dataType:"JSON",
               data:"&action=refresh",
               success:function(data){
                   if(data.estado === "ok")
                    {
                        //alert("Hay puntos en la BD");
                        var objeto = JSON.parse(data.mensaje);  
                        console.log(objeto);
                        $.each(objeto, function(i, item){
                            //OBTENER LAS COORDENADAS DEL PUNTO
                            var posi = new google.maps.LatLng(item.cx, item.cy);//bien
                            //CARGAR LAS PROPIEDADES AL MARCADOR
                            var marca = new google.maps.Marker({
                                idMarcador:item.id,
                                position:posi,
                                titulo: item.titulo,
                                cx:item.cx,
                                cy:item.cy,
                                direc: item.direccion,
                                tel:item.telefono,
                                departamento:item.LugarString
                            });                                                        
                            
                            //AGREGAR EVENTO CLICK AL MARCADOR
                            google.maps.event.addListener(marca, "click", function(){
                                $("#collapseOne").collapse('hide');
                                $("#collapseTwo").collapse('show');
                               //alert("Hiciste click en "+marca.idMarcador + " - " + marca.titulo) ;
                               //SOLO MOVER CUANDO SE MARQUE EL CHECKBOX DEL FORMULARIO
                               if($("#opc_edicion").prop("checked"))
                               
                               {
                                    //HACER UN MARCADOR DRAGGABLE
                                    marca.setOptions({draggable: true});

                                    google.maps.event.addListener(marca, 'dragend', function(event) { 
                                        //AL FINAL DE MOVE EL MARCADOR
                                        //ESTE MISMO YA SE ACTUALIZA CON LAS NUEVAS COORDENADAS
                                        //CON EL GEOCODER SE MOSTRARÁ EN ESTE CASO EL NOMBRE DE LA DIRECCIÓN
                                        //alert(marca.position);       
                                        
           var geocoder = new google.maps.Geocoder();
           
                       f_eliminar.find("input[name='direc']").val('');
            f_eliminar.find("input[name='departamento']").val('');
           
            geocoder.geocode({'latLng': marca.getPosition()}, function(results, status) {
               if (status === google.maps.GeocoderStatus.OK) {
                    console.clear();
                    if(results[0]!==null){
                        console.log(results[0]);  
                        if(results[0]['address_components']!==null){
                            var address_components=results[0]['address_components'];
                        //    console.log(address_components);
                            for(var i = 0; i < address_components.length; i++) {
                                 // console.log(address_components[i]);
                                  var departamento= address_components[i].long_name;
     //                               console.log(departamento);
                                    if(departamento_array.includes(departamento)){
                                        
                                         var departamento_lima = new Array();
                                         var departamento_lima = [
                                                    "Provincia de Lima",
                                                    "Departamento de Lima",
                                                    "Gobierno Regional de Lima",
                                                    "Municipalidad Metropolitana de Lima",
                                                    "Callao"
                                         ];
                                             
                                        if(departamento_lima.includes(departamento)){
                                            departamento ="Lima";
                                        }
                                        
                                       f_eliminar.find("input[name='departamento']").val(departamento);
                                       break;
                                    }                                     
                            }
                        }
                        if(results[0]['formatted_address']!==null){
                            var address=results[0]['formatted_address'];
      //                      console.log(address);
                            f_eliminar.find("input[name='direc']").val(address);
                        }                                                       
                    }
               }
            });       
                                                                                
                                        var coordenadas = event.latLng.toString();
                                        coordenadas = coordenadas.replace("(", "");
                                        coordenadas = coordenadas.replace(")", "");
                                        var lista = coordenadas.split(",");
                                        f_eliminar.find("input[name='cx']").val(lista[0]);
                                        f_eliminar.find("input[name='cy']").val(lista[1]);
                                        
                                    } );
                                }
                                else
                                {                                    
                                    f_eliminar.find("input[name='titulo']").val(marca.titulo);
                                    f_eliminar.find("input[name='cx']").val(marca.cx);
                                    f_eliminar.find("input[name='cy']").val(marca.cy);
                                    f_eliminar.find("input[name='direc']").val(marca.direc);
                                    f_eliminar.find("input[name='departamento']").val(marca.departamento);
                                    f_eliminar.find("input[name='tel']").val(marca.tel);
                                    f_eliminar.find("input[name='id']").val(marca.idMarcador);
                                }
                                limpiar_marcadores(nuevos_marcadores);
                            });
                            //AGREGAR EL MARCADOR A LA VARIABLE MARCADORES_BD
                            marcadores_bd.push(marca);
                            //UBICAR EL MARCADOR EN EL MAPA
                            marca.setMap(mapa);
                        });
                    }
                else
                    {
                        alert("NO hay puntos en la BD");
                    }
               },
               beforeSend:function(){
                   
               },
               complete:function(){
                   
               }
           });
           
    $("input[name='tel']").keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,'');         
    });
    $("input[name='tel']").change(function () {
        var tel = $("input[name='tel']").val();
        if(tel.length >= 9 ){
             this.value = this.value.slice(0,9); 
        }
    });     
    }
    //PLANTILLA AJAX    