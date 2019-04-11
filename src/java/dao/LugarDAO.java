
package dao;

import dao.Conexion;
import dao.DAO;
import entidad.Encomienda;
import entidad.Lugar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LugarDAO  extends Conexion implements DAO{ 

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
    
    public ArrayList<String> ConsultarOrigenDestino(String find) throws SQLException{
         ArrayList<String> lugares = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String sql = "SELECT nombre FROM lugares WHERE nombre LIKE ?";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, find+"%");      
            rs = pst.executeQuery();
            while(rs.next()){
                lugares.add(
                        rs.getString("nombre")
                 
                );            
            }               
        } catch (Exception e) {
        }
         return lugares;
    }        
      
    
}
