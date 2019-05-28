
package entidad;

public class Calificacion {
    
    private int id;
    private int encomienda;
    private int perdida;    
    private int demora;
    private int daño;
    private String comentario;

    public Calificacion() {
    }

    public Calificacion(int id, int encomienda, int perdida, int demora, int daño, String comentario) {
        this.id = id;
        this.encomienda = encomienda;
        this.perdida = perdida;
        this.demora = demora;
        this.daño = daño;
        this.comentario = comentario;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEncomienda() {
        return encomienda;
    }

    public void setEncomienda(int encomienda) {
        this.encomienda = encomienda;
    }

    public int getPerdida() {
        return perdida;
    }

    public void setPerdida(int perdida) {
        this.perdida = perdida;
    }

    public int getDemora() {
        return demora;
    }

    public void setDemora(int demora) {
        this.demora = demora;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    
    
}
