
package controlador;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.EncomiendaDAO;
import dao.LocalDAO;
import dao.LugarDAO;
import entidad.Encomienda;
import entidad.Local;
import entidad.Lugar;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                PrintWriter out = response.getWriter();
        String action = request.getParameter("action");          
        String estado = "";          
        String mensaje = "";
        String mensaje2 = "";
                String localOrigen = "";
        String localDestino = "";
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
           /*
           else if(action.equalsIgnoreCase("ubicacion2")){
               String find = request.getParameter("nombre");
                lugares = lugarDAO.ConsultarDepartamento(find);
                List<Local> locales = localDAO.consultar();
                for (int i = 0; i < lugares.size(); i++) {
                   if(lugares.get(i).getId()>0){
                       for (int j = 0; j < locales.size(); j++) {                          
                           if(locales.get(j).getIdLugar() == lugares.get(i).getId()){
                                mensaje = new Gson().toJson(locales.get(j));
                                                                mensaje2 = new Gson().toJson(lugares.get(i));
                                estado = "ok";
                           }
                       }
                   }
                }
           }            
           */
           else if(action.equalsIgnoreCase("buscar")){               
                int idEncomienda = Integer.parseInt(request.getParameter("idEncomienda"));
                                
                EncomiendaDAO encomiendaDAO = new EncomiendaDAO();
                                     
               String existe = "";
               Encomienda encomienda_buscada = encomiendaDAO.BuscarPorId(idEncomienda);
                      
               if(encomienda_buscada.getId() == idEncomienda){
                   existe = "Si";
                   
                    Lugar lugar_origen = lugarDAO.BuscarPorId(encomienda_buscada.getOrigen());
                    Lugar lugar_destino = lugarDAO.BuscarPorId(encomienda_buscada.getDestino());
                    encomienda_buscada.setOrigenS( lugar_origen.getNombre());
                    encomienda_buscada.setDestinoS(lugar_destino.getNombre());          
                                          
            String find = lugar_origen.getNombre();
            lugares = lugarDAO.ConsultarDepartamento(find);
                List<Local> locales = localDAO.consultar();
                for (int i = 0; i < lugares.size(); i++) {
                    //igual el origen con su lugar
                   if(lugares.get(i).getId() == encomienda_buscada.getOrigen() ){
                       for (int j = 0; j < locales.size(); j++) {                          
                           if(locales.get(j).getIdLugar() == lugares.get(i).getId()){
                               localOrigen = new Gson().toJson(locales.get(j));
                              //  mensaje2 = new Gson().toJson(lugares.get(i));
                            //    estado = "ok";
                           }
                       }
                   }
                }  
                
              String find2 = lugar_destino.getNombre();    
           List<Lugar> lugares2 = lugarDAO.ConsultarDepartamento(find2);               
                for (int i = 0; i < lugares2.size(); i++) {
                    //igual el origen con su lugar
                   if(lugares2.get(i).getId() == encomienda_buscada.getDestino()){
                       for (int j = 0; j < locales.size(); j++) {                          
                           if(locales.get(j).getIdLugar() == lugares2.get(i).getId()){
                               localDestino = new Gson().toJson(locales.get(j));
                              //  mensaje2 = new Gson().toJson(lugares.get(i));
                            //    estado = "ok";
                           }
                       }
                   }
                }                                                                                                                                                      
                mensaje = new Gson().toJson(encomienda_buscada);
                estado = "ok";    
               }
               else{
                   existe = "No";   
                    estado = "error";
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
                 jsonObject.put("localOrigen", localOrigen);
                 jsonObject.put("localDestino", localDestino);                
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

        String mensaje = "";
        String localOrigen = "";
        String localDestino = "";
           //    String find = request.getParameter("nombre").toUpperCase();
           LugarDAO lugarDAO = new LugarDAO();
           LocalDAO localDAO = new LocalDAO();
           
           
 int idEncomienda = 123;
                EncomiendaDAO encomiendaDAO = new EncomiendaDAO();
                Encomienda encomienda_buscada = encomiendaDAO.BuscarPorId(idEncomienda);
                
                    Lugar lugar_origen = lugarDAO.BuscarPorId(encomienda_buscada.getOrigen());
                    Lugar lugar_destino = lugarDAO.BuscarPorId(encomienda_buscada.getDestino());
                     encomienda_buscada.setOrigenS( lugar_origen.getNombre());
                     encomienda_buscada.setDestinoS(lugar_destino.getNombre());                      
                
               String find = lugar_origen.getNombre();
           List<Lugar>     lugares = lugarDAO.ConsultarDepartamento(find);
                List<Local> locales = localDAO.consultar();
                for (int i = 0; i < lugares.size(); i++) {
                    //igual el origen con su lugar
                   if(lugares.get(i).getId() == encomienda_buscada.getOrigen() ){
                       for (int j = 0; j < locales.size(); j++) {                          
                           if(locales.get(j).getIdLugar() == lugares.get(i).getId()){
                               localOrigen = new Gson().toJson(locales.get(j));
                              //  mensaje2 = new Gson().toJson(lugares.get(i));
                            //    estado = "ok";
                           }
                       }
                   }
                }  
                
              String find2 = lugar_destino.getNombre();    
           List<Lugar> lugares2 = lugarDAO.ConsultarDepartamento(find2);               
                for (int i = 0; i < lugares2.size(); i++) {
                    //igual el origen con su lugar
                   if(lugares2.get(i).getId() == encomienda_buscada.getDestino()){
                       for (int j = 0; j < locales.size(); j++) {                          
                           if(locales.get(j).getIdLugar() == lugares2.get(i).getId()){
                               localDestino = new Gson().toJson(locales.get(j));
                              //  mensaje2 = new Gson().toJson(lugares.get(i));
                            //    estado = "ok";
                           }
                       }
                   }
                }                  
                     
                mensaje = new Gson().toJson(encomienda_buscada);
                
            //    estado = "ok";                                           
  /*         
               String find = "amazonas";
           List<Lugar>    lugares = lugarDAO.ConsultarDepartamento(find);
                List<Local> locales = localDAO.consultar();

                for (int i = 0; i < lugares.size(); i++) {
                   if(lugares.get(i).getId()>0){
                       for (int j = 0; j < locales.size(); j++) {                          
                           if(locales.get(j).getIdLugar() == lugares.get(i).getId()){
                                mensaje = new Gson().toJson(locales.get(j));
                                mensaje2 = new Gson().toJson(lugares.get(i));
                              
                           }
                       }
                   }
                }                  
                
  */         
                 JSONObject jsonObject=new  JSONObject();
                jsonObject.put("mensaje", mensaje);
                 jsonObject.put("localOrigen", localOrigen);
                 jsonObject.put("localDestino", localDestino);
               // jsonObject.put("mensaje2", mensaje2);


               System.out.print(mensaje);
               System.out.print(localOrigen);
       System.out.print(localDestino);
}    


}

