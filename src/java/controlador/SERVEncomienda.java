
package controlador;

import dao.ClienteDAO;
import dao.EncomiendaDAO;
import dao.Envio;
import dao.TipoEncomiendaDAO;
import dao.VehiculoDAO;
import entidad.Cliente;
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
import entidad.TipoEncomienda;
import entidad.Vehiculo;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpSession;

public class SERVEncomienda extends HttpServlet {

    private static String insert= "/RegistrarEncomienda.jsp";
    private static String edit = "/EditarEncomienda.jsp";
    private static String list_encomienda = "/ListarEncomienda.jsp";
    private static String exito = "/exito.jsp";
    private EncomiendaDAO encomiendadao;
    private TipoEncomiendaDAO tipoEncomiendaDAO;
    private VehiculoDAO vehiculoDAO;
    private ClienteDAO clientedao;       
    Encomienda enc = new Encomienda();

            
     public SERVEncomienda() {
        tipoEncomiendaDAO = new TipoEncomiendaDAO(){};
    	encomiendadao = new EncomiendaDAO(){};
        vehiculoDAO = new VehiculoDAO(){};
        clientedao = new ClienteDAO(){};
    }         
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          String forward = "";   
            String action = request.getParameter("action");
                
            //ELIMINAR CLIE
            if (action.equalsIgnoreCase("delete")) {  
                 HttpSession sesion = request.getSession();
                 
                Encomienda encomienda = new Encomienda();
                Encomienda encomienda2 = new Encomienda();
                int idEncomienda = 0;
                int idCliente = 0;
                List<Encomienda> encomienda_list = null;
                if(request.getParameter("idEncomienda")!=null){
                    idEncomienda  = Integer.parseInt(request.getParameter("idEncomienda"));
                }                                               
               
                encomienda.setId(idEncomienda);
                                                          
              try {                
                    encomienda2 = encomiendadao.BuscarPorId(idEncomienda);
                    idCliente = encomienda2.getIdCliente();
                    encomiendadao.eliminar(encomienda);  
                                               
                    encomienda_list= encomiendadao.consultarEncomiendaPorIdCliente(idCliente);
                
              } catch (Exception ex) {
                  Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
              }    
                          
                
                request.setAttribute("encomienda", encomienda_list);
                sesion.setAttribute("idCliente",  idCliente);
                RequestDispatcher view = request.getRequestDispatcher("ListarEncomienda.jsp");
                view.forward(request, response);

            }
            //EDITAR CLIENTE
            else if (action.equalsIgnoreCase("edit")) {
                try {
                    forward = edit;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    int id = Integer.parseInt(request.getParameter("id"));
                    Encomienda encomienda = encomiendadao.BuscarPorId(id);             
                    
                         if(encomienda.getEnvio() != null || encomienda.getLlegada()!= null){
                           
                            Date envio_date = encomienda.getEnvio();
                            Date llegada_date = encomienda.getLlegada();
                             
                            String envio_string = sdf.format(envio_date);                                                            
                            String llegada_string = sdf.format(llegada_date);
                                                        
                            encomienda.setEnvioS(envio_string);
                            encomienda.setLlegadaS(llegada_string);                       
                         }                    
                  
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
                if(request.getParameter("idCliente")!=null){
                     idCliente =Integer.parseInt(request.getParameter("idCliente"));
                }
                try {
                    forward = list_encomienda;                              
                    
                    List<Encomienda> encomienda = encomiendadao.consultarEncomiendaPorIdCliente(idCliente);
     
     
                    Encomienda enco[] = new Encomienda[encomienda.size()];
                    enco = encomienda.toArray(enco);            
                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    
                    for(int i = 0; i < enco.length; i++){

                         if(enco[i].getFechaRegistroTime() != null ){
                           
                            Date envio_date = enco[i].getFechaRegistroTime();     
                             
                            String fecha_string = sdf.format(envio_date);                                                            
                                                        
                            enco[i].setFechaRegistroTimeString(fecha_string);                      
                         }
                    }
                    List<Encomienda> con_filtro = new ArrayList(Arrays.asList(enco));                    
                    
             /*       
                    Encomienda enco[] = new Encomienda[encomienda.size()];
                    enco = encomienda.toArray(enco);            
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
              
                    for(int i = 0; i < enco.length; i++){

                         if(enco[i].getEnvio() != null || enco[i].getLlegada()!= null){
                           
                            Date envio_date = enco[i].getEnvio();
                            Date llegada_date = enco[i].getLlegada();
                             
                            String envio_string = sdf.format(envio_date);                                                            
                            String llegada_string = sdf.format(llegada_date);
                                                        
                            enco[i].setEnvioS(envio_string);
                            enco[i].setLlegadaS(llegada_string);                       
                         }
                    }
                    List<Encomienda> con_filtro = new ArrayList(Arrays.asList(enco));
                */    
                    request.setAttribute("encomienda", con_filtro); 
                 
                 //   sesion.setAttribute("nivel", request);
                } catch (Exception e) {
                }
                               
            RequestDispatcher view = request.getRequestDispatcher(forward);
           view.forward(request, response);
            }                       
            
            else if(action.equalsIgnoreCase("refreshPrueba")){
                
                HttpSession sesion = request.getSession();
                
                Cliente cliente = new Cliente();
                String  usuario_de_login = null;
                String usuario = null ;
                int idUsuario = 0;
            
                if(sesion.getAttribute("idUsuario")!=null){
                    idUsuario = Integer.parseInt(String.valueOf(sesion.getAttribute("idUsuario")));
                }
                if(sesion.getAttribute("usuario")!=null){
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                }                
                try {
                    cliente = clientedao.BuscarPorId(idUsuario);
                } catch (Exception ex) {
                    Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
                }   
                usuario = cliente.getUsuario();
                
                if(idUsuario < 0 || usuario_de_login == null){
                    response.sendRedirect("index.jsp");
                }
                else if (idUsuario != 0){
                //El nabvar esta incluido en la pag RegistrarEncomienda1
                //si se envia session el nabvar recibirá el idCliente, todo lo contrario con enviar request
                sesion.setAttribute("idUsuario", idUsuario);
                sesion.setAttribute("usuarioPrueba", usuario);
                sesion.setAttribute("usuario_de_login", usuario_de_login);
                request.setAttribute("usuarioPrueba",  usuario);                 
                request.setAttribute("idUsuario",  idUsuario);
                RequestDispatcher view = request.getRequestDispatcher("RegistrarEncomienda1.jsp");
                view.forward(request, response);  
                }

                

            }
            
        else if(action.equalsIgnoreCase("buscarEncomienda")){                        
            HttpSession sesion = request.getSession();    

                forward = list_encomienda; 
                int idEncomienda_buscar = 0;
                int idCliente_buscar = 0;
                Encomienda enco[] = null;
                /*
                if(request.getParameter("txtCodigo")!=null){
                    idEncomienda_buscar = Integer.parseInt(request.getParameter("txtCodigo"));  
                } 
                */
                if(sesion.getAttribute("idUsuario")!=null){
                    idCliente_buscar = Integer.parseInt(String.valueOf(request.getParameter("idUsuario")));  
                }                 
                
                try {
                   // idCliente_buscar = encomiendadao.consultarClienteIdPorEncomiendaId(idEncomienda_buscar);
                    List<Encomienda> encomiendaList = encomiendadao.consultarEncomiendaPorIdCliente(idCliente_buscar);
        
                enco = new Encomienda[encomiendaList.size()];
                enco = encomiendaList.toArray(enco);            
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    for(int i = 0; i < enco.length; i++){

                         if(enco[i].getFechaRegistroTime() != null ){

                            Date envio_date = enco[i].getFechaRegistroTime();     

                            String fecha_string = sdf.format(envio_date);                                                            

                            enco[i].setFechaRegistroTimeString(fecha_string);                      
                         }
                    }
                }
                 catch (Exception ex) {
               
                Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
                 }
            List<Encomienda> con_filtro = new ArrayList(Arrays.asList(enco));    
            request.setAttribute("encomienda", con_filtro); 
          //  request.setAttribute("idEncomienda", idEncomienda_buscar);
            request.setAttribute("idCliente", idCliente_buscar);           
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);         
        }     
            
        if(action.equalsIgnoreCase("cerrar")){
            HttpSession session = request.getSession();
           /* session.setAttribute("idCliente", null);        
            response.sendRedirect("index.jsp");
            if(request.getParameter("cerrar")!=null){
            session.invalidate();
            }
            */
            session.invalidate();
            response.sendRedirect("index.jsp");
        }            
             
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
         HttpSession sesion = request.getSession();
        RequestDispatcher rd = null;
        
        
        String tipo = null;
        String origen = null;
        String destino = null;
        int idCliente = 0;
        int idEncomienda = 0;
        int cantidadSobre = 0;
        double pesoSobre = 0; 
        double precioSobre = 0; 
        double altura = 0;
        double anchura = 0;
        double largo = 0;
        int cantidadPaquete = 0;
        double pesoPaquete = 0; 
        double precioPaquete = 0;         
        
        double pesoVolumen = 0;
        
        int idVehiculo = 0;
        String matricula = null;
        String ticket = null;
       
         
        if(request.getParameter("txtOrigen")!=null) {
            origen = request.getParameter("txtOrigen");
        }
        if(request.getParameter("txtDestino")!=null) {
            destino = request.getParameter("txtDestino");
        }
        if(request.getParameter("txtIdCliente2")!=null) {
            idCliente = Integer.parseInt(request.getParameter("txtIdCliente2"));
        } 
                
      //  String id =request.getParameter("txtId");                             

        int cantidad = 0;
        double peso = 0;
        double precio = 0;
        
        if(request.getParameter("pago1")!=null) {
            tipo = request.getParameter("pago1");
            if(tipo.equals("sobre")){
                tipo = "sobre";   
                
        //Viene de un input type number 
        if(request.getParameter("txtCantidadSobre")!=null){
                cantidadSobre = Integer.parseInt(request.getParameter("txtCantidadSobre"));
        }
        if(request.getParameter("txtPesoSobre")!=null){
                pesoSobre = Double.parseDouble(request.getParameter("txtPesoSobre"));
        }
        //Viene de un input type text
        if(request.getParameter("txtPrecioSobre")!=null){
                precioSobre = Double.parseDouble(String.valueOf(request.getParameter("txtPrecioSobre")));
        }                      
                altura = 0;
                anchura = 0;
                largo = 0;     
                
                cantidad = cantidadSobre;
                peso = pesoSobre;
                precio = precioSobre;
                
            }
            else if (tipo.equals("paquete")){
                
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
                cantidadPaquete = Integer.parseInt(request.getParameter("txtCantidadPaquete"));
        }     
        if(request.getParameter("txtPesoVolumen")!=null){
                pesoVolumen = Double.parseDouble(request.getParameter("txtPesoVolumen"));
        }          
        if(request.getParameter("txtPesoPaquete")!=null){
                pesoPaquete = Double.parseDouble(request.getParameter("txtPesoPaquete"));
        }
        if(request.getParameter("txtPrecioPaquete")!=null){
                precioPaquete = Double.parseDouble(request.getParameter("txtPrecioPaquete"));
        }              
            cantidadSobre = 0;
            pesoSobre = 0; 
            precioSobre = 0; 

            tipo = "paquete";                           
            cantidad = cantidadPaquete;
            if(pesoVolumen > pesoPaquete){
                peso = pesoVolumen;
            }
            else{
                peso = pesoPaquete;
            }               
            precio = precioPaquete;
            }
        }        
                                        
            
        if(request.getParameter("btnRegistrarPrueba")!=null){
       
        try {
           
            Encomienda encomienda = new Encomienda();
            encomienda.setOrigen(origen);            
            encomienda.setDestino(destino);
            encomienda.setIdCliente(idCliente);

            encomiendadao.insertar(encomienda);

            encomienda = encomiendadao.getUltimoEncomiendaByIdCliente(idCliente);

            idEncomienda = encomienda.getId();

            TipoEncomienda tipoEncomienda = new TipoEncomienda();
            tipoEncomienda.setTipo(tipo);
            tipoEncomienda.setAltura(altura);
            tipoEncomienda.setAnchura(anchura);
            tipoEncomienda.setLargo(largo);
            tipoEncomienda.setCantidad(cantidad);
            tipoEncomienda.setPeso(peso);
            tipoEncomienda.setPrecio(precio);
            tipoEncomienda.setIdEncomienda(idEncomienda);
       
            tipoEncomiendaDAO.insertar(tipoEncomienda);
            
            Vehiculo vehiculo1 = new Vehiculo();     
            double volumen = altura*anchura*largo/1000000;
            vehiculo1 = EscogerVehiculo(volumen, peso);
            
            idVehiculo = vehiculo1.getId();
            matricula = vehiculo1.getPlaca();
                
            ticket = ""+idCliente+"."+idEncomienda+"."+idVehiculo;
            
            Envio envio = new Envio();
            Cliente  cliente1 = new Cliente();
            
            cliente1 = clientedao.BuscarPorId(idCliente);
            String email = cliente1.getEmail();
            envio.EnviarCodigo(ticket , idCliente , idEncomienda, idVehiculo, matricula, email);

            } catch (Exception ex) {
               
                Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
            }
        
          //  sesion.setAttribute("idCliente", idCliente);
          //  sesion.setAttribute("idEncomienda", idEncomienda);
            //response.sendRedirect(request.getContextPath() + "/SERVPedido?action=refreshPrueba"); 
        
          response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=cerrar");
        }
    /*   
        if(request.getParameter("btnBuscar")!=null){      
            
            String forward = list_encomienda; 
            int idEncomienda_buscar = 0;
            int idCliente_buscar = 0;
             Encomienda enco[] = null;
            if(request.getParameter("txtCodigo")!=null){
                idEncomienda_buscar = Integer.parseInt(request.getParameter("txtCodigo"));  
            }    
            try {
                idCliente_buscar = encomiendadao.consultarClienteIdPorEncomiendaId(idEncomienda_buscar);
                List<Encomienda> encomiendaList = encomiendadao.consultarEncomiendaPorIdCliente(idCliente_buscar);
      
            enco = new Encomienda[encomiendaList.size()];
            enco = encomiendaList.toArray(enco);            
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    
            for(int i = 0; i < enco.length; i++){

                 if(enco[i].getFechaRegistroTime() != null ){

                    Date envio_date = enco[i].getFechaRegistroTime();     

                    String fecha_string = sdf.format(envio_date);                                                            

                    enco[i].setFechaRegistroTimeString(fecha_string);                      
                 }
            }
            
             


                           
            } catch (Exception ex) {
                Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
            }            
            List<Encomienda> con_filtro = new ArrayList(Arrays.asList(enco));    
            request.setAttribute("encomienda", con_filtro); 
            request.setAttribute("idEncomienda", idEncomienda_buscar);
            request.setAttribute("idCliente", idCliente_buscar);           
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);                        
            
        }
       */
   
        //String origen = request.getParameter("txtOrigen");
       // String destino = request.getParameter("txtDestino");                        
       
        /*
        try {
            
            List<java.sql.Date> cambio = Fechas(envio, llegada);
                        
            Encomienda encomienda = new Encomienda();
            encomienda.setOrigen(origen);            
            encomienda.setDestino(destino);      
            encomienda.setEnvio(cambio.get(0));      
            encomienda.setLlegada(cambio.get(1));            
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

*/
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
        
    public List<java.sql.Date> Fechas(String fech_ini, String fech_fin) throws ParseException {
                     
        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");

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
    
    public Vehiculo EscogerVehiculo(double volumen, double capacidad){
           Vehiculo vehiculo = new Vehiculo();     
           Vehiculo vehiculo2 = new Vehiculo();
           VehiculoDAO vehiculoDAO2 = new VehiculoDAO();

           double actual_volumen;
           double actual_capacidad;
           double suma_volumen = 0;
           double suma_capacidad = 0;
           int idVehiculo = 0;
           List<Vehiculo>  vehiculo_list;
           try {
              vehiculo_list= vehiculoDAO2.ElegirVehiculo(volumen, capacidad);

               for(int i = 0; i < vehiculo_list.size(); i++){

                   actual_volumen = vehiculo_list.get(i).getActualvolumen();
                   actual_capacidad = vehiculo_list.get(i).getActualcapacidad();

                   suma_volumen = actual_volumen + volumen;       
                   suma_capacidad = actual_capacidad + capacidad;

                   if(suma_volumen < vehiculo_list.get(i).getVolumen()
                           && suma_capacidad < vehiculo_list.get(i).getCapacidad()){

                       idVehiculo =  vehiculo_list.get(i).getId();
                       vehiculo.setId(idVehiculo);
                       vehiculo.setActualvolumen(suma_volumen);
                       vehiculo.setActualcapacidad(suma_capacidad);
                       vehiculoDAO2.modificar(vehiculo);
                       break;
                   }
                   suma_volumen = 0;
                   actual_capacidad = 0;
                }
                
               Vehiculo vehiculo_buscado = vehiculoDAO.BuscarPorId(idVehiculo);
               
            
               vehiculo2.setId(vehiculo_buscado.getId());
               vehiculo2.setPlaca(vehiculo_buscado.getPlaca());
               vehiculo2.setVolumen(vehiculo_buscado.getVolumen());
               vehiculo2.setCapacidad(vehiculo_buscado.getCapacidad());
               vehiculo2.setActualcapacidad(vehiculo_buscado.getActualcapacidad());
               vehiculo2.setActualvolumen(vehiculo_buscado.getActualvolumen());
         
           } catch (Exception e) {
           System.out.println ("El error es: " + e.getMessage());
           e.printStackTrace();
           }
           
             return vehiculo2;    
    }
           
}
