/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.Acceso;
import dao.ClienteDAO;
import entidad.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SERVLogin extends HttpServlet {
    
    private static String ingresar= "/login.jsp";
    private static String registrar = "/RegistrarCliente.jsp";
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
        
        if(action.equalsIgnoreCase("ingresar")){
            vista = ingresar;
            rd = request.getRequestDispatcher(vista);
                      
        }        
        if(action.equalsIgnoreCase("registrar")){
            vista = registrar;
            rd = request.getRequestDispatcher(vista);            
            
        }
        if(action.equalsIgnoreCase("regresar")){
            vista = index;
            rd = request.getRequestDispatcher(vista);
                      
        }                
        rd.forward(request, response);  
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            String usuario = null;
            String email;
            String contra;
            int nivel = 0;
            Acceso acc = new Acceso();            
          
                        
            if(request.getParameter("btnIniciar")!=null){
                
                email = request.getParameter("txtEmail");
                contra = request.getParameter("txtContra");
                nivel = acc.validar(email, contra);
                try {
                    //nombre = acc.ExtraerNombre(email);
                    usuario = clienteDAO.UsuarioByEmail(email);
                } catch (SQLException ex) {
                    Logger.getLogger(SERVLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                

                if(nivel > 0){
                    
                    request.setAttribute("usuario", usuario);
                    request.setAttribute("email", email);
                    request.setAttribute("nivel", nivel);
                    rd = request.getRequestDispatcher("login.jsp");  
                }                
                else{
                    rd = request.getRequestDispatcher("error.jsp");
                }

            }
            if(request.getParameter("btnRegistrar")!=null){
                rd = request.getRequestDispatcher("RegistrarCliente.jsp");
            }          
            
            rd.forward(request, response);  
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
