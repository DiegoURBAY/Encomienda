/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.Acceso;
import dao.ClienteDAO;
import dao.Envio;
import entidad.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SERVLogin extends HttpServlet {
    
    private static String ingresar= "/login.jsp";   
    private static String registrar_encomienda = "/SERVEncomienda2?action=refreshPrueba";
    private static String index = "/index.jsp";
    private static String error = "/error.jsp";
    private static String ingresar_administrador = "/SERVCliente2?action=refresh";
    private static String ingresar_cliente = "/SERVEncomienda2?action=insert";
    
   // String cookie = "mycookie=test; Secure; HttpOnly";
    
    ClienteDAO clienteDAO = new ClienteDAO();

    RequestDispatcher rd = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       // response.addHeader("Set-Cookie", cookie);                
        response.addHeader("X-XSS-Protection", "1; mode=block");
        response.addHeader("X-Frame-Options", "DENY");
        response.setContentType("text/html;charset=UTF-8");
    
        try (PrintWriter out = response.getWriter()) {

        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    //   response.addHeader("Set-Cookie", cookie); 
        response.addHeader("X-XSS-Protection", "1; mode=block");
        response.addHeader("X-Frame-Options", "DENY");
        String action = request.getParameter("action");
        String vista = null;        
        try {
            if(action.equalsIgnoreCase("inicio")){
                vista = index;
            }
        } catch (Exception ex) {
            vista = error;        
            Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            RequestDispatcher view = request.getRequestDispatcher(vista);
            view.forward(request, response);     
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       // response.addHeader("Set-Cookie", cookie); 
        response.addHeader("X-XSS-Protection", "1; mode=block");
        response.addHeader("X-Frame-Options", "DENY");

        int nivel;
        String vista = null;
                 
        try {
            if(request.getParameter("btnIniciarUsuario")!=null){
                HttpSession sesion = request.getSession();   
                Acceso acc = new Acceso();     
                
                String email = request.getParameter("txtEmail");
                String contra = request.getParameter("txtContra");
                int idUsuario = acc.getClienteID(email, contra);

                Cliente cliente = clienteDAO.ConsultarByEmail(email);
                String usuario = cliente.getUsuario();
                nivel = cliente.getNivel();

                //nivel 1 = administrador

                if(idUsuario > 0){
   
                    sesion.setAttribute("usuario", usuario);
                    
                    if(nivel == 2 ){                       
                        //SERVEncomienda2?action=insert
                        vista = ingresar_cliente;                        
                    }
                    if(nivel == 1 ){
                        //SERVCliente2?action=refresh
                        vista = ingresar_administrador;                        
                    }                

                } 
                else{
                    vista = index;
                }                
            }
        } catch (SQLException ex) {
            vista = index;
            Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            response.sendRedirect(request.getContextPath() + vista);            
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
     

}
