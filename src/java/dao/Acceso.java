
package dao;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Acceso extends Conexion{

    String sql="";
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public Acceso() {
    }
            
    public int validar(String email, String contraseña){
        int nivel = 0;
        try{
            this.conectar();
            sql = "SELECT email, contraseña  FROM clientes WHERE email=? AND contraseña =? AND estado = ?";
            pst =  (PreparedStatement) conexion.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, contraseña);
            pst.setInt(3, 1);
            rs = pst.executeQuery();
            while(rs.next()){
                nivel = 1;
            }
            this.cerrar();
            rs.close();

        }
        catch (SQLException e){
            System.err.println("Algo ha salido mal en autentificar "+e);
        }
        finally{
            try {
                this.cerrar();
            } catch (SQLException ex) {
                Logger.getLogger(Acceso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nivel;
    }
}
