
package controlador;

import dao.ClienteDAO;
import dao.EncomiendaDAO;
import dao.TipoEncomiendaDAO;
import entidad.Cliente;
import entidad.Encomienda;
import entidad.TipoEncomienda;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private EncomiendaDAO encomiendaDAO;
    private ClienteDAO clienteDAO;
    TipoEncomienda tipoEncomienda;
    Encomienda encomienda;

     public SERVTipoEncomienda() {
    	tipoEncomiendaDAO = new TipoEncomiendaDAO(){};
        tipoEncomienda = new TipoEncomienda(){};
        encomiendaDAO = new EncomiendaDAO(){};
        encomienda = new Encomienda(){};
        clienteDAO = new ClienteDAO(){};
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
                HttpSession sesion = request.getSession();
                
                List<Encomienda> encomienda_list = null;                 
                String Usuario = null;
                String vista = null;
                int nivel = 0;
                int idCliente = 0;   
                if(sesion.getAttribute("usuario")!= null){
                    Usuario = String.valueOf(sesion.getAttribute("usuario"));
                }
                int idTipoEncomienda = 0;
                if(request.getParameter("id")!= null){
                    idTipoEncomienda = Integer.parseInt(request.getParameter("id"));
                }            
                 
                try {              

                   TipoEncomienda tipoEncomienda2 = tipoEncomiendaDAO.BuscarPorId(idTipoEncomienda);
                   int idEncomienda = tipoEncomienda2.getIdEncomienda();
                   tipoEncomiendaDAO.eliminar(tipoEncomienda2); 
                   Encomienda encomienda2 = new  Encomienda();
                   encomienda2.setId(idEncomienda);
                   encomiendaDAO.eliminar(encomienda2);                   
                   Cliente cliente = clienteDAO.BuscarPorUsuario(Usuario);
                   idCliente = cliente.getId();                   
                   nivel = cliente.getNivel();
                   encomienda_list = encomiendaDAO.consultarEncomiendaPorIdCliente(idCliente);
                   
                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    for(int i = 0; i < encomienda_list.size(); i++){

                         if(encomienda_list.get(i).getFechaRegistroTime() != null ){

                            Date envio_date = encomienda_list.get(i).getFechaRegistroTime();     

                            String fecha_string = sdf.format(envio_date);                                                            

                            encomienda_list.get(i).setFechaRegistroTimeString(fecha_string);                      
                         }
                    }    
                   
                } catch (Exception ex) {
                    Logger.getLogger(SERVTipoEncomienda.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(nivel == 1){
                    vista = "ListarEncomienda.jsp";
                }
                if(nivel == 2){
                    vista = "ListarEncomienda1.jsp";
                }
                request.setAttribute("encomienda", encomienda_list);
                sesion.setAttribute("usuario",  Usuario);
                RequestDispatcher view = request.getRequestDispatcher(vista);
                view.forward(request, response);                       
            }
/*
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
  */
            else if (action.equalsIgnoreCase("edit")) {
                Encomienda encomienda = new Encomienda();
                TipoEncomienda tipoEncomienda = new TipoEncomienda();
                EncomiendaDAO encomiendaDAO = new EncomiendaDAO();
                String vista = null;
                String tipo = null;
                int idTipoEncomienda = 0;
                int idEncomienda = 0;
                double volumen = 0;
                double volumen_aprox = 0;
                try {
                    forward = edit;                   
                 //   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    idTipoEncomienda = Integer.parseInt(request.getParameter("id"));                    
                    tipoEncomienda = tipoEncomiendaDAO.BuscarPorId(idTipoEncomienda);  
                    tipo = tipoEncomienda.getTipo();
                    idEncomienda = tipoEncomienda.getIdEncomienda();
                    encomienda = encomiendaDAO.BuscarPorId(idEncomienda);
                    volumen = (tipoEncomienda.getAltura()*tipoEncomienda.getAnchura()*tipoEncomienda.getLargo()*tipoEncomienda.getCantidad())/1000000;
                    volumen_aprox = redondearDecimales(volumen, 2);
                    if(volumen_aprox < 0.01){
                        volumen_aprox = 0.01;
                    }
                    /*
                         if(encomienda.getEnvio() != null || encomienda.getLlegada()!= null){
                           
                            Date envio_date = encomienda.getEnvio();
                            Date llegada_date = encomienda.getLlegada();
                             
                            String envio_string = sdf.format(envio_date);                                                            
                            String llegada_string = sdf.format(llegada_date);
                                                        
                            encomienda.setEnvioS(envio_string);
                            encomienda.setLlegadaS(llegada_string);                       
                         }                    
                  */

                } catch (Exception ex) {
                }              
                
                if(tipo.equalsIgnoreCase("sobre")){
                    vista = "EditarEncomiendaSobre.jsp";
                }
                if(tipo.equalsIgnoreCase("paquete")){
                    vista = "EditarEncomiendaPaquete.jsp";
                }
                request.setAttribute("idTipoEncomienda", idTipoEncomienda);
                request.setAttribute("encomienda", encomienda);
                request.setAttribute("volumen",volumen_aprox);
                request.setAttribute("tipoEncomienda", tipoEncomienda);                                              
            RequestDispatcher view = request.getRequestDispatcher(vista);
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
        
        if(request.getParameter("btnEditarSobre")!=null){
            TipoEncomienda tipoEncomiendaSobre = new TipoEncomienda();
            HttpSession sesion = request.getSession();
            List<Encomienda> encomienda_list = null;
            Cliente cliente = new Cliente();
            int idTipoEncomienda = 0;
            int idEncomiendaEditar = 0;
            int cantidadSobreEditar = 0;
            double pesoSobreEditar = 0;
            double precioSobreEditar = 0;
            
            String usuario = null;
            int nivel = 0;
            String vista = null;            
            
            if(request.getParameter("txtTipoEncomienda")!=null){
                idTipoEncomienda = Integer.parseInt(request.getParameter("txtTipoEncomienda"));
            }
            //Viene de un input type number 
            if(request.getParameter("txtCantidadSobre")!=null){
                cantidadSobreEditar = Integer.parseInt(request.getParameter("txtCantidadSobre"));
            }
            if(request.getParameter("txtPesoSobre")!=null){
                    pesoSobreEditar = Double.parseDouble(request.getParameter("txtPesoSobre"));
            }
            //Viene de un input type text
            if(request.getParameter("txtPrecioSobre")!=null){
                    precioSobreEditar = Double.parseDouble(String.valueOf(request.getParameter("txtPrecioSobre")));
            }                                                 
           
            try {
                
            tipoEncomiendaSobre.setCantidad(cantidadSobreEditar);
            tipoEncomiendaSobre.setPeso(pesoSobreEditar);
            tipoEncomiendaSobre.setPrecio(precioSobreEditar);
            tipoEncomiendaSobre.setAltura(0);
            tipoEncomiendaSobre.setAnchura(0);
            tipoEncomiendaSobre.setLargo(0);                
            tipoEncomiendaSobre.setTipo("sobre");
                
                TipoEncomienda SobreEncontrado = tipoEncomiendaDAO.BuscarPorId(idTipoEncomienda);  
                idEncomiendaEditar = SobreEncontrado.getIdEncomienda();
                
                //encomiendaSobre = encomiendaDAO.BuscarPorId(idEncomiendaEditar);                
              //  tipoEncomiendas = tipoEncomiendaDAO.consultarTipoPorEncomienda(idEncomiendaEditar);
                                                
                tipoEncomiendaSobre.setIdEncomienda(idEncomiendaEditar);
                tipoEncomiendaSobre.setId(idTipoEncomienda);
                tipoEncomiendaDAO.modificar(tipoEncomiendaSobre);
                
                Encomienda encomienda = new Encomienda();
                
                encomienda = encomiendaDAO.BuscarPorId(idEncomiendaEditar);
                
               int idCliente =  encomienda.getIdCliente();
                
            encomienda_list = encomiendaDAO.consultarEncomiendaPorIdCliente(idCliente);
            
            usuario = String.valueOf(sesion.getAttribute("usuario"));            
            cliente = clienteDAO.BuscarPorUsuario(usuario);
            nivel = cliente.getNivel();            
            
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    for(int i = 0; i < encomienda_list.size(); i++){

                         if(encomienda_list.get(i).getFechaRegistroTime() != null ){

                            Date envio_date = encomienda_list.get(i).getFechaRegistroTime();     

                            String fecha_string = sdf.format(envio_date);                                                            

                            encomienda_list.get(i).setFechaRegistroTimeString(fecha_string);                      
                         }
                    }            
                
            } catch (Exception ex) {
                Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(nivel == 1){
                vista = "/ListarEncomienda.jsp";
            }
            if(nivel == 2){
                vista = "/ListarEncomienda1.jsp";
            }              
          
            sesion.setAttribute("encomienda", encomienda_list);
            response.sendRedirect(request.getContextPath() + vista);
            //response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=buscarEncomienda");
           
        }        
                
        
        if(request.getParameter("btnEditarPaquete")!=null){
            TipoEncomienda tipoEncomiendaPaquete = new TipoEncomienda();
            HttpSession sesion = request.getSession();
            Cliente cliente = new Cliente();
            List<Encomienda> encomienda_list = null;
            int idTipoEncomienda = 0;
            int idEncomiendaEditar = 0;
            int cantidadPaqueteEditar = 0;
            double pesoPaqueteEditar = 0;
            double pesoVolumenEditar = 0;
            double precioPaqueteEditar = 0;
            double altura = 0;
            double anchura = 0;
            double largo = 0;
            String usuario = null;
            int nivel = 0;
            String vista = null;

            if(request.getParameter("txtTipoEncomienda")!=null){
                idTipoEncomienda = Integer.parseInt(request.getParameter("txtTipoEncomienda"));
            }
            if(request.getParameter("txtAltura")!=null){
                    altura = Double.parseDouble(request.getParameter("txtAltura"));
            }
            if(request.getParameter("txtAnchura")!=null){
                    anchura = Double.parseDouble(request.getParameter("txtAnchura"));
            }              
            if(request.getParameter("txtLargo")!=null){
                    largo = Double.parseDouble(request.getParameter("txtLargo"));
            }
            if(request.getParameter("txtCantidadPaquete")!=null){
                    cantidadPaqueteEditar = Integer.parseInt(request.getParameter("txtCantidadPaquete"));
            }     
            if(request.getParameter("txtPesoVolumen")!=null){
                    pesoVolumenEditar = Double.parseDouble(request.getParameter("txtPesoVolumen"));
            }          
            if(request.getParameter("txtPrecioPaquete")!=null){
                    precioPaqueteEditar = Double.parseDouble(request.getParameter("txtPrecioPaquete"));
            }                    
            
            int idCliente = 0;
        try {
                
            tipoEncomiendaPaquete.setCantidad(cantidadPaqueteEditar);
        //    tipoEncomiendaPaquete.setPeso(peso);
            tipoEncomiendaPaquete.setPrecio(precioPaqueteEditar);
            tipoEncomiendaPaquete.setAltura(altura);
            tipoEncomiendaPaquete.setAnchura(anchura);
            tipoEncomiendaPaquete.setLargo(largo);                
            tipoEncomiendaPaquete.setTipo("paquete");
                
                TipoEncomienda PaqueteEncontrado = tipoEncomiendaDAO.BuscarPorId(idTipoEncomienda);  
                idEncomiendaEditar = PaqueteEncontrado.getIdEncomienda();
                
                //encomiendaSobre = encomiendaDAO.BuscarPorId(idEncomiendaEditar);                
              //  tipoEncomiendas = tipoEncomiendaDAO.consultarTipoPorEncomienda(idEncomiendaEditar);
                                                
                tipoEncomiendaPaquete.setIdEncomienda(idEncomiendaEditar);
                tipoEncomiendaPaquete.setId(idTipoEncomienda);
                tipoEncomiendaDAO.modificar(tipoEncomiendaPaquete);
                
                Encomienda encomienda2= encomiendaDAO.BuscarPorId(idEncomiendaEditar);
                
              //  encomienda2 = encomiendaDAO.BuscarPorId(idEncomiendaEditar);
                
            idCliente =  encomienda2.getIdCliente();
            
            usuario = String.valueOf(sesion.getAttribute("usuario"));            
            cliente = clienteDAO.BuscarPorUsuario(usuario);
            nivel = cliente.getNivel();
                
            encomienda_list = encomiendaDAO.consultarEncomiendaPorIdCliente(idCliente);
                                
                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    for(int i = 0; i < encomienda_list.size(); i++){

                         if(encomienda_list.get(i).getFechaRegistroTime() != null ){

                            Date envio_date = encomienda_list.get(i).getFechaRegistroTime();     

                            String fecha_string = sdf.format(envio_date);                                                            

                            encomienda_list.get(i).setFechaRegistroTimeString(fecha_string);                      
                         }
                    }
            } catch (Exception ex) {
                Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            if(nivel == 1){
                vista = "/ListarEncomienda.jsp";
            }
            if(nivel == 2){
                vista = "/ListarEncomienda1.jsp";
            }        
           // sesion.setAttribute("idUsuario", idCliente);
           sesion.setAttribute("encomienda", encomienda_list);
            response.sendRedirect(request.getContextPath() + vista);
         //   response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=buscarEncomienda");            
        }
      /*    
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
        
        */


                      
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

        public  double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }

}
