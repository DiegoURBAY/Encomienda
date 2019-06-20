
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    protected Connection conexion;
    
    
    //private String driver = "org.gjt.mm.mysql.Driver";
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3307/encomienda";

    private String user = "root";
    private String password = "";
        
    public void conectar() throws ClassNotFoundException, SQLException{
        conexion = null;
        try{
            Class.forName(driver);        
            conexion = DriverManager.getConnection(url, user, password);
            
            if(conexion != null){
                System.out.println("Conexion establecida..");
            }
                            
        }catch(ClassNotFoundException | SQLException e){
            throw e;            
        }
    }
        
    public void cerrar() throws SQLException{
        if(conexion != null){
            if(!conexion.isClosed()){
                conexion.close();
            }
        }
    }
}
