
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
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;



public class SERVCliente2 extends HttpServlet {

    
    private ClienteDAO clienteDAO;   
    
    public SERVCliente2() {
        clienteDAO = new ClienteDAO(){};
    }         
        

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            response.addHeader("X-XSS-Protection", "1; mode=block");
            response.addHeader("X-Frame-Options","DENY");
            response.setContentType("text/html;charset=UTF-8");
            
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         response.addHeader("X-XSS-Protection", "1; mode=block");
        response.addHeader("X-Frame-Options","DENY");
        String action = request.getParameter("action");
        String usuario_de_login;
        String vista = "";
        
        try {
            
            if(action.equalsIgnoreCase("insertar")){
                vista = "RegistrarCliente.jsp";
            }
            //Si un usuario intenta edit a  otro usuario a través del url
            //entonces va a editarse a si mismo
            else if(action.equalsIgnoreCase("edit")){
                HttpSession sesion = request.getSession();                
                Cliente cliente_buscado ;
                
                if(sesion.getAttribute("usuario")!=null){                                        
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));                
                    cliente_buscado = clienteDAO.BuscarPorUsuario(usuario_de_login);
                    int nivel = cliente_buscado.getNivel();
                    //como cliente
                    if(nivel == 2){
                        vista = "EditarCliente1.jsp";
                    }
                    //como administrador
                    else if(nivel == 1){
                        //si el administrador quiere editar a un cliente
                        if(request.getParameter("email")!=null){
                            String email_cliente = request.getParameter("email");
                            cliente_buscado = clienteDAO.ConsultarByEmail(email_cliente);
                            if(cliente_buscado.getNivel() == 2){
                                vista = "EditarCliente.jsp";
                            }
                            //si el administrador quiere editar a otro administrador
                            //entonces se le evita enviando sus propios datos al formulario
                            else{
                                 cliente_buscado = clienteDAO.BuscarPorUsuario(usuario_de_login);
                              //    vista = "EditarCliente.jsp";
                                vista = "EditarAdministrador.jsp";
                            }
                            
                        }
                        //si el administrador quiere editar su cuenta
                        else{
                           // vista = "EditarCliente.jsp";
                           vista = "EditarAdministrador.jsp";
                        }
                    }
                    request.setAttribute("cliente", cliente_buscado);
                    request.setAttribute("usuario",  usuario_de_login);
                }
                else{
                    vista = "index.jsp";
                }                                
            }
            else if(action.equalsIgnoreCase("delete")){
                HttpSession sesion = request.getSession();
                if(sesion.getAttribute("usuario")!=null){
                    
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));                
                    Cliente cliente_buscado = clienteDAO.BuscarPorUsuario(usuario_de_login);
                    int nivel = cliente_buscado.getNivel();
                                     
                    if(nivel == 2){
                        vista = "index.jsp";
                    }
                    else if(nivel == 1){
                        String email = request.getParameter("email");
                        Cliente cliente = clienteDAO.ConsultarByEmail(email);
                        clienteDAO.eliminar(cliente);
                        
                        List<Cliente> cliente_lista = clienteDAO.consultar();

                        for(int i = 0; i < cliente_lista.size(); i++){
                            cliente_lista.get(i).getNivel();
                            if( cliente_lista.get(i).getNivel() < 2){
                                cliente_lista.remove(i);
                            }
                        }
                        vista = "ListarCliente.jsp";
                        request.setAttribute("cliente", cliente_lista);
                        request.setAttribute("usuario",  usuario_de_login);
                    }
                                    
                }
                else{
                    vista = "index.jsp";
                }  
            }            
            else if(action.equalsIgnoreCase("refresh")){
                HttpSession sesion = request.getSession();
                if(sesion.getAttribute("usuario")!=null){
                    
                usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));                
                Cliente cliente_buscado = clienteDAO.BuscarPorUsuario(usuario_de_login);
                int nivel = cliente_buscado.getNivel();
                
                List<Cliente> cliente_lista = null;
                 
                    if(nivel == 2){
                        
                    }
                    else if(nivel == 1){
                                                           
                       cliente_lista = clienteDAO.consultar();

                        for(int i = 0; i < cliente_lista.size(); i++){
                            cliente_lista.get(i).getNivel();
                            if( cliente_lista.get(i).getNivel() < 2){
                                cliente_lista.remove(i);
                            }
                        }
                    }
                    
                    vista = "ListarCliente.jsp";
                    request.setAttribute("cliente", cliente_lista);
                    request.setAttribute("usuario",  usuario_de_login);
                }   
                else{
                    vista = "index.jsp";
                }                                
            }
            else if(action.equalsIgnoreCase("cerrar")){
                HttpSession sesion = request.getSession();
                if(sesion.getAttribute("usuario")!=null){
                    vista = "index.jsp";
                    sesion.invalidate();                   
                }                
            }             
        } catch (Exception e) {
            vista = "error.jsp";
            Logger.getLogger(SERVCliente2.class.getName()).log(Level.SEVERE, null, e);            
        }        
        finally{           
            //request.setAttribute("usuario",  usuario_de_login);
            RequestDispatcher view = request.getRequestDispatcher(vista);
            view.forward(request, response);            
        }                        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        
        response.addHeader("X-XSS-Protection", "1; mode=block");
        response.addHeader("x-frame-options","DENY");
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
        
        String usuario_de_login;
        String action = request.getParameter("action");      
        String estado = ""; 
        String mensaje = "";
        String asunto;
        String mensaje_correo;
                
        
        try {
          
            if (action.equalsIgnoreCase("insert")) {
               
                cliente.setNivel(2);
                clienteDAO.insertar(cliente);      
                
                estado = "ok";
                mensaje = "Grabación exitosa";
            }
            else if (action.equalsIgnoreCase("edit")){
                if(sesion.getAttribute("usuario")!=null){
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente_buscado = clienteDAO.BuscarPorUsuario(usuario_de_login);
                    
                    String id = request.getParameter("txtId");
                     
                    int nivel = cliente_buscado.getNivel();
                 
                    
                    if(nivel == 2){
                        
                        
                    }
                    else if(nivel == 1){
                                                                        
                    }
                    
                    cliente.setId(Integer.parseInt(id)); 
                    clienteDAO.modificar(cliente);
                    
                    estado = "ok";
                    mensaje = "Edición exitosa";                       
                }
            }
            else if(action.equalsIgnoreCase("registro")){
                Envio envio = new Envio();
                String email_registrado = request.getParameter("email"); 
                asunto = "Registro de cuenta";
                mensaje_correo = "Bienvenido a Zurita SAC, "+email_registrado;
                envio.EnviarCorreo(email_registrado, asunto, mensaje_correo);
                estado = "ok";
                mensaje = "Envío exitoso"; 
            }
            else if(action.equalsIgnoreCase("edito")){
                Envio envio = new Envio();
                String email_registrado = request.getParameter("email");      
                asunto = "Edición de cuenta";
                mensaje_correo = "Ha editado exitosamente su cuenta, "+email_registrado;
                envio.EnviarCorreo(email_registrado, asunto, mensaje_correo);
                estado = "ok";
                mensaje = "Envío exitoso"; 
            }            
        } catch (SQLException e) {
            estado = "error";
            mensaje = "error al grabar "+e;
            Logger.getLogger(SERVCliente2.class.getName()).log(Level.SEVERE, null, e);
        } catch (MessagingException ex) {
            estado = "error";
            mensaje = "error al grabar "+ex;            
            Logger.getLogger(SERVCliente2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            estado = "error";
            mensaje = "error al grabar "+ex;            
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


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
/*
     public static void main(String[] json) throws Exception {
                 Cliente cliente = new Cliente();
                 ClienteDAO clienteDAO = new ClienteDAO();
     //   Envio envio = new Envio();
        
        String usuario_de_login;
        String mensaje = "";
        String estado = "";
        
 
        
         try {
             
        mensaje = "exito";
        estado = "ok";
        cliente.setIdentificador("123");
        cliente.setNombre("123");
        cliente.setEmail("123");
        cliente.setUsuario("123");
        cliente.setContraseña("123");        
        cliente.setTelefono("123");    
        cliente.setNivel(2);
        
         clienteDAO.insertar(cliente);
         
         } catch (SQLException e) {
             estado = "error";
             mensaje = "error al grabar"+e;
             
         }
        
       System.out.println(mensaje);
       System.out.println(estado);
     }

*/
}
