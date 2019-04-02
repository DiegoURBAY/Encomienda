
package dao;

import java.util.List;

public interface DAO<T, K> {
    //Se tendria como parametro Usario usario, pero como muchas clases usaran la misma operacion
    // entonces tendria que tener muchos insertar. Es por que uso Object obj 
    public void insertar(T obj) throws Exception;
    
    public void eliminar(T obj)  throws Exception;
    
    public void modificar(T obj) throws Exception;    
    
    public List<T> consultar() throws Exception;

    public T BuscarPorId(int id) throws Exception;    
    
     public boolean ConsultarNombre(String nom)throws Exception;
        
}
