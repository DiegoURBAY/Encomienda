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
        <title>Lista de Encomienda  </title>
        <jsp:include page="navbarCliente.jsp"/>             
    </head>    
    <body>
        <div class="container">
            <input type="text" value="<%= sesion.getAttribute("nivel") %>">
            <input type="text" value="<%= sesion.getAttribute("usuario") %>">
            <input type="text" value="<%= sesion.getAttribute("email") %>"> <br>
           <input type="text" id="cliente" value="<%= sesion.getAttribute("idCliente") %>">
           
                <h1>Lista de Encomiendas  Modo Cliente</h1>
                <hr>                
                <br>
                <br>                
            <form method="POST">
                <table id="tableUser" class="display responsive nowrap" style="width:100%">
                    <thead>
                        <tr>
                            <th class="text-center">ID</th>                
                            <th class="text-center">ORIGEN</th>      
                            <th class="text-center">DESTINO</th>
                            <th class="text-center">FECHA DE REGISTRO</th>
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
                                           <c:out value="${encomienda.origenS}"   />                                                                                           
                                </td>
                                <td>
                                        <c:out value="${encomienda.destinoS}"/>
                                </td> 
                                <td>
                                        <c:out value="${encomienda.fechaRegistroTimeString}"/>
                                </td>                                    
                                <td class="text-center">
                                    <!-- 
                                    
                                    -->
                                    <a href="SERVTipoEncomienda?action=refresh2&idEncomienda=<c:out value="${encomienda.id}"/>"  class="btn btn-secondary btn-sm">Ver tipo de envio</a>
                                </td>                                 
                                <td class="text-center">                                    
                                    <!-- 
                                    <a href="SERVEncomienda?action=edit&id=<c:out value="${encomienda.id}"/>&usuario=<c:out value="<%= sesion.getAttribute("usuario") %>" />"  class="btn btn-warning btn-sm">Editar</a>   
                                    <a href="SERVEncomienda2?action=delete2&idEncomienda=<c:out value="${encomienda.id}"/>" onclick="return confirm('¿Seguro que desea eliminar el registro?')" class="btn btn-danger btn-sm">Eliminar</a>                                    
                                    -->
                                    <a href="SERVEncomienda2?action=edit&id=<c:out value="${encomienda.id}"/>"  class="btn btn-warning btn-sm">Editar</a>   
                                    <a href="SERVEncomienda2?action=delete&id=<c:out value="${encomienda.id}"/>" onclick="return confirm('¿Seguro que desea eliminar el registro?')" class="btn btn-danger btn-sm">Eliminar</a>
                                </td>
                            </tr>
                        </c:forEach>                         
                    </tbody>                                               
                </table>                 
            </form>
        </div>        
    </body>
</html>
