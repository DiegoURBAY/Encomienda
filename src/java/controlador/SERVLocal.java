
package controlador;

import com.google.gson.Gson;
import dao.LocalDAO;
import dao.LugarDAO;
import entidad.Local;
import entidad.Lugar;
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
import org.json.JSONException;
import org.json.JSONObject;

public class SERVLocal extends HttpServlet {

    private LocalDAO ubicaciondao;
    private LugarDAO lugarDAO;
    Local u = new Local();

            
     public SERVLocal() {
    	ubicaciondao = new LocalDAO(){};
        lugarDAO = new LugarDAO(){};
    }         
            

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("X-Frame-Options", "DENY");
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.addHeader("X-Frame-Options", "DENY");
        processRequest(request, response);         
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");          
        String estado = "";          
        String mensaje = "";

        try {
            //ELIMINAR UBICACIÓN
            if (action.equalsIgnoreCase("delete")) {                  
                    u.setId(Integer.parseInt(request.getParameter("id")));
                    ubicaciondao.eliminar(u);         
                    estado = "ok";
                    mensaje = "Borrado exitoso";


            }
            //EDITAR USUARIO
            else if (action.equalsIgnoreCase("edit")) {
                try {
                    estado = "ok"; 
                    String nombre = request.getParameter("titulo");
                    List<Local> titulo = ubicaciondao.ConsultarTitulo(nombre);
                    mensaje = new Gson().toJson(titulo); 
                  
                } catch (Exception ex) {
                    Logger.getLogger(SERVLocal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }              
            //LISTAR O ACTUALIZAR UBICACIÓN
            else if(action.equalsIgnoreCase("refresh")){                 
                estado = "ok";                                
                Thread.sleep(2000);
                List<Local> ubicacion = ubicaciondao.consultar();
             
                for (int i = 0; i < ubicacion.size(); i++) {
                    int idLugar = ubicacion.get(i).getIdLugar();
                    Lugar lugar = lugarDAO.BuscarPorId(idLugar);
                    lugar.getNombre();
                    
                    ubicacion.get(i).setLugarString(lugar.getNombre());
                    
                }
                
                mensaje = new Gson().toJson(ubicacion);                                            
            }
            
            //LISTAR O ACTUALIZAR UBICACIÓN rapida
            else if(action.equalsIgnoreCase("refreshRapido")){                 
                estado = "ok";                                
                List<Local> ubicacion = ubicaciondao.consultar();
             
                for (int i = 0; i < ubicacion.size(); i++) {
                    int idLugar = ubicacion.get(i).getIdLugar();
                    Lugar lugar = lugarDAO.BuscarPorId(idLugar);
                    lugar.getNombre();                    
                    ubicacion.get(i).setLugarString(lugar.getNombre());                    
                }
                
                mensaje = new Gson().toJson(ubicacion);                                            
            }            
            
        } catch (Exception ex) {
            Logger.getLogger(SERVLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                JSONObject jsonObject=new  JSONObject();
                jsonObject.put("estado", estado);                     
                jsonObject.put("mensaje", mensaje);
                response.setCharacterEncoding("utf8");
                response.setContentType("application/json");
                out.print(jsonObject);
                
     
            } catch (JSONException ex) {
                Logger.getLogger(SERVLocal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /*
           if(action.equalsIgnoreCase("local")){
             HttpSession sesion = request.getSession();
               sesion.setAttribute("idUsuario", 1);             
                    
                RequestDispatcher view = request.getRequestDispatcher("GestionarLocal.jsp");
                view.forward(request, response);                           
       
     
           }
           if(action.equalsIgnoreCase("ruta")){
                            HttpSession sesion = request.getSession();
               sesion.setAttribute("idUsuario", 1);

RequestDispatcher view = request.getRequestDispatcher("GestionarRuta.jsp");
                view.forward(request, response);                     
           }          
        */
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.addHeader("X-Frame-Options", "DENY");
        processRequest(request, response);
        
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        String titulo =  request.getParameter("titulo");
        String cx = request.getParameter("cx");
        String cy = request.getParameter("cy");
        String telefono = request.getParameter("tel");
        String departamento = request.getParameter("departamento");
        String direc =request.getParameter("direc");
         

        Local u = new Local();    
        u.setTitulo(titulo);
        u.setCx(cx);                       
        u.setCy(cy);
        u.setDireccion(direc);
        u.setTelefono(telefono);
        
        String action = request.getParameter("action");      
        String estado = ""; 
        String mensaje = "";
        //ELIMINAR USUARIO
              
        try {
            
            Lugar lugar = lugarDAO.ConsultarPorNombre(departamento);
            int idLugar = lugar.getId();
            u.setIdLugar(idLugar);  
           
                if (action.equalsIgnoreCase("insert")) {
                    
                ubicaciondao.insertar(u);
                Thread.sleep(2000);
                estado = "ok";
                mensaje = "Grabación exitosa";
            
                }
                if(action.equalsIgnoreCase("update")){  
                    String id =request.getParameter("id");
                    try {
                        u.setId(Integer.parseInt(id));
                        ubicaciondao.modificar(u);
                        estado = "ok";
                        mensaje = "Edición exitosa";
                    } catch (Exception ex) {
                        Logger.getLogger(SERVLocal.class.getName()).log(Level.SEVERE, null, ex);
                    }        
                }

        } catch (Exception e) {
            estado = "error";
            mensaje = "error al grabar";
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(SERVLocal.class.getName()).log(Level.SEVERE, null, ex);
            }
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
/*
      public static void main(String[] json) throws Exception {

 LocalDAO localDAO = new LocalDAO();
Local tipoEncomienda = new Local();
        tipoEncomienda.setId(21);
        tipoEncomienda.setTitulo("sobre");
        tipoEncomienda.setCx("sobre");
        tipoEncomienda.setCy("sobre");
        tipoEncomienda.setDireccion("sobre");
        tipoEncomienda.setTelefono("sobre");
        
          try {
              localDAO.insertar(tipoEncomienda);
          } catch (Exception e) {
              throw e;
          }
}    
*/
}
