
package dao;

import entidad.TipoUsuario;
import java.util.List;
import java.sql.*;
import java.util.*;
import entidad.Usuario;

public class UsuarioDAO extends Conexion implements DAO{

    
    @Override
    public List<Usuario> consultar() throws Exception  {
        List<Usuario> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT u.id, u.nom, u.pass, u.email, tp.nom FROM usuarios u, tiposusuarios tp WHERE u.id_nivel = tp.id AND u.estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();       
            while(rs.next()){
                datos.add(new Usuario(
                        rs.getInt("u.id"),
                        rs.getString("u.nom"),
                        rs.getString("u.pass"),
                        rs.getString("u.email"),
                        rs.getString("tp.nom"))                    
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
    
    public List<Usuario> consultar2() throws Exception  {
        List<Usuario> datos = new ArrayList<>();
        TipoUsuario tp = new TipoUsuario();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT u.id, u.nom, u.pass, u.email, tp.nom FROM usuarios u, tiposusuarios tp WHERE u.id_nivel = tp.id AND u.estado = 1";
        String sql2 = "SELECT tp.id, tp.nom FROM usuarios u, tiposusuarios tp WHERE u.id_nivel = tp.id AND u.estado = 1 AND u.id = 2";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();       
            while(rs.next()){
                datos.add(new Usuario(
                        rs.getInt("u.id"),
                        rs.getString("u.nom"),
                        rs.getString("u.pass"),
                        rs.getString("u.email"),
                        new TipoUsuario(1, sql2))
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
    public void insertar(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public Object BuscarPorId(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ConsultarNombre(String nom) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
