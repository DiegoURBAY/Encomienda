    
    var chart1, chart2;
    var dateToday = new Date();    
    
    var mes_array = new Array();
    var tipo_cliente;    
    
jQuery(function ($) {
     $("#div2").hide();
     //el div 4 esta dentro de 2
     $("#div4").hide();    
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
 
            var color = "#FF0000";
            
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
            $("#chartdiv4").empty();
            $("#div4").hide();
            getGraficoBarrasFecha1();
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
           
   getGraficoBarrasFecha1 = function () {       
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();
        var tipo = 1;
        $.ajax({
                type: "POST",           
                url: 'SERVReporte',
                data: "&action=listarClientePorFecha&tipo="+tipo+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final,
             //   data: busqueda,
                dataType: 'json',
                success: function (data) {
                    if(data.estado === "ok"){
                        if(data.mensaje !== "vacio"){                 
                            console.log(data.mensaje);
                          //  console.log(data.cantidad);
                            alert("¡Hay clientes! No olvide dar clic al primer reporte para ver más detalles");
                            $("#div2").show();
                            _private.setBarrasFecha1(data, fecha_inicio, fecha_final, data.cantidad); 
                                                                                                  
                        }else{
                            alert("No hay clientes");   
                     //    $("#div2").hide();
                         
                        }
                        
                    }
                    else{
                        alert("No hay clientes");          
                    }
                     
                },
                complete:function(){
                   
                    getGraficoPieFecha1();
                }
        });                
    }; 
    
   getGraficoBarrasFecha2 = function (tipo, mes) {       
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();

        var url = "";

        if(mes === null){
            url = "&action=listarClientePorFecha2&cliente="+tipo+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final;
        //no hay identificador pero si mes    
        }else{
            url = "&action=listarClientePorFecha2&cliente="+tipo+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&mes="+mes;
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
                            _private.setBarrasFecha2(data, tipo, mes, fecha_inicio, fecha_final); 
                                                                                                  
                        }else{
                         
                        }
                        
                    }
                    else{
                    }
                     
                },
                complete:function(){
                                   
                }
        });                
    };          
      
   getGraficoPieFecha1 = function () {       
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();
        var tipo = 2;
        
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            data: "&action=listarClientePorFecha&tipo="+tipo+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final,
            dataType: 'json',
            success: function (data) {
                if(data.estado === "ok"){
                    if(data.mensaje !== "vacio"){                                     
                        console.log(data.mensaje);                        
                        _private.setPie1(data, fecha_inicio, fecha_final);                  

                    }else{
                     alert("No hay clientes");                          
                    }

                }
                else{
                    alert("No hay clientes");          
                }

            },
            complete:function(){
             //   getGraficoLineasFecha1();
            }
        });                
    };
    
   exportXLSX2 = function () {       
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();
        var tipo = 1;
        
        var url = "";
        
        //Saber si existe el grafico detallado
        var tlist_3 = $('#chartdiv4').html().replace(/\s/ig, '').length;
        if(tlist_3 !== 0){
            
            tipo = 2;
            //Si existe, saber que tipo de grafico es             
            if(mes_array.length === 0){
                url = "&action=generarCliente&tipo="+tipo+"&cliente="+tipo_cliente+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final;
            //no hay identificador pero si mes    
            }else{
                var ultimo = mes_array[mes_array.length - 1];
                console.log(ultimo);
                url = "&action=generarCliente&tipo="+tipo+"&cliente="+tipo_cliente+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final+"&mes="+ultimo;
            }                 
        }
        else{
             url ="&action=generarCliente&tipo="+tipo+"&cliente="+tipo_cliente+"&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final;
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
     _private.setBarrasFecha1 = function (data, fecha_inicio, fecha_final, cantidad) {
        var objeto = JSON.parse(data.mensaje);  
        var objetocantidad = JSON.parse(cantidad);                         
       
        var cantidadEmpresa = objetocantidad[0];
        var cantidadPersona = objetocantidad[1];
        console.log("cantidadEmpresa: "+cantidadEmpresa);
        console.log("cantidadPersona: "+cantidadPersona);
        
        for(var i=0; i< objeto.length; i++){
               delete objeto[i].total;
        }
     
        var arreglado = objeto.map( item => { 
            return { mes: item.tiempo , empresa: item.sobre, persona: item.paquete }; 
        });

        console.log(arreglado);                         
      
        var titulo = "Reporte de cantidad de clientes registrados desde "+fecha_inicio+" hasta "+fecha_final;

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
                "title": "empresa "+cantidadEmpresa,
                "type": "column",
                "color": "#000000",
                "valueField": "empresa",
                "listeners": [{
                  "event": "clickGraphItem",
                  "method": empresa
                }]                
            }, {
                "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                "fillAlphas": 0.8,
                "labelText": "[[value]]",
                "lineAlpha": 0.3,
                "title": "persona "+cantidadPersona,
                "type": "column",
                "color": "#000000",
                "valueField": "persona",
                "listeners": [{
                  "event": "clickGraphItem",
                  "method": persona
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
                }]
*/            },/*
            "listeners": [{
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
        
        function empresa(e) {           
            var cliente = "empresa";
            tipo_cliente = "empresa"; 
            var objeto_escogido = Object.values(e)[2]; 
            var mes = objeto[objeto_escogido].tiempo;
    //        alert("mes : "+mes+" cliente escogido: "+cliente);
            //var answer = confirm("¿Seguro que desea ver el reporte de "+tipo+"s del mes de "+mes+" ?");
            var answer = confirm("¿Ver reporte de "+cliente+"s del mes de "+mes+" ? Si cancela verá el reporte con el rango de fechas establecido");
            if (answer)
            {                             
                console.log('yes');
                mes_array.push(mes);
             //   alert('Ha aceptado, mes '+mes+", tipo: "+tipo+", ident: "+identificador2);
                getGraficoBarrasFecha2(cliente, mes);
                return false;
            }
            
            else
            {
                console.log('cancel');     
                mes_array.splice(0, mes_array.length);
                var mes = null;
            //    alert('Ha cancelado, mes '+mes+", tipo: "+tipo);
                getGraficoBarrasFecha2(cliente, mes);
      //          return false;
            }             
        }
        function persona(e) {                     
            var cliente = "persona";
            tipo_cliente = "persona";            
            var objeto_escogido = Object.values(e)[2]; 
            var mes = objeto[objeto_escogido].tiempo;            
            //var answer = confirm("¿Seguro que desea ver el reporte de "+tipo+"s del mes de "+mes+" ?");
            var answer = confirm("¿Ver reporte de "+cliente+"s del mes de "+mes+" ? Si cancela verá el reporte con el rango de fechas establecido");
            if (answer)
            {                             
                console.log('yes');
         //       alert('Ha aceptado, mes '+mes+", tipo: "+tipo);
                mes_array.push(mes);
                getGraficoBarrasFecha2(cliente, mes);
                return false;
            }
            else
            {
         //       console.log('cancel');                
                var mes = null;
                mes_array.splice(0, mes_array.length);
          //      alert('Ha cancelado, mes '+mes+", tipo: "+tipo);
                getGraficoBarrasFecha2(cliente, mes);
          //      return false;
            }  
        }        
        
    /*
  
        function exportXLSX() {
            chart["export"].toXLSX({
                data: chart.dataProvider
            }, function(data) {
                this.download(data, this.defaults.formats.XLSX.mimeType, "ReporteCantidadTipoEncomiendaDesde"+from+"Hasta"+to+".xlsx");
            });
        }    
        */
    };
    
    _private.setBarrasFecha2 = function (data, tipo, mes, fecha_inicio, fecha_final)  {
        
        var objeto = JSON.parse(data.mensaje);  
            
        var arreglado = objeto.map( item => { 
            return { fecha: item.tiempo , cliente: item.total }; 
        });               
        
  
        console.log(arreglado);                         
      
        var titulo = "";
                
            if(mes !== null){
                titulo = "Reporte de cantidad de "+tipo+"s registradas durante el mes de "+mes;
            }
            else{
                titulo = "Reporte de cantidad de "+tipo+"s registradas  desde "+fecha_inicio+" hasta "+fecha_final;
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
                "title": "cliente",
                "valueField": "cliente"
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
    
    _private.setPie1 = function (data, fecha_inicio, fecha_final) {
        var objeto = JSON.parse(data.mensaje);         
        
        for(var i=0; i< objeto.length; i++){
            delete objeto[i].paquete;
            delete objeto[i].sobre;
        }
        
        var arreglado = objeto.map( item => { 
            return { tipo: item.tiempo , total: item.total }; 
        });               
        
        var titulo = "Reporte de % y cantidad de clientes registrados desde "+fecha_inicio+" hasta "+fecha_final;
           
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
                //cambia el color de los bordes por el de fondo
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
                if (AmCharts.charts[x].div.id === ids[i])
                  charts[ids[i]] = AmCharts.charts[x];
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
               
        
        var titulo = "Cliente_Reporte.pdf";
                    addtext = "Encomiendas Sac.\n\
        \n\
\n\
        Fecha y hora de exportación: "+fecha_actual+ " "+horaImprimible+ "\n\
\n\
\n\
\n\
REPORTE  DE  CLIENTES  REGISTRADOS  DESDE:  " + from + "   HASTA:  " + to;            
      
        
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
        
        var titulo = "Cliente_Reporte_"+x+".xlsx";   
        
        chart.export.toXLSX({}, function(data) {
        this.download(data, this.defaults.formats.XLSX.mimeType, titulo);
        });
    }
/*
    chart.export.toXLSX({}, function(data) {
        this.download(data, this.defaults.formats.XLSX.mimeType, "amCharts.xlsx");
    });
*/
}

