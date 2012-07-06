package com.pmarlen.dev.task;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * CrearImagenesDePruebaEnMultimedio
 */
public class CrearImagenesDePruebaEnMultimedio {

    public void crearImagesnes(String url, String user, String password, String dirImages, String maskFileName) {
		//System.err.println(" \t====> debug: url="+url+", user="+user+", password="+password+", dirImages="+dirImages+", maskFileName="+maskFileName);
		
        Connection conn = null;
        PreparedStatement psProducto = null;
        PreparedStatement psMultimedio = null;
        PreparedStatement psInsertMultimedio = null;
        PreparedStatement psInsertProductoMultimedio = null;

        ResultSet rsProducto = null;
        ResultSet rsMultimedio = null;
        ArrayList<Integer> productoIds = new ArrayList<Integer>();
        ArrayList<Integer> multimedioIds = null;
        DecimalFormat df = new DecimalFormat("0000");
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");

        float jpegImageQuality = 1.0f;
        //----------------

        // Get a ImageWriter for jpeg format.
        Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No JPEG writers found");
        }
        ImageWriter jpegImageWriter = (ImageWriter) writers.next();
        
        ImageWriteParam param = jpegImageWriter.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(jpegImageQuality);

        try {
            conn = DriverManager.getConnection(url, user, password);

            psProducto = conn.prepareStatement("SELECT ID FROM PRODUCTO");
            rsProducto = psProducto.executeQuery();

            while (rsProducto.next()) {
                Integer productoId = rsProducto.getInt("Id");
                productoIds.add(productoId);
            }
            rsProducto.close();
            psProducto.close();

            System.err.println("Ok, found " + productoIds.size() + " productos.");

            psMultimedio = conn.prepareStatement("SELECT PRODUCTO_ID,MULTIMEDIO_ID FROM PRODUCTO_MULTIMEDIO WHERE PRODUCTO_ID = ?");
            psInsertMultimedio = conn.prepareStatement("INSERT INTO MULTIMEDIO (CONTENIDO,MIME_TYPE,NOMBRE_ARCHIVO) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            psInsertProductoMultimedio = conn.prepareStatement("INSERT INTO PRODUCTO_MULTIMEDIO(PRODUCTO_ID,MULTIMEDIO_ID) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            for (Integer productoId : productoIds) {
                psMultimedio.clearParameters();

                psMultimedio.setInt(1, productoId);
                rsMultimedio = psMultimedio.executeQuery();

                multimedioIds = new ArrayList<Integer>();
                while (rsMultimedio.next()) {
                    Integer multimedioId = rsMultimedio.getInt("MULTIMEDIO_ID");
                    multimedioIds.add(multimedioId);
                }
                rsMultimedio.close();
                int rowsAffected = -1;
                if (multimedioIds.size() > 0) {
                    Statement st = conn.createStatement();
                    for (Integer multimedioId : multimedioIds) {
                        System.err.println("\tDelete multimedio resources for producto:" + productoId);
                        rowsAffected = -1;
                        if ((rowsAffected = st.executeUpdate("DELETE FROM PRODUCTO_MULTIMEDIO WHERE MULTIMEDIO_ID=" + multimedioId)) < 1) {
                            System.err.println(" in delete PRODUCTO_MULTIMEDIO with MULTIMEDIO_ID=" + multimedioId + ", affects :" + rowsAffected);
                        }
                        rowsAffected = -1;
                        if ((rowsAffected = st.executeUpdate("DELETE FROM MULTIMEDIO WHERE ID=" + multimedioId)) < 1) {
                            System.err.println(" in delete MULTIMEDIO with ID=" + multimedioId + ", affects :" + rowsAffected);
                        }

                    }
                    st.close();
                }

                psInsertMultimedio.clearParameters();
                //--------------------------------------------------------------
                boolean loadFromfile;
                InputStream is = null;
                
                loadFromfile = (dirImages != null && maskFileName != null);
                if( loadFromfile ) {
                    File imageFile = new File(dirImages + File.separator + maskFileName.replace("@PRODUCTO_ID@", df.format(productoId)));
                    
                    
                    System.err.println(" ====> debug: maskFileName=\""+maskFileName+"\", filename="+maskFileName.replace("@PRODUCTO_ID@", df.format(productoId))+", replaced by: "+df.format(productoId));
                    if (imageFile.exists() && imageFile.canRead()) {
                        try {
                            is = new FileInputStream(imageFile);
                            System.err.println("\tOk loaded image for producto:" + productoId);
                        } catch (IOException ioe) {
                            ioe.printStackTrace(System.err);
                            loadFromfile = false;
                        }
                    } else {
                        System.err.println("\tCan't read image from :->" + imageFile + "<- : " + imageFile.exists() + " && " + imageFile.canRead());
                        loadFromfile = false;
                    }
                }

                if (!loadFromfile) {

                    if(maskFileName == null) {
                        maskFileName = "@PRODUCTO_ID@.JPG";
                    }

                    BufferedImage bi = null;
                    bi = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

                    Graphics2D g2d = (Graphics2D) bi.getGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
                    g2d.setColor(Color.GREEN);
                    g2d.drawString("IMAGEN TEMPORAL" + productoId, 5, bi.getHeight() / 2);
                    g2d.drawString("PRODUCTO :" + productoId, 5, bi.getHeight() / 2 + 50);
                    g2d.drawString("FECHA :" + sdf.format(new Date()), 5, bi.getHeight() / 2 + 100);


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
                    jpegImageWriter.setOutput(ios);
                    jpegImageWriter.write(null, new IIOImage(bi, null, null), param);
                    //ImageIO.write(bi, "JPEG", baos);

                    byte[] imageBytes = null;
                    imageBytes = baos.toByteArray();

                    is = new ByteArrayInputStream(imageBytes);
                    System.err.println("\tOk created image for producto:" + productoId);
                }

                psInsertMultimedio.setBlob(1, is);
                psInsertMultimedio.setString(2, "image/jpeg");
                psInsertMultimedio.setString(3, maskFileName.replace("@PRODUCTO_ID@", df.format(productoId)));

                Integer multimedioId = null;
                rowsAffected = psInsertMultimedio.executeUpdate();
                if (rowsAffected < 1) {
                    System.err.println(" in insert MULTIMEDIO just affects :" + rowsAffected);
                } else {
                    ResultSet rsgk = psInsertMultimedio.getGeneratedKeys();
                    if (rsgk.next()) {
                        multimedioId = rsgk.getInt(1);
                    }
                    rsgk.close();
                }

                if (multimedioId != null) {
                    psInsertProductoMultimedio.clearParameters();
                    psInsertProductoMultimedio.setInt(1, productoId);
                    psInsertProductoMultimedio.setInt(2, multimedioId);

                    rowsAffected = psInsertProductoMultimedio.executeUpdate();
                    if (rowsAffected < 1) {
                        System.err.println(" in insert PRODUCTO_MULTIMEDIO just affects :" + rowsAffected);
                    }
                }
            }

            conn.close();

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return;
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return;
        }
    }

    public void extraerImagenes(String url, String user, String password, String dirImages) {

        Connection conn = null;
        PreparedStatement psMultimedio = null;

        ResultSet rsMultimedio = null;
        byte[] buffer = new byte[1028 * 64];
        try {
            conn = DriverManager.getConnection(url, user, password);

            psMultimedio = conn.prepareStatement("SELECT ID,CONTENIDO,NOMBRE_ARCHIVO FROM MULTIMEDIO");
            rsMultimedio = psMultimedio.executeQuery();


            while (rsMultimedio.next()) {
                Integer multimedioId = rsMultimedio.getInt("ID");
                String nombrearchivo = rsMultimedio.getString("NOMBRE_ARCHIVO");

                InputStream is = rsMultimedio.getBinaryStream("CONTENIDO");
                FileOutputStream fos = new FileOutputStream(dirImages + File.separator + nombrearchivo);
                int r = -1;
                while ((r = is.read(buffer, 0, buffer.length)) != -1) {
                    fos.write(buffer, 0, r);
                }
                is.close();
                fos.close();
                System.err.println("\tOk exploded image for multimedio:" + multimedioId);
            }
            rsMultimedio.close();
            psMultimedio.close();

            conn.close();

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return;
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return;
        }
    }

    public static void main(String[] args) {
        CrearImagenesDePruebaEnMultimedio cidpm = new CrearImagenesDePruebaEnMultimedio();
        if(args.length == 0){
            System.err.println(" -u  url  user  password [ dirImages  @PRODUCTO_ID@.JPG ]");
            System.err.println(" -x  url  user  password   dirImages");
            
            System.exit(1);
        }
        if (args[0].equals("-u")) {
            if(args.length == 4) {
                cidpm.crearImagesnes(args[1], args[2], args[3], null, null);
            } else {
                cidpm.crearImagesnes(args[1], args[2], args[3], args[4], args[5]);
            }
        } else if (args[0].equals("-x")) {
            cidpm.extraerImagenes(args[1], args[2], args[3], args[4]);
        }

    }
}
