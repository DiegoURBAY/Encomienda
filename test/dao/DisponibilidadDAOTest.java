/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidad.Disponibilidad;
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
public class DisponibilidadDAOTest {
    
    public DisponibilidadDAOTest() {
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
     * Test of main method, of class DisponibilidadDAO.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        DisponibilidadDAO.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertar method, of class DisponibilidadDAO.
     */
    @Test
    public void testInsertar() throws Exception {
        System.out.println("insertar");
        Object obj = null;
        DisponibilidadDAO instance = new DisponibilidadDAO();
        instance.insertar(obj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminar method, of class DisponibilidadDAO.
     */
    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        Object obj = null;
        DisponibilidadDAO instance = new DisponibilidadDAO();
        instance.eliminar(obj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modificar method, of class DisponibilidadDAO.
     */
    @Test
    public void testModificar() throws Exception {
        System.out.println("modificar");
        Object obj = null;
        DisponibilidadDAO instance = new DisponibilidadDAO();
        instance.modificar(obj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consultar method, of class DisponibilidadDAO.
     */
    @Test
    public void testConsultar() throws Exception {
        System.out.println("consultar");
        DisponibilidadDAO instance = new DisponibilidadDAO();
        List<Disponibilidad> expResult = null;
        List<Disponibilidad> result = instance.consultar();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of BuscarPorId method, of class DisponibilidadDAO.
     */
    @Test
    public void testBuscarPorId() throws Exception {
        System.out.println("BuscarPorId");
        int id = 0;
        DisponibilidadDAO instance = new DisponibilidadDAO();
        Object expResult = null;
        Object result = instance.BuscarPorId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consultarPorIdVehiculo method, of class DisponibilidadDAO.
     */
    @Test
    public void testConsultarPorIdVehiculo() throws Exception {
        System.out.println("consultarPorIdVehiculo");
        int idVehiculo = 0;
        DisponibilidadDAO instance = new DisponibilidadDAO();
        List<Disponibilidad> expResult = null;
        List<Disponibilidad> result = instance.consultarPorIdVehiculo(idVehiculo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of consultarPorIdTipoEncomienta method, of class DisponibilidadDAO.
     */
    @Test
    public void testConsultarPorIdTipoEncomienta() throws Exception {
        System.out.println("consultarPorIdTipoEncomienta");
        int idTipoEncomienda = 0;
        DisponibilidadDAO instance = new DisponibilidadDAO();
        Disponibilidad expResult = null;
        Disponibilidad result = instance.consultarPorIdTipoEncomienta(idTipoEncomienda);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarPorTipoEncomienda method, of class DisponibilidadDAO.
     */
    @Test
    public void testEliminarPorTipoEncomienda() throws Exception {
        System.out.println("eliminarPorTipoEncomienda");
        Object obj = null;
        DisponibilidadDAO instance = new DisponibilidadDAO();
        instance.eliminarPorTipoEncomienda(obj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
