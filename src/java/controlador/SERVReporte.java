
package controlador;

import com.google.gson.Gson;
import dao.ClienteDAO;
import dao.ExcelDAO;
import dao.ReporteDAO;
import entidad.Cliente;
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
         response.setHeader("X-Frame-Options", "DENY");
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
                response.setHeader("X-Frame-Options", "DENY");

        processRequest(request, response);      
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       response.setHeader("X-Frame-Options", "DENY");

        processRequest(request, response);
         
         
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");                   
        String mensaje = "";        
        String cantidad = "";     
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
                        //si se le envia el dni  lleno entonces se esta buscado una empresa o persona natural
                        if(request.getParameter("dni")!=null){
                            String dni = request.getParameter("dni");
                            Cliente cliente_por_dni = clientedao.BuscarPorDni(dni);
                            reporte_para_cliente = reporteDAO.consultarEncomiendaPorFecha(cambio.get(0), cambio.get(1), cliente_por_dni.getId());
                        //de lo contrario, si es nulo se esta buscando todas las encomiendas
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
                
            }
            //consultarEncomiendaPorFecha2
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
            //consultarEncomiendaPorFecha2
            else if (action.equalsIgnoreCase("listarEncomiendaPorMes")) {                
            HttpSession sesion = request.getSession();
            
                if(sesion.getAttribute("usuario")!=null){            
                    String usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);     
                    
                    int nivel = cliente.getNivel();
                                        
                    String tipo = request.getParameter("tipo");
                    String dni = request.getParameter("dni");
                    
                    int eleccion = 0;
                    //eleccion = 1 cuando no hay dni
                    //eso sirve para el administrador, el cliente no necesita
                    if(dni == null || dni.isEmpty()){
                        eleccion =1 ;
                    }
                    //Si no se le asigna null, el ReporteDAO no lo tomará como tal en caso sea así
                    String mes = request.getParameter("mes");
                    
                    String fech_ini = request.getParameter("fechaInicio");
                    String fech_fin = request.getParameter("fechaFinal");         
                    
                    List<java.sql.Date> cambio = Fechas(fech_ini, fech_fin);                    

                    List<Reporte> reporte_para_cliente = new ArrayList<>();
                    if(nivel == 2){
                        reporte_para_cliente = reporteDAO.consultarEncomiendaPorMes(tipo, mes , cambio.get(0), cambio.get(1), cliente.getId());
                    }
                    else if(nivel == 1){
                  
                        //ver reporte de todo (cliente y empresa)
                        if(eleccion== 1){
                            //ver reporte de un mes en especifico
                            if(request.getParameter("mes") != null){
                                
                                reporte_para_cliente = reporteDAO.consultarEncomiendaPorMes(tipo, mes , cambio.get(0), cambio.get(1), 0);
                            }
                            //ver reporte con la fechas establecidas
                            else{
                                reporte_para_cliente = reporteDAO.consultarEncomiendaPorMes(tipo, mes , cambio.get(0), cambio.get(1), 0);
                            } 
                        }
                        //ver reporte com cliente o empresa
                        else{
                            Cliente cliente_id = clientedao.BuscarPorDni(dni);
                            
                            //ver reporte  de un mes
                            if(request.getParameter("mes") != null){
                                mes = request.getParameter("mes");
                                reporte_para_cliente = reporteDAO.consultarEncomiendaPorMes(tipo, mes , cambio.get(0), cambio.get(1), cliente_id.getId());
                            }
                            //ver reporte con la fechas establecidas
                            else{
                                reporte_para_cliente = reporteDAO.consultarEncomiendaPorMes(tipo, mes , cambio.get(0), cambio.get(1), cliente_id.getId());

                            }                             
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
            else if (action.equalsIgnoreCase("listarClientePorFecha")) {
            HttpSession sesion = request.getSession();
            
                if(sesion.getAttribute("usuario")!=null){            
                    
                    String fech_ini = request.getParameter("fechaInicio");
                    String fech_fin = request.getParameter("fechaFinal");
                    List<java.sql.Date> cambio = Fechas(fech_ini, fech_fin);
                    
                    List<Reporte> reporte_para_cliente = new ArrayList<>();
                    
                    List<Integer> conteo =  new ArrayList<>();
                    String tipo = request.getParameter("tipo");
                    
                    if(tipo.equals("1")){
                        reporte_para_cliente = reporteDAO.consultarClientePorFecha(cambio.get(0), cambio.get(1));
                        
                        int cont_empresa = 0;
                        int cont_persona = 0;
                        for (int i = 0; i < reporte_para_cliente.size(); i++) {
                                cont_empresa = cont_empresa +reporte_para_cliente.get(i).getSobre();
                                cont_persona = cont_persona +reporte_para_cliente.get(i).getPaquete();
                        }
                        conteo.add(cont_empresa);
                        conteo.add(cont_persona);
                        cantidad = new Gson().toJson(conteo); 
                    }
                    else if(tipo.equals("2")){
                        reporte_para_cliente = reporteDAO.consultarClientePorFecha2(cambio.get(0), cambio.get(1));
                    }                 
                    
                    if(reporte_para_cliente.size() < 1){
                        mensaje = "vacio";
                    }else{
                        mensaje = new Gson().toJson(reporte_para_cliente); 
                    }                                        
                    estado = "ok";
                }
                //consultarClientePorFecha
            }
            else if (action.equalsIgnoreCase("listarClientePorFecha2")) {
            HttpSession sesion = request.getSession();
            
                if(sesion.getAttribute("usuario")!=null){            
                    
                    String fech_ini = request.getParameter("fechaInicio");
                    String fech_fin = request.getParameter("fechaFinal");
                    List<java.sql.Date> cambio = Fechas(fech_ini, fech_fin);
                    
                    List<Reporte> reporte_para_cliente = new ArrayList<>();
                    

                    //cliente y mes para grafico detallado
                    String cliente = request.getParameter("cliente");
                    String mes = request.getParameter("mes");
                    
                    int tipo_cliente;

                    if(cliente.equalsIgnoreCase("persona")){
                        tipo_cliente = 8;
                    }
                    else{
                        tipo_cliente =11;
                    }

                    if(request.getParameter("mes") != null){
                        reporte_para_cliente = reporteDAO.consultarClientePorFecha3(tipo_cliente, mes ,cambio.get(0), cambio.get(1));
                    }
                    else  if(request.getParameter("mes") == null){
                        reporte_para_cliente = reporteDAO.consultarClientePorFecha3(tipo_cliente, mes ,cambio.get(0), cambio.get(1));
                    }

                    
                    if(reporte_para_cliente.size() < 1){
                        mensaje = "vacio";
                    }else{
                        mensaje = new Gson().toJson(reporte_para_cliente); 
                    }                                        
                    estado = "ok";
                }
                //consultarClientePorFecha
            }            
            else if (action.equalsIgnoreCase("listarPrecioPorFecha")) {
            HttpSession sesion = request.getSession();
            
                if(sesion.getAttribute("usuario")!=null){ 
                    String fech_ini = request.getParameter("fechaInicio");
                    String fech_fin = request.getParameter("fechaFinal");
                    List<java.sql.Date> cambio = Fechas(fech_ini, fech_fin);
                    
                    List<Reporte> reporte_de_precios = new ArrayList<>();
                    
                    List<Integer> conteo =  new ArrayList<>();                    
                     String tipo =  request.getParameter("tipo");
                     
                    if(tipo.equals("1")){
                        reporte_de_precios = reporteDAO.consultarPrecioPorFecha(cambio.get(0), cambio.get(1));
                        
                        int sum_empresa = 0;
                        int sum_persona = 0;
                        for (int i = 0; i < reporte_de_precios.size(); i++) {                               
                            sum_empresa = sum_empresa +reporte_de_precios.get(i).getSobre();                              
                            sum_persona = sum_persona +reporte_de_precios.get(i).getPaquete();
                        }
                        conteo.add(sum_empresa);
                        conteo.add(sum_persona);
                        cantidad = new Gson().toJson(conteo); 
                    }
                    else if(tipo.equals("2")){
                       reporte_de_precios = reporteDAO.consultarPrecioPorFecha2(cambio.get(0), cambio.get(1));
                    }     
                                       
                    if(reporte_de_precios.size() < 1){
                        mensaje = "vacio";
                    }else{
                        mensaje = new Gson().toJson(reporte_de_precios); 
                    }                                        
                    estado = "ok";                    
                }    
            }
            else if (action.equalsIgnoreCase("listarPrecioPorFecha2")) {
            HttpSession sesion = request.getSession();
            
                if(sesion.getAttribute("usuario")!=null){ 
                    String fech_ini = request.getParameter("fechaInicio");
                    String fech_fin = request.getParameter("fechaFinal");
                    List<java.sql.Date> cambio = Fechas(fech_ini, fech_fin);
                    
                    List<Reporte> reporte_de_precios = new ArrayList<>();
                    
                    //cliente y mes para grafico detallado
                    String cliente = request.getParameter("cliente");
                    String mes = request.getParameter("mes");
                    
                    int tipo_cliente;

                    if(cliente.equalsIgnoreCase("persona")){
                        tipo_cliente = 8;
                    }
                    else{
                        tipo_cliente =11;
                    }

                    if(request.getParameter("mes") != null){
                        reporte_de_precios = reporteDAO.consultarPrecioPorFecha3(tipo_cliente, mes ,cambio.get(0), cambio.get(1));
                    }
                    else  if(request.getParameter("mes") == null){
                        reporte_de_precios = reporteDAO.consultarPrecioPorFecha3(tipo_cliente, mes ,cambio.get(0), cambio.get(1));
                    }
                    
                    if(reporte_de_precios.size() < 1){
                        mensaje = "vacio";
                    }else{
                        mensaje = new Gson().toJson(reporte_de_precios); 
                    }                                        
                    estado = "ok";                    
                }    
            }        
            else if (action.equalsIgnoreCase("generarIngresos")) {
            HttpSession sesion = request.getSession();
            
                if(sesion.getAttribute("usuario")!=null){ 
                    String fech_ini = request.getParameter("fechaInicio");
                    String fech_fin = request.getParameter("fechaFinal");
                    List<java.sql.Date> cambio = Fechas(fech_ini, fech_fin);
                    
                                    
                ExcelDAO excelDAO = new ExcelDAO();
                
                String tipo =  request.getParameter("tipo");                
                String cliente = request.getParameter("cliente");
                String mes = request.getParameter("mes");

                int tipo_cliente;
                String tipo_cliente_string;

                if(cliente.equalsIgnoreCase("persona")){
                    tipo_cliente = 8;
                    tipo_cliente_string = "persona";
                }
                else{
                    tipo_cliente =11;
                    tipo_cliente_string = "empresa";
                }                
                String situacion = "";
                
                //el tipo ayuda saber que excel exportar
                if(tipo.equalsIgnoreCase("1")){
                      situacion = excelDAO.generarIngresos(cambio.get(0), cambio.get(1));
                }
                else if(tipo.equalsIgnoreCase("2")){
                     situacion = excelDAO.generarIngresos(cambio.get(0), cambio.get(1));
                     excelDAO.generarIngresosDetallado(tipo_cliente_string, tipo_cliente, mes,  cambio.get(0), cambio.get(1));
                }

                
                if(situacion.equalsIgnoreCase("ok")){
                    estado = "ok";
                    mensaje = "Se ha descargado excel en el escritorio (Desktop)"; 
                }else{
                    estado = "error";
                    mensaje = "Se ha producido un error"+situacion; 
                }
               
                    
                }    

            }
            else if (action.equalsIgnoreCase("generarCliente")) {
            HttpSession sesion = request.getSession();
            
                if(sesion.getAttribute("usuario")!=null){ 
                    String fech_ini = request.getParameter("fechaInicio");
                    String fech_fin = request.getParameter("fechaFinal");
                    List<java.sql.Date> cambio = Fechas(fech_ini, fech_fin);
                    
                                    
                ExcelDAO excelDAO = new ExcelDAO();
                
                String tipo =  request.getParameter("tipo");                
                String cliente = request.getParameter("cliente");
                String mes = request.getParameter("mes");

                int tipo_cliente;
                String tipo_cliente_string;

                if(cliente.equalsIgnoreCase("persona")){
                    tipo_cliente = 8;
                    tipo_cliente_string = "persona";
                }
                else{
                    tipo_cliente =11;
                    tipo_cliente_string = "empresa";
                }                
                String situacion = "";
                
                //el tipo ayuda saber que excel exportar
                if(tipo.equalsIgnoreCase("1")){
                      situacion = excelDAO.generarClientes(cambio.get(0), cambio.get(1));
                }
                else if(tipo.equalsIgnoreCase("2")){
                     situacion = excelDAO.generarClientes(cambio.get(0), cambio.get(1));
                     excelDAO.generarClientesDetallado(tipo_cliente_string, tipo_cliente, mes,  cambio.get(0), cambio.get(1));
                }

                
                if(situacion.equalsIgnoreCase("ok")){
                     estado = "ok";
                    mensaje = "Se ha descargado excel en el escritorio (Desktop)"; 
                }else{
                    estado = "error";
                    mensaje = "Se ha producido un error"+situacion; 
                }
               
                    
                }    

            }        
            else if (action.equalsIgnoreCase("generarEncomienda")) {
            HttpSession sesion = request.getSession();
            
                if(sesion.getAttribute("usuario")!=null){            
                    String usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);     
                    
                    int nivel = cliente.getNivel();
                                        
                    String tipo_encomienda = request.getParameter("encomienda");
                    String dni = request.getParameter("dni");
                    
                    int eleccion = 0;
                    //eleccion = 1 cuando no hay dni
                    //eso sirve para el administrador, el cliente no necesita
                    if(dni == null || dni.isEmpty()){
                        eleccion =1 ;
                    }
                    //Si no se le asigna null, el ReporteDAO no lo tomará como tal en caso sea así
                    String mes = request.getParameter("mes");
                    
                    String fech_ini = request.getParameter("fechaInicio");
                    String fech_fin = request.getParameter("fechaFinal");         
                    
                    List<java.sql.Date> cambio = Fechas(fech_ini, fech_fin);   
                     
                    ExcelDAO excelDAO = new ExcelDAO();

                    String situacion = "";
                    if(nivel == 2){
                        situacion = excelDAO.generarEncomiendas(tipo_encomienda, cambio.get(0), cambio.get(1), cliente.getId(), cliente.getIdentificador() );
                    }
                    else if(nivel == 1){
                  
                        //ver reporte de todo (cliente y empresa)
                        if(eleccion== 1){
                            //ver reporte de un mes en especifico
                            if(request.getParameter("mes") != null){
                                
                    //            situacion = reporteDAO.consultarEncomiendaPorMes(tipo, mes , cambio.get(0), cambio.get(1), 0);
                            }
                            //ver reporte con la fechas establecidas
                            else{
                    //            situacion = reporteDAO.generarEncomiendas(tipo, mes , cambio.get(0), cambio.get(1), 0);
                            } 
                        }
                        //ver reporte com cliente o empresa
                        else{
                            Cliente cliente_id = clientedao.BuscarPorDni(dni);
                            
                            //ver reporte  de un mes
                            if(request.getParameter("mes") != null){
                                mes = request.getParameter("mes");
                        //        situacion = reporteDAO.consultarEncomiendaPorMes(tipo, mes , cambio.get(0), cambio.get(1), cliente_id.getId());
                            }
                            //ver reporte con la fechas establecidas
                            else{
                                situacion = excelDAO.generarEncomiendas(tipo_encomienda, cambio.get(0), cambio.get(1), cliente_id.getId(), cliente_id.getIdentificador());

                            }                             
                        }
                        
                    }
                                        
                    if(situacion.equalsIgnoreCase("ok")){
                         estado = "ok";
                        mensaje = "Se ha descargado excel en el escritorio (Desktop)"; 
                    }else{
                        estado = "error";
                        mensaje = "Se ha producido un error"+situacion; 
                    }
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
                jsonObject.put("cantidad", cantidad);                
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
