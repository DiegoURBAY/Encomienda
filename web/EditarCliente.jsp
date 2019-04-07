<%-- 
    Document   : EditarCliente
    Created on : 05/04/2019, 09:02:28 PM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="POST" action="SERVCliente">
            <input type="text" name="txtId" value="<c:out value="${cliente.id}" />" > Id <br>
            <input type="text" name="txtIdentificador" value="<c:out value="${cliente.identificador}" />" > Identificador <br>
            <input type="text" name="txtNombre" value="<c:out value="${cliente.nombre}" />"> Nombre <br>
            <input type="text" name="txtEmail" value="<c:out value="${cliente.email}" />"> Email <br>
            <input type="text" name="txtUsuario" value="<c:out value="${cliente.usuario}" />"> usuario <br>
            <input type="text" name="txtContraseña" value="<c:out value="${cliente.contraseña}" />"> Contraseña <br>            
            <input type="text" name="txtTelefono" value="<c:out value="${cliente.telefono}" />"> Telefono <br>
            <input type="submit" name="btnEditar" value="Editar"> 
        </form>
    </body>
</html>
