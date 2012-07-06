/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.pmarlen.client.textreport.TicketPrinter;
import com.pmarlen.model.beans.*;
import java.util.ArrayList;
import org.junit.*;
import org.junit.Assert.*;

/**
 *
 * @author alfredo
 */
//@Ignore
public class TicketPrinterTest {
    
    public TicketPrinterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testGenerateTicket() {
        
        PedidoVenta pedidoVenta = new PedidoVenta(1234);
        
        Cliente cliente = new Cliente();
        cliente.setCalle("MAXIMILIANO");
        cliente.setNumExterior("EX11");
        cliente.setNumInterior("IN11");
        Poblacion poblacion = new Poblacion();
        poblacion.setNombre("SAN JUAN TEJUXILIAPAN EL GRANDE");
        poblacion.setEntidadFederativa("VERACRUZ");
        poblacion.setMunicipioODelegacion("SANTA VERTA DE LA BURGUER");
        
        cliente.setPoblacion(poblacion);
        
        cliente.setNombreEstablecimiento("NOMBRE DE NOMBRE DEL CLIENTE CON ESTABLECIMIENTO DEL\tCLIENTE");
        
        pedidoVenta.setCliente(cliente);
        pedidoVenta.setComentarios("comentarios");
        pedidoVenta.setEsFiscal(1);
        pedidoVenta.setFactoriva(new Double(0.16));
        pedidoVenta.setFormaDePago(new FormaDePago());
        
        Usuario agente = new Usuario();
        agente.setNombreCompleto("AGENTE   CON     NOMBRE\t\nMUY MUY LARGO Y PATERNO LARGO Y MATERNO LARGO");
        
        pedidoVenta.setUsuario(agente);
        
        ArrayList<PedidoVentaDetalle> pvdList = new ArrayList<PedidoVentaDetalle>();
        
        for(int i =0; i<15;i++) {
            final PedidoVentaDetalle pvd = new PedidoVentaDetalle();
            
            pvd.setCantidad(25+(int)(Math.random()*25.0));
            pvd.setDescuentoAplicado(Math.random()>=0.5?0.1:0.0);
            pvd.setPrecioBase(250+(int)(Math.random()*250.0));
            
            Producto producto = new Producto();
            
            producto.setNombre("Producto XXX_"+i);
            producto.setPresentacion(Integer.toHexString(producto.getNombre().hashCode()).toUpperCase()+" "+Integer.toHexString(producto.getNombre().hashCode()).toUpperCase());
            
            pvd.setProducto(producto);
            
            pvdList.add(pvd);
        }
        
        pedidoVenta.setPedidoVentaDetalleCollection(pvdList);
        
        TicketPrinter.generateTicket(pedidoVenta);
    
    }
}
