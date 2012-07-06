/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.servlet;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author VEAXX9M
 */
public class DynamicImagesProducto extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(ContextAndSessionListener.class);

//    @Autowired
//    ProductoJpaController productoJpaController;
    private String jndi_datasource_name;
    public void init(ServletConfig config) {
        //SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        InputStream is = config.getServletContext().getResourceAsStream("/jdbc.properties");
        logger.debug("====>>>1) servelt  DynamicImagesProducto.init: Can read from Inputstream /jdbc.properties ? "+(is != null));
        
        jndi_datasource_name = config.getInitParameter("jndi_datasource_name");
        /*
        ServletContext servletContext =this.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
        String beanNames[] = wac.getBeanDefinitionNames();
        logger.debug("====>>>3) servelt DynamicImagesProducto.init: SpringContext");
        for(String name: beanNames) {
            logger.debug("====>>> \tservelt DynamicImagesProducto.init: SpringContext : Bean "+name);
        }
        */
    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream os = response.getOutputStream();

        String productoIdParam = request.getParameter("id");
        String imageSizeParam = null;
        Integer productoId = null;
        Dimension imageSize = null;

        if (productoIdParam != null) {
            String[] productoIdParamValues = productoIdParam.split("_");
            productoId = Integer.parseInt(productoIdParamValues[0]);
            if (productoIdParamValues.length == 2) {
                imageSizeParam = productoIdParamValues[1];
            }
        }

        logger.debug("--->>> DynamicImagesProducto: productoIdParam=" + productoIdParam + ", imageSizeParam=" + imageSizeParam);
        logger.debug("--->>> DynamicImagesProducto: 0");
        if (imageSizeParam != null) {
            if (imageSizeParam.contains("x")) {
                String[] imageSizeComponents = imageSizeParam.split("x");
                imageSize = new Dimension(Integer.parseInt(imageSizeComponents[0]), Integer.parseInt(imageSizeComponents[0]));
                logger.debug("--->>> DynamicImagesProducto: 0.2.1 : imageSize="+imageSize);
            }
        }


        try {

            logger.debug("--->>> DynamicImagesProducto: 5");
            BufferedImage originalImage = getFirstMultimedioForProductoId(productoId, imageSizeParam);

            if (originalImage != null) {
                                
                response.setContentType("image/jpeg");
                
                if (imageSize != null) {
                    Image scalledImage = originalImage.getScaledInstance(imageSize.width, imageSize.height, Image.SCALE_SMOOTH);
                    BufferedImage scalledBufferedImage = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);
                    scalledBufferedImage.getGraphics().drawImage(scalledImage, 0, 0, null);

                    ImageIO.write(scalledBufferedImage, "JPEG", os);
                } else {
                    ImageIO.write(originalImage, "JPEG", os);                    
                }
                os.flush();
                logger.debug("--->>> DynamicImagesProducto: 5.8: ok write Image from Multimedio");
                
            } else {
                logger.debug("--->>> DynamicImagesProducto: 6: originalImage="+originalImage+" !! fix this with default image !!");
                
                InputStream isdi = DynamicImagesProducto.class.getResourceAsStream("/imgs/proximamente.jpg");
                
                BufferedImage defaultImage = ImageIO.read(isdi);
                
                if (imageSize != null) {
                    Image scalledImage = defaultImage.getScaledInstance(imageSize.width, imageSize.height, Image.SCALE_SMOOTH);
                    BufferedImage scalledBufferedImage = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_RGB);
                    scalledBufferedImage.getGraphics().drawImage(scalledImage, 0, 0, null);

                    ImageIO.write(scalledBufferedImage, "JPEG", os);
                } else {
                    ImageIO.write(defaultImage, "JPEG", os);                    
                }
                os.flush();
                
                logger.debug("--->>> DynamicImagesProducto: 7: ok write default image");
            }
        } finally {
            os.close();            
        }
    }

    private DataSource datasource;

    private Connection conn;    

    private BufferedImage getFirstMultimedioForProductoId(Integer productoId, String mimeType) {
        BufferedImage bufferedImage = null;
        if (datasource == null) {
            try {
                InitialContext cxt = new InitialContext();
                //datasource = (DataSource) cxt.lookup("java:/comp/env/jdbc/PMARLEN_DB_TEST_DS");
                datasource = (DataSource) cxt.lookup(jndi_datasource_name);
                logger.debug("\t==>> getFirstMultimedioForProductoId: DataSource retrieved.");
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
                return null;
            }
        }
        try {
            if (conn == null || conn.isClosed()) {
                conn = datasource.getConnection();
                logger.debug("\t==>> getFirstMultimedioForProductoId: Connection retrieved.");                
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return null;
        }

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT MU.CONTENIDO,MU.MIME_TYPE FROM MULTIMEDIO MU,PRODUCTO_MULTIMEDIO PM WHERE PM.PRODUCTO_ID=? AND PM.MULTIMEDIO_ID=MU.ID");
            logger.debug("\t==>> getFirstMultimedioForProductoId: PreparedStatement created.");
            preparedStatement.setInt(1, productoId);
            ResultSet rs = preparedStatement.executeQuery();
            logger.debug("\t==>> getFirstMultimedioForProductoId: Query executed.");
            while(rs.next()){
                if(rs.getString(2).startsWith("image")){
                    bufferedImage = ImageIO.read(rs.getBinaryStream(1));
                    logger.debug("\t==>> getFirstMultimedioForProductoId: BufferedImage retrieved.");
                    break;
                }
            }
            rs.close();
            preparedStatement.close();
            preparedStatement = null;
            rs = null;
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            return null;
        }

        return bufferedImage;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
