
package controlador;

import com.google.gson.Gson;
import dao.ConductorDAO;
import entidad.Conductor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class SERVVerificar extends HttpServlet {

    private ConductorDAO conductorDAO;

    public SERVVerificar() {
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
            out.println("<title>Servlet SERVVerificar</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SERVVerificar at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");          
        String estado = "";          
        String mensaje = "";        
        try {
            if(action.equalsIgnoreCase("verificarConductor")){
                List<Conductor> conductorList = conductorDAO.consultar();  
                int tipo = 0;
                  String dni = "";
                  String licencia = "";
                  String email = "";
                if(request.getParameter("dni")!=null){
                    dni = request.getParameter("dni");
                    tipo = 1;
                }
                if(request.getParameter("licencia")!=null){
                    licencia = request.getParameter("licencia");
                    tipo = 2;
                }             
                if(request.getParameter("email")!=null){
                    email = request.getParameter("email");
                    tipo = 3;
                }                   
                int contador = 0;
                for (int i = 0; i < conductorList.size(); i++) {
                    if(tipo == 1){
                        if(conductorList.get(i).getDni().equalsIgnoreCase(dni)){
                            contador = i + 1;
                        }
                    }
                    if(tipo == 2){
                        if(conductorList.get(i).getLic().equalsIgnoreCase(licencia)){
                            contador = i + 1;
                        }                        
                    }
                    if(tipo == 3){
                        if(conductorList.get(i).getEmail().equalsIgnoreCase(email)){
                            contador = i + 1;
                        }                        
                    }                    
                } 
                if(contador > 0){
                    mensaje = "existe";
                }
                estado = "ok";
            }
        } catch (Exception e) {
            estado = "error";
            mensaje = "error al grabar";          
            Logger.getLogger(SERVLugar.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            try {
                JSONObject jsonObject=new  JSONObject();
                jsonObject.put("mensaje", mensaje);
                jsonObject.put("estado", estado);            
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(jsonObject);                       
            } catch (JSONException e) {
                Logger.getLogger(SERVLugar.class.getName()).log(Level.SEVERE, null, e);
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
    public static void main(String[] json) throws Exception {
        ConductorDAO conductorDAO = new ConductorDAO();
        try {
                    List<Conductor> conductorList = conductorDAO.consultar();  
                    String dni = "22222222";
                    int contador = 0;
                    for (int i = 0; i < conductorList.size(); i++) {
                            if(conductorList.get(i).getDni().equalsIgnoreCase(dni)){
                               contador = i + 1;
                         //    System.out.println(conductorList.get(i).getDni());
                            }
                          
                           //   System.out.println(conductorList.get(i).getDni());
                    }   
                    if(contador > 0){
                        System.out.println("existe");
                    }             
                    else{
                         System.out.println(" no existe");
                    }
        } catch (Exception e) {
            throw e;
        }
   
    }
}
