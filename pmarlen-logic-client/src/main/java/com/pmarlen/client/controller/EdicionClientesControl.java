/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client.controller;

import com.pmarlen.client.ApplicationInfo;
import com.pmarlen.client.ApplicationLogic;
import com.pmarlen.client.BusinessException;
import com.pmarlen.client.model.EntidadFederativa;
import com.pmarlen.client.model.ValidatorHelper;
import com.pmarlen.client.view.EdicionClienteDialog;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.controller.BasicInfoDAO;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alfred
 */
@Controller("edicionClientesControl")
public class EdicionClientesControl {
    private Logger logger;

    @Autowired
    private ApplicationLogic applicationLogic;
    
    @Autowired
    private PrincipalControl principalControl;

    @Autowired
    private BasicInfoDAO basicInfoDAO;
	
	@Autowired
	PoblacionFinderControl poblacionFinderControl;
    
    Cliente cliente;
	
	Poblacion poblacionSeleccionada;
    EdicionClienteDialog edicionClienteDialog;
    //private static EdicionClientesControl instance;
    private int exitStatus;
    boolean selectingByPoblacion = false;
    JComponentValidator validator;

    public EdicionClientesControl() {
        logger = LoggerFactory.getLogger(EdicionClientesControl.class);
    }
    public void setup() {
        edicionClienteDialog = new EdicionClienteDialog(principalControl.getPrincipalForm());

        validator = new JComponentValidator(edicionClienteDialog, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_TITLE")) ;

        validator.add(new ValidatorHelper(edicionClienteDialog.getRazonSocial(), "[a-zA-Z]{2}[a-zA-Z0-9\\. ]{5,126}", true, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_RAZON_SOCIAL"), ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED")));
        validator.add(new ValidatorHelper(edicionClienteDialog.getRfc(),         "[a-zA-Z]{3,4}[0-9]{6}[a-zA-Z0-9]{3}", true, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_RFC"), ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED")));
        validator.add(new ValidatorHelper(edicionClienteDialog.getCalle(),       "[a-zA-Z0-9\\. #-,]{1,128}", true, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_CALLE"), ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED")));
        validator.add(new ValidatorHelper(edicionClienteDialog.getNoInterior(),  "[a-zA-Z0-9\\. #-,]{1,16}", false, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_NOINT"), ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED")));
        validator.add(new ValidatorHelper(edicionClienteDialog.getNoExterior(),  "[a-zA-Z0-9\\. #-,]{1,16}", false, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_NOEXT"), ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED")));
//        validator.add(new ValidatorHelper(edicionClienteDialog.getEntidadFederativa(),  ""                  , true, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_ENTFED"),ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED_LIST")));
//        validator.add(new ValidatorHelper(edicionClienteDialog.getMunicipioODelegacion(),  ""               , true, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_MPIO"),ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED_LIST")));
//        validator.add(new ValidatorHelper(edicionClienteDialog.getCodigoPostal(),  ""                       , true, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_CP"),ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED_LIST")));
//        validator.add(new ValidatorHelper(edicionClienteDialog.getPoblacion(),  ""                          , true, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_POB"),ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED_LIST")));
//		validator.add(new ValidatorHelper(edicionClienteDialog.getDireccion(),  "[a-zA-Z0-9\\. #-,]{5,255}"  , true, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_DIR"),ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED_LIST")));
        validator.add(new ValidatorHelper(edicionClienteDialog.getTelefonos(),  "[0-9]{4,8}(,[0-9]{4,8})*", false, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_TELSNO"), ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED")));
        validator.add(new ValidatorHelper(edicionClienteDialog.getEmail(),  "([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)", false, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_MAIL"), ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED")));
        validator.add(new ValidatorHelper(edicionClienteDialog.getObservaciones(),  "[a-zA-Z0-9\\. #-,]{1,255}", false, ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_COMMENT"), ApplicationInfo.getLocalizedMessage("DLG_EDIT_CLIENTE_MSG_REQUIRED")));

        List<EntidadFederativa> entidadList;
        entidadList = basicInfoDAO.getEntidadFederativaList();

        edicionClienteDialog.getGuardar().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                aceptar();
            }
        });

        edicionClienteDialog.getCancelar().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });
        
        edicionClienteDialog.getBtnBuscarPoblacion().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                buscarPoblacion();
            }

        });

        edicionClienteDialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                cancelar();
            }
        });
        
        edicionClienteDialog.getRootPane().setDefaultButton(edicionClienteDialog.getGuardar());

    }

    void setCliente(Cliente c) {
        this.cliente = c;
    }

    public void editarCliente(Cliente clienteEditar) {
        cliente = clienteEditar;
        estadoInicial();
    }

    public void crearCliente() {
        cliente = new Cliente();

//        cliente.setCalle("");
//        cliente.setClasificacionFiscal(-1);
//        cliente.setDescripcionRuta("");
//        cliente.setEmail("");
//        cliente.setFaxes("");
//        cliente.setFechaCreacion(new Date());
//        cliente.setNombreEstablecimiento("");
//        cliente.setNumExterior("");
//        cliente.setNumInterior("");
//        cliente.setObservaciones("");
//        cliente.setPlazoDePago(-1);
//        cliente.setPoblacion(null);
//        cliente.setRazonSocial("");
//        
        estadoInicial();
    }

    void estadoInicial() {        
        if(edicionClienteDialog==null){
            setup();
        }        
        edicionClienteDialog.getNoExterior().setText(cliente.getNumExterior() != null && cliente.getNumExterior().trim().length() == 0 ? cliente.getNumExterior() : "");
        edicionClienteDialog.getCalle().setText(cliente.getCalle());
        
        logger.debug("===>> estadoInicial: Cliente numInterior="+cliente.getNumInterior()+", numExterior="+cliente.getNumExterior());
        
        edicionClienteDialog.getNoInterior().setText(cliente.getNumInterior() != null && cliente.getNumInterior().trim().length() > 0 ? cliente.getNumInterior() : "");
		edicionClienteDialog.getNoExterior().setText(cliente.getNumExterior() != null && cliente.getNumExterior().trim().length() > 0 ? cliente.getNumExterior() : "");
        edicionClienteDialog.getRazonSocial().setText(cliente.getRazonSocial());
        edicionClienteDialog.getEmail().setText(cliente.getEmail());
        edicionClienteDialog.getTelefonos().setText(cliente.getTelefonos());
        
		logger.debug("===>> estadoInicial: Cliente poblacion="+cliente.getPoblacion());
        if(cliente.getPoblacion() != null ){
            poblacionSeleccionada = cliente.getPoblacion();

            edicionClienteDialog.getDireccion().setText(cliente.getPoblacion().getNombre());
            edicionClienteDialog.getCodigoPostal().setText(cliente.getPoblacion().getCodigoPostal());
            edicionClienteDialog.getDelegacionOMunicipio().setText(cliente.getPoblacion().getMunicipioODelegacion());
            edicionClienteDialog.getEntidadFederativa().setText(cliente.getPoblacion().getEntidadFederativa());
        }
        
		edicionClienteDialog.getObservaciones().setText(cliente.getObservaciones()!=null?cliente.getObservaciones().trim():"");
        
        edicionClienteDialog.getRfc().setText(cliente.getRfc());

        edicionClienteDialog.getGuardar().setEnabled(true);
        edicionClienteDialog.getCancelar().setEnabled(true);

        if (!edicionClienteDialog.isVisible()) {
            centerInScreenAndSetVisible(edicionClienteDialog);
        }
    }

    void fillCliente() throws Exception {
        cliente.setNumExterior(edicionClienteDialog.getNoExterior().getText().trim());
        cliente.setNumInterior(edicionClienteDialog.getNoInterior().getText().trim());
        
        cliente.setTelefonos(edicionClienteDialog.getTelefonos().getText().trim());
        
        cliente.setCalle(edicionClienteDialog.getCalle().getText());
        cliente.setRazonSocial(edicionClienteDialog.getRazonSocial().getText().trim());
        if (cliente.getId() == null) {
            cliente.setFechaCreacion(new Date());
        }
        cliente.setRfc(edicionClienteDialog.getRfc().getText().trim());
        //cliente.setPoblacion((Poblacion) edicionClienteDialog.getPoblacion().getSelectedItem());
        cliente.setObservaciones(edicionClienteDialog.getObservaciones().getText().trim()+"~"+edicionClienteDialog.getDireccion().getText());
        cliente.setEmail(edicionClienteDialog.getEmail().getText().trim());
    }

    public void aceptar() {
        logger.debug("[ACEPTAR]");
        try {
			
            if ( validator.validate() ) {
				
				if(poblacionSeleccionada == null) {
					throw new Exception(BusinessException.getLocalizedMessage("APP_LOGIC_CLIENTE_SELECT_POBLACION"));
				}
				
                fillCliente();
                cliente.setClasificacionFiscal(1);
                cliente.setPoblacion(poblacionSeleccionada);
                cliente.setNombreEstablecimiento("");
                cliente.setFaxes("");
                cliente.setTelefonosMoviles("");
                cliente.setUrl("");
                cliente.setDescripcionRuta("");
                cliente.setPlazoDePago(30);
                logger.debug("==>>aceptar(): Cliente="+cliente.getId());
                applicationLogic.persistCliente(cliente);  
                
                edicionClienteDialog.setVisible(false);
                exitStatus = JOptionPane.OK_OPTION;
            }
        } catch (BusinessException ex) {
			logger.error("-->> despues de aceptar guardar BusinessException:", ex);
            JOptionPane.showMessageDialog(edicionClienteDialog,
                    ex.getMessage(),
                    ex.getTitle(),
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
			logger.error("-->> despues de aceptar guardar Exception:", ex);
            JOptionPane.showMessageDialog(edicionClienteDialog,
					ex.getMessage(),
                    BusinessException.getLocalizedMessage("APP_LOGIC_CLIENTE_NOT_SAVED"),                    
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelar() {
        edicionClienteDialog.setVisible(false);
        exitStatus = JOptionPane.CANCEL_OPTION;
    }
    
    private void buscarPoblacion() {
        logger.debug("==>>buscarPoblacion(): CP="+edicionClienteDialog.getCodigoPostal().getText().trim()+", poblacionSeleccionada="+poblacionSeleccionada);
		if(poblacionSeleccionada != null){
			poblacionFinderControl.estadoInicial(poblacionSeleccionada);
		} else {
			poblacionFinderControl.estadoInicial(edicionClienteDialog.getCodigoPostal().getText().trim());
		}
		
		if(poblacionFinderControl.getExitStatus() == JOptionPane.OK_OPTION) {
			poblacionSeleccionada = poblacionFinderControl.getPoblacionSelected();

			logger.debug("==>>buscarPoblacion: poblacionSelected="+poblacionSeleccionada);
			poblacionEncontrada(poblacionSeleccionada);
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

	private void poblacionEncontrada(Poblacion poblacionSelected) {
		
		edicionClienteDialog.getDireccion().setText(poblacionSelected.getNombre());
		edicionClienteDialog.getDelegacionOMunicipio().setText(poblacionSelected.getMunicipioODelegacion());
		edicionClienteDialog.getEntidadFederativa().setText(poblacionSelected.getEntidadFederativa());
		edicionClienteDialog.getCodigoPostal().setText(poblacionSelected.getCodigoPostal());
		
	}
}
