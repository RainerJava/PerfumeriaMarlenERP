/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client;

/**
 *
 * @author alfred
 */
public interface ProgressProcessListener {
    void updateProgress(int prog,String msg);
    int getProgress();
}
