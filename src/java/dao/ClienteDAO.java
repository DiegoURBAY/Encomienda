
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
        String sql="INSERT INTO clientes (identificador, nombre, contraseña, email, telefono, fecharegistro) VALUES(?,?,?,?,?, CURDATE())";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getIdentificador());
            pst.setString(2, c.getNombre());
            pst.setString(3, c.getContraseña());
            pst.setString(4, c.getEmail());
            pst.setString(5, c.getTelefono());
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
        String sql="UPDATE clientes SET identificador=?, nombre=?, contraseña=?, email=?, telefono=? WHERE id=?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getIdentificador());
            pst.setString(2, c.getNombre());
            pst.setString(3, c.getContraseña());
            pst.setString(4, c.getEmail());
            pst.setString(5, c.getTelefono());
            pst.setInt(6, c.getId());

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
        String sql = "SELECT id, identificador, nombre, contraseña, email, telefono, fecharegistro FROM clientes WHERE estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("identificador"),
                        rs.getString("nombre"),
                        rs.getString("contraseña"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecharegistro")
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
                    c.setContraseña(res.getString("contraseña"));
                    c.setEmail(res.getString("email"));
                    c.setTelefono(res.getString("telefono"));
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

    public boolean ConsultarRUCDNI(String nombre) throws SQLException{
        PreparedStatement pst;
        ResultSet res = null;
        String sql = "SELECT * FROM clientes WHERE ruc_dni='"+nombre+"'";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            res = pst.executeQuery();
        } catch (Exception e) {
        }
         return res.next();
    }     
    
    
    
}
