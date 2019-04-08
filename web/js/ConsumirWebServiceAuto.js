$(document).ready(
function()
{
     //Create a variable for the CarQuery object.  You can call it whatever you like.
     var carquery = new CarQuery();

     //Run the carquery init function to get things started:
     carquery.init();

     //Optional: Pass sold_in_us:true to the setFilters method to show only US models. 
     carquery.setFilters( {sold_in_us:true} );

     //Optional: initialize the year, make, model, and trim drop downs by providing their element IDs
     carquery.initYearMakeModelTrim('car-years', 'car-makes', 'car-models', 'car-model-trims');

     //Optional: initialize the make, model, trim lists by providing their element IDs.
     carquery.initMakeModelTrimList('make-list', 'model-list', 'trim-list', 'trim-data-list');

     //Optional: set minimum and/or maximum year options.
     carquery.year_select_min=1990;
     carquery.year_select_max=2019;
     
    //obtengo el valor que escogo en el select de años y lo coloco en un input 
    $('#car-years').change(function (){
        $('#añoSelecionado').val($('#car-years').val());
    });
    
    $('#car-makes').change(function (){
        $('#marcaSelecionado').val($('#car-makes').val());
    });    
    
    $('#car-models').change(function (){
        $('#modeloSelecionado').val($('#car-models').val());
    }); 

    $('#car-model-trims').change(function (){        
        var serie = $('#car-model-trims option:selected').text().trim();
        $('#serieSelecionada').val(serie);
    }); 
});