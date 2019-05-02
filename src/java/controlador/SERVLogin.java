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
    
    private static String registrar_encomienda = "/SERVEncomienda?action=refreshPrueba";
    
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
 response.setContentType("text/html;charset=UTF-8");
        String vista = "";
        String action = request.getParameter("action");
        
        if(action.equalsIgnoreCase("adios")){
        HttpSession sesion = request.getSession(false);        
        request.setAttribute("idUsuario", null);
            rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response); 
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
        PrintWriter out = response.getWriter();
        
            String eemail = null;
            String ccontra = null;
            if(request.getParameter("eemail2")!=null){
                 eemail = request.getParameter("eemail2");
            }                
            if( request.getParameter("ccontra2")!=null ){
                ccontra = request.getParameter("ccontra2");
            }
        
        if(eemail != null && ccontra != null){
            
            String report = null;

            try {
                //report = VerificarEmail2(eemail);
                report = VerificarEmail2(eemail,ccontra);
            } catch (SQLException ex) {
                Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

            response.setContentType("text/plain");
            out.println("" + report + "");
            out.flush();
            out.close();
        }

        HttpSession sesion = request.getSession();
        
        Cliente cliente = new Cliente();
        
        String usuario = null;
        String email;
        String contra;
        String select;
        int nivel = 0;
        int idUsuario = 0;
        Acceso acc = new Acceso();            
        String forward = "";

        if(request.getParameter("btnIniciarUsuario")!=null){
            email = request.getParameter("txtEmail");
            contra = request.getParameter("txtContra");
            idUsuario = acc.getClienteID(email, contra);
                
            try {
                cliente = clienteDAO.ConsultarByEmail(email);
                usuario = cliente.getUsuario();
                nivel = cliente.getNivel();
                //usuario = clienteDAO.UsuarioByEmail(email);
            } catch (SQLException ex) {
                Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

            Envio envio = new Envio();
            
            //nivel 1 = administrador

            if(idUsuario > 0){

                sesion.setAttribute("idUsuario", idUsuario);
                sesion.setAttribute("usuario", usuario);
                
                if(nivel == 2 ){
                    response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=refreshPrueba"); 
                }
                if(nivel == 1 ){
                    sesion.setAttribute("nivel", nivel);
                    response.sendRedirect(request.getContextPath() + "/SERVCliente?action=refreshCliente"); 
                }                
              
            } 
            else{
                rd = request.getRequestDispatcher("index.jsp");
                 rd.forward(request, response);  
            }                
        }
                
            
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
     
    
    private String VerificarEmail2(String eemail, String ccontra) throws SQLException {
        String report2 = null;
        
        if(ccontra.equals("")){
            report2 = "";
        }
        else if(clienteDAO.ConsultarEmailContra(eemail, ccontra)){
            report2 = "Ya existe";
        }
        else if(!clienteDAO.ConsultarEmailContra(eemail, ccontra)){
            report2 = "Libre";
        }
        return report2;

    }       
}
