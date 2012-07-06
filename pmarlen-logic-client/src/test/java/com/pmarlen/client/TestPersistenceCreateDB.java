/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client;

import com.tracktopell.dbutil.DBInstaller;
import com.tracktopell.dbutil.DerbyDBInstaller;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.junit.*;

import static org.junit.Assert.assertNotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author aestrada
 */
@Ignore
public class TestPersistenceCreateDB {

    final static Logger logger = LoggerFactory.getLogger(TestPersistenceCreateDB.class);
    
	static boolean deleteDBDir4CreateDB = false;
	
    public TestPersistenceCreateDB() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
		if(deleteDBDir4CreateDB) {
			Properties prop = new Properties();

			prop.load(TestPersistenceCreateDB.class.getResourceAsStream("/jdbc.properties"));

			String userHomeDir = System.getProperty("user.home", ".");
			String systemDir = userHomeDir + File.separator + "." + prop.getProperty("app.dataDirectory", "app.dataDirectory");

			File fileAppDir = new File(systemDir);

			if (fileAppDir.exists() && fileAppDir.isDirectory() && fileAppDir.canWrite()) {
				logger.info("try to delete dir: " + fileAppDir.getAbsolutePath());
				boolean result = deleteDirectory(fileAppDir);
				logger.info("Ok, delete dir " + fileAppDir + ", result:" + result);
			}
		}
    }

    private static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
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

    private static boolean displayExist() {
        try {
            java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
            java.awt.Dimension scrnsize = toolkit.getScreenSize();
            return true;
        } catch (java.awt.HeadlessException e) {
            return false;
        }
    }

    @Test
    public void testRealXCrearDB() {
        DBInstaller dbInstaller = null;
        try {
            dbInstaller = new DerbyDBInstaller("classpath:/jdbc.properties");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        if (!dbInstaller.existDB()) {
            dbInstaller.installDBfromScratch();
			logger.debug("=================>After install: load contex to update Hibernate extra tables");
			// TO-DO: 
			// context = new ClassPathXmlApplicationContext(new String[]{"application-context_update.xml"});
			ClassPathXmlApplicationContext context_update = new ClassPathXmlApplicationContext(new String[]{"application-context_update.xml"});
			logger.debug("=================>After install: shutdown Context");
			context_update.registerShutdownHook();			
			context_update.close();
			logger.debug("=================>After install: OK close context ?");
			context_update = null;
			logger.debug("=================>After install: OK load the normal context");			
        }    
    }
    
    @Test
    @Ignore
    public void testCrearDB() {
        if (!displayExist()) {
            logger.info("==> 0: Test aborted DISPLAY NOT EXIST!");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date currDate = new Date();
        logger.info("==> 1:start:" + sdf.format(currDate));

        ApplicationStarter applicationStarter = ApplicationStarter.getInstance();

        try {
            applicationStarter.beginProcess(new ProgressProcessListener() {

                int internalProgress;

                @Override
                public void updateProgress(int prog, String msg) {
                    internalProgress = prog;
                    logger.info("==> updateProgress:" + prog + " : " + msg);
                }

                @Override
                public int getProgress() {
                    return internalProgress;
                }
            });
        } catch (BusinessException e) {
            logger.error("Error: lm=" + e.getLocalizedMessage(), ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_TITLE"), e);
            Assert.fail();
        } catch (Exception e) {
            logger.error("Error: lm2=" + e.getLocalizedMessage(), ApplicationInfo.getLocalizedMessage("APPLICATION_STARTER_TITLE"), e);
            Assert.fail();
        }
        logger.info("==>> 2: Ready to login.");

        ClassPathXmlApplicationContext context = applicationStarter.getContext();

        logger.info("==>> 3: context is null ?" + (context == null));
        assertNotNull("Context is null" + (context == null), context);
    }
}
