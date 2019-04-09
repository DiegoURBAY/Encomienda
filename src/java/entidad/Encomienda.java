
package entidad;

import java.sql.Date;

public class Encomienda {
    
    private int id;
    private String origen;
    private String destino;    
    private Date envio;
    private Date llegada;
    private int idCliente;

    public Encomienda() {
    }

    public Encomienda(int id, String origen, String destino, Date envio, Date llegada, int idCliente) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.envio = envio;
        this.llegada = llegada;
        this.idCliente = idCliente;
    }   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getEnvio() {
        return envio;
    }

    public void setEnvio(Date envio) {
        this.envio = envio;
    }

    public Date getLlegada() {
        return llegada;
    }

    public void setLlegada(Date llegada) {
        this.llegada = llegada;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
                
}
