
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidad.Encomienda;
import entidad.TipoEncomienda;

public class EncomiendaDAO extends Conexion implements DAO{ 

    /*
        public static void main(String[] args) throws Exception {
        Encomienda encomienda = new Encomienda();
        encomienda.setOrigen("aaa");
        encomienda.setDestino("aa");
        encomienda.setIdCliente(23);
        
        EncomiendaDAO encomiendaDAO = new  EncomiendaDAO();
        encomiendaDAO.insertar(encomienda);
        
        //se necesita traer el id de encomienda que se acaba de crear
        //se usará el idCliente??
        encomienda = encomiendaDAO.getUltimoEncomiendaByIdCliente(23);
        
        encomienda.getId();
  
        TipoEncomienda tipoEncomienda = new TipoEncomienda();
        tipoEncomienda.setTipo("sobre");
        tipoEncomienda.setAltura(0);
        tipoEncomienda.setAnchura(0);
        tipoEncomienda.setLargo(0);
        tipoEncomienda.setCantidad(11111);
        tipoEncomienda.setPeso(11111);
        tipoEncomienda.setPrecio(1111);
        tipoEncomienda.setIdEncomienda(encomienda.getId());
        
        TipoEncomiendaDAO tipoEncomiendaDAO = new TipoEncomiendaDAO();
        tipoEncomiendaDAO.insertar(tipoEncomienda);
        
    }
    */
    @Override
    public void insertar(Object obj) throws Exception {
        Encomienda c = (Encomienda) obj;
        PreparedStatement pst;
        String sql="INSERT INTO encomiendas ( origen, destino, idCliente, fecharegistro) VALUES (?,?,?, CURDATE())";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getOrigen());
            pst.setString(2, c.getDestino());            
            pst.setInt(3, c.getIdCliente());
            pst.executeUpdate();            

        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }          
    }

    @Override
    public void eliminar(Object obj) throws Exception {
        Encomienda c = (Encomienda) obj;
        PreparedStatement pst;
        String sql="UPDATE encomiendas SET estado = 0 WHERE id = ?";
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
    public void modificar(Object obj) throws Exception {
        Encomienda c = (Encomienda) obj;
        PreparedStatement pst;
        String sql="UPDATE encomiendas SET origen=?, destino=?, envio=?, llegada=?, idCliente=? WHERE id=?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, c.getOrigen());
            pst.setString(2, c.getDestino());            
            pst.setDate(3, c.getEnvio());
            pst.setDate(4, c.getLlegada());
            pst.setInt(5, c.getIdCliente());
            pst.setInt(6, c.getId());
            pst.executeUpdate();      
            
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }     
    }

    @Override
    public List consultar() throws Exception {
        List<Encomienda> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, origen, destino, envio, llegada, idCliente FROM encomiendas WHERE estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Encomienda(
                        rs.getInt("id"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getDate("envio"),
                        rs.getDate("llegada"),                                              
                        rs.getInt("idCliente")
                        )
                );
            }
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
        return datos;
    }
    
    public List<Encomienda> consultar(int idCliente) throws Exception {
        List<Encomienda> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, origen, destino, envio, llegada, idCliente FROM encomiendas WHERE estado = 1 AND idCliente = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, idCliente);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Encomienda(
                        rs.getInt("id"),
                        rs.getString("origen"),
                        rs.getString("destino"),
                        rs.getDate("envio"),
                        rs.getDate("llegada"),                                              
                        rs.getInt("idCliente")
                        )
                );            
            }           
            
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
        return datos;
    }    

    public int consultarClienteIdPorEncomiendaId(int idEncomienda) throws Exception {
        int idCliente = 0;
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, origen, destino, envio, llegada, idCliente FROM encomiendas WHERE estado = 1 AND id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, idCliente);
            rs = pst.executeQuery();
            if(rs.next()){
                idCliente = rs.getInt("idCliente");
            }
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
        return idCliente;
    }        
    
    @Override
    public Encomienda BuscarPorId(int id) throws Exception {
           Encomienda c = new Encomienda();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT id, origen, destino, envio, llegada, idCliente FROM encomiendas WHERE estado = 1 AND id = ?";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);                 
               res = pst.executeQuery();                                    
                if (res.next()) {          
                    c.setId(res.getInt("id"));
                    c.setOrigen(res.getString("origen"));            
                    c.setDestino(res.getString("destino"));                               
                    c.setEnvio(res.getDate("envio"));   
                    c.setLlegada(res.getDate("llegada")); 
                    c.setIdCliente(res.getInt("idCliente"));
                }                   
     
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
           return c;        
    }
    
    
    public Encomienda getUltimoEncomiendaByIdCliente(int idCliente) throws SQLException{
           Encomienda c = new Encomienda();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT MAX(id) AS id, origen, destino, idCliente FROM encomiendas WHERE idCliente = ?";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,idCliente);                 
               res = pst.executeQuery();                                    
                if (res.next()) {          
                    c.setId(res.getInt("id"));
                    c.setOrigen(res.getString("origen"));            
                    c.setDestino(res.getString("destino"));                                
                    c.setIdCliente(res.getInt("idCliente"));
                }                   
     
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
           return c;        
    }

  
}
