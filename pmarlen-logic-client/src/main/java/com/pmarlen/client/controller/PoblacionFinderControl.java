/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client.controller;

import com.pmarlen.client.ApplicationLogic;
import com.pmarlen.client.model.*;
import com.pmarlen.client.view.PoblacionFinderDialog;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.controller.BasicInfoDAO;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import javax.swing.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alfred
 */
@Controller("poblacionFinderControl")
public class PoblacionFinderControl {
    private Logger logger;

    @Autowired
    private ApplicationLogic applicationLogic;
    
    @Autowired
    private PrincipalControl principalControl;

    @Autowired
    private BasicInfoDAO basicInfoDAO;
    
    Cliente cliente;
    PoblacionFinderDialog poblacionFinderDialog;
	
    private int exitStatus;
	
    boolean selectingByPoblacion = false;
	
    JComponentValidator validator;
	
	private Poblacion poblacionSelected;

    public PoblacionFinderControl() {
        logger = LoggerFactory.getLogger(PoblacionFinderControl.class);		
    }
	
    public void setup() {
        poblacionFinderDialog = new PoblacionFinderDialog(principalControl.getPrincipalForm());
		
		poblacionSelected = null;
		
		poblacionFinderDialog.getEntidadFederativa().setModel(new EntidadComboBoxModel(basicInfoDAO.getEntidadFederativaList()));
		
		poblacionFinderDialog.getEntidadFederativa().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JComboBox comboBox = (JComboBox) ae.getSource();
				EntidadFederativa entidadSelected = (EntidadFederativa)comboBox.getSelectedItem();
				actualizarMunicipios(entidadSelected);
			}

		});
		
		poblacionFinderDialog.getDelegacionOMunicipio().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JComboBox comboBox = (JComboBox) ae.getSource();
				MunicipioODelegacion municipioODelegacionSelected = (MunicipioODelegacion)comboBox.getSelectedItem();
				actualizarCodigosPostales(municipioODelegacionSelected);
			}


		});
		
		poblacionFinderDialog.getCodigoPostal().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JComboBox comboBox = (JComboBox) ae.getSource();
				CPDePoblacion cpDePoblacionSelected = (CPDePoblacion)comboBox.getSelectedItem();
				actualizarPoblaciones(cpDePoblacionSelected);
			}
		});
		
		poblacionFinderDialog.getPoblacion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JComboBox comboBox = (JComboBox) ae.getSource();
				PoblacionItem poblacionItemSelected = (PoblacionItem)comboBox.getSelectedItem();
				sleccionPoblacion(poblacionItemSelected);
			}
		});

		poblacionFinderDialog.getAceptar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				aceptarSeleccionPoblacion();
			}

		});

        poblacionFinderDialog.getCancelar().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });

        poblacionFinderDialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                cancelar();
            }
        });
        
        poblacionFinderDialog.getRootPane().setDefaultButton(poblacionFinderDialog.getAceptar());

    }

    void estadoInicial(final String codigoPostalPreeditado) {        
        if(poblacionFinderDialog==null){
            setup();
        }        
		
		poblacionSelected = null;
		
        poblacionFinderDialog.getAceptar().setEnabled(false);
        poblacionFinderDialog.getCancelar().setEnabled(true);

		if (!poblacionFinderDialog.isVisible()) {
			logger.debug("==>>estadoInicial: invocando before setVisible");
			new Thread(){
				@Override
				public void run() {
					if(codigoPostalPreeditado != null) {
						logger.debug("==>>estadoInicial: invocando a inicializarFromCodigoPostal, codigoPostalPreeditado="+codigoPostalPreeditado);
						inicializarFromCodigoPostal(codigoPostalPreeditado);
					}
				}				
			}.start();
            centerInScreenAndSetVisible(poblacionFinderDialog);
			logger.debug("==>>estadoInicial: invocando after setVisible");
        }		
    }
	
	void estadoInicial(final Poblacion poblacion) {        
        if(poblacionFinderDialog==null){
            setup();
        }        
		
		poblacionSelected = null;
		
        poblacionFinderDialog.getAceptar().setEnabled(false);
        poblacionFinderDialog.getCancelar().setEnabled(true);

		if (!poblacionFinderDialog.isVisible()) {
			logger.debug("==>>estadoInicial: invocando before setVisible");
			new Thread(){
				@Override
				public void run() {
					if(poblacion != null) {
						logger.debug("==>>estadoInicial: invocando a inicializarFromPoblacion, poblacion="+poblacion);
						inicializarFromPoblacion(poblacion);
					}
				}
				
			}.start();
            centerInScreenAndSetVisible(poblacionFinderDialog);
			logger.debug("==>>estadoInicial: invocando after setVisible");
        }		
    }


    private void aceptarSeleccionPoblacion() {
	    logger.debug("[ACEPTAR]");
		PoblacionItem poblacionItemSelected = (PoblacionItem)poblacionFinderDialog.getPoblacion().getSelectedItem();
		poblacionSelected = poblacionItemSelected.getPoblacion();
		poblacionFinderDialog.dispose();
        exitStatus = JOptionPane.OK_OPTION;
    }

    public void cancelar() {
        poblacionFinderDialog.dispose();
        exitStatus = JOptionPane.CANCEL_OPTION;
    }
    
	private void actualizarMunicipios(EntidadFederativa entidadSelected) {
		String nombreSelected = entidadSelected.getNombre();
		logger.info("==>>actualizarMunicipios: entidadFederativa="+nombreSelected);
		if(! nombreSelected.equals("--")){
			poblacionFinderDialog.getDelegacionOMunicipio().setModel(
					new MunicipioComboBoxModel(basicInfoDAO.getMunicipioODelegacionList(entidadSelected)));
			//poblacionFinderDialog.getDelegacionOMunicipio().setEnabled(true);
			poblacionFinderDialog.getDelegacionOMunicipio().requestFocus();
			
			poblacionFinderDialog.getPoblacion().setModel(new DefaultComboBoxModel());
			poblacionFinderDialog.getCodigoPostal().setModel(new DefaultComboBoxModel());
		} else {
			poblacionFinderDialog.getDelegacionOMunicipio().setModel(new DefaultComboBoxModel());
			poblacionFinderDialog.getPoblacion().setModel(new DefaultComboBoxModel());
			poblacionFinderDialog.getCodigoPostal().setModel(new DefaultComboBoxModel());
		}
	}
	
	private void actualizarCodigosPostales(MunicipioODelegacion municipioODelegacionSelected) {
		String nombreSelected = municipioODelegacionSelected.getNombre();
		logger.info("==>>actualizarCodigosPostales: municipioODelegacionSelected="+nombreSelected);
		if(! nombreSelected.equals("--")){
			poblacionFinderDialog.getCodigoPostal().setModel(			
					new CodigoPostalComboBoxModel(basicInfoDAO.getCPDePoblacionList(municipioODelegacionSelected)));
			
			poblacionFinderDialog.getPoblacion().setModel(
					new PoblacionComboBoxModel(basicInfoDAO.getPoblacionList(municipioODelegacionSelected)));
			
			poblacionFinderDialog.getCodigoPostal().requestFocus();
		} else {
			poblacionFinderDialog.getCodigoPostal().setModel(new DefaultComboBoxModel());
			poblacionFinderDialog.getPoblacion().setModel(new DefaultComboBoxModel());			
		}
	}
	
	private void actualizarPoblaciones(CPDePoblacion cpDePoblacionSelected) {
		String cpSelected = cpDePoblacionSelected.cp;
		
		logger.info("==>>actualizarPoblaciones: cpSelected="+cpSelected);
		MunicipioODelegacion municipioODelegacionSelected = (MunicipioODelegacion)poblacionFinderDialog.getDelegacionOMunicipio().getSelectedItem();
		
		if(! cpSelected.equals("<TODOS>")){
			poblacionFinderDialog.getPoblacion().setModel(
					new PoblacionComboBoxModel(basicInfoDAO.getPoblacionListForCP(cpDePoblacionSelected)));
			
			poblacionFinderDialog.getPoblacion().requestFocus();
			
			
		} else {
			poblacionFinderDialog.getPoblacion().setModel(
					new PoblacionComboBoxModel(basicInfoDAO.getPoblacionList(municipioODelegacionSelected)));
			
			poblacionFinderDialog.getPoblacion().requestFocus();
		}
	}
	
	private void inicializarFromCodigoPostal(String cpDePoblacionSelected) {
		String cpSelected = cpDePoblacionSelected;
		CPDePoblacion cpDePoblacion = new CPDePoblacion(cpDePoblacionSelected);
		logger.info("==>>inicializarFromCodigoPostal: cpSelected="+cpSelected);		
		
		EntidadFederativa entidadFederativaForCP = basicInfoDAO.getEntidadFederativaForCP(cpDePoblacion);		
		logger.info("==>>inicializarFromCodigoPostal: entidadFederativaForCP="+entidadFederativaForCP);		
		
		EntidadComboBoxModel entidadFederativaModel = (EntidadComboBoxModel)poblacionFinderDialog.getEntidadFederativa().getModel();
		entidadFederativaModel.setSelectedItem(entidadFederativaForCP);
		//----------------------------------------------------------------------		
		actualizarMunicipios(entidadFederativaForCP);
		
		MunicipioODelegacion municipioODelegacionSelected = basicInfoDAO.getMunicipioODelegacionForCP(cpDePoblacion);
		MunicipioComboBoxModel municipioComboBoxModel = (MunicipioComboBoxModel)poblacionFinderDialog.getDelegacionOMunicipio().getModel();		
		municipioComboBoxModel.setSelectedItem(municipioODelegacionSelected);
		//----------------------------------------------------------------------		
		actualizarCodigosPostales(municipioODelegacionSelected);
		CodigoPostalComboBoxModel codigoPostalComboBoxModel=(CodigoPostalComboBoxModel)poblacionFinderDialog.getCodigoPostal().getModel();
		codigoPostalComboBoxModel.setSelectedItem(cpDePoblacion);
		//----------------------------------------------------------------------
		
		poblacionFinderDialog.getPoblacion().setModel(
				new PoblacionComboBoxModel(basicInfoDAO.getPoblacionListForCP(new CPDePoblacion(cpDePoblacionSelected))));		
						 
	}
	
	public void inicializarFromPoblacion(Poblacion poblacion) {		
		logger.info("==>>inicializarFromPoblacion: poblacion="+poblacion);		
		inicializarFromCodigoPostal(poblacion.getCodigoPostal());
		
		PoblacionComboBoxModel poblacionModel = (PoblacionComboBoxModel)poblacionFinderDialog.getPoblacion().getModel();
		poblacionModel.setSelectedItem(new PoblacionItem(poblacion));
		poblacionFinderDialog.getAceptar().setEnabled(true);
		poblacionFinderDialog.getPoblacion().requestFocus();
	}
	private void sleccionPoblacion(PoblacionItem poblacionItemSelected) {
		
		logger.info("==>>sleccionPoblacion: poblacionItemSelected="+poblacionItemSelected);
		
		if(poblacionItemSelected.getPoblacion()!=null) {
			poblacionFinderDialog.getAceptar().setEnabled(true);
		} else {
			poblacionFinderDialog.getAceptar().setEnabled(false);
		}
		
	}
    void centerInScreenAndSetVisible(JDialog w) {
        int fw = w.getWidth();
        int fh = w.getHeight();
        Rectangle recScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        w.setBounds(((int) recScreen.getWidth() - fw) / 2, ((int) recScreen.getHeight() - fh) / 2,
                fw, fh);
        w.setVisible(true);
    }

    /**
     * @return the exitStatus
     */
    public int getExitStatus() {
        return exitStatus;
    }

    /**
     * @return the applicationLogic
     */
    public ApplicationLogic getApplicationLogic() {
        return applicationLogic;
    }

    /**
     * @param applicationLogic the applicationLogic to set
     */
    public void setApplicationLogic(ApplicationLogic applicationLogic) {
        this.applicationLogic = applicationLogic;
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

    /**
     * @return the basicInfoDAO
     */
    public BasicInfoDAO getBasicInfoDAO() {
        return basicInfoDAO;
    }

    /**
     * @param basicInfoDAO the basicInfoDAO to set
     */
    public void setBasicInfoDAO(BasicInfoDAO basicInfoDAO) {
        this.basicInfoDAO = basicInfoDAO;
    }

	/**
	 * @return the poblacionSelected
	 */
	public Poblacion getPoblacionSelected() {
		return poblacionSelected;
	}
	
	
}
