
package dao;

import entidad.Disponibilidad;
import entidad.Encomienda;
import entidad.Vehiculo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DisponibilidadDAO extends Conexion implements DAO{ 
    
    public static void main(String[] args) throws Exception {
        Disponibilidad disponibilidad = new Disponibilidad();
        DisponibilidadDAO disponibilidadDAO = new DisponibilidadDAO();
        
        
        int idVehiculo = 3;
        int idTipoEncomienda = 142;
        double peso = 202.00;
        double volumen = 2.00;        
        int situacion = 1;
           
        disponibilidad.setIdVehiculo(idVehiculo);
        disponibilidad.setIdTipoEncomienda(idTipoEncomienda);
        disponibilidad.setActualvolumen(volumen);
        disponibilidad.setActualcapacidad(peso);        
        disponibilidad.setSituacion(situacion);
        
        disponibilidadDAO.insertar(disponibilidad);
}

    @Override
    public void insertar(Object obj) throws Exception {
        Disponibilidad c = (Disponibilidad) obj;
        PreparedStatement pst;
        String sql="INSERT INTO disponibilidad (idVehiculo, idTipoEncomienda, actualvolumen, actualcapacidad, situacion, fecharegistro) VALUES ( ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, c.getIdVehiculo());
            pst.setInt(2, c.getIdTipoEncomienda());
            pst.setDouble(3, c.getActualvolumen());
            pst.setDouble(4, c.getActualcapacidad());
            pst.setInt(5, c.getSituacion());
            pst.executeUpdate();            

        } catch (SQLException e) {
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
        Disponibilidad disponibilidad = (Disponibilidad) obj;
        PreparedStatement pst;
        String sql="UPDATE vehiculos set actualvolumen = ?, actualcapacidad = ? WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            
            
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
    public Object BuscarPorId(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
 public List<Disponibilidad> consultarPorIdVehiculo(int idVehiculo) throws Exception {
        List<Disponibilidad> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, idVehiculo, idTipoEncomienda, actualvolumen, actualcapacidad, situacion FROM disponibilidad WHERE estado = 1 AND idVehiculo = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setDouble(1, idVehiculo);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Disponibilidad(
                        rs.getInt("id"),
                        rs.getInt("idVehiculo"),
                        rs.getInt("idTipoEncomienda"),
                        rs.getDouble("actualvolumen"),
                        rs.getDouble("actualcapacidad"),
                        rs.getInt("situacion")
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
}
