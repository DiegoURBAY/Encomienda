    
    var chart1, chart2;
    var dateToday = new Date(); 
    
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
    
    $(document).ready(function(){
                       
        /*Ingreso Grafico de Barras*/  
        $('#btnGraficoBarraPastelGananciaVehiculoPorAño').click(function () {
            
            getGraficoBarras1();
            //Luego ejecuta --> getGraficoPie1();
            
        });                
        $('#btnGraficoBarraPastelGananciaVehiculoPorMes').click(function () {
            
            getGraficoBarras2();
        });
        
        $('#btnGraficoBarraLineaGananciaVehiculoPorFecha').click(function () {     
            
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
            else if( from > to ) {
              alert('[ERROR] La fecha de inicio no puede ser mayor a la fecha final');
              return false;              
            }            
            getGraficoBarrasFecha1();
                     
        });                    
        
        $('#btnExportChartsPDF').click(function (){                                     
            fc_export_pdf(); 
        });
//        $('#btnExportChartsExcel').click(function (){                                     
//            fc_export_excel(); 
//        });        
    });    
    
    
      getGraficoBarras1 = function () {
                
        $.ajax({
            type: "POST",            
            url: 'SERVReporte',
            data: "&action=listarVehiculoPorAño",
            dataType: 'json',
            success: function (data) {
                console.log(data.mvehiculo);
               _private.setBarras1(data);
            },
            complete:function(){
                getGraficoPie1();
            }
        });
    };


    getGraficoBarras2 = function () {
        var año = $("#listarAño").val();
        $.ajax({
            type: "POST",            
            url: 'SERVReporte',
            data: "&action=listarVehiculoPorMes&año="+año,
            dataType: 'json',
            success: function (data) {
                console.log(data.mvehiculo);
               _private.setBarras2(data);
            },
            complete:function(){
                getGraficoPie2();
            }
        });
    };
    
    getGraficoBarras3 = function () {
        $.ajax({
            type: "POST",
            url: 'SERVReporte',
            data: "&action=listarTipoVehiculoPorAño",
            dataType: 'json',
            success: function (data) {
                console.log(data.mvehiculo);
               _private.setBarras3(data);
            }
        });
    };  
    
    getGraficoBarras4 = function () {
        var año = $("#listarAño").val();
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            data: "&action=listarTipoVehiculoMes&año="+año,
            dataType: 'json',
            success: function (data) {
                 console.log(data.mvehiculo);
                _private.setBarras4(data);
            }
        });
    };    
            
    getGraficoPie1 = function () {
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            data: "&action=listarVehiculoPorAño",
            dataType: 'json',
            success: function (data) {
                 console.log(data.mvehiculo);
                _private.setPie1(data);
            },
            complete:function(){
                getGraficoBarras3();
            }
        });
    };
    
    
        
    getGraficoPie2 = function () {
        var año = $("#listarAño").val();
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            data: "&action=listarVehiculoPorMes&año="+año,
            dataType: 'json',
            success: function (data) {
                 console.log(data.mvehiculo);
                _private.setPie2(data);
            },
            complete:function(){
                getGraficoBarras4();
            }
        });
    };
    
    getGraficoPie3 = function () {
        var tipo = $("#listarTipo").val();
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            data: "&action=listarTipoIngresoPorAño&tipo="+tipo,
            dataType: 'json',
            success: function (data) {
                 console.log(data.mvehiculo);
                _private.setPie3(data);
            }
        });
    };          
     
    getGraficoBarrasFecha1 = function () {
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            data: "&action=listarTipoVehiculoPorFecha&fechaI="+fecha_inicio+"&fechaF="+fecha_final,
            dataType: 'json',
            success: function (data) {
                 console.log(data.mvehiculo);
                _private.setBarrasFecha1(data);
            },
            complete:function(){
                getGraficoLineasFecha1();
            }
        });                
    };    
    
    getGraficoLineasFecha1 = function () {
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            data: "&action=listarVehiculoPorFecha&fechaI="+fecha_inicio+"&fechaF="+fecha_final,
            dataType: 'json',
            success: function (data) {
                 console.log(data.mvehiculo);
                _private.setLineasFecha1(data);
            },
            complete:function(){
                getGraficoBarrasFecha2();
            }
        });                
    };        
    
    getGraficoBarrasFecha2 = function () {
        var fecha_inicio = $("#from").val();
        var fecha_final = $("#to").val();        
        $.ajax({
            type: "POST",           
            url: 'SERVReporte',
            data: "&action=listarTipoVehiculoPorFecha&fechaI="+fecha_inicio+"&fechaF="+fecha_final,
            dataType: 'json',
            success: function (data) {
                 console.log(data.mvehiculo);
                _private.setBarrasFecha2(data);
            }
        });                
    };   
    
    /*metodos, propiedades privadas*/
    var _private = {};

    _private.setBarras1 = function (data) {
        var objeto = JSON.parse(data.mvehiculo);  
        
        for(var i=0; i< objeto.length; i++){
               delete objeto[i].acura;
               delete objeto[i].audi;
               delete objeto[i].honda;
               delete objeto[i].cadillac;
               delete objeto[i].ford;
        }

        var arreglado = objeto.map( item => { 
            return { año: item.tiempo , total : item.total }; 
        });

        console.log(arreglado);

        var chart = AmCharts.makeChart("chartdiv1", {
            "theme": "light",
            "type": "serial",
            "titles": [{
                    "text": "Reporte de cantidad de vehiculos según el año",
                    "size": 16
                }],            
            "startDuration": 2,
            "dataProvider": arreglado,
            "valueAxes": [{
                    "position": "left",
                    "title": "cantidad de vehiculos"
                }],
            "graphs": [{
                    "balloonText": "[[category]]: <b>[[value]]</b>",
                    "fillColorsField": "color",
                    "fillAlphas": 1,
                    "lineAlpha": 0.1,
                    "type": "column",
                    "valueField": "total",
                    labelText: "[[value]]"
                }],
            "depth3D": 20,
            "angle": 30,
            "chartCursor": {
                "categoryBalloonEnabled": false,
                "cursorAlpha": 0,
                "zoomable": false
            },
            "categoryField": "año",
            "categoryAxis": {
                "gridPosition": "start",
                "labelRotation": 90,
                "title": "Años", 
                "listeners": [{
                  "event": "clickGraphItem",
                  "method": exportXLSX
                }]
            },
            "listeners": [{
              "event": "clickGraphItem",
              "method": exportXLSX
            }],
            "export": {
                "enabled": true,
                "menu": []                
            }
        });
        function exportXLSX() {
            chart["export"].toXLSX({
                data: chart.dataProvider
            }, function(data) {
                this.download(data, this.defaults.formats.XLSX.mimeType, "ReporteCantidadVehiculoPorAño.xlsx");
            });
        }        
    };

    _private.setBarras2 = function (data) {
        var objeto = JSON.parse(data.mvehiculo);  
        
        for(var i=0; i< objeto.length; i++){
               delete objeto[i].acura;
               delete objeto[i].audi;
               delete objeto[i].honda;
               delete objeto[i].cadillac;
               delete objeto[i].ford;
        }
     
        var arreglado = objeto.map( item => { 
            return { mes: item.tiempo , total : item.total }; 
        });

        console.log(arreglado);
        
        var año = $("#listarAño").val();
        
        var chart = AmCharts.makeChart("chartdiv1", {
            "theme": "light",
            "type": "serial",
            "titles": [{
                    "text": "Reporte de cantidad de vehiculos del "+año +" según la marca",
                    "size": 16
                }],            
            "startDuration": 2,
            "dataProvider": arreglado,
            "valueAxes": [{
                    "position": "left",
                    "title": "Cantidad de marcas"
                }],
            "graphs": [{
                    "balloonText": "[[category]]: <b>[[value]]</b>",
                    "fillColorsField": "color",
                    "fillAlphas": 1,
                    "lineAlpha": 0.1,
                    "type": "column",
                    "valueField": "total"
                }],
            "depth3D": 20,
            "angle": 30,
            "chartCursor": {
                "categoryBalloonEnabled": false,
                "cursorAlpha": 0,
                "zoomable": false
            },
            "categoryField": "mes",
            "categoryAxis": {
                "gridPosition": "start",
                "labelRotation": 90,
                "title": "Marcas", 
                "listeners": [{
                  "event": "clickGraphItem",
                  "method": exportXLSX
                }]
            },
            "listeners": [{
              "event": "clickGraphItem",
              "method": exportXLSX
            }],
            "export": {
                "enabled": true,
                "menu": []
            }
        });
        function exportXLSX() {
            chart["export"].toXLSX({
                data: chart.dataProvider
            }, function(data) {
                this.download(data, this.defaults.formats.XLSX.mimeType, "ReporteCantidadVehiculos"+año+".xlsx");
            });
        }            
    };
        
    _private.setBarras3 = function (data) {
        var objeto = JSON.parse(data.mvehiculo);  
        
        for(var i=0; i< objeto.length; i++){
               delete objeto[i].total;
        }
     
        var arreglado = objeto.map( item => { 
            return { año: item.tiempo , acura: item.acura, audi: item.audi, honda: item.honda, cadillac: item.cadillac, ford: item.ford }; 
        });

        console.log(arreglado);
        
        var chart = AmCharts.makeChart("chartdiv3", {
            "type": "serial",            
            "theme": "light",          
            "categoryField": "año",
            "rotate": true,
            "startDuration": 1,
            "categoryAxis": 
                {
                    "gridPosition": "start",
                    "position": "left",
                    "title": "Años", 
                    "listeners": [{
                        "event": "clickGraphItem",
                        "method": exportXLSX
                    }]
                },
            "listeners": [{ 
                "event": "clickGraphItem",
                "method": exportXLSX
            }],
            "trendLines": [],
            "graphs": [
                    {
                        "balloonText": "acura:[[value]]",
                        "fillAlphas": 0.8,
                        "id": "AmGraph-1",
                        "lineAlpha": 0.2,
                        "title": "Acura",
                        "type": "column",
                        "valueField": "acura",
                        labelText: "acura:[[value]]"
                    },
                    {
                        "balloonText": "audi:[[value]]",
                        "fillAlphas": 0.8,
                        "id": "AmGraph-2",
                        "lineAlpha": 0.2,
                        "title": "Audi",
                        "type": "column",
                        "valueField": "audi",
                        labelText: "audi:[[value]]"
                    },
                    {
                        "balloonText": "honda:[[value]]",
                        "fillAlphas": 0.8,
                        "id": "AmGraph-3",
                        "lineAlpha": 0.2,
                        "title": "Honda",
                        "type": "column",
                        "valueField": "honda",
                        labelText: "honda:[[value]]"
                    },
                    {
                        "balloonText": "cadillac:[[value]]",
                        "fillAlphas": 0.8,
                        "id": "AmGraph-4",
                        "lineAlpha": 0.2,
                        "title": "Cadillac",
                        "type": "column",
                        "valueField": "cadillac",
                        labelText: "cadillac:[[value]]"
                    },
                    {
                        "balloonText": "ford:[[value]]",
                        "fillAlphas": 0.8,
                        "id": "AmGraph-5",
                        "lineAlpha": 0.2,
                        "title": "Ford",
                        "type": "column",
                        "valueField": "ford",
                        labelText: "ford:[[value]]"
                    }                    
            ],
            "guides": [],
            "valueAxes": [
                    {
                        "id": "ValueAxis-1",
                        "position": "left",
                        "axisAlpha": 0,
                        "title": "Cantidad de vehiculos"
                    }
            ],
            "allLabels": [],
            "balloon": {},
            "titles": [
                {
                    "text": "Reporte de cantidad de marcas según el año"
                }
            ],
            "dataProvider": arreglado,
            "export": {
                "enabled": true,
                "menu": []
            }
        });  
        function exportXLSX() {
            chart["export"].toXLSX({
                data: chart.dataProvider
            }, function(data) {
                this.download(data, this.defaults.formats.XLSX.mimeType, "ReporteCantidadMarcasPorAño.xlsx");
            });
        }       
    };    
        
    _private.setBarras4 = function (data) {
        var objeto = JSON.parse(data.mvehiculo);  
        
        for(var i=0; i< objeto.length; i++){
               delete objeto[i].total;
        }
     
        var arreglado = objeto.map( item => { 
            return { mes: item.tiempo , acura: item.acura, audi: item.audi, honda: item.honda, cadillac: item.cadillac, ford: item.ford }; 
        });

        console.log(arreglado);    
        
        var año = $("#listarAño").val();   
        
        var año = $("#listarAño").val();        
        
        var chart = AmCharts.makeChart("chartdiv3", {
            "type": "serial",            
            "theme": "light",          
            "categoryField": "mes",
            "rotate": true,
            "startDuration": 1,
            "categoryAxis": {
                    "gridPosition": "start",
                    "position": "left",
                    "title": "Años", 
                    "listeners": [{
                        "event": "clickGraphItem",
                        "method": exportXLSX
                    }]
                },
            "listeners": [{ 
                "event": "clickGraphItem",
                "method": exportXLSX
            }],
            "trendLines": [],
            "graphs": [
                    {
                        "balloonText": "acura:[[value]]",
                        "fillAlphas": 0.8,
                        "id": "AmGraph-1",
                        "lineAlpha": 0.2,
                        "title": "Acura",
                        "type": "column",
                        "valueField": "acura",
                        labelText: "acura:[[value]]"
                    },
                    {
                        "balloonText": "audi:[[value]]",
                        "fillAlphas": 0.8,
                        "id": "AmGraph-2",
                        "lineAlpha": 0.2,
                        "title": "Audi",
                        "type": "column",
                        "valueField": "audi",
                        labelText: "audi:[[value]]"
                    },
                    {
                        "balloonText": "honda:[[value]]",
                        "fillAlphas": 0.8,
                        "id": "AmGraph-3",
                        "lineAlpha": 0.2,
                        "title": "Honda",
                        "type": "column",
                        "valueField": "honda",
                        labelText: "honda:[[value]]"
                    },
                    {
                        "balloonText": "cadillac:[[value]]",
                        "fillAlphas": 0.8,
                        "id": "AmGraph-4",
                        "lineAlpha": 0.2,
                        "title": "Cadillac",
                        "type": "column",
                        "valueField": "cadillac",
                        labelText: "cadillac:[[value]]"
                    },
                    {
                        "balloonText": "ford:[[value]]",
                        "fillAlphas": 0.8,
                        "id": "AmGraph-5",
                        "lineAlpha": 0.2,
                        "title": "Ford",
                        "type": "column",
                        "valueField": "ford",
                        labelText: "ford:[[value]]"
                    }  
            ],
            "guides": [],
            "valueAxes": [
                    {
                        "id": "ValueAxis-1",
                        "position": "left",
                        "axisAlpha": 0,
                        "title": "Cantidad de marcas"
                    }
            ],
            "allLabels": [],
            "balloon": {},
            "titles": [
                 {
                    "text": "Reporte de tipos de marcas del "+año+" según el año"
                }
            ],
            "dataProvider": arreglado,
            "export": {
                "enabled": true,
                "menu": []
            }
        });            
        function exportXLSX() {
            chart["export"].toXLSX({
                data: chart.dataProvider
            }, function(data) {
                this.download(data, this.defaults.formats.XLSX.mimeType, "ReporteTipoMarcas"+año+".xlsx");
            });
        }         
    };    

    _private.setBarrasFecha1 = function (data) {
        var objeto = JSON.parse(data.mvehiculo);  

        for(var i=0; i< objeto.length; i++){
               delete objeto[i].total;
        }
     
        var arreglado = objeto.map( item => { 
            return { mes: item.tiempo , acura: item.acura, audi: item.audi, honda: item.honda, cadillac: item.cadillac, ford: item.ford };
        });

        console.log(arreglado);

        var from = $('#from').val();
        var to = $('#to').val();                                
        
        var chart = AmCharts.makeChart("chartdiv1", {
            
            "type": "serial",
            "theme": "none",
            "titles": [{
                    "text": "Reporte de cantidad de vehiculos  según el tipo y el mes",
                    "size": 16
                }],              
            "legend": {
                "horizontalGap": 10,
                "maxColumns": 1,
                "position": "right",
                "useGraphSettings": true,
                "markerSize": 10
            },
            "dataProvider": arreglado,
            "graphs": [
                {
                    "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                    "fillAlphas": 0.8,
                    "labelText": "[[value]]",
                    "lineAlpha": 0.3,
                    "title": "Acura",
                    "type": "column",
                    "color": "#000000",
                    "valueField": "acura"
                }, {
                    "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                    "fillAlphas": 0.8,
                    "labelText": "[[value]]",
                    "lineAlpha": 0.3,
                    "title": "Audi",
                    "type": "column",
                    "color": "#000000",
                    "valueField": "audi"
                }, {
                    "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                    "fillAlphas": 0.8,
                    "labelText": "[[value]]",
                    "lineAlpha": 0.3,
                    "title": "Honda",
                    "type": "column",
                    "color": "#000000",
                    "valueField": "honda"
                }, {
                    "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                    "fillAlphas": 0.8,
                    "labelText": "[[value]]",
                    "lineAlpha": 0.3,
                    "title": "Cadillac",
                    "type": "column",
                    "color": "#000000",
                    "valueField": "cadillac"
                }, {
                    "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                    "fillAlphas": 0.8,
                    "labelText": "[[value]]",
                    "lineAlpha": 0.3,
                    "title": "Ford",
                    "type": "column",
                    "color": "#000000",
                    "valueField": "ford"
                }
                    ],
            "categoryField": "mes",
            "categoryAxis": {
                "gridPosition": "start",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "position": "left",
                "title": "Mes", 
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
                        "title": " Cantidad de marcas"
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
                this.download(data, this.defaults.formats.XLSX.mimeType, "ReporteCantidadMarcaDesde"+from+"Hasta"+to+".xlsx");
            });
        }            
    };

    _private.setPie1 = function (data) {
        var objeto = JSON.parse(data.mvehiculo);  
//        alert(objeto.length);
        
        var chart = AmCharts.makeChart("chartdiv2", {
            "type": "pie",
            "theme": "light",
            "titles": [{
                    "text": "Reporte % de cantidad de vehiculos según el año",
                    "size": 16
                }],
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

    _private.setPie2 = function (data) {
        var objeto = JSON.parse(data.mvehiculo);  
//        alert(objeto.length);
        var año = $("#listarAño").val();
        
        var chart = AmCharts.makeChart("chartdiv2", {
            "type": "pie",
            "theme": "light",
            "titles": [{
                    "text": "Reporte % de cantidad de vehiculos del "+año+" según la marca",
                    "size": 16
                }],
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
    
    _private.setPie3 = function (data) {
        var objeto = JSON.parse(data.mvehiculo);  
//        alert(objeto.length);
        var tipo = $("#listarTipo").val();
        
        var chart = AmCharts.makeChart("chartdiv3", {
            "type": "pie",
            "theme": "light",
            "titles": [{
                    "text": "Reporte % de cantidad de marcas de "+tipo+" según el año",
                    "size": 16
                }],
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
    
    _private.setLineasFecha1 = function (data) {
        var objeto = JSON.parse(data.mvehiculo);  

        for(var i=0; i< objeto.length; i++){
               delete objeto[i].sobre;
               delete objeto[i].paquete;
        }
     
        var arreglado = objeto.map( item => { 
            return { fecha: item.tiempo , total: item.total }; 
        });

        console.log(arreglado);

        var from = $('#from').val();
        var to = $('#to').val();
        
        var chart = AmCharts.makeChart("chartdiv2", {
        "type": "serial",
        "titles": [{
                "text": "Reporte de cantidad de vehiculos según la fecha",
                "size": 16
            }],           
        "theme": "light",
        "marginRight": 40,
        "marginLeft": 40,
        "autoMarginOffset": 20,
        "mouseWheelZoomEnabled":true,
        "dataDateFormat": "YYYY-MM-DD",
        "valueAxes": [{
            "id": "v1",
            "axisAlpha": 0,
            "position": "left",
            "ignoreAxisWidth":true,
            "title": "Cantidad de vehiculos"
        }],
        "balloon": {
            "borderThickness": 1,
            "shadowAlpha": 0
        },
        "graphs": [{
            "id": "g1",
            "balloon":{
              "drop":true,
              "adjustBorderColor":false,
              "color":"#ffffff"
            },
            "bullet": "round",
            "bulletBorderAlpha": 1,
            "bulletColor": "#FFFFFF",
            "bulletSize": 5,
            "hideBulletsCount": 50,
            "lineThickness": 2,
            "title": "red line",
            "useLineColorForBulletBorder": true,
            "valueField": "total",
            "labelText": "[[value]]",            
            "balloonText": "<span style='font-size:18px;'>[[value]]</span>"
        }],
        "chartScrollbar": {
            "graph": "g1",
            "oppositeAxis":false,
            "offset":30,
            "scrollbarHeight": 80,
            "backgroundAlpha": 0,
            "selectedBackgroundAlpha": 0.1,
            "selectedBackgroundColor": "#888888",
            "graphFillAlpha": 0,
            "graphLineAlpha": 0.5,
            "selectedGraphFillAlpha": 0,
            "selectedGraphLineAlpha": 1,
            "autoGridCount":true,
            "color":"#AAAAAA"
        },
        "chartCursor": {
            "pan": true,
            "valueLineEnabled": true,
            "valueLineBalloonEnabled": true,
            "cursorAlpha":1,
            "cursorColor":"#258cbb",
            "limitToGraph":"g1",
            "valueLineAlpha":0.2,
            "valueZoomable":true
        },
        "valueScrollbar":{
          "oppositeAxis":false,
          "offset":50,
          "scrollbarHeight":10
        },
        "categoryField": "fecha",
        "categoryAxis": {
            "parseDates": true,
            "dashLength": 1,
            "minorGridEnabled": true,
            "title": "Fechas", 
            "listeners": [{
                  "event": "clickGraphItem",
                  "method": exportXLSX
                }]
            },
        "listeners": [{
          "event": "clickGraphItem",
          "method": exportXLSX
        }],
        "export": {
            "enabled": true,
                "menu": []
        },
        "dataProvider": arreglado
    });

        chart.addListener("rendered", zoomChart);

        zoomChart();

        function zoomChart() {
            chart.zoomToIndexes(chart.dataProvider.length - 40, chart.dataProvider.length - 1);
        }
        function exportXLSX() {
            chart["export"].toXLSX({
                data: chart.dataProvider
            }, function(data) {
                this.download(data, this.defaults.formats.XLSX.mimeType, "ReporteCantidadVehiculoDesde"+from+"Hasta"+to+".xlsx");
            });
        }          
    };   
    
    _private.setBarrasFecha2 = function (data) {
        var objeto = JSON.parse(data.mvehiculo);  

        for(var i=0; i< objeto.length; i++){
               delete objeto[i].total;
        }
     
        var arreglado = objeto.map( item => { 
            return { mes: item.tiempo , acura: item.acura, audi: item.audi, honda: item.honda, cadillac: item.cadillac, ford: item.ford };
        });

        console.log(arreglado);  

        var from = $('#from').val();
        var to = $('#to').val();        
        
        var chart = AmCharts.makeChart("chartdiv3", {
            "type": "serial",
            "titles": [{
                "text": "Reporte de cantidad de marcas por tipo según el mes",
                "size": 16
            }],               
             "theme": "light",
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
                 "title": "Cantidad de marcas"
             }],
             "graphs": [
                {
                    "balloonText": "<img src='https://www.amcharts.com/lib/3/images/car.png' style='vertical-align:bottom; margin-right: 10px; width:28px; height:21px;'><span style='font-size:14px; color:#000000;'><b>[[value]]</b></span>",
                    "fillAlphas": 0.6,
                    "hidden": true,
                    "lineAlpha": 0.4,
                    "title": "Otro",
                    "valueField": "Otro"
                }, {
                    "balloonText": "<img src='https://www.amcharts.com/lib/3/images/car.png' style='vertical-align:bottom; margin-right: 10px; width:28px; height:21px;'><span style='font-size:14px; color:#000000;'><b>[[value]]</b></span>",
                    "fillAlphas": 0.6,
                    "lineAlpha": 0.4,
                    "title": "Acura",
                    "valueField": "acura"
                }, {
                    "balloonText": "<img src='https://www.amcharts.com/lib/3/images/car.png' style='vertical-align:bottom; margin-right: 10px; width:28px; height:21px;'><span style='font-size:14px; color:#000000;'><b>[[value]]</b></span>",
                    "fillAlphas": 0.6,
                    "lineAlpha": 0.4,
                    "title": "Audi",
                    "valueField": "audi"
                }, {
                    "balloonText": "<img src='https://www.amcharts.com/lib/3/images/car.png' style='vertical-align:bottom; margin-right: 10px; width:28px; height:21px;'><span style='font-size:14px; color:#000000;'><b>[[value]]</b></span>",
                    "fillAlphas": 0.6,
                    "lineAlpha": 0.4,
                    "title": "Honda",
                    "valueField": "honda"
                }, {
                    "balloonText": "<img src='https://www.amcharts.com/lib/3/images/car.png' style='vertical-align:bottom; margin-right: 10px; width:28px; height:21px;'><span style='font-size:14px; color:#000000;'><b>[[value]]</b></span>",
                    "fillAlphas": 0.6,
                    "lineAlpha": 0.4,
                    "title": "Cadillac",
                    "valueField": "cadillac"
                }, {
                    "balloonText": "<img src='https://www.amcharts.com/lib/3/images/car.png' style='vertical-align:bottom; margin-right: 10px; width:28px; height:21px;'><span style='font-size:14px; color:#000000;'><b>[[value]]</b></span>",
                    "fillAlphas": 0.6,
                    "lineAlpha": 0.4,
                    "title": "Ford",
                    "valueField": "ford"
                }
                    ],
             "plotAreaBorderAlpha": 0,
             "marginTop": 10,
             "marginLeft": 0,
             "marginBottom": 0,
             "chartScrollbar": {},
             "chartCursor": {
                 "cursorAlpha": 0
             },
             "categoryField": "mes",
             "categoryAxis": {
                 "startOnAxis": true,
                 "axisColor": "#DADADA",
                 "gridAlpha": 0.07,
                 "title": "Fecha",
                 "listeners": [{
                  "event": "clickGraphItem",
                  "method": exportXLSX
                }],                   
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
            "listeners": [{
              "event": "clickGraphItem",
              "method": exportXLSX
            }],              
             "export": {
                 "enabled": true,
                "menu": []
              }
         });            
        function exportXLSX() {
            chart["export"].toXLSX({
                data: chart.dataProvider
            }, function(data) {
                this.download(data, this.defaults.formats.XLSX.mimeType, "ReporteCantidadMarcaDesde"+from+"Hasta"+to+".xlsx");
            });
        }           
    };
    
var myObj = { firstname : "John", lastname : "Doe" };
console.log(myObj);

var charts = {};
             
function fc_export_pdf()
    {
        try
        {
            // So that we know export was started
            console.log("Starting export...");

            // Define IDs of the charts we want to include in the report
            var ids = ["chartdiv1", "chartdiv2", "chartdiv3"];

            // Collect actual chart objects out of the AmCharts.charts array
            var charts = {}
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
        
        addtext = "REPORTE  DE  VEHICULOS  ADQUIRIDOS  DESDE:  " + from + "   HASTA:  " + to;


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
        
        layout.content.push({
            image: charts["chartdiv3"].exportedImage,
            fit: [523, 300]
        });        

        chart["export"].toPDF(layout, function(data) {
            this.download(data, "application/pdf", "ReporteVehiculo.pdf");
          });
        }         
        
    }
