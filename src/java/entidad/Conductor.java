package entidad;

public class Conductor {
    private int id;  
    private String dni;
    private String nom;
    private String ape;   
    private String lic;      
    private String email;
    private String tel;    
    private String tipo;
    private int disp;
    private int estado;
    
    public Conductor() {
    }

    public Conductor(int id, String dni, String nom, String ape, String lic, String email, String tel, int disp) {
        this.id = id;
        this.dni = dni;
        this.nom = nom;
        this.ape = ape;
        this.lic = lic;
        this.email = email;
        this.tel = tel;
        this.disp = disp;
    }   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLic() {
        return lic;
    }

    public void setLic(String lic) {
        this.lic = lic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDisp() {
        return disp;
    }

    public void setDisp(int disp) {
        this.disp = disp;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
 
    
    
}
