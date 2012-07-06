/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.web.operation;

import com.tracktopell.dbutil.DBInstaller;
import com.tracktopell.dbutil.MySQLDBInstaller;

/**
 *
 * @author praxis
 */
public class ReviewDB {
    public static void main(String args[]) {
        DBInstaller dbInstaller;
        try {
            dbInstaller = new MySQLDBInstaller(args[0]);
            dbInstaller.shellDB();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
