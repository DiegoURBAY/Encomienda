
package controlador;

import dao.ClienteDAO;
import dao.ConductorDAO;
import dao.EncomiendaDAO;
import dao.LugarDAO;
import dao.TipoEncomiendaDAO;
import dao.VehiculoDAO;
import entidad.Cliente;
import entidad.Encomienda;
import entidad.Lugar;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SERVEncomienda2 extends HttpServlet {

    String index = "/index.jsp";
   
    private EncomiendaDAO encomiendadao;
    private TipoEncomiendaDAO tipoEncomiendaDAO;
    private VehiculoDAO vehiculoDAO;
    private ClienteDAO clientedao;
    private LugarDAO lugarDAO;
    private ConductorDAO conductorDAO;
    Encomienda encomienda = new Encomienda();

            
     public SERVEncomienda2() {
        tipoEncomiendaDAO = new TipoEncomiendaDAO(){};
    	encomiendadao = new EncomiendaDAO(){};
        vehiculoDAO = new VehiculoDAO(){};
        clientedao = new ClienteDAO(){};
        lugarDAO = new LugarDAO(){};
        conductorDAO = new ConductorDAO(){};
    }       
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SERVEncomienda2</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SERVEncomienda2 at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");        
        String usuario_de_login = "";
        String vista = "";
        
        List<Encomienda> encomienda_list = null;
                         
        try {
            if (action.equalsIgnoreCase("delete")) { 
                
            }
            else if (action.equalsIgnoreCase("delete2")) {

            }
            else if (action.equalsIgnoreCase("edit")) {
                
            }
            else if(action.equalsIgnoreCase("insert")) {  
                HttpSession sesion = request.getSession();
                if(sesion.getAttribute("usuario")!=null){
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);
                    int nivel = cliente.getNivel();
                    if(nivel == 2){                        
                        vista = "RegistrarEncomienda1.jsp";
                    }
                    else if(nivel == 1){

                    }
                }
                else{
                    vista = "index.jsp";
                }                   
            }
       //     else if(action.equalsIgnoreCase("refresh")){
                
      //      }
            else if(action.equalsIgnoreCase("refresh")){
                HttpSession sesion = request.getSession();                               
                if(sesion.getAttribute("usuario")!=null){
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);
                    int nivel = cliente.getNivel();
                    if(nivel == 2){                        
                         vista = "ListarEncomienda1.jsp";
                    }
                    else if(nivel == 1){
                        vista = "ListarEncomienda.jsp";                        
                    }

                    encomienda_list = encomiendadao.consultarEncomiendaPorIdCliente(cliente.getId());

                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    for(int i = 0; i < encomienda_list.size(); i++){

                        if(encomienda_list.get(i).getFechaRegistroTime() != null ){

                           Date envio_date = encomienda_list.get(i).getFechaRegistroTime();     

                           String fecha_string = sdf.format(envio_date);                                                            

                           encomienda_list.get(i).setFechaRegistroTimeString(fecha_string);

                           Lugar lugar_origen = lugarDAO.BuscarPorId( encomienda_list.get(i).getOrigen());
                           Lugar lugar_destino = lugarDAO.BuscarPorId( encomienda_list.get(i).getDestino());
                           encomienda_list.get(i).setOrigenS( lugar_origen.getNombre());
                           encomienda_list.get(i).setDestinoS(lugar_destino.getNombre());                               
                        }
                    }                    
                    
                }
                else{
                    vista = "index.jsp";
                }                
            }
            else if(action.equalsIgnoreCase("buscarEncomienda")){
                
            }
            else if(action.equalsIgnoreCase("cerrar")){
                HttpSession sesion = request.getSession();
                sesion.invalidate();
                response.sendRedirect("index.jsp");
            }            
        } catch (Exception e) {
            vista = "error.jsp";
             Logger.getLogger(SERVEncomienda2.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
                request.setAttribute("usuario",  usuario_de_login);
                request.setAttribute("encomienda", encomienda_list); 
                RequestDispatcher view = request.getRequestDispatcher(vista);
                view.forward(request, response);              
            
        }       
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
