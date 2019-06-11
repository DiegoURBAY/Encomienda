/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidad.Reporte;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author usuario
 */
public class ReporteDAO extends Conexion implements DAO{ 
    
     public static void main(String[] args) throws Exception {
        ReporteDAO reporteDAO = new ReporteDAO();    
        Reporte reporte = new Reporte();
        
        String fecha_original  ="2019-01-01";
String[] fecha_total = fecha_original.split("-");
String año_nuevo = fecha_total[0];
String mes_nuevo = fecha_total[1];
String dia_nuevo = fecha_total[2];
String fecha_nueva = dia_nuevo+"/"+mes_nuevo+"/"+año_nuevo;    
                                            System.out.println(fecha_nueva);
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
    public List consultar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object BuscarPorId(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Lista que usa el para el grafico de barras
    public List<Reporte> consultarEncomiendaPorFecha(Date inicio, Date fin, int idCliente) throws Exception  {
        List<Reporte> datos = new ArrayList<>();
        PreparedStatement pst;
        PreparedStatement pst1;
        ResultSet rs;
        ResultSet rs1;
        String sqlTrac = "SET lc_time_names = 'es_ES' ";    
        
        String sql ;
        //Si el idcliente es mayor que cero entonces la búsqueda lo hace el cliente
        if(idCliente > 0){
            sql = "SELECT monthname(e.fechatime) AS mes, SUM(CASE WHEN p.tipo = 'sobre' THEN e.estado ELSE 0 END) AS sobre, SUM(CASE WHEN p.tipo = 'paquete' THEN e.estado ELSE 0 END) AS paquete FROM encomiendas e INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE e.fechatime BETWEEN '"+inicio+"' and '"+fin+"' AND e.idCliente="+idCliente+" GROUP BY mes ORDER BY e.fechatime";
        }
        //sino lo hace el administrador
        else{
            sql = "SELECT monthname(e.fechatime) AS mes, SUM(CASE WHEN p.tipo = 'sobre' THEN e.estado ELSE 0 END) AS sobre, SUM(CASE WHEN p.tipo = 'paquete' THEN e.estado ELSE 0 END) AS paquete FROM encomiendas e INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE e.fechatime BETWEEN '"+inicio+"' and '"+fin+"' GROUP BY mes ORDER BY e.fechatime";
        }
        
        try {
            this.conectar();
            pst1 = conexion.prepareStatement(sqlTrac);
            pst = conexion.prepareStatement(sql);
            rs1 = pst1.executeQuery();             
            rs = pst.executeQuery();       
            while(rs.next()){
                datos.add(new Reporte(
                        rs.getString("mes"),
                        rs.getInt("sobre"),
                        rs.getInt("paquete")
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
    
    public List<Reporte> consultarEncomiendaPorMes(String tipo, String mes, Date inicio, Date fin, int idCliente) throws Exception  {
        List<Reporte> datos = new ArrayList<>();
        PreparedStatement pst;
        PreparedStatement pst1;
        ResultSet rs;
        ResultSet rs1;
        String sqlTrac = "SET lc_time_names = 'es_ES' ";    
        
        String sql ;
        //Si el idcliente es mayor que cero entonces la búsqueda lo hace el cliente
        //SELECT p.fechatime AS mes, SUM(CASE WHEN LENGTH(c.identificador) =8 THEN c.estado ELSE 0 END) AS persona, SUM(CASE WHEN LENGTH(c.identificador) =11 THEN c.estado ELSE 0 END) AS empresa FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE e.fechatime AND  monthname(c.fecharegistro) = "january" GROUP BY mes ORDER BY e.fechatime
        if(idCliente > 0){
//            sql = "SELECT monthname(e.fechatime) AS mes, SUM(CASE WHEN p.tipo = 'sobre' THEN e.estado ELSE 0 END) AS sobre, SUM(CASE WHEN p.tipo = 'paquete' THEN e.estado ELSE 0 END) AS paquete FROM encomiendas e INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE e.fechatime BETWEEN '"+inicio+"' and '"+fin+"' AND e.idCliente="+idCliente+" GROUP BY mes ORDER BY e.fechatime";             
            if(mes ==null){  
                sql = "SELECT p.fecharegistro AS mes, SUM(CASE WHEN LENGTH(c.identificador) =8 THEN c.estado ELSE 0 END) AS persona, SUM(CASE WHEN LENGTH(c.identificador) =11 THEN c.estado ELSE 0 END) AS empresa FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE p.fecharegistro BETWEEN '"+inicio+"' AND '"+fin+"' AND p.tipo ='"+tipo+"' AND e.idCliente="+idCliente+" GROUP BY mes ORDER BY p.fecharegistro";
            }
            else{
                sql = "SELECT p.fecharegistro AS mes, SUM(CASE WHEN LENGTH(c.identificador) =8 THEN c.estado ELSE 0 END) AS persona, SUM(CASE WHEN LENGTH(c.identificador) =11 THEN c.estado ELSE 0 END) AS empresa FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE  monthname(p.fecharegistro)='"+mes+"' AND p.tipo ='"+tipo+"' AND e.idCliente="+idCliente+" GROUP BY mes ORDER BY p.fecharegistro";
                
            }
        }
        //sino lo hace el administrador
        else{
           // sql = "SELECT p.fecharegistro AS mes, SUM(CASE WHEN LENGTH(c.identificador) =8 THEN c.estado ELSE 0 END) AS persona, SUM(CASE WHEN LENGTH(c.identificador) =11 THEN c.estado ELSE 0 END) AS empresa FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE monthname(p.fecharegistro) = "+mes+" GROUP BY mes ORDER BY p.fecharegistro";
           if(mes !=null){              
               sql = "SELECT p.fecharegistro AS mes, SUM(CASE WHEN LENGTH(c.identificador) =8 THEN c.estado ELSE 0 END) AS persona, SUM(CASE WHEN LENGTH(c.identificador) =11 THEN c.estado ELSE 0 END) AS empresa FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE  monthname(p.fecharegistro)='"+mes+"' AND p.tipo ='"+tipo+"' GROUP BY mes ORDER BY p.fecharegistro";
           }
           else{
                sql = "SELECT p.fecharegistro AS mes, SUM(CASE WHEN LENGTH(c.identificador) =8 THEN c.estado ELSE 0 END) AS persona, SUM(CASE WHEN LENGTH(c.identificador) =11 THEN c.estado ELSE 0 END) AS empresa FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE p.fecharegistro BETWEEN '"+inicio+"' AND '"+fin+"' AND p.tipo ='"+tipo+"' GROUP BY mes ORDER BY p.fecharegistro";
           }
           
        }
        
        try {
            this.conectar();
            pst1 = conexion.prepareStatement(sqlTrac);
            pst = conexion.prepareStatement(sql);
            rs1 = pst1.executeQuery();             
            rs = pst.executeQuery();       
            while(rs.next()){
                datos.add(new Reporte(
                        rs.getString("mes"),
                        rs.getInt("empresa"),
                        rs.getInt("persona")
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
    
    //Lista que usa el cliente para el grafico de pastel
    public List<Reporte> consultarEncomiendaPorFecha2(Date inicio, Date fin, int idCliente) throws Exception  {
        List<Reporte> datos = new ArrayList<>();
        PreparedStatement pst;
        PreparedStatement pst1;
        ResultSet rs;
        ResultSet rs1;
        String sqlTrac = "SET lc_time_names = 'es_ES' ";       
        
        String sql;
        if(idCliente > 0){
            sql = "SELECT p.tipo AS tipo, count(p.id) AS total FROM encomiendas e INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE p.fecharegistro BETWEEN '"+inicio+"' and '"+fin+"' AND e.idCliente="+idCliente+" GROUP BY tipo ASC";
        }else{
            sql = "SELECT p.tipo AS tipo, count(p.id) AS total FROM encomiendas e INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE p.fecharegistro BETWEEN '"+inicio+"' and '"+fin+"' GROUP BY tipo ASC";
        }
        
        try {
            this.conectar();
            pst1 = conexion.prepareStatement(sqlTrac);
            pst = conexion.prepareStatement(sql);
            rs1 = pst1.executeQuery();             
            rs = pst.executeQuery();       
            while(rs.next()){
                datos.add(new Reporte(
                        rs.getString("tipo"),
                        rs.getInt("total")
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
    
//SELECT monthname(c.fecharegistro) AS mes, SUM(CASE WHEN LENGTH(c.identificador)=11 THEN c.estado ELSE 0 END) AS empresa, SUM(CASE WHEN LENGTH(c.identificador)=8 THEN c.estado ELSE 0 END) AS persona FROM clientes c WHERE c.fecharegistro BETWEEN '2019-04-01' AND '2019-05-01' GROUP BY mes ORDER BY c.fecharegistro
    
    public List<Reporte> consultarClientePorFecha(Date inicio, Date fin) throws Exception  {
        List<Reporte> datos = new ArrayList<>();
        PreparedStatement pst;
        PreparedStatement pst1;
        ResultSet rs;
        ResultSet rs1;
        String sqlTrac = "SET lc_time_names = 'es_ES' ";               
        String sql = "SELECT monthname(c.fecharegistro) AS mes, SUM(CASE WHEN LENGTH(c.identificador)=11 THEN c.estado ELSE 0 END) AS empresa, SUM(CASE WHEN LENGTH(c.identificador)=8 THEN c.estado ELSE 0 END) AS persona FROM clientes c WHERE c.fecharegistro BETWEEN '"+inicio+"' AND '"+fin+"' GROUP BY mes ORDER BY c.fecharegistro";        
        try {
            this.conectar();
            pst1 = conexion.prepareStatement(sqlTrac);
            pst = conexion.prepareStatement(sql);
            rs1 = pst1.executeQuery();             
            rs = pst.executeQuery();       
            while(rs.next()){
                datos.add(new Reporte(
                        rs.getString("mes"),
                        rs.getInt("empresa"),
                        rs.getInt("persona")
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
    
    public List<Reporte> consultarClientePorFecha2(Date inicio, Date fin) throws Exception  {
        List<Reporte> datos = new ArrayList<>();
        PreparedStatement pst;
        PreparedStatement pst1;
        ResultSet rs;
        ResultSet rs1;
        String sqlTrac = "SET lc_time_names = 'es_ES' ";               
        String sql ="SELECT monthname(c.fecharegistro) AS mes, SUM(c.estado) AS total FROM clientes c WHERE c.fecharegistro BETWEEN '"+inicio+"' AND '"+fin+"' GROUP BY mes ORDER BY c.fecharegistro";
        
        try {
            this.conectar();
            pst1 = conexion.prepareStatement(sqlTrac);
            pst = conexion.prepareStatement(sql);
            rs1 = pst1.executeQuery();             
            rs = pst.executeQuery();       
            while(rs.next()){
                datos.add(new Reporte(
                        rs.getString("mes"),
                        rs.getInt("total")
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
    
    public List<Reporte> consultarPrecioPorFecha(Date inicio, Date fin) throws Exception  {
        List<Reporte> datos = new ArrayList<>();
        PreparedStatement pst;
        PreparedStatement pst1;
        ResultSet rs;
        ResultSet rs1;
        String sqlTrac = "SET lc_time_names = 'es_ES' ";               
        String sql ="SELECT monthname(e.fechatime) AS mes, SUM(CASE WHEN LENGTH(c.identificador) =8 THEN p.precio ELSE 0 END) AS persona, SUM(CASE WHEN LENGTH(c.identificador) =11 THEN p.precio ELSE 0 END) AS empresa FROM clientes c INNER JOIN encomiendas e ON e.idCliente = c.id INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE e.fechatime BETWEEN '"+inicio+"' AND '"+fin+"' GROUP BY mes ORDER BY e.fechatime";
        
        try {
            this.conectar();
            pst1 = conexion.prepareStatement(sqlTrac);
            pst = conexion.prepareStatement(sql);
            rs1 = pst1.executeQuery();             
            rs = pst.executeQuery();       
            while(rs.next()){
                datos.add(new Reporte(
                        rs.getString("mes"),
                        rs.getInt("empresa"),
                        rs.getInt("persona")
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
    
    public List<Reporte> consultarPrecioPorFecha2(Date inicio, Date fin) throws Exception  {
        List<Reporte> datos = new ArrayList<>();
        PreparedStatement pst;
        PreparedStatement pst1;
        ResultSet rs;
        ResultSet rs1;
        String sqlTrac = "SET lc_time_names = 'es_ES' ";               
        String sql ="SELECT monthname(c.fecharegistro) AS mes, SUM(c.precio) AS total FROM tiposencomiendas c WHERE c.fecharegistro BETWEEN '"+inicio+"' AND '"+fin+"' GROUP BY mes ORDER BY c.fecharegistro";
        
        try {
            this.conectar();
            pst1 = conexion.prepareStatement(sqlTrac);
            pst = conexion.prepareStatement(sql);
            rs1 = pst1.executeQuery();             
            rs = pst.executeQuery();       
            while(rs.next()){
                datos.add(new Reporte(
                        rs.getString("mes"),
                        rs.getInt("total")
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
}
