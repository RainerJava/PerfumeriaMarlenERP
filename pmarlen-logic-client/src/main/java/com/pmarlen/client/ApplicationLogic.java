/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

//import com.pmarlen.client.business.dao.BasicInfoDAO;
import com.pmarlen.businesslogic.exception.AuthenticationException;
import com.pmarlen.businesslogic.exception.PedidoVentaException;
import com.pmarlen.client.model.ApplicationSession;
import com.pmarlen.client.model.ProductoFastDisplayModel;
import com.pmarlen.client.textreport.TicketPrinter;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.PedidoVentaDetallePK;
import com.pmarlen.model.beans.PedidoVentaEstado;
import com.pmarlen.model.beans.PedidoVentaEstadoPK;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.BasicInfoDAO;
import com.pmarlen.model.controller.PersistEntityWithTransactionDAO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alfred
 */
@Controller("applicationLogic")
public class ApplicationLogic {
    
    private ApplicationSession applicationSession;
    
    private SendDataSynchronizer sendDataSynchronizer;
    
    private Logger logger;

    private EntityManagerFactory emf;
	
	@Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }
	
	private BasicInfoDAO basicInfoDAO;
	
	@Autowired
    public void setBasicInfoDAO(BasicInfoDAO basicInfoDAO) {
        this.basicInfoDAO = basicInfoDAO;
    }

	PersistEntityWithTransactionDAO persistEntityWithTransactionDAO;
	
	@Autowired
    public void setPersistEntityWithTransactionDAO(PersistEntityWithTransactionDAO persistEntityWithTransactionDAO) {
        this.persistEntityWithTransactionDAO = persistEntityWithTransactionDAO;
    }    
    
    private double factorIVA;

    public ApplicationLogic() {
        factorIVA = 0.16;
        logger = LoggerFactory.getLogger(ApplicationLogic.class);        
        logger.debug("->ApplicationLogic, created");
        //this.startNewPedidoVentaSession();
    }

    public void deleteProductoFromCurrentPedidoVenta(int prodToDelete) {
        PedidoVentaDetalle dvpFound = null;
        try {
            dvpFound = ((List<PedidoVentaDetalle>) applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection()).get(prodToDelete);
            logger.debug("->dvpFound=" + dvpFound);
            for (PedidoVentaDetalle dpv : applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection()) {
                logger.debug("\tBEFORE ->DPV=" + dpv);
            }
            boolean r = applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection().remove(dvpFound);

            logger.debug("->removed ?" + r);

            for (PedidoVentaDetalle dpv : applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection()) {
                logger.debug("\tAFTER ->DPV=" + dpv);
            }

        } catch (IndexOutOfBoundsException ioe) {
            throw new IllegalArgumentException("Producto [index] Not exist in PedidoVentaDetalle:" + ioe.getMessage());
        }
    }

    public void setMarcaPorLinea(int idMarca) {
        logger.debug("->setMarcaDeProductos: ");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Marca marca = em.find(Marca.class, idMarca);
            applicationSession.setMarcaPorLinea(marca);
        } catch (Exception e) {
            logger.error("Exception caught:", e);
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void setMarcaPorEmpresa(int idMarca) {
        logger.debug("->setMarcaDeProductos: ");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Marca marca = em.find(Marca.class, idMarca);
            applicationSession.setMarcaPorEmpresa(marca);
        } catch (Exception e) {
            logger.error("Exception caught:", e);
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void startNewPedidoVentaSession() {
        applicationSession.setProductoBuscadoActual(null);
        applicationSession.setPedidoVenta(new PedidoVenta());
        applicationSession.getPedidoVenta().
                setPedidoVentaDetalleCollection(new ArrayList<PedidoVentaDetalle>());
    }

    public PedidoVentaDetalle searchProducto(Producto prod) {
        PedidoVentaDetalle dvpFound = null;
        for (PedidoVentaDetalle dvp : applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection()) {
            if (dvp.getProducto().getId().equals(prod.getId())) {
                dvpFound = dvp;
                break;
            }
        }
        return dvpFound;
    }

    public void addProductoToCurrentPedidoVenta(Producto prod) {
        PedidoVentaDetalle dvpFound = searchProducto(prod);
        PedidoVentaDetalle dvp = null;
        if (dvpFound == null) {
            dvp = new PedidoVentaDetalle();
            dvp.setProducto(prod);
            dvp.setCantidad(0);
            dvp.setPrecioBase(prod.getPrecioBase());
            dvp.setDescuentoAplicado(prod.getFactorMaxDesc());
            dvp.setPedidoVentaDetallePK(new PedidoVentaDetallePK(0, prod.getId()));
        } else {
            dvp = dvpFound;
        }

        dvp.setCantidad(dvp.getCantidad() + 1);

        if (dvpFound == null) {
            applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection().add(dvp);
        }
    }

    public void addProductoCajaToCurrentPedidoVenta(Producto prod) {
        PedidoVentaDetalle dvpFound = searchProducto(prod);
        PedidoVentaDetalle dvp = null;
        if (dvpFound == null) {
            dvp = new PedidoVentaDetalle();
            dvp.setProducto(prod);
            dvp.setCantidad(0);
            dvp.setPrecioBase(prod.getPrecioBase());
            dvp.setDescuentoAplicado(prod.getFactorMaxDesc());
            dvp.setPedidoVentaDetallePK(new PedidoVentaDetallePK(0, prod.getId()));
        } else {
            dvp = dvpFound;
        }

        dvp.setCantidad(dvp.getCantidad() + prod.getUnidadesPorCaja());

        if (dvpFound == null) {
            applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection().add(dvp);
        }
    }

    public void addProductoNToCurrentPedidoVenta(Producto prod, int n) {
        PedidoVentaDetalle dvpFound = searchProducto(prod);
        PedidoVentaDetalle dvp = null;
        if (dvpFound == null) {
            dvp = new PedidoVentaDetalle();
            dvp.setProducto(prod);
            dvp.setCantidad(0);
            dvp.setPrecioBase(prod.getPrecioBase());
            dvp.setDescuentoAplicado(prod.getFactorMaxDesc());
            dvp.setPedidoVentaDetallePK(new PedidoVentaDetallePK(0, prod.getId()));
        } else {
            dvp = dvpFound;
        }

        dvp.setCantidad(n);

        if (dvpFound == null) {
            applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection().add(dvp);
        }
    }

    public void addProductoSurtidoNToCurrentPedidoVenta(Producto prod, int n) {
        logger.debug("->addProductoSurtidoNToCurrentPedidoVenta: prod=" + prod.getNombre() + ": caja=" + prod.getUnidadesPorCaja() + ", n=" + n);
        EntityManager em = null;
        List<Producto> surtidoList = null;

        List<Producto> surtidoListOrder = new ArrayList<Producto>();
        List<Integer> surtidoListOrderCant = new ArrayList<Integer>();
        int count = 0;
        int multiploSurtido = 0;
        int reciduoMultiploSurtido = 0;
        try {
            em = emf.createEntityManager();
            logger.debug("->prod: surtido para " + prod.getNombre());
            Query q = em.createQuery("SELECT p FROM Producto p WHERE p.nombre = :nombre and p.unidadMedida = :unidadMedida and p.contenido = :contenido");

            q.setParameter("nombre", prod.getNombre());
            q.setParameter("unidadMedida", prod.getUnidadMedida());
            q.setParameter("contenido", prod.getContenido());

            surtidoList = q.getResultList();
            count = 0;
            if (n < surtidoList.size() - 1) {
                logger.debug("->prod: surtido para " + prod.getNombre());
                n = surtidoList.size();
                multiploSurtido = 1;
                reciduoMultiploSurtido = 0;
            } else {
                multiploSurtido = n / (surtidoList.size() - 1);
                reciduoMultiploSurtido = n % (surtidoList.size() - 1);
            }

            logger.debug("Distribucion surtida: multiploSurtido=" + multiploSurtido + ", reciduoMultiploSurtido=" + reciduoMultiploSurtido);
            int cantPedir;
            double pasoSurtir = (double) ((surtidoList.size() - 1)) / (double) reciduoMultiploSurtido;

            Hashtable<Integer, Boolean> marks = new Hashtable<Integer, Boolean>();
            for (int ip = 1; ip <= reciduoMultiploSurtido; ip++) {
                logger.debug("==>>[" + pasoSurtir + "]ip++ : " + ((int) (ip * pasoSurtir)));
                marks.put((int) (ip * pasoSurtir), Boolean.TRUE);
            }
            int totalPedir = 0;
            for (Producto ps : surtidoList) {
                if (!ps.equals(prod)) {

                    cantPedir = multiploSurtido;
                    Boolean kark = marks.get(count + 1);

                    if (count == surtidoList.size() - 2) {
                        // Es el ultimo
                        logger.debug("\t Quedan muchos ahun !!");
                        cantPedir = n - totalPedir;
                    } else if (kark != null && kark) {
                        logger.debug("\t OK, [" + (count + 1) + "] karked !!");
                        cantPedir += 1;
                    }

                    logger.debug("\t->[" + cantPedir + "]" + ps.getNombre() + "(" + ps.getPresentacion() + ")");
                    surtidoListOrder.add(ps);
                    surtidoListOrderCant.add(cantPedir);

                    totalPedir += cantPedir;
                    count++;
                }
            }
            logger.debug("--------------------");

            for (int j = 0; j < surtidoListOrder.size(); j++) {
                addProductoNToCurrentPedidoVenta(surtidoListOrder.get(j), surtidoListOrderCant.get(j));
            }


        } catch (Exception e) {
            logger.error("Exception caught:", e);
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void editProductoCajaToCurrentPedidoVenta(Producto prod, int cantidadPedida) {
        PedidoVentaDetalle dvpFound = searchProducto(prod);
        PedidoVentaDetalle dvp = null;
        if (dvpFound == null) {
            throw new IllegalArgumentException("Producto not exist in PedidoVentaDetalle");
        }

        if (cantidadPedida == 0) {
            applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection().remove(dvp);
        } else {
            dvp.setCantidad(cantidadPedida);
        }
    }

    public void setClienteToCurrentPedidoVenta(Cliente cliente) {
        applicationSession.getPedidoVenta().setCliente(cliente);
    }

    public void setFormaDePagoToCurrentPedidoVenta(FormaDePago formaDePago) {
        applicationSession.getPedidoVenta().setFormaDePago(formaDePago);
    }

    public void persistCurrentPedidoVenta() throws BusinessException {
        logger.debug("->persistCurrentPedidoVenta: ");
        
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            em.getTransaction().begin();
            logger.debug("->begin transaction");
            PedidoVenta nuevoPedidoVenta = new PedidoVenta();
            logger.debug("->prepare PedidoVenta");
			
			
            nuevoPedidoVenta.setUsuario(em.getReference(Usuario.class, applicationSession.getUsuario().getUsuarioId()));
            nuevoPedidoVenta.setCliente(em.getReference(Cliente.class, applicationSession.getPedidoVenta().getCliente().getId()));
            nuevoPedidoVenta.setFormaDePago(em.getReference(FormaDePago.class, applicationSession.getPedidoVenta().getFormaDePago().getId()) );
			nuevoPedidoVenta.setComentarios(applicationSession.getPedidoVenta().getComentarios());
			nuevoPedidoVenta.setFactoriva( getFactorIVA() );
			
            nuevoPedidoVenta.setEsFiscal(applicationSession.getPedidoVenta().getEsFiscal());

            em.persist(nuevoPedidoVenta);
            logger.debug("->ok, Pedido inserted");
			
            PedidoVentaEstado pedidoVentaEstado = null;
            pedidoVentaEstado = new PedidoVentaEstado();
            pedidoVentaEstado.setPedidoVentaEstadoPK(new PedidoVentaEstadoPK(nuevoPedidoVenta.getId(), Constants.ESTADO_CAPTURADO));
			pedidoVentaEstado.setFecha(new Date());
			pedidoVentaEstado.setUsuario(applicationSession.getUsuario());
			pedidoVentaEstado.setComentarios("TEST CASE INSERT");
			
            List<PedidoVentaEstado> listPedidoVentaEstados = new ArrayList<PedidoVentaEstado>();            
			listPedidoVentaEstados.add(pedidoVentaEstado);
            
			Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection();
			
			List<PedidoVentaDetalle> pedidoVentaDetalleCollectionInsert = new ArrayList<PedidoVentaDetalle>();
			
			em.persist(pedidoVentaEstado);
			logger.debug("->ok, PedidoVentaEstado inserted");
			
			for(PedidoVentaDetalle pvd: pedidoVentaDetalleCollection) {
				PedidoVentaDetalle pvdInsert = new PedidoVentaDetalle();
				
				pvdInsert.setCantidad(pvd.getCantidad());
				pvdInsert.setDescuentoAplicado(pvd.getDescuentoAplicado());
				pvdInsert.setPedidoVenta(nuevoPedidoVenta);
				pvdInsert.setPrecioBase(pvd.getPrecioBase());
				pvdInsert.setProducto(em.getReference(Producto.class,pvd.getProducto().getId()));
				
				pedidoVentaDetalleCollectionInsert.add(pvdInsert);
				pvd.setPedidoVentaDetallePK(
                        new PedidoVentaDetallePK(nuevoPedidoVenta.getId(),
				pvd.getProducto().getId()));
                em.persist(pvd);
				
                logger.debug("->\tok, PedidoVentaDetalle inserted");
			}
			
            em.getTransaction().commit();
            logger.debug("->committed.");
            em.refresh(nuevoPedidoVenta);
            applicationSession.setPedidoVenta(nuevoPedidoVenta);
            logger.debug("->refreshed: nuevoPedidoVenta.id="+nuevoPedidoVenta.getId());
            Collection<PedidoVentaDetalle> pedidoVentaDetalleCollectionForIteration = nuevoPedidoVenta.getPedidoVentaDetalleCollection();
            for(PedidoVentaDetalle dvpI: pedidoVentaDetalleCollectionForIteration) {
                logger.debug("->\trefreshed: nuevoPedidoVenta.getPedidoVentaDetalleCollection.producto="+dvpI.getProducto());
            }
            //
            
        } catch (Exception e) {
            logger.error("Exception caught:", e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new BusinessException(getClass().getSimpleName(), "APP_LOGIC_PEDIDO_NOT_PERSISTED");
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
        
    }

    public void sendPedidosAndDelete(ProgressProcessListener pl) throws AuthenticationException, PedidoVentaException {
        sendDataSynchronizer.sendAndDeletePedidos();
    }

    public void persistCliente(Cliente c) throws BusinessException {
        Cliente parecidoRfc = null;
		if( c.getId() == null) {
			try {
				parecidoRfc = basicInfoDAO.getClienteByRFC(c);		
            } catch (NoResultException ex1) {
                logger.debug("->Ok, not Found by RFC");
			} catch (NonUniqueResultException ex) {
				throw new BusinessException(
							ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLEINTE_TITLE"),
							"APP_LOGIC_CLIENTE_EXIST_RFC");            
			}
		}
		
        try {

            if (c.getId() == null) {
                //persistEntityWithTransactionDAO.persistCliente(c);
				persistEntityWithTransactionDAO.create(c);
            } else {
                persistEntityWithTransactionDAO.updateCliente(c);
                
            }
        } catch (NoResultException ex) {
            throw new BusinessException(
                    ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLEINTE_TITLE"),
                    "APP_LOGIC_CLIENTE_NOT_FOUND");
        } catch (Exception ex) {
            logger.error("Exception caught:", ex);
            throw new BusinessException(
                    ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLEINTE_TITLE"),
                    "APP_LOGIC_CLIENTE_NOT_SAVED");
        }
    }

    public void removeCliente(Cliente c) throws BusinessException {
        try {
            persistEntityWithTransactionDAO.deleteCliente(c);
        } catch (NoResultException ex) {
            throw new BusinessException(
                    ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLEINTE_TITLE"),
                    "APP_LOGIC_CLIENTE_NOT_FOUND");
        } catch (Exception ex) {
            logger.error("Exception caught:", ex);
            throw new BusinessException(
                    ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLEINTE_TITLE"),
                    "APP_LOGIC_CLIENTE_NOT_DELETED");
        }

    }

    public void printTicketPedido(boolean sendToTprinter) throws BusinessException {
        String ticketFileName = null;

        try {
            ticketFileName = TicketPrinter.generateTicket(applicationSession.getPedidoVenta());
            logger.debug("-> printTicketPedido: ticketFileName=" + ticketFileName);
        } catch (Exception ex) {
            logger.error("-> printTicketPedido:TicketPrinter.generateTicket ", ex);
            throw new BusinessException(getClass().getSimpleName(), "APP_LOGIC_TICKET_NOT_GENERATED");
        }

        if (sendToTprinter) {
            try {
                logger.debug("-> printTicketPedido:now send to default Printer");
                TicketPrinter.print(ticketFileName);
            } catch (Exception ex) {
                logger.error("-> printTicketPedido:TicketPrinter.generateTicket ", ex);
                throw new BusinessException(getClass().getSimpleName(), "APP_LOGIC_TICKET_NOT_PRINTED");
            }
        }
    }

    public void testPrinter() throws BusinessException {
        try {
            logger.debug("-> testPrinter:TicketPrinter.testDefaultPrinter ?");
            TicketPrinter.testDefaultPrinter();
            logger.debug("-> testPrinter: Ok !");
        } catch (Exception ex) {
            logger.error("-> testPrinter:TicketPrinter.testDefaultPrinter", ex);
            throw new BusinessException(getClass().getSimpleName(), "APP_LOGIC_TICKET_NOT_PRINTED");
        }
    }

    public void exit() throws BusinessException {
        if (applicationSession.getPedidoVenta().getPedidoVentaDetalleCollection().size() > 0) {
            throw new BusinessException(getClass().getSimpleName(), "APP_LOGIC_PEDIDO_NOT_SAVED");
        }
    }
    @Autowired
    public void setApplicationSession(ApplicationSession as) {
        applicationSession = as;
    }

    public ApplicationSession getSession() {
        return applicationSession;
    }
    
    ProductoFastDisplayModel[] arrProds;
    //Hashtable<Integer,Producto> productosFastTable;

    /**
     * @return the producto4Display
     */
    public ProductoFastDisplayModel[] getProducto4Display() {
        logger.debug("->getProducto4Display()");
        if (arrProds == null) {
            try {
                //productosFastTable = new Hashtable<Integer,Producto> ();
                List<Producto> producto4Display = null;
                producto4Display = basicInfoDAO.getProductos4DisplayList();
                arrProds = new ProductoFastDisplayModel[producto4Display.size()];
                int ap = 0;
                for (Producto p : producto4Display) {
                    arrProds[ap++] = new ProductoFastDisplayModel(
                            p.getId(),
                            p.getId() != null ? (p.getNombre() + "(" + p.getPresentacion() + "):" + p.getContenido() + "" + p.getUnidadMedida()) : "",
                            p);
                    //productosFastTable.put(p.getId(), p);
                }
            } catch (Exception ex) {
                logger.error("",ex);
            }
        }

        return arrProds;
    }

    public ProductoFastDisplayModel[] getProducto4Display(String searchString) {
        ArrayList<ProductoFastDisplayModel> found = new ArrayList<ProductoFastDisplayModel>();
        ProductoFastDisplayModel[] foundArray = null;
        ProductoFastDisplayModel[] search = getProducto4Display();
        if(search != null) {
            if (searchString.trim().length() > 0) {
                for (ProductoFastDisplayModel pfd : search) {
                    if (pfd.getForDisplay().toLowerCase().indexOf(searchString.toLowerCase()) >= 0) {
                        found.add(pfd);
                    }
                }
            }
        }

        foundArray = new ProductoFastDisplayModel[found.size()];
        found.toArray(foundArray);
        return foundArray;
    }

    /**
     * @return the factorIVA
     */
    public double getFactorIVA() {
        return factorIVA;
    }

    /**
     * @param factorIVA the factorIVA to set
     */
    public void setFactorIVA(double factorIVA) {
        this.factorIVA = factorIVA;
    }
//    public Producto getProducto(Integer id){
//        return productosFastTable.get(id);
//    }


    void setComentariosToCurrentPedidoVenta(String comentarios) {
        this.getSession().getPedidoVenta().setComentarios(comentarios);
    }
    
    /**
     * @return the applicationSession
     */
    @Autowired
    public ApplicationSession getApplicationSession() {
        return applicationSession;
    }
    
    /**
     * @param sendDataSynchronizer the sendDataSynchronizer to set
     */
    @Autowired
    public void setSendDataSynchronizer(SendDataSynchronizer sendDataSynchronizer) {
        this.sendDataSynchronizer = sendDataSynchronizer;
    }

    private BufferedImage defaultImageForProducto;
    
    public BufferedImage getDefaultImageForProducto() {
        if(this.defaultImageForProducto == null) {
            try {
                this.defaultImageForProducto = ImageIO.read(ApplicationLogic.class.getResource("/imgs/proximamente_2.jpg"));
            } catch (IOException ex) {
                logger.error("DefaultImageForProducto not found:", ex);
            }
        }
        return this.defaultImageForProducto;
    }
    
    public static String getMD5Encrypted(String e) {

        MessageDigest mdEnc = null; // Encryption algorithm
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
        mdEnc.update(e.getBytes(), 0, e.length());
        return (new BigInteger(1, mdEnc.digest())).toString(16);
    }
}
