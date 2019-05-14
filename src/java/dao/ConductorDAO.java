
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidad.Conductor;

public class ConductorDAO extends Conexion implements DAO{    

    @Override
    public void insertar(Object obj) throws Exception{
        Conductor c = (Conductor) obj;
        PreparedStatement pst;
        String sql="INSERT INTO conductores ( nom, ape, dni, lic, email, tel, direc, distr, id_tipo) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getApe());
            pst.setString(3, c.getDni());
            pst.setString(4, c.getLic());
            pst.setString(5, c.getEmail());
            pst.setString(6, c.getTel());
            pst.setString(7, c.getDirec());
            pst.setString(8, c.getDistr());
            pst.setString(9, c.getTipo());
            pst.executeUpdate();            

        } catch (SQLException e) {
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
        }
        finally{
            this.cerrar();
        }   
    }

    @Override
    public void modificar(Object obj) throws Exception{
        Conductor c = (Conductor) obj;
        PreparedStatement pst;
        String sql="UPDATE conductores SET nom=?, ape=?, dni=?, lic=?, email=?, tel=?, direc=?, distr=?, id_tipo=? WHERE id=?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getApe());
            pst.setString(3, c.getDni());
            pst.setString(4, c.getLic());
            pst.setString(5, c.getEmail());
            pst.setString(6, c.getTel());
            pst.setString(7, c.getDirec());
            pst.setString(8, c.getDistr());
            pst.setString(9, c.getTipo());            
            pst.setInt(10, c.getId());
            pst.executeUpdate();      
            
        } catch (SQLException e) {
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
           String sql = "SELECT c.id, c.nom, c.ape, c.dni, c.lic, c.email, c.tel, c.direc, c.distr, tc.nom FROM conductores c, tiposconductores tc WHERE c.id_tipo = tc.id AND c.id=? AND c.estado = 1";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);                 
               res = pst.executeQuery();                                    
                if (res.next()) {                                     
                    c.setNom(res.getString("c.nom"));            
                    c.setApe(res.getString("c.ape"));      
                    c.setDni(res.getString("c.dni"));
                    c.setLic(res.getString("c.lic"));                       
                    c.setEmail(res.getString("c.email"));
                    c.setTel(res.getString("c.tel"));                    
                    c.setDirec(res.getString("c.direc"));
                    c.setDistr(res.getString("c.distr"));
                    c.setTipo(res.getString("tc.nom"));
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
        String sql = "SELECT c.id, c.nom, c.ape, c.dni, c.lic, c.email, c.tel, c.direc, c.distr, tc.nom FROM conductores c, tiposconductores tc WHERE c.id_tipo = tc.id AND c.estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Conductor(
                        rs.getInt("c.id"),
                        rs.getString("c.nom"),
                        rs.getString("c.ape"),
                        rs.getString("c.dni"),
                        rs.getString("c.lic"),                                                                        
                        rs.getString("c.email"),
                        rs.getString("c.tel"),
                        rs.getString("c.direc"),
                        rs.getString("c.distr"),
                        rs.getString("tc.nom"))
                );
            }
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
        return datos;
    }

    public boolean ConsultarNombre(String nom) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
