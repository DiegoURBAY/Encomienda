
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidad.Encomienda;
import entidad.TipoEncomienda;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncomiendaDAO extends Conexion implements DAO{ 

   
        public static void main(String[] args) throws Exception {
            
        TipoEncomiendaDAO tipoEncomiendaDAO = new TipoEncomiendaDAO();    
        EncomiendaDAO encomiendaDAO = new EncomiendaDAO();
        
        List<Integer> locales = new ArrayList<Integer>();
        locales.add(26);
        locales.add(34);
        locales.add(9);
        locales.add(25);
        locales.add(23);
        locales.add(35);
        locales.add(37);
        locales.add(10);
        
        Encomienda encomienda_nueva = new Encomienda();
        
        for (int i = 0; i < 5; i++) {
            encomienda_nueva.setOrigen(37);            
            encomienda_nueva.setDestino(24);
            encomienda_nueva.setIdCliente(28);

            encomiendaDAO.insertar(encomienda_nueva);                

            encomienda_nueva = encomiendaDAO.getUltimoEncomiendaByIdCliente(28);

            int idEncomienda = encomienda_nueva.getId();

            TipoEncomienda tipoEncomienda = new TipoEncomienda();
            tipoEncomienda.setTipo("paquete");
            tipoEncomienda.setAltura(10);
            tipoEncomienda.setAnchura(12);
            tipoEncomienda.setLargo(14);
            tipoEncomienda.setCantidad(2);
            tipoEncomienda.setPeso(175);
            tipoEncomienda.setPrecio(40);
            tipoEncomienda.setDelicado(1);
            tipoEncomienda.setIdEncomienda(idEncomienda);

            tipoEncomiendaDAO.insertar(tipoEncomienda);                 
        }

    }
  
    
    
    @Override
    public void insertar(Object obj) throws Exception {
        Encomienda c = (Encomienda) obj;
        PreparedStatement pst;
        String sql="INSERT INTO encomiendas ( origen, destino, idCliente, fechatime) VALUES (?,?,?, CURRENT_TIMESTAMP)";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, c.getOrigen());
            pst.setInt(2, c.getDestino());            
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
            pst.setInt(1, c.getOrigen());
            pst.setInt(2, c.getDestino());            
          //  pst.setDate(3, c.getEnvio());
         //   pst.setDate(4, c.getLlegada());
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
                        rs.getInt("origen"),
                        rs.getInt("destino"),
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
    
    public List<Encomienda> consultarEncomiendaPorIdCliente(int idCliente) throws Exception {
        List<Encomienda> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, origen, destino, idCliente, fechatime  FROM encomiendas WHERE estado = 1 AND idCliente = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, idCliente);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new Encomienda(
                        rs.getInt("id"),
                        rs.getInt("origen"),
                        rs.getInt("destino"),
                     //  rs.getDate("envio"),
                      //  rs.getDate("llegada"),                                              
                        rs.getInt("idCliente"),
                        rs.getTimestamp("fechatime")
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
        String sql = "SELECT id, origen, destino, idCliente FROM encomiendas WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, idEncomienda);
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
           String sql = "SELECT id, origen, destino, idCliente, fechatime FROM encomiendas WHERE id = ?";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);                 
               res = pst.executeQuery();                                    
                if (res.next()) {          
                    c.setId(res.getInt("id"));
                    c.setOrigen(res.getInt("origen"));            
                    c.setDestino(res.getInt("destino"));                               
               //     c.setEnvio(res.getDate("envio"));   
                 //   c.setLlegada(res.getDate("llegada")); 
                    c.setIdCliente(res.getInt("idCliente"));
                    c.setFechaRegistroTime(res.getTimestamp("fechatime"));
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
                    c.setOrigen(res.getInt("origen"));            
                    c.setDestino(res.getInt("destino"));                                
                    c.setIdCliente(res.getInt("idCliente"));                    
                }                   
     
        } catch (SQLException e) {
        }   catch (ClassNotFoundException ex) {
                Logger.getLogger(EncomiendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        finally{
            this.cerrar();
        }   
           return c;        
    }

  
}
