
package controlador;

import dao.VehiculoDAO;
import entidad.Vehiculo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SERVVehiculo extends HttpServlet {

    private static String insert= "/RegistrarTipoEncomiendaSobre.jsp";
    private static String insert_paquete= "/RegistrarTipoEncomiendaPaquete.jsp";
    private static String edit = "/EditarTipoEncomiendaSobre.jsp";
    private static String edit_paquete = "/EditarTipoEncomiendaPaquete.jsp";
    private static String list_encomienda = "/ListarVehiculo.jsp";
    private VehiculoDAO vehiculoDAO;
    Vehiculo vehiculo;

     public SERVVehiculo() {
    	vehiculoDAO = new VehiculoDAO(){};
        vehiculo = new Vehiculo(){};
    }               

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SERVVehiculo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SERVVehiculo at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        response.setContentType("text/html;charset=UTF-8");
        String forward = "";   
        String action = request.getParameter("action");
                
            //ELIMINAR CLIENTE
            if (action.equalsIgnoreCase("delete")) {                 
                
                  int idEncomienda = Integer.parseInt(request.getParameter("idEncomienda"));
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    vehiculo.setId(id);
                    vehiculoDAO.eliminar(vehiculo);                   
                //    request.setAttribute("tipoEncomienda", tipoEncomiendaDAO.consultarTipoPorEncomienda(idEncomienda));                      
                } catch (Exception ex) {
                }              
                               
              response.sendRedirect(request.getContextPath() + "/SERVTipoEncomienda?action=refresh2&idEncomienda="+idEncomienda);  
            }


            
            else if(action.equalsIgnoreCase("refresh")){
                 HttpSession sesion = request.getSession();
                 
                forward = list_encomienda;
                //int id = Integer.parseInt(request.getParameter("id")); 
                double peso= 0;
                if(request.getParameter("peso")!=null){
                     peso = Double.parseDouble(request.getParameter("peso")); 
                     
                    try { 
                        List vehiculo = vehiculoDAO.consultarPorPeso(peso);                                               
                                                
                        request.setAttribute("vehiculo", vehiculo);                                        
     

                        RequestDispatcher view = request.getRequestDispatcher(forward);
                    view.forward(request, response);      

                    } catch (Exception ex) {
                        Logger.getLogger(SERVTipoEncomienda.class.getName()).log(Level.SEVERE, null, ex);
                    }                     
                }

            }           
    }

     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
