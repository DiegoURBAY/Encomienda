
package controlador;

import dao.ClienteDAO;
import dao.EncomiendaDAO;
import dao.Envio;
import entidad.Cliente;
import entidad.Encomienda;
import java.io.IOException;
import java.io.PrintWriter;
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


public class SERVPedido extends HttpServlet {

    private static String insert= "/RegistrarEncomienda.jsp";
    private static String edit = "/EditarEncomienda.jsp";
    private static String list = "/ListarPedido.jsp";
    private static String exito = "/exito.jsp";
    private EncomiendaDAO encomiendadao;
    private ClienteDAO clientedao;       
    Encomienda enc = new Encomienda();
    
     public SERVPedido() {
    	encomiendadao = new EncomiendaDAO(){};
        clientedao = new ClienteDAO(){};
    }             

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SERVPedido</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SERVPedido at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
          String forward = "";   
            String action = request.getParameter("action");
            
                    //LISTAR O ACTUALIZAR CLIENTE
            if(action.equalsIgnoreCase("refresh")){
                     HttpSession sesion = request.getSession();
                     
                 int idCliente = 0;
                 int idEncomienda = 0;
                 int idVehiculo = 0;
                 String placaVehiculo= null;
                if(request.getParameter("idCliente")!=null){
                     idCliente =Integer.parseInt(request.getParameter("idCliente"));
                }
                if(request.getParameter("idEncomienda")!=null){
                     idEncomienda =Integer.parseInt(request.getParameter("idEncomienda"));
                }                
                if(request.getParameter("idVehiculo")!=null){
                     idVehiculo =Integer.parseInt(request.getParameter("idVehiculo"));
                }                    
                if(request.getParameter("placaVehiculo")!=null){
                     placaVehiculo =request.getParameter("placaVehiculo");
                }                                    
                
                try {
                    forward = list;
                    sesion.setAttribute("idCliente", idCliente);
                    sesion.setAttribute("idEncomienda", idEncomienda);
                    sesion.setAttribute("idVehiculo", idVehiculo);
                    sesion.setAttribute("placaVehiculo", placaVehiculo);
                 
                 //   sesion.setAttribute("nivel", request);
                } catch (Exception e) {
                }
                               
            RequestDispatcher view = request.getRequestDispatcher(forward);
           view.forward(request, response);
            }
        
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        

                 Envio envio = new Envio();
                 ClienteDAO clienteDAO = new ClienteDAO();
                 Cliente  cliente = new Cliente();
                
                String codigo = request.getParameter("txtCodigo");                                                               
                String Idcliente = request.getParameter("txtIdcliente");
                String IdEncomienda = request.getParameter("txtidEncomienda");
                String IdVehiculo = request.getParameter("txtidVehiculo");
                String placaVehiculo = request.getParameter("txtplacaVehiculo");
                
                
                if(request.getParameter("btnIngresar")!=null){
                    
                    try {  
                       cliente = clienteDAO.BuscarPorId(Integer.parseInt(Idcliente));
                        String email = cliente.getEmail();
                        
                        envio.EnviarCodigo(codigo , Idcliente, IdEncomienda, IdVehiculo, placaVehiculo, email);
                        sesion.setAttribute("email", email);
                                                
                    } catch (MessagingException ex) {
                        Logger.getLogger(SERVPedido.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(SERVPedido.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        
                response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=refresh&nivel="+cliente.getId());   
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
