
package entidad;

public class Vehiculo {
    
    private int id;
    private String placa;     
    private double capacidad;
    private int idencomienda;

    public Vehiculo() {
    }

    public Vehiculo(int id, String placa, double capacidad, int idencomienda) {
        this.id = id;
        this.placa = placa;
        this.capacidad = capacidad;
        this.idencomienda = idencomienda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }


    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

    public int getIdencomienda() {
        return idencomienda;
    }

    public void setIdencomienda(int idencomienda) {
        this.idencomienda = idencomienda;
    }


    
}
