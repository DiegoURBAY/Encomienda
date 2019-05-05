
package dao;

import entidad.Encomienda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entidad.TipoEncomienda;

public class TipoEncomiendaDAO extends Conexion implements DAO{
    
 /*  
    public static void main(String[] args) throws Exception {
        TipoEncomienda tipoEncomienda = new TipoEncomienda();
        tipoEncomienda.setTipo("sobre");
        tipoEncomienda.setAltura(0);
        tipoEncomienda.setAnchura(0);
        tipoEncomienda.setLargo(0);
        tipoEncomienda.setCantidad(11111);
        tipoEncomienda.setPeso(11111);
        tipoEncomienda.setPrecio(1111);
        tipoEncomienda.setIdEncomienda(14);
        
        TipoEncomiendaDAO tipoEncomiendaDAO = new TipoEncomiendaDAO();
        tipoEncomiendaDAO.insertar(tipoEncomienda);
    }
*/
    
/*
    public static void main(String[] args) throws Exception {
        TipoEncomienda tipoEncomiendaSobre = new TipoEncomienda();       
        Encomienda encomienda = new Encomienda();     
        
            tipoEncomiendaSobre.setTipo("sobre");
            tipoEncomiendaSobre.setAltura(0);
            tipoEncomiendaSobre.setAnchura(0);
            tipoEncomiendaSobre.setLargo(0);                            
            tipoEncomiendaSobre.setCantidad(12);
            tipoEncomiendaSobre.setPeso(1.05);
            tipoEncomiendaSobre.setPrecio(120);
            tipoEncomiendaSobre.setIdEncomienda(119);
            tipoEncomiendaSobre.setId(177);
            
             TipoEncomiendaDAO tipoEncomiendaDAO = new TipoEncomiendaDAO();
             tipoEncomiendaDAO.modificar(tipoEncomiendaSobre);
         //   int idTipoEncomienda = 177;
           
            TipoEncomienda SobreEncontrado = tipoEncomiendaDAO.BuscarPorId(idTipoEncomienda);  
             int   idEncomiendaEditar = SobreEncontrado.getIdEncomienda();
                
                //encomiendaSobre = encomiendaDAO.BuscarPorId(idEncomiendaEditar);                
              //  tipoEncomiendas = tipoEncomiendaDAO.consultarTipoPorEncomienda(idEncomiendaEditar);
                                                
                tipoEncomiendaSobre.setIdEncomienda(idEncomiendaEditar);
                tipoEncomiendaSobre.setId(119);              
     
    }
     */
    @Override
    public void insertar(Object obj) throws Exception {
        TipoEncomienda te = (TipoEncomienda) obj;
        PreparedStatement pst;
        String sql="INSERT INTO tiposencomiendas (tipo, altura, anchura, largo, cantidad, peso, precio, idEncomienda, fecharegistro) VALUES (?, ?, ?, ?, ?, ?, ?,?, CURDATE())";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, te.getTipo());
            pst.setDouble(2, te.getAltura());
            pst.setDouble(3, te.getAnchura());
            pst.setDouble(4, te.getLargo());
            pst.setInt(5, te.getCantidad());
            pst.setDouble(6, te.getPeso());
            pst.setDouble(7, te.getPrecio());
            pst.setInt(8, te.getIdEncomienda());
            pst.executeUpdate();      

        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }           
    }

    @Override
    public void eliminar(Object obj) throws Exception {
        TipoEncomienda te = (TipoEncomienda) obj;
        PreparedStatement pst;
        String sql="UPDATE tiposencomiendas set estado = 0 WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, te.getId());
            pst.executeUpdate();            
                  
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }     
    }

    @Override
    public void modificar(Object obj) throws Exception {
        TipoEncomienda te = (TipoEncomienda) obj;
        PreparedStatement pst;
        String sql="UPDATE tiposencomiendas set tipo = ?, altura = ?, anchura = ?, largo = ?, cantidad = ?, precio = ?, idEncomienda = ? WHERE id = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setString(1, te.getTipo());
            pst.setDouble(2, te.getAltura());
            pst.setDouble(3, te.getAnchura());
            pst.setDouble(4, te.getLargo());
            pst.setInt(5, te.getCantidad());
            pst.setDouble(6, te.getPrecio());
            pst.setInt(7, te.getIdEncomienda());
            pst.setInt(8, te.getId());
            pst.executeUpdate();            
                    
        } catch (SQLException e) {
           throw  e;
        }
        finally{
            this.cerrar();
        }        
    }

    @Override
    public List consultar() throws Exception {
        List<TipoEncomienda> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT id, tipo, altura, anchura, largo, cantidad, peso, precio, idEncomienda FROM tiposencomiendas WHERE estado = 1";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new TipoEncomienda(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDouble("altura"),
                        rs.getDouble("anchura"),
                        rs.getDouble("largo"),
                        rs.getInt("cantidad"),
                        rs.getDouble("preso"),
                        rs.getDouble("precio"),
                        rs.getInt("idEncomienda")
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

        public List<TipoEncomienda> consultarTipoPorEncomienda(int idEncomienda) throws Exception {
        List<TipoEncomienda> datos = new ArrayList<>();
        PreparedStatement pst;
        ResultSet rs;
        String sql = "SELECT te.id, te.tipo, te.altura, te.anchura, te.largo, te.cantidad, te.peso, te.precio, te.idEncomienda FROM tiposencomiendas te, encomiendas e WHERE te.estado = 1 AND te.idEncomienda = e.id AND te.idEncomienda = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, idEncomienda);
            rs = pst.executeQuery();
            while(rs.next()){
                datos.add(new TipoEncomienda(
                        rs.getInt("te.id"),
                        rs.getString("te.tipo"),
                        rs.getDouble("te.altura"),
                        rs.getDouble("te.anchura"),
                        rs.getDouble("te.largo"),
                        rs.getInt("te.cantidad"),
                        rs.getDouble("te.peso"),
                        rs.getDouble("te.precio"),
                        rs.getInt("te.idEncomienda")
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
        
        public TipoEncomienda consultarPorEncomienda(int idEncomienda) throws Exception {
        TipoEncomienda tipoEncomienda = new TipoEncomienda();
        PreparedStatement pst;
        ResultSet res;
        String sql = "SELECT te.id, te.tipo, te.altura, te.anchura, te.largo, te.cantidad, te.peso, te.precio, te.idEncomienda FROM tiposencomiendas te, encomiendas e WHERE te.estado = 1 AND te.idEncomienda = e.id AND te.idEncomienda = ?";
        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            pst.setInt(1, idEncomienda);
            res = pst.executeQuery();
            if (res.next()) {           
               tipoEncomienda.setId(res.getInt("te.id"));
               tipoEncomienda.setTipo(res.getString("te.tipo"));
               tipoEncomienda.setAltura(res.getDouble("te.altura"));
               tipoEncomienda.setAnchura(res.getDouble("te.anchura"));
               tipoEncomienda.setLargo(res.getDouble("te.largo"));
               tipoEncomienda.setCantidad(res.getInt("te.cantidad"));
               tipoEncomienda.setPeso(res.getDouble("te.peso"));
               tipoEncomienda.setPrecio(res.getDouble("te.precio"));
               tipoEncomienda.setIdEncomienda(res.getInt("te.idEncomienda"));                 
            }
        } catch (SQLException e) {
            throw e;
        }
        finally{
            this.cerrar();
        }
        return tipoEncomienda;        
    }        

    @Override
    public TipoEncomienda BuscarPorId(int id) throws Exception {
           TipoEncomienda tu = new TipoEncomienda();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT id, tipo, altura, anchura, largo, cantidad, peso, precio, idEncomienda FROM tiposencomiendas WHERE id =?";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);  
               res = pst.executeQuery();                                    
                if (res.next()) {
                    tu.setId(res.getInt("id"));
                    tu.setTipo(res.getString("tipo"));
                    tu.setAltura(res.getDouble("altura"));
                    tu.setAnchura(res.getDouble("anchura"));
                    tu.setLargo(res.getDouble("largo"));
                    tu.setCantidad(res.getInt("cantidad"));
                    tu.setPeso(res.getDouble("peso"));
                    tu.setPrecio(res.getDouble("precio"));
                    tu.setIdEncomienda(res.getInt("idEncomienda"));
                    
                }                        
            } catch (SQLException e) {
            }
            finally{
                this.cerrar();
            }
           return tu;        
    }


    public int ConsultarTipoNombrePorEncomienda(int id) throws Exception {
           int result = 0;
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT te.idEncomienda FROM tiposencomiendas te, encomiendas e WHERE te.estado = 1 AND te.idEncomienda = e.id AND te.idEncomienda = ?";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,id);  
               res = pst.executeQuery();                                    
                if (res.next()) {
                    result = res.getInt("te.idEncomienda");
                    
                }                        
            } catch (SQLException e) {
            }
            finally{
                this.cerrar();
            }
           return result;        
    }

    public double ConsultarPesoPorEncomiendaID(int idEncomienda) throws Exception {
           double result = 0;
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT SUM(peso) AS peso FROM tiposencomiendas WHERE estado = 1 AND idEncomienda = ?";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,idEncomienda);  
               res = pst.executeQuery();                                    
                if (res.next()) {
                    result = res.getInt("peso");
                    
                }                        
            } catch (SQLException e) {
            }
            finally{
                this.cerrar();
            }
           return result;        
    }
    
    //        //SELECT SUM(peso) FROM tiposencomiendas WHERE estado = 1 AND idEncomienda = 13
    public boolean ConsultarNombre(String nom) throws Exception {
        PreparedStatement pst;
        ResultSet res = null;
        String sql = "SELECT * FROM tiposencomiendas WHERE nom='"+nom+"'";

        try {
            this.conectar();
            pst = conexion.prepareStatement(sql);
            res = pst.executeQuery();
        } catch (Exception e) {
        }
         return res.next();        
    }
    
    
    
    public TipoEncomienda getUltimoTipoEncomiendaByIdEncomienda(int idEncomienda) throws SQLException{
           TipoEncomienda tipoEncomienda = new TipoEncomienda();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT MAX(id) AS id, tipo, altura, anchura, largo, cantidad, peso, precio FROM tiposencomiendas WHERE idEncomienda = ?";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,idEncomienda);                 
               res = pst.executeQuery();                                    
                if (res.next()) {          
                    tipoEncomienda.setId(res.getInt("id"));
                    tipoEncomienda.setTipo(res.getString("tipo"));            
                    tipoEncomienda.setAltura(res.getDouble("altura"));                                
                    tipoEncomienda.setAnchura(res.getDouble("anchura"));
                    tipoEncomienda.setLargo(res.getDouble("largo"));
                    tipoEncomienda.setCantidad(res.getInt("cantidad"));
                    tipoEncomienda.setPeso(res.getDouble("peso"));
                    tipoEncomienda.setPrecio(res.getDouble("precio"));
                }                   
     
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
        return tipoEncomienda;  
    } 
    
    
     public TipoEncomienda buscarTipoEncomiendaPorEncomiendaID(int idEncomienda) throws SQLException{
           TipoEncomienda tipoEncomienda = new TipoEncomienda();
           PreparedStatement pst;
           ResultSet res;
           String sql = "SELECT  tp.id, tp.tipo, tp.altura, tp.anchura, tp.largo, tp.cantidad, tp.peso, tp.precio, tp.idEncomienda FROM tiposencomiendas tp, encomiendas e Where e.id = tp.idEncomienda AND tp.estado = 1 AND tp.idEncomienda = ?";
           try {
            this.conectar();
               pst = conexion.prepareStatement(sql);
               pst.setInt(1,idEncomienda);                 
               res = pst.executeQuery();                                    
                if (res.next()) {          
                    tipoEncomienda.setId(res.getInt("tp.id"));
                    tipoEncomienda.setTipo(res.getString("tp.tipo"));            
                    tipoEncomienda.setAltura(res.getDouble("tp.altura"));                                
                    tipoEncomienda.setAnchura(res.getDouble("tp.anchura"));
                    tipoEncomienda.setLargo(res.getDouble("tp.largo"));
                    tipoEncomienda.setCantidad(res.getInt("tp.cantidad"));
                    tipoEncomienda.setPeso(res.getDouble("tp.peso"));
                    tipoEncomienda.setPrecio(res.getDouble("tp.precio"));
                    tipoEncomienda.setIdEncomienda(res.getInt("tp.idEncomienda"));
                }                   
     
        } catch (SQLException e) {
        }
        finally{
            this.cerrar();
        }   
        return tipoEncomienda;  
    }    
    
}
