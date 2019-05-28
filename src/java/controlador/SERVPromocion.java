/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.google.gson.Gson;
import dao.ClienteDAO;
import dao.PromocionDAO;
import entidad.Cliente;
import entidad.Promocion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class SERVPromocion extends HttpServlet {

    private PromocionDAO promocionDAO;
    private ClienteDAO clienteDAO;

    Promocion promocion = new Promocion();
            
     public SERVPromocion() {
    	promocionDAO = new PromocionDAO(){};
        clienteDAO = new ClienteDAO();
    }         
            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SERVPromocion</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SERVPromocion at " + request.getContextPath() + "</h1>");
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
            if(action.equalsIgnoreCase("buscarCliente")){
                estado = "ok"; 
                String usuario = request.getParameter("usuario");
                int id;
                
                Cliente cliente_buscado = clienteDAO.BuscarPorUsuario(usuario);
                
                if(cliente_buscado.getIdentificador().length() == 8){
                    id = 1;
                }
                else{
                    id = 2;
                }                
                
                int cliente_promocion =  estadoPromocion(usuario);
                
                Promocion promocion_buscada = promocionDAO.BuscarPorId(id);
                mensaje = new Gson().toJson(cliente_promocion+", "+promocion_buscada.getPorcentaje());      
                
            }
            if(action.equalsIgnoreCase("buscar")){
                estado = "ok"; 
                String tipo= request.getParameter("tipo");
                int id = 0;
                if(tipo.equalsIgnoreCase("cliente")){
                    id = 1;
                }
                else if(tipo.equalsIgnoreCase("empresa")){
                     id = 2;
                }
                Promocion promocion_buscada = promocionDAO.BuscarPorId(id); 
                mensaje = new Gson().toJson(promocion_buscada);                                
            }            
        } catch (Exception e) {
            estado = "error";
            mensaje = "error al grabar";                      
            Logger.getLogger(SERVPromocion.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SERVPromocion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");      
        String estado = ""; 
        String mensaje = "";
        
        Double porcentaje = Double.parseDouble(request.getParameter("txtPromocion"));
        
        Promocion promocion_guardar = new Promocion();
        promocion_guardar.setPorcentaje(porcentaje);
        
        try {
            if(action.equalsIgnoreCase("edit")){  
                String tipo= request.getParameter("tipo");
                
                int id = 0;
                if(tipo.equalsIgnoreCase("cliente")){
                    id = 1;
                }
                else if(tipo.equalsIgnoreCase("empresa")){
                     id = 2;
                }
                    promocion_guardar.setId(id);
                    promocionDAO.modificar(promocion_guardar);
                    estado = "ok";
                    mensaje = "Edición exitosa"; 
            }            
        } catch (Exception e) {
            Logger.getLogger(SERVPromocion.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(SERVLocal.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static void main(String[] args) throws Exception {
        /*
        String usuario = "pancho";
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente_buscado = clienteDAO.BuscarPorUsuario(usuario);
        
        System.out.println(cliente_buscado.getPromocion());
*/
     
        ClienteDAO clienteDAO2 = new ClienteDAO();
        Cliente cliente = clienteDAO2.BuscarPorUsuario("pancho");
        cliente.getFechapromo();
        
        Calendar ultima_promocion = Calendar.getInstance();
        ultima_promocion.setTime( cliente.getFechapromo());
        
        Calendar fecha_actual = Calendar.getInstance();

        int ultima_promocion_mes = ultima_promocion.get(Calendar.MONTH)+1;
        int ultima_promocion_año = ultima_promocion.get(Calendar.YEAR);
        int mes_actual = fecha_actual.get(Calendar.MONTH)+1;
        int año_actual = fecha_actual.get(Calendar.YEAR);

        System.out.println(ultima_promocion_mes);
        System.out.println(ultima_promocion_año);
        System.out.println(mes_actual);
        System.out.println(año_actual);        

              int estado = 0;
        if( cliente.getPromocion() == 0){
            if(año_actual > ultima_promocion_año){
                 estado = 1;
            }
            else if(mes_actual > ultima_promocion_mes){
                 estado = 1;
            }  
        }
          if( cliente.getPromocion() == 1){
                estado = 1;
          }
        cliente.setPromocion(estado);
        clienteDAO2.modificar(cliente);
          System.out.println(estado);        
    }
    
    
    public int estadoPromocion(String usuario) throws Exception{

        ClienteDAO clienteDAO2 = new ClienteDAO();
        Cliente cliente = clienteDAO2.BuscarPorUsuario(usuario);
        cliente.getFechapromo();
        
        Calendar ultima_promocion = Calendar.getInstance();
        ultima_promocion.setTime( cliente.getFechapromo());
        
        Calendar fecha_actual = Calendar.getInstance();

        int ultima_promocion_mes = ultima_promocion.get(Calendar.MONTH)+1;
        int ultima_promocion_año = ultima_promocion.get(Calendar.YEAR);
        int mes_actual = fecha_actual.get(Calendar.MONTH)+1;
        int año_actual = fecha_actual.get(Calendar.YEAR);

        System.out.println(ultima_promocion_mes);
        System.out.println(ultima_promocion_año);
        System.out.println(mes_actual);
        System.out.println(año_actual);        

        int estado = 0;
        if( cliente.getPromocion() == 0){
            if(año_actual > ultima_promocion_año){
                 estado = 1;
            }
            else if(mes_actual > ultima_promocion_mes){
                 estado = 1;
            }           
        }                      
        if( cliente.getPromocion() == 1){
              estado = 1;
        }            
    
        cliente.setPromocion(estado);
        clienteDAO2.modificar(cliente);
        
        return estado;
    }
}
