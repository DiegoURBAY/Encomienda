
package entidad;

public class Usuario {
    
    private int id;
    private String nom;
    private String password;
    private String email;
    private String nivel;

    //Constructor vacío para crear objetos vacios
    public Usuario() {
    }

    //Constructor para inicializar todos los atributos de la clase

    public Usuario(int id, String nom, String password, String email, String nivel) {
        this.id = id;
        this.nom = nom;
        this.password = password;
        this.email = email;
        this.nivel = nivel;
    }
    
    //Para obtener o establecer los valores de los campos

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
        
    
    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

}
