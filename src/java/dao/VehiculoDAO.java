
package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidad.Vehiculo;

public class VehiculoDAO extends Conexion implements DAO{    

    @Override
    public void insertar(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        String sql = "SELECT id, placa, capacidad, idEncomienda	FROM vehiculos WHERE estado = 1 AND capacidad > ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setDouble(1, peso);
            rs = pst.executeQuery();
                 if (rs.next()) {          
                    vehiculo.setId(rs.getInt("id"));
                    vehiculo.setPlaca(rs.getString("placa"));
                    vehiculo.setCapacidad(rs.getDouble("capacidad"));
                    vehiculo.setIdencomienda(rs.getInt("idEncomienda"));
                }  
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }
        return vehiculo;     
    }    

                
}
