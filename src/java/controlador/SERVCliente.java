/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.ClienteDAO;
import dao.Envio;
import entidad.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author usuario
 */
public class SERVCliente extends HttpServlet {
    
    private static final String insert= "/RegistrarCliente.jsp";
    private static final String edit = "/EditarCliente2.jsp";
    private static final String list_cliente = "/navegador.jsp";
    private static final String action_listar = "/SERVCliente?action=listar";
    
        ClienteDAO clienteDAO = new ClienteDAO();
    Cliente cliente = new Cliente();
    
    RequestDispatcher rd = null;
  String vista = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SERVCliente</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SERVCliente at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        String action = request.getParameter("action");
                                      
        if(action.equalsIgnoreCase("buscar")){
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                
                vista = edit;
                cliente = clienteDAO.BuscarPorId(id);
                request.setAttribute("cliente", cliente);
                rd = request.getRequestDispatcher(vista);
                rd.forward(request, response);
                
            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(action.equalsIgnoreCase("insert")) {        
            vista = insert;
            rd = request.getRequestDispatcher(vista);
            rd.forward(request, response);
        }        
        else if(action.equalsIgnoreCase("listar")){
            vista = list_cliente;
            try {   
                request.setAttribute("cliente", clienteDAO.consultar());
                rd = request.getRequestDispatcher(vista);
                rd.forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
     
        int id = 0;
        if(request.getParameter("txtId")!= null){
            id = Integer.parseInt(request.getParameter("txtId"));
        }
        String identificador = request.getParameter("txtIdentificador");
        String nombre = request.getParameter("txtNombre");
        String email = request.getParameter("txtEmail");
        String usuario = request.getParameter("txtUsuario");
        String contraseña = request.getParameter("txtContraseña");        
        String telefono = request.getParameter("txtTelefono");
        
        
        cliente.setIdentificador(identificador);
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setUsuario(usuario);
        cliente.setContraseña(contraseña);        
        cliente.setTelefono(telefono);
                
        Envio envio = new Envio();
 
         
        if(request.getParameter("btnRegistrar")!= null){
            try {
                               
                clienteDAO.insertar(cliente);
                envio.EnviarCorreo(email);

            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                 

        }

        if(request.getParameter("btnEditar")!=null){
            try {
               cliente.setId(id);
               clienteDAO.modificar(cliente);

            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
          
        }
        
         response.sendRedirect(request.getContextPath() + "/SERVCliente?action=listar");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
