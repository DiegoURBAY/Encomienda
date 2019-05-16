
package dao;

import entidad.Disponibilidad;
import entidad.TipoEncomienda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidad.Vehiculo;

public class VehiculoDAO extends Conexion implements DAO{


public static void main(String[] args) throws Exception {
       
   
       Vehiculo vehiculo = new Vehiculo();     
        Vehiculo vehiculo2 = new Vehiculo();
        VehiculoDAO vehiculoDAO2 = new VehiculoDAO();
        List<Vehiculo>  vehiculo_list;
        DisponibilidadDAO disponibilidadDAO = new DisponibilidadDAO();
        List<Disponibilidad> disponibilidadList = new ArrayList<>();
        Disponibilidad disponibilidad = new Disponibilidad();
        
        //adicional
         int idTipoEncomienda = 184;
        TipoEncomiendaDAO tipoEncomiendaDAO = new TipoEncomiendaDAO();
        TipoEncomienda tipoEncomienda = tipoEncomienda = tipoEncomiendaDAO.BuscarPorId(idTipoEncomienda);
        
               
        double volumen = 0.47;
        double capacidad = 400;        
        
        double volumen_max = 0;
        double capacidad_max = 0;
        double suma_volumen = 0;
        double suma_capacidad = 0;
        double volumen_encomienda;
        double capacidad_encomienda;    
        int situacion = 1;
        int idVehiculo = 0;        
               
           try {
         
               //Lista de vehiculos
            vehiculo_list= vehiculoDAO2.ElegirVehiculo(volumen, capacidad);

             for(int i = 0; i < vehiculo_list.size(); i++){

                 volumen_max = vehiculo_list.get(i).getVolumen();
                 capacidad_max = vehiculo_list.get(i).getCapacidad();

                 idVehiculo = vehiculo_list.get(i).getId();

                 disponibilidadList = disponibilidadDAO.consultarPorIdVehiculo(idVehiculo);
                 
                 
                 
                for(int j = 0; j < disponibilidadList.size(); j++){
                    if(disponibilidadList.get(j).getEstado() != 0){
                        suma_volumen = suma_volumen + disponibilidadList.get(j).getActualvolumen();
                    suma_capacidad = suma_capacidad + disponibilidadList.get(j).getActualcapacidad();  
                    }

                }
                 

           //  double volumen_mayor = 0;
         //    double capacidad_mayor = 0;
            
             /*
            if(disponibilidadList.size() > 0){
                
                volumen_mayor = disponibilidadList.get(0).getActualvolumen();
                capacidad_mayor = disponibilidadList.get(0).getActualcapacidad();
             for(int j = 1; j < disponibilidadList.size(); j++){                                   
                     double volumen_no_mayor = disponibilidadList.get(j).getActualvolumen();
               double capacidad_no_mayor = disponibilidadList.get(j).getActualcapacidad();
               
                    if(volumen_no_mayor > volumen_mayor){
                        volumen_mayor = volumen_no_mayor;                                                
                    }
                     if(capacidad_no_mayor > capacidad_mayor){
                        capacidad_mayor = capacidad_no_mayor;                                                
                    }
                }
            
            suma_volumen = volumen_mayor;
            suma_capacidad = capacidad_mayor;             
            
            }                       
             */
             
             
                 volumen_encomienda = suma_volumen + volumen;
                 capacidad_encomienda = suma_capacidad + capacidad;
                 
                 if( volumen_encomienda<  volumen_max &&  capacidad_encomienda<  capacidad_max ){

                     disponibilidad.setIdVehiculo(idVehiculo);
                     disponibilidad.setIdTipoEncomienda(idTipoEncomienda);
                     disponibilidad.setActualvolumen(volumen);
                     disponibilidad.setActualcapacidad(capacidad);
                     disponibilidad.setSituacion(situacion);
                     disponibilidadDAO.insertar(disponibilidad);
                     break;
                 }                   
                suma_volumen = 0;
                suma_capacidad = 0;
                volumen_encomienda = 0;
                capacidad_encomienda = 0;         
         }               
             /*  
            Vehiculo vehiculo_buscado = vehiculoDAO2.BuscarPorId(idVehiculo);
            
            vehiculo2.setId(vehiculo_buscado.getId());
            vehiculo2.setPlaca(vehiculo_buscado.getPlaca());
            vehiculo2.setVolumen(vehiculo_buscado.getVolumen());
            vehiculo2.setCapacidad(vehiculo_buscado.getCapacidad());    
             */
           } catch (Exception e) {
           System.out.println ("El error es: " + e.getMessage());
           e.printStackTrace();
           }
           
        //     return vehiculo2;    
    }    
    /*
 Vehiculo vehiculo = new Vehiculo(); 
    VehiculoDAO vehiculoDAO2 = new VehiculoDAO();
    DisponibilidadDAO disponibilidadDAO = new DisponibilidadDAO();
    List<Disponibilidad> disponibilidadList = new ArrayList<>();
    Disponibilidad disponibilidad = new Disponibilidad();
    double capacidad = 100;
    double volumen = 0.01;
    
    double volumen_max = 0;
    double capacidad_max = 0;
    double volumen_actual;
    double capacidad_actual;
    double suma_volumen = 0;
    double suma_capacidad = 0;
    double volumen_encomienda;
    double capacidad_encomienda;
    int idVehiculo;
    List<Vehiculo>  vehiculo_list;
   
    int idTipoEncomienda = 160;
    int situacion = 1;
    try {
       vehiculo_list= vehiculoDAO2.ElegirVehiculo(volumen, capacidad);

        for(int i = 0; i < vehiculo_list.size(); i++){
            
            volumen_max = vehiculo_list.get(i).getVolumen();
            capacidad_max = vehiculo_list.get(i).getCapacidad();
            //capacidad_max = 1390.00
            idVehiculo = vehiculo_list.get(i).getId();
            //id 
            disponibilidadList = disponibilidadDAO.consultarPorIdVehiculo(idVehiculo);
            
             double volumen_mayor = 0;
             double capacidad_mayor = 0;
            
             
            if(disponibilidadList.size() > 0){
                
                volumen_mayor = disponibilidadList.get(0).getActualvolumen();
                capacidad_mayor = disponibilidadList.get(0).getActualcapacidad();
             for(int j = 1; j < disponibilidadList.size(); j++){                                   
                     double volumen_no_mayor = disponibilidadList.get(j).getActualvolumen();
               double capacidad_no_mayor = disponibilidadList.get(j).getActualcapacidad();
               
                    if(volumen_no_mayor > volumen_mayor){
                        volumen_mayor = volumen_no_mayor;                                                
                    }
                     if(capacidad_no_mayor > capacidad_mayor){
                        capacidad_mayor = capacidad_no_mayor;                                                
                    }
                }
             
            suma_volumen = volumen_mayor;
            suma_capacidad = capacidad_mayor;             
            
            }
           
            volumen_encomienda = suma_volumen + volumen;            
            capacidad_encomienda = suma_capacidad + capacidad;
            
            if( volumen_encomienda<  volumen_max  &&  capacidad_encomienda<  capacidad_max ){
                
                disponibilidad.setIdVehiculo(idVehiculo);
                disponibilidad.setIdTipoEncomienda(idTipoEncomienda);
                disponibilidad.setActualvolumen(volumen_encomienda);
                disponibilidad.setActualcapacidad(capacidad_encomienda);
                disponibilidad.setSituacion(situacion);
                disponibilidadDAO.insertar(disponibilidad);
                
                break;
            }                   
            suma_volumen = 0;
            suma_capacidad = 0;
            volumen_encomienda = 0;
            capacidad_encomienda = 0;
            
            
    }
    } catch (Exception e) {
    System.out.println ("El error es: " + e.getMessage());
    e.printStackTrace();
    }
       */ 
    /*
    Vehiculo vehiculo = new Vehiculo(); 
    VehiculoDAO vehiculoDAO2 = new VehiculoDAO();
    DisponibilidadDAO disponibilidadDAO = new DisponibilidadDAO();
    List<Disponibilidad> disponibilidadList = new ArrayList<>();
    Disponibilidad disponibilidad = new Disponibilidad();
    double capacidad = 100;
    double volumen = 0;
    
    double volumen_max = 0;
    double capacidad_max = 0;
    double volumen_actual;
    double capacidad_actual;
    double suma_volumen = 0;
    double suma_capacidad = 0;
    double volumen_encomienda;
    double capacidad_encomienda;
    int idVehiculo;
    List<Vehiculo>  vehiculo_list;
   
    int idTipoEncomienda = 160;
    int situacion = 1;
    try {
       vehiculo_list= vehiculoDAO2.ElegirVehiculo(volumen, capacidad);

        for(int i = 0; i < vehiculo_list.size(); i++){
            
            volumen_max = vehiculo_list.get(i).getVolumen();
            capacidad_max = vehiculo_list.get(i).getCapacidad();
            //capacidad_max = 1390.00
            idVehiculo = vehiculo_list.get(i).getId();
            //id 
            disponibilidadList = disponibilidadDAO.consultarPorIdVehiculo(idVehiculo);
            
            for(int j = 0; j < disponibilidadList.size(); j++){
                suma_volumen =  suma_volumen + disponibilidadList.get(j).getActualvolumen();
                suma_capacidad = suma_capacidad + disponibilidadList.get(j).getActualcapacidad();
            }                          
            volumen_encomienda = suma_volumen + volumen;            
            capacidad_encomienda = suma_capacidad + capacidad;
            
            if( volumen_encomienda<  volumen_max  &&  capacidad_encomienda<  capacidad_max ){
                
                disponibilidad.setIdVehiculo(idVehiculo);
                disponibilidad.setIdTipoEncomienda(idTipoEncomienda);
                disponibilidad.setActualvolumen(volumen_encomienda);
                disponibilidad.setActualcapacidad(capacidad_encomienda);
                disponibilidad.setSituacion(situacion);
                disponibilidadDAO.insertar(disponibilidad);
                
                break;
            }                   
            suma_volumen = 0;
            suma_capacidad = 0;
            volumen_encomienda = 0;
            capacidad_encomienda = 0;
            
            
    }
    } catch (Exception e) {
    System.out.println ("El error es: " + e.getMessage());
    e.printStackTrace();
    }
   } 
*/
    
  
    



   
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
        String sql="UPDATE vehiculos set actualvolumen = ?, actualcapacidad = ? WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setDouble(1, veh.getActualvolumen());
            pst.setDouble(2, veh.getActualcapacidad());
            pst.setInt(3, veh.getId());
            pst.executeUpdate(); 
            
        } catch ( SQLException e) {
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
           String sql = "SELECT id, placa, volumen, capacidad, actualvolumen, actualcapacidad FROM vehiculos WHERE estado = 1 AND id = ?";
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
        String sql = "SELECT id, placa, volumen, capacidad, actualvolumen, actualcapacidad FROM vehiculos WHERE estado = 1 AND volumen > ? AND capacidad > ? AND ocupado = 1";
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
