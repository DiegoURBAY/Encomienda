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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
                    RequestDispatcher rd = null;

        request.setCharacterEncoding("UTF-8");
        
        String identificador = request.getParameter("txtIdentificador");
        String nombre = request.getParameter("txtNombre");
        String contraseña = request.getParameter("txtContraseña");
        String email = request.getParameter("txtEmail");
        String telefono = request.getParameter("txtTelefono");
        
        Cliente cliente = new Cliente();
        cliente.setIdentificador(identificador);
        cliente.setNombre(nombre);
        cliente.setContraseña(contraseña);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        
        
        ClienteDAO clienteDAO = new ClienteDAO();
        Envio envio = new Envio();
        
        if(request.getParameter("btnInsertar")!= null){
                        try {
                            clienteDAO.insertar(cliente);
                            envio.EnviarCorreo(email);
                        } catch (Exception ex) {
                            Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
            rd = request.getRequestDispatcher("exito.jsp");
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
