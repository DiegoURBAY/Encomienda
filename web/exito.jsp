
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="POST" action="SERVCliente">
            <input type="text" id="cliente" name="txtIdCliente" value="<%= request.getAttribute("idEncomienda") %>"  >
            <input type="text" id="cliente2" name="txtIdCliente2" value="<%= request.getAttribute("idCliente") %>"  >
            <a href="SERVEncomienda?action=refresh" > Exito!!! Continuar</a>
        </form>       
    </body>
</html>
