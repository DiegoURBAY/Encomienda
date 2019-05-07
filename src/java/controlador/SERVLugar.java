
package controlador;

import com.google.gson.Gson;
import dao.LocalDAO;
import dao.LugarDAO;
import entidad.Local;
import entidad.Lugar;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

public class SERVLugar extends HttpServlet {


   private LocalDAO localDAO;
    private LugarDAO lugarDAO;
    Local u = new Local();
            
     public SERVLugar() {
    	localDAO = new LocalDAO(){};
        lugarDAO = new LugarDAO(){};
    }         
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
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
                PrintWriter out = response.getWriter();
        String action = request.getParameter("action");          
        String estado = "";          
        String mensaje = "";
        
        response.setContentType("text/html;charset=UTF-8");
        
        List<Lugar> lugares;
                
        try {
           //Autocompletar de departamentos
           if(action.equalsIgnoreCase("completar")){
                String find = request.getParameter("nombre").toUpperCase();
                lugares = lugarDAO.ConsultarDepartamento(find);
                List<Local> locales = localDAO.consultar();
                List<Lugar> lugares_con_locales = new ArrayList<>();
                for (int i = 0; i < lugares.size(); i++) {
                        for (int j = 0; j < locales.size(); j++) {                          
                           if(locales.get(j).getIdLugar() == lugares.get(i).getId()){
                               Lugar lugar = new Lugar();
                               lugar.setId(lugares.get(i).getId());
                               lugar.setNombre(lugares.get(i).getNombre());
                               lugares_con_locales.add(lugar);
                           }
                       }                   
                }
                mensaje = new Gson().toJson(lugares_con_locales);
                estado = "ok";
           }                      
           //Trar locales segun el departamento
           else if(action.equalsIgnoreCase("ubicacion")){
               String find = request.getParameter("nombre");
                lugares = lugarDAO.ConsultarDepartamento(find);
                List<Local> locales = localDAO.consultar();
                for (int i = 0; i < lugares.size(); i++) {
                   if(lugares.get(i).getId()>0){
                       for (int j = 0; j < locales.size(); j++) {                          
                           if(locales.get(j).getIdLugar() == lugares.get(i).getId()){
                                mensaje = new Gson().toJson(locales.get(j));
                                estado = "ok";
                           }
                       }
                   }
                }
           }            
           
       } catch (SQLException ex) {
            estado = "error";
            mensaje = "error al grabar";           
           Logger.getLogger(SERVLugar.class.getName()).log(Level.SEVERE, null, ex);
       } catch (Exception ex) {
           Logger.getLogger(SERVLugar.class.getName()).log(Level.SEVERE, null, ex);
       }
       finally{        
            try {        
                JSONObject jsonObject=new  JSONObject();
                jsonObject.put("estado", estado);
                jsonObject.put("mensaje", mensaje);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(jsonObject);
            } catch (JSONException ex) {
                Logger.getLogger(SERVLugar.class.getName()).log(Level.SEVERE, null, ex);
            }

       }
        
             

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
    

    public static void main(String[] json) throws Exception {

        String find = "s";
           //    String find = request.getParameter("nombre").toUpperCase();
           LugarDAO lugarDAO = new LugarDAO();
           LocalDAO localDAO = new LocalDAO();
           
           List<Lugar> lugares_con_locales = new ArrayList<>();
           String mensaje = "s";
              //  String find = request.getParameter("nombre").toUpperCase();
            List<Lugar>   lugares = lugarDAO.ConsultarDepartamento(find);
                List<Local> locales = localDAO.consultar();
                for (int i = 0; i < lugares.size(); i++) {
                   if(lugares.get(i).getId()>0){
                       for (int j = 0; j < locales.size(); j++) {                          
                            if(locales.get(j).getIdLugar() == lugares.get(i).getId()){
                                 mensaje = new Gson().toJson(locales.get(j));
                                  System.out.print(lugares.get(i).getNombre());
                                // estado = "ok";
                            }
                       }
                   }
                }
           //     mensaje = new Gson().toJson(lugares_con_locales);
                 JSONObject jsonObject=new  JSONObject();
                
                jsonObject.put("mensaje", mensaje);
       System.out.print(mensaje);
}    


}

