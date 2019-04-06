<%-- 
    Document   : CuentaCliente
    Created on : 05/04/2019, 09:38:02 PM
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
HttpSession sesion = request.getSession();
    if(sesion.getAttribute("nivel")==null){
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="POST" action="SERVCliente">  
            <h1>BIENVENIDO <%= sesion.getAttribute("nombre") %></h1>
            <input type="text" name="txtNombre" value="<c:out value="${cliente.nombre}" />"> Nombre <br>

            <a href="SERVCliente?action=editar&id=<c:out value="<%= sesion.getAttribute("nivel") %>" />"  class="btn btn-warning btn-sm" >Editar</a>   
        </form>
    </body>
</html>
