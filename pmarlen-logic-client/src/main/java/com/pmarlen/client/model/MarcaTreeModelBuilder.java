package com.pmarlen.client.model;

import com.pmarlen.model.beans.Empresa;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("marcaTreeModelBuilder")
public class MarcaTreeModelBuilder {
    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private Logger logger;

    private static MarcaTreeModelBuilder instance;

    public static MarcaTreeModelBuilder getInstance() {
        if(instance == null) {
            instance = new MarcaTreeModelBuilder();
        }
        return instance;
    }    

    private MarcaTreeModelBuilder() {
        logger     = LoggerFactory.getLogger(getClass().getSimpleName());
    }

    public void reloadEMF() {
        if(emf!=null) {
            logger.debug("->EntityManagerFactory emf is prepared to close,is open?"+emf.isOpen());
            emf.close();
            logger.debug("->EntityManagerFactory emf referenced null");
            emf = null;
            emf = Persistence.createEntityManagerFactory("ClienteOperacionPU");
            logger.debug("->EntityManagerFactory emf, created");
        }
    }

    public DefaultTreeModel createModelForLineas(){
        EntityManager em = null;
        try {
            em = emf.createEntityManager();            
            // create root node with its children expanded
            DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();
            
            rootTreeNode.setUserObject("Lineas");

            List<Linea> listLineas = null;
            listLineas = (List<Linea>) em.createQuery("select x from Linea x order by x.nombre").getResultList();
            logger.debug("->listLineas.size:"+listLineas.size());
            
            DefaultTreeModel model = new DefaultTreeModel(rootTreeNode);
            
            for (Linea linea : listLineas) {
                DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
                
                branchNode.setAllowsChildren(true);
                branchNode.setUserObject(new LineaTreeNode(linea));
                rootTreeNode.add(branchNode);
                                
                List<Marca> listMarca = null;
                Query ql = em.createQuery("select x from Marca x where x.linea.id=:lineaId  order by x.nombre");
                ql.setParameter("lineaId", linea.getId());
                listMarca = (List<Marca>) ql.getResultList();

                logger.debug("\t->linea["+linea.getNombre()+"].listMarca.size:"+listMarca.size());
                for (Marca marca : listMarca) {
                    DefaultMutableTreeNode subBranchNode = new DefaultMutableTreeNode();
                    subBranchNode.setAllowsChildren(false);
                    subBranchNode.setUserObject(new MarcaTreeNode(marca));
                    branchNode.add(subBranchNode);
                }
            }
            return model;
        } catch (Exception ex) {
            logger.error( "Exception", ex);
            return null;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }

    public DefaultTreeModel createModelForEmpresa(){
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            // create root node with its children expanded
            DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode();

            rootTreeNode.setUserObject("Empresas");

            List<Empresa> listEmpresas = null;
            listEmpresas = (List<Empresa>) em.createQuery("select x from Empresa x order by x.nombre").getResultList();
            logger.debug("->listEmpresas.size:"+listEmpresas.size());
            // model is accessed by by the ice:tree component
            DefaultTreeModel model = new DefaultTreeModel(rootTreeNode);
            logger.debug("-->> building the tree, root");
            // add some child notes
            for (Empresa empresa : listEmpresas) {
                DefaultMutableTreeNode branchNode = new DefaultMutableTreeNode();
                branchNode.setAllowsChildren(true);
                branchNode.setUserObject(new EmpresaTreeNode(empresa));
                rootTreeNode.add(branchNode);                

                List<Marca> listMarca = null;
                Query ql = em.createQuery("select x from Marca x where x.empresa.id=:empresaId  order by x.nombre");
                ql.setParameter("empresaId", empresa.getId());
                listMarca = (List<Marca>) ql.getResultList();


                logger.debug("\t->empresa["+empresa.getNombre()+"].listMarca.size:"+listMarca.size());
                for (Marca marca : listMarca) {
                    DefaultMutableTreeNode subBranchNode = new DefaultMutableTreeNode();
                    subBranchNode.setAllowsChildren(false);
                    subBranchNode.setUserObject(new MarcaTreeNode(marca));

                    branchNode.add(subBranchNode);                    
                }
            }
            return model;
        } catch (Exception ex) {
            logger.error( "Exception", ex);
            return null;
        } finally {
            if (em != null) {
                em.close();
                logger.debug("->Ok, Entity Manager Closed");
            }
        }
    }
}
