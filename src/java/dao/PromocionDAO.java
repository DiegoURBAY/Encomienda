
package dao;

import entidad.Promocion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PromocionDAO extends Conexion implements DAO{ 
    
    public static void main(String[] args) throws Exception {
        Promocion promocion = new Promocion();
        PromocionDAO promocionDAO = new PromocionDAO();
        promocion.setId(1);
        promocion.setPorcentaje(90999999);
        
        
        Promocion promocion_buscada = promocionDAO.BuscarPorId(2);
        System.out.println(promocion_buscada.getId() + ", "+ promocion_buscada.getPorcentaje());
    }

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
        Promocion promocion = (Promocion) obj;
        PreparedStatement pst;
        String sql="UPDATE promociones SET porcentaje=? WHERE id=?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setDouble(1, promocion.getPorcentaje());
            pst.setInt(2, promocion.getId());

             pst.executeUpdate();            
                       
        } catch (SQLException e) {
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
    public Promocion BuscarPorId(int id) throws Exception {
           Promocion promocion = new Promocion();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT * FROM promociones WHERE id =?";
           try {
               this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);                                
               res = pst.executeQuery();               
                if (res.next()) {
                    promocion.setPorcentaje(res.getDouble("porcentaje"));            
                    promocion.setId(res.getInt("id"));
                }                   
     
           } catch ( SQLException e ) {
               throw e;
           }
           finally{
               this.cerrar();
           }
           return promocion;
    }
    
}
