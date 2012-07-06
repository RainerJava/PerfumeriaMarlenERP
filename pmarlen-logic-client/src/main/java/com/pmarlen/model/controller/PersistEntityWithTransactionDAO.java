/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.controller;

import com.pmarlen.client.ProgressProcessListener;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Contacto;
import com.pmarlen.model.beans.CuentaBancaria;
import com.pmarlen.model.beans.Empresa;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.PedidoVentaEstado;
import com.pmarlen.model.beans.Perfil;
import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Usuario;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alfred
 */
@Repository("persistEntityWithTransactionDAO")
public class PersistEntityWithTransactionDAO {

    private Logger logger;
    private EntityManagerFactory emf;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PersistEntityWithTransactionDAO() {
        logger = LoggerFactory.getLogger(PersistEntityWithTransactionDAO.class);
    }

    public Cliente persistCliente(Cliente c) throws Exception {
        logger.debug("->persistCliente: ");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            em.getTransaction().begin();
            logger.debug("->Begin transaction");

            Cliente clienteToPersist = new Cliente();

            clienteToPersist.setClasificacionFiscal(c.getClasificacionFiscal());
            clienteToPersist.setRfc(c.getRfc());
            clienteToPersist.setFechaCreacion(c.getFechaCreacion());
            clienteToPersist.setRazonSocial(c.getRazonSocial());
            clienteToPersist.setNombreEstablecimiento(c.getNombreEstablecimiento());
            clienteToPersist.setCalle(c.getCalle());
            clienteToPersist.setNumInterior(c.getNumInterior());
            clienteToPersist.setNumExterior(c.getNumExterior());
            clienteToPersist.setPoblacion(c.getPoblacion());
            clienteToPersist.setTelefonos(c.getTelefonos());
            clienteToPersist.setFaxes(c.getFaxes());
            clienteToPersist.setTelefonosMoviles(c.getTelefonosMoviles());
            clienteToPersist.setEmail(c.getEmail());
            clienteToPersist.setPlazoDePago(c.getPlazoDePago());
            clienteToPersist.setUrl(c.getUrl());
            clienteToPersist.setObservaciones(c.getObservaciones());
            clienteToPersist.setDescripcionRuta(c.getDescripcionRuta());
            
            em.persist(clienteToPersist);

            em.getTransaction().commit();
            logger.debug("->Commit");

            return clienteToPersist;
        } catch (Exception e) {
            logger.error("Exception caught:", e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }
	
	public void create(Cliente cliente) {
        if (cliente.getCuentaBancariaCollection() == null) {
            cliente.setCuentaBancariaCollection(new ArrayList<CuentaBancaria>());
        }
        if (cliente.getContactoCollection() == null) {
            cliente.setContactoCollection(new ArrayList<Contacto>());
        }
        if (cliente.getPedidoVentaCollection() == null) {
            cliente.setPedidoVentaCollection(new ArrayList<PedidoVenta>());
        }
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Poblacion poblacion = cliente.getPoblacion();
            if (poblacion != null) {
                poblacion = em.getReference(poblacion.getClass(), poblacion.getId());
                cliente.setPoblacion(poblacion);
            }
            Collection<CuentaBancaria> attachedCuentaBancariaCollection = new ArrayList<CuentaBancaria>();
            for (CuentaBancaria cuentaBancariaCollectionCuentaBancariaToAttach : cliente.getCuentaBancariaCollection()) {
                cuentaBancariaCollectionCuentaBancariaToAttach = em.getReference(cuentaBancariaCollectionCuentaBancariaToAttach.getClass(), cuentaBancariaCollectionCuentaBancariaToAttach.getId());
                attachedCuentaBancariaCollection.add(cuentaBancariaCollectionCuentaBancariaToAttach);
            }
            cliente.setCuentaBancariaCollection(attachedCuentaBancariaCollection);
            Collection<Contacto> attachedContactoCollection = new ArrayList<Contacto>();
            for (Contacto contactoCollectionContactoToAttach : cliente.getContactoCollection()) {
                contactoCollectionContactoToAttach = em.getReference(contactoCollectionContactoToAttach.getClass(), contactoCollectionContactoToAttach.getId());
                attachedContactoCollection.add(contactoCollectionContactoToAttach);
            }
            cliente.setContactoCollection(attachedContactoCollection);
            Collection<PedidoVenta> attachedPedidoVentaCollection = new ArrayList<PedidoVenta>();
            for (PedidoVenta pedidoVentaCollectionPedidoVentaToAttach : cliente.getPedidoVentaCollection()) {
                pedidoVentaCollectionPedidoVentaToAttach = em.getReference(pedidoVentaCollectionPedidoVentaToAttach.getClass(), pedidoVentaCollectionPedidoVentaToAttach.getId());
                attachedPedidoVentaCollection.add(pedidoVentaCollectionPedidoVentaToAttach);
            }
            cliente.setPedidoVentaCollection(attachedPedidoVentaCollection);
            em.persist(cliente);
            if (poblacion != null) {
                poblacion.getClienteCollection().add(cliente);
                poblacion = em.merge(poblacion);
            }
            for (CuentaBancaria cuentaBancariaCollectionCuentaBancaria : cliente.getCuentaBancariaCollection()) {
                cuentaBancariaCollectionCuentaBancaria.getClienteCollection().add(cliente);
                cuentaBancariaCollectionCuentaBancaria = em.merge(cuentaBancariaCollectionCuentaBancaria);
            }
            for (Contacto contactoCollectionContacto : cliente.getContactoCollection()) {
                contactoCollectionContacto.getClienteCollection().add(cliente);
                contactoCollectionContacto = em.merge(contactoCollectionContacto);
            }
            for (PedidoVenta pedidoVentaCollectionPedidoVenta : cliente.getPedidoVentaCollection()) {
                Cliente oldClienteOfPedidoVentaCollectionPedidoVenta = pedidoVentaCollectionPedidoVenta.getCliente();
                pedidoVentaCollectionPedidoVenta.setCliente(cliente);
                pedidoVentaCollectionPedidoVenta = em.merge(pedidoVentaCollectionPedidoVenta);
                if (oldClienteOfPedidoVentaCollectionPedidoVenta != null) {
                    oldClienteOfPedidoVentaCollectionPedidoVenta.getPedidoVentaCollection().remove(pedidoVentaCollectionPedidoVenta);
                    oldClienteOfPedidoVentaCollectionPedidoVenta = em.merge(oldClienteOfPedidoVentaCollectionPedidoVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public Cliente updateCliente(Cliente c) throws Exception {
        logger.debug("->persistCliente: ");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            em.getTransaction().begin();
            logger.debug("->Begin transaction");

            Cliente xc = em.getReference(Cliente.class, c.getId());

            copySimpleProperties(xc, c);

            em.getTransaction().commit();
            logger.debug("->Commit");

            logger.debug("->Ok, Cliente created:" + c.getId());

            return c;
        } catch (Exception e) {
            logger.error("Exception caught:", e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void deleteCliente(Cliente del) throws Exception {
        Cliente x = null;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            em.getTransaction().begin();
            logger.debug("->begin transaction");
            x = em.find(del.getClass(), del.getId());
            logger.debug("->ok, Cliente retrieved");
            em.remove(x);
            logger.debug("->ok, Cliente removed");
            em.getTransaction().commit();
            logger.debug("->Ok, commit");
        } catch (RollbackException rbe) {
            logger.error("->Some has wrong. shuld rollback !", rbe);
            em.getTransaction().rollback();
            throw rbe;
        } catch (Exception e) {
            logger.error("->Some has wrong", e);
            throw e;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void deleteAllObjects() throws Exception {
        logger.debug("->deleteAllObjects: ");
        String qry = null;
        Query q = null;
        int r = -1;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            em.getTransaction().begin();

            Class entityClassNames[] = {
                PedidoVentaEstado.class, PedidoVentaDetalle.class,
                PedidoVenta.class};

            Class entityRelationClassNames[] = {
                Perfil.class, Usuario.class, Cliente.class,
                Multimedio.class, Producto.class, Marca.class,
                Linea.class, Empresa.class, Estado.class, FormaDePago.class};

            for (Class entityClass : entityClassNames) {

                qry = "delete from " + entityClass.getSimpleName() + " x where 1=1 ";
                q = em.createQuery(qry);
                r = q.executeUpdate();
                em.flush();
                logger.debug("->flusshed, result delete " + entityClass.getSimpleName() + " : " + r);
            }

            for (Class entityRelatedClass : entityRelationClassNames) {

                q = em.createQuery("select x from " + entityRelatedClass.getSimpleName() + " x ");
                List listEntities = q.getResultList();

                logger.debug("\t->remove " + entityRelatedClass.getSimpleName() + " the lis:" + listEntities);
                for (Object objEntity : listEntities) {
                    logger.debug("\t\t->Ok remove Related entitie:" + objEntity.getClass().getSimpleName());
                    em.remove(objEntity);
                }
                em.flush();
                logger.debug("\t->Ok remove all Releated entities.");
            }

            logger.debug("Prepared to exec commint !.");

            em.getTransaction().commit();

            logger.debug("->commit delleteAllObjects!!");
        } catch (Exception ex) {
            logger.error("-------->>> delleteAllObjects:" + ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void deletePedidos() throws Exception {
        logger.debug("->deletePedidos: ");
        String qry = null;
        Query q = null;
        int r = -1;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Class entityRelationClassNames[] = {PedidoVentaEstado.class, PedidoVentaDetalle.class, PedidoVenta.class};

            for (Class entityRelatedClass : entityRelationClassNames) {

                q = em.createQuery("select x from " + entityRelatedClass.getSimpleName() + " x ");
                List listEntities = q.getResultList();

                logger.debug("\t->remove " + entityRelatedClass.getSimpleName() + " the lis:" + listEntities);
                for (Object objEntity : listEntities) {
                    logger.debug("\t\t->Ok remove Related entitie:" + objEntity.getClass().getSimpleName());
                    em.remove(objEntity);
                }
                em.flush();
                logger.debug("\t->Ok remove all Releated entities.");
            }

            em.getTransaction().commit();

            logger.debug("->commit delletePedidos!!");
        } catch (Exception ex) {
            //logger.error( "delletePedidos:", ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void inicializarUsuarios(List<Usuario> usuarioReceivedList, ProgressProcessListener progressListener) throws Exception {
        logger.debug("->inicializarUsuarios: ");

        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            logger.debug("->Ok prepare to insert the collection for Usuarios");
            em.getTransaction().begin();
            int total = usuarioReceivedList.size();
            int percIni = progressListener.getProgress();
            int i = 0;

            Hashtable<Perfil, HashSet<Usuario>> usuarioDePerfiles = new Hashtable<Perfil, HashSet<Usuario>>();

            for (Usuario usr : usuarioReceivedList) {

                Usuario usuarioNuevo = new Usuario();

                usuarioNuevo.setUsuarioId(usr.getUsuarioId());
                usuarioNuevo.setNombreCompleto(usr.getNombreCompleto());
                usuarioNuevo.setPassword(usr.getPassword());
                usuarioNuevo.setEmail(usr.getEmail());
                usuarioNuevo.setFactorComisionVenta(usr.getFactorComisionVenta());
                usuarioNuevo.setHabilitado(usr.getHabilitado());
                usuarioNuevo.setPerfilCollection(null);

                Collection<Perfil> perfilListInUsuario = usr.getPerfilCollection();
                em.persist(usuarioNuevo);

                i++;
                progressListener.updateProgress((int) Math.ceil(percIni + ((double) i / (double) total) * 5),
                        "actualizando Usuario :" + formatProgress(i, total));

                for (Perfil perfil : perfilListInUsuario) {
                    HashSet<Usuario> usuarios = usuarioDePerfiles.get(perfil);
                    if (usuarios == null) {
                        usuarios = new HashSet<Usuario>();
                    }
                    usuarios.add(usuarioNuevo);
                    usuarioDePerfiles.put(perfil, usuarios);
                }
            }
            em.flush();

            Enumeration<Perfil> enumPerfiles = usuarioDePerfiles.keys();

            while (enumPerfiles.hasMoreElements()) {

                Perfil perfilASincronizar = enumPerfiles.nextElement();
                Perfil perfil = null;

                perfil = new Perfil(perfilASincronizar.getId());
                perfil.setDescripcion(perfilASincronizar.getDescripcion());
                em.persist(perfil);
            }
            em.flush();

            enumPerfiles = usuarioDePerfiles.keys();

            while (enumPerfiles.hasMoreElements()) {
                Perfil perfil = enumPerfiles.nextElement();
                HashSet<Usuario> usuarios = usuarioDePerfiles.get(perfil);

                Perfil perfilASincronizar = em.find(Perfil.class, perfil.getId());

                perfilASincronizar.setUsuarioCollection(usuarios);
                em.merge(perfilASincronizar);
            }
            em.flush();

            em.getTransaction().commit();
            logger.debug("->commit the collection for Usuarios OK!!");
        } catch (Exception ex) {
            //logger.error( "inicializarUsuarios:", ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void inicializarLinea(List<Linea> lineaReceivedList, ProgressProcessListener progressListener) throws Exception {
        logger.debug("->inicializarLineas: V5");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            logger.debug("->Ok prepare to insert the collection for Linea");
            em.getTransaction().begin();
            int total = lineaReceivedList.size();
            int percIni = progressListener.getProgress();
            int i = 0;
            for (Linea linea : lineaReceivedList) {
                Query nativeQuery = em.createNativeQuery("INSERT INTO LINEA(ID,NOMBRE) VALUES (?,?)");
                nativeQuery.setParameter(1, linea.getId());
                nativeQuery.setParameter(2, linea.getNombre());
                int result = nativeQuery.executeUpdate();
                i++;
                progressListener.updateProgress((int) Math.ceil(percIni + ((double) i / (double) total) * 5),
                        "actualizando Linea :" + formatProgress(i, total) + ":" + result + " registros afectados.");
            }

            em.flush();
            em.getTransaction().commit();

            logger.debug("->commit the collection for Linea OK!!");
        } catch (Exception ex) {
            //logger.error( "inicializarLinea:", ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void inicializarEmpresas(List<Empresa> empresaReceivedList, ProgressProcessListener progressListener) throws Exception {
        logger.debug("->inicializarEmpresas: V5");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            logger.debug("->Ok prepare to insert the collection for Empresa");
            em.getTransaction().begin();
            int total = empresaReceivedList.size();
            int percIni = progressListener.getProgress();
            int i = 0;
            for (Empresa empresa : empresaReceivedList) {
                Query nativeQuery = em.createNativeQuery("INSERT INTO EMPRESA(ID,NOMBRE,NOMBRE_FISCAL) VALUES (?,?,?)");
                nativeQuery.setParameter(1, empresa.getId());
                nativeQuery.setParameter(2, empresa.getNombre());
                nativeQuery.setParameter(3, empresa.getNombreFiscal());
                int result = nativeQuery.executeUpdate();
                i++;

                progressListener.updateProgress((int) Math.ceil(percIni + ((double) i / (double) total) * 5),
                        "actualizando Empresa :" + formatProgress(i, total) + ":" + result + " registros afectados.");
            }
            em.flush();
            em.getTransaction().commit();

            logger.debug("->commit the collection for Empresa OK!!");
        } catch (Exception ex) {
            //logger.error( "inicializarEmpresas:", ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void inicializarMarcas(List<Marca> marcaReceivedList, ProgressProcessListener progressListener) throws Exception {
        logger.debug("->inicializarMarcas: ");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            logger.debug("->Ok prepare to insert the collection for Marca");
            em.getTransaction().begin();
            int total = marcaReceivedList.size();
            int percIni = progressListener.getProgress();
            int i = 0;
            for (Marca marca : marcaReceivedList) {

                Marca marcaNueva = new Marca();
                Linea marcaLineaNueva = em.find(Linea.class, marca.getLinea().getId());
                Empresa marcaEmpresaNueva = em.find(Empresa.class, marca.getEmpresa().getId());

                if (marcaLineaNueva == null || marcaEmpresaNueva == null) {

                    List<Linea> lineaList = em.createQuery("select l from Linea l").getResultList();

                    logger.warn("there is not fucking working : listing Linea");
                    for (Linea l : lineaList) {
                        logger.warn("->\tlinea:" + l.getId() + ":" + l.getNombre());
                    }

                    List<Empresa> empresaList = em.createQuery("select e from Empresa e").getResultList();

                    logger.warn("there is not fucking working : listing Empresa");
                    for (Empresa e : empresaList) {
                        logger.warn("->empresa:" + e.getId() + ":" + e.getNombre());
                    }

                    throw new RollbackException("->for Marca can't be fucking null: linea and/or empresa");
                } else {
                    Query nativeQuery = em.createNativeQuery("INSERT INTO MARCA(ID,LINEA_ID,EMPRESA_ID,NOMBRE) VALUES (?,?,?,?)");
                    nativeQuery.setParameter(1, marca.getId());
                    nativeQuery.setParameter(2, marca.getLinea().getId());
                    nativeQuery.setParameter(3, marca.getEmpresa().getId());
                    nativeQuery.setParameter(4, marca.getNombre());

                    int result = nativeQuery.executeUpdate();
                    i++;
                    progressListener.updateProgress((int) Math.ceil(percIni + ((double) i / (double) total) * 5),
                            "actualizando Marcas :" + formatProgress(i, total) + " registros afectados.");
                }
            }
            em.flush();
            em.getTransaction().commit();

            logger.debug("->commit the collection for Marca OK!!");
        } catch (Exception ex) {
            //logger.error( "inicializarMarcas:", ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void inicializarProductos(List<Producto> productoReceivedList, ProgressProcessListener progressListener) throws Exception {
        logger.debug("->inicializarProductos: ");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            logger.debug("->Ok prepare to insert the collection for Producto");
            em.getTransaction().begin();
            int total = productoReceivedList.size();
            int percIni = progressListener.getProgress();
            int i = 0;
            for (Producto producto : productoReceivedList) {
                Producto productoNuevo = new Producto();
                Marca productoMarcaNueva = em.find(Marca.class, producto.getMarca().getId());

                if (productoMarcaNueva == null) {
                    List<Marca> marcaList = em.createQuery("select m from Marca m").getResultList();

                    logger.warn("there is not fucking working : listing Marca");
                    for (Marca m : marcaList) {
                        logger.warn("->marca:" + m.getId() + ":" + m.getNombre());
                    }

                    throw new RollbackException("->for Producto can't be fucking null: productoMarcaNueva[" + producto.getMarca().getId() + "]=" + productoMarcaNueva);
                } else {
                    Query nativeQuery = em.createNativeQuery("INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,FACTOR_MAX_DESC,COSTO,FACTOR_PRECIO,PRECIO_BASE,UNIDAD_MEDIDA) "
                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");

                    nativeQuery.setParameter(1, producto.getId());
                    nativeQuery.setParameter(2, producto.getMarca().getId());
                    nativeQuery.setParameter(3, producto.getNombre());
                    nativeQuery.setParameter(4, producto.getPresentacion());
                    nativeQuery.setParameter(5, producto.getContenido());
                    nativeQuery.setParameter(6, producto.getUnidadesPorCaja());
                    nativeQuery.setParameter(7, producto.getFactorMaxDesc());
                    nativeQuery.setParameter(8, producto.getCosto());
                    nativeQuery.setParameter(9, producto.getFactorPrecio());
                    nativeQuery.setParameter(10, producto.getPrecioBase());
                    nativeQuery.setParameter(11, producto.getUnidadMedida());

                    int result = nativeQuery.executeUpdate();

                    i++;
                    progressListener.updateProgress((int) Math.ceil(percIni + ((double) i / (double) total) * 5),
                            "actualizando Productos :" + formatProgress(i, total) + " registros afectados.");
                }
            }

            em.flush();
            em.getTransaction().commit();

            logger.debug("->commit the collection for Producto OK!!");
        } catch (Exception ex) {
            //logger.error( "inicializarProductos:", ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void inicializarMultimedio(List<Multimedio> multimedioReceivedList, ProgressProcessListener progressListener) throws Exception {
        logger.debug("->inicializarMultimedio: ");

        if (multimedioReceivedList == null) {
            logger.warn("->multimedioReceivedList is null !");
            return;
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            logger.debug("->Ok prepare to insert the collection for Multimedio");
            em.getTransaction().begin();
            int total = multimedioReceivedList.size();
            int percIni = progressListener.getProgress();
            int i = 0;
            for (Multimedio multimedio : multimedioReceivedList) {
                Query nativeQuery = em.createNativeQuery("INSERT INTO MULTIMEDIO(ID,CONTENIDO,MIME_TYPE,NOMBRE_ARCHIVO) VALUES(?,?,?,?)");
                nativeQuery.setParameter(1, multimedio.getId());
                nativeQuery.setParameter(2, multimedio.getContenido());
                nativeQuery.setParameter(3, multimedio.getMimeType());
                nativeQuery.setParameter(4, multimedio.getNombreArchivo());
                int result = nativeQuery.executeUpdate();
                em.flush();
                i++;
                Collection<Producto> productoCollection = multimedio.getProductoCollection();

                if (productoCollection != null) {
                    for (Producto producto : productoCollection) {
                        Query nativeQueryInner = em.createNativeQuery("INSERT INTO PRODUCTO_MULTIMEDIO(PRODUCTO_ID,MULTIMEDIO_ID) VALUES(?,?)");
                        nativeQueryInner.setParameter(1, producto.getId());
                        nativeQueryInner.setParameter(2, multimedio.getId());
                        nativeQueryInner.executeUpdate();
                    }
                }

                progressListener.updateProgress((int) Math.ceil(percIni + ((double) i / (double) total) * 5),
                        "actualizando Multimedio :" + formatProgress(i, total) + ":" + result + " registros afectados.");
            }

            em.getTransaction().commit();

            logger.debug("->commit the collection for Multimedio OK!!");

        } catch (Exception ex) {
            //logger.error( "inicializarMultimedio:", ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void inicializarFormaDePago(List<FormaDePago> formaDePagoReceivedList, ProgressProcessListener progressListener) throws Exception {

        logger.debug("->inicializarFormaDePagos: ");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            logger.debug("->Ok prepare to insert the collection for FormaDePago");
            em.getTransaction().begin();
            int total = formaDePagoReceivedList.size();
            int percIni = progressListener.getProgress();
            int i = 0;
            for (FormaDePago formaDePago : formaDePagoReceivedList) {
                Query nativeQuery = em.createNativeQuery("INSERT INTO FORMA_DE_PAGO(ID,DESCRIPCION) VALUES (?,?)");
                nativeQuery.setParameter(1, formaDePago.getId());
                nativeQuery.setParameter(2, formaDePago.getDescripcion());
                int result = nativeQuery.executeUpdate();
                i++;
                progressListener.updateProgress((int) Math.ceil(percIni + ((double) i / (double) total) * 5),
                        "actualizando FormaDePago :" + formatProgress(i, total) + ":" + result + " registros afectados.");
            }
            em.flush();
            em.getTransaction().commit();

            logger.debug("->commit the collection for FormaDePago OK!!");
        } catch (Exception ex) {
            //logger.error( "inicializarFormaDePago:", ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void inicializarEstado(List<Estado> estadoReceivedList, ProgressProcessListener progressListener) throws Exception {
        logger.debug("->inicializarEstado: ");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            logger.debug("->Ok prepare to insert the collection for Estado");
            em.getTransaction().begin();
            int total = estadoReceivedList.size();
            int percIni = progressListener.getProgress();
            int i = 0;
            for (Estado estado : estadoReceivedList) {
                Query nativeQuery = em.createNativeQuery("INSERT INTO ESTADO(ID,DESCRIPCION) VALUES (?,?)");
                nativeQuery.setParameter(1, estado.getId());
                nativeQuery.setParameter(2, estado.getDescripcion());
                int result = nativeQuery.executeUpdate();
                i++;
                progressListener.updateProgress((int) Math.ceil(percIni + ((double) i / (double) total) * 5),
                        "actualizando Estado :" + formatProgress(i, total) + ":" + result + " registros afectados.");
            }
            em.flush();
            em.getTransaction().commit();

            logger.debug("->commit the collection for Estado OK!!");
        } catch (Exception ex) {
            //logger.error( "inicializarEstado:", ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public void inicializarCliente(List<Cliente> clienteReceivedList, ProgressProcessListener progressListener) throws Exception {
        logger.debug("->inicializarClientes: V5");
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            logger.debug("->Ok prepare to insert the collection for Cliente");
            em.getTransaction().begin();

            List<Cliente> clientes = em.createQuery("select c from Cliente c").getResultList();
            logger.debug("->Clientes currently found:" + clientes.size());
            for (Cliente c : clientes) {
                logger.debug("->Cliente currently found:" + c);
            }

            int total = clienteReceivedList.size();
            int percIni = progressListener.getProgress();
            int i = 0;

            BeanUtils bu = new BeanUtils();
            int maxId = -1;
            for (Cliente cliente : clienteReceivedList) {

                logger.debug("->Cliente toInsert:" + bu.describe(cliente));

                Query nativeQuery = em.createNativeQuery("INSERT INTO CLIENTE("
                        + "ID,CLASIFICACION_FISCAL,RFC,FECHA_CREACION,RAZON_SOCIAL,"
                        + "NOMBRE_ESTABLECIMIENTO,CALLE,NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,"
                        + "TELEFONOS,FAXES,TELEFONOS_MOVILES,EMAIL,PLAZO_DE_PAGO,"
                        + "URL,OBSERVACIONES,DESCRIPCION_RUTA) VALUES "
                        + "(?,?,?,?,?,"
                        + (cliente.getNombreEstablecimiento() != null ? ("'" + cliente.getNombreEstablecimiento() + "'") : "NULL") + ",?," + (cliente.getNumInterior() != null ? "'" + cliente.getNumInterior() + "'" : "NULL") + "," + (cliente.getNumExterior() != null ? "'" + cliente.getNumExterior() + "'" : "NULL") + ",?,"
                        + (cliente.getTelefonos() != null ? "'" + cliente.getTelefonos() + "'" : "NULL") + "," + (cliente.getFaxes() != null ? "'" + cliente.getFaxes() + "'" : "NULL") + "," + (cliente.getTelefonosMoviles() != null ? "'" + cliente.getTelefonosMoviles() + "'" : "NULL") + "," + (cliente.getEmail() != null ? "'" + cliente.getEmail() + "'" : "NULL") + "," + (cliente.getPlazoDePago() != null ? cliente.getPlazoDePago().toString() : "NULL") + ","
                        + (cliente.getUrl() != null ? "'" + cliente.getUrl() + "'" : "NULL") + "," + (cliente.getObservaciones() != null ? "'" + cliente.getObservaciones() + "'" : "NULL") + "," + (cliente.getDescripcionRuta() != null ? "'" + cliente.getDescripcionRuta() + "'" : "NULL") + ")");

                int paramNQ = 0;
                maxId = cliente.getId() > maxId ? cliente.getId(): maxId;
                
                nativeQuery.setParameter(++paramNQ, cliente.getId());
                nativeQuery.setParameter(++paramNQ, cliente.getClasificacionFiscal());
                nativeQuery.setParameter(++paramNQ, cliente.getRfc());
                nativeQuery.setParameter(++paramNQ, cliente.getFechaCreacion());
                nativeQuery.setParameter(++paramNQ, cliente.getRazonSocial());

                //nativeQuery.setParameter(++paramNQ, cliente.getNombreEstablecimiento());

                nativeQuery.setParameter(++paramNQ, cliente.getCalle());
                //nativeQuery.setParameter(++paramNQ, cliente.getNumInterior());
                //nativeQuery.setParameter(++paramNQ, cliente.getNumExterior());
                nativeQuery.setParameter(++paramNQ, cliente.getPoblacion().getId());
                //nativeQuery.setParameter(++paramNQ, cliente.getPlazoDePago());

                /*
                 *
                 * Query nativeQuery = em.createNativeQuery("INSERT INTO
                 * CLIENTE(ID,CLASIFICACION_FISCAL,RFC,FECHA_CREACION,RAZON_SOCIAL,NOMBRE_ESTABLECIMIENTO,CALLE,"
                 * +
                 * "NUM_INTERIOR,NUM_EXTERIOR,POBLACION_ID,TELEFONOS,FAXES,TELEFONOS_MOVILES,EMAIL,PLAZO_DE_PAGO,URL,OBSERVACIONES,DESCRIPCION_RUTA)
                 * VALUES " + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
                 * nativeQuery.setParameter(1, cliente.getId());
                 * nativeQuery.setParameter(2, new
                 * Integer(cliente.getClasificacionFiscal()));
                 * nativeQuery.setParameter(3, cliente.getRfc());
                 * nativeQuery.setParameter(4, cliente.getFechaCreacion());
                 * nativeQuery.setParameter(5, cliente.getRazonSocial());
                 * nativeQuery.setParameter(6,
                 * (cliente.getNombreEstablecimiento()==null?"<SIN_NOMBRE>":cliente.getNombreEstablecimiento()));
                 * nativeQuery.setParameter(7, cliente.getCalle());
                 * nativeQuery.setParameter(8, cliente.getNumInterior());
                 * nativeQuery.setParameter(9, cliente.getNumExterior());
                 * nativeQuery.setParameter(10, cliente.getPoblacion().getId());
                 * nativeQuery.setParameter(11, cliente.getTelefonos());
                 * nativeQuery.setParameter(12, cliente.getFaxes());
                 * nativeQuery.setParameter(13, cliente.getTelefonosMoviles());
                 * nativeQuery.setParameter(14, cliente.getEmail());
                 * nativeQuery.setParameter(15, cliente.getPlazoDePago());
                 * nativeQuery.setParameter(16, cliente.getUrl());
                 * nativeQuery.setParameter(17, cliente.getObservaciones());
                 * nativeQuery.setParameter(18, cliente.getDescripcionRuta());
                 */
                int result = nativeQuery.executeUpdate();
                i++;
                progressListener.updateProgress((int) Math.ceil(percIni + ((double) i / (double) total) * 5),
                        "actualizando Cliente :" + formatProgress(i, total) + ":" + result + " registros afectados.");
            }
            em.flush();
            
            logger.debug("============>>> Cleinte: maxId="+maxId);
            
            Query nativeQueryUpdateHUK = em.createNativeQuery("update HIBERNATE_UNIQUE_KEY set NEXT_HI=?");
            nativeQueryUpdateHUK.setParameter(1, maxId+1);
            
            int resultUpdateHUK = nativeQueryUpdateHUK.executeUpdate();
            
            logger.debug("============>>> HIBERNATE_UNIQUE_KEY rows affected="+resultUpdateHUK);
            
            em.flush();
            
            em.getTransaction().commit();

            logger.debug("->commit the collection for Cliente OK!!");
        } catch (Exception ex) {
            //logger.error( "inicializarCliente:", ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                logger.debug("->Rollback executed!!");
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }
    static DecimalFormat df = new DecimalFormat("### %");

    static String formatProgress(int d, int t) {
        return df.format((double) d / (double) t);
    }
    //==========================================================================

    private void copySimpleProperties(Object a, Object b) {
        copySimpleProperties(a, b, new ArrayList<String>());
    }

    private void copySimpleProperties(Object a, Object b, String[] propsNot2Copy) {
        copySimpleProperties(a, b, Arrays.asList(propsNot2Copy));
    }

    private void copySimpleProperties(Object a, Object b, List<String> propsNot2Copy) {
        try {
            logger.debug("->copySimpleProperties(Object a class" + a.getClass() + ",Object b class" + b.getClass() + ")");

            Class ca = a.getClass();
            Class cb = b.getClass();

            if (ca.isInstance(cb)) {
                throw new IllegalArgumentException("Diferent Classes:" + ca.getClass() + " !=" + cb.getClass());
            }

            Field[] fus = cb.getDeclaredFields();
            for (Field f : fus) {
                if (propsNot2Copy.contains(f.getName())) {
                    logger.debug("\t->exclude:" + f);
                    continue;
                }

                int mod = f.getModifiers();
                if ((mod & Modifier.PRIVATE) != 0
                        && (mod & Modifier.FINAL) == 0
                        && (mod & Modifier.STATIC) == 0
                        && !f.getName().toLowerCase().contains("collection")) {

                    String setterName = "set"
                            + f.getName().substring(0, 1).toUpperCase()
                            + f.getName().substring(1);

                    Method mSetter = ca.getMethod(setterName, new Class[]{f.getType()});

                    String getterName = "get"
                            + f.getName().substring(0, 1).toUpperCase()
                            + f.getName().substring(1);

                    Method mGetter = cb.getMethod(getterName, new Class[]{});

                    Object r = mGetter.invoke(b);
                    mSetter.invoke(a, r);
                    Object rx = mGetter.invoke(a);
                    logger.debug("x." + setterName + "(bean." + getterName + "());");
                    //logger.debug("\t=> invoke a."+mSetter+"( b."+mGetter+") = "+r+" => result :"+rx);
                }
            }
        } catch (Exception e) {
            logger.error("Some has wrong", e);
        }
    }
}
