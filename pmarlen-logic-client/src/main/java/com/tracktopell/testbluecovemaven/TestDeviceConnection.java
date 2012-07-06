/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracktopell.testbluecovemaven;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author alfredo
 */
public class TestDeviceConnection {

    public static boolean tryToConnect(String btAdress) {
        OutputStream os = null;
        StreamConnection conn = null;
        InputStream is = null;
        String urlBTAddress = null;
        
        System.err.println("==>> TestDeviceConnection to "+btAdress);
            
        try {       
            System.err.println("==>> try to connect !");
            urlBTAddress = "btspp://"+btAdress.replace(":", "") +":1";
            
            conn = (StreamConnection) Connector.open(urlBTAddress);
            os = conn.openOutputStream();
            
            System.err.println("==>> ok connected !");
            return true;
        }catch(Exception ex) {
            ex.printStackTrace(System.err);
            return false;
        } finally {
            try {
                if(os != null) {
                    os.close();
                    conn.close();
                    System.err.println("==>> ok disconnected !");
                }
                if(is != null) {
                    is.close();
                }
            } catch(IOException ioe) {
                ioe.printStackTrace(System.err);
            }
        }
    }
}
