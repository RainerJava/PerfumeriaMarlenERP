/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client;

import com.tracktopell.dbutil.DBInstaller;
import com.tracktopell.dbutil.DerbyDBInstaller;

/**
 *
 * @author praxis
 */
public class ExploreDB {
    public static void main(String[] args) {
        DBInstaller dbInstaller;
        try {
            dbInstaller = new DerbyDBInstaller("classpath:/jdbc.properties");
            dbInstaller.shellDB();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
