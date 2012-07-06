/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.businesslogic.reports;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author VEAXX9M
 */
public class TestJR {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String reportLocation = "/reports/report16_1.jrxml";
            JasperDesign jd = JRXmlLoader.load(TestJR.class.getResourceAsStream(reportLocation));
            System.err.println("->OK JasperDesign, loaded from :"+reportLocation);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            System.err.println("->OK JasperReport compiled from design.");

            JRDataSource jrds = null;

            ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
            Random random = new Random(System.currentTimeMillis());
            DecimalFormat df = new DecimalFormat("##################.00");
            double subtotal = 0.0;
            for (int i=0;i<4;i++) {
                HashMap<String, Object> dataHashMap = new HashMap<String, Object>();
                int cant = random.nextInt(1+100);
                double punitario = 10+random.nextDouble()*1000;

                dataHashMap.put("r_cant",cant);
                dataHashMap.put("r_unidad","PZ");
                dataHashMap.put("r_descripcion" ,"PRODUCTO "+random.nextDouble());
                dataHashMap.put("r_punitario" , punitario);
                dataHashMap.put("r_importe" , cant * punitario);

                subtotal += cant*punitario;
                dataList.add(dataHashMap);
            }

            jrds = new JRMapCollectionDataSource(dataList);
            HashMap<String, Object> parametters = new HashMap<String, Object>();
            
            double total = subtotal * 1.16;
            
            CantidadAMoneda cam =  new CantidadAMoneda();
            String totalFormateadoCadena = df.format(total);

            int total_letra_entero    = Integer.parseInt(totalFormateadoCadena.substring(0,totalFormateadoCadena.length()-3));
            int total_letra_decimales = Integer.parseInt(totalFormateadoCadena.substring(totalFormateadoCadena.length()-2));
            String importe_letra = ("==[" + cam.convertirLetras(total_letra_entero)+
                    " PESOS " + 
                    cam.convertirLetras(total_letra_decimales)+"/100 M.N.]==").toUpperCase();
            parametters.put("facturado_a" ,"FACTURADO A");
            parametters.put("rfc"  ,"EAGA8012254X2");
            parametters.put("fact_no" , 12121212L);
            parametters.put("fecha" ,new Date());
            parametters.put("condiciones"  ,"PAGO EN EFECTIVO");
            parametters.put("total_letra"  ,importe_letra);
            parametters.put("subtotal" , subtotal);
            parametters.put("iva" , subtotal * 0.16);
            parametters.put("total" , total);
            
            /*
            BufferedImage img = null;
            try {
                img = ImageIO.read(new FileInputStream(args[1]));
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
            parametters.put("image2.object", img);
            */

            JasperPrint jp = JasperFillManager.fillReport(jr,parametters , jrds);
            System.err.println("->OK JasperPrint filled and ready to deliver to viewer AND export.");
            //------------------------------------------------------------------
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
            System.err.println("->Exporting ....");
            JasperExportManager.exportReportToHtmlFile(jp, "./out_JR_"+sdf.format(now)+".html");
            System.err.println("->OK, JasperExportManager.exportReportToHtmlFile");
            JasperExportManager.exportReportToPdfFile(jp, "./out_JR_"+sdf.format(now)+".pdf");
            System.err.println("->OK, JasperExportManager.exportReportToPdfFile");
            JasperExportManager.exportReportToXmlFile(jp, "./out_JR_"+sdf.format(now)+".xml", false);
            System.err.println("->OK, JasperExportManager.exportReportToXmlFile");
            JasperPrintManager.printReport(jp,false);
            System.err.println("->OK, JasperExportManager.printReport");

            //------------------------------------------------------------------            
            JasperViewer.viewReport(jp);
            System.err.println("->OK JasperViewer viwing the report.");
            //------------------------------------------------------------------
            
            System.err.println("->OK, End JasperReports test !!");
        } catch (JRException ex) {
            ex.printStackTrace(System.err);
        }
    }

}
