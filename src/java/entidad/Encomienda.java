
package entidad;

import java.sql.Timestamp;
import java.sql.Date;

public class Encomienda {
    
    private int id;
    private String origen;
    private String destino;    
    private Date envio;
    private Date llegada;
    private int idCliente;
    private Timestamp fechaRegistroTime;
    
    private String envioS;
    private String llegadaS;
    private String fechaRegistroTimeString;

    public Encomienda() {
    }
    //Originalmente, ya no se usa
    public Encomienda(int id, String origen, String destino, Date envio, Date llegada, int idCliente) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.envio = envio;
        this.llegada = llegada;
        this.idCliente = idCliente;
    }   
    //Se usaba para mostrar bien las fechas
    public Encomienda(int id, String origen, String destino, int idCliente, String envioS, String llegadaS) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.idCliente = idCliente;
        this.envioS = envioS;
        this.llegadaS = llegadaS;
    }

    //el unico q se usa actualmente
    public Encomienda(int id, String origen, String destino, int idCliente) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.idCliente = idCliente;
    }

    public Encomienda(int id, String origen, String destino, int idCliente,Timestamp fechaRegistroTime) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.idCliente = idCliente;
        this.fechaRegistroTime = fechaRegistroTime;
    }

    public Encomienda(int id, String origen, String destino, int idCliente, String fechaRegistroTimeString) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.idCliente = idCliente;
        this.fechaRegistroTimeString = fechaRegistroTimeString;
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

    public String getEnvioS() {
        return envioS;
    }

    public void setEnvioS(String envioS) {
        this.envioS = envioS;
    }

    public String getLlegadaS() {
        return llegadaS;
    }

    public void setLlegadaS(String llegadaS) {
        this.llegadaS = llegadaS;
    }

    public Timestamp getFechaRegistroTime() {
        return fechaRegistroTime;
    }

    public void setFechaRegistroTime(Timestamp fechaRegistroTime) {
        this.fechaRegistroTime = fechaRegistroTime;
    }

    public String getFechaRegistroTimeString() {
        return fechaRegistroTimeString;
    }

    public void setFechaRegistroTimeString(String fechaRegistroTimeString) {
        this.fechaRegistroTimeString = fechaRegistroTimeString;
    }

       
}
