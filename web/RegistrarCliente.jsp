<%-- 
    Document   : RegistrarCliente
    Created on : 03/04/2019, 10:19:49 PM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <form method="POST" action="SERVCliente">
            <input type="text" name="txtIdentificador" > Identificador <br>
            <input type="text" name="txtNombre" > Nombre <br>
            <input type="text" name="txtContraseña" > Contraseña <br>
            <input type="text" name="txtEmail" > Email <br>
            <input type="text" name="txtTelefono" > Telefono <br>
            <input type="submit" name="btnInsertar" value="Registrar"> 
        </form>
    </body>
</html>
