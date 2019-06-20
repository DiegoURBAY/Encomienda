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
        <title>Lista de Cliente</title>
        <jsp:include page="navbar.jsp"/>
        <script src="js/validarListarCliente.js" type="text/javascript"></script>
    </head>    
    <body> 
        <div class="container">
                <h1>Lista de Clientes</h1>
                <hr>
                   
                <br>
                <br>
            <form method="POST">
                <table id="tableUser" class="display responsive nowrap" style="width:100%">
                    <thead>
                        <tr>
                           <th class="text-center">ID</th>           
                           <th class="text-center">IDENTIFICADOR</th> 
                           <th class="text-center">NOMBRES</th>                                                               
                           <th class="text-center">EMAIL</th>      
                           <th class="text-center">USUARIO</th>      
                           <th class="text-center">CONTRASEÑA</th>      
                           <th class="text-center">TELEFONO</th>
                           <th class="text-center">ENVIOS</th>
                           <th class="text-center">ACCIONES</th>
                       </tr>                       
                    </thead>
                    <tbody>
                        <c:forEach items="${cliente}" var="item">
                            <tr>
                              <td>
                                        <c:out value="${item.id}"/>
                                </td>
                                <td>
                                        <c:out value="${item.identificador}"/>
                                </td>
                                <td>
                                        <c:out value="${item.nombre}"/>
                                </td>                                                     
                                <td>
                                        <c:out value="${item.email}" />
                                </td>                    
                                <td>
                                        <c:out value="${item.usuario}"/>
                                </td>                    
                                <td>
                                        <c:out value="${item.contraseña}"/>
                                </td>                                        
                                <td>
                                        <c:out value="${item.telefono}"/>
                                </td>   
                                <td class="text-center"> 
                                     <a href="SERVEncomienda2?action=refresh&id=<c:out value="${item.id}"/>"  class="btn btn-secondary btn-sm">Ver Envios</a>
                                </td>                                     
                                <td class="text-center">
                                    <a href="SERVCliente2?action=edit&email=<c:out value="${item.email}"/>"  class="btn btn-warning btn-sm" >Editar</a>                                      
                                    <a href="SERVCliente2?action=delete&email=<c:out value="${item.email}"/>" class="btn btn-danger btn-sm" onclick="return confirm('¿Está seguro que desea eliminar el registro?')">Eliminar</a>                                    
                                </td>
                            </tr>
                        </c:forEach>                         
                    </tbody>                                             
                </table>                 
            </form> 
        </div>        
    </body>
</html>
