    
    var chart1, chart2;
    var dateToday = new Date();    
    
jQuery(function ($) {
     $("#div2").hide();
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

            }

            getGraficoBarrasFecha1(identificador);
        });          
            
            
        $('#btnExportChartsPDF').click(function (){                                     
            //fc_export_pdf(); 
            fc_export_pdf();
        });            
        $('#btnExportChartsXSXL').click(function (){                                     
            //fc_export_pdf(); 
            exportXLSX();
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
                            alert("Hay encomiendas");   
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
    
    
    
    
    var _private = {};
    var chart = {};    
     _private.setBarrasFecha1 = function (data, fecha_inicio, fecha_final, identificador) {
        var objeto = JSON.parse(data.mensaje);  
        
        for(var i=0; i< objeto.length; i++){
               delete objeto[i].total;
        }
     
        var arreglado = objeto.map( item => { 
            return { mes: item.tiempo , sobre: item.sobre, paquete: item.paquete }; 
        });

        console.log(arreglado);                         
      
        var titulo = "";
        if(identificador !==null){
            if(identificador.length <= 8){
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
                "valueField": "paquete"
            }, {
                "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                "fillAlphas": 0.8,
                "labelText": "[[value]]",
                "lineAlpha": 0.3,
                "title": "sobre",
                "type": "column",
                "color": "#000000",
                "valueField": "sobre"
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
            if(identificador.length <= 8){
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
            var ids = ["chartdiv1", "chartdiv2"];

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
        var dni = $('#dni').val();    
        var ruc = $('#ruc').val();
        
        var addtext = "";
        
        
        
        var titulo = "";
        if(dni !==null && dni.length !== 0){
            titulo = "Encomienda_Reporte_DNI_"+dni+".pdf";
                    addtext = "Zurita Sac.\n\n\
\n\
\n\
\n\
            REPORTE  DE  ENCOMIENDAS  REALIZADAS  DESDE:  " + from + "   HASTA:  " + to+" DEL DNI: "+dni;
            
        }else if(ruc !==null && ruc.length !== 0){
                        titulo = "Encomienda_Reporte_RUC_"+dni+".pdf";
                    addtext = "Zurita Sac.\n\n\
\n\
\n\
\n\
            REPORTE  DE  ENCOMIENDAS  REALIZADAS  DESDE:  " + from + "   HASTA:  " + to+" DEL RUC: "+ruc;
        }
        else{
            titulo = "Encomienda_Reporte.pdf";
            
        addtext = "Zurita Sac.\n\n\
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
            var ids = ["chartdiv1", "chartdiv2"];

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

