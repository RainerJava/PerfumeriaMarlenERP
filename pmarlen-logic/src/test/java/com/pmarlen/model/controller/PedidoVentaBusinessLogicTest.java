package com.pmarlen.model.controller;

import com.pmarlen.businesslogic.LogicaFinaciera;
import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.*;

import java.sql.SQLException;
import java.util.*;

import java.util.logging.Level;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PedidoVentaBusinessLogicTest {

    private Logger logger = LoggerFactory.getLogger(PedidoVentaBusinessLogicTest.class);
    @Autowired
    private ProductoJpaController productoJpaController;
    @Autowired
    private MarcaJpaController marcaJpaController;
    @Autowired
    private EmpresaJpaController empresaJpaController;
    @Autowired
    private LineaJpaController lineaJpaController;
    @Autowired
    private ClienteJpaController clienteJpaController;
    @Autowired
    private FormaDePagoJpaController formaDePagoJpaController;
    @Autowired
    private PedidoVentaJpaController pedidoVentaJpaController;
    @Autowired
    private PedidoVentaEstadoJpaController pedidoVentaEstadoJpaController;
    @Autowired
    private PedidoVentaDetalleJpaController pedidoVentaDetalleJpaController;
    @Autowired
    private UsuarioJpaController usuarioJpaController;
    @Autowired
    private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;

    @Test
    //@Ignore
    public void testCrearPedidoVentaByPedidoVentaBusinessLogic() throws SQLException {
        logger.info(">>testCrearPedidoVentaByPedidoVentaBusinessLogic: ============================== INITIALIZED CONTEXT ============================");

        assertNotNull("pedidoVentaJpaController is null.", pedidoVentaJpaController);
        assertNotNull("formaDePagoJpaController is null.", formaDePagoJpaController);
        assertNotNull("clienteJpaController is null.", clienteJpaController);
        assertNotNull("productoJpaController is null.", productoJpaController);
        assertNotNull("marcaJpaController is null.", marcaJpaController);
        assertNotNull("lineaJpaController is null.", lineaJpaController);
        assertNotNull("pedidoVentaEstadoJpaController is null.", pedidoVentaEstadoJpaController);
        assertNotNull("usuarioJpaController is null.", usuarioJpaController);
        assertNotNull("pedidoVentaBusinessLogic is null.", pedidoVentaBusinessLogic);

        List<Producto> productoList = productoJpaController.findProductoEntities();
        List<PedidoVentaDetalle> detalleVentaPedidoList = new ArrayList<PedidoVentaDetalle>();

        PedidoVenta pedidoVenta = new PedidoVenta();
        if (Math.random() >= 0.5) {
            pedidoVenta.setEsFiscal(1);
        } else {
            pedidoVenta.setEsFiscal(0);
        }

        for (Producto producto : productoList) {
            if (Math.random() >= 0.5) {
                Producto productoBuscado = productoJpaController.findProducto(producto.getId());
                PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();

                detalleVentaPedido.setProducto(productoBuscado);
                detalleVentaPedido.setPrecioBase(productoBuscado.getPrecioBase());
                detalleVentaPedido.setDescuentoAplicado(Math.random() >= 0.5 ? productoBuscado.getFactorMaxDesc() : 0.0);
                int cantMax = 0;
                Collection<AlmacenProducto> almacenProductoCollection = productoBuscado.getAlmacenProductoCollection();
                for (AlmacenProducto almacenProducto : almacenProductoCollection) {
                    cantMax += almacenProducto.getCantidadActual();
                }
                detalleVentaPedido.setCantidad(cantMax / 2);                
                //detalleVentaPedido.setPedidoVentaDetallePK(new PedidoVentaDetallePK(0, detalleVentaPedido.getProducto().getId()));
                
                detalleVentaPedidoList.add(detalleVentaPedido);
            }
        }

        Cliente cliente = clienteJpaController.findCliente(1);
        assertNotNull("cliente is null.", cliente);

        Usuario usuario = usuarioJpaController.findUsuario("test1");
        Usuario usuarioModifico = usuarioJpaController.findUsuario("test2");
        assertNotNull("usuario is null.", usuario);


        FormaDePago formaDePago = formaDePagoJpaController.findFormaDePago(1);
        assertNotNull("formaDePago is null.", formaDePago);

        pedidoVenta.setCliente(cliente);
        pedidoVenta.setUsuario(usuario);
        pedidoVenta.setFormaDePago(formaDePago);
        pedidoVenta.setComentarios("Prueba desde TEST@" + (new Date()));        
        pedidoVenta.setPedidoVentaDetalleCollection(detalleVentaPedidoList);

        List<PedidoVenta> pedidoVentaListBefore = pedidoVentaJpaController.findPedidoVentaEntities();

        pedidoVentaBusinessLogic.crearPedidoCapturado(pedidoVenta,usuarioModifico);
        
        logger.info(">> PedidoVenta creado: pedidoVenta.getId()="+pedidoVenta.getId());

        List<PedidoVenta> pedidoVentaListAfter = pedidoVentaJpaController.findPedidoVentaEntities();

        assertEquals("The # of beans in List: " + (pedidoVentaListBefore.size() + 1) + " != " + pedidoVentaListAfter.size(),
                pedidoVentaListBefore.size() + 1, pedidoVentaListAfter.size());

        PedidoVenta pedidoVentaAfter = pedidoVentaJpaController.findPedidoVenta(pedidoVenta.getId());
        Collection<PedidoVentaDetalle> detalleVentaPedidoCollectionAfter = pedidoVentaAfter.getPedidoVentaDetalleCollection();

        assertEquals("The # of beans in List: " + detalleVentaPedidoCollectionAfter.size()  + " != " + detalleVentaPedidoList.size(),
                detalleVentaPedidoCollectionAfter.size(), detalleVentaPedidoList.size());    
    }

	@Test
    //@Ignore
    public void testGuardarPedidosEnviadosVentaByPedidoVentaBusinessLogic() throws SQLException {
        logger.info(">>testGuardarPedidosEnviadosVentaByPedidoVentaBusinessLogic: ============================== INITIALIZED CONTEXT ============================");

        assertNotNull("pedidoVentaJpaController is null.", pedidoVentaJpaController);
        assertNotNull("formaDePagoJpaController is null.", formaDePagoJpaController);
        assertNotNull("clienteJpaController is null.", clienteJpaController);
        assertNotNull("productoJpaController is null.", productoJpaController);
        assertNotNull("marcaJpaController is null.", marcaJpaController);
        assertNotNull("lineaJpaController is null.", lineaJpaController);
        assertNotNull("pedidoVentaEstadoJpaController is null.", pedidoVentaEstadoJpaController);
        assertNotNull("usuarioJpaController is null.", usuarioJpaController);
        assertNotNull("pedidoVentaBusinessLogic is null.", pedidoVentaBusinessLogic);

        List<Producto> productoList = productoJpaController.findProductoEntities();
		int numPedidos = (int)(5+Math.random()*4.0);
		
		List<PedidoVenta> pedidoVentaToInsert = new ArrayList<PedidoVenta>();
		
		Usuario usuarioModifico = usuarioJpaController.findUsuario("test2");
		for(int numPedido = 0; numPedido<numPedidos;numPedido++){
		
			List<PedidoVentaDetalle> detalleVentaPedidoList = new ArrayList<PedidoVentaDetalle>();

			PedidoVenta pedidoVenta = new PedidoVenta();
			if (Math.random() >= 0.5) {
				pedidoVenta.setEsFiscal(1);
			} else {
				pedidoVenta.setEsFiscal(0);
			}

			for (Producto producto : productoList) {
				if (Math.random() >= 0.5) {
					Producto productoBuscado = productoJpaController.findProducto(producto.getId());
					PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();

					detalleVentaPedido.setProducto(productoBuscado);
					detalleVentaPedido.setPrecioBase(productoBuscado.getPrecioBase());
					detalleVentaPedido.setDescuentoAplicado(Math.random() >= 0.5 ? productoBuscado.getFactorMaxDesc() : 0.0);
					int cantMax = 0;
					Collection<AlmacenProducto> almacenProductoCollection = productoBuscado.getAlmacenProductoCollection();
					for (AlmacenProducto almacenProducto : almacenProductoCollection) {
						cantMax += almacenProducto.getCantidadActual();
					}
					detalleVentaPedido.setCantidad(cantMax / 2);                
					//detalleVentaPedido.setPedidoVentaDetallePK(new PedidoVentaDetallePK(0, detalleVentaPedido.getProducto().getId()));

					detalleVentaPedidoList.add(detalleVentaPedido);
				}
			}

			Cliente cliente = null;
			if (Math.random() >= 0.5) {
				cliente = new Cliente();
				Cliente clienteParecido = clienteJpaController.findCliente(1);
				
				BeanUtils.copyProperties(clienteParecido, cliente, new String[]{"id","cuentaBancariaCollection","contactoCollection","pedidoVentaCollection"});
				cliente.setId(clienteParecido.getId()+(int)(10000*Math.random()));
				cliente.setRazonSocial(clienteParecido.getRazonSocial()+"(PARECIDO)");
				cliente.setNombreEstablecimiento(clienteParecido.getNombreEstablecimiento()+"(PARECIDO)");
			} else {
				cliente = clienteJpaController.findCliente(1);
			}
			
			assertNotNull("cliente is null.", cliente);

			Usuario usuario = usuarioJpaController.findUsuario("test1");			
			assertNotNull("usuario is null.", usuario);


			FormaDePago formaDePago = formaDePagoJpaController.findFormaDePago(1);
			assertNotNull("formaDePago is null.", formaDePago);

			pedidoVenta.setCliente(cliente);
			pedidoVenta.setUsuario(usuario);
			pedidoVenta.setFormaDePago(formaDePago);
			pedidoVenta.setComentarios("Prueba desde TEST@" + (new Date()));        
			pedidoVenta.setPedidoVentaDetalleCollection(detalleVentaPedidoList);
			
			pedidoVentaToInsert.add(pedidoVenta);
		}
		
		
		List<PedidoVenta> pedidoVentaListBefore = pedidoVentaJpaController.findPedidoVentaEntities();
        try {
            pedidoVentaBusinessLogic.guardarPedidosEnviados(usuarioModifico, pedidoVentaToInsert);
        } catch (Exception ex) {
            logger.error(" ==>> pedidoVentaBusinessLogic.guardarPedidosEnviados: ", ex);
        }
		
        List<PedidoVenta> pedidoVentaListAfter = pedidoVentaJpaController.findPedidoVentaEntities();

        assertEquals("The # of beans in List: " + (pedidoVentaListBefore.size() + numPedidos) + " != " + pedidoVentaListAfter.size(),
                pedidoVentaListBefore.size() + numPedidos, pedidoVentaListAfter.size());

    }
	
    //@Ignore
    @Test
    public void testEditarPedidoVentaByPedidoVentaBusinessLogic() throws SQLException {
        logger.info(">>testEditarPedidoVentaByPedidoVentaBusinessLogic: ============================== INITIALIZED CONTEXT ============================");

        assertNotNull("pedidoVentaJpaController is null.", pedidoVentaJpaController);
        assertNotNull("formaDePagoJpaController is null.", formaDePagoJpaController);
        assertNotNull("clienteJpaController is null.", clienteJpaController);
        assertNotNull("productoJpaController is null.", productoJpaController);
        assertNotNull("marcaJpaController is null.", marcaJpaController);
        assertNotNull("lineaJpaController is null.", lineaJpaController);
        assertNotNull("pedidoVentaEstadoJpaController is null.", pedidoVentaEstadoJpaController);
        assertNotNull("usuarioJpaController is null.", usuarioJpaController);
        assertNotNull("pedidoVentaBusinessLogic is null.", pedidoVentaBusinessLogic);

        List<Producto> productoList = productoJpaController.findProductoEntities();
        List<PedidoVentaDetalle> detalleVentaPedidoList = new ArrayList<PedidoVentaDetalle>();

        PedidoVenta pedidoVenta = new PedidoVenta();
        if (Math.random() >= 0.5) {
            pedidoVenta.setEsFiscal(1);
            pedidoVenta.setFactoriva(LogicaFinaciera.getImpuestoIVA());
        } else {
            pedidoVenta.setEsFiscal(0);
        }

        for (Producto producto : productoList) {
            if (Math.random() >= 0.5) {
                Producto productoBuscado = productoJpaController.findProducto(producto.getId());
                PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();

                detalleVentaPedido.setProducto(productoBuscado);
                detalleVentaPedido.setPrecioBase(productoBuscado.getPrecioBase());
                detalleVentaPedido.setDescuentoAplicado(Math.random() >= 0.5 ? productoBuscado.getFactorMaxDesc() : 0.0);
                int cantMax = 0;
                Collection<AlmacenProducto> almacenProductoCollection = productoBuscado.getAlmacenProductoCollection();
                for (AlmacenProducto almacenProducto : almacenProductoCollection) {
                    cantMax += almacenProducto.getCantidadActual();
                }
                detalleVentaPedido.setCantidad(cantMax / 2);
                detalleVentaPedido.setPedidoVentaDetallePK(new PedidoVentaDetallePK(0, detalleVentaPedido.getProducto().getId()));

                detalleVentaPedidoList.add(detalleVentaPedido);
            }
        }

        Cliente cliente = clienteJpaController.findCliente(1);
        assertNotNull("cliente is null.", cliente);

        Usuario usuario = usuarioJpaController.findUsuario("test1");
        Usuario usuarioModifico = usuarioJpaController.findUsuario("test2");
        assertNotNull("usuario is null.", usuario);


        FormaDePago formaDePago = formaDePagoJpaController.findFormaDePago(1);
        assertNotNull("formaDePago is null.", formaDePago);

        pedidoVenta.setCliente(cliente);
        pedidoVenta.setUsuario(usuario);
        pedidoVenta.setFormaDePago(formaDePago);
        pedidoVenta.setComentarios("Prueba desde TEST@" + (new Date()));
        
        pedidoVenta.setPedidoVentaDetalleCollection(detalleVentaPedidoList);

        List<PedidoVenta> pedidoVentaListBefore = pedidoVentaJpaController.findPedidoVentaEntities();

        pedidoVentaBusinessLogic.crearPedidoCapturado(pedidoVenta,usuarioModifico);

        logger.info(">> PedidoVenta creado: pedidoVenta.getId()="+pedidoVenta.getId());

        List<PedidoVenta> pedidoVentaListAfter = pedidoVentaJpaController.findPedidoVentaEntities();

        assertEquals("The # of beans in List: " + (pedidoVentaListBefore.size() + 1) + " != " + pedidoVentaListAfter.size(),
                pedidoVentaListBefore.size() + 1, pedidoVentaListAfter.size());
        //----------------------------------------------------------------------
        PedidoVenta pedidoVentaAfter = pedidoVentaJpaController.findPedidoVenta(pedidoVenta.getId());
        Collection<PedidoVentaDetalle> detalleVentaPedidoCollectionAfter = pedidoVentaAfter.getPedidoVentaDetalleCollection();

        assertEquals("The # of beans in List: " + detalleVentaPedidoCollectionAfter.size()  + " != " + detalleVentaPedidoList.size(),
                detalleVentaPedidoCollectionAfter.size(), detalleVentaPedidoList.size());

        //======================================================================

        Collection<PedidoVentaDetalle> detalleVentaPedidoCollectionChanged = pedidoVentaAfter.getPedidoVentaDetalleCollection();
        List<PedidoVentaDetalle> detalleVentaPedidoCollectionToDelete = new ArrayList<PedidoVentaDetalle>();

        for(PedidoVentaDetalle dvp: detalleVentaPedidoCollectionChanged) {
            if (Math.random() >= 0.5) {
                detalleVentaPedidoCollectionToDelete.add(dvp);
            }
        }

        for(PedidoVentaDetalle dvpDelete: detalleVentaPedidoCollectionToDelete) {
            assertTrue("Cant delete PedidoVentaDetalle from collection:",detalleVentaPedidoCollectionChanged.remove(dvpDelete));
        }

        for (Producto producto : productoList) {
            if (Math.random() >= 0.5) {
                boolean agregado = false;
                for (PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoCollectionChanged) {
                    if(detalleVentaPedido.getProducto().equals(producto)){
                        agregado = true;
                        break;
                    }
                }
                if( ! agregado ) {                    
                    Producto productoBuscado = productoJpaController.findProducto(producto.getId());
                    PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();

                    detalleVentaPedido.setProducto(productoBuscado);
                    detalleVentaPedido.setPrecioBase(productoBuscado.getPrecioBase());
                    detalleVentaPedido.setDescuentoAplicado(Math.random() >= 0.5 ? productoBuscado.getFactorMaxDesc() : 0.0);
                    int cantMax = 0;
                    Collection<AlmacenProducto> almacenProductoCollection = productoBuscado.getAlmacenProductoCollection();
                    for (AlmacenProducto almacenProducto : almacenProductoCollection) {
                        cantMax += almacenProducto.getCantidadActual();
                    }
                    detalleVentaPedido.setCantidad(cantMax / 2);
                    detalleVentaPedido.setPedidoVenta(pedidoVentaAfter);
                    //detalleVentaPedido.setPedidoVentaDetallePK(new PedidoVentaDetallePK(pedidoVentaAfter.getId(), detalleVentaPedido.getProducto().getId()));

                    detalleVentaPedidoCollectionChanged.add(detalleVentaPedido);
                }
            }
        }

        Cliente clienteChanged = clienteJpaController.findCliente(2);
        assertNotNull("clienteChanged is null.", clienteChanged);

        Usuario usuarioChanged = usuarioJpaController.findUsuario("test2");
        assertNotNull("usuarioChanged is null.", usuarioChanged);

        FormaDePago formaDePagoChanged = formaDePagoJpaController.findFormaDePago(2);
        assertNotNull("formaDePagoChanged is null.", formaDePagoChanged);

        pedidoVentaAfter.setCliente(clienteChanged);
        pedidoVentaAfter.setUsuario(usuarioChanged);
        pedidoVentaAfter.setFormaDePago(formaDePago);
        pedidoVentaAfter.setComentarios("Changed TEST@" + (new Date()));        
        pedidoVentaAfter.setPedidoVentaDetalleCollection(detalleVentaPedidoCollectionChanged);
        
        try {
            pedidoVentaBusinessLogic.sincronizarPedido(pedidoVentaAfter,usuarioChanged);
            logger.info(">> pedidoVentaAfter editado");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            fail(ex.getMessage());
        }

    }

    /**
     * @return the productoJpaController
     */
    public ProductoJpaController getProductoJpaController() {
        return productoJpaController;
    }

    /**
     * @param productoJpaController the productoJpaController to set
     */
    public void setProductoJpaController(ProductoJpaController productoJpaController) {
        this.productoJpaController = productoJpaController;
    }

    /**
     * @return the marcaJpaController
     */
    public MarcaJpaController getMarcaJpaController() {
        return marcaJpaController;
    }

    /**
     * @param marcaJpaController the marcaJpaController to set
     */
    public void setMarcaJpaController(MarcaJpaController marcaJpaController) {
        this.marcaJpaController = marcaJpaController;
    }

    /**
     * @return the empresaJpaController
     */
    public EmpresaJpaController getEmpresaJpaController() {
        return empresaJpaController;
    }

    /**
     * @param empresaJpaController the empresaJpaController to set
     */
    public void setEmpresaJpaController(EmpresaJpaController empresaJpaController) {
        this.empresaJpaController = empresaJpaController;
    }

    /**
     * @return the lineaJpaController
     */
    public LineaJpaController getLineaJpaController() {
        return lineaJpaController;
    }

    /**
     * @param lineaJpaController the lineaJpaController to set
     */
    public void setLineaJpaController(LineaJpaController lineaJpaController) {
        this.lineaJpaController = lineaJpaController;
    }

    /**
     * @return the clienteJpaController
     */
    public ClienteJpaController getClienteJpaController() {
        return clienteJpaController;
    }

    /**
     * @param clienteJpaController the clienteJpaController to set
     */
    public void setClienteJpaController(ClienteJpaController clienteJpaController) {
        this.clienteJpaController = clienteJpaController;
    }

    /**
     * @return the formaDePagoJpaController
     */
    public FormaDePagoJpaController getFormaDePagoJpaController() {
        return formaDePagoJpaController;
    }

    /**
     * @param formaDePagoJpaController the formaDePagoJpaController to set
     */
    public void setFormaDePagoJpaController(FormaDePagoJpaController formaDePagoJpaController) {
        this.formaDePagoJpaController = formaDePagoJpaController;
    }

    /**
     * @return the pedidoVentaJpaController
     */
    public PedidoVentaJpaController getPedidoVentaJpaController() {
        return pedidoVentaJpaController;
    }

    /**
     * @param pedidoVentaJpaController the pedidoVentaJpaController to set
     */
    public void setPedidoVentaJpaController(PedidoVentaJpaController pedidoVentaJpaController) {
        this.pedidoVentaJpaController = pedidoVentaJpaController;
    }

    /**
     * @return the pedidoVentaEstadoJpaController
     */
    public PedidoVentaEstadoJpaController getPedidoVentaEstadoJpaController() {
        return pedidoVentaEstadoJpaController;
    }

    /**
     * @param pedidoVentaEstadoJpaController the pedidoVentaEstadoJpaController to set
     */
    public void setPedidoVentaEstadoJpaController(PedidoVentaEstadoJpaController pedidoVentaEstadoJpaController) {
        this.pedidoVentaEstadoJpaController = pedidoVentaEstadoJpaController;
    }

    /**
     * @return the pedidoVentaDetalleJpaController
     */
    public PedidoVentaDetalleJpaController getPedidoVentaDetalleJpaController() {
        return pedidoVentaDetalleJpaController;
    }

    /**
     * @param pedidoVentaDetalleJpaController the pedidoVentaDetalleJpaController to set
     */
    public void setPedidoVentaDetalleJpaController(PedidoVentaDetalleJpaController pedidoVentaDetalleJpaController) {
        this.pedidoVentaDetalleJpaController = pedidoVentaDetalleJpaController;
    }

    /**
     * @return the usuarioJpaController
     */
    public UsuarioJpaController getUsuarioJpaController() {
        return usuarioJpaController;
    }

    /**
     * @param usuarioJpaController the usuarioJpaController to set
     */
    public void setUsuarioJpaController(UsuarioJpaController usuarioJpaController) {
        this.usuarioJpaController = usuarioJpaController;
    }

    /**
     * @return the pedidoVentaBusinessLogic
     */
    public PedidoVentaBusinessLogic getPedidoVentaBusinessLogic() {
        return pedidoVentaBusinessLogic;
    }

    /**
     * @param pedidoVentaBusinessLogic the pedidoVentaBusinessLogic to set
     */
    public void setPedidoVentaBusinessLogic(PedidoVentaBusinessLogic pedidoVentaBusinessLogic) {
        this.pedidoVentaBusinessLogic = pedidoVentaBusinessLogic;
    }
}
