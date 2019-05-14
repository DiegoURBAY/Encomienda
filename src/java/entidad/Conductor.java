package entidad;

public class Conductor {
    private int id;  
    private String nom;
    private String ape;
    private String dni;
    private String lic;      
    private String email;
    private String tel;    
    private String direc;
    private String distr;
    private String tipo;
    

    public Conductor() {
    }

    public Conductor(int id, String nom, String ape, String dni, String lic, String email, String tel, String direc, String distr, String tipo) {
        this.id = id;
        this.nom = nom;
        this.ape = ape;
        this.dni = dni;
        this.lic = lic;
        this.email = email;
        this.tel = tel;
        this.direc = direc;
        this.distr = distr;
        this.tipo = tipo;
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

    public String getDirec() {
        return direc;
    }

    public void setDirec(String direc) {
        this.direc = direc;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDistr() {
        return distr;
    }

    public void setDistr(String distr) {
        this.distr = distr;
    }       
     
}
