
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
               Cliente cliente2 = new Cliente();
               
            int idCliente = 0;
            String usuario = null;
            int nivel = 0;
            
            if(sesion.getAttribute("idUsuario")!=null){
                  idCliente = Integer.parseInt(String.valueOf(sesion.getAttribute("idUsuario")));
                  nivel = 2;
            }
            try {                
                cliente = clienteDAO.BuscarPorId(idCliente);
                usuario = cliente.getUsuario();
            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }            
            if(idCliente == 0){
                response.sendRedirect("index.jsp");
            }
            else if(idCliente > 0){
                sesion.setAttribute("idUsuario", idCliente);
                sesion.setAttribute("usuarioPrueba", usuario);
                request.setAttribute("cliente", cliente);
                RequestDispatcher view = request.getRequestDispatcher("EditarCliente1.jsp");
                view.forward(request, response);                  
            }
        }
        
        if(action.equalsIgnoreCase("buscarPorAdmin")){
               HttpSession sesion = request.getSession();
               Cliente cliente = new Cliente();
               
            int idCliente = 0;
            int nivel = 0;
            String usuario = null;
            
            if(request.getParameter("idUsuarioPorAdmin")!=null){
                  idCliente = Integer.parseInt(request.getParameter("idUsuarioPorAdmin"));
            }                 
            try {                
                cliente = clienteDAO.BuscarPorId(idCliente);
                usuario = cliente.getUsuario();
                nivel = cliente.getNivel();
            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }   
                        
/*
            
           if(idCliente == 0  || nivel >= 2){
                response.sendRedirect("index.jsp");
            }
            else             
            
            */            
             if(idCliente > 0 ){
                sesion.setAttribute("idUsuario", idCliente);
                sesion.setAttribute("usuarioPrueba", usuario);
                request.setAttribute("cliente", cliente);
                RequestDispatcher view = request.getRequestDispatcher("EditarClientePorAdmin.jsp");
                view.forward(request, response);                  
            }
        }        
        
        else if(action.equalsIgnoreCase("deleteCliente")){
            
            HttpSession sesion = request.getSession();
            
            Cliente cliente = new Cliente();
             List<Cliente> cliente_list = null ;
             int idCliente_request = 0;
             int idCliente_sesion = 0;
             int nivel = 0;
            if(request.getParameter("idClienteR")!=null){
                idCliente_request = Integer.parseInt(request.getParameter("idClienteR"));                              
            }                  
            if(sesion.getAttribute("idUsuario")!=null){
                idCliente_sesion = Integer.parseInt(String.valueOf(sesion.getAttribute("idUsuario")));
            }          
               try {
                    cliente.setId(idCliente_request);
                    clienteDAO.eliminar(cliente);
                    cliente_list =  clienteDAO.consultar();
                                         
                } catch (Exception ex) {
                }  
            for(int i = 0; i < cliente_list.size(); i++){
                nivel =  cliente_list.get(i).getNivel();
                if(nivel == 1){
                    cliente_list.remove(i);                    
                }
            }    
            if(idCliente_request == 0  || idCliente_sesion == 0){
                response.sendRedirect("index.jsp");
            }              
            else if(idCliente_request != 0 ){                
            request.setAttribute("cliente", cliente_list);
            RequestDispatcher view = request.getRequestDispatcher("ListarCliente.jsp");
            view.forward(request, response);
            }
        }
        else if(action.equalsIgnoreCase("refreshCliente")){
            
            HttpSession sesion = request.getSession();
            
            List<Cliente> cliente = null ;
            Cliente cliente1 = new Cliente();
            int nivel = 0;
            int idCliente = 0;
            String  usuario_de_login = null;
            String usuario_de_id_recibido = null;
                        
            if(sesion.getAttribute("nivel")!=null){
                nivel  = Integer.parseInt(String.valueOf(sesion.getAttribute("nivel")));
            }
            if(sesion.getAttribute("idUsuario")!=null){
                idCliente = Integer.parseInt(String.valueOf(sesion.getAttribute("idUsuario")));
            }
            if(sesion.getAttribute("usuario")!=null){
                usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
            }                
             
            try {      
                cliente = clienteDAO.consultar();
                
                for(int i = 0; i < cliente.size(); i++){
                    cliente.get(i).getNivel();
                    if( cliente.get(i).getNivel() < 2){
                        cliente.remove(i);
                    }
                }
                cliente1 = clienteDAO.BuscarPorId(idCliente);
                usuario_de_id_recibido = cliente1.getUsuario();
            } catch (Exception e) {
                
            }
            //nivel 1 = administrador
            if(idCliente == 0 || usuario_de_login == null || nivel != 1 ){
                response.sendRedirect("index.jsp");
            }      
            else if (idCliente != 0 && nivel == 1){
            sesion.setAttribute("idUsuario", idCliente);
            sesion.setAttribute("nivel", nivel);
            sesion.setAttribute("usuario", usuario_de_login);
            request.setAttribute("cliente", cliente); 
            RequestDispatcher view = request.getRequestDispatcher("ListarCliente.jsp");
            view.forward(request, response);                
            }

        }
        
        if(action.equalsIgnoreCase("cerrar")){
            HttpSession session = request.getSession();
           /* session.setAttribute("idCliente", null);        
            response.sendRedirect("index.jsp");
            if(request.getParameter("cerrar")!=null){
            session.invalidate();
            }
            */
            session.invalidate();
            response.sendRedirect("index.jsp");
        }           
        
        
            else if(action.equalsIgnoreCase("refreshClienteProAdmin")){
      
                try {
                    
                    request.setAttribute("cliente", clienteDAO.consultar()); 
                } catch (Exception e) {
                }
            RequestDispatcher view = request.getRequestDispatcher("ListarCliente.jsp");
            view.forward(request, response);
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
        
        int nivel = 0;
        
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
        
        if(request.getParameter("btnEditarPruebaAdmin")!=null){
        
        cliente.setId(Integer.parseInt(id));
            try {
                clienteDAO.modificar(cliente);
            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
         
          response.sendRedirect(request.getContextPath() + "/SERVCliente?action=refreshClienteProAdmin"); 
        }
        
        
        
        /*        
        if(request.getParameter("btnEditarPrueba")!=null){
        
        cliente.setId(Integer.parseInt(id));
            try {
                clienteDAO.modificar(cliente);
            } catch (Exception ex) {
                Logger.getLogger(SERVCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
         
          response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=cerrar"); 
        }                           
        */     
                
        if(request.getParameter("btnRegistrarCliente")!= null){
            nivel = 2;
            try {
                cliente.setNivel(nivel);
                clienteDAO.insertar(cliente);
//                envio.EnviarCorreo(email);

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
      //esa funcion dentro del index ya no existe
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
