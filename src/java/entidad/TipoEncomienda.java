
package entidad;

public class TipoEncomienda {
    private int id;
    private String tipo;
    private double altura;
    private double anchura;
    private double largo;
    private int cantidad;
    private double peso;
    private double precio;
    private int idEncomienda;

    public TipoEncomienda() {
    }

    public TipoEncomienda(int id, String tipo, double altura, double anchura, double largo, int cantidad, double peso, double precio, int idEncomienda) {
        this.id = id;
        this.tipo = tipo;
        this.altura = altura;
        this.anchura = anchura;
        this.largo = largo;
        this.cantidad = cantidad;
        this.peso = peso;
        this.precio = precio;
        this.idEncomienda = idEncomienda;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getAnchura() {
        return anchura;
    }

    public void setAnchura(double anchura) {
        this.anchura = anchura;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdEncomienda() {
        return idEncomienda;
    }

    public void setIdEncomienda(int idEncomienda) {
        this.idEncomienda = idEncomienda;
    }

    
    
}