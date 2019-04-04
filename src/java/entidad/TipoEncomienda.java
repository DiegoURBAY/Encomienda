
package entidad;

public class TipoEncomienda {
    private int id;
    private String nom;

    public TipoEncomienda() {
    }

    public TipoEncomienda(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}