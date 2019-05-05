<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>

<%
HttpSession sesion = request.getSession();
    if(sesion.getAttribute("usuario")==null){
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de tipos de Envios</title>                    
        <jsp:include page="navbar.jsp"/>             
    </head>    
    <body>
        <div class="container">
            <input type=hidden value="<%= sesion.getAttribute("idEncomienda") %>"><br>
            <input type="hidden" value="<%= sesion.getAttribute("nivel") %>"><br>
            <input type="hidden" value="<%= sesion.getAttribute("peso") %>">
            <input type="text" value="<%= sesion.getAttribute("usuario") %>">
            <h1>Lista de tipos de Envios  </h1>
             <h4> Encomienda #: <%= sesion.getAttribute("idEncomienda") %></h4>
                <hr>                                
                <br>
                <br>                
            <form method="POST">
                <table id="tableUser" class="display responsive nowrap" style="width:100%">
                    <thead>
                        <tr>
                            <th class="text-center">ID</th>                
                            <th class="text-center">TIPO</th>      
                            <th class="text-center">ALTURA</th>
                            <th class="text-center">ANCHURA</th>
                            <th class="text-center">LARGO</th>
                            <th class="text-center">CANTIDAD</th>
                            <th class="text-center">PESO</th>
                            <th class="text-center">PRECIO</th>
                            <th class="text-center">ACCIONES</th>
                        </tr>                        
                    </thead>
                    <tbody>
                        <c:forEach items="${tipoEncomienda}" var="tipoEncomienda">
                            <tr>
                                <td>
                                        <c:out value="${tipoEncomienda.id}"/>
                                </td>                                
                                <td>
                                        <c:out value="${tipoEncomienda.tipo}"/>
                                </td>
                                <td>
                                        <c:out value="${tipoEncomienda.altura}"/>
                                </td>                                   
                                <td>
                                        <c:out value="${tipoEncomienda.anchura}"/>
                                </td>
                                <td>
                                        <c:out value="${tipoEncomienda.largo}"/>
                                </td>
                                <td>
                                        <c:out value="${tipoEncomienda.cantidad}"/>
                                </td>
                                <td>
                                        <c:out value="${tipoEncomienda.peso}"/>
                                </td>                                
                                <td>
                                        <c:out value="${tipoEncomienda.precio}"/>
                                </td>                               
                                <td class="text-center">                                    
                                    <a href="SERVTipoEncomienda?action=edit&id=<c:out value="${tipoEncomienda.id}"/>"  class="btn btn-warning btn-sm">Editar</a>   
                                    <a href="SERVTipoEncomienda?action=delete&id=<c:out value="${tipoEncomienda.id}"/>&usuario=<%= sesion.getAttribute("usuario") %>" onclick="return confirm('¿Está seguro que desea eliminar el registro?')" class="btn btn-danger btn-sm">Eliminar</a>
                                </td>
                            </tr>
                        </c:forEach>                         
                    </tbody>                                               
                </table>                 
            </form>
        </div>        
    </body>
</html>
