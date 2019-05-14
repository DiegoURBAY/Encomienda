package entidad;

public class Ayudante {
    private int id;
    private String nom;
    private String ape;
    private String dni;
    private String email;
    private String tel;    
    private String direc;
    private String distr;

    public Ayudante() {
    }

    public Ayudante(int id, String nom, String ape, String dni, String email, String tel, String direc, String distr) {
        this.id = id;
        this.nom = nom;
        this.ape = ape;
        this.dni = dni;
        this.email = email;
        this.tel = tel;
        this.direc = direc;
        this.distr = distr;
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

    public String getDistr() {
        return distr;
    }

    public void setDistr(String distr) {
        this.distr = distr;
    }
    
    
    
}
