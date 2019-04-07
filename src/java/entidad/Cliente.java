
package entidad;

import java.sql.Date;

public class Cliente {
    
    private int id;
    private String identificador;
    private String nombre;
    private String email;    
    private String usuario;
    private String contraseña;    
    private String telefono;
    private Date fecharegistro;

    public Cliente() {
    }


    public Cliente(int id, String identificador, String nombre, String email, String usuario, String contraseña, String telefono, Date fecharegistro) {
        this.id = id;
        this.identificador = identificador;
        this.nombre = nombre;
        this.email = email;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.fecharegistro = fecharegistro;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }



    
    
}
