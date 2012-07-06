package com.pmarlen.client.view;

import com.pmarlen.client.ProgressProcessListener;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JWindow;

public class SplashWindow extends JWindow implements ProgressProcessListener{

    SplashPanel sp;

    public SplashWindow(String appVersion) {
        super();
        sp = new SplashPanel(appVersion);
		setAlwaysOnTop(false);
		
		sp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2 && me.getButton()==MouseEvent.BUTTON3 ) {
					System.err.println("==>>> SplashWindow: double rigth click to hide !!");
					SplashWindow.this.setAlwaysOnTop(false);					
					setVisible(false);
				}
			}
		});
		
        add(sp);
        this.pack();
    }

    public void updateProgress(int prog, String msg) {
        sp.setMsgProgress(msg);
        sp.setLoadPerc(prog, false);
    }

    public int getProgress() {
        return sp.getLoadPerc();
    }

    public void centerInScreenAndSetVisible() {
        int fw = this.getWidth();
        int fh = this.getHeight();
        Rectangle recScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        this.setBounds(((int) recScreen.getWidth() - fw) / 2, ((int) recScreen.getHeight() - fh) / 2,
                fw, fh);
        this.setVisible(true);
    }
}