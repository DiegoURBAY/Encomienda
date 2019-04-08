<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>

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
        <title>Lista de Encomienda</title>                    
        <jsp:include page="navbar.jsp"/>             
    </head>    
    <body>
        <div class="container">
                <h1>Lista de Encomiendas</h1>
                <hr>                
                    <a class="btn btn-success btn-lg" href="SERVEncomienda?action=insert&id=<c:out value="<%= sesion.getAttribute("nivel") %>"/>">Nuevo Registro</a>
                    <a class="btn btn-primary btn-lg" href="SERVEncomienda?action=refresh">Actualizar Lista</a>   
                    <a class="btn btn-info btn-lg" href="SERVTipoConductor?action=refresh">Tipo de Estado</a>                         
                <br>
                <br>                
            <form method="POST">
                <table id="tableUser" class="display responsive nowrap" style="width:100%">
                    <thead>
                        <tr>
                            <th class="text-center">ID</th>                
                            <th class="text-center">ORIGEN</th>      
                            <th class="text-center">DESTINO</th>
                            <th class="text-center">ENVIO</th>
                            <th class="text-center">LLEGADA</th>
                            <th class="text-center">ENVIOS</th>
                            <th class="text-center">ACCIONES</th>
                        </tr>                        
                    </thead>
                    <tbody>
                        <c:forEach items="${encomienda}" var="encomienda">
                            <tr>
                                <td>
                                        <c:out value="${encomienda.id}"/>
                                </td>                                
                                <td>
                                        <c:out value="${encomienda.origen}"/>
                                </td>
                                <td>
                                        <c:out value="${encomienda.destino}"/>
                                </td>                                   
                                <td>
                                        <c:out value="${encomienda.envio}"/>
                                </td>
                                <td>
                                        <c:out value="${encomienda.llegada}"/>
                                </td>                                                                                             
                                <td class="text-center">
                                    <a href="SERVTipoEncomienda?action=refresh"  class="btn btn-secondary btn-sm">Añadir Envio</a>                                       
                                </td>                                 
                                <td class="text-center">                                    
                                    <a href="SERVEncomienda?action=edit&id=<c:out value="${encomienda.id}"/>"  class="btn btn-warning btn-sm">Editar</a>   
                                    <a href="SERVEncomienda?action=delete&id=<c:out value="${encomienda.id}"/>" onclick="return confirm('¿Está seguro que desea eliminar el registro?')" class="btn btn-danger btn-sm">Eliminar</a>
                                </td>
                            </tr>
                        </c:forEach>                         
                    </tbody>                                               
                </table>                 
            </form>
        </div>        
    </body>
</html>
