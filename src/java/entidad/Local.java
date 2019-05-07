
package entidad;

public class Local {
    
    private int id;
    private String titulo;
    private String cx;
    private String cy;
    private String direccion;
    private String telefono;
    private int idLugar;
    
    private String LugarString;

    public Local() {
    }

    public Local(int id, String titulo, String cx, String cy, String direccion, String telefono, int idLugar) {
        this.id = id;
        this.titulo = titulo;
        this.cx = cx;
        this.cy = cy;
        this.direccion = direccion;
        this.telefono = telefono;
        this.idLugar = idLugar;
    }



    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLugarString() {
        return LugarString;
    }

    public void setLugarString(String LugarString) {
        this.LugarString = LugarString;
    }
    
    
    
    
}
