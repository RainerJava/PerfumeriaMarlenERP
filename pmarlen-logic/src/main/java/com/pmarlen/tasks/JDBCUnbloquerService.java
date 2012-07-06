/**
 * Archivo: JDBCUnbloquerTask.java
 * 
 * Fecha de Creaci&oacute;n: 26/09/2011
 *
 * 2H Software - Bursatec 2011
 */
package com.pmarlen.tasks;

import com.pmarlen.model.beans.Producto;
import java.util.List;
import java.util.TimerTask;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Alfredo Estrada Gonz&aacute;lez.
 * @version 1.0
 *
 */

@Service("jdbcUnbloquerService")
public class JDBCUnbloquerService extends TimerTask{

	private EntityManagerFactory emf = null;
	
  	public JDBCUnbloquerService(){
		logger = LoggerFactory.getLogger(JDBCUnbloquerService.class.getSimpleName());        
        logger.debug("->JDBCUnbloquerTask, created");		
	}
	
    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

	private Logger logger;


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private List<Producto> findProductoForReport() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT p FROM Producto p");

            List<Producto> resultList = q.getResultList();

            return resultList;
        } finally {
            em.close();
        }
    }

	@Scheduled(cron = "*/30 * * * * *")
	public void run() {
		List<Producto> productoForReport = findProductoForReport();
		logger.debug("->> run: productoForReport.size="+productoForReport.size());		
		for(Producto p: productoForReport) {
			logger.debug("->> \trun: Producto:"+p.getNombre());			
		}
	}	
}
