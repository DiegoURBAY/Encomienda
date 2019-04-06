<%-- 
    Document   : index
    Created on : 03/04/2019, 08:52:29 PM
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
        <form action="SERVLogin" method="POST">
            <input type="text" name="txtEmail">Email<br>
            <input type="text" name="txtContra">Contra<br>
            <input type="submit" name="btnIniciar" value="Ingresar"><br>
            <input type="submit" name="btnRegistrar" value="Registrar"><br>            
        </form>
                
    <%
        HttpSession sesion = request.getSession();
        int nivel = 0;
        if(request.getAttribute("nivel")!=null){
            nivel = (Integer)request.getAttribute("nivel");
            if(!(nivel == 0)){
                sesion.setAttribute("email", request.getAttribute("email"));
                sesion.setAttribute("nivel", nivel);
                response.sendRedirect("CuentaCliente.jsp");
            }
        }
        if(request.getParameter("cerrar")!=null){
            session.invalidate();
        }
    %>           
    </body>
        
</html>
