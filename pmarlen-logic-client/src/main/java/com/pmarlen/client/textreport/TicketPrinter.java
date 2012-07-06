/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client.textreport;

import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.tracktopell.testbluecovemaven.SendBytesToDevice;
import com.tracktopell.testbluecovemaven.TestDeviceConnection;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alfredo
 */
public class TicketPrinter {
    private static final String SPACES = "                                                    ";
    private static final String DEFAULT_BT_PRINTER = "00:03:7A:6D:14:BC";
    private static final String TICKET_TEST        = "/ticket_layout/TICKET_TEST.txt";    
    private static final String TICKET_LAYOUT_FILE = "/ticket_layout/TICKET_DEFINITION_FISCAL.txt";
    
    private static SimpleDateFormat sdf_fecha_full = new SimpleDateFormat("yyyyMMdd_hhmmss");    
    private static SimpleDateFormat sdf_fecha = new SimpleDateFormat("yyyy/MM/dd");
    private static SimpleDateFormat sdf_hora = new SimpleDateFormat("hh:mm");
    private static int maxCharsPerColumns = 48;

    public static String generateTicket(PedidoVenta pedidoVenta) {
        String tiketPrinted = null;
        PrintStream psPrintTicket = null;
        
        
        InputStream is = TicketPrinter.class.getResourceAsStream(TICKET_LAYOUT_FILE);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            String line = null;
            Date fecha = new Date();
            tiketPrinted = "TICKET_"+pedidoVenta.getId()+"_"+sdf_fecha_full.format(fecha)+".TXT";
            psPrintTicket = new PrintStream(new File(tiketPrinted),"ISO-8859-1");

            HashMap<String, String> staticVars = new HashMap();
            //${FECHA},${HORA}
            staticVars.put("${FECHA}", sdf_fecha.format(fecha));
            staticVars.put("${HORA}", sdf_hora.format(fecha));
            staticVars.put("${PREPEDIDO}", pedidoVenta.getId().toString());

            List<String> agenteNombreList = justifyText(pedidoVenta.getUsuario().getNombreCompleto(), 36);
            staticVars.put("${AGENTE_0}", agenteNombreList.get(0));
            if (agenteNombreList.size() > 1) {
                staticVars.put("?{AGENTE_1}", agenteNombreList.get(1));
            }

            List<String> clienteNombreList = justifyText(pedidoVenta.getCliente().getRazonSocial()!=null?   pedidoVenta.getCliente().getRazonSocial():
                                                                                                            pedidoVenta.getCliente().getNombreEstablecimiento(), 36);
            staticVars.put("${CLIENTE_0}", clienteNombreList.get(0));
            if (agenteNombreList.size() > 1) {
                staticVars.put("?{CLIENTE_1}", clienteNombreList.get(1));
            }
            if (agenteNombreList.size() > 2) {
                staticVars.put("?{CLIENTE_2}", clienteNombreList.get(2));
            }

            String direccion = pedidoVenta.getCliente().getCalle() + " "
                    + pedidoVenta.getCliente().getNumExterior() + " "
                    + pedidoVenta.getCliente().getNumInterior() + ", "
                    + pedidoVenta.getCliente().getPoblacion().getNombre() + ", "
                    + pedidoVenta.getCliente().getPoblacion().getMunicipioODelegacion() + ", "
                    + pedidoVenta.getCliente().getPoblacion().getEntidadFederativa();

            List<String> direccionList = justifyText(direccion, 36);

            staticVars.put("${DIRECCION_0}", direccionList.get(0));
            if (direccionList.size() > 1) {
                staticVars.put("?{DIRECCION_1}", direccionList.get(1));
            }
            if (direccionList.size() > 2) {
                staticVars.put("?{DIRECCION_2}", direccionList.get(2));
            }
            if (direccionList.size() > 3) {
                staticVars.put("?{DIRECCION_3}", direccionList.get(3));
            }

            boolean skipLine = false;
            boolean detailStart = false;
            boolean expandDetail = false;
            int numIter = 1;
            List<String> iterationLines = new ArrayList<String>();
            List<PedidoVentaDetalle> pedidoVentaDetalleCollection = new ArrayList<PedidoVentaDetalle>();
            Collection<PedidoVentaDetalle> pedidoVentaDetalleCollectionOriginal = pedidoVenta.getPedidoVentaDetalleCollection();
            
            for(PedidoVentaDetalle pvdOrig: pedidoVentaDetalleCollectionOriginal) {
                pedidoVentaDetalleCollection.add(pvdOrig);
            }
            
            
            while ((line = br.readLine()) != null) {
                if (line.contains("#{")) {
                    if (line.contains("#{DETAIL_START}")) {
                        detailStart = true;
                        iterationLines.clear();
                        continue;
                    } else if (line.contains("#{DETAIL_END}")) {
                        detailStart = false;
                        expandDetail = true;
                    }
                } else if (!detailStart) {
                    iterationLines.clear();
                    iterationLines.add(line);
                    //System.out.print("#_>>"+iterationLines.size()+"\t");
                }

                if (detailStart) {
                    iterationLines.add(line);
                    //System.out.println("#=>>"+line);
                    continue;
                }

                if (expandDetail) {
                    numIter = pedidoVentaDetalleCollection.size();
                } else {
                    numIter = 1;
                }
                DecimalFormat df_6 = new DecimalFormat("#####0");
                DecimalFormat df_6_2 = new DecimalFormat("###,##0.00");
                DecimalFormat df_9_2 = new DecimalFormat("###,###,##0.00");
                DecimalFormat dfs9_2 = new DecimalFormat("########0.00");


                double sum_importe = 0.0;
                double importe = 0.0;
                double sum_desc = 0.0;
                double desc = 0.0;

                for (int i = 0; i < numIter; i++) {

                    if (expandDetail) {
                        staticVars.put("${CANT}"    , alignTextRigth(df_6.format(pedidoVentaDetalleCollection.get(i).getCantidad()),4));
                        String productoPresentacion = pedidoVentaDetalleCollection.get(i).getProducto().getNombre()+
                                "("+pedidoVentaDetalleCollection.get(i).getProducto().getPresentacion()+")";
                        staticVars.put("${PRODUCTO_PRESENTACION}", alignTextLeft (productoPresentacion,42));

                        importe  = pedidoVentaDetalleCollection.get(i).getCantidad() * pedidoVentaDetalleCollection.get(i).getPrecioBase();
                        desc     = pedidoVentaDetalleCollection.get(i).getPrecioBase() * pedidoVentaDetalleCollection.get(i).getDescuentoAplicado();
                        sum_importe += importe;
                        sum_desc += desc;

                        staticVars.put("${PRECIO}" , alignTextRigth(df_6_2.format(pedidoVentaDetalleCollection.get(i).getPrecioBase()),10));
                        staticVars.put("${DESCDET}", alignTextRigth(df_6_2.format(desc),10));                        
                        staticVars.put("${IMPORTE}", alignTextRigth(df_6_2.format(importe),10));
                    }

                    for (String innerLine : iterationLines) {

                        skipLine = false;
                        Iterator<String> ik = staticVars.keySet().iterator();
                        while (ik.hasNext()) {
                            String k = ik.next();
                            if (innerLine.contains(k)) {
                                innerLine = innerLine.replace(k, staticVars.get(k));
                            }
                            //System.out.println("\t\t===>>> replace "+k+" ->"+staticVars.get(k));
                        }
                        if (innerLine.indexOf("?{") >= 0) {
                            String optionalField = innerLine.substring(innerLine.indexOf("?{"), innerLine.indexOf("}"));
                            if (!staticVars.containsKey(optionalField)) {
                                skipLine = true;
                            }
                        }
                        if (!skipLine) {
                            //System.out.println("=>>" + innerLine);
                            psPrintTicket.print(innerLine+"\r");
                        } else {
                            //System.out.println("X=>>" + innerLine);
                        }

                    }
                }

                if (expandDetail) {
                    //System.out.println("#=>>______________");
                    expandDetail = false;
                    staticVars.put("${SUBTOTAL}" , alignTextRigth(df_9_2.format(sum_importe),12));
                    staticVars.put("${DESCUENTO}", alignTextRigth(df_6_2.format(sum_desc),12));
                    double total = sum_importe - sum_desc;
                    String strTotal = dfs9_2.format(total);
                    //staticVars.put("${IVA}", df_6_2.format(sum_importe * 0.16));
                    staticVars.put("${TOTAL}", alignTextRigth(df_6_2.format(total),12));

                    
                    String enterosLetra  = NumeroCastellano.numeroACastellano(Long.parseLong(strTotal.substring(0, strTotal.indexOf(".")))).toUpperCase().trim();
                    String centavosLetra = strTotal.substring(strTotal.indexOf(".") + 1);

                    List<String> totalLetraList = justifyText("[" + enterosLetra + " " + centavosLetra + "/100 M.N.]", maxCharsPerColumns);

                    staticVars.put("${TOTAL_LETRA_0}", totalLetraList.get(0));
                    if (totalLetraList.size() > 1) {
                        staticVars.put("?{TOTAL_LETRA_1}", totalLetraList.get(1));
                    }
                }
            }
        } catch (IOException ex) {
            //ex.printStackTrace(System.err);
            throw new IllegalStateException("No sepuede generar el Ticket:");
        } finally {
            if(psPrintTicket!= null){
                psPrintTicket.close();
            }
        }

        return tiketPrinted;
    }
    
    private static String alignTextRigth(String text, int maxPerColumn) {
        try{
            if(text.trim().length()<maxPerColumn){
                
                return SPACES.substring(0,maxPerColumn-text.trim().length())+
                        text;
            }else {
                return text.trim().substring(0, maxPerColumn);
            }
        }catch(Exception ex){
            System.out.println("\t==>>> alignTextRigth: ->"+text.trim()+"<-["+text.trim().length()+"],"+maxPerColumn+":"+ex.getMessage());
            return text.trim();
        }
    }
    
    private static String alignTextLeft(String text, int maxPerColumn) {
        try{
            if(text.trim().length()<maxPerColumn){                
                return text+SPACES.substring(0,maxPerColumn-text.trim().length());
            }else {
                return text.trim().substring(0, maxPerColumn);
            }
        }catch(Exception ex){
            System.out.println("\t==>>> alignTextLeft: ->"+text.trim()+"<-["+text.trim().length()+"],"+maxPerColumn+":"+ex.getMessage());
            return text.trim();
        }
    }

    private static List<String> justifyText(String text, int maxPerColumn) {
        List<String> result = new ArrayList<String>();

        String[] words = text.split("\\s");
        String currentLine = "";

        for (String w : words) {
            if (w.trim().length() == 0) {
                continue;
            }
            if (currentLine.length() + 1 + w.length() <= maxPerColumn) {
                if (currentLine.length() == 0) {
                    currentLine = w;
                } else {
                    currentLine = currentLine + " " + w;
                }

            } else {
                result.add(currentLine);
                currentLine = w;
            }
        }

        if (currentLine.length() > 0) {
            result.add(currentLine);
            currentLine = "";
        }

        return result;
    }
    
    public static void print(String ticketFileName) {        
        try {
            SendBytesToDevice.print(DEFAULT_BT_PRINTER,new FileInputStream(ticketFileName));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.err);
        }
    }

    public static void testDefaultPrinter() {
        //if(TestDeviceConnection.tryToConnect(DEFAULT_BT_PRINTER)) {                   
        SendBytesToDevice.print(DEFAULT_BT_PRINTER, TicketPrinter.class.getResourceAsStream(TICKET_TEST));        
        //}
    }
}
