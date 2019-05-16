
package controlador;

import dao.ConductorDAO;
import entidad.Conductor;
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


public class SERVConductor extends HttpServlet {

    private ConductorDAO conductorDAO;
 
     public SERVConductor() {
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
            out.println("<title>Servlet SERVConductor</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SERVConductor at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String vista = "";
        
        String action = request.getParameter("action");
        
        try {
            if(action.equalsIgnoreCase("refresh")){
                List<Conductor> conductorList = conductorDAO.consultar();
             
                vista = "ListarConductor.jsp";
                
                request.setAttribute("conductor", conductorList); 
            }
            else if(action.equalsIgnoreCase("insert")){
                vista = "RegistrarConductor.jsp";
            }
            else if(action.equalsIgnoreCase("edit")){
                
                
                int idConductor = Integer.parseInt(request.getParameter("id"));
                Conductor conductor = conductorDAO.BuscarPorId(idConductor);
                vista = "EditarConductor.jsp";
                request.setAttribute("conductor", conductor); 
            }
            else if(action.equalsIgnoreCase("delete")){
               vista = "exito.jsp";
            }            
            
        } catch (Exception e) {
            vista = "error.jsp";
            Logger.getLogger(SERVConductor.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            RequestDispatcher view = request.getRequestDispatcher(vista);
            view.forward(request, response);             
         }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String vista = "";
        HttpSession sesion = request.getSession();
        
        Conductor conductor = new Conductor();
         List<Conductor> conductorList = null;
         
        String id = request.getParameter("txtId");
        String dni = request.getParameter("txtDni");
        String nombre = request.getParameter("txtNombre");
        String apellido = request.getParameter("txtApellido");
        String licencia = request.getParameter("txtLicencia");
        String email = request.getParameter("txtEmail");           
        String telefono = request.getParameter("txtTelefono");
        
        try {
                conductor.setDni(dni);
                conductor.setNom(nombre);
                conductor.setApe(apellido);
                conductor.setLic(licencia);
                conductor.setEmail(email);
                conductor.setTel(telefono);   
                
           conductorList = conductorDAO.consultar();
            if(request.getParameter("btnRegistrar")!=null){
         
                conductorDAO.insertar(conductor);
                vista = "/ListarConductor.jsp";
            }
            else if(request.getParameter("btnEditar")!= null){
                conductor.setId(Integer.parseInt(id));
                conductorDAO.modificar(conductor);
                vista = "/ListarConductor.jsp";
            }
        } catch (Exception e) {
            vista = "/error.jsp";
           Logger.getLogger(SERVConductor.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            
            sesion.setAttribute("conductor", conductorList);
            response.sendRedirect(request.getContextPath() + vista); 
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
