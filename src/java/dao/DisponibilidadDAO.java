
package dao;

import entidad.Disponibilidad;
import entidad.Encomienda;
import entidad.Vehiculo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DisponibilidadDAO extends Conexion implements DAO{ 
    
    public static void main(String[] args) throws Exception {
        Disponibilidad disponibilidad = new Disponibilidad();
        
        
        int idVehiculo = 1;
        int idTipoEncomienda = 1;
        double pesoactual = 551;
        java.util.Date d1 = new java.util.Date();  
        java.sql.Timestamp date1 = new java.sql.Timestamp(d1.getTime());

        Date date= new Date();
	long time = date.getTime();
        java.sql.Timestamp date11 = new java.sql.Timestamp(time);
        
        
        Timestamp desde = date11;
        
        java.util.Date d2 = new java.util.Date();  
        java.sql.Timestamp date2 = new java.sql.Timestamp(d2.getTime());

        Timestamp hasta = date11;
        
        int situacion = 1;
        
      
        disponibilidad.setIdTipoEncomienda(idTipoEncomienda);
        disponibilidad.setPesoactual(pesoactual);
        disponibilidad.setDesde(desde);
        disponibilidad.setHasta(hasta);
        disponibilidad.setSituacion(situacion);
                
        DisponibilidadDAO DDAO = new DisponibilidadDAO();
        VehiculoDAO vehiculoDAO = new VehiculoDAO(); 
        
        Vehiculo vehiculo = new Vehiculo();
        
        vehiculo = vehiculoDAO.BuscarPorId(idVehiculo);
        double capacidad = vehiculo.getCapacidad();
        
        List<Vehiculo> vehiList = vehiculoDAO.consultarPorPeso(capacidad);
        
        Vehiculo veh[] = new Vehiculo[vehiList.size()];
        veh = vehiList.toArray(veh);            

        for(int i = 0; i < veh.length; i++){

             if(veh[i].getCapacidad() > pesoactual ){

                 vehiculoDAO.insertar(disponibilidad);
             }
        }
        
        if(situacion == 0){
        
        }
        
}

    @Override
    public void insertar(Object obj) throws Exception {
        Disponibilidad c = (Disponibilidad) obj;
        PreparedStatement pst;
        String sql="INSERT INTO disponibilidad (idVehiculo, idTipoEncomienda, pesoactual, desde, hasta, situacion, fecharegistro) VALUES ( ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, c.getIdVehiculo());
            pst.setInt(2, c.getIdTipoEncomienda());
            pst.setDouble(3, c.getPesoactual());
            pst.setTimestamp(4, c.getDesde());
            pst.setTimestamp(5, c.getHasta());
            pst.setInt(6, c.getSituacion() );
            pst.executeUpdate();            

        } catch (SQLException e) {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List consultar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object BuscarPorId(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
