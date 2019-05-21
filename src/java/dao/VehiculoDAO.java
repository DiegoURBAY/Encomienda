
package dao;


import entidad.Disponibilidad;
import entidad.Encomienda;
import entidad.TipoEncomienda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidad.Vehiculo;

public class VehiculoDAO extends Conexion implements DAO{


public static void main(String[] args) throws Exception {
       
   DisponibilidadDAO disponibilidadDAO = new DisponibilidadDAO();
    
    TipoEncomiendaDAO tipoEncomiendaDAO = new TipoEncomiendaDAO();
     EncomiendaDAO encomiendaDAO = new EncomiendaDAO();
     VehiculoDAO vehiculoDAO = new VehiculoDAO();
   int idVehiculo = 0;
   int origen = 90;
   int destino = 23;
   int idMenor = 0;
   String mensaje = "";
   String mensaje2 = "";
   String mensaje3 = "";
   double volumen = 0.01;
   double capacidad = 0.01;
   double suma_volumen = 0;
   double suma_capacidad = 0;
   double volumen_encomienda = 0;
   double capacidad_encomienda = 0;

     List<Vehiculo> vehiculo_list= vehiculoDAO.ElegirVehiculo(volumen, capacidad);
     //List<Vehiculo> vehiculo_disponibles = new ArrayList<>();
     List<Disponibilidad> todo = disponibilidadDAO.consultar();
     
     int cantidad ;
        if(todo.size() > vehiculo_list.size()){
            cantidad = todo.size();
            mensaje = "lista de disponibilidad mayor";
        }
        else{
            cantidad  = vehiculo_list.size();
            mensaje = "lista de vehiculo mayor";
        }

        System.out.println(mensaje+" con :"+cantidad);
        System.out.println("--------------");
        
      //   idMenor = vehiculo_list.get(0).getId();
      int cont = 0;
        for (int i = 0; i < cantidad; i++) {
            if(vehiculo_list.get(i).getId() == todo.get(i).getIdVehiculo()){
                
                mensaje2 = "encontrado "+vehiculo_list.get(i).getId();
                cont = cont + i;
                
                idMenor = todo.get(0).getIdVehiculo();
                for (int n = 0; n < todo.size(); n++) {
                    if(todo.get(n).getIdVehiculo() <  idMenor){
                        idMenor = todo.get(n).getIdVehiculo();
                    }
                }
               
                
                int idTipoEncomienda = todo.get(i).getIdTipoEncomienda();
                TipoEncomienda tipo_encomienda_buscada = tipoEncomiendaDAO.BuscarPorId(idTipoEncomienda);
                Encomienda encomienda_buscada = encomiendaDAO.BuscarPorId(tipo_encomienda_buscada.getIdEncomienda());
                if(encomienda_buscada.getOrigen() == origen  &&
                   encomienda_buscada.getDestino() == destino){
                    mensaje3 = "tiene los mismos origen y destino";
                    idVehiculo = todo.get(i).getIdVehiculo();
                    break;
                }
                if(encomienda_buscada.getOrigen() != origen  ||
                   encomienda_buscada.getDestino() != destino){
                    mensaje3 = " no tiene los mismos origen y destino";
                 //   idVehiculo = vehiculo_list.get(i).getId();  
                  // vehiculo_list= vehiculoDAO.ElegirVehiculo(volumen, capacidad);                 
                     break;
                }                
                
            }
        }
        
        System.out.println("El idVehiculo encontrado :"+mensaje2);
        System.out.println("mensaje 3: "+mensaje3);
        System.out.println("contador:"+cont);
        System.out.println("idVehiculo:"+idVehiculo);
        System.out.println("--------------");
        
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
                          System.out.println("volumen:"+volumen_encomienda);
                          System.out.println("capacidad:"+capacidad_encomienda);
        System.out.println("--------------");
    }    

    

    @Override
    public void insertar(Object obj) throws Exception {
        Vehiculo veh = (Vehiculo) obj;
        PreparedStatement pst;
        String sql="INSERT INTO vehiculos (placa, volumen, capacidad, fechatime) VALUES(?,?,?, CURRENT_TIMESTAMP)";
        try {
            this.conectar();           
            pst = conexion.prepareStatement(sql);
            pst.setString(1, veh.getPlaca());
            pst.setDouble(2, veh.getVolumen());
            pst.setDouble(3, veh.getCapacidad());
            pst.executeUpdate();            
                               
        } catch ( SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }        
    }

    @Override
    public void eliminar(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Object obj) throws Exception {
        Vehiculo veh = (Vehiculo) obj;
        PreparedStatement pst;
        String sql="UPDATE vehiculos set actualvolumen = ?, actualcapacidad = ?, idConductor = ?, idAyudante = ? WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setDouble(1, veh.getActualvolumen());
            pst.setDouble(2, veh.getActualcapacidad());
            pst.setInt(3, veh.getIdConductor());
            pst.setInt(4, veh.getIdAyudante());
            pst.setInt(5, veh.getId());
            pst.executeUpdate(); 
            
        } catch ( SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }        
    }

    @Override
    public List consultar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vehiculo BuscarPorId(int id) throws Exception {
           Vehiculo c = new Vehiculo();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT id, placa, volumen, capacidad, actualvolumen, actualcapacidad, idConductor, idAyudante FROM vehiculos WHERE estado = 1 AND id = ?";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);                 
               res = pst.executeQuery();                                    
                if (res.next()) {          
                    c.setId(res.getInt("id"));
                    c.setPlaca(res.getString("placa"));            
                    c.setVolumen(res.getDouble("volumen"));                               
                    c.setCapacidad(res.getDouble("capacidad"));                    
                    c.setActualvolumen(res.getDouble("actualvolumen"));
                    c.setActualcapacidad(res.getDouble("actualcapacidad"));
                    c.setIdConductor(res.getInt("idConductor"));
                    c.setIdAyudante(res.getInt("idAyudante"));
                    
               //     c.setEnvio(res.getDate("envio"));   
                 //   c.setLlegada(res.getDate("llegada")); 
                }                   
     
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
           return c;        
    }           

    public List<Vehiculo> consultarPorPeso(double peso) throws Exception {
        List<Vehiculo> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, placa, capacidad, idEncomienda	FROM vehiculos WHERE estado = 1 AND capacidad > ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setDouble(1, peso);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Vehiculo(
                        rs.getInt("id"),
                        rs.getString("placa"),
                        rs.getDouble("capacidad"),
                        rs.getInt("idEncomienda")
                    )
                );
            }
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }
        return datos;     
    }
    
    public Vehiculo consultarVehiculoPorPeso(double peso) throws Exception {
        Vehiculo vehiculo = new Vehiculo();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, placa, capacidad FROM vehiculos WHERE estado = 1 AND capacidad > ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setDouble(1, peso);
            rs = pst.executeQuery();
                 if (rs.next()) {          
                    vehiculo.setId(rs.getInt("id"));
                    vehiculo.setPlaca(rs.getString("placa"));
                    vehiculo.setCapacidad(rs.getDouble("capacidad"));
                }  
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }
        return vehiculo;     
    }    
    
    
    public List<Vehiculo> ElegirVehiculo(double volumen, double peso) throws Exception {
      
        List<Vehiculo> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet res;
        String sql = "SELECT id, placa, volumen, capacidad, actualvolumen, actualcapacidad FROM vehiculos WHERE ocupado = 1 AND estado = 1 AND volumen > ? AND capacidad > ? ";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);            
            pst.setDouble(1, volumen);
            pst.setDouble(2, peso);            
            res = pst.executeQuery();
            while(res.next()){
                datos.add(new Vehiculo(
                        res.getInt("id"),
                        res.getString("placa"),
                        res.getDouble("volumen"),
                        res.getDouble("capacidad"),
                        res.getDouble("actualvolumen"),
                        res.getDouble("actualcapacidad")                        
                        )
                );            
            }           
            
        } catch (SQLException e) {
                System.out.println ("El error es: " + e.getMessage());
        }
        finally{
            this.cerrar();
        }   
        return datos;        
    }     

                
}
