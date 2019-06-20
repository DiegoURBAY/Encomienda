
package controlador;

import com.google.gson.Gson;
import dao.CalificacionDAO;
import dao.ClienteDAO;
import dao.EncomiendaDAO;
import dao.Envio;
import dao.LugarDAO;
import entidad.Calificacion;
import entidad.Cliente;
import entidad.Disponibilidad;
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
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;


public class SERVEnvio extends HttpServlet {

    private ClienteDAO clienteDAO;  
    private EncomiendaDAO encomiendaDAO;  
    private LugarDAO lugarDAO;    
    private CalificacionDAO calificacionDAO;
            
     public SERVEnvio() {    	
        clienteDAO = new ClienteDAO();        
        encomiendaDAO = new EncomiendaDAO();
        lugarDAO = new LugarDAO();        
        calificacionDAO = new CalificacionDAO();    
    }             

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("X-Frame-Options", "DENY");
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SERVEnvio</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SERVEnvio at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.addHeader("X-Frame-Options", "DENY");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");          
        String estado = "";          
        String mensaje = "";
        String vista = "";
        String usuario_de_login = null;
        
        int tipo_envio = 0;
        try {
            if(action.equalsIgnoreCase("sent")){
                HttpSession sesion = request.getSession();   
                if(sesion.getAttribute("usuario")!=null){ 
                    vista = "EnviarCalificacion.jsp";
                    tipo_envio = 1;
                }
            }
            else if(action.equalsIgnoreCase("buscar")){
                estado = "ok"; 
                String usuario = request.getParameter("usuario");
                int encomienda = Integer.parseInt(request.getParameter("encomienda"));
                                
                Cliente cliente_buscado = clienteDAO.BuscarPorUsuario(usuario);
                List<Encomienda> encomienda_buscado = encomiendaDAO.consultarEncomiendaPorIdCliente(cliente_buscado.getId());
                
                int pertenece = 0;
                for (int i = 0; i < encomienda_buscado.size(); i++) {
                    if(encomienda_buscado.get(i).getId() == encomienda){
                        pertenece = 1;
                    }
                }
                
                List<Calificacion> calificacionList = calificacionDAO.consultar();

                int existe = 0;
                for (int i = 0; i < calificacionList.size(); i++) {
                    if(calificacionList.get(i).getEncomienda() == encomienda){
                        existe = 1;
                    }
                //    System.out.println(calificacionList.get(i).getEncomienda());
                }
                        
                
                if(pertenece == 1 && existe == 0){
                                           
                    Encomienda encomienda_usuario = encomiendaDAO.BuscarPorId(encomienda);
              
                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    
                    Date envio_date = encomienda_usuario.getFechaRegistroTime();     

                    String fecha_string = sdf.format(envio_date);                                                            

                    encomienda_usuario.setFechaRegistroTimeString(fecha_string);                      
              
                    Lugar lugar_origen = lugarDAO.BuscarPorId(encomienda_usuario.getOrigen());
                    Lugar lugar_destino = lugarDAO.BuscarPorId(encomienda_usuario.getDestino());
                    encomienda_usuario.setOrigenS( lugar_origen.getNombre());
                    encomienda_usuario.setDestinoS(lugar_destino.getNombre());            
                    
                    mensaje = new Gson().toJson(encomienda_usuario.getId()+", "+encomienda_usuario.getOrigenS()+", "+encomienda_usuario.getDestinoS()+", "+encomienda_usuario.getFechaRegistroTimeString());
                    
                }
                else if(pertenece == 1 && existe == 1){     
                     estado = "error";
                    mensaje = "Ya ha dado su opinión a la encomienda "+encomienda;     
                }
                else{
                    estado = "error";
                    mensaje = "no tiene registrado una encomienda con el id "+encomienda;     
                }
            }            
        } catch (Exception e) {
            estado = "error";
            mensaje = "error al buscar";     
            Logger.getLogger(SERVEnvio.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            
            if(tipo_envio == 1){
                request.setAttribute("usuario",  usuario_de_login);
                RequestDispatcher view = request.getRequestDispatcher(vista);
                view.forward(request, response); 
            }
            else{
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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.addHeader("X-Frame-Options", "DENY");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");      
        String estado = ""; 
        String mensaje = "";

     //   String vista = "";
        
        try {
            //  if(request.getParameter("txtInsertar")!=null){  
            if(action.equalsIgnoreCase("insert")){  
                Calificacion calificacion = new Calificacion();
                
                estado = "ok";
                mensaje = "Envío exitoso"; 
                
                int encomienda = 0;
                String perdida= "";
                String demora= "";
                String daño= "";
                String comentario= "";

                if(request.getParameter("txtEncomienda")!=null){
                    encomienda= Integer.parseInt(request.getParameter("txtEncomienda"));
                }
                if(request.getParameter("perdida")!=null){
                    perdida= request.getParameter("perdida");
                }        
                if(request.getParameter("tiempo")!=null){
                    demora= request.getParameter("tiempo");
                }                
                if(request.getParameter("dao")!=null){
                    daño= request.getParameter("dao");
                }                
                if(request.getParameter("txtComentario")!=null){
                    comentario= request.getParameter("txtComentario");
                }                
                                                   
                
                int idPerdida = 0;
                if(perdida.equalsIgnoreCase("si")){
                    idPerdida = 1;
                }
                else if(perdida.equalsIgnoreCase("no")){
                     idPerdida = 2;
                }
                else if(perdida.equalsIgnoreCase("nunca")){
                     idPerdida = 3;
                }
                
                int idDemora = 0;
                if(demora.equalsIgnoreCase("si")){
                    idDemora = 1;
                }
                else if(demora.equalsIgnoreCase("no")){
                     idDemora = 2;
                }
    
                int idDaño = 0;
                if(daño.equalsIgnoreCase("si")){
                    idDaño = 1;
                }
                else if(daño.equalsIgnoreCase("no")){
                     idDaño = 2;
                }                
                
                calificacion.setEncomienda(encomienda);
                calificacion.setPerdida(idPerdida);
                calificacion.setDemora(idDemora);
                calificacion.setDaño(idDaño);
                calificacion.setComentario(comentario);
                                
                calificacionDAO.insertar(calificacion);
                
                
        //       vista = "/exito.jsp";
            }
        }catch (Exception e) {
            estado = "error";
            mensaje = "error al grabar";      
           
            Logger.getLogger(SERVPromocion.class.getName()).log(Level.SEVERE, null, e);
      //      vista = "/error.jsp";
        }
        //      vista = "/error.jsp";
        
        
        finally{
      //    response.sendRedirect(request.getContextPath() + vista);
          
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
        Calificacion calificacion = new Calificacion();
        CalificacionDAO calificacionDAO = new CalificacionDAO();
        /*
          int encomienda= Integer.parseInt(request.getParameter("txtEncomienda"));
                String perdida= request.getParameter("perdida");
                String demora= request.getParameter("tiempo");
                String daño= request.getParameter("daño");
                String comentario= request.getParameter("txtComentario");
         */       
         int encomienda= 1;
                String perdida="si";
                String demora= "si";
                String daño= "si";
                String comentario= "qwe";
                
                int idPerdida = 0;
                if(perdida.equalsIgnoreCase("si")){
                    idPerdida = 1;
                }
                else if(perdida.equalsIgnoreCase("no")){
                     idPerdida = 2;
                }
                else if(perdida.equalsIgnoreCase("nunca")){
                     idPerdida = 3;
                }
                
                int idDemora = 0;
                if(demora.equalsIgnoreCase("si")){
                    idDemora = 1;
                }
                else if(demora.equalsIgnoreCase("no")){
                     idDemora = 2;
                }
    
                int idDaño = 0;
                if(daño.equalsIgnoreCase("si")){
                    idDaño = 1;
                }
                else if(daño.equalsIgnoreCase("no")){
                     idDaño = 2;
                }                
                
                calificacion.setEncomienda(encomienda);
                calificacion.setPerdida(idPerdida);
                calificacion.setDemora(idDemora);
                calificacion.setDaño(idDaño);
                calificacion.setComentario(comentario);
                                
                calificacionDAO.insertar(calificacion);
    }
}
