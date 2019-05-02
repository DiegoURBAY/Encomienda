
package entidad;

import java.sql.Date;
import java.sql.Timestamp;

public class Disponibilidad {
    private int id;
    private int idVehiculo;
    private int idTipoEncomienda;
    private double pesoactual;
    private Timestamp desde;
    private Timestamp hasta;
    private int situacion;
    private Timestamp fecharegistro;


    public Disponibilidad() {
    }

    public Disponibilidad(int id, int idVehiculo, int idTipoEncomienda, double pesoactual, Timestamp desde, Timestamp hasta, int situacion) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.idTipoEncomienda = idTipoEncomienda;
        this.pesoactual = pesoactual;
        this.desde = desde;
        this.hasta = hasta;
        this.situacion = situacion;
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

    public double getPesoactual() {
        return pesoactual;
    }

    public void setPesoactual(double pesoactual) {
        this.pesoactual = pesoactual;
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

 
    
    
}
//INSERT INTO `disponibilidad` (`id`, `idVehiculo`, `idTipoEncomienda`, `pesoactual`, `desde`, `hasta`, `situacion`, `fecharegistro`, `estado`) VALUES (NULL, '1', '1', '0.21', '2019-04-09 11:23:21', '2019-04-10 09:20:13', '1', '2019-04-09 13:25:22', '1');