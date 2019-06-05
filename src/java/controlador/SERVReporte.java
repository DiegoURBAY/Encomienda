
package controlador;

import com.google.gson.Gson;
import dao.ClienteDAO;
import dao.ReporteDAO;
import entidad.Cliente;
import entidad.Encomienda;
import entidad.Reporte;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;


public class SERVReporte extends HttpServlet {

    private ClienteDAO clientedao;
    private ReporteDAO reporteDAO;
           
    public SERVReporte() {
        reporteDAO = new ReporteDAO(){};
        clientedao = new ClienteDAO(){};
    }   
     
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
        /*       
        try (PrintWriter out = response.getWriter()) {
             TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SERVReporte</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SERVReporte at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    */
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);      
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
         
         
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");                   
        String mensaje = "";        
        String estado = "";        
                
        try {
            if (action.equalsIgnoreCase("listarEncomiendaPorFecha")) {                
            HttpSession sesion = request.getSession();
            
                if(sesion.getAttribute("usuario")!=null){            
                    String usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);
                    int nivel = cliente.getNivel();
                    
                    String fech_ini = request.getParameter("fechaInicio");
                    String fech_fin = request.getParameter("fechaFinal");

                    List<java.sql.Date> cambio = Fechas(fech_ini, fech_fin);
                    
                    List<Reporte> reporte_para_cliente = new ArrayList<>();
                    if(nivel == 2){
                        reporte_para_cliente = reporteDAO.consultarEncomiendaPorFecha(cambio.get(0), cambio.get(1), cliente.getId());
                    }
                    else if(nivel == 1){
                        //si se le envia el dni con un valor diferente de null hara esto
                        if(request.getParameter("dni")!=null){
                            String dni = request.getParameter("dni");
                            Cliente cliente_por_dni = clientedao.BuscarPorDni(dni);
                            reporte_para_cliente = reporteDAO.consultarEncomiendaPorFecha(cambio.get(0), cambio.get(1), cliente_por_dni.getId());
                        //de lo contrario
                        }else {
                            reporte_para_cliente = reporteDAO.consultarEncomiendaPorFecha(cambio.get(0), cambio.get(1), 0);
                        }
                        
                    }
                    
                    if(reporte_para_cliente.size() < 1){
                        mensaje = "vacio";
                    }else{
                        mensaje = new Gson().toJson(reporte_para_cliente); 
                    }                                        
                    estado = "ok";
                }
                
            }   //consultarEncomiendaPorFecha2
            else if (action.equalsIgnoreCase("listarEncomiendaPorFecha2")) {                
            HttpSession sesion = request.getSession();
            
                if(sesion.getAttribute("usuario")!=null){            
                    String usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);     
                    
                    int nivel = cliente.getNivel();
                    
                    String fech_ini = request.getParameter("fechaInicio");
                    String fech_fin = request.getParameter("fechaFinal");

                    List<java.sql.Date> cambio = Fechas(fech_ini, fech_fin);

                   List<Reporte> reporte_para_cliente = new ArrayList<>();
                    if(nivel == 2){
                        reporte_para_cliente = reporteDAO.consultarEncomiendaPorFecha2(cambio.get(0), cambio.get(1), cliente.getId());
                    }
                    else if(nivel == 1){
                        if(request.getParameter("dni")!=null){
                              String dni = request.getParameter("dni");
                            Cliente cliente_por_dni = clientedao.BuscarPorDni(dni);
                            reporte_para_cliente = reporteDAO.consultarEncomiendaPorFecha2(cambio.get(0), cambio.get(1), cliente_por_dni.getId());
                        }
                        else{
                            reporte_para_cliente = reporteDAO.consultarEncomiendaPorFecha2(cambio.get(0), cambio.get(1), 0);
                        }
                        
                    }
                                        
                    if(reporte_para_cliente.size() < 1){
                        mensaje = "vacio";
                    }else{
                        mensaje = new Gson().toJson(reporte_para_cliente); 
                    }                                        
                    estado = "ok";
                }            

            }
        } catch (Exception e) {
            estado = "error";
            mensaje = "error: "+e;
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
                Logger.getLogger(SERVReporte.class.getName()).log(Level.SEVERE, null, ex);
            }
        }         

    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public List<java.sql.Date> Fechas(String fech_ini, String fech_fin) throws ParseException {
                     
        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yy");

        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

        //parsear String (dd/mm/yy) a Date (dd/mm/yy)
        Date date = parseador.parse(fech_ini);              
        Date date2 = parseador.parse(fech_fin);

        //formatear el Date (dd/mm/yy) a un String (yyyy-MM-dd)
        String d1 = formateador.format(date);
        String d2 = formateador.format(date2);

        
        
        //Aca tengo en fomarto yyyy-MM-dd"

        //formatear el String (yyyy-MM-dd) a un Date (yyyy-MM-dd)
        java.util.Date inicio = formateador.parse(d1);
        java.util.Date fin = formateador.parse(d2);
            
        //se aumenta un dia
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fin); // Configuramos la fecha que se recibe

        calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días    
        
        java.util.Date fin2 = calendar.getTime();

        java.sql.Date sqlStartDateInicio = new java.sql.Date(inicio.getTime());
        java.sql.Date sqlStartDateFinal = new java.sql.Date(fin2.getTime());
                   
        List<java.sql.Date> ejemploLista = new ArrayList<>();
        ejemploLista.add(sqlStartDateInicio);
        ejemploLista.add(sqlStartDateFinal);
        
        return ejemploLista;
        
    }    
    
}
