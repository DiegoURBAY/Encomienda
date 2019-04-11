
package controlador;

import dao.ClienteDAO;
import dao.EncomiendaDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entidad.Encomienda;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpSession;

public class SERVEncomienda extends HttpServlet {

    private static String insert= "/RegistrarEncomienda.jsp";
    private static String edit = "/EditarEncomienda.jsp";
    private static String list_encomienda = "/ListarEncomienda.jsp";
    private static String exito = "/exito.jsp";
    private EncomiendaDAO encomiendadao;
    private ClienteDAO clientedao;       
    Encomienda enc = new Encomienda();

            
     public SERVEncomienda() {
    	encomiendadao = new EncomiendaDAO(){};
        clientedao = new ClienteDAO(){};
    }         
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          String forward = "";   
            String action = request.getParameter("action");
                
            //ELIMINAR CLIENT
            if (action.equalsIgnoreCase("delete")) {  
                
                int idCliente = Integer.parseInt(request.getParameter("nivel"));
                try {

                    int id = Integer.parseInt(request.getParameter("id"));
                    enc.setId(id);
                    encomiendadao.eliminar(enc);  
                    List<Encomienda> encomienda = encomiendadao.consultar(idCliente);
                    request.setAttribute("encomienda", encomienda); 
                    
                } catch (Exception ex) {
                }              
                          
                response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=refresh&nivel="+idCliente);  
                
    //              RequestDispatcher view = request.getRequestDispatcher(forward);
      //     view.forward(request, response);
            }
            //EDITAR CLIENTE
            else if (action.equalsIgnoreCase("edit")) {
                try {
                    forward = edit;

                    int id = Integer.parseInt(request.getParameter("id"));
                    Encomienda encomienda = encomiendadao.BuscarPorId(id);             
                    
                  
                    request.setAttribute("encomienda", encomienda);
                } catch (Exception ex) {
                }
                              
            RequestDispatcher view = request.getRequestDispatcher(forward);
           view.forward(request, response);
            }            
            //INSERTAR CLIENTE    
            else if(action.equalsIgnoreCase("insert")) {        
            try {
                forward = insert;
                int nivel = Integer.parseInt(request.getParameter("nivel"));              
             
                request.setAttribute("nivel", nivel);

                             
            } catch (Exception ex) {
 
            }
                        RequestDispatcher view = request.getRequestDispatcher(forward);
           view.forward(request, response);
            
            }
            //LISTAR O ACTUALIZAR CLIENTE
            else if(action.equalsIgnoreCase("refresh")){
                     HttpSession sesion = request.getSession();
                     
                 int idCliente = 0;
                if(request.getParameter("nivel")!=null){
                     idCliente =Integer.parseInt(request.getParameter("nivel"));
                }
                try {
                    forward = list_encomienda;
                    List<Encomienda> encomienda = encomiendadao.consultar(idCliente);
                    request.setAttribute("encomienda", encomienda); 
                 
                 //   sesion.setAttribute("nivel", request);
                } catch (Exception e) {
                }
                               
            RequestDispatcher view = request.getRequestDispatcher(forward);
           view.forward(request, response);
            }
             
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String origen = request.getParameter("txtOrigen");
        String destino = request.getParameter("txtDestino");
        String envio = request.getParameter("txtEnvio");
        String llegada = request.getParameter("txtLlegada");
        String id =request.getParameter("txtId");
        int idCliente =Integer.parseInt(request.getParameter("txtIdCliente"));

        
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateEnvio = sdf1.parse(envio);
            java.util.Date dateLlegada = sdf1.parse(llegada);
            java.sql.Date sqlStartDateEnvio = new java.sql.Date(dateEnvio.getTime()); 
            java.sql.Date sqlStartDateLlegada = new java.sql.Date(dateLlegada.getTime()); 
            
            Encomienda encomienda = new Encomienda();
            encomienda.setOrigen(origen);            
            encomienda.setDestino(destino);      
            encomienda.setEnvio(sqlStartDateEnvio);      
            encomienda.setLlegada(sqlStartDateLlegada);            
            encomienda.setIdCliente(idCliente);
           
                if (id == null || id.isEmpty()) {
                    try {
                        encomiendadao.insertar(encomienda);
                    } catch (Exception ex) {
                        
                    }
                } else {                    
                    try {
                        encomienda.setId(Integer.parseInt(id));
                        encomiendadao.modificar(encomienda);
                    } catch (Exception ex) {
                                            
                    }
                } 
        } catch (Exception e) {
        }
                   
        response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=refresh&nivel="+idCliente);   
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

        java.sql.Date sqlStartDateInicio = new java.sql.Date(inicio.getTime());
        java.sql.Date sqlStartDateFinal = new java.sql.Date(fin.getTime());

        List<java.sql.Date> ejemploLista = new ArrayList<>();
        ejemploLista.add(sqlStartDateInicio);
        ejemploLista.add(sqlStartDateFinal);
        
        return ejemploLista;
        
    }
}
