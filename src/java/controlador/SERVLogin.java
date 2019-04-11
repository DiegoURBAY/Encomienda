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
    //private static String registrar = "/RegistrarCliente.jsp";
    private static String index = "/index.jsp";
    ClienteDAO clienteDAO = new ClienteDAO();
    RequestDispatcher rd = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String vista = "";
        String action = request.getParameter("action");
        
        if(action.equalsIgnoreCase("adios")){
        HttpSession sesion = request.getSession();
        sesion.invalidate();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
        }
        
        if(action.equalsIgnoreCase("ingresar")){
            vista = ingresar;
            rd = request.getRequestDispatcher(vista);
                      rd.forward(request, response); 
        }    
/*        
        if(action.equalsIgnoreCase("registrar")){
            vista = registrar;
            rd = request.getRequestDispatcher(vista);            
            
        }
        */
        if(action.equalsIgnoreCase("regresar")){
            vista = index;
            rd = request.getRequestDispatcher(vista);
                      rd.forward(request, response); 
        }             
         
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          HttpSession sesion = request.getSession();
            String usuario = null;
            String email;
            String contra;
            int nivel = 0;
            Acceso acc = new Acceso();            

                        
            if(request.getParameter("btnIniciar")!=null || request.getParameter("btnRecuperar")!=null){
                
                email = request.getParameter("txtEmail");
                contra = request.getParameter("txtContra");
                nivel = acc.validar(email, contra);
                try {
                    //nombre = acc.ExtraerNombre(email);
                    usuario = clienteDAO.UsuarioByEmail(email);
                } catch (SQLException ex) {
                    Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                   Envio envio = new Envio();

                if(nivel > 0){
                    
                    request.setAttribute("usuario", usuario);
                    request.setAttribute("nivel", nivel);
                    rd = request.getRequestDispatcher("index.jsp");  
                  //  rd = request.getRequestDispatcher("RegistrarCliente.jsp");
                     rd.forward(request, response);  
                } 
                /*
                if(contra==null){
                    nivel = 0;
                    email = request.getParameter("txtEmail");  
                    try {
                        contra = clienteDAO.ContraseñaByEmail(email);
                    } catch (SQLException ex) {
                        Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {                    
                        envio.RecuperarContraseña(email, contra);
                    } catch (MessagingException ex) {
                        Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        contra = clienteDAO.ContraseñaByEmail(email);
                    } catch (SQLException ex) {
                        Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     rd = request.getRequestDispatcher("index.jsp");
                     rd.forward(request, response);  
                }
*/
                else{
                    rd = request.getRequestDispatcher("index.jsp");
                     rd.forward(request, response);  
                }
                
                
                

            }
            
                 
      
                    
                    String index = "/index.jsp";
                     String vista = index;
       /*              
            if(request.getParameter("btnRecuperar")!=null){                                                               

                try {
                   
                    email = request.getParameter("txtEmail");                      
                    contra = clienteDAO.ContraseñaByEmail(email);
                    envio.RecuperarContraseña(email, contra);
                } catch (MessagingException ex) {
                    Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                    response.sendRedirect(request.getContextPath() + "/SERVLogin?action=adios");          
            }                       
            
        */   
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
