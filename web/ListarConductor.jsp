<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true"%>

<%
HttpSession sesion = request.getSession();
    if(sesion.getAttribute("idUsuario")==null){
        response.sendRedirect("index.jsp");
    }
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Conductores</title>
        <jsp:include page="navbar.jsp"/>
    </head>    
    <body> 
        <div class="container">
                <h1>Lista de Conductores</h1>
                <hr>
                   
                <br>
                <br>
            <form method="POST">
                <table id="tableUser" class="display responsive nowrap" style="width:100%">
                    <thead>
                        <tr>
                           <th class="text-center">ID</th>           
                           <th class="text-center">DNI</th> 
                           <th class="text-center">NOMBRES</th>
                           <th class="text-center">APELLIDOS</th>
                           <th class="text-center">LICENCIA</th>  
                           <th class="text-center">EMAIL</th>         
                           <th class="text-center">TELEFONO</th>
                           <th class="text-center">FECHA DE REGISTRO</th>
                           <th class="text-center">DISPONIBLIDAD</th>
                       </tr>                       
                    </thead>
                    <tbody>
                        <c:forEach items="${conductor}" var="conductor">
                            <tr>
                              <td>
                                        <c:out value="${conductor.id}"/>
                                </td>
                                <td>
                                        <c:out value="${conductor.dni}"/>
                                </td>
                                <td>
                                        <c:out value="${conductor.nom}"/>
                                </td>                                                     
                                <td>
                                        <c:out value="${conductor.ape}"/>
                                </td>                                                                
                                <td>
                                        <c:out value="${conductor.lic}"/>
                                </td>                    
                                <td>
                                        <c:out value="${conductor.email}"/>
                                </td>                                                        
                                <td>
                                        <c:out value="${conductor.tel}"/>
                                </td>                                     
                                <td class="text-center">
                                    <a href="SERVConductor?action=edit&id=<c:out value="${conductor.id}"/>"  class="btn btn-warning btn-sm" >Editar</a>   
                                    <a href="SERVConductor?action=delete&id=<c:out value="${conductor.id}"/>" onclick="return confirm('¿Está seguro que desea eliminar el registro?')"  class="btn btn-danger btn-sm">Eliminar</a>
                                </td>
                            </tr>
                        </c:forEach>                         
                    </tbody>                                             
                </table>                 
            </form> 
        </div>        
    </body>
</html>
