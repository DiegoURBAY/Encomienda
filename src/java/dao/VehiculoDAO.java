
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidad.Vehiculo;

public class VehiculoDAO extends Conexion implements DAO{

/*
public static void main(String[] args) throws Exception {
        
    Vehiculo vehiculo = new Vehiculo(); 
    VehiculoDAO vehiculoDAO2 = new VehiculoDAO();
    double capacidad = 100;
    double volumen = 1;
    
    double actual_volumen;
    double actual_capacidad;
    double suma_volumen;
    double suma_capacidad;
    int idVehiculo;
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
    } catch (Exception e) {
    System.out.println ("El error es: " + e.getMessage());
    e.printStackTrace();
}
    


/*
    for(int i = 0; i < vehiculo_list.size(); i++){
        
       
                if(i > 2){
                                vehiculo.setActualvolumen(volumen);
            vehiculo.setActualcapacidad(capacidad);
            vehiculo.setId(5);
            vehiculoDAO2.modificar(vehiculo);  
                }
       
    
       suma_volumen = Actualvolumen + volumen;       
       suma_capacidad = Actualcapacidad + capacidad;
       
        if(suma_volumen < vehiculo_list.get(i).getVolumen()
                && suma_capacidad < vehiculo_list.get(i).getCapacidad()){
            
            idVehiculo =  vehiculo_list.get(i).getId();
            encomienda2.setId(idVehiculo);
            encomienda2.setActualvolumen(volumen);
            encomienda2.setActualcapacidad(capacidad);
            vehiculoDAO2.modificar(encomienda2);
        }
     
    }
    
        //Se busca y luego modifica
    if(vehiculo_list !=null){
        
        //Conseguir el id 
        //Se analiza la capacidad actual tanto de la capacidad y del vehiculo
         vehiculoDAO2.modificar(encomienda2);
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
