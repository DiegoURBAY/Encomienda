
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
        int idTipoEncomienda = 210;
        double peso = 202.00;
        double volumen = 2.00;        
        int situacion = 1;
           
        disponibilidad.setIdVehiculo(idVehiculo);
        disponibilidad.setIdTipoEncomienda(idTipoEncomienda);
        disponibilidad.setIdConductor(1);
        disponibilidad.setIdAyudante(2);
        disponibilidad.setActualvolumen(volumen);
        disponibilidad.setActualcapacidad(peso);        
        disponibilidad.setSituacion(situacion);
        
        disponibilidadDAO.insertar(disponibilidad);
}

    @Override
    public void insertar(Object obj) throws Exception {
        Disponibilidad c = (Disponibilidad) obj;
        PreparedStatement pst;
        String sql="INSERT INTO disponibilidad (idVehiculo, idTipoEncomienda, idConductor, idAyudante, actualvolumen, actualcapacidad, situacion, fecharegistro) VALUES ( ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, c.getIdVehiculo());
            pst.setInt(2, c.getIdTipoEncomienda());
            pst.setInt(3, c.getIdConductor());
            pst.setInt(4, c.getIdAyudante());
            pst.setDouble(5, c.getActualvolumen());
            pst.setDouble(6, c.getActualcapacidad());
            pst.setInt(7, c.getSituacion());
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
        Disponibilidad disponibilidad = (Disponibilidad) obj;
        PreparedStatement pst;
        String sql="UPDATE disponibilidad set estado = 0 WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, disponibilidad.getId());
            pst.executeUpdate();            
                      
        } catch (SQLException e) {
            throw e;
        }
        finally{
                this.cerrar();
        }            
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
    public List<Disponibilidad> consultar() throws Exception {
        List<Disponibilidad> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, idVehiculo, idTipoEncomienda, idConductor, idAyudante, actualvolumen, actualcapacidad, situacion, estado FROM disponibilidad WHERE estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Disponibilidad(
                        rs.getInt("id"),
                        rs.getInt("idVehiculo"),
                        rs.getInt("idTipoEncomienda"),
                        rs.getInt("idConductor"),
                        rs.getInt("idAyudante"),                            
                        rs.getDouble("actualvolumen"),
                        rs.getDouble("actualcapacidad"),                    
                        rs.getInt("situacion"),
                        rs.getInt("estado")
                    )
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }
        return datos;     
         
    }

    @Override
    public Object BuscarPorId(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
 public List<Disponibilidad> consultarPorIdVehiculo(int idVehiculo) throws Exception {
        List<Disponibilidad> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, idVehiculo, idTipoEncomienda, idConductor, idAyudante, actualvolumen, actualcapacidad, situacion, estado FROM disponibilidad WHERE estado = 1 AND idVehiculo = ?";
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
                        rs.getInt("idConductor"),
                        rs.getInt("idAyudante"),                            
                        rs.getDouble("actualvolumen"),
                        rs.getDouble("actualcapacidad"),                    
                        rs.getInt("situacion"),
                        rs.getInt("estado")
                    )
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }
        return datos;     
    }    
 
  public Disponibilidad consultarPorIdTipoEncomienta(int idTipoEncomienda) throws Exception {
        Disponibilidad disponibilidad = new Disponibilidad();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, idVehiculo, idTipoEncomienda, idConductor, idAyudante, actualvolumen, actualcapacidad, situacion, estado FROM disponibilidad WHERE estado = 1 AND idTipoEncomienda = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setDouble(1, idTipoEncomienda);
            rs = pst.executeQuery();
            if(rs.next()){

                disponibilidad.setId(rs.getInt("id"));
                disponibilidad.setIdVehiculo(rs.getInt("idVehiculo"));
                disponibilidad.setIdTipoEncomienda(rs.getInt("idTipoEncomienda"));
                disponibilidad.setIdConductor(rs.getInt("idConductor"));
                disponibilidad.setIdAyudante(rs.getInt("idAyudante"));                        
                disponibilidad.setActualvolumen(rs.getInt("actualvolumen"));
                disponibilidad.setActualcapacidad(rs.getInt("actualcapacidad"));
                disponibilidad.setSituacion(rs.getInt("situacion"));
                disponibilidad.setEstado(rs.getInt("estado"));                   

            }
        } catch (SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }
        return disponibilidad;     
    }  
  
    public void eliminarPorTipoEncomienda(Object obj) throws Exception {
        Disponibilidad disponibilidad = (Disponibilidad) obj;
        PreparedStatement pst;
        String sql="UPDATE disponibilidad set estado = 0 WHERE idTipoEncomienda = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, disponibilidad.getIdTipoEncomienda());
            pst.executeUpdate();            
                      
        } catch (SQLException e) {
            throw e;
        }
        finally{
                this.cerrar();
        }            
    } 
}
