    
    var chart1, chart2;
    var dateToday = new Date();    
    
    var mes_array = new Array();
    var tipo_encomienda;    
    var identificador_array = new Array();

jQuery(function ($) {
     $("#div2").hide();
     //el div 4 esta dentro de 2
     $("#div4").hide();
    //Cuando inicie sesion un administrador pueda ver q enomiendas ver
    $("input[type=radio]").click(function(event){
        var valor = $(event.target).val();
        if(valor === "todo"){
            $("#div1").hide();
            $("#div3").hide();
            $("#dni").val(null);
            $("#ruc").val(null);
            $("#ReportarIdentificador").text("");
            $("#ReportarIdentificadorRuc").text("");
        } else if (valor === "cliente") {
            $("#div1").show();   
            $("#div3").hide();  
            $("#ruc").val(null);
            $("#ReportarIdentificadorRuc").val(null);
            $("#ReportarIdentificadorRuc").text("");
            $("#dni").keyup(function(){            
                var palabra_buscar = $('#dni').val();
                var tipo = 1;
                verificar_ajax(tipo,palabra_buscar);             
            }); 
        } else if (valor === "empresa") {
            $("#div3").show();       
            $("#div1").hide();
            $("#dni").val(null);
            $("#ReportarIdentificador").val(null);
            $("#ReportarIdentificador").text("");            
            $("#ruc").keyup(function(){            
                var palabra_buscar = $('#ruc').val();
                var tipo = 2;
                verificar_ajax(tipo,palabra_buscar);             
            }); 
        }         
    });
    
    $('#dni,#ruc').keyup(function () {
        this.value = this.value.replace(/[^0-9]/g,''); 
    }); 
    
        
        
function verificar_ajax(tipo, palabra_buscar){
    
        var palabra = "identificador";        
        var mensaje = "";
        $.ajax({
            type:"GET",
            dataType:"JSON",
            url:"SERVVerificar",
            data:"&action=verificarCliente&"+palabra+"="+palabra_buscar,
            success: function(data){
              if(data.estado === "ok")
              {                     
                console.log(data.mensaje);
                
                var color = "";
                var estado;
                if(data.mensaje === "existe"){                    
                    mensaje = "existe";
                    var color = "#33FF58";    
                    estado = 1;
                }
                else if(data.mensaje === "libre"){
                    mensaje = "no existe";
                    var color = "#FF0000";
                    estado = 0;
                }
                if(tipo === 1){
                    if(palabra_buscar.length <= 8){
                        $("#ReportarIdentificador").text("[Aviso] DNI "+mensaje);
                        $("#ReportarIdentificador").val(estado);
                        $('#ReportarIdentificador').css("color", color);                  
                    }
                    else if(palabra_buscar.length > 8){
                        $("#ReportarIdentificador").text("[Aviso] DNI inválido");
                        $('#ReportarIdentificador').css("color", "#FF0000");                   
                    }
                }
                if(tipo === 2){
                    if(palabra_buscar.length <= 11){
                        $("#ReportarIdentificadorRuc").text("[Aviso] ruc "+mensaje);
                        $("#ReportarIdentificadorRuc").val(estado);
                        $('#ReportarIdentificadorRuc').css("color", color);
                        if(palabra_buscar.length ===8){
                            $("#ReportarIdentificadorRuc").text("[Aviso] ruc no existe");
                            $('#ReportarIdentificadorRuc').css("color", "#FF0000");  
                        }
                    }
                    else if(palabra_buscar.length > 11 ){
                        $("#ReportarIdentificadorRuc").text("[Aviso] ruc inválido");
                        $('#ReportarIdentificadorRuc').css("color", "#FF0000");                     
                    }

                }                     

              }
            },
            beforeSend: function(){
           
            },
            complete: function(){

            }
        });        
    }
    
    $(function() {
        $.datepicker.regional['es'] = {
            closeText: 'Cerrar',
            currentText: 'Hoy',
            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
            'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
            'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié;', 'Juv', 'Vie', 'Sáb'],
            dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'],
            weekHeader: 'Sm',
            dateFormat: 'dd/mm/yy',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: ''
            };
        $.datepicker.setDefaults($.datepicker.regional["es"]);
        $("#from, #to").datepicker({ 
            numberOfMonths: 3, 
            showButtonPanel: true

        }); 
    });     
                       
        $('#btnGraficoFecha').click(function () {     
            
            var from = $('#from').val();
            var to = $('#to').val();
            var dni = $("#dni").val();
            var ruc = $("#ruc").val();
            var respuestaIdentificador = parseInt($('#ReportarIdentificador').val());
            var respuestaIdentificadorRuc = parseInt($('#ReportarIdentificadorRuc').val());
            var eleccion = $('input:radio[name=pago1]:checked').val();

            var identificador = null;
            var color = "#FF0000";
            identificador_array.splice(0, identificador_array.length);
            
            if( from === null || from.length === 0 || /^\s+$/.test(from) ) {
                alert('[Aviso] Ingrese fecha de inicio');
                return false;              
            }
            else if( to === null || to.length === 0 || /^\s+$/.test(to) ) {
                alert('[Aviso] Ingrese fecha final');
                return false;              
            }
                     
            var x = from.split("/");
            var z = to.split("/");

            //Cambiamos el orden al formato americano, de esto dd/mm/yyyy a esto mm/dd/yyyy
            from = x[2] + "-" + x[1] + "-" + x[0];
            to = z[2] + "-" + z[1] + "-" + z[0];

            //Comparamos las fechas
            if (Date.parse(from) > Date.parse(to)){
                alert("[Aviso] La fecha de envio no puede superar la fecha de llegada");
                return false;   
            }   
            if(eleccion ==="cliente"){
                if (dni === null || dni.length === 0 || /^\s+$/.test(dni)){
                    $("#ReportarIdentificador").text("[Aviso] DNI vacío");
                    $('#ReportarIdentificador').css("color", color);   
                    return false;   
                }   
                if (dni.length !== 8){
                    $("#ReportarIdentificador").text("[Aviso] DNI inválido");
                    return false;   
                }                                
                if(respuestaIdentificador === 0){
                    //EN EL ajax verificar ya genera esta alerta
                    //alert('[ERROR] Ingrese un DNI existente');
                    $("#inputIdentificador").focus();
                    return false;
                }               
                identificador =  $("#dni").val();
                identificador_array.push(identificador);
            }
            else if(eleccion ==="empresa"){
                if (ruc === null || ruc.length === 0 || /^\s+$/.test(ruc)){
                    $("#ReportarIdentificadorRuc").text("[Aviso] ruc vacío");
                    $('#ReportarIdentificadorRuc').css("color", color);                    
                    return false;   
                }   
                if (ruc.length !== 11){
                    $("#ReportarIdentificadorRuc").text("[Aviso] ruc inválido");
                    $('#ReportarIdentificadorRuc').css("color", color);
                    return false;   
                }                
                if(respuestaIdentificadorRuc === 0){
                    //EN EL ajax verificar ya genera esta alerta
                    //alert('[ERROR] Ingrese un ruc existente');
                    $("#inputIdentificadorRuc").focus();
                    return false;
                }
                identificador =  $("#ruc").val();
                identificador_array.push(identificador);
            }
            $("#chartdiv4").empty();
            $("#div4").hide();
                       
            getGraficoBarrasFecha1(identificador);
        });          
            
            
        $('#btnExportChartsPDF').click(function (){                                     
            //fc_export_pdf(); 
            fc_export_pdf();
        });            
        $('#btnExportChartsXSXL').click(function (){                                     
            //fc_export_pdf(); 
           exportXLSX2();
        });       
    });    
           
   getGraficoBarrasFecha1 = function (identificador) {       
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();

        $.ajax({
                type: "POST",           
                url: 'SERVReporte',
                data: "&action=listarEncomiendaPorFecha&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&dni="+identificador,
             //   data: busqueda,
                dataType: 'json',
                success: function (data) {
                    if(data.estado === "ok"){
                        if(data.mensaje !== "vacio"){                 
                            console.log(data.mensaje);
                             alert("¡Hay encomiendas! No olvide dar clic al primer reporte para ver más detalles");
                            $("#div2").show();
                            _private.setBarrasFecha1(data, fecha_inicio, fecha_final, identificador); 
                                                                                                  
                        }else{
                            alert("No hay encomiendas");   
                     //    $("#div2").hide();
                         
                        }
                        
                    }
                    else{
                        alert("No hay encomiendas");          
                    }
                     
                },
                complete:function(){
                   
                    getGraficoPieFecha1(identificador);
                }
        });                
    }; 
      
   getGraficoBarrasFecha2 = function (tipo, mes, identificador) {       
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();

        var url = "";
        if(identificador === null ){
            //no hay identificador y no hay mes
            if(mes === null){
                url = "&action=listarEncomiendaPorMes&tipo="+tipo+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final;
            //no hay identificador pero si mes    
            }else{
                url = "&action=listarEncomiendaPorMes&tipo="+tipo+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&mes="+mes;
            }
        }
        //hay identificador
        else{
            //hay identificador pero no mes
            if(mes === null){
                url = "&action=listarEncomiendaPorMes&tipo="+tipo+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&dni="+identificador;
            //hay identificador y mes
            }else{
                url = "&action=listarEncomiendaPorMes&tipo="+tipo+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&dni="+identificador+"&mes="+mes;                
            }            
            
        }

    //     alert("2 .-Hay encomiendas del mes: "+mes+" tipo: "+tipo+" iden: "+identificador);
       $.ajax({
                type: "POST",           
                url: 'SERVReporte',
                //data: "&action=listarEncomiendaPorMes&tipo="+tipo+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&dni="+identificador+"&mes="+mes,
               data: url,
                dataType: 'json',
                success: function (data) {
                    if(data.estado === "ok"){
                        if(data.mensaje !== "vacio"){                 
                            console.log(data.mensaje);
                            alert("Generado reporte detallado");
                            $("#div4").show();
                            _private.setBarrasFecha2(data, tipo, mes, fecha_inicio, fecha_final, identificador); 
                                                                                                  
                        }else{
                //            alert("No hay encomiendas del mes "+mes);   
                     //    $("#div2").hide();
                         
                        }
                        
                    }
                    else{
                //        alert("No hay encomiendas del mes "+mes);   
                    }
                     
                },
                complete:function(){
                                   
                }
        });                
    };      
      
   getGraficoPieFecha1 = function (identificador) {       
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();
  //      var dni = $("#dni").val();
   //     var ruc = $("#ruc").val();
        
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            data: "&action=listarEncomiendaPorFecha2&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&dni="+identificador,
            dataType: 'json',
            success: function (data) {
                if(data.estado === "ok"){
                    if(data.mensaje !== "vacio"){                                           
                        console.log(data.mensaje);                        
                        _private.setPie1(data, fecha_inicio, fecha_final, identificador);                  

                    }else{
                     alert("No hay encomiendas");                          
                    }

                }
                else{
                    alert("No hay encomiendas");          
                }

            },
            complete:function(){
             //   getGraficoLineasFecha1();
            }
        });                
    };
    
    
    //el boton
   exportXLSX2 = function () {       
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();
        var tipo = 1;
        
        var url = "";

        //Saber si existe el grafico detallado
        var tlist_3 = $('#chartdiv4').html().replace(/\s/ig, '').length;
        if(tlist_3 !== 0){
             tipo = 2;
              if(identificador_array.length === 0 ){
                  var ultimo_identificador = identificador_array[identificador_array.length - 1];
                  
                    //Si existe, saber que tipo de grafico es             
                    if(mes_array.length === 0){
                        console.log("OPCION 1, long mes: "+mes_array.length);
                        console.log("OPCION 1, long dni: "+identificador_array.length);
                        url = "&action=generarEncomienda&tipo="+tipo+"&encomienda="+tipo_encomienda+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&dni="+ultimo_identificador;
                    //no hay identificador pero si mes    
                    }else{
                        console.log("OPCION 2, long: "+mes_array.length);
                        console.log("OPCION 2, long dni: "+identificador_array.length);
                        var ultimo = mes_array[mes_array.length - 1];                        
                        console.log(ultimo);
                        url = "&action=generarEncomienda&tipo="+tipo+"&encomienda="+tipo_encomienda+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&mes="+ultimo+"&dni="+ultimo_identificador;
                    }   
              }
              else{           
                    //Si existe, saber que tipo de grafico es             
                    if(mes_array.length === 0){
                        console.log("OPCION 3, long: "+mes_array.length);
                         console.log("OPCION 3, long dni: "+identificador_array.length);
                        url = "&action=generarEncomienda&tipo="+tipo+"&encomienda="+tipo_encomienda+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final;                       
                    //no hay identificador pero si mes    
                    }else{
                        console.log("OPCION 4, long: "+mes_array.length);
                          console.log("OPCION 4, long dni: "+identificador_array.length);
                        var ultimo = mes_array[mes_array.length - 1];
                        console.log(ultimo);
                        url = "&action=generarEncomienda&tipo="+tipo+"&encomienda="+tipo_encomienda+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&mes="+ultimo;
                    }                     
              }
              
        }
        else{
            if(identificador_array.length === 0 ){                
                 console.log("OPCION 5, long: mes"+mes_array.length);
                          console.log("OPCION 5, long dni: "+identificador_array.length);
                url ="&action=generarEncomienda&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final;
            }else{
                  var ultimo_identificador = identificador_array[identificador_array.length - 1];
                   console.log("OPCION 6, long: "+mes_array.length);
                          console.log("OPCION 6, long dni: "+identificador_array.length);
                url ="&action=generarEncomienda&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&dni="+ultimo_identificador;;
            }

        }
       
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            //data: "&action=generarIngresos&tipo="+tipo+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final,
            data: url,
            dataType: 'json',
            success: function (data) {
                if(data.estado === "ok"){
                                 
                    alert(data.mensaje);
                }
                else{
                    alert(data.mensaje);          
                }

            },
            complete:function(){
             //   getGraficoLineasFecha1();
            }
        });                
    };        
    
    var _private = {};
    var chart = {};    
    
    
    _private.setBarrasFecha1 = function (data, fecha_inicio, fecha_final, identificador) {
        var objeto = JSON.parse(data.mensaje);  
        
        //sin esta variable el valor que tenga identificador será null, a pesar que no lo sea
        var identificador2 = identificador;
        
        for(var i=0; i< objeto.length; i++){
               delete objeto[i].total;               
        }
       
        var arreglado = objeto.map( item => { 
            return { mes: item.tiempo , sobre: item.sobre, paquete: item.paquete }; 
        });
  
        console.log(arreglado);                         
      
        var titulo = "";
        if(identificador !==null){
            if(identificador.length <= 8 && identificador.length > 0){
                titulo = "Reporte de cantidad de encomiendas realizadas según el tipo desde "+fecha_inicio+" hasta "+fecha_final+" del DNI "+identificador;
            }else  if(identificador.length > 8){
                titulo = "Reporte de cantidad de encomiendas realizadas según el tipo desde "+fecha_inicio+" hasta "+fecha_final+" del RUC "+identificador;
            }
            
        }else{
            titulo = "Reporte de cantidad de encomiendas realizadas según el tipo desde "+fecha_inicio+" hasta "+fecha_final;
        }

        chart = AmCharts.makeChart("chartdiv1", {
            
            "type": "serial",
            "theme": "light",
            "titles": [{
                    "text": titulo,
                    "size": 14
                }],              
            "legend": {
                "horizontalGap": 10,
                "maxColumns": 1,
                "position": "right",
                "useGraphSettings": true,
                "markerSize": 10
            },
            "dataProvider": arreglado,
            "graphs": [{
                "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                "fillAlphas": 0.8,
                "labelText": "[[value]]",
                "lineAlpha": 0.3,
                "title": "paquete",
                "type": "column",
                "color": "#000000",
                "valueField": "paquete",
                "listeners": [{
                  "event": "clickGraphItem",
                  "method": paquete
                }]

            }, {
                "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                "fillAlphas": 0.8,
                "labelText": "[[value]]",
                "lineAlpha": 0.3,
                "title": "sobre",
                "type": "column",
                "color": "#000000",
                "valueField": "sobre",
                "listeners": [{
                  "event": "clickGraphItem",
                  "method": sobre
                }]
             
            }],
            "categoryField": "mes",
           "categoryAxis": {
                "gridPosition": "start",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "position": "left",
                "title": "mes" /*,
                "listeners": [{
                  "event": "clickGraphItem",
                  "method": exportXLSX
                }]*/
            },
/*            "listeners": [{
              "event": "clickGraphItem",
              "method": exportXLSX
            }],*/
            "valueAxes": [
                    {
                        "id": "ValueAxis-1",
                        "position": "left",
                        "axisAlpha": 0,
                        "title": "Cantidad"
                    }                
            ],
            "export": {
                "enabled": true,
                "menu": []               
             }
        });   
 /*       
        if($("#dni").val()!==null || $("#dni").val().length !==0){
            identificador = $("#dni").val();
        }
        if($("#ruc").val()!==null || $("#ruc").val().length !==0){
            identificador = $("#ruc").val();
        } 
        else{
            identificador = null;
        }        
   */   
        function paquete(e) {           
            var tipo = "paquete";
            tipo_encomienda = "paquete";
            var objeto_escogido = Object.values(e)[2]; 
            var mes = objeto[objeto_escogido].tiempo;
            //var answer = confirm("¿Seguro que desea ver el reporte de "+tipo+"s del mes de "+mes+" ?");
            var answer = confirm("¿Ver reporte de "+tipo+"s del mes de "+mes+" ? Si cancela verá el reporte con el rango de fechas establecido");
            if (answer)
            {                             
                console.log('yes');
                mes_array.push(mes);
             //   alert('Ha aceptado, mes '+mes+", tipo: "+tipo+", ident: "+identificador2);
                getGraficoBarrasFecha2(tipo, mes, identificador2);
                return false;
            }
            
            else
            {
                console.log('cancel');                
                var mes = null;
                 mes_array.splice(0, mes_array.length);
            //    alert('Ha cancelado, mes '+mes+", tipo: "+tipo);
                getGraficoBarrasFecha2(tipo, mes, identificador2);
      //          return false;
            }             
        }
        function sobre(e) {                     
            var tipo = "sobre";
            tipo_encomienda = "sobre";
            var objeto_escogido = Object.values(e)[2]; 
            var mes = objeto[objeto_escogido].tiempo;
            //var answer = confirm("¿Seguro que desea ver el reporte de "+tipo+"s del mes de "+mes+" ?");
            var answer = confirm("¿Ver reporte de "+tipo+"s del mes de "+mes+" ? Si cancela verá el reporte con el rango de fechas establecido");
            if (answer)
            {                             
                console.log('yes');
                mes_array.push(mes);
         //       alert('Ha aceptado, mes '+mes+", tipo: "+tipo);
                getGraficoBarrasFecha2(tipo, mes, identificador);
                return false;
            }
            else
            {
         //       console.log('cancel');                
                var mes = null;
                mes_array.splice(0, mes_array.length);
          //      alert('Ha cancelado, mes '+mes+", tipo: "+tipo);
                getGraficoBarrasFecha2(tipo, mes, identificador);
          //      return false;
            }  
        }
         /*
 function exportXLSX(e) {
 data: chart.graphs;
   var objeto_escogido2 = Object.values(data);      
      alert("1: "+objeto_escogido2[1]);
      alert("2: "+objeto_escogido2[2]);
        
            //el objeto 2 es el numero del indice del json que muestra el grafico
    var objeto_escogido = Object.values(e)[2];        
        console.log(objeto[objeto_escogido]);
        console.log(objeto[objeto_escogido].tiempo);
        
    var mes = objeto[objeto_escogido].tiempo;
    getGraficoBarrasFecha2(mes, identificador);
    
    //recordar todo los objetos del evento clickGraphItem
    /*

    //toda la data
    data: chart.dataProvider;

      console.log("1: "+data);
    console.log("2: "+Object.values(data));
    
    console.log("3: "+e);
  
    var objetos = Object.values(e);
    for (const prop in objetos) {
        console.log(`obj.${prop} = ${Object.values(e)[prop]}`);
    }
    */

    };
 /*     
    var numero_escogido = Object.values(e)[2];
    console.log(objeto[numero_escogido]);
     
      console.log("Fila "+numero_escogido+" del evento seleccionada");
    console.log(`obj.${numero_escogido} = ${Object.values(e)[numero_escogido]}`);

   */
  
      /*
        function exportXLSX() {
            chart["export"].toXLSX({
                data: chart.dataProvider
            }, function(data) {
                this.download(data, this.defaults.formats.XLSX.mimeType, "ReporteCantidadTipoEncomiendaDesde"+from+"Hasta"+to+".xlsx");
            });
        }    
     
   };
*/ 

    _private.setBarrasFecha2 = function (data, tipo, mes, fecha_inicio, fecha_final, identificador)  {
        
        var objeto = JSON.parse(data.mensaje);  
        
        for(var i=0; i< objeto.length; i++){
               delete objeto[i].total;               
        }
            
        var arreglado = objeto.map( item => { 
            return { fecha: item.tiempo , empresa: item.sobre, persona: item.paquete }; 
        });
  
        console.log(arreglado);                         
      
        var titulo = "";
        if(identificador !==null && identificador.length !== 0){
            if(identificador.length <= 8 && identificador.length > 0){
                
                if(mes !== null){
                    titulo = "Reporte de cantidad de "+tipo+"s realizados durante el mes de "+mes+ " del DNI "+identificador;
                }
                else{
                    titulo = "Reporte de cantidad de "+tipo+"s realizados por tipo de cliente según el tipo desde "+fecha_inicio+" hasta "+fecha_final+" del DNI "+identificador;
                }
            }else  if(identificador.length > 8){
                if(mes !== null){
                    titulo = "Reporte de cantidad de "+tipo+"s realizados durante el mes de "+mes+ " del RUC "+identificador;
                }
                else{                
                titulo = "Reporte de cantidad de "+tipo+"s realizados por tipo de cliente según el tipo desde "+fecha_inicio+" hasta "+fecha_final+" del RUC "+identificador;
                }
            }
            
        }else{
            if(mes !== null){
                titulo = "Reporte de cantidad de "+tipo+"s realizadas por tipo de cliente del mes de "+mes;
            }
            else{
                titulo = "Reporte de cantidad de "+tipo+"s realizadas por tipo de cliente según la fecha desde "+fecha_inicio+" hasta "+fecha_final;
            }
            
        }

        chart = AmCharts.makeChart("chartdiv4", {
            "type": "serial",
            "titles": [{
                    "text": titulo,
                    "size": 14
                }],             
            "theme": "none",              
            "marginRight":30,
            "legend": {
                "equalWidths": false,
                "periodValueText": "total: [[value.sum]]",
                "position": "top",
                "valueAlign": "left",
                "valueWidth": 100
            },
            "dataProvider": arreglado,
            "valueAxes": [{
                "stackType": "regular",
                "gridAlpha": 0.07,
                "position": "left",
                "title": "Cantidad"
            }],
            "graphs": [ {
                "balloonText": "<img src='https://www.amcharts.com/lib/3/images/motorcycle.png' style='vertical-align:bottom; margin-right: 10px; width:28px; height:21px;'><span style='font-size:14px; color:#000000;'><b>[[value]]</b></span>",
                "fillAlphas": 0.6,
                "lineAlpha": 0.4,
                "title": "empresa",
                "valueField": "empresa"
            }, {
                "balloonText": "<img src='https://www.amcharts.com/lib/3/images/bicycle.png' style='vertical-align:bottom; margin-right: 10px; width:28px; height:21px;'><span style='font-size:14px; color:#000000;'><b>[[value]]</b></span>",
                "fillAlphas": 0.6,
                "lineAlpha": 0.4,
                "title": "persona",
                "valueField": "persona"
            }],
            "plotAreaBorderAlpha": 0,
            "marginTop": 10,
            "marginLeft": 0,
            "marginBottom": 0,
            "chartScrollbar": {},
            "chartCursor": {
                "cursorAlpha": 0
            },
            "categoryField": "fecha",
            "categoryAxis": {
                "startOnAxis": true,
                "axisColor": "#DADADA",
                "gridAlpha": 0.07,
                "title": "fechas",
                "labelRotation": 90,
                "guides": [{
                    category: "2001",
                    toCategory: "2003",
                    lineColor: "#CC0000",
                    lineAlpha: 1,
                    fillAlpha: 0.2,
                    fillColor: "#CC0000",
                    dashLength: 2,
                    inside: true,
                    labelRotation: 90,
                    label: "fines for speeding increased"
                }, {
                    category: "2007",
                    lineColor: "#CC0000",
                    lineAlpha: 1,
                    dashLength: 2,
                    inside: true,
                    labelRotation: 90,
                    label: "motorcycle fee introduced"
                }]
            },
            "export": {
                "enabled": true,
                "menu": []
             }
        });          
   };

   
    _private.setPie1 = function (data, fecha_inicio, fecha_final, identificador) {
        var objeto = JSON.parse(data.mensaje);         
        
        for(var i=0; i< objeto.length; i++){
            delete objeto[i].paquete;
            delete objeto[i].sobre;
        }
        
        var arreglado = objeto.map( item => { 
            return { tipo: item.tiempo , total: item.total }; 
        });        
                
        var titulo = "";
   
        if(identificador !==null){
            if(identificador.length <= 8 && identificador.length > 0){
                titulo = "Reporte de cantidad de encomiendas realizadas según el tipo desde "+fecha_inicio+" hasta "+fecha_final+" del DNI "+identificador;
            }else  if(identificador.length > 8){                
                titulo = "Reporte de cantidad de encomiendas realizadas según el tipo desde "+fecha_inicio+" hasta "+fecha_final+" del RUC "+identificador;
            }
            
        }else{
            titulo = "Reporte en % y cantidad de encomiendas realizadas según el tipo desde "+fecha_inicio+" hasta "+fecha_final;
        }        
         chart = AmCharts.makeChart("chartdiv2", {
            "type": "pie",
            "theme": "light",
            "titles": [{
                    "text": titulo,
                    "size": 14
                }],              
            "legend": {
                "horizontalGap": 10,
                "maxColumns": 1,
                "position": "right",
          //      "useGraphSettings": true,
                "markerSize": 10
            },            
            "dataProvider": arreglado,
            "valueField": "total",
            "titleField": "tipo",
            "startEffect": "elastic",
            "startDuration": 2,
            "labelRadius": 15,
            "innerRadius": "50%",
            "depth3D": 10,
            "balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
            "angle": 15,
            "export": {
                "enabled": true,
                "menu": []
            }
        });
    }; 
    
    var charts = {};
    
    function fc_export_pdf()
    {
        try
        {
            // So that we know export was started
            console.log("Starting export...");

            // Define IDs of the charts we want to include in the report
            var ids = [];
            
            var tlist_3 = $('#chartdiv4').html().replace(/\s/ig, '').length;
            if(tlist_3 !== 0){
                 var ids = ["chartdiv1", "chartdiv4", "chartdiv2"];
            }else{
                 
                  var ids = ["chartdiv1", "chartdiv2"];
            }

            // Collect actual chart objects out of the AmCharts.charts array
            var charts = {};
            var charts_remaining = ids.length;
            for (var i = 0; i < ids.length; i++) {
              for (var x = 0; x < AmCharts.charts.length; x++) {
                if (AmCharts.charts[x].div.id === ids[i]){
                        charts[ids[i]] = AmCharts.charts[x];
                    
                }
                  
              }
            }

            // Trigger export of each chart
            for (var x in charts) {
              if (charts.hasOwnProperty(x)) {
                var chart = charts[x];
                chart["export"].capture({}, function() {
                  this.toPNG({}, function(data) {

                    // Save chart data into chart object itself
                    this.setup.chart.exportedImage = data;

                    // Reduce the remaining counter
                    charts_remaining--;

                    // Check if we got all of the charts
                    if (charts_remaining === 0) {
                      // Yup, we got all of them
                      // Let's proceed to putting PDF together
                      fc_generate_pdf();
                      
                    }
                  });
                });
              }
            }

        }
        catch(err)
        {
            alert('Ocurrió un error al exportar.\nConsulte con el administrador.');
            console.log(err.message);
        }
        
     
    function fc_generate_pdf() {                
        
        var from = $('#from').val();
        var to = $('#to').val();      
        var dni = $('#dni').val();    
        var ruc = $('#ruc').val();
        
        var addtext = "";
        
        var momentoActual = new Date();
        
        var hora = momentoActual.getHours(); 
        var minuto = momentoActual.getMinutes(); 
        var segundo = momentoActual.getSeconds(); 
        
        
        if (hora < 10) {hora = "0" + hora;}
        if (minuto < 10) {minuto = "0" + minuto;}
        if (segundo < 10) {segundo = "0" + segundo;}

        var horaImprimible = hora + ":" + minuto + ":" + segundo;
        

        var month = momentoActual.getMonth()+1;
        var day = momentoActual.getDate();

        var fecha_actual = (day<10 ? '0' : '') + day +'/'+ (month<10 ? '0' : '') + month + '/'+ momentoActual.getFullYear();
        
        var titulo = "";
        if(dni !==null && dni.length !== 0){
            titulo = "Encomienda_Reporte_DNI_"+dni+".pdf";
                    addtext = "Zurita Sac.\n\
        \n\
\n\
        Fecha y hora de exportación: "+fecha_actual+ " "+horaImprimible+ "\n\
\n\
\n\
\n\
            REPORTE  DE  ENCOMIENDAS  REALIZADAS  DESDE:  " + from + "   HASTA:  " + to+" DEL DNI: "+dni;
            
        }else if(ruc !==null && ruc.length !== 0){
                        titulo = "Encomienda_Reporte_RUC_"+dni+".pdf";
                    addtext = "Zurita Sac.\n\
        \n\
\n\
        Fecha y hora de exportación: "+fecha_actual+ " "+horaImprimible+ "\n\
\n\
\n\
\n\
            REPORTE  DE  ENCOMIENDAS  REALIZADAS  DESDE:  " + from + "   HASTA:  " + to+" DEL RUC: "+ruc;
        }
        else{
            titulo = "Encomienda_Reporte.pdf";
                    addtext = "Zurita Sac.\n\
        \n\
\n\
        Fecha y hora de exportación: "+fecha_actual+ " "+horaImprimible+ "\n\
\n\
\n\
\n\
REPORTE  DE  ENCOMIENDAS  REALIZADAS  DESDE:  " + from + "   HASTA:  " + to;            
        }
        
        var layout = {
            "content": []
        };
        


        layout.content.push({
            text: addtext,
            fontSize: 11
        });
        
        layout.content.push({
            "image": charts["chartdiv1"].exportedImage,
            "fit": [ 523, 300 ]
        });
        
        var tlist_3 = $('#chartdiv4').html().replace(/\s/ig, '').length;
         if(tlist_3 !== 0){
             layout.content.push({
                 "image": charts["chartdiv4"].exportedImage,
                 "fit": [ 523, 300 ]
             }); 
         }      
                     
        layout.content.push({
            "image": charts["chartdiv2"].exportedImage,
            "fit": [ 523, 300 ]
        });                       

        chart["export"].toPDF(layout, function(data) {
            this.download(data, "application/pdf", titulo);
          });
        }           
        
    }
    /*
function exportXLSX() {
    
    try {
            console.log("Starting export...");

            // Define IDs of the charts we want to include in the report
            var ids = ["chartdiv1", "chartdiv4", "chartdiv2"];

            // Collect actual chart objects out of the AmCharts.charts array
            var charts = {};
            for (var i = 0; i < ids.length; i++) {
              for (var x = 0; x < AmCharts.charts.length; x++) {
                if (AmCharts.charts[x].div.id === ids[i])
                  charts[ids[i]] = AmCharts.charts[x];            
                }
            }
             console.log(charts);
             
             var cont = 0;
            for (var x in charts) {
                cont ++;
                cont = cont + 0;
                 var chart = charts[x];
                  console.log(chart);
                   fc_generate_excel(cont);
            }
   
    } catch (e) {
        alert('Ocurrió un error al exportar.\nConsulte con el administrador.');
            console.log(e.message);
    }
    
    function fc_generate_excel(x){ 
        
        var dni = $('#dni').val();    
        var ruc = $('#ruc').val();   
        
        var titulo = "";
        if(dni !==null && dni.length !== 0){
            titulo = "Encomienda_Reporte_"+x+"_DNI_"+dni+".xlsx";
            
        }
        else if(ruc !==null && ruc.length !== 0){
            titulo = "Encomienda_Reporte_"+x+"_RUC_"+ruc+".xlsx";
            
        }        
        else{
            titulo = "Encomienda_Reporte_"+x+".xlsx";
         
        }        
            
        chart["export"].toXLSX({}, function(data) {
        this.download(data, "application/xlsx", titulo);
        });
    }
/*
    chart.export.toXLSX({}, function(data) {
        this.download(data, this.defaults.formats.XLSX.mimeType, "amCharts.xlsx");
    });

}

*/