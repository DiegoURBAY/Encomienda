    
package entidad;


public class Reporte {
    
    /*
    
    SELECT monthname(e.fechatime) AS mes, SUM(CASE WHEN p.tipo = 'sobre' THEN e.estado ELSE 0 END) AS sobre, SUM(CASE WHEN p.tipo = 'paquete' THEN e.estado ELSE 0 END) AS paquete FROM encomiendas e INNER JOIN tiposencomiendas p ON e.id = p.idEncomienda WHERE e.fechatime BETWEEN '2019-01-01' and '2019-06-02' AND e.idCliente=28 GROUP BY mes ORDER BY e.fechatime
    
    */
    
    private String tiempo;
    private double total;
    private int paquete;
    private int sobre;
    

    public Reporte() {        
    }    
    
    public Reporte(String tiempo) {
        this.tiempo = tiempo;
     
    }    

    public Reporte(String tiempo,int sobre, int paquete) {
        this.tiempo = tiempo;
        this.sobre = sobre;
        this.paquete = paquete;        
    }

    public Reporte(String tiempo, double total) {
        this.tiempo = tiempo;
        this.total = total;
    }      
    
    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getPaquete() {
        return paquete;
    }

    public void setPaquete(int paquete) {
        this.paquete = paquete;
    }

    public int getSobre() {
        return sobre;
    }

    public void setSobre(int sobre) {
        this.sobre = sobre;
    }


    
}
