
package controlador;

import dao.ClienteDAO;
import dao.Envio;
import entidad.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SERVCliente extends HttpServlet {
    
    private static final String insert= "/RegistrarCliente.jsp";
    private static final String edit = "/EditarCliente.jsp";
    private static final String list_cliente = "/navegador.jsp";
    private static final String action_listar = "/SERVCliente?action=listar";
    private ClienteDAO clienteDAO;   
    Cliente cliente = new Cliente();
    
   public SERVCliente() {
        clienteDAO = new ClienteDAO(){};
    }         
    
    RequestDispatcher rd = null;
  String vista = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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
        
        
        if(action.equalsIgnoreCase("buscarPrueba")){
               HttpSession sesion = request.getSession();
               Cliente cliente = new Cliente();
               
            int idCliente = 0;
            String usuario = null;
            
            if(sesion.getAttribute("idCliente")!=null){
                  idCliente = Integer.parseInt(request.getParameter("idCliente"));
            }           
            try {                
                cliente = clienteDAO.BuscarPorId(idCliente);
                usuario = cliente.getUsuario();
            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }            
            if(idCliente == 0){
                response.sendRedirect("indexPrueba.jsp");
            }
            else if(idCliente != 0){
                sesion.setAttribute("idCliente", idCliente);
                sesion.setAttribute("usuarioPrueba", usuario);
                request.setAttribute("cliente", cliente);
                RequestDispatcher view = request.getRequestDispatcher("EditarCliente1.jsp");
                view.forward(request, response);                  
            }

        }        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        
         PrintWriter out = response.getWriter();
        
        if(request.getParameter("iidentificador")!=null){
            String iidentificador = request.getParameter("iidentificador");
            String report = null;
            try {
                report = VerificarIdentificador(iidentificador);
            } catch (SQLException ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

            response.setContentType("text/plain");
            out.println("" + report + "");
                            
            out.flush();
            out.close();
        }         
        if(request.getParameter("eemail")!=null){
            String eemail = request.getParameter("eemail");
            String report = null;
            try {
                report = VerificarEmail(eemail);
            } catch (SQLException ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

            response.setContentType("text/plain");
            out.println("" + report + "");
            out.flush();
            out.close();
        }      
        
        if(request.getParameter("uusuario")!=null){
            String uusuario = request.getParameter("uusuario");
            String report = null;
            try {
                report = VerificarUsuario(uusuario);
            } catch (SQLException ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

            response.setContentType("text/plain");
            out.println("" + report + "");
            out.flush();
            out.close();
        }          

      
        String id = request.getParameter("txtId");
        String identificador = request.getParameter("txtIdentificador");
        String nombre = request.getParameter("txtNombre");
        String email = request.getParameter("txtEmail");
        String usuario = request.getParameter("txtUsuario");
        String contra = request.getParameter("txtContrase");        
        String telefono = request.getParameter("txtTelefono");
        
        
        cliente.setIdentificador(identificador);
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setUsuario(usuario);
        cliente.setContraseña(contra);        
        cliente.setTelefono(telefono);
                
        Envio envio = new Envio();
 
        
        if(request.getParameter("btnEditarPrueba")!=null){
        
        cliente.setId(Integer.parseInt(id));
            try {
                clienteDAO.modificar(cliente);
            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
         
          response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=cerrar"); 
        }
      /*   
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
*/
        /*
        if (id == null || id.isEmpty()) {
            try {
                clienteDAO.insertar(cliente);
                envio.EnviarCorreo(email);
            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           //     response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=refresh&nivel="+id); 
            
        } else {                    
            try {
                cliente.setId(Integer.parseInt(id));
                clienteDAO.modificar(cliente);
            } catch (Exception ex) {
               Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);                  
            }
             
        }             
                       response.sendRedirect(request.getContextPath() + "/index.jsp?cerrar=true"); 
             
*/
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
 private String VerificarIdentificador(String identificador) throws SQLException {
        String report = null;
        
        if(identificador.equals("")){
            report = "";
        }
        else if(clienteDAO.ConsultarRUCDNI(identificador)){
            report = "Ya existe";
        }
        else {
            report = "Libre";
        }
        return report;
    }                

    private String VerificarEmail(String eemail) throws SQLException {
        String report2 = null;
        
        if(eemail.equals("")){
            report2 = "";
        }
        else if(clienteDAO.ConsultarEmail(eemail)){
            report2 = "Ya existe";
        }
        else {
            report2 = "Libre";
        }
        return report2;

    }     
    
    private String VerificarUsuario(String uusuario) throws SQLException {
        String report3 = null;
        if(uusuario.equals("")){
            report3 = "";
        }
        else if(clienteDAO.ConsultarUsuario(uusuario)){
            report3 = "Ya existe";
        }
        else {
            report3 = "Libre";
        }
        return report3;

    }       
    

}
