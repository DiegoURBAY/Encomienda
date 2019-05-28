
package dao;

import entidad.Calificacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CalificacionDAO extends Conexion implements DAO{

    
    
    public static void main(String[] args) throws Exception {
      //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        CalificacionDAO calificacionDAO = new CalificacionDAO();
        Calificacion calificacion = new Calificacion();
        calificacion.setEncomienda(1);
        calificacion.setPerdida(1);
        calificacion.setDemora(1);
        calificacion.setDaño(1);
        calificacion.setComentario("QWEQEQWE");
        
        
        List<Calificacion> calificacionList = calificacionDAO.consultar();
        
        int existe = 0;
        for (int i = 0; i < calificacionList.size(); i++) {
            if(calificacionList.get(i).getEncomienda() == 1421){
                existe = 1;
            }
            System.out.println(calificacionList.get(i).getEncomienda());
        }
        
         System.out.println(existe);
       // calificacionDAO.insertar(calificacion);
    }    
    
    @Override
    public void insertar(Object obj) throws Exception {
       Calificacion calificacion = (Calificacion) obj;
        PreparedStatement pst = null;
        String sql="INSERT INTO calificacion (idEncomienda, perdida, demora, daño, comentario) VALUES(?,?,?,?,?)";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, calificacion.getEncomienda());
            pst.setInt(2, calificacion.getPerdida());
            pst.setInt(3, calificacion.getDemora());
            pst.setInt(4, calificacion.getDaño());           
            pst.setString(5, calificacion.getComentario());           
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List consultar() throws Exception {
        List<Calificacion> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT * FROM calificacion WHERE estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Calificacion(
                        rs.getInt("id"),
                        rs.getInt("idEncomienda"),
                        rs.getInt("perdida"),
                        rs.getInt("demora"),
                        rs.getInt("daño"),
                        rs.getString("comentario")
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
           Calificacion calificacion = new Calificacion();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT * FROM calificacion WHERE id =?";
           try {
               this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);                                
               res = pst.executeQuery();               
                if (res.next()) {

                    calificacion.setEncomienda(res.getInt("idEncomienda"));
                    calificacion.setPerdida(res.getInt("perdida"));
                    calificacion.setDemora(res.getInt("demora"));
                    calificacion.setDaño(res.getInt("daño"));
                    calificacion.setComentario(res.getString("comentario"));
                    calificacion.setId(res.getInt("id"));
                    
                }                   
     
           } catch ( SQLException e ) {
           }
           finally{
               this.cerrar();
           }
           return calificacion;
    }
    
}
