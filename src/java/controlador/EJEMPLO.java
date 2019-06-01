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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author usuario
 */
public class EJEMPLO extends HttpServlet {
    

    private ClienteDAO clienteDAO;


            
     public EJEMPLO() {
        clienteDAO = new ClienteDAO(){};
    }         
                

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
        processRequest(request, response);
        
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
       /*
    
        String identificador = request.getParameter("ruc_dni");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String usuario = request.getParameter("usuario");
        String contra = request.getParameter("contra");        
        String telefono = request.getParameter("celular");            
        */

        String identificador = request.getParameter("txtIdentificador");
        String nombre = request.getParameter("txtNombre");
        String email = request.getParameter("txtEmail");
        String usuario = request.getParameter("txtUsuario");
        String contra = request.getParameter("txtContrase");        
        String telefono = request.getParameter("txtTelefono");  
        
        
        Cliente cliente = new Cliente();
        cliente.setIdentificador(identificador);
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setUsuario(usuario);
        cliente.setContraseña(contra);        
        cliente.setTelefono(telefono);   
        

        HttpSession sesion = request.getSession();        

       
       Envio envio = new Envio();
        
        String usuario_de_login;
        String action = request.getParameter("action");      
        String estado = ""; 
        String mensaje = "";
                
        
        try {
          
          if (action.equalsIgnoreCase("insert")) {
               
                cliente.setNivel(2);
                clienteDAO.insertar(cliente);         
                Thread.sleep(2000);
                estado = "ok";
                mensaje = "Grabación exitosa";
            }
            else if (action.equalsIgnoreCase("edit")){
                if(sesion.getAttribute("usuario")!=null){
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente_buscado = clienteDAO.BuscarPorUsuario(usuario_de_login);
                    
                    String id = request.getParameter("txtId");
                     

                    cliente.setId(Integer.parseInt(id)); 
                    clienteDAO.modificar(cliente);
                    int nivel = cliente_buscado.getNivel();
                    estado = "ok";
                    mensaje = "Edición exitosa";                    
                    
                    if(nivel == 2){
                        
                    }
                    else if(nivel == 1){
                        
                    }
                }
            }

        } catch (SQLException e) {
            estado = "error";
            mensaje = "error al grabar";
            try {
                throw e;
            } catch (SQLException ex) {
                Logger.getLogger(SERVCliente2.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(SERVCliente2.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{                                   
            try {
                JSONObject jsonObject=new  JSONObject();
                jsonObject.put("estado", estado);
                jsonObject.put("mensaje", mensaje);
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setCharacterEncoding("utf8");
                response.setContentType("application/json");
                out.print(jsonObject);                         
            } catch (JSONException ex) {
                Logger.getLogger(SERVCliente2.class.getName()).log(Level.SEVERE, null, ex);
            }

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
