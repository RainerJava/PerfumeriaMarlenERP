/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.client.controller;

import com.pmarlen.client.ApplicationStarter;
import com.pmarlen.client.model.ApplicationSession;
import com.pmarlen.client.model.LoginModel;
import com.pmarlen.client.view.LoginFrame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alfred
 */
@Controller("loginControl")
public class LoginControl{    
    
    LoginFrame loginFrame;
    
    LoginModel loginModel;    
    
    ApplicationSession as = null;

    @Autowired
    private PrincipalControl principalControl;
    //private static LoginControl instance;

    public LoginControl(){
	System.err.println("===============>>> try to construct LoginControl ");
	try {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		if(env == null) { throw new Exception("error 1");}	
		GraphicsDevice[] devices = env.getScreenDevices();
		if(devices == null || devices.length == 0) { throw new Exception("error 2");}	

        	loginFrame = new LoginFrame();
	        loginModel = new LoginModel();

        	loginFrame.getEntratBtn().addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                entrar();
        	    }
	        });
	        loginFrame.getPassword().addActionListener(new ActionListener() {
        	    public void actionPerformed(ActionEvent e) {
	                entrar();
        	    }
	        });
        	loginFrame.getClaveUsuario().addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
        	        entrar();
	            }
	        });
	} catch(Exception ex){
		ex.printStackTrace(System.err);
	}
    }

//    public static LoginControl getInstance() {
//        if(instance == null) {
//            instance = new LoginControl();
//        }
//        return instance;
//    }

    public void estadoInicial() {
        loginFrame.getClaveUsuario().setText("");
        loginFrame.getPassword().setText("");
        loginModel.setIntentos(0);
        loginModel.setClaveUsuario(null);
        loginModel.setPassword(null);        

        loginFrame.setVisible(true);
        loginFrame.getClaveUsuario().requestFocus();
    }
    
    boolean validateMinimunLength() {
        return  loginFrame.getClaveUsuario().getText().trim().length() >= 3 &&
                (new String(loginFrame.getPassword().getPassword())).trim().length()>= 3;
    }
    void entrar(){
        if( validateMinimunLength()) {
            loginModel.setClaveUsuario(loginFrame.getClaveUsuario().getText());
            loginModel.setPassword(new String(loginFrame.getPassword().getPassword()));
            Cursor c = loginFrame.getCursor();
            try {
                loginFrame.getEntratBtn().setEnabled(false);
                loginFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                as = ApplicationStarter.getInstance().login(
                        loginModel.getClaveUsuario(), loginModel.getPassword());
                loggedIn();
            } catch(Exception ex) {
                ex.printStackTrace(System.err);

                loginModel.setIntentos(loginModel.getIntentos()+1);
                JOptionPane.showMessageDialog(loginFrame, ex.getMessage(), "Login",
                        JOptionPane.ERROR_MESSAGE);
                if(loginModel.getIntentos()>=3){
                    System.exit(1);
                }                
            } finally {
                loginFrame.getEntratBtn().setEnabled(true);
                loginFrame.setCursor(c);
            }
        }
    }

    void loggedIn(){
        principalControl.setup();
        loginFrame.setVisible(false);        
        principalControl.estadoInicial();
    }

    /**
     * @return the principalControl
     */
    public PrincipalControl getPrincipalControl() {
        return principalControl;
    }

    /**
     * @param principalControl the principalControl to set
     */
    public void setPrincipalControl(PrincipalControl principalControl) {
        this.principalControl = principalControl;
    }

}
