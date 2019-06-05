    
    var chart1, chart2;
    var dateToday = new Date();    
    
jQuery(function ($) {
        
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
            
            if( from === null || from.length === 0 || /^\s+$/.test(from) ) {
              alert('[ERROR] Ingrese fecha de inicio');
              return false;              
            }
            else if( to === null || to.length === 0 || /^\s+$/.test(to) ) {
              alert('[ERROR] Ingrese fecha final');
              return false;              
            }
                     
            var x = from.split("/");
            var z = to.split("/");

            //Cambiamos el orden al formato americano, de esto dd/mm/yyyy a esto mm/dd/yyyy
            from = x[2] + "-" + x[1] + "-" + x[0];
            to = z[2] + "-" + z[1] + "-" + z[0];

            //Comparamos las fechas
            if (Date.parse(from) > Date.parse(to)){
                alert("[ERROR] La fecha de envio no puede superar la fecha de llegada");
                return false;   
            }            

                getGraficoBarrasFecha1();
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
           
   getGraficoBarrasFecha1 = function () {       
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();
        $.ajax({
                type: "POST",           
                url: 'SERVReporte',
                data: "&action=listarEncomiendaPorFecha&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final,
                dataType: 'json',
                success: function (data) {
                    if(data.estado === "ok"){
                        if(data.mensaje !== "vacio"){
                            alert("Hay encomiendas");                       
                            console.log(data.mensaje);
                            $("#div2").show();
                            _private.setBarrasFecha1(data, fecha_inicio, fecha_final);
                         
                            
                        }else{
                         alert("No hay encomiendas");                          
                        }
                        
                    }
                    else{
                        alert("No hay encomiendas");          
                    }
                     
                },
                complete:function(){
                    getGraficoPieFecha1();
                }
        });                
    }; 
      
   getGraficoPieFecha1 = function () {       
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            data: "&action=listarEncomiendaPorFecha2&fechaInicio="+fecha_inicio+"&fechaFinal="+fecha_final,
            dataType: 'json',
            success: function (data) {
                if(data.estado === "ok"){
                    if(data.mensaje !== "vacio"){
                        alert("Hay encomiendas");                       
                        console.log(data.mensaje);                        
                        _private.setPie1(data, fecha_inicio, fecha_final);

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
     
     _private.setBarrasFecha1 = function (data, fecha_inicio, fecha_final) {
        var objeto = JSON.parse(data.mensaje);  
        
        for(var i=0; i< objeto.length; i++){
               delete objeto[i].total;
        }
     
        var arreglado = objeto.map( item => { 
            return { mes: item.tiempo , sobre: item.sobre, paquete: item.paquete }; 
        });

        console.log(arreglado);

        var from = $('#from').val();
        var to = $('#to').val();                                
        
        var chart = AmCharts.makeChart("chartdiv1", {
            
            "type": "serial",
            "theme": "light",
            "titles": [{
                    "text": "Reporte de cantidad de encomiendas realizadas según el tipo desde "+fecha_inicio+" hasta "+fecha_final,
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
               "title": "mes", 
                "listeners": [{
                  "event": "clickGraphItem",
                  "method": exportXLSX
                }]
            },
            "listeners": [{
              "event": "clickGraphItem",
              "method": exportXLSX
            }],
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
    
  
        function exportXLSX() {
            chart["export"].toXLSX({
                data: chart.dataProvider
            }, function(data) {
                this.download(data, this.defaults.formats.XLSX.mimeType, "ReporteCantidadTipoEncomiendaDesde"+from+"Hasta"+to+".xlsx");
            });
        }         
    };
    
    _private.setPie1 = function (data, fecha_inicio, fecha_final) {
        var objeto = JSON.parse(data.mensaje);         
        
        var chart = AmCharts.makeChart("chartdiv2", {
            "type": "pie",
            "theme": "light",
            "titles": [{
                    "text": "Reporte de cantidad y porcentaje de encomiendas realizadas según el tipo desde "+fecha_inicio+" hasta "+fecha_final,
                    "size": 14
                }],              
            "legend": {
                "horizontalGap": 10,
                "maxColumns": 1,
                "position": "right",
          //      "useGraphSettings": true,
                "markerSize": 10
            },            
            "dataProvider": objeto,
            "valueField": "total",
            "titleField": "tiempo",
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
        var addtext = "";
        
        var layout = {
            "content": []
        };
        
        addtext = "Zurita Sac.\n\n\
\n\
\n\
\n\
REPORTE  DE  ENCOMIENDAS  REALIZADAS  DESDE:  " + from + "   HASTA:  " + to;

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
            this.download(data, "application/pdf", "ReporteEncomienda.pdf");
          });
        }           
        
    }
    

