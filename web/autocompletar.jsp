<%-- 
    Document   : autocompletar
    Created on : 05/05/2019, 01:13:28 PM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <title>JSP Page</title>
        
  <script>
                          
    $(function() {
        /*
        $("#search").autocomplete({    
            source : function(request, response) {
                $.getJSON(
                    "SERVLugar",  
                    { nombre : request.term },
                    function ( data ) {
                        response(data);
                    }
                );
            }
        });
        */
        var availableTags = new Array();
        
        $("#tags").bind("keydown",function (event){
           var data = {nombre:$("#tags").val()} ;
                $.getJSON(
                    "SERVLugar",  
                    data,
                    function ( res, est, jqXHR ) {
                        availableTags.length = 0;
                        $.each(res, function (i, item){
                            availableTags[i] = item;
                        });
                    }
                );
        });
        
        
         $("#tags").autocomplete({
            source: availableTags           
         });
    });
    
  </script>        
    </head>
    <body>
       <div class="ui-widget">
            <label for="tags">Tags: </label>
            <input id="tags">
        </div>
        
         <input type="text" id="search" name="search" class="search"/>
    </body>
</html>
