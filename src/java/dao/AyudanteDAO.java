package dao;

import java.sql.*;
import java.util.*;
import entidad.Ayudante;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class AyudanteDAO extends Conexion implements DAO{
    
    @Override
    public void insertar(Object obj) throws Exception{
        Ayudante ayudante = (Ayudante) obj;
        PreparedStatement pst;
        String sql="INSERT INTO ayudantes (nom, ape, dni, email, tel, direc, distr) VALUES(?,?,?,?,?,?,?)";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, ayudante.getNom());            
            pst.setString(2, ayudante.getApe());
            pst.setString(3, ayudante.getDni());
            pst.setString(4, ayudante.getEmail());            
            pst.setString(5, ayudante.getTel());            
            pst.setString(6, ayudante.getDirec());
            pst.setString(7, ayudante.getDistr());            
            pst.executeUpdate();           
            
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
    }

    @Override
    public void eliminar(Object obj) throws Exception{
        Ayudante ayudante = (Ayudante) obj;
        PreparedStatement pst;
        String sql="UPDATE ayudantes set estado = 0 WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, ayudante.getId());
            pst.executeUpdate();         
            
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
    }

    @Override
    public void modificar(Object obj) throws Exception{
        Ayudante ayudante = (Ayudante) obj;
        PreparedStatement pst;
        String sql="UPDATE ayudantes SET dni=?, nom=?, ape=?, direc=?, tel=?, email=? WHERE id=?";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, ayudante.getDni());
            pst.setString(2, ayudante.getNom());
            pst.setString(3, ayudante.getApe());
            pst.setString(4, ayudante.getDirec());
            pst.setString(5, ayudante.getTel());
            pst.setString(6, ayudante.getEmail());            
            pst.setInt(7, ayudante.getId());
            pst.executeUpdate();       
            
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
    }

    @Override
    public Ayudante BuscarPorId(int id) throws Exception{
           Ayudante ayudante = new Ayudante();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT * FROM ayudantes WHERE id = ?";
           try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);  
               res = pst.executeQuery();                                    
                if (res.next()) {
                    ayudante.setNom(res.getString("nom"));            
                    ayudante.setApe(res.getString("ape"));      
                    ayudante.setDni(res.getString("dni"));    
                    ayudante.setEmail(res.getString("email"));
                    ayudante.setTel(res.getString("tel"));    
                    ayudante.setDirec(res.getString("direc"));                                                                           
                    ayudante.setDistr(res.getString("distr"));                    
                    ayudante.setId(res.getInt("id"));
                }                   
     
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
           return ayudante;
    }

    @Override
    public List<Ayudante> consultar() throws Exception{
        List<Ayudante> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT * FROM ayudantes WHERE estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Ayudante(
                        rs.getInt("id"),                       
                        rs.getString("nom"),
                        rs.getString("ape"),
                        rs.getString("dni"),
                        rs.getString("email"),                        
                        rs.getString("tel"),
                        rs.getString("direc"),
                        rs.getString("distr"))
                        
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
        String sql = "SELECT * FROM ayudantes WHERE dni='"+nombre+"'";

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
        String sql = "SELECT * FROM ayudantes WHERE email='"+email+"'";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            res = pst.executeQuery();
        } catch (Exception e) {
        }
         return res.next();
    }     
    
    public static void main(String[] args) throws Exception {
                try {
            URL url = new URL("http://stackoverflow.com");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);
            }
}
        } catch (IOException e) {
            throw e;
        }
    }
    
        public void HOLA () throws IOException{
        

    }
}
