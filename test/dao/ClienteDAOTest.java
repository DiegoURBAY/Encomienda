/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidad.Cliente;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author usuario
 */
public class ClienteDAOTest {
    
    public ClienteDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class ClienteDAO.
     */
    /*
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        ClienteDAO.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
    /**
     * Test of insertar method, of class ClienteDAO.
     */
    @Test
    public void testInsertar() throws Exception {
        System.out.println("REGISTRANDO.....");

        String dni = "73099999";
        String nombre = "miguel";
        String email = "miguel@gmail.com";
        String telefono = "979528870";
        String usuario = "miguel";
        String contra = "123";
        
        Cliente cliente = new Cliente();
        
        cliente.setIdentificador(dni);
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setUsuario(usuario);
        cliente.setContraseña(contra);
        cliente.setNivel(2);
        
        ClienteDAO instance = new ClienteDAO();
        instance.insertar(cliente);
    
        System.out.println("BUSCANDO.....");
        Cliente cliente_creado = instance.BuscarPorDni(dni);
        
        if(cliente_creado != null){
            System.out.println("---Cliente encontrado con DNI: "+cliente_creado.getIdentificador()+" ----");
            System.out.println("Nombre: "+cliente_creado.getNombre());
            System.out.println("Correo: "+cliente_creado.getEmail());
            System.out.println("Teléfono: "+cliente_creado.getTelefono());
            System.out.println("Usuario: "+cliente_creado.getUsuario());
            System.out.println("Contraseña: "+cliente_creado.getContraseña());
            System.out.println("Nivel: "+cliente_creado.getNivel());
        }
        else{
            fail("No se registró cliente");
        }
     
    }

    /**
     * Test of eliminar method, of class ClienteDAO.
     
    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        Object obj = null;
        ClienteDAO instance = new ClienteDAO();
        instance.eliminar(obj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    /**
     * Test of modificar method, of class ClienteDAO.
    
    @Test
    public void testModificar() throws Exception {
        System.out.println("modificar");
        Object obj = null;
        ClienteDAO instance = new ClienteDAO();
        instance.modificar(obj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
 */
    /**
     * Test of actualizarPromo method, of class ClienteDAO.
     
    @Test
    public void testActualizarPromo() throws Exception {
        System.out.println("actualizarPromo");
        Object obj = null;
        ClienteDAO instance = new ClienteDAO();
        instance.actualizarPromo(obj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    /**
     * Test of consultar method, of class ClienteDAO.

    @Test
    public void testConsultar() throws Exception {
        System.out.println("consultar");
        ClienteDAO instance = new ClienteDAO();
        List<Cliente> expResult = null;
        List<Cliente> result = instance.consultar();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */
    /**
     * Test of BuscarPorId method, of class ClienteDAO.
   
    @Test
    public void testBuscarPorId() throws Exception {
        System.out.println("BuscarPorId");
        int id = 0;
        ClienteDAO instance = new ClienteDAO();
        Cliente expResult = null;
        Cliente result = instance.BuscarPorId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
  */
    /**
     * Test of BuscarPorUsuario method, of class ClienteDAO.
    
    @Test
    public void testBuscarPorUsuario() throws Exception {
        System.out.println("BuscarPorUsuario");
        String usuario = "";
        ClienteDAO instance = new ClienteDAO();
        Cliente expResult = null;
        Cliente result = instance.BuscarPorUsuario(usuario);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
 */
    /**
     * Test of BuscarPorDni method, of class ClienteDAO.
    
    @Test
    public void testBuscarPorDni() throws Exception {
        System.out.println("BuscarPorDni");
        String dni = "";
        ClienteDAO instance = new ClienteDAO();
        Cliente expResult = null;
        Cliente result = instance.BuscarPorDni(dni);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
 */
    /**
     * Test of ConsultarByEmail method, of class ClienteDAO.
   
    @Test
    public void testConsultarByEmail() throws Exception {
        System.out.println("ConsultarByEmail");
        String email = "";
        ClienteDAO instance = new ClienteDAO();
        Cliente expResult = null;
        Cliente result = instance.ConsultarByEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
  */
    /**
     * Test of ConsultarEmailContra method, of class ClienteDAO.
  
    @Test
    public void testConsultarEmailContra() throws Exception {
        System.out.println("ConsultarEmailContra");
        String email = "";
        String contraseña = "";
        ClienteDAO instance = new ClienteDAO();
        boolean expResult = false;
        boolean result = instance.ConsultarEmailContra(email, contraseña);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
       */
}
