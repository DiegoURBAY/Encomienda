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
        <title>Vehiculos</title>                    
        <jsp:include page="navbar.jsp"/>             
    </head>    
    <body>
        <div class="container">
            <input type="hidden" value="<%= sesion.getAttribute("idEncomienda") %>">
            <input type="hidden" value="<%= sesion.getAttribute("nivel") %>">
            <input type="hidden" value="<%= sesion.getAttribute("peso") %>">
            <h1>Lista de Vehiculos</h1>
            <h4> Peso de encomienda: <%= sesion.getAttribute("peso") %></h4>
                <hr>                                
                <a class="btn btn-primary btn-lg" href="SERVVehiculo?action=refresh&peso=<c:out value="<%= sesion.getAttribute("peso") %>"/>"> Actualizar Lista</a>
                <a class="btn btn-info btn-lg" href="SERVEncomienda?action=refresh&nivel=<c:out value="<%= sesion.getAttribute("nivel") %>"/>">Mis Encomiendas</a>
                <a class="btn btn-info btn-lg" href="SERVTipoEncomienda?action=refresh2&idEncomienda=<c:out value="<%= sesion.getAttribute("idEncomienda") %>"/>">Mis Tipos de Envios</a>
                <br>
                <br>                
            <form method="POST">
                <table id="tableUser" class="display responsive nowrap" style="width:100%">
                    <thead>
                        <tr>
                            <th class="text-center">ID</th>                
                            <th class="text-center">PLACA</th>      
                            <th class="text-center">CAPACIDAD</th>
                            <th class="text-center">ACCIONES</th>
                        </tr>                        
                    </thead>
                    <tbody>
                        <c:forEach items="${vehiculo}" var="vehiculo">
                            <tr>
                                <td>
                                        <c:out value="${vehiculo.id}"/>
                                </td>                                
                                <td>
                                        <c:out value="${vehiculo.placa}"/>
                                </td>
                                <td>
                                        <c:out value="${vehiculo.capacidad}"/>
                                </td>                                                               
                                <td class="text-center">                                    
                                    <a href="SERVPedido?action=refresh&idVehiculo=<c:out value="${vehiculo.id}"/>&placaVehiculo=<c:out value="${vehiculo.placa}"/>&idCliente=<c:out value="<%= sesion.getAttribute("nivel") %>"/>&idEncomienda=<c:out value="<%= sesion.getAttribute("idEncomienda") %>"/>"  class="btn btn-warning btn-sm" onclick="return confirm('¿Seguro que su elección?')" > Elegir</a>                                       
                                </td>
                            </tr>
                        </c:forEach>                         
                    </tbody>                                               
                </table>                 
            </form>
        </div>        
    </body>
</html>
