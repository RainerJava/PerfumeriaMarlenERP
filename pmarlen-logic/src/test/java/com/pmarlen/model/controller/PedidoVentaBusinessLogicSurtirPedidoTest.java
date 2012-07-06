package com.pmarlen.model.controller;

import com.pmarlen.businesslogic.LogicaFinaciera;
import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.model.beans.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PedidoVentaBusinessLogicSurtirPedidoTest {

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
    public void testSurtirPedidoVenta() throws SQLException {
        logger.info("============================== INITIALIZED CONTEXT ============================");
        SimpleDateFormat sdfDefault  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        SimpleDateFormat sdfExtended = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS zzzzzz (Z)");
        Date dateSystem = new Date();
        logger.debug("-->> TimeZone.getDefault()="+TimeZone.getDefault().getDisplayName()+
                ", Time=defaultformat:"+sdfDefault.format(dateSystem)+", ExtendedFormat:"+sdfExtended.format(dateSystem));        

        assertNotNull("pedidoVentaJpaController is null.", pedidoVentaJpaController);
        assertNotNull("formaDePagoJpaController is null.", formaDePagoJpaController);
        assertNotNull("clienteJpaController is null.", clienteJpaController);
        assertNotNull("productoJpaController is null.", productoJpaController);
        assertNotNull("marcaJpaController is null.", marcaJpaController);
        assertNotNull("lineaJpaController is null.", lineaJpaController);
        assertNotNull("pedidoVentaEstadoJpaController is null.", pedidoVentaEstadoJpaController);
        assertNotNull("usuarioJpaController is null.", usuarioJpaController);
        assertNotNull("pedidoVentaBusinessLogic is null.", pedidoVentaBusinessLogic);

        //======================================================================

        ArrayList<Producto> productoListPedido1 = new ArrayList<Producto>();
        List<PedidoVentaDetalle> detalleVentaPedido1List = new ArrayList<PedidoVentaDetalle>();

        PedidoVenta pedidoVenta1 = new PedidoVenta();
        pedidoVenta1.setEsFiscal(1);
        pedidoVenta1.setFactoriva(LogicaFinaciera.getImpuestoIVA());


        productoListPedido1.add(productoJpaController.findProducto(1));
        productoListPedido1.add(productoJpaController.findProducto(2));
        productoListPedido1.add(productoJpaController.findProducto(3));
        productoListPedido1.add(productoJpaController.findProducto(4));
        productoListPedido1.add(productoJpaController.findProducto(5));
        productoListPedido1.add(productoJpaController.findProducto(6));

        boolean conDescPedido1[] = new boolean[]{
                true,
                false,
                true,
                false,
                true,
                false
        };
        int cantPedidaPedido1[] = new int[]{
                8,
                5,
                4,
                16,
                43,
                15
        };

        for(int indProd=0; indProd < productoListPedido1.size(); indProd++){
            Producto producto = productoListPedido1.get(indProd);

            Producto productoBuscado = productoJpaController.findProducto(producto.getId());
            PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();

            detalleVentaPedido.setProducto(productoBuscado);
            detalleVentaPedido.setPrecioBase(productoBuscado.getPrecioBase());
            detalleVentaPedido.setDescuentoAplicado(conDescPedido1[indProd] ? productoBuscado.getFactorMaxDesc() : 0.0);
            detalleVentaPedido.setCantidad(cantPedidaPedido1[indProd]);            

            detalleVentaPedido1List.add(detalleVentaPedido);
        }

        pedidoVenta1.setCliente(new Cliente(1));
        pedidoVenta1.setUsuario(new Usuario("test1"));
        pedidoVenta1.setFormaDePago(new FormaDePago(1));
        pedidoVenta1.setComentarios("Prueba desde TEST@" + (new Date()));
        
        pedidoVenta1.setPedidoVentaDetalleCollection(detalleVentaPedido1List);

        List<PedidoVenta> pedidoVentaListBefore = pedidoVentaJpaController.findPedidoVentaEntities();

        pedidoVentaBusinessLogic.crearPedidoCapturado(pedidoVenta1,new Usuario("test2"));

        logger.info(">> PedidoVenta creado: pedidoVenta.getId()="+pedidoVenta1.getId());

        List<PedidoVenta> pedidoVentaListAfter = pedidoVentaJpaController.findPedidoVentaEntities();

        assertEquals("The # of beans in List: " + (pedidoVentaListBefore.size() + 1) + " != " + pedidoVentaListAfter.size(),
                pedidoVentaListBefore.size() + 1, pedidoVentaListAfter.size());
        //----------------------------------------------------------------------
        PedidoVenta pedidoVentaAfter = pedidoVentaJpaController.findPedidoVenta(pedidoVenta1.getId());
        Collection<PedidoVentaDetalle> detalleVentaPedidoCollectionAfter = pedidoVentaAfter.getPedidoVentaDetalleCollection();

        assertEquals("The # of beans in List: " + detalleVentaPedidoCollectionAfter.size()  + " != " + detalleVentaPedido1List.size(),
                detalleVentaPedidoCollectionAfter.size(), detalleVentaPedido1List.size());

        //======================================================================

        try {
            pedidoVentaBusinessLogic.sincronizarPedido(pedidoVentaAfter,new Usuario("test2"));
            logger.info(">> Ok, sincronizarPedido");

            pedidoVentaBusinessLogic.verificarPedido(pedidoVentaAfter, new Usuario("test2"));
            logger.info(">> Ok, verificarPedido");

            pedidoVentaBusinessLogic.surtirPedido(pedidoVentaAfter,new Usuario("test2"));
            logger.info(">> Ok, surtirPedido");

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            fail(ex.getMessage());
        }

        List<Producto> productoForReport = pedidoVentaBusinessLogic.findProductoForReport();
        DecimalFormat df = new DecimalFormat("000000");

        for(Producto produto: productoForReport) {
            logger.info("==>>Producto:"+produto.getId());
            Collection<AlmacenProducto> almacenProductoCollection = produto.getAlmacenProductoCollection();
            for(AlmacenProducto ap: almacenProductoCollection){
                logger.info("\t==>>Almacen: CantidadActual=\t"+df.format(ap.getCantidadActual())+"\t"+ap.getAlmacen().getNombre());
            }
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = produto.getMovimientoHistoricoProductoCollection();
            for(MovimientoHistoricoProducto mhp: movimientoHistoricoProductoCollection) {
                logger.info("\t==>>M H P  : Cantidad      =\t"+df.format(mhp.getCantidad())+"\t"+mhp.getAlmacen().getNombre()+"\t"+mhp.getTipoMovimiento().getDescripcion());
            }
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
