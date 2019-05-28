
package controlador;

import dao.ClienteDAO;
import dao.ConductorDAO;
import dao.DisponibilidadDAO;
import dao.EncomiendaDAO;
import dao.Envio;
import dao.LugarDAO;
import dao.TipoEncomiendaDAO;
import dao.VehiculoDAO;
import entidad.Cliente;
import entidad.Conductor;
import entidad.Disponibilidad;
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
import entidad.Lugar;
import entidad.TipoEncomienda;
import entidad.Vehiculo;
import java.text.DateFormat;
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
    private TipoEncomiendaDAO tipoEncomiendaDAO;
    private VehiculoDAO vehiculoDAO;
    private ClienteDAO clientedao;
    private LugarDAO lugarDAO;
    private ConductorDAO conductorDAO;
    Encomienda enc = new Encomienda();

            
     public SERVEncomienda() {
        tipoEncomiendaDAO = new TipoEncomiendaDAO(){};
    	encomiendadao = new EncomiendaDAO(){};
        vehiculoDAO = new VehiculoDAO(){};
        clientedao = new ClienteDAO(){};
        lugarDAO = new LugarDAO(){};
        conductorDAO = new ConductorDAO(){};
    }         
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          String forward = "";   
            String action = request.getParameter("action");
                
            //ELIMINAR ENCOMIENDA COMO ADMINISTRADOR
            if (action.equalsIgnoreCase("delete")) {  
                 HttpSession sesion = request.getSession();
                 
                Encomienda encomienda = new Encomienda();
                DisponibilidadDAO disponibilidadDAO = new DisponibilidadDAO();
                 Disponibilidad disponibilidad = new Disponibilidad();
         //       Encomienda encomienda2 = new Encomienda();
                int idEncomienda = 0;
                int idCliente = 0;
                List<Encomienda> encomienda_list = null;
                if(request.getParameter("idEncomienda")!=null){
                    idEncomienda  = Integer.parseInt(request.getParameter("idEncomienda"));
                }                                               
               
                encomienda.setId(idEncomienda);
                                                          
              try {                
                    Encomienda encomienda_encontrada  = encomiendadao.BuscarPorId(idEncomienda);
                    idCliente = encomienda_encontrada.getIdCliente();
                    
                    TipoEncomienda tipoEncomienda = tipoEncomiendaDAO.consultarPorEncomienda(idEncomienda);                    
                    disponibilidad.setIdTipoEncomienda(tipoEncomienda.getId());                    
                    disponibilidadDAO.eliminarPorTipoEncomienda(disponibilidad);
                    
                    encomiendadao.eliminar(encomienda);       
                    tipoEncomiendaDAO.eliminar(tipoEncomienda);
                    
                //    TipoEncomienda tipoEncomienda = tipoEncomiendaDAO.consultarPorEncomienda(idEncomienda);
                 //   disponibilidadDAO.eliminarPorTipoEncomienda(tipoEncomienda.getId());                          
                    encomienda_list= encomiendadao.consultarEncomiendaPorIdCliente(idCliente);
                              
                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    
                    for(int i = 0; i < encomienda_list.size(); i++){

                         if(encomienda_list.get(i).getFechaRegistroTime() != null ){
                           
                            Date envio_date = encomienda_list.get(i).getFechaRegistroTime();     
                             
                            String fecha_string = sdf.format(envio_date);                                                            
                                                        
                            encomienda_list.get(i).setFechaRegistroTimeString(fecha_string);  
                            
                            
                            Lugar lugar_origen = lugarDAO.BuscarPorId( encomienda_list.get(i).getOrigen());
                            Lugar lugar_destino = lugarDAO.BuscarPorId( encomienda_list.get(i).getDestino());
                              encomienda_list.get(i).setOrigenS( lugar_origen.getNombre());
                              encomienda_list.get(i).setDestinoS(lugar_destino.getNombre());                               
                         }
                    }      
                    
                
              } catch (Exception ex) {
                  Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
              }                                              
                request.setAttribute("encomienda", encomienda_list);
                sesion.setAttribute("idCliente",  idCliente);
                RequestDispatcher view = request.getRequestDispatcher("ListarEncomienda.jsp");
                view.forward(request, response);

            }
            
                           
            //ELIMINAR ENCOMIENDA COMO CLIENTE
            if (action.equalsIgnoreCase("delete2")) {  
                 HttpSession sesion = request.getSession();
                  Disponibilidad disponibilidad = new Disponibilidad();
                Encomienda encomienda = new Encomienda();
          //      Encomienda encomienda2 = new Encomienda();
                DisponibilidadDAO disponibilidadDAO = new DisponibilidadDAO();
                int idEncomienda = 0;
                int idCliente = 0;
                List<Encomienda> encomienda_list = null;
                if(request.getParameter("idEncomienda")!=null){
                    idEncomienda  = Integer.parseInt(request.getParameter("idEncomienda"));
                }                                               
               
                encomienda.setId(idEncomienda);
                                                                          
              try {                
                    Encomienda encomienda_encontrada = encomiendadao.BuscarPorId(idEncomienda);
                    idCliente = encomienda_encontrada.getIdCliente();
                    
                    tipoEncomiendaDAO.consultarPorEncomienda(idEncomienda);
                    TipoEncomienda tipoEncomienda = tipoEncomiendaDAO.consultarPorEncomienda(idEncomienda);   
                   
                    disponibilidad.setIdTipoEncomienda(tipoEncomienda.getId());
                    disponibilidadDAO.eliminarPorTipoEncomienda(disponibilidad);
                    encomiendadao.eliminar(encomienda);                                                                                
                    tipoEncomiendaDAO.eliminar(tipoEncomienda);
           //         disponibilidadDAO.eliminarPorTipoEncomienda(tipoEncomienda.getId());
                    encomienda_list= encomiendadao.consultarEncomiendaPorIdCliente(idCliente);
                              
                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    
                    for(int i = 0; i < encomienda_list.size(); i++){

                         if(encomienda_list.get(i).getFechaRegistroTime() != null ){
                           
                            Date envio_date = encomienda_list.get(i).getFechaRegistroTime();     
                             
                            String fecha_string = sdf.format(envio_date);                                                            
                                                        
                            encomienda_list.get(i).setFechaRegistroTimeString(fecha_string);
                            
                            Lugar lugar_origen = lugarDAO.BuscarPorId( encomienda_list.get(i).getOrigen());
                            Lugar lugar_destino = lugarDAO.BuscarPorId( encomienda_list.get(i).getDestino());
                              encomienda_list.get(i).setOrigenS( lugar_origen.getNombre());
                              encomienda_list.get(i).setDestinoS(lugar_destino.getNombre());                              
                         }
                    }                                       
                
              } catch (Exception ex) {
                  Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
              }    
                          
                
                request.setAttribute("encomienda", encomienda_list);
                sesion.setAttribute("idCliente",  idCliente);
                RequestDispatcher view = request.getRequestDispatcher("ListarEncomienda1.jsp");
                view.forward(request, response);

            }            
            //EDITAR ENCOMIENDA
            else if (action.equalsIgnoreCase("edit")) {
                HttpSession sesion = request.getSession();
                 Encomienda encomienda = null;
                 Cliente cliente = new Cliente();
                TipoEncomienda tipoEncomienda = null;
                String vista = null;
                String tipo = null;
                int idEncomienda = 0;
                String Usuario = null;
                try {
                    forward = edit;                   
                 //   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    idEncomienda = Integer.parseInt(request.getParameter("id"));
                    Usuario = String.valueOf(sesion.getAttribute("usuario"));
                    encomienda = encomiendadao.BuscarPorId(idEncomienda); 
                    tipoEncomienda = tipoEncomiendaDAO.buscarTipoEncomiendaPorEncomiendaID(idEncomienda);
                    tipo = tipoEncomienda.getTipo();
                    tipoEncomienda.getId();
                    
                    cliente = clientedao.BuscarPorUsuario(Usuario);
                    int nivel =cliente.getNivel();
                    
                    Lugar lugar_origen = lugarDAO.BuscarPorId(encomienda.getOrigen());
                    Lugar lugar_destino = lugarDAO.BuscarPorId(encomienda.getDestino());
                     encomienda.setOrigenS( lugar_origen.getNombre());
                     encomienda.setDestinoS(lugar_destino.getNombre());                  
                    
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
                //Para la editar encomienda como administrador
                request.setAttribute("idTipoEncomienda", tipoEncomienda.getId());
                sesion.setAttribute("usuario", Usuario);
                //
                request.setAttribute("idEncomienda", idEncomienda);
                request.setAttribute("encomienda", encomienda);
                request.setAttribute("tipoEncomienda", tipoEncomienda);                                              
            RequestDispatcher view = request.getRequestDispatcher(vista);
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
                    
                    List<Encomienda> encomienda_list = encomiendadao.consultarEncomiendaPorIdCliente(idCliente);
                  
                    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    
                    for(int i = 0; i < encomienda_list.size(); i++){

                         if(encomienda_list.get(i).getFechaRegistroTime() != null ){
                           
                            Date envio_date = encomienda_list.get(i).getFechaRegistroTime();     
                             
                            String fecha_string = sdf.format(envio_date);                                                            
                                                        
                            encomienda_list.get(i).setFechaRegistroTimeString(fecha_string);
                            
                            Lugar lugar_origen = lugarDAO.BuscarPorId( encomienda_list.get(i).getOrigen());
                            Lugar lugar_destino = lugarDAO.BuscarPorId( encomienda_list.get(i).getDestino());
                              encomienda_list.get(i).setOrigenS( lugar_origen.getNombre());
                              encomienda_list.get(i).setDestinoS(lugar_destino.getNombre());                               
                         }
                    }                
                    
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
                    request.setAttribute("encomienda", encomienda_list); 
                 
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
                String usuario ;
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
                String vista = null;
                int idEncomienda_buscar = 0;
                int idCliente_buscar = 0;
                 int nivel = 0;
               List<Encomienda> encomiendaList = null;
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
                   encomiendaList = encomiendadao.consultarEncomiendaPorIdCliente(idCliente_buscar);
                    Cliente cliente = clientedao.BuscarPorId(idCliente_buscar);
                   nivel = cliente.getNivel();
                            
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    for(int i = 0; i < encomiendaList.size(); i++){

                        if(encomiendaList.get(i).getFechaRegistroTime() != null ){

                           Date envio_date = encomiendaList.get(i).getFechaRegistroTime();     

                           String fecha_string = sdf.format(envio_date);                                                            

                            encomiendaList.get(i).setFechaRegistroTimeString(fecha_string);                      
                        }
                        
                       
                        Lugar lugar_origen = lugarDAO.BuscarPorId(encomiendaList.get(i).getOrigen());
                        Lugar lugar_destino = lugarDAO.BuscarPorId(encomiendaList.get(i).getDestino());
                         encomiendaList.get(i).setOrigenS( lugar_origen.getNombre());
                         encomiendaList.get(i).setDestinoS(lugar_destino.getNombre());
                    }
                }
                catch (Exception ex) {               
                Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(nivel == 1){
                    vista = "ListarEncomienda.jsp";
                }
                if(nivel == 2){
                    vista = "ListarEncomienda1.jsp";
                }               
            request.setAttribute("encomienda", encomiendaList); 
          //  request.setAttribute("idEncomienda", idEncomienda_buscar);
            request.setAttribute("idCliente", idCliente_buscar);           
            RequestDispatcher view = request.getRequestDispatcher(vista);
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
        String tiempoConvertido = "";
        String recorrido = "";
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
        if(request.getParameter("txtTiempoConvertido")!=null) {
            tiempoConvertido = request.getParameter("txtTiempoConvertido");
        }
        if(request.getParameter("txtRecorrido")!=null) {
            recorrido = request.getParameter("txtRecorrido");
        }
        if(request.getParameter("txtIdCliente2")!=null) {
            idCliente = Integer.parseInt(request.getParameter("txtIdCliente2"));
        } 
                
      //  String id =request.getParameter("txtId");                             

        int cantidad = 0;
        double peso = 0;
        double precio = 0;
        int delicado = 0;
        
        if(request.getParameter("txtDelicado")!=null) {
            delicado = Integer.parseInt(request.getParameter("txtDelicado"));
            if(delicado == 1){
                delicado = 1;
            }else{
                delicado = 0;
            }
        }
        
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
            
         
            Lugar lugar_origen = lugarDAO.ConsultarPorNombre(origen);
            Lugar lugar_destino = lugarDAO.ConsultarPorNombre(destino);
            
            int idOrigen = lugar_origen.getId();
            int idDestino = lugar_destino.getId();
           
            Encomienda encomienda = new Encomienda();
            encomienda.setOrigen(idOrigen);            
            encomienda.setDestino(idDestino);
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
            tipoEncomienda.setDelicado(delicado);
            tipoEncomienda.setIdEncomienda(idEncomienda);
       
            tipoEncomiendaDAO.insertar(tipoEncomienda);
            
            TipoEncomienda te = tipoEncomiendaDAO.buscarTipoEncomiendaPorEncomiendaID(idEncomienda);
            
            int idTipoEncomienda = te.getId();           
             double volumen = altura*anchura*largo*cantidad/1000000;
          //  double volumen = 25*31*75*cantidad/1000000;
            
            double volumen_aprox = redondearDecimales(volumen, 2);
            if(volumen_aprox < 0.01){
                volumen_aprox = 0.01;
            }            
            
     /*   
            List<Conductor> conductorList = conductorDAO.consultar();
            int idConductor = 0;
            for (int i = 0; i < conductorList.size(); i++) {
                if(conductorList.get(i).getDisp() > 0){
                   idConductor =  conductorList.get(i).getId();
                   Conductor conductor_eliminado = conductorDAO.BuscarPorId(idConductor);
                   conductorDAO.eliminarDisponiblidad(conductor_eliminado);
                    break;
                }
            }      
              
            int estado = determinarAyundate(tiempoConvertido);
            int idAyudante = 0;
            if(estado == 1){
                  List<Conductor> ayudanteList  = conductorDAO.consultar();
                for (int i = 0; i < ayudanteList.size(); i++) {
                    if(ayudanteList.get(i).getDisp() > 0){
                       idAyudante =  ayudanteList.get(i).getId();
                        Conductor ayudante_eliminado = conductorDAO.BuscarPorId(idAyudante);
                        conductorDAO.eliminarDisponiblidad(ayudante_eliminado);                       
                        break;
                    }
                } 
            }            
  */       
            //Vehiculo vehiculo_seleccionado =  EscogerVehiculo(volumen_aprox, peso, idTipoEncomienda, idConductor, idAyudante);
        //    int idConductor = 1;
           // int idAyudante = 0;
            Vehiculo vehiculo_seleccionado =  EscogerVehiculo(volumen_aprox, peso,idEncomienda, idTipoEncomienda, tiempoConvertido, idOrigen, idDestino);
            
            idVehiculo = vehiculo_seleccionado.getId();
            matricula = vehiculo_seleccionado.getPlaca();
            
             Vehiculo vehiculo_conductor_ayudante = vehiculoDAO.BuscarPorId(idVehiculo);
            
            Envio envio = new Envio();
            Cliente  cliente_encontrado = clientedao.BuscarPorId(idCliente);    
            
            if(cliente_encontrado.getPromocion()>0){
                cliente_encontrado.setPromocion(0);
            }
            clientedao.modificar(cliente_encontrado);
            clientedao.actualizarPromo(cliente_encontrado);
            String email = cliente_encontrado.getEmail();                        
            envio.EnviarCodigo(idCliente , idEncomienda, vehiculo_conductor_ayudante.getIdConductor(), vehiculo_conductor_ayudante.getIdAyudante(), idVehiculo, matricula, email, precio);

            } catch (Exception ex) {
               
                Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, ex);
            }
        
          //  sesion.setAttribute("idCliente", idCliente);
          //  sesion.setAttribute("idEncomienda", idEncomienda);
            //response.sendRedirect(request.getContextPath() + "/SERVPedido?action=refreshPrueba"); 
        
          response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=cerrar");
        }
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
    
    
      public  double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
          
    public Vehiculo EscogerVehiculo(double volumen, double capacidad, int idEncomienda, int idTipoEncomienda, String tiempoConvertido, int idOrigen, int  idDestino){
        
        Vehiculo vehiculo = new Vehiculo();             
        VehiculoDAO vehiculoDAO2 = new VehiculoDAO();
        List<Vehiculo>  vehiculo_list;
        DisponibilidadDAO disponibilidadDAO = new DisponibilidadDAO();        
        Disponibilidad disponibilidad = new Disponibilidad();
        EncomiendaDAO encomiendaDAO = new EncomiendaDAO();
        double volumen_max = 0;
        double capacidad_max = 0;
        double suma_volumen = 0;
        double suma_capacidad = 0;
        double volumen_encomienda;
        double capacidad_encomienda;    
        int situacion = 1;
        int idVehiculo = 0;        
        int idConductor = 0;
        int idAyudante = 0;
        int idMenor;
        
           try {                              
               
     vehiculo_list= vehiculoDAO.ElegirVehiculo(volumen, capacidad);
/*     
     //List<Vehiculo> vehiculo_disponibles = new ArrayList<>();
     List<Disponibilidad> todo = disponibilidadDAO.consultar();
     
     int cantidad ;
        if(todo.size() > vehiculo_list.size()){
            cantidad = todo.size();
           // mensaje = "lista de disponibilidad mayor";
        }
        else{
            cantidad  = vehiculo_list.size();
           // mensaje = "lista de vehiculo mayor";
        }

    //    System.out.println(mensaje+" con :"+cantidad);
        System.out.println("--------------");
        
      int cont = 0;
        for (int i = 0; i < cantidad; i++) {
            if(vehiculo_list.get(i).getId() == todo.get(i).getIdVehiculo()){
                
           //     mensaje2 = "encontrado "+vehiculo_list.get(i).getId();
                cont = cont + i;
                
                idMenor = todo.get(0).getIdVehiculo();
                for (int n = 0; n < todo.size(); n++) {
                    if(todo.get(n).getIdVehiculo() <  idMenor){
                        idMenor = todo.get(n).getIdVehiculo();
                    }
                }
               
                List<Disponibilidad> disponibilidads_menor = disponibilidadDAO.consultarPorIdVehiculo(idMenor);
                
                int idTipoEncomienda_buscada = disponibilidads_menor.get(0).getIdTipoEncomienda();
                TipoEncomienda tipo_encomienda_buscada = tipoEncomiendaDAO.BuscarPorId(idTipoEncomienda_buscada);
                Encomienda encomienda_buscada = encomiendaDAO.BuscarPorId(tipo_encomienda_buscada.getIdEncomienda());
                if(encomienda_buscada.getOrigen() == idOrigen  &&
                   encomienda_buscada.getDestino() == idDestino){
                //    mensaje3 = "tiene los mismos origen y destino";
                    idVehiculo = idMenor;
                    break;
                }
                if(encomienda_buscada.getOrigen() != idOrigen  ||
                   encomienda_buscada.getDestino() != idDestino){
             //       mensaje3 = " no tiene los mismos origen y destino";
                 //   idVehiculo = vehiculo_list.get(i).getId();  
                  // vehiculo_list= vehiculoDAO.ElegirVehiculo(volumen, capacidad);                 
                     break;
                }                
                
            }
        }               
         
     //       vehiculo_list= vehiculoDAO2.ElegirVehiculo(volumen, capacidad);
  */                                
            for(int i = 0; i < vehiculo_list.size(); i++){

            volumen_max = vehiculo_list.get(i).getVolumen();
            capacidad_max = vehiculo_list.get(i).getCapacidad();

            idVehiculo = vehiculo_list.get(i).getId();        
            List<Disponibilidad>   disponibilidadList = disponibilidadDAO.consultarPorIdVehiculo(idVehiculo);
               
                for(int j = 0; j < disponibilidadList.size(); j++){                                        
                    if( disponibilidadList.get(j).getEstado() != 0 &&
                        disponibilidadList.get(j).getSituacion() > 0 
                            ){                                               
                        suma_volumen = suma_volumen + disponibilidadList.get(j).getActualvolumen();
                        suma_capacidad = suma_capacidad + disponibilidadList.get(j).getActualcapacidad();  
                    }
                }                                          
                 
                volumen_encomienda = suma_volumen + volumen;
                capacidad_encomienda = suma_capacidad + capacidad;

                
                Vehiculo vehiculo_guardado = vehiculoDAO.BuscarPorId(idVehiculo);

                if(vehiculo_guardado.getIdConductor() < 1){
                    List<Integer> ids = EscogerConductorAyudante(tiempoConvertido);
                    VehiculoDAO vehiculoDAO3 = new VehiculoDAO();
                    if(ids.get(0) > 0){
                    idConductor = ids.get(0);
                        if(ids.get(1) > 0){
                            idAyudante = ids.get(1);
                            vehiculo_guardado.setIdAyudante(idAyudante);
                        }
                    vehiculo_guardado.setIdConductor(idConductor);                                        
                    vehiculoDAO3.modificar(vehiculo_guardado); 
                    }

                }
                else {                        

                    idConductor = vehiculo_guardado.getIdConductor();
                    idAyudante = vehiculo_guardado.getIdAyudante();
                }
                    
                if( volumen_encomienda<  volumen_max &&  capacidad_encomienda<  capacidad_max ){

                    disponibilidad.setIdVehiculo(idVehiculo);
                    disponibilidad.setIdTipoEncomienda(idTipoEncomienda);                  
                    disponibilidad.setActualvolumen(volumen);
                    disponibilidad.setActualcapacidad(capacidad);
                    disponibilidad.setSituacion(situacion);
                    disponibilidad.setIdConductor(idConductor);
                    disponibilidad.setIdAyudante(idAyudante);   
                    disponibilidadDAO.insertar(disponibilidad);     
                    break;
                }                   
                suma_volumen = 0;
                suma_capacidad = 0;
                volumen_encomienda = 0;
                capacidad_encomienda = 0;         
         }               
               
            Vehiculo vehiculo_buscado = vehiculoDAO2.BuscarPorId(idVehiculo);
            
            vehiculo.setId(vehiculo_buscado.getId());
            vehiculo.setPlaca(vehiculo_buscado.getPlaca());
            vehiculo.setVolumen(vehiculo_buscado.getVolumen());
            vehiculo.setCapacidad(vehiculo_buscado.getCapacidad());           
           } catch (Exception e) {
           System.out.println ("El error es: " + e.getMessage());
           e.printStackTrace();
           }
           
             return vehiculo;    
    }
    
    public List<Integer> EscogerConductorAyudante(String tiempoConvertido){
        ConductorDAO  conductorDAO = new ConductorDAO();
        List<Integer> id_con_ayu = new ArrayList<>();                       
        
        try {
            
            int estado = 0;
            int minutosEntero = Integer.parseInt(tiempoConvertido);
            int minuto_maximo = 480;

            if(minutosEntero > minuto_maximo){
                estado = 1;
            }        
            
            
            List<Conductor> conductorList =  conductorDAO.consultar();
            int idConductor = 0;
            for (int c = 0; c < conductorList.size(); c++) {
                if(conductorList.get(c).getDisp() > 0){
                   idConductor =  conductorList.get(c).getId();
                   Conductor conductor_eliminado =  conductorDAO.BuscarPorId(idConductor);
                    conductorDAO.eliminarDisponiblidad(conductor_eliminado);
                    break;
                }
            }      
            id_con_ayu.add(idConductor);
            
            int idAyudante = 0;
            if(estado == 1){
                  List<Conductor> ayudanteList  =  conductorDAO.consultar();
                for (int a = 0; a < ayudanteList.size(); a++) {
                    if(ayudanteList.get(a).getDisp() > 0){
                       idAyudante =  ayudanteList.get(a).getId();
                        Conductor ayudante_eliminado =  conductorDAO.BuscarPorId(idAyudante);
                        conductorDAO.eliminarDisponiblidad(ayudante_eliminado);                       
                        break;
                    }
                } 
            }  
            id_con_ayu.add(idAyudante);
        } catch (Exception e) {
              Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, e);
        }
         return id_con_ayu;        
    }
/*
    public static void main(String[] args) throws Exception {
        
        ConductorDAO conductorDAO = new ConductorDAO();
        List<Integer> id_con_ayu = new ArrayList<>();
        
        try {
            
            int estado = 0;
            int minutosEntero = Integer.parseInt("100");
            int minuto_maximo = 480;

     //       String respuesta = " no agregar ayudante";
            if(minutosEntero > minuto_maximo){
       //         respuesta = "  agregar ayudante";
                estado = 1;
            }        
                        
            List<Conductor> conductorList = conductorDAO.consultar();
            int idConductor = 0;
            for (int c = 0; c < conductorList.size(); c++) {
                if(conductorList.get(c).getDisp() > 0){
                   idConductor =  conductorList.get(c).getId();
                   Conductor conductor_eliminado = conductorDAO.BuscarPorId(idConductor);
                   conductorDAO.eliminarDisponiblidad(conductor_eliminado);
                    break;
                }
            }      
            id_con_ayu.add(idConductor);
            
            int idAyudante = 0;
            if(estado == 1){
                  List<Conductor> ayudanteList  = conductorDAO.consultar();
                for (int a = 0; a < ayudanteList.size(); a++) {
                    if(ayudanteList.get(a).getDisp() > 0){
                       idAyudante =  ayudanteList.get(a).getId();
                        Conductor ayudante_eliminado = conductorDAO.BuscarPorId(idAyudante);
                        conductorDAO.eliminarDisponiblidad(ayudante_eliminado);                       
                        break;
                    }
                } 
            }  
            id_con_ayu.add(idAyudante);
        } catch (Exception e) {
              Logger.getLogger(SERVEncomienda.class.getName()).log(Level.SEVERE, null, e);
        }
         System.out.println(id_con_ayu.get(0) +", "+ id_con_ayu.get(1));

    }
/*
    public int determinarAyundate(String tiempoConvertido){
        int estado = 0;
        
        int minutosEntero = Integer.parseInt(tiempoConvertido);
        int minuto_maximo = 480;
        
        String respuesta = " no agregar ayudante";
        if(minutosEntero > minuto_maximo){
            respuesta = "  agregar ayudante";
            estado = 1;
        }        
        
        return estado;
    }        
  /*  
    public Vehiculo EscogerVehiculo(double volumen, double capacidad, int id){
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
           */
}
