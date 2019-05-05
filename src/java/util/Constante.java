
package util;

import dao.DisponibilidadDAO;
import dao.VehiculoDAO;
import entidad.Disponibilidad;
import entidad.Vehiculo;
import java.util.ArrayList;
import java.util.List;

public class Constante {
    /*
    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
     /*   
    public static Vehiculo EscogerVehiculo(double volumen, double capacidad, int idTipoEncomienda){
        Vehiculo vehiculo = new Vehiculo();     
        Vehiculo vehiculo2 = new Vehiculo();
        VehiculoDAO vehiculoDAO2 = new VehiculoDAO();
        List<Vehiculo>  vehiculo_list;
        DisponibilidadDAO disponibilidadDAO = new DisponibilidadDAO();
        List<Disponibilidad> disponibilidadList = new ArrayList<>();
        Disponibilidad disponibilidad = new Disponibilidad();
        
        double volumen_max = 0;
        double capacidad_max = 0;
        double volumen_actual;
        double capacidad_actual;
        double suma_volumen = 0;
        double suma_capacidad = 0;
        double volumen_encomienda;
        double capacidad_encomienda;    
        int situacion = 1;
        int idVehiculo = 0;        
        
           try {
         
            vehiculo_list= vehiculoDAO2.ElegirVehiculo(volumen, capacidad);

             for(int i = 0; i < vehiculo_list.size(); i++){

                 volumen_max = vehiculo_list.get(i).getVolumen();
                 capacidad_max = vehiculo_list.get(i).getCapacidad();

                 idVehiculo = vehiculo_list.get(i).getId();

                 disponibilidadList = disponibilidadDAO.consultarPorIdVehiculo(idVehiculo);

                 for(int j = 0; j < disponibilidadList.size(); j++){
                     suma_volumen =  suma_volumen + disponibilidadList.get(j).getActualvolumen();
                     suma_capacidad = suma_capacidad + disponibilidadList.get(j).getActualcapacidad();
                 }                          
                 
                 volumen_encomienda = suma_volumen + volumen;
                 capacidad_encomienda = suma_capacidad + capacidad;
                 
                 if( volumen_encomienda<  volumen_max &&  capacidad_encomienda<  capacidad_max ){

                     disponibilidad.setIdVehiculo(idVehiculo);
                     disponibilidad.setIdTipoEncomienda(idTipoEncomienda);
                     disponibilidad.setActualvolumen(volumen_encomienda);
                     disponibilidad.setActualcapacidad(capacidad_encomienda);
                     disponibilidad.setSituacion(situacion);
                     disponibilidadDAO.insertar(disponibilidad);
                     break;
                 }                   
                suma_volumen = 0;
                suma_capacidad = 0;
                volumen_encomienda = 0;
                capacidad_encomienda = 0;         
         }               
               
            Vehiculo vehiculo_buscado = vehiculoDAO2.BuscarPorId(idVehiculo);
            
            vehiculo2.setId(vehiculo_buscado.getId());
            vehiculo2.setPlaca(vehiculo_buscado.getPlaca());
            vehiculo2.setVolumen(vehiculo_buscado.getVolumen());
            vehiculo2.setCapacidad(vehiculo_buscado.getCapacidad());           
           } catch (Exception e) {
           System.out.println ("El error es: " + e.getMessage());
           e.printStackTrace();
           }
           
             return vehiculo2;    
    }          
*/    
}
