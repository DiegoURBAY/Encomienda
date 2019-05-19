
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidad.Conductor;

public class ConductorDAO extends Conexion implements DAO{    
    
    public static void main(String[] args) throws Exception {
        Conductor conductor = new Conductor();
        ConductorDAO conductorDAO = new ConductorDAO();
        List<Conductor> conductors = conductorDAO.consultar();
        
   /*     conductor.setId(1);
        conductor.setDni("1111");
        conductor.setNom("QWa");
        conductor.setApe("QWEa");
        conductor.setLic("QWEa");
        conductor.setEmail("QWEa");
        conductor.setTel("QWEa");     
        
     */ 
           for (int i = 0; i < conductors.size(); i++) {
                   System.out.println(conductors.get(i).getNom());
        System.out.println(conductors.get(i).getApe());
        System.out.println(conductors.get(i).getLic());
        }


      //  conductorDAO.modificar(conductor);
        
 
    }
    
    @Override
    public void insertar(Object obj) throws Exception{
        Conductor c = (Conductor) obj;
        PreparedStatement pst;
        String sql="INSERT INTO conductores (dni, nom, ape, lic, email, tel, fechatime) VALUES(?,?,?,?,?,?, CURRENT_TIMESTAMP)";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getDni());
            pst.setString(2, c.getNom());
            pst.setString(3, c.getApe());
            pst.setString(4, c.getLic());
            pst.setString(5, c.getEmail());
            pst.setString(6, c.getTel());
            pst.executeUpdate();            

        } catch (SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }   
    }

    @Override
    public void eliminar(Object obj) throws Exception{
        Conductor c = (Conductor) obj;
        PreparedStatement pst;
        String sql="UPDATE conductores SET estado = 0 WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, c.getId());
            pst.executeUpdate();            
          
        } catch (SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }   
    }
    
    public void eliminarDisponiblidad(Object obj) throws Exception{
        Conductor c = (Conductor) obj;
        PreparedStatement pst;
        String sql="UPDATE conductores SET disp = 0 WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, c.getId());
            pst.executeUpdate();            
          
        } catch (SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }   
    }    

    @Override
    public void modificar(Object obj) throws Exception{
        Conductor c = (Conductor) obj;
        PreparedStatement pst;
        String sql="UPDATE conductores SET dni=?, nom=?, ape=?, lic=?, email=?, tel=? WHERE id=?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getDni());
            pst.setString(2, c.getNom());
            pst.setString(3, c.getApe());
            pst.setString(4, c.getLic());
            pst.setString(5, c.getEmail());
            pst.setString(6, c.getTel());          
            pst.setInt(7, c.getId());
            pst.executeUpdate();                  
        } catch (SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }   
    }

    @Override
    public Conductor BuscarPorId(int id) throws Exception{
           Conductor c = new Conductor();
           PreparedStatement pst;
           ResultSet res;
          // String sql = "SELECT c.id, c.nom, c.ape, c.dni, c.lic, c.email, c.tel, c.direc, c.distr, tc.nom FROM conductores c, tiposconductores tc WHERE c.id_tipo = tc.id AND c.id=? AND c.estado = 1";
          String sql = "SELECT * FROM conductores WHERE estado = 1 AND id = ?";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);                 
               res = pst.executeQuery();                                    
                if (res.next()) {                                     
                    c.setId(res.getInt("id"));
                    c.setDni(res.getString("dni"));
                    c.setNom(res.getString("nom"));            
                    c.setApe(res.getString("ape"));                         
                    c.setLic(res.getString("lic"));                       
                    c.setEmail(res.getString("email"));
                    c.setTel(res.getString("tel"));                    
                    c.setId(res.getInt("c.id"));
                }                   
     
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
           return c;
    }

    @Override
    public List<Conductor> consultar() throws Exception{
        List<Conductor> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
       // String sql = "SELECT c.id, c.nom, c.ape, c.dni, c.lic, c.email, c.tel, c.direc, c.distr, tc.nom FROM conductores c, tiposconductores tc WHERE c.id_tipo = tc.id AND c.estado = 1";
       String sql = "SELECT * FROM conductores WHERE estado = 1 ";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Conductor(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nom"),
                        rs.getString("ape"),                        
                        rs.getString("lic"),                                                                        
                        rs.getString("email"),
                        rs.getString("tel"),
                        rs.getInt("disp")
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


    public boolean ConsultarDNI(String nombre) throws SQLException{
        PreparedStatement pst;
        ResultSet res = null;
        String sql = "SELECT * FROM conductores WHERE dni='"+nombre+"'";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            res = pst.executeQuery();
        } catch (Exception e) {
        }
         return res.next();
    }       

    public boolean ConsultarLicencia(String licencia) throws SQLException{
        PreparedStatement pst;
        ResultSet res = null;
        String sql = "SELECT * FROM conductores WHERE lic='"+licencia+"'";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            res = pst.executeQuery();
        } catch (Exception e) {
        }
         return res.next();
    }           
    
    public boolean ConsultarEmail(String email) throws SQLException{
        PreparedStatement pst;
        ResultSet res = null;
        String sql = "SELECT * FROM conductores WHERE email='"+email+"'";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            res = pst.executeQuery();
        } catch (Exception e) {
        }
         return res.next();
    }      
}
