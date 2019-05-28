
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidad.Cliente;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;



public class ClienteDAO extends Conexion implements DAO{
    
    public static void main(String[] args) throws Exception {
      //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.BuscarPorId(28);
        cliente.getFechapromo();
        
        Calendar ultima_promocion = Calendar.getInstance();
        ultima_promocion.setTime( cliente.getFechapromo());
        
        Calendar fecha_actual = Calendar.getInstance();

        int ultima_promocion_mes = ultima_promocion.get(Calendar.MONTH)+1;
        int ultima_promocion_año = ultima_promocion.get(Calendar.YEAR);
        int mes_actual = fecha_actual.get(Calendar.MONTH)+1;
        int año_actual = fecha_actual.get(Calendar.YEAR);

        System.out.println(ultima_promocion_mes);
        System.out.println(ultima_promocion_año);
        System.out.println(mes_actual);
        System.out.println(año_actual);        

        int estado = 0;

        if(estado == 0){
            if(año_actual > ultima_promocion_año){
                 estado = 1;
            }
            else if(mes_actual > ultima_promocion_mes){
                 estado = 1;
            }  
        }
           System.out.println(estado);
    }


 
    
    @Override
    public void insertar(Object obj) throws Exception {
        Cliente c = (Cliente) obj;
        PreparedStatement pst = null;
        String sql="INSERT INTO clientes (identificador, nombre, email, usuario, contraseña, telefono, nivel, fechaPromo, fecharegistro) VALUES(?,?,?,?,?,?,?, CURDATE(), CURDATE())";
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
          //  pst.setDate(8, c.getFecharegistro());
            pst.executeUpdate();            
                      
        } catch ( SQLException e) {           
            throw e;
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
        String sql="UPDATE clientes SET identificador=?, nombre=?, email=?, usuario=?, contraseña=?, telefono=?, promocion=? WHERE id=?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getIdentificador());
            pst.setString(2, c.getNombre());
            pst.setString(3, c.getEmail());
            pst.setString(4, c.getUsuario());
            pst.setString(5, c.getContraseña());
            pst.setString(6, c.getTelefono());
            pst.setInt(7, c.getPromocion());
            pst.setInt(8, c.getId());

             pst.executeUpdate();            
                       
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }

    }
    
    public void actualizarPromo(Object obj) throws Exception{
        Cliente c = (Cliente) obj;
        PreparedStatement pst;
        String sql="UPDATE clientes SET fechaPromo=CURDATE() WHERE id=?";
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
                    c.setPromocion(res.getInt("promocion"));
                    c.setFechapromo(res.getDate("fechaPromo"));
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
                    c.setPromocion(res.getInt("promocion"));
                    c.setFechapromo(res.getDate("fechaPromo"));
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
