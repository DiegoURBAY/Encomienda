
package entidad;

import java.sql.Date;
import java.sql.Timestamp;

public class Disponibilidad {
    private int id;
    private int idVehiculo;
    private int idTipoEncomienda;
    private double actualvolumen;
    private double actualcapacidad;  
    private Timestamp desde;
    private Timestamp hasta;
    private int situacion;
    private Timestamp fecharegistro;
    private int estado;


    public Disponibilidad() {
    }

    public Disponibilidad(int id, int idVehiculo, int idTipoEncomienda, double actualvolumen, double actualcapacidad, Timestamp desde, Timestamp hasta, int situacion) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.idTipoEncomienda = idTipoEncomienda;
        this.actualvolumen = actualvolumen;
        this.actualcapacidad = actualcapacidad;
        this.desde = desde;
        this.hasta = hasta;
        this.situacion = situacion;
    }

        public Disponibilidad(int id, int idVehiculo, int idTipoEncomienda, double actualvolumen, double actualcapacidad, int situacion, int estado) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.idTipoEncomienda = idTipoEncomienda;
        this.actualvolumen = actualvolumen;
        this.actualcapacidad = actualcapacidad;
        this.situacion = situacion;
        this.estado = estado;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getIdTipoEncomienda() {
        return idTipoEncomienda;
    }

    public void setIdTipoEncomienda(int idTipoEncomienda) {
        this.idTipoEncomienda = idTipoEncomienda;
    }

    public double getActualvolumen() {
        return actualvolumen;
    }

    public void setActualvolumen(double actualvolumen) {
        this.actualvolumen = actualvolumen;
    }

    public double getActualcapacidad() {
        return actualcapacidad;
    }

    public void setActualcapacidad(double actualcapacidad) {
        this.actualcapacidad = actualcapacidad;
    }

    public Timestamp getDesde() {
        return desde;
    }

    public void setDesde(Timestamp desde) {
        this.desde = desde;
    }

    public Timestamp getHasta() {
        return hasta;
    }

    public void setHasta(Timestamp hasta) {
        this.hasta = hasta;
    }

    public int getSituacion() {
        return situacion;
    }

    public void setSituacion(int situacion) {
        this.situacion = situacion;
    }

    public Timestamp getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Timestamp fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    

}
//INSERT INTO `disponibilidad` (`id`, `idVehiculo`, `idTipoEncomienda`, `pesoactual`, `desde`, `hasta`, `situacion`, `fecharegistro`, `estado`) VALUES (NULL, '1', '1', '0.21', '2019-04-09 11:23:21', '2019-04-10 09:20:13', '1', '2019-04-09 13:25:22', '1');