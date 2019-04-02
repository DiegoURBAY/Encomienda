
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
            
    public int validar(String usuario, String contra){
        int nivel = 0;
        try{
            this.conectar();
            sql = "SELECT id_nivel FROM usuarios WHERE nom=? AND pass =? AND estado = ?";
            pst =  (PreparedStatement) conexion.prepareStatement(sql);
            pst.setString(1, usuario);
            pst.setString(2, contra);
            pst.setInt(3, 1);
            rs = pst.executeQuery();
            while(rs.next()){
                nivel =rs.getInt("id_nivel");
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
