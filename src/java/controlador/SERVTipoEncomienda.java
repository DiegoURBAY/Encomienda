
package controlador;

import dao.TipoEncomiendaDAO;
import entidad.Encomienda;
import entidad.TipoEncomienda;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SERVTipoEncomienda extends HttpServlet {
    
    private static String insert= "/RegistrarTipoEncomiendaSobre.jsp";
    private static String insert_paquete= "/RegistrarTipoEncomiendaPaquete.jsp";
    private static String edit = "/EditarTipoEncomiendaSobre.jsp";
    private static String edit_paquete = "/EditarTipoEncomiendaPaquete.jsp";
    private static String list_encomienda = "/ListarTipoEncomienda.jsp";
    private TipoEncomiendaDAO tipoEncomiendaDAO;
    TipoEncomienda tipoEncomienda;

     public SERVTipoEncomienda() {
    	tipoEncomiendaDAO = new TipoEncomiendaDAO(){};
        tipoEncomienda = new TipoEncomienda(){};
    }           
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 response.setContentType("text/html;charset=UTF-8");
        String forward = "";   
        String action = request.getParameter("action");
                
            //ELIMINAR CLIENTE
            if (action.equalsIgnoreCase("delete")) {                 
                
                  int idEncomienda = Integer.parseInt(request.getParameter("idEncomienda"));
                try {
                    int id = Integer.parseInt(request.getParameter("id"));
                    tipoEncomienda.setId(id);
                    tipoEncomiendaDAO.eliminar(tipoEncomienda);                   
                    request.setAttribute("tipoEncomienda", tipoEncomiendaDAO.consultarTipoPorEncomienda(idEncomienda));                      
                } catch (Exception ex) {
                }              
                               
              response.sendRedirect(request.getContextPath() + "/SERVTipoEncomienda?action=refresh2&idEncomienda="+idEncomienda);  
            }

            //EDITAR Paquete
            else if (action.equalsIgnoreCase("edit")) {
                try {
                    String tipo = request.getParameter("tipo");                                        
    
                    int id = Integer.parseInt(request.getParameter("id"));
                    tipoEncomienda = tipoEncomiendaDAO.BuscarPorId(id);    
                    
                   
                    if(tipo.equalsIgnoreCase("sobre")){
                        forward = edit;
                    }
                    if(tipo.equalsIgnoreCase("paquete")){
                         forward = edit_paquete;
                    }
                    request.setAttribute("tipoEncomienda", tipoEncomienda);

                } catch (Exception ex) {
                    
                }
                               
            RequestDispatcher view = request.getRequestDispatcher(forward);
           view.forward(request, response);
            }              
            
            //INSERTAR CLIENTE    
            else if(action.equalsIgnoreCase("insert")) {  
                HttpSession sesion = request.getSession();
            try {
                
                if(request.getParameter("tipo")!=null){
                       forward = insert_paquete;   
                }
                if(request.getParameter("tipo")== null){
                        forward = insert;   
                }                
                         
                int nivel = Integer.parseInt(request.getParameter("id")); 
                int idEncomienda = Integer.parseInt( request.getParameter("idEncomienda"));
                request.setAttribute("nivel", nivel);
                sesion.setAttribute("idEncomienda", idEncomienda);
                sesion.setAttribute("nivel", nivel);
                             
            } catch (Exception ex) {
 
            }
           RequestDispatcher view = request.getRequestDispatcher(forward);
           view.forward(request, response);
                               
            }                             
            
            //LISTAR O ACTUALIZAR ENCOMIENDA
            else if(action.equalsIgnoreCase("refresh")){
                
                forward = list_encomienda;
                //int id = Integer.parseInt(request.getParameter("id")); 
                                int idEncomienda= 0;
                if(request.getParameter("idEncomienda")!=null){
                     idEncomienda = Integer.parseInt(request.getParameter("idEncomienda")); 
                }      
         
                if(request.getParameter("id")!=null){
                     idEncomienda = Integer.parseInt(request.getParameter("id")); 
                }
                                                                    
                try { 
                    List tipos = tipoEncomiendaDAO.consultarTipoPorEncomienda(idEncomienda);

                } catch (Exception ex) {
                    Logger.getLogger(SERVTipoEncomienda.class.getName()).log(Level.SEVERE, null, ex);
                }
                RequestDispatcher view = request.getRequestDispatcher(forward);
                view.forward(request, response);     


            }
        
            else if(action.equalsIgnoreCase("refresh2")){
                 HttpSession sesion = request.getSession();
                 
                forward = list_encomienda;
                //int id = Integer.parseInt(request.getParameter("id")); 
                int idEncomienda= 0;
                if(request.getParameter("idEncomienda")!=null){
                     idEncomienda = Integer.parseInt(request.getParameter("idEncomienda")); 
                     
                    try { 
                        List tipos = tipoEncomiendaDAO.consultarTipoPorEncomienda(idEncomienda);

                        //int idEncomienda2 =  tipoEncomiendaDAO.ConsultarTipoNombrePorEncomienda(idEncomienda);
                        double peso = tipoEncomiendaDAO.ConsultarPesoPorEncomiendaID(idEncomienda);
                        
                        request.setAttribute("tipoEncomienda", tipos);                                        
                        sesion.setAttribute("idEncomienda", idEncomienda);
                        sesion.setAttribute("peso", peso);

                        RequestDispatcher view = request.getRequestDispatcher(forward);
                    view.forward(request, response);      

                    } catch (Exception ex) {
                        Logger.getLogger(SERVTipoEncomienda.class.getName()).log(Level.SEVERE, null, ex);
                    }                     
                }

            } 
            
         /*   
             else if(action.equalsIgnoreCase("refreshPrueba")){
                  HttpSession sesion = request.getSession();
                    
                  String usuario = null ;
                    if(sesion.getAttribute("usuarioPrueba")!=null){
                          usuario = (String) sesion.getAttribute("usuarioPrueba");
                    }                   
                 request.setAttribute("usuarioPrueba",  usuario);                 
                  RequestDispatcher view = request.getRequestDispatcher("RegistrarEncomienda1.jsp");
                    view.forward(request, response);      
            }
            */
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        request.setCharacterEncoding("UTF-8");
        /*
        int cantidadSobre = Integer.parseInt(request.getParameter("txtCantidadSobre"));
        double pesoSobre =Double.parseDouble(request.getParameter("txtPesoSobre"));
          double precioSobre =Double.parseDouble(request.getParameter("txtPrecioSobre"));  
        
         TipoEncomienda prueba = new TipoEncomienda();
         
         prueba.setAltura(0);
         prueba.setAnchura(0);
         prueba.setLargo(0);
 RequestDispatcher rd = null;
        if(request.getParameter("btnRegistrarPrueba")!=null){
            String tipo = "sobre";
            prueba.setTipo(tipo);
            prueba.setCantidad(cantidadSobre);
            prueba.setPeso(pesoSobre);         
            prueba.setPrecio(precioSobre);
            prueba.setIdEncomienda(15);
          
            try {
                tipoEncomiendaDAO.insertar(prueba);
               
            } catch (Exception ex) {
               
                Logger.getLogger(SERVTipoEncomienda.class.getName()).log(Level.SEVERE, null, ex);
            }
            rd = request.getRequestDispatcher("exito.jsp");
             rd.forward(request, response);  
        }                
   */
        String id =request.getParameter("txtIdTipoEncomienda");
        //String tipo = request.getParameter("txtTipo");
        
        int cantidad = Integer.parseInt(request.getParameter("txtCantidad"));
        double peso =Double.parseDouble(request.getParameter("txtPeso"));
        double precio =Double.parseDouble(request.getParameter("txtPrecio"));       
        int idEncomienda =Integer.parseInt(request.getParameter("txtidEncomienda"));
        
        double altura = 0;
        double anchura = 0;
        double largo = 0;
        
        if(request.getParameter("txtAltura")!= null){
            altura = Double.parseDouble(request.getParameter("txtAltura"));
        }
        if(request.getParameter("txtAnchura")!= null){
            anchura = Double.parseDouble(request.getParameter("txtAnchura"));
        }
        if(request.getParameter("txtLargo")!= null){
             largo = Double.parseDouble(request.getParameter("txtLargo"));
        }                   
        
                 
        TipoEncomienda tipoEncomienda = new TipoEncomienda();                
        tipoEncomienda.setCantidad(cantidad);
        tipoEncomienda.setPeso(peso);
        tipoEncomienda.setPrecio(precio);
        tipoEncomienda.setIdEncomienda(idEncomienda);
           
        if(request.getParameter("btnAceptarSobre")!=null){
        String tipo = "sobre";
        tipoEncomienda.setTipo(tipo);            
        tipoEncomienda.setAltura(0);      
        tipoEncomienda.setAnchura(0);      
        tipoEncomienda.setLargo(0);            

            
            if (id == null || id.isEmpty()) {
                try {                    
                    tipoEncomiendaDAO.insertar(tipoEncomienda);
                } catch (Exception ex) {

                }
            } else {                    
                try {
                    tipoEncomienda.setId(Integer.parseInt(id));
                    tipoEncomiendaDAO.modificar(tipoEncomienda);
                } catch (Exception ex) {

                }
            } 
                 //response.sendRedirect(request.getContextPath() + "/SERVTipoEncomienda?action=refresh");
                 response.sendRedirect(request.getContextPath() + "/SERVTipoEncomienda?action=refresh2&idEncomienda="+idEncomienda);  
            
        }

        if(request.getParameter("btnAceptarPaquete")!=null){

            String tipo = "paquete";
            tipoEncomienda.setTipo(tipo);            
            tipoEncomienda.setAltura(altura);      
            tipoEncomienda.setAnchura(anchura);      
            tipoEncomienda.setLargo(largo);                       

            if (id == null || id.isEmpty()) {
                try {                    
                    tipoEncomiendaDAO.insertar(tipoEncomienda);
                } catch (Exception ex) {

                }
            } else {                    
                try {
                    tipoEncomienda.setId(Integer.parseInt(id));
                    tipoEncomiendaDAO.modificar(tipoEncomienda);
                } catch (Exception ex) {

                }
            } 
                    response.sendRedirect(request.getContextPath() + "/SERVTipoEncomienda?action=refresh2&idEncomienda="+idEncomienda);   
        }        
        
        


        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
