/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.dev.task;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportProductoRelatedEntitiesFromXLSX {

    private Hashtable<Integer, ProveedorEXCECLValue> proveedorHT;
    private Hashtable<String, EmpresaEXCECLValue> empresaHT;
    private Hashtable<String, LineaEXCECLValue> lineaHT;
    private Hashtable<String, MarcaEXCECLValue> marcaHT;
    private Hashtable<Integer, ProductoEXCECLValue> productoHT;

    private DecimalFormat dfClave;
    private DecimalFormat dfImporte;
    private DecimalFormat dfCodigoBarras;

    public ImportProductoRelatedEntitiesFromXLSX() {
        proveedorHT = new Hashtable<Integer, ProveedorEXCECLValue>();
        empresaHT   = new Hashtable<String , EmpresaEXCECLValue>();
        lineaHT     = new Hashtable<String , LineaEXCECLValue>();
        marcaHT     = new Hashtable<String , MarcaEXCECLValue>();
        productoHT  = new Hashtable<Integer, ProductoEXCECLValue>();

        dfClave   = new DecimalFormat("0000");
        dfCodigoBarras   = new DecimalFormat("#########0000");
        dfImporte = new DecimalFormat("#########0.000000");

    }

    public static void main(String[] args) {
        
//        args = new String[] {"/home/praxis/tracktopell/ultimos_respaldos/Archivos_importacion/Base_de_productos_ultima_definitiva.xlsx",
//                             "3",
//                             "524",                             
//                             "./out_ImportProductoRelatedEntitiesFromXLSX_"+(System.currentTimeMillis())+".sql"};
        
        if (args.length != 4) {
            System.err.println("Usage: file.xlsx firstRow2Import lastRow2Import minCels2Scan outputFile");
            System.exit(1);
        }

        String fileName     = args[0];
        int firstRow2Import = Integer.parseInt(args[1]) - 1;
        int lastRow2Import  = Integer.parseInt(args[2]) - 1;
        String outputFile   = args[3];

        ImportProductoRelatedEntitiesFromXLSX ipreX = new ImportProductoRelatedEntitiesFromXLSX();
        try {
            ipreX.importFromProductosXLSX(fileName, firstRow2Import, lastRow2Import, outputFile);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace(System.err);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.err);
        }

    }

    private void importFromProductosXLSX(String fileName, int firstRow2Import, int lastRow2Import, String outputFile)
            throws  UnsupportedEncodingException,
                    FileNotFoundException {

        PrintWriter    pwOut = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFile),"UTF8"));

        //XSSFWorkbook embeddedWorkbook = null;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fileName);
            Iterator<XSSFSheet> iteratorXSSFSheet = workbook.iterator();
            while (iteratorXSSFSheet.hasNext()) {
                XSSFSheet xssfSheet = iteratorXSSFSheet.next();
                System.err.print("SheetName: " + xssfSheet.getSheetName() + ", rows: [" + xssfSheet.getFirstRowNum() + ", " + xssfSheet.getLastRowNum() + "]");

                if (firstRow2Import >= xssfSheet.getFirstRowNum() && firstRow2Import <= xssfSheet.getLastRowNum()
                        && lastRow2Import <= xssfSheet.getLastRowNum()) {
                }

                Iterator<Row> rowIterator = xssfSheet.rowIterator();

                for (int rowNum = firstRow2Import; rowNum <= lastRow2Import; rowNum++) {
                    Row row = xssfSheet.getRow(rowNum);

                    if (row == null) {
                        continue;
                    }
                    
                    processRowProductos(row);

                }

            }
            /*
            for(ProveedorEXCECLValue pev:proveedorHT.values()){
                pwOut.print("-- pev->"+pev.id+", \""+pev.nombre+"\"");
                pwOut.print("\n");
            }
            */

            pwOut.print("-- ==================== EMPRESA: "+empresaHT.size()+" ITEMS");
            pwOut.print("\n");
            int idEmpresa = 1;
            for(EmpresaEXCECLValue eev:empresaHT.values()){
                eev.id = (idEmpresa++);
                pwOut.print("INSERT INTO EMPRESA(ID,NOMBRE,NOMBRE_FISCAL) VALUES ("+eev.id+",'"+eev.nombre+"','"+eev.nombre+"');");
                pwOut.print("\n");
            }
            pwOut.print("\n");
            pwOut.print("-- ==================== LINEA: "+lineaHT.size()+" ITEMS");
            pwOut.print("\n");
            int idLinea = 1;
            for(LineaEXCECLValue lev:lineaHT.values()){
                lev.id = (idLinea++);
                pwOut.print("INSERT INTO LINEA(ID,NOMBRE) VALUES ("+lev.id+", '"+lev.nombre+"');");
                pwOut.print("\n");
            }
            pwOut.print("\n");
            pwOut.print("-- ==================== MARCA: "+marcaHT.size()+" ITEMS");
            pwOut.print("\n");
            int idMarca = 1;
            for(MarcaEXCECLValue mev:marcaHT.values()){
                mev.id = (idMarca++);
                pwOut.print("INSERT INTO MARCA(ID,LINEA_ID,EMPRESA_ID,NOMBRE) VALUES ("+mev.id+","+mev.linea.id+","+mev.empresa.id+", '"+mev.nombre+"');");
                pwOut.print("\n");
            }
            pwOut.print("\n");
            pwOut.print("-- ==================== PRODUCTO: "+productoHT.size()+" ITEMS");
            pwOut.print("\n");
            int np=0;
            for(ProductoEXCECLValue prodev:productoHT.values()){
                pwOut.print("INSERT INTO PRODUCTO(ID,MARCA_ID,NOMBRE,PRESENTACION,CONTENIDO,UNIDADES_POR_CAJA,FACTOR_MAX_DESC,COSTO,FACTOR_PRECIO,PRECIO_BASE,UNIDAD_MEDIDA) VALUES");
                pwOut.print("("+prodev.id+","+prodev.marca.id+",'"+prodev.descripcion+"','"+prodev.presentacion+"',"+prodev.cont+","+prodev.unidadesXCaja+","+prodev.factorMaxDesc+","+prodev.costo+","+prodev.factorPrecio+","+dfImporte.format(prodev.factorPrecio*prodev.costo).trim()+",'"+prodev.unidadMedida+"');");
                pwOut.print("\n");
                pwOut.print("INSERT INTO CODIGO_DE_BARRAS(CODIGO,PRODUCTO_ID) VALUES");
                pwOut.print("('"+prodev.codigoBarras+"',"+prodev.id+");");
                pwOut.print("\n");
                pwOut.print("\n");
            }
            pwOut.print("\n");
            pwOut.print("-- ============================= END EXPORT");
            pwOut.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }

    }

    private void processRowProductos(Row row) throws ParseException {
        int cells = row.getPhysicalNumberOfCells();
        //----------------------------------------------------------------------
        ProveedorEXCECLValue pev = new ProveedorEXCECLValue();

        pev.id      = dfClave.parse(row.getCell(0).toString()).intValue();
        pev.nombre  = row.getCell(1).toString();

        if(proveedorHT.get(pev.id) == null){
            proveedorHT.put(pev.id, pev);
        }
        //----------------------------------------------------------------------
        EmpresaEXCECLValue eev = new EmpresaEXCECLValue();

        eev.id      = null;
        eev.nombre  = row.getCell(2).toString();

        if(empresaHT.get(eev.nombre) == null){
            empresaHT.put(eev.nombre, eev);
        }
        //----------------------------------------------------------------------
        LineaEXCECLValue lev = new LineaEXCECLValue();

        lev.id      = null;
        lev.nombre  = row.getCell(4).toString();

        if(lineaHT.get(lev.nombre) == null){
            lineaHT.put(lev.nombre, lev);
        }

        //----------------------------------------------------------------------
        MarcaEXCECLValue mev = new MarcaEXCECLValue();

        mev.id      = null;
        mev.nombre  = row.getCell(3).toString();
        mev.linea   = lineaHT.get(lev.nombre);
        mev.empresa = empresaHT.get(eev.nombre);

        if(marcaHT.get(mev.nombre)==null){
            marcaHT.put(mev.nombre, mev);
        }

        //----------------------------------------------------------------------

        ProductoEXCECLValue prodev = new ProductoEXCECLValue();

        prodev.id            = dfClave.parse(row.getCell(5).toString()).intValue();
        
        prodev.codigoBarras  = dfCodigoBarras.format(Double.parseDouble(row.getCell(6).toString()));

        prodev.descripcion   = row.getCell(7).toString().replace("'","''");
        prodev.presentacion  = row.getCell(8).toString();
        if(row.getCell(9).toString().equals("-")){
            prodev.cont          = 1;
            prodev.unidadMedida  = "PZ";
        } else {
            prodev.cont          = (int)Double.parseDouble(row.getCell(9).toString());
            prodev.unidadMedida  = row.getCell(10).toString();
        }
        prodev.unidadesXCaja = row.getCell(11).toString();
        prodev.costo         = Double.parseDouble(row.getCell(12).toString());
        prodev.factorPrecio  = Double.parseDouble(row.getCell(16).toString());
        prodev.factorMaxDesc = Double.parseDouble(row.getCell(21).toString());
        prodev.marca         = marcaHT.get(mev.nombre);

        productoHT.put(prodev.id, prodev);
    }

}
