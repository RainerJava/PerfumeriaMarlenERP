/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.controller;

import com.pmarlen.model.beans.*;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("getListDataBusinesJpaController")
public class GetListDataBusinesJpaController {

    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Usuario x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Usuario> resultLsit = q.getResultList();
            for (Usuario x : resultLsit) {
                Collection<Perfil> perfilCollection = x.getPerfilCollection();
                Collection<PedidoVenta> pedidoVentaCollection = x.getPedidoVentaCollection();
                Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                Collection<EstadoDeCuenta> estadoDeCuentaCollection = x.getEstadoDeCuentaCollection();
                Collection<PedidoCompraEstado> pedidoCompraEstadoCollection = x.getPedidoCompraEstadoCollection();
                Collection<PedidoCompra> pedidoCompraCollection = x.getPedidoCompraCollection();
                Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = x.getPedidoVentaEstadoCollection();

                x.setPerfilCollection(null);
                x.setPedidoVentaCollection(null);
                x.setMovimientoHistoricoProductoCollection(null);
                x.setEstadoDeCuentaCollection(null);
                x.setPedidoCompraEstadoCollection(null);
                x.setPedidoCompraCollection(null);
                x.setPedidoVentaEstadoCollection(null);
            }
            return resultLsit;
        } finally {
            em.close();
        }
    }

    public List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Empresa x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Empresa> resultList = q.getResultList();
            for (Empresa x : resultList) {
                Collection<Marca> marcaCollection = x.getMarcaCollection();
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();

                x.setMarcaCollection(null);
                x.setMultimedioCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Linea> findLineaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Linea x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Linea> resultList = q.getResultList();
            for (Linea x : resultList) {
                Collection<Marca> marcaCollection = x.getMarcaCollection();
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();

                x.setMarcaCollection(null);
                x.setMultimedioCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Marca x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Marca> resultList = q.getResultList();
            for (Marca x : resultList) {
                Collection<Producto> productoCollection = x.getProductoCollection();
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
                
                x.setProductoCollection(null);
                x.setMultimedioCollection(null);
                x.setEmpresa(null);
                x.setLinea(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Producto x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Producto> resultList = q.getResultList();
            for (Producto x : resultList) {
                Collection<AlmacenProducto> almacenProductoCollection = x.getAlmacenProductoCollection();
                Collection<CodigoDeBarras> codigoDeBarrasCollection = x.getCodigoDeBarrasCollection();
                Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
                Collection<PedidoCompraDetalle> pedidoCompraDetalleCollection = x.getPedidoCompraDetalleCollection();
                Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = x.getPedidoVentaDetalleCollection();
                Collection<ProveedorProducto> proveedorProductoCollection = x.getProveedorProductoCollection();

                x.setAlmacenProductoCollection(null);
                x.setCodigoDeBarrasCollection(null);
                x.setMovimientoHistoricoProductoCollection(null);
                x.setMultimedioCollection(null);
                x.setPedidoCompraDetalleCollection(null);
                x.setPedidoVentaDetalleCollection(null);
                x.setProveedorProductoCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Multimedio> findMultimedioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Multimedio x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Multimedio> resultList = q.getResultList();
            for (Multimedio x : resultList) {
                Collection<Empresa> empresaCollection = x.getEmpresaCollection();
                Collection<Linea> lineaCollection = x.getLineaCollection();
                Collection<Marca> marcaCollection = x.getMarcaCollection();
                Collection<Producto> productoCollection = x.getProductoCollection();

                x.setEmpresaCollection(null);
                x.setLineaCollection(null);
                x.setMarcaCollection(null);
                x.setProductoCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<FormaDePago> findFormaDePagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM FormaDePago x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<FormaDePago> resultList = q.getResultList();
            for (FormaDePago x : resultList) {
                Collection<PedidoCompra> pedidoCompraCollection = x.getPedidoCompraCollection();
                Collection<PedidoVenta> pedidoVentaCollection = x.getPedidoVentaCollection();
                x.setPedidoCompraCollection(null);
                x.setPedidoVentaCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Estado x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Estado> resultList = q.getResultList();
            for (Estado x : resultList) {
                Collection<PedidoCompraEstado> pedidoCompraEstadoCollection = x.getPedidoCompraEstadoCollection();
                Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = x.getPedidoVentaEstadoCollection();

                x.setPedidoCompraEstadoCollection(null);
                x.setPedidoVentaEstadoCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Cliente x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Cliente> resultList = q.getResultList();
            for (Cliente x : resultList) {
                Collection<Contacto> contactoCollection = x.getContactoCollection();
                Collection<CuentaBancaria> cuentaBancariaCollection = x.getCuentaBancariaCollection();
                Collection<PedidoVenta> pedidoVentaCollection = x.getPedidoVentaCollection();

                x.setContactoCollection(null);
                x.setCuentaBancariaCollection(null);
                x.setPedidoVentaCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }
       
    public List<PedidoVenta> findPedidoVentaEntities() {
        return findPedidoVentaEntities(true, -1, -1);
    }

    public List<PedidoVenta> findPedidoVentaEntities(int maxResults, int firstResult) {
        return findPedidoVentaEntities(false, maxResults, firstResult);
    }

    private List<PedidoVenta> findPedidoVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PedidoVenta as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<PedidoVenta> result = q.getResultList();
            for(PedidoVenta pedidoVenta:result){
                pedidoVenta.getCliente();
                
                pedidoVenta.getFormaDePago();
                
                pedidoVenta.getUsuario();                
                
                Collection<PedidoVentaDetalle> detalleVentaPedidoCollection = pedidoVenta.getPedidoVentaDetalleCollection();
                for(PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoCollection) {
                    Producto producto = detalleVentaPedido.getProducto();
                }

                Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pedidoVenta.getPedidoVentaEstadoCollection();
                for(PedidoVentaEstado pedidoVentaEstado:pedidoVentaEstadoCollection) {
                }

                Collection<FacturaCliente> facturaClienteCollection = pedidoVenta.getFacturaClienteCollection();                
            }
            return result;
        } finally {
            em.close();
        }
    }

}
