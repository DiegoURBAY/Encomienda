package dao;

import entidad.Local;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LocalDAO extends Conexion implements DAO{
    
    public static void main(String[] args) throws Exception {
        Local tipoEncomienda = new Local();
     /*   tipoEncomienda.setId(21);
        tipoEncomienda.setTitulo("paquete");
        tipoEncomienda.setCx("paquete");
        tipoEncomienda.setCy("paquete");
        tipoEncomienda.setDireccion("paquete");
     
        String nombre = "s";
        
        LocalDAO tipoEncomiendaDAO = new LocalDAO();
        List<Local> tipoEncomienda2 = tipoEncomiendaDAO.ConsultarTitulo(nombre);
        for (int i = 0; i < tipoEncomienda2.size(); i++) {
            System.out.println(tipoEncomienda2.get(i).getTitulo());
        }
           
 LocalDAO localDAO = new LocalDAO();

        tipoEncomienda.setTitulo("sobre");
        tipoEncomienda.setCx("sobre");
        tipoEncomienda.setCy("sobre");
        tipoEncomienda.setDireccion("sobre");
        tipoEncomienda.setTelefono("sobre");     
                  try {
              localDAO.insertar(tipoEncomienda);
          } catch (Exception e) {
              throw e;
          } 
        */ 
    }    

    @Override
    public void insertar(Object obj) throws Exception {
        Local local = (Local) obj;
        PreparedStatement pst;
        
        String sql="INSERT INTO locales (titulo, cx, cy, direccion, telefono, idLugar) VALUES(?,?,?,?,?,?)";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, local.getTitulo());
            pst.setString(2, local.getCx());
            pst.setString(3, local.getCy());
            pst.setString(4, local.getDireccion());
            pst.setString(5, local.getTelefono());
            pst.setInt(6, local.getIdLugar());
            pst.executeUpdate();                        
            
        }
        catch ( SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }        
    }

    @Override
    public void eliminar(Object obj) throws Exception {
        Local local = (Local) obj;
        PreparedStatement pst;
        String sql="UPDATE locales SET estado = 0 WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, local.getId());
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
        Local local = (Local) obj;
        PreparedStatement pst;        
        String sql = "UPDATE locales SET titulo=?, cx=?, cy=?, direccion = ?, telefono= ?, idLugar=? WHERE id=? AND estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, local.getTitulo());
            pst.setString(2, local.getCx());            
            pst.setString(3, local.getCy());            
            pst.setString(4, local.getDireccion());            
            pst.setString(5, local.getTelefono());
            pst.setInt(6, local.getIdLugar());
            pst.setInt(7, local.getId()); 
            pst.executeUpdate();            
        } catch ( SQLException e) {   
             throw e;
        }
        finally{
            this.cerrar();            
        }
    }

    @Override
    public List<Local> consultar() throws Exception  {
        List<Local> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT * FROM locales WHERE estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();       
            while(rs.next()){
                datos.add(new Local(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("cx"),
                        rs.getString("cy"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getInt("idLugar")
                    )                    
                );
            }
        } catch (SQLException e ) {
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
    
    
    public  List<Local> ConsultarTitulo(String find) throws Exception {
        List<Local> titulo = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM locales WHERE estado = 1 AND titulo LIKE ?";
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, find+"%"); 
            rs = pst.executeQuery();
            while (rs.next()) {
                titulo.add(new Local(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("cx"),
                        rs.getString("cy"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),                        
                        rs.getInt("idLugar")
                    )
                );                
            }
        } catch (SQLException e) {
            throw e;
        }
        finally{
           this.cerrar();
        }        
        return titulo;
    }        
    
    
    public List<Local> ConsultarPorDirección(String find) throws SQLException{
        List<Local> local = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs = null;
        String sql = "SELECT * FROM locales WHERE nombre = ? AND estado = 1";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, find);      
            rs = pst.executeQuery();
            while (rs.next()) {
                local.add(new Local(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("cx"),
                        rs.getString("cy"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),                        
                        rs.getInt("idLugar")
                    )
                );                
            }      
        } catch (SQLException e) {
            throw e;
        }
         return local;
    }            
    
}
