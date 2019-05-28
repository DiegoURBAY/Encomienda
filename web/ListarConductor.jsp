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
        <title>Conductores</title>
        <jsp:include page="navbar.jsp"/>
    </head>    
    <body> 
        <div class="container">
            <form method="POST">    
                <div class="row">
                    <h1>Lista de Conductores</h1>
                    <hr>        
                        <a class="btn btn-success btn-lg" href="SERVConductor?action=insert">Nuevo Registro</a>
                    <br>
                    <br>
                    <div  class="col-sm-12">
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
                                   <th class="text-center">OPCION</th>
                               </tr>                       
                            </thead>
                            <tbody>                        
                                <c:forEach items="${conductor}" var="item">
                                    <tr>
                                        <td>
                                            <c:out value="${item.id}"/>
                                        </td>
                                        <td>
                                            <c:out value="${item.dni}"/>
                                        </td>
                                        <td>
                                            <c:out value="${item.nom}"/>
                                        </td>                                                     
                                        <td>
                                            <c:out value="${item.ape}"/>
                                        </td>                    
                                        <td>
                                            <c:out value="${item.lic}"/>
                                        </td>                    
                                        <td>
                                            <c:out value="${item.email}"/>
                                        </td>                                        
                                        <td>
                                            <c:out value="${item.tel}"/>
                                        </td>                                    
                                        <td class="text-center">
                                            <a href="SERVConductor?action=edit&id=<c:out value="${item.id}"/>"  class="btn btn-warning btn-sm" >Editar</a>   
                                        
                                        </td>
                                    </tr>
                                </c:forEach>                         
                            </tbody>                                             
                        </table>                        
                    </div>
                </div>                             
            </form> 
        </div>        
    </body>
</html>
