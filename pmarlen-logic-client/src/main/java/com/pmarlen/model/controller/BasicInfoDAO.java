/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.controller;

import com.pmarlen.client.model.CPDePoblacion;
import com.pmarlen.client.model.EntidadFederativa;
import com.pmarlen.client.model.MunicipioODelegacion;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.FacturaCliente;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.PedidoVentaEstado;
import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.beans.Producto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alfred
 */
@Repository("basicInfoDAO")
public class BasicInfoDAO {

    private Logger logger;

    private EntityManagerFactory emf;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    //private static BasicInfoDAO instance;

    public BasicInfoDAO() {
        logger = LoggerFactory.getLogger(BasicInfoDAO.class.getSimpleName());        
    }

    public List<PedidoVenta> getPedidoVentaList() throws Exception {
        logger.debug("->getProductosList: ");
        EntityManager em = null;
        List<PedidoVenta> resultList = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            resultList = em.createQuery("select x from PedidoVenta x").
                    getResultList();
            logger.debug("->resultList.size=" + resultList.size());
            for ( PedidoVenta pv: resultList){            
                logger.debug("\t feching all objets to use: ->resultList");
                Collection<FacturaCliente> facturaClienteCollection = pv.getFacturaClienteCollection();
                Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = pv.getPedidoVentaDetalleCollection();
                for(PedidoVentaDetalle pvd: pedidoVentaDetalleCollection){
                    pvd.getProducto();
                }
                Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pv.getPedidoVentaEstadoCollection();
                for (PedidoVentaEstado pve: pedidoVentaEstadoCollection){
                    pve.getPedidoVenta();
                }
                pv.getUsuario();
                pv.getFormaDePago();
            }
            
        } catch (Exception e) {
            logger.error( "Exception caught:", e);
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
        return resultList;
    }

    public List<Producto> getProductosList() throws Exception {
        logger.debug("->getProductosList: ");
        EntityManager em = null;
        List<Producto> resultList = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            resultList = em.createQuery("select x from PedidoVenta x").
                    getResultList();
            logger.debug("->resultList.size=" + resultList.size());
        } catch (Exception e) {
            logger.error( "Exception caught:", e);
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
        return resultList;
    }

    public List<Producto> getProductos4DisplayList() throws Exception {
        logger.debug("->getProductos4DisplayList: ");
        EntityManager em = null;
        List<Producto> resultList = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery("SELECT p FROM Producto p order by p.nombre,p.presentacion,p.contenido");
            resultList = q.getResultList();
            logger.debug("->resultList.size=" + resultList.size());
        } catch (Exception e) {
            logger.error( "Exception caught:", e);
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
        return resultList;
    }


    public List<Cliente> getClientesList() throws Exception {
        logger.debug("->getClientesList: ");
        EntityManager em = null;
        List<Cliente> resultList = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            resultList = em.createQuery("SELECT c FROM Cliente c ORDER BY c.razonSocial, c.rfc").
                    getResultList();
            logger.debug("->resultList.size=" + resultList.size());
			for(Cliente x: resultList) {
				x.getContactoCollection();
				x.getCuentaBancariaCollection();
				x.getPedidoVentaCollection();
				x.getPoblacion();
			}
        } catch (Exception e) {
            logger.error( "Exception caught:", e);
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
        return resultList;
    }

    public List<FormaDePago> getFormaDePagosList() throws Exception {
        logger.debug("->getFormaDePagosList: ");
        EntityManager em = null;
        List<FormaDePago> resultList = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            resultList = em.createQuery("select x from FormaDePago x").
                    getResultList();
            logger.debug("->resultList.size=" + resultList.size());
        } catch (Exception e) {
            logger.error( "Exception caught:", e);
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
        return resultList;
    }

    public List<Producto> getProductoByMarca(Integer marcaId) {
        List<Producto> listresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery("SELECT x FROM Producto x where x.marca.id = :marcaId order by x.nombre");
            logger.debug("->query created");
            q.setParameter("marcaId", marcaId);
            listresult = (List<Producto>) q.getResultList();
            logger.debug("->listresult.size="+listresult.size());
            for(Producto  p :listresult) {
                logger.debug("\t->Force to  retrieve Multimedio List:");
                List<Multimedio> multimedioList = (List<Multimedio>)p.getMultimedioCollection();
                logger.debug("\t->Multimedio List size="+multimedioList.size());
                for(Multimedio m: multimedioList) {
                    logger.debug("\t\t->Multimedio:"+m.getNombreArchivo()+" ("+m.getMimeType()+")");
                }
            }

            return listresult;
        } catch (PersistenceException pex) {
            logger.error( "Failed:", pex);
            return null;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public Multimedio getFirstMultimedioByProducto(Integer productoId) {
        Multimedio result=null;        
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createNativeQuery("SELECT MULTIMEDIO FROM PRODUCTO_MULTIMEDIO WHERE PRODUCTO=?");
            logger.debug("->query created");
            q.setParameter(1, productoId);
            logger.debug("->query filled with productoId="+productoId);
            Vector vo = (Vector)q.getSingleResult();
            logger.debug("->query executed:");
            if(vo!=null){
                logger.debug("->Object retrieved:"+vo.get(0)+", class"+vo.get(0).getClass());
                Query qm = em.createQuery("select x from Multimedio x where x.id = :id");
                qm.setParameter("id", new Integer(vo.get(0).toString()));
                logger.debug("->query for Multimedio created, prepared");
                Multimedio m = (Multimedio)qm.getSingleResult();
                logger.debug("->query for Multimedio executed:"+m);
                result = m;
            }
            
            return result;
        } catch (NoResultException pex) {
            logger.warn("Not Multimedio found For this Producto.");
            return null;
        } catch (Exception pex) {
            logger.error( "Failed:", pex);
            return null;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    //==========================================================================

    public Poblacion getPoblacionByCP(int codigoPostal) {
        Poblacion beanresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery(
                    "SELECT x FROM Poblacion x where x.codigoPostal = :codigoPostal order by x.nombre");

            q.setParameter("codigoPostal", codigoPostal);

            logger.debug("->query created");
            List<Poblacion> p = q.getResultList();
            if(p.size()>0){
                beanresult = p.get(0);
            }
            return beanresult;
        } catch (PersistenceException pex) {
            logger.error( "Failed:", pex);
            return null;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public List<Poblacion> getPoblacionList(Integer idMunicipio) {
        List<Poblacion> listresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery(
                    "SELECT x FROM Poblacion x where x.municipioODelegacion.id = :idMunicipio order by x.nombre");

            q.setParameter("idMunicipio", idMunicipio);

            logger.debug("->query created");
            listresult = (List<Poblacion>) q.getResultList();

            return listresult;
        } catch (PersistenceException pex) {
            logger.error( "Failed:", pex);
            return null;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public List<Poblacion> getPoblacionList(Integer idMunicipio, int codigoPostal) {
        List<Poblacion> listresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery(
                    "SELECT x FROM Poblacion x where x.municipioODelegacion.id = :idMunicipio and x.codigoPostal =:codigoPostal order by x.codigoPostal,x.nombre");

            q.setParameter("idMunicipio", idMunicipio);
            q.setParameter("codigoPostal", codigoPostal);

            logger.debug("->query created");
            listresult = (List<Poblacion>) q.getResultList();

            return listresult;
        } catch (PersistenceException pex) {
            logger.error( "Failed:", pex);
            return null;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public List<Poblacion> getCodigoPostalList(Integer idMunicipio) {
        List<Poblacion> listresult = null;
        List<Poblacion> listresultCP = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery(
                    "SELECT x FROM Poblacion x where x.municipioODelegacion.id = :idMunicipio order by x.codigoPostal");

            q.setParameter("idMunicipio", idMunicipio);

            logger.debug("->query created");
            listresult   = (List<Poblacion>) q.getResultList();
            listresultCP = new ArrayList<Poblacion>();
            Hashtable<Integer,Poblacion> poblacionCP = new Hashtable<Integer,Poblacion> ();
            listresultCP = new ArrayList<Poblacion>();
            for(Poblacion p:listresult){
                if(!poblacionCP.containsKey(p.getCodigoPostal())){
                    listresultCP.add(p);
                }
            }

            return listresultCP;
        } catch (PersistenceException pex) {
            logger.error( "Failed:", pex);
            return null;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public Cliente getClienteByRFC(Cliente cl) {
        Cliente c = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery("select x from Cliente x where x.rfc = :rfc");
            logger.debug("->query created");
            q.setParameter("rfc", cl.getRfc());
            logger.debug("->query filled");
            c = (Cliente) q.getSingleResult();
            return c;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public List<EntidadFederativa> getEntidadFederativaList() {
        List<EntidadFederativa> listresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery("select distinct entidadFederativa from Poblacion x order by entidadFederativa");
            logger.debug("->query created");
            
            List resultList = q.getResultList();
            listresult = new ArrayList<EntidadFederativa> ();
            
            for(Object ef:resultList){
                listresult.add(new EntidadFederativa(String.valueOf(ef.hashCode()),ef.toString()));
            }
            return listresult;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }
	
	public List<MunicipioODelegacion> getMunicipioODelegacionList(EntidadFederativa entidadFederativa) {
        List<MunicipioODelegacion> listresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery("select distinct municipioODelegacion from Poblacion x where x.entidadFederativa = :entidadFederativa order by municipioODelegacion");
            logger.debug("->query created");
            q.setParameter("entidadFederativa", entidadFederativa.getNombre());
            logger.debug("->query filled");
            List resultList = q.getResultList();
            listresult = new ArrayList<MunicipioODelegacion> ();
            
            for(Object ef:resultList){
                listresult.add(new MunicipioODelegacion(String.valueOf(ef.hashCode()),ef.toString()));
            }
            return listresult;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }
	
	public List<CPDePoblacion> getCPDePoblacionList(MunicipioODelegacion municipioODelegacion) {
        List<CPDePoblacion> listresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery("select distinct codigoPostal from Poblacion x where x.municipioODelegacion = :municipioODelegacion order by codigoPostal");
            logger.debug("->query created");
            q.setParameter("municipioODelegacion", municipioODelegacion.getNombre());
            logger.debug("->query filled");
            List resultList = q.getResultList();
            listresult = new ArrayList<CPDePoblacion> ();
            
            for(Object ef:resultList){
                listresult.add(new CPDePoblacion(ef.toString()));
            }
            return listresult;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }
	
	public List<Poblacion> getPoblacionList(MunicipioODelegacion municipioODelegacion) {
        List<Poblacion> listresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery("select x from Poblacion x where x.municipioODelegacion = :municipioODelegacion order by nombre, codigoPostal ");
            logger.debug("->query created");
            q.setParameter("municipioODelegacion", municipioODelegacion.getNombre());
            logger.debug("->query filled");
            
			listresult = q.getResultList();
			
            return listresult;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

	public Collection<Poblacion> getPoblacionListForCP(CPDePoblacion cpDePoblacionSelected) {
		List<Poblacion> listresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery("select x from Poblacion x where x.codigoPostal = :codigoPostal order by nombre, codigoPostal ");
            logger.debug("->query created");
            q.setParameter("codigoPostal", cpDePoblacionSelected.cp);
            logger.debug("->query filled");
            
            logger.debug("->query filled");
            
			listresult = q.getResultList();
			
            return listresult;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
	}
	
	public MunicipioODelegacion getMunicipioODelegacionForCP(CPDePoblacion cpDePoblacionSelected) {
		List listresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery("select distinct municipioODelegacion from Poblacion x where x.codigoPostal = :codigoPostal  order by municipioODelegacion");
            logger.debug("->query created");
            q.setParameter("codigoPostal", cpDePoblacionSelected.cp);
            logger.debug("->query filled");
            
            logger.debug("->query filled");
            
			listresult = q.getResultList();
			String mod = listresult.get(0).toString();
            return new MunicipioODelegacion(String.valueOf(mod.hashCode()),mod);
			
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
	}
	
	public EntidadFederativa getEntidadFederativaForCP(CPDePoblacion cpDePoblacionSelected) {
		List listresult = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            logger.debug("->EntityManager em, created");
            Query q = em.createQuery("select distinct entidadFederativa from Poblacion x where x.codigoPostal = :codigoPostal order by entidadFederativa");
            logger.debug("->query created");
            q.setParameter("codigoPostal", cpDePoblacionSelected.cp);
            logger.debug("->query filled");
            
            logger.debug("->query filled");
            
			listresult = q.getResultList();
			String mod = listresult.get(0).toString();
            return new EntidadFederativa(String.valueOf(mod.hashCode()),mod);
			
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
	}

}
