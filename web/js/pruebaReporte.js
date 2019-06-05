/*
Plugin Name: amCharts Export; post data processor plugin (CSV, XLSX)
Description: Merges multiple chart instance into one
Author: Benjamin Maertz, amCharts
Version: 1.0
Author URI: https://www.amcharts.com/

Copyright 2016 amCharts

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Please note that the above license covers only this plugin. It by all means does
not apply to any other amCharts products that are covered by different licenses.
*/

/**
 * Set init handler
 */
AmCharts.addInitHandler( function( chart ) {
  var _this = chart.export; // Chart export instance
  var toXLSX = _this.toXLSX; // toXLSX method backup


  /*
   ** REPLACE toXLSX METHOD TO MODIFY IT'S BEHAVIOUR
   */
  _this.toXLSX = function( options, callback ) {
    var cfg = _this.deepMerge( {
      name: "amCharts",
      dateFormat: _this.config.dateFormat || "dateObject",
      SheetNames: [],
      Sheets: {},
      withHeader: true,
      stringify: false,
    }, options || {}, true );
    var data = "";
    var wb = {
      SheetNames: [],
      Sheets: {}
    }

    function datenum( v, date1904 ) {
      if ( date1904 ) v += 1462;
      var epoch = Date.parse( v );
      return ( epoch - new Date( Date.UTC( 1899, 11, 30 ) ) ) / ( 24 * 60 * 60 * 1000 );
    }

    function sheet_from_array_of_arrays( data, opts ) {
      var ws = {};
      var range = {
        s: {
          c: 10000000,
          r: 10000000
        },
        e: {
          c: 0,
          r: 0
        }
      };
      for ( var R = 0; R != data.length; ++R ) {
        for ( var C = 0; C != data[ R ].length; ++C ) {
          if ( range.s.r > R ) range.s.r = R;
          if ( range.s.c > C ) range.s.c = C;
          if ( range.e.r < R ) range.e.r = R;
          if ( range.e.c < C ) range.e.c = C;
          var cell = {
            v: data[ R ][ C ]
          };
          if ( cell.v == null ) continue;
          var cell_ref = XLSX.utils.encode_cell( {
            c: C,
            r: R
          } );

          if ( typeof cell.v === "number" ) cell.t = "n";
          else if ( typeof cell.v === "boolean" ) cell.t = "b";
          else if ( cell.v instanceof Date ) {
            cell.t = "n";
            cell.z = XLSX.SSF._table[ 14 ];
            cell.v = datenum( cell.v );
          } else cell.t = "s";

          ws[ cell_ref ] = cell;
        }
      }
      if ( range.s.c < 10000000 ) ws[ "!ref" ] = XLSX.utils.encode_range( range );
      return ws;
    }

    // GET CHART DATA
    cfg.data = cfg.data ? cfg.data : _this.getChartData( cfg );

    // ARRAY MODIFICATION
    if ( _this.config.postDataProcessor instanceof Function ) {
      cfg.data = _this.config.postDataProcessor( "ARRAY", cfg.data );

      // GATHER POSSIBLE NEW FIELDS
      cfg.data = _this.getChartData( {
        data: cfg.data
      } );

    // GIVEN SHEETS
    } else if ( _this.config.postDataProcessor instanceof Object ) {
      cfg.SheetNames = _this.config.postDataProcessor.SheetNames || [];
      cfg.Sheets = _this.config.postDataProcessor.Sheets || {};
    }

    // DEFINE DEFAULT DATA SHEET
    cfg.Sheets[ cfg.name ] = cfg.data;
    if ( cfg.SheetNames.indexOf( cfg.name ) == -1 ) {
      cfg.SheetNames.unshift( cfg.name );
    }

    if ( _this.config.postDataProcessor instanceof Function ) {
      cfg = _this.config.postDataProcessor( "SHEETS", cfg );
    }

    // CRAWL THROUGH GIVEN SHEETS
    for ( var i1 = 0; i1 < cfg.SheetNames.length; i1++ ) {
      var sheetName = cfg.SheetNames[ i1 ];
      var sheetData = _this.toArray( {
        data: cfg.Sheets[ sheetName ],
        withHeader: true,
        stringify: false
      } );

      // PREPEND; APPEND
      if ( _this.config.postDataProcessor instanceof Object ) {

        if ( _this.config.postDataProcessor.prepend ) {
          sheetData.unshift([_this.config.postDataProcessor.prepend]);
        }

        if ( _this.config.postDataProcessor.append ) {
          sheetData.push([_this.config.postDataProcessor.append]);
        }

      }

      wb.SheetNames.push( sheetName );
      wb.Sheets[ sheetName ] = sheet_from_array_of_arrays( sheetData );
    }

    data = XLSX.write( wb, {
      bookType: "xlsx",
      bookSST: true,
      type: "base64"
    } );

    data = "data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64," + data;

    _this.handleCallback( callback, data );

    return data;
  }

}, [ "pie", "serial", "xy", "funnel", "radar", "gauge", "stock", "map", "gantt" ] );

var chart = AmCharts.makeChart("chartdiv", {
  "type": "serial",
  "theme": "light",
  "dataProvider": [{
    "country": "USA",
    "visits": 2025
  }, {
    "country": "China",
    "visits": 1882
  }, {
    "country": "Japan",
    "visits": 1809
  }, {
    "country": "Germany",
    "visits": 1322
  }, {
    "country": "UK",
    "visits": 1122
  }, {
    "country": "France",
    "visits": 1114
  }, {
    "country": "India",
    "visits": 984
  }, {
    "country": "Spain",
    "visits": 711
  }, {
    "country": "Netherlands",
    "visits": 665
  }, {
    "country": "Russia",
    "visits": 580
  }, {
    "country": "South Korea",
    "visits": 443
  }, {
    "country": "Canada",
    "visits": 441
  }, {
    "country": "Brazil",
    "visits": 395
  }],
  "valueAxes": [{
    "gridColor": "#FFFFFF",
    "gridAlpha": 0.2,
    "dashLength": 0
  }],
  "gridAboveGraphs": true,
  "startDuration": 1,
  "graphs": [{
    "balloonText": "[[category]]: <b>[[value]]</b>",
    "fillAlphas": 0.8,
    "lineAlpha": 0.2,
    "type": "column",
    "valueField": "visits"
  }],
  "chartCursor": {
    "categoryBalloonEnabled": false,
    "cursorAlpha": 0,
    "zoomable": false
  },
  "categoryField": "country",
  "categoryAxis": {
    "gridPosition": "start",
    "gridAlpha": 0,
    "tickPosition": "start",
    "tickLength": 20
  },
  "export": {
    "enabled": true,
    "menu": []
    
    /*
      
,
    "postDataProcessor": function(type, data) {
      // CSV
      if (type == "ARRAY") {
        data.push({
          "foo": "bar"
        });
        
      } else if (type == "SHEETS") {
        data.SheetNames.push("foobar");
        
        // Unshift would add the sheet at first place
        // data.SheetNames.unshift("foobar");

        data.Sheets["foobar"] = [{
          "foo": "bar1",
          "bar": "foo1"
        }, {
          "foo": "bar2",
          "bar": "foo2"
        }];
      }
      return data;
    }      
      
     */
  }

});


 
    function cambio(data) {
     var type = "SHEETS";
      if (type === "ARRAY") {
        data.push({
          "foo": "bar"
        });
        
      } else if (type === "SHEETS") {
        data.SheetNames.push("foobar");
        
        // Unshift would add the sheet at first place
        // data.SheetNames.unshift("foobar");

        data.Sheets["foobar"] = [{
          "foo": "bar1",
          "bar": "foo1"
        }, {
          "foo": "bar2",
          "bar": "foo2"
        }];
      }
      return data;
    }    
 
      
function exportXLSX() {
  chart.export.toXLSX({}, function(data) {
      
    var data2 =  cambio(data);
    this.download(data2, this.defaults.formats.XLSX.mimeType, "amCharts.xlsx");
  });
}