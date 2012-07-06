package  com.pmarlen.ws.services;

import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.businesslogic.exception.AuthenticationException;
import com.pmarlen.businesslogic.exception.PedidoVentaException;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.PedidoVentaDetallePK;
import com.pmarlen.model.beans.PedidoVentaEstado;
import com.pmarlen.model.beans.PedidoVentaEstadoPK;
import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.UsuarioJpaController;
import com.pmarlen.wscommons.services.SincronizadorDePedidos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.jws.WebService;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alfred
 */

@WebService(endpointInterface = "com.pmarlen.wscommons.services.SincronizadorDePedidos")
@Repository("sincronizadorDePedidosImpl")
public class SincronizadorDePedidosImpl implements SincronizadorDePedidos{
    private Logger logger;

	private UsuarioJpaController usuarioJpaController;

    private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;

    public SincronizadorDePedidosImpl() {
        logger = LoggerFactory.getLogger(SincronizadorDePedidosImpl.class);
    }

	
	@Autowired
    public void setUsuarioJpaController( UsuarioJpaController usuarioJpaController){
        this.usuarioJpaController = usuarioJpaController;
    }	
	
    @Autowired
    public void setPedidoVentaBusinessLogic(PedidoVentaBusinessLogic pedidoVentaBusinessLogic) {
        this.pedidoVentaBusinessLogic = pedidoVentaBusinessLogic;
    }

    public void enviarPedido(String usuarioId,String password,List<com.pmarlen.wscommons.services.dto.PedidoVenta> pedidoVentaList) 
            throws AuthenticationException, PedidoVentaException{
		
        logger.debug("==>> enviarPedido: authenticateUsuario("+usuarioId+","+password+"); ");
        Usuario usuarioAuthenticated =  null;
        try {
            usuarioAuthenticated =  usuarioJpaController.findUsuario(usuarioId,password);
        } catch(NoResultException nre) {
            nre.printStackTrace(System.err);
            throw new AuthenticationException("ERROR: USER / PASSWORD");            
        }            
        List<PedidoVenta> pedidoVentaJPAList = new ArrayList<PedidoVenta>();

        for(com.pmarlen.wscommons.services.dto.PedidoVenta pv: pedidoVentaList) {
            PedidoVenta pvJPA = new PedidoVenta();

            //==============================================================
            Cliente clienteJPA = new Cliente(pv.getCliente().getId());
            com.pmarlen.wscommons.services.dto.Cliente cliente = pv.getCliente();

            clienteJPA.setCalle(cliente.getCalle());
            clienteJPA.setClasificacionFiscal(cliente.getClasificacionFiscal());
            clienteJPA.setDescripcionRuta(cliente.getDescripcionRuta());
            clienteJPA.setEmail(cliente.getEmail());
            clienteJPA.setFaxes(cliente.getFaxes());
            clienteJPA.setTelefonos(cliente.getTelefonos());
            clienteJPA.setTelefonosMoviles(cliente.getTelefonosMoviles());
            clienteJPA.setFechaCreacion(cliente.getFechaCreacion());
            clienteJPA.setNombreEstablecimiento(cliente.getNombreEstablecimiento());
            clienteJPA.setNumExterior(cliente.getNumExterior());
            clienteJPA.setNumInterior(cliente.getNumInterior());
            clienteJPA.setObservaciones(cliente.getObservaciones());
            clienteJPA.setPlazoDePago(cliente.getPlazoDePago());
            clienteJPA.setPoblacion(new Poblacion(cliente.getPoblacion().getId()));
            clienteJPA.setRazonSocial(cliente.getRazonSocial());
            clienteJPA.setRfc(cliente.getRfc());

            pvJPA.setCliente(clienteJPA);
            //----------------------------------------------------------
            FormaDePago formaDePagoJPA = new FormaDePago(pv.getFormaDePago().getId());
            pvJPA.setFormaDePago(formaDePagoJPA);
            //----------------------------------------------------------
            pvJPA.setComentarios(pv.getComentarios());
            pvJPA.setEsFiscal(pv.getEsFiscal());
            pvJPA.setFactoriva(pv.getFactoriva());
            pvJPA.setUsuario(new Usuario(pv.getUsuario().getUsuarioId()));
            //----------------------------------------------------------
            Collection<com.pmarlen.wscommons.services.dto.PedidoVentaDetalle> pvdList = pv.getPedidoVentaDetalleCollection();
            List<PedidoVentaDetalle> pedidoVentaJPADetalleList = new ArrayList<PedidoVentaDetalle>();

            for(com.pmarlen.wscommons.services.dto.PedidoVentaDetalle pvd: pvdList) {
                PedidoVentaDetalle pvdJPA = new PedidoVentaDetalle();

                pvdJPA.setPedidoVentaDetallePK(new PedidoVentaDetallePK(pvd.getPedidoVentaDetallePK().getPedidoVentaId(), pvd.getPedidoVentaDetallePK().getProductoId()));
                pvdJPA.setCantidad(pvd.getCantidad());
                pvdJPA.setDescuentoAplicado(pvd.getDescuentoAplicado());
                pvdJPA.setPrecioBase(pvd.getPrecioBase());
                pvdJPA.setProducto(new Producto(pvd.getProducto().getId()));                        

                pedidoVentaJPADetalleList.add(pvdJPA);
            }

            pvJPA.setPedidoVentaDetalleCollection(pedidoVentaJPADetalleList);

            //----------------------------------------------------------
            Collection<com.pmarlen.wscommons.services.dto.PedidoVentaEstado> pveList = pv.getPedidoVentaEstadoCollection();
            List<PedidoVentaEstado> pveJPAList = new ArrayList<PedidoVentaEstado>();
            if(pveList != null && pveList.size()>0) {
                for(com.pmarlen.wscommons.services.dto.PedidoVentaEstado pve: pveList) {
                    PedidoVentaEstadoPK pedidoVentaEstadoPKGenerated = new PedidoVentaEstadoPK();
                    //PedidoVentaEstado pveJPA = new PedidoVentaEstado(new PedidoVentaEstadoPK(pve.getPedidoVentaEstadoPK().getPedidoVentaId(),pve.getPedidoVentaEstadoPK().getEstadoId()));
                    pedidoVentaEstadoPKGenerated.setPedidoVentaId(pve.getPedidoVentaEstadoPK().getPedidoVentaId());
                    pedidoVentaEstadoPKGenerated.setEstadoId(pve.getPedidoVentaEstadoPK().getEstadoId());
                    
                    PedidoVentaEstado pveJPA = new PedidoVentaEstado();
                    pveJPA.setPedidoVentaEstadoPK(pedidoVentaEstadoPKGenerated);
                    
                    pveJPA.setPedidoVenta(pvJPA);
                    pveJPA.setEstado(new Estado(pve.getEstado().getId()));
                    pveJPA.setComentarios(pve.getComentarios());
                    pveJPA.setFecha(pve.getFecha());
                    pveJPA.setUsuario(new Usuario(pve.getUsuario().getUsuarioId()));                                                

                    pveJPAList.add(pveJPA);
                }
            }

            pvJPA.setPedidoVentaEstadoCollection(pveJPAList);
            //----------------------------------------------------------

            pedidoVentaJPAList.add(pvJPA);
        }

        logger.debug("==>> enviarPedido: usuarioAuthenticated ="+usuarioAuthenticated+", pedidoVentaBusinessLogic="+pedidoVentaBusinessLogic);
        try {
            pedidoVentaBusinessLogic.guardarPedidosEnviados(usuarioAuthenticated,pedidoVentaJPAList);
        } catch(Exception ex){
            logger.error("==>> enviarPedido: fail :(", ex);
            throw new PedidoVentaException(PedidoVentaException.ERROR_PERSISTENCIA, ex.getMessage());
        }

	}

    

}
