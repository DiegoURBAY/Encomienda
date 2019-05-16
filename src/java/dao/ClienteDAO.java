
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidad.Cliente;

public class ClienteDAO extends Conexion implements DAO{
    
 
    @Override
    public void insertar(Object obj) throws Exception {
        Cliente c = (Cliente) obj;
        PreparedStatement pst = null;
        String sql="INSERT INTO clientes (identificador, nombre, email, usuario, contraseña, telefono, nivel, fecharegistro) VALUES(?,?,?,?,?,?,?, CURDATE())";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getIdentificador());
            pst.setString(2, c.getNombre());
            pst.setString(3, c.getEmail());
            pst.setString(4, c.getUsuario());
            pst.setString(5, c.getContraseña());
            pst.setString(6, c.getTelefono());
            pst.setInt(7, c.getNivel());
            pst.executeUpdate();            
                      
        } catch ( SQLException e) {           
        }
        finally{
                this.cerrar();
        }
    }

    @Override
    public void eliminar(Object obj) throws Exception {
        Cliente c = (Cliente) obj;
        PreparedStatement pst;
        String sql="UPDATE clientes set estado = 0 WHERE id = ?";
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
        Cliente c = (Cliente) obj;
        PreparedStatement pst;
        String sql="UPDATE clientes SET identificador=?, nombre=?, email=?, usuario=?, contraseña=?,  telefono=? WHERE id=?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getIdentificador());
            pst.setString(2, c.getNombre());
            pst.setString(3, c.getEmail());
            pst.setString(4, c.getUsuario());
            pst.setString(5, c.getContraseña());
            pst.setString(6, c.getTelefono());
            pst.setInt(7, c.getId());

             pst.executeUpdate();            
                       
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }

    }

    @Override
    public List<Cliente> consultar() throws Exception{
        List<Cliente> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, identificador, nombre, email, usuario, contraseña, telefono, nivel FROM clientes WHERE estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("identificador"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("usuario"),
                        rs.getString("contraseña"),
                        rs.getString("telefono"),
                        rs.getInt("nivel")
                    )
                );
            }
        } catch (SQLException e ) {            
        }
        finally{
            this.cerrar();
        }
        return datos;
    }

    @Override
    public Cliente BuscarPorId(int id) throws Exception{        
           Cliente c = new Cliente();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT * FROM clientes WHERE id =?";
           try {
               this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);                                
               res = pst.executeQuery();               
                if (res.next()) {
                    c.setIdentificador(res.getString("identificador"));
                    c.setNombre(res.getString("nombre"));            
                    c.setEmail(res.getString("email"));
                    c.setUsuario(res.getString("usuario"));
                    c.setContraseña(res.getString("contraseña"));                    
                    c.setTelefono(res.getString("telefono"));
                    c.setNivel(res.getInt("nivel"));
                    c.setFecharegistro(res.getDate("fecharegistro"));
                    c.setId(res.getInt("id"));
                }                   
     
           } catch ( SQLException e ) {
           }
           finally{
               this.cerrar();
           }
           return c;
    }  
    
    
    public Cliente BuscarPorUsuario(String usuario) throws Exception{        
           Cliente c = new Cliente();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT * FROM clientes WHERE usuario =?";
           try {
               this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setString(1,usuario);                                
               res = pst.executeQuery();               
                if (res.next()) {
                    c.setIdentificador(res.getString("identificador"));
                    c.setNombre(res.getString("nombre"));            
                    c.setEmail(res.getString("email"));
                    c.setUsuario(res.getString("usuario"));
                    c.setContraseña(res.getString("contraseña"));                    
                    c.setTelefono(res.getString("telefono"));
                    c.setNivel(res.getInt("nivel"));
                    c.setFecharegistro(res.getDate("fecharegistro"));
                    c.setId(res.getInt("id"));
                }                   
     
           } catch ( SQLException e ) {
               throw  e;
           }
           finally{
               this.cerrar();
           }
           return c;
    }  
        
    public Cliente ConsultarByEmail(String email) throws SQLException{
        
        Cliente c = new Cliente();
        PreparedStatement pst;
        ResultSet res = null;
        String sql = "SELECT id, identificador, nombre, email, usuario, contraseña, telefono, nivel FROM clientes WHERE email=?";

        try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setString(1,email);                                
               res = pst.executeQuery();               
            if (res.next()) {
                    c.setId(res.getInt("id"));
                    c.setIdentificador(res.getString("identificador"));
                    c.setNombre(res.getString("nombre"));            
                    c.setEmail(res.getString("email"));
                    c.setUsuario(res.getString("usuario"));
                    c.setContraseña(res.getString("contraseña"));                    
                    c.setTelefono(res.getString("telefono"));
                    c.setNivel(res.getInt("nivel"));
            }
        } catch (Exception e) {
        }
         return c;
    }
    
    
    public String UsuarioByEmail(String email) throws SQLException{
        String result = null;
        PreparedStatement pst;
        ResultSet rs = null;
        String sql = "SELECT usuario FROM clientes WHERE email=?";
        
        try {
            this.conectar();
            pst = conexion.prepareCall(sql);
            pst.setString(1, email);
            rs = pst.executeQuery();
            if(rs.next()){
                result = rs.getString("usuario");
            }
        }
        catch (Exception e) {            
        }
        return  result;
        
    }
    
    
    public String ContraseñaByEmail(String email) throws SQLException{
        String result = null;
        PreparedStatement pst;
        ResultSet rs = null;
        String sql = "SELECT contra FROM clientes WHERE email=?";
        
        try {
            this.conectar();
            pst = conexion.prepareCall(sql);
            pst.setString(1, email);
            rs = pst.executeQuery();
            if(rs.next()){
                result = rs.getString("contra");
            }
        }
        catch (Exception e) {            
        }
        return  result;
        
    }    
    
    
   public boolean ConsultarRUCDNI(String nombre) throws SQLException{
        PreparedStatement pst;
        ResultSet rs = null;
        String sql = "SELECT * FROM clientes WHERE identificador= ? ";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1,nombre);    
            rs = pst.executeQuery();
        } catch (Exception e) {
        }
         return rs.next();
    }    
   
    public boolean ConsultarEmail(String email) throws SQLException{
    
        PreparedStatement pst;
        ResultSet rs = null;
        String sql = "SELECT email, contraseña FROM clientes WHERE email=?";
        
        try {
            this.conectar();
            pst = conexion.prepareCall(sql);
            pst.setString(1, email);
            rs = pst.executeQuery();  
        }
        catch (Exception e) {            
        }
        return rs.next();
        
    }   
    
    public boolean ConsultarEmailContra(String email, String contraseña) throws SQLException{
    
        PreparedStatement pst;
        ResultSet rs = null;
        String sql = "SELECT email, contraseña FROM clientes WHERE email=? AND contraseña=?";
        
        try {
            this.conectar();
            pst = conexion.prepareCall(sql);
            pst.setString(1, email);
            pst.setString(2, contraseña);
            rs = pst.executeQuery();  
        }
        catch (Exception e) {            
        }
        return rs.next();
        
    }         
    
    public boolean ConsultarUsuario(String usuario) throws SQLException{

        PreparedStatement pst;
        ResultSet rs = null;
        String sql = "SELECT usuario FROM clientes WHERE usuario=?";
        
        try {
            this.conectar();
            pst = conexion.prepareCall(sql);
            pst.setString(1, usuario);
            rs = pst.executeQuery();
        }
        catch (Exception e) {            
        }
        return rs.next();
        
    }    
    
    
}
