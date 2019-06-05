
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
import entidad.Encomienda;
import entidad.Lugar;
import entidad.TipoEncomienda;
import entidad.Vehiculo;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
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

public class SERVEncomienda2 extends HttpServlet {

    String index = "/index.jsp";
   
    private EncomiendaDAO encomiendadao;
    private TipoEncomiendaDAO tipoEncomiendaDAO;
    private VehiculoDAO vehiculoDAO;
    private ClienteDAO clientedao;
    private LugarDAO lugarDAO;
    private ConductorDAO conductorDAO;
    private DisponibilidadDAO disponibilidadDAO;
    Encomienda encomienda = new Encomienda();

            
     public SERVEncomienda2() {
        tipoEncomiendaDAO = new TipoEncomiendaDAO(){};
    	encomiendadao = new EncomiendaDAO(){};
        vehiculoDAO = new VehiculoDAO(){};
        clientedao = new ClienteDAO(){};
        lugarDAO = new LugarDAO(){};
        conductorDAO = new ConductorDAO(){};
       disponibilidadDAO = new DisponibilidadDAO(){};
    }       
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SERVEncomienda2</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SERVEncomienda2 at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");        
        String usuario_de_login = "";
        String vista = "";
        
        List<Encomienda> encomienda_list = null;
                         
        try {
            if (action.equalsIgnoreCase("delete")) { 
                HttpSession sesion = request.getSession();
                
                if(sesion.getAttribute("usuario")!=null){
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);                                                          
                    int nivel = cliente.getNivel();     
                    
                    int idEncomienda = Integer.parseInt(request.getParameter("id"));
                    if(nivel == 2){                         
                        vista = "ListarEncomienda1.jsp";
                        encomienda_list = deleteEncomiendaById(idEncomienda);
                        
                    }
                    else if(nivel == 1){
                        vista = "ListarEncomienda.jsp";
                        encomienda_list = deleteEncomiendaById(idEncomienda);
                    }                 
                    request.setAttribute("encomienda", encomienda_list);                  
                }                    
                else{
                    vista = "index.jsp";
                }                  
                
            }
            else if (action.equalsIgnoreCase("edit")) {
                HttpSession sesion = request.getSession();
                
                //Todo la lógica se hará siempre y cuando el usuario siga en sesión
                if(sesion.getAttribute("usuario")!=null){
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);
                    int nivel = cliente.getNivel();          
                    String tipo;
                    Encomienda encomienda_buscada;
                    TipoEncomienda tipoEncomienda;
                   String idEncomiendaString = request.getParameter("id");
                   
                    if(nivel == 2){                   
                        
                        int estado = 0;
                        List<Encomienda> encomienda_usuario_login = encomiendadao.consultarEncomiendaPorIdCliente(cliente.getId());
                        int idEncomienda = 0 ;
                        
                        //ESTA VALIDACION SE HACE POR SI ALGUIEN CAMBIA EL ID DE LA URL
                        if(idEncomiendaString.length() <= 11){
                          idEncomienda = Integer.parseInt(request.getParameter("id"));
                            for (int i = 0; i < encomienda_usuario_login.size(); i++) {
                                if(encomienda_usuario_login.get(i).getId() == idEncomienda){
                                    estado = 1;
                                }
                            }
                        }
                        //Si el id no es el correcto entonces se le manda a su lista
                        else{
                           vista = "ListarEncomienda1.jsp";
                           encomienda_usuario_login = getEncomiendaByClienteID(cliente.getId());
                           request.setAttribute("encomienda", encomienda_usuario_login); 
                        }
                        
                        //si el id de la encomienda le pertenece al usuario que esta en sesion entonces se busca
                        if(estado == 1){
                            encomienda_buscada = encomiendadao.BuscarPorId(idEncomienda);
                            tipoEncomienda = tipoEncomiendaDAO.buscarTipoEncomiendaPorEncomiendaID(idEncomienda);
                            
                            Lugar lugar_origen = lugarDAO.BuscarPorId(encomienda_buscada.getOrigen());
                            Lugar lugar_destino = lugarDAO.BuscarPorId(encomienda_buscada.getDestino());
                            encomienda_buscada.setOrigenS( lugar_origen.getNombre());
                            encomienda_buscada.setDestinoS(lugar_destino.getNombre());      
                     
                            tipo = tipoEncomienda.getTipo();    
                            
                                   
                            if(tipo.equalsIgnoreCase("sobre")){
                                vista = "EditarEncomiendaSobre.jsp";
                            }
                            if(tipo.equalsIgnoreCase("paquete")){
                                vista = "EditarEncomiendaPaquete.jsp";
                            }
                            request.setAttribute("encomienda", encomienda_buscada);
                            request.setAttribute("tipoEncomienda", tipoEncomienda);
                            
                        }
                        //si no le pertenece
                        else{
                           vista = "ListarEncomienda1.jsp";
                           encomienda_usuario_login = getEncomiendaByClienteID(cliente.getId());
                           request.setAttribute("encomienda", encomienda_usuario_login); 
                        }
                        
                    }
                    else if(nivel == 1){
                        
                        int idEncomienda = Integer.parseInt(request.getParameter("id"));
                        encomienda_buscada = encomiendadao.BuscarPorId(idEncomienda);
                        tipoEncomienda = tipoEncomiendaDAO.buscarTipoEncomiendaPorEncomiendaID(idEncomienda);

                        Lugar lugar_origen = lugarDAO.BuscarPorId(encomienda_buscada.getOrigen());
                        Lugar lugar_destino = lugarDAO.BuscarPorId(encomienda_buscada.getDestino());
                        encomienda_buscada.setOrigenS( lugar_origen.getNombre());
                        encomienda_buscada.setDestinoS(lugar_destino.getNombre());      

                        tipo = tipoEncomienda.getTipo();    


                        if(tipo.equalsIgnoreCase("sobre")){
                            vista = "EditarEncomiendaSobre.jsp";
                        }
                        if(tipo.equalsIgnoreCase("paquete")){
                            vista = "EditarEncomiendaPaquete.jsp";
                        }
                        
                        request.setAttribute("encomienda", encomienda_buscada); 
                        request.setAttribute("tipoEncomienda", tipoEncomienda);                        
                    } 
                 
                }       
                else{
                    vista = "index.jsp";
                }                   
            }
            else if(action.equalsIgnoreCase("insert")) {  
                HttpSession sesion = request.getSession();
                if(sesion.getAttribute("usuario")!=null){
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);
                    int nivel = cliente.getNivel();
                    if(nivel == 2){                        
                        vista = "RegistrarEncomienda1.jsp";
                    }
                    else if(nivel == 1){

                    }
                  //  request.setAttribute("encomienda", encomienda_list); 
                }
                else{
                    vista = "index.jsp";
                }                   
            }
            else if(action.equalsIgnoreCase("refresh")){
                HttpSession sesion = request.getSession();        
                
                if(sesion.getAttribute("usuario")!=null){
                    usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                    Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);
                    int nivel = cliente.getNivel();
                    int idCliente = 0;
                    if(nivel == 2){                        
                        vista = "ListarEncomienda1.jsp";
                        idCliente = cliente.getId();
                    }
                    else if(nivel == 1){
                        
                        //cuando el administrador desea entrar en las encomiendas de un cliente
                        if(request.getParameter("id")!=null){
                            idCliente = Integer.parseInt(request.getParameter("id"));                                                     
                            vista = "ListarEncomienda.jsp";                                       
                        }
                        //cuando el administrador desea entrar a todas las encomiendas
               //         else{
              //              vista = "ListarEncomienda.jsp";                        
               //         }                                              
                    }

                    encomienda_list = getEncomiendaByClienteID(idCliente);
                    request.setAttribute("encomienda", encomienda_list);                     
                }
                else{
                    vista = "index.jsp";
                }                
            }
            else if(action.equalsIgnoreCase("buscarEncomienda")){
                
            }
            else if(action.equalsIgnoreCase("cerrar")){
                HttpSession sesion = request.getSession();
                sesion.invalidate();
                response.sendRedirect("index.jsp");
            }            
        } catch (Exception e) {
            vista = "error.jsp";
             Logger.getLogger(SERVEncomienda2.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
                request.setAttribute("usuario",  usuario_de_login);               
                RequestDispatcher view = request.getRequestDispatcher(vista);
                view.forward(request, response);              
            
        }       
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
                       
        try {               
            HttpSession sesion = request.getSession();
            if(sesion.getAttribute("usuario")!=null){
                String usuario_de_login = String.valueOf(sesion.getAttribute("usuario"));
                Cliente cliente = clientedao.BuscarPorUsuario(usuario_de_login);

               String origen = request.getParameter("txtOrigen");        
               String destino = request.getParameter("txtDestino");
               String tiempoConvertido = request.getParameter("txtTiempoConvertido");
               String recorrido = request.getParameter("txtRecorrido");
               int idCliente = cliente.getId();        

               int  delicado = 0;     
               
               if(request.getParameter("txtDelicado")!=null){
                    delicado = Integer.parseInt(request.getParameter("txtDelicado"));
               }
               
               String tipo = request.getParameter("pago1");

               int cantidad = 0;
               double peso = 0; 
               double precio = 0;

               double altura = 0;
               double anchura = 0;
               double largo = 0;             

               if(tipo.equalsIgnoreCase("sobre")){

                   int cantidadSobre = Integer.parseInt(request.getParameter("txtCantidadSobre"));
                   double pesoSobre = Double.parseDouble(request.getParameter("txtPesoSobre"));
                   double precioSobre = Double.parseDouble(String.valueOf(request.getParameter("txtPrecioSobre")));

                   cantidad = cantidadSobre;
                   peso = pesoSobre;
                   precio = precioSobre;            

               }
               else if(tipo.equalsIgnoreCase("paquete")){

                   altura = Double.parseDouble(request.getParameter("txtAltura"));                
                   anchura = Double.parseDouble(request.getParameter("txtAnchura"));                              
                   largo = Double.parseDouble(request.getParameter("txtLargo"));        

                   int cantidadPaquete = Integer.parseInt(request.getParameter("txtCantidadPaquete"));
                   double pesoVolumen = Double.parseDouble(request.getParameter("txtPesoVolumen"));       
                   double pesoPaquete = Double.parseDouble(request.getParameter("txtPesoPaquete"));
                   double precioPaquete = Double.parseDouble(request.getParameter("txtPrecioPaquete"));    

                   cantidad = cantidadPaquete;
                   if(pesoVolumen > pesoPaquete){
                       peso = pesoVolumen;
                   }
                   else{
                       peso = pesoPaquete;
                   }               
                   precio = precioPaquete;           
               }


                if(request.getParameter("btnRegistrar")!=null){

                   Lugar lugar_origen = lugarDAO.ConsultarPorNombre(origen);
                   Lugar lugar_destino = lugarDAO.ConsultarPorNombre(destino);

                   int idOrigen = lugar_origen.getId();
                   int idDestino = lugar_destino.getId();

                   Encomienda encomienda_nueva = new Encomienda();
                   encomienda_nueva.setOrigen(idOrigen);            
                   encomienda_nueva.setDestino(idDestino);
                   encomienda_nueva.setIdCliente(idCliente);

                   encomiendadao.insertar(encomienda_nueva);

                   encomienda_nueva = encomiendadao.getUltimoEncomiendaByIdCliente(idCliente);

                   int idEncomienda = encomienda_nueva.getId();

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

                   double volumen_aprox = redondearDecimales(volumen, 2);
                   if(volumen_aprox < 0.01){
                       volumen_aprox = 0.01;
                   }            

                   Vehiculo vehiculo_seleccionado =  EscogerVehiculo(volumen_aprox, peso,idEncomienda, idTipoEncomienda, tiempoConvertido, idOrigen, idDestino);

                   int idVehiculo = vehiculo_seleccionado.getId();
                   String matricula = vehiculo_seleccionado.getPlaca();

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

               }
               else{

               } 

           }            
        } catch (Exception e) {
        }
        finally{
            response.sendRedirect(request.getContextPath() + "/SERVEncomienda?action=cerrar");
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    //busca y luego lista
    public List<Encomienda> getEncomiendaByClienteID(int idCliente) throws Exception{

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
        return encomienda_list;
    }
    
    //Elimina y luego lista
    public List<Encomienda> deleteEncomiendaById(int idEncomienda) throws Exception{
        
        Disponibilidad disponibilidad = new Disponibilidad();
        Encomienda encomienda_encontrada = encomiendadao.BuscarPorId(idEncomienda);
        int idCliente = encomienda_encontrada.getIdCliente();

        Encomienda encomienda_borrar = new Encomienda();
        encomienda_borrar.setId(idEncomienda);

        tipoEncomiendaDAO.consultarPorEncomienda(idEncomienda);
        TipoEncomienda tipoEncomienda = tipoEncomiendaDAO.consultarPorEncomienda(idEncomienda);   

        disponibilidad.setIdTipoEncomienda(tipoEncomienda.getId());
        disponibilidadDAO.eliminarPorTipoEncomienda(disponibilidad);
        encomiendadao.eliminar(encomienda_borrar);                                                                                
        tipoEncomiendaDAO.eliminar(tipoEncomienda);
        
        return getEncomiendaByClienteID(idCliente); 
//         disponibilidadDAO.eliminarPorTipoEncomienda(tipoEncomienda.getId());   
        
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
  //      DisponibilidadDAO disponibilidadDAO = new DisponibilidadDAO();        
        Disponibilidad disponibilidad = new Disponibilidad();
        EncomiendaDAO encomiendaDAO = new EncomiendaDAO();
        double volumen_max;
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
     //   ConductorDAO  conductorDAO = new ConductorDAO();
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
}
