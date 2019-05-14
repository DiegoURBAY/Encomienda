
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
    
    
public static void main(String[] args) throws Exception {
        
        String lugar = "Lima";
    
        Lugar tipoEncomienda = new Lugar();
        

        
        
        LugarDAO tipoEncomiendaDAO = new LugarDAO();
     Lugar departamento =   tipoEncomiendaDAO.ConsultarPorNombre(lugar);
        
       System.out.print(departamento.getId() +","+ departamento.getNombre());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List consultar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Lugar BuscarPorId(int id) throws Exception {
           Lugar c = new Lugar();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT * FROM lugares WHERE id =?";
           try {
               this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);                                
               res = pst.executeQuery();               
                if (res.next()) {
                    c.setNombre(res.getString("nombre"));            
                    c.setId(res.getInt("id"));
                }                   
     
           } catch ( SQLException e ) {
               throw e;
           }
           finally{
               this.cerrar();
           }
           return c;
    }  
    
    public List<Lugar> ConsultarDepartamento(String find) throws SQLException{
         List<Lugar> lugares = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String sql = "SELECT id, nombre FROM lugares WHERE nombre LIKE ?";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, find+"%");      
            rs = pst.executeQuery();
            while(rs.next()){
                lugares.add(new Lugar(
                    rs.getInt("id"),
                    rs.getString("nombre")
                    )                 
                );            
            }               
        } catch (SQLException e) {
            throw e;
        }
         return lugares;
    }        
      
    
    public Lugar ConsultarPorNombre(String find) throws SQLException{
        Lugar lugar = new Lugar();
        PreparedStatement pst;
        ResultSet rs = null;
        String sql = "SELECT id, nombre FROM lugares WHERE nombre = ?";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, find);      
            rs = pst.executeQuery();
            if (rs.next()) {
                  lugar.setId(rs.getInt("id"));
                  lugar.setNombre(rs.getString("nombre"));
            }           
        } catch (SQLException e) {
            throw e;
        }
         return lugar;
    }      
    
      
    
}
