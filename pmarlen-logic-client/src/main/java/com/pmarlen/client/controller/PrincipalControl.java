/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.client.controller;

import com.pmarlen.client.ApplicationInfo;
import com.pmarlen.client.ApplicationLogic;
import com.pmarlen.client.BusinessException;
import com.pmarlen.client.ProgressProcessListener;
import com.pmarlen.client.model.ClienteComboBoxModel;
import com.pmarlen.client.model.ClienteItemList;
import com.pmarlen.client.model.ClientesTableModel;
import com.pmarlen.client.model.EmpresaTreeNode;
import com.pmarlen.client.model.EntityDisplayableItem;
import com.pmarlen.client.model.FormaDePagoComboBoxModel;
import com.pmarlen.client.model.FormaDePagoItemList;
import com.pmarlen.client.model.ImageCellRender;
import com.pmarlen.client.model.LineaTreeNode;
import com.pmarlen.client.model.MarcaTreeModelBuilder;
import com.pmarlen.client.model.MarcaTreeNode;
import com.pmarlen.client.model.PedidoVentaDetalleTableModel;
import com.pmarlen.client.model.PedidosGuardadosTableModel;
import com.pmarlen.client.model.PrincipalModel;
import com.pmarlen.client.model.ProductoFastDisplayModel;
import com.pmarlen.client.model.VisorDeProductosDefaultModel;
import com.pmarlen.client.view.PrincipalForm;
import com.pmarlen.client.view.VisorDeAtributosProductosPanel2;
import com.pmarlen.client.view.VisorDeMarcasPanel;
import com.pmarlen.client.view.VisorDeProductosDefaultPanel;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.controller.BasicInfoDAO;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alfred
 */
@Controller("principalControl")
public class PrincipalControl {

    private PrincipalForm principalForm;
    //private static PrincipalControl instance;
    @Autowired
    private VisorDeProductosDefaultModel vpmMarcaDeLinea;
    @Autowired
    private VisorDeProductosDefaultModel vpmMarcaDeEmpresa;
    private VisorDeMarcasPanel vmpLinea;
    private VisorDeProductosDefaultPanel prodsPorMarcaDeLineaPnl;
    private VisorDeMarcasPanel vmpEmpresa;
    private VisorDeProductosDefaultPanel prodsPorMarcaDeEmpresaPnl;
    private PrincipalModel principalModel;
    String currentTextToSearch;
    private Logger logger;
    @Autowired
    private ApplicationLogic applicationLogic;
    @Autowired
    private BasicInfoDAO basicInfoDAO;
    @Autowired
    private MarcaTreeModelBuilder marcaTreeModelBuilder;
    @Autowired
    private EdicionClientesControl edicionClientesControl;

    public PrincipalControl() {
        logger = LoggerFactory.getLogger(PrincipalControl.class);
    }

//    public static PrincipalControl getInstance() {
//        if (instance == null) {
//            instance = new PrincipalControl();
//        }
//
//        return instance;
//    }
    public void setup() {
        logger.debug("setup():");
        List<Producto> listProductosVacios = null;
        currentTextToSearch = "";
        principalForm = new PrincipalForm();
        principalModel = new PrincipalModel();
        //=====================MENUS=======================
        principalForm.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                exitByClick();
            }
        });
        principalForm.getExitMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        principalForm.getViewCatLineaMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                verCatLinea();
            }
        });
        principalForm.getViewCatEmpresaMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                verCatEmpresa();
            }
        });
        principalForm.getAddProductoMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });
        principalForm.getAddCajaMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                agregarCaja();
            }
        });
        principalForm.getViewDetailMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                verDetalle();
            }
        });
        principalForm.getTestPrintMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                testPrinter();
            }
        });
        principalForm.getViewPedidosMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                verPedidos();
            }
        });
        principalForm.getViewClientesMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                verClientes();
            }
        });
        principalForm.getDeleteItemPedidoMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                eliminarProductoDePedido();
            }
        });
        principalForm.getNuevoClienteBtn().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                createCliente();
            }
        });
        principalForm.getNuevoClienteAComboBox().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                createCliente();
            }
        });

        principalForm.getNombreBuscar().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (getPrincipalForm().getProductoCBEncontrados().getModel().getSize() >= 1) {
                        getPrincipalForm().getProductoCBEncontrados().setSelectedIndex(getPrincipalForm().getProductoCBEncontrados().getModel().getSize() > 1 ? 1 : 0);
                        getPrincipalForm().getProductoCBEncontrados().requestFocus();
                    } else {
                        return;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    updateProductoSelected(null);
                    getPrincipalForm().getDetallePedidoTable().requestFocus();
                }
            }
        });

        principalForm.getNombreBuscar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (getPrincipalForm().getProductoCBEncontrados().getModel().getSize() > 0) {
                    principalForm.getProductoCBEncontrados().setPopupVisible(false);
                    getPrincipalForm().getCantidadPedida().requestFocus();
                }
            }
        });
        principalForm.getProductoCBEncontrados().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    logger.debug("->principalForm.getProductoCBEncontrados().keyPressed(): VK_ENTER");
                    if (getPrincipalForm().getProductoCBEncontrados().getModel().getSize() > 0) {
                        principalForm.getProductoCBEncontrados().setPopupVisible(false);
                        getPrincipalForm().getCantidadPedida().requestFocus();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    updateProductoSelected(null);
                    getPrincipalForm().getDetallePedidoTable().requestFocus();
                }
            }
        });

        principalForm.getNombreBuscar().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!principalForm.getNombreBuscar().getText().toLowerCase().trim().equals(currentTextToSearch)) {
                    currentTextToSearch = principalForm.getNombreBuscar().getText().toLowerCase().trim();
                    logger.debug("->insertUpdate: currentTextToSearch=" + currentTextToSearch);
                    updateProductosFound();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!principalForm.getNombreBuscar().getText().toLowerCase().trim().equals(currentTextToSearch)) {
                    currentTextToSearch = principalForm.getNombreBuscar().getText().toLowerCase().trim();
                    logger.debug("->removeUpdate: currentTextToSearch=" + currentTextToSearch);
                    updateProductosFound();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                logger.debug("->changedUpdate: " + principalForm.getNombreBuscar().getText() + " != " + currentTextToSearch + " ???");
            }
        });
        getPrincipalForm().getProductoCBEncontrados().setModel(new DefaultComboBoxModel(
                applicationLogic.getProducto4Display(currentTextToSearch)));
        getPrincipalForm().getProductoCBEncontrados().addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object itemSelect = e.getItem();
                    updateProductoSelected((ProductoFastDisplayModel) itemSelect);
                }
            }
        });

        getPrincipalForm().getCantidadPedida().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    updateProductoSelected(null);
                    getPrincipalForm().getDetallePedidoTable().requestFocus();
                }
            }
        });

        getPrincipalForm().getCantidadPedida().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                agregarNProducto();
            }
        });

        principalForm.getDetallePedidoTable().addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (principalForm.getDetallePedidoTable().getRowCount() > 0) {
                    if (principalForm.getDetallePedidoTable().getSelectedRowCount() == 0) {
                        principalForm.getDetallePedidoTable().setRowSelectionInterval(0, 0);
                    }
                }
            }
        });
        //----------------------------------------------------------------------
        vmpLinea = (VisorDeMarcasPanel) principalForm.getVisorDeMarcaPorLinea();

        prodsPorMarcaDeLineaPnl = (VisorDeProductosDefaultPanel) vmpLinea.getPanelVisorContainer();
        prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                DefaultListSelectionModel dsm = (DefaultListSelectionModel) e.getSource();
                if (!e.getValueIsAdjusting()) {
                    logger.debug("->ProductoPorLinea selected:" + dsm.getLeadSelectionIndex());
                    updateImageForDisplayInPanel(dsm.getLeadSelectionIndex(), vpmMarcaDeLinea, prodsPorMarcaDeLineaPnl);
                }
            }
        });

        vmpLinea.getMarcasJTree().addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int selRow = vmpLinea.getMarcasJTree().getRowForLocation(e.getX(), e.getY());
                TreePath selPath = vmpLinea.getMarcasJTree().getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 2) {
                        DefaultMutableTreeNode objNode = (DefaultMutableTreeNode) selPath.getLastPathComponent();
                        logger.debug("Clicked by Selection id:" + objNode + " class: " + objNode.getClass());
                        if (objNode.getUserObject() instanceof EntityDisplayableItem) {
                            MarcaTreeNode marcaNode = (MarcaTreeNode) objNode.getUserObject();
                            logger.debug("mousePressed-->> Selected Marca :" + marcaNode.getMarca().getNombre());
                            updateProductosLineaMarca(marcaNode.getMarca());
                        }
                    }
                }
            }
        });
        vmpLinea.getMarcasJTree().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent evt) {


                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    TreePath ps = vmpLinea.getMarcasJTree().getSelectionPath();
                    DefaultMutableTreeNode objNode = (DefaultMutableTreeNode) ps.getLastPathComponent();
                    logger.debug("Last Selected id:" + objNode + " class: " + objNode.getClass());
                    if (objNode.getUserObject() instanceof EntityDisplayableItem) {
                        MarcaTreeNode marcaNode = (MarcaTreeNode) objNode.getUserObject();
                        logger.debug("keyPressed-->> Selected Marca :" + marcaNode.getMarca().getNombre());
                        updateProductosLineaMarca(marcaNode.getMarca());
                    }
                }
            }
        });
        //prodsPorMarcaDeLineaPnl = (VisorDeProductosDefaultPanel) vmpLinea.getPanelVisorContainer();
        updateProductosLineaMarca(null);
        //======================================================================
        vmpEmpresa = (VisorDeMarcasPanel) principalForm.getVisorDeMarcaPorEmpresa();
        prodsPorMarcaDeEmpresaPnl = (VisorDeProductosDefaultPanel) vmpEmpresa.getPanelVisorContainer();
        prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                DefaultListSelectionModel dsm = (DefaultListSelectionModel) e.getSource();
                if (!e.getValueIsAdjusting()) {
                    logger.debug("->ProductoPorEmpresa selected:" + dsm.getLeadSelectionIndex());
                    updateImageForDisplayInPanel(dsm.getLeadSelectionIndex(), vpmMarcaDeEmpresa, prodsPorMarcaDeEmpresaPnl);
                }
            }
        });

        vmpEmpresa.getMarcasJTree().addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int selRow = vmpEmpresa.getMarcasJTree().getRowForLocation(e.getX(), e.getY());
                TreePath selPath = vmpEmpresa.getMarcasJTree().getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 2) {
                        DefaultMutableTreeNode objNode = (DefaultMutableTreeNode) selPath.getLastPathComponent();
                        logger.debug("Clicked by Selection id:" + objNode + " class: " + objNode.getClass());
                        if (objNode.getUserObject() instanceof EntityDisplayableItem) {
                            MarcaTreeNode marcaNode = (MarcaTreeNode) objNode.getUserObject();
                            logger.debug("mousePressed-->> Selected Marca :" + marcaNode.getMarca().getNombre());
                            updateProductosEmpresaMarca(marcaNode.getMarca());
                        }
                    }
                }
            }
        });
        vmpEmpresa.getMarcasJTree().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent evt) {


                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    TreePath ps = vmpEmpresa.getMarcasJTree().getSelectionPath();
                    DefaultMutableTreeNode objNode = (DefaultMutableTreeNode) ps.getLastPathComponent();
                    logger.debug("Last Selected id:" + objNode + " class: " + objNode.getClass());
                    if (objNode.getUserObject() instanceof EntityDisplayableItem) {
                        MarcaTreeNode marcaNode = (MarcaTreeNode) objNode.getUserObject();
                        logger.debug("keyPressed-->>Selected Marca :" + marcaNode.getMarca().getNombre());
                        updateProductosEmpresaMarca(marcaNode.getMarca());
                    }
                }
            }
        });
        //prodsPorMarcaDeEmpresaPnl = (VisorDeProductosDefaultPanel) vmpEmpresa.getPanelVisorContainer();
        updateProductosEmpresaMarca(null);
        //----------------------------------------------------------------------

        principalForm.getEsFiscal().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                updateDetallePedidoTable();
            }
        });

        List<Cliente> listCliente = null;
        List<FormaDePago> listFormaDePago = null;
        try {
            listCliente = basicInfoDAO.getClientesList();
            listFormaDePago =
                    basicInfoDAO.getFormaDePagosList();
        } catch (Exception ex) {
            listCliente = new ArrayList<Cliente>();
            listFormaDePago =
                    new ArrayList<FormaDePago>();
            logger.error("", ex);
        }

        principalForm.getCliente().setModel(new ClienteComboBoxModel(listCliente));
        principalForm.getCliente().addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                logger.debug("->cliente.itemStateChanged" + e.getItem() + ": " + e.getStateChange() + " == " + ItemEvent.SELECTED + "? " + (e.getStateChange() == ItemEvent.SELECTED));
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    verifyAllSelections();
                }

            }
        });
        principalForm.getFormaDePago().setModel(new FormaDePagoComboBoxModel(listFormaDePago));
        principalForm.getFormaDePago().addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                logger.debug("->formaDePago.itemStateChanged: " + e.getItem() + ": " + e.getStateChange() + " == " + ItemEvent.SELECTED + "? " + (e.getStateChange() == ItemEvent.SELECTED));
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    verifyAllSelections();
                }

            }
        });

        principalForm.getProcederPedidoBtn().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                procederPedido();
            }
        });

        principalForm.getReiniciarPedidoBtn().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                reiniciarPedido();
            }
        });

        principalForm.getAvoidPedidoMenu().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                reiniciarPedido();
            }
        });

        principalForm.getEnviarPedidosBtn().addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                enviarPedidos();
            }
        });

        principalForm.getClientesTable().setModel(new ClientesTableModel(listCliente));

        principalForm.getClientesTable().getColumnModel().getColumn(0).setPreferredWidth(40);
        principalForm.getClientesTable().getColumnModel().getColumn(1).setPreferredWidth(400);
        principalForm.getClientesTable().getColumnModel().getColumn(2).setPreferredWidth(120);
        principalForm.getClientesTable().getColumnModel().getColumn(3).setPreferredWidth(280);
        principalForm.getClientesTable().getColumnModel().getColumn(4).setPreferredWidth(75);
        principalForm.getClientesTable().getColumnModel().getColumn(5).setPreferredWidth(75);
        principalForm.getClientesTable().getColumnModel().getColumn(6).setPreferredWidth(450);
        principalForm.getClientesTable().getColumnModel().getColumn(7).setPreferredWidth(220);
        principalForm.getClientesTable().getColumnModel().getColumn(8).setPreferredWidth(180);
        principalForm.getClientesTable().getColumnModel().getColumn(9).setPreferredWidth(200);
        principalForm.getClientesTable().getColumnModel().getColumn(10).setPreferredWidth(100);

        principalForm.getClientesTable().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    editCliente();
                } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                    deleteCliente();
                }

            }
        });

        principalForm.getClientesTable().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editCliente();
                }

            }
        });

        try {
            iconTree1 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/imgs/arrow_right_green_16x16.png")));
            iconTree2 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/imgs/free_16x16.png")));
            iconTree3 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/imgs/enterprise_16x16.png")));
            iconTree4 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/imgs/grid_16x16.png")));
            iconTree5 = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/imgs/box_16x16.png")));
        } catch (Exception ex) {
            logger.error("", ex);
        }

    }
    static Icon iconTree1;
    static Icon iconTree2;
    static Icon iconTree3;
    static Icon iconTree4;
    static Icon iconTree5;

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

    public void setMarcaTreeModelBuilder(MarcaTreeModelBuilder marcaTreeModelBuilder) {
        this.marcaTreeModelBuilder = marcaTreeModelBuilder;
    }

    /**
     * @param vpmMarcaDeLinea the vpmMarcaDeLinea to set
     */
    public void setVpmMarcaDeLinea(VisorDeProductosDefaultModel vpmMarcaDeLinea) {
        this.vpmMarcaDeLinea = vpmMarcaDeLinea;
    }

    /**
     * @param vpmMarcaDeEmpresa the vpmMarcaDeEmpresa to set
     */
    public void setVpmMarcaDeEmpresa(VisorDeProductosDefaultModel vpmMarcaDeEmpresa) {
        this.vpmMarcaDeEmpresa = vpmMarcaDeEmpresa;
    }

    /**
     * @param edicionClientesControl the edicionClientesControl to set
     */
    public void setEdicionClientesControl(EdicionClientesControl edicionClientesControl) {
        this.edicionClientesControl = edicionClientesControl;
    }

    class MyMarcaTreeNodeRenderer extends DefaultTreeCellRenderer {

        public MyMarcaTreeNodeRenderer() {
        }

        public Component getTreeCellRendererComponent(
                JTree tree,
                Object value,
                boolean sel,
                boolean expanded,
                boolean leaf,
                int row,
                boolean hasFocus) {

            super.getTreeCellRendererComponent(
                    tree, value, sel,
                    expanded, leaf, row,
                    hasFocus);
            if (!leaf && isRootNode(value)) {
                setIcon(iconTree1);
                setToolTipText("-");
            } else if (isLineaNode(value)) {
                setIcon(iconTree2);
                setToolTipText("Linea");
            } else if (isEmpresaNode(value)) {
                setIcon(iconTree3);
                setToolTipText("Empresa");
            } else if (isMarcaNode(value)) {
                setIcon(iconTree4);
                setToolTipText("Marca");
            } else {
                setIcon(iconTree5);
                setToolTipText("?");
            }

            return this;
        }

        protected boolean isRootNode(Object value) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            return (node.getUserObject() instanceof String);
        }

        protected boolean isMarcaNode(Object value) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            return (node.getUserObject() instanceof MarcaTreeNode);
        }

        protected boolean isLineaNode(Object value) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            return (node.getUserObject() instanceof LineaTreeNode);
        }

        protected boolean isEmpresaNode(Object value) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            return (node.getUserObject() instanceof EmpresaTreeNode);
        }
    }

    class DiscountDVPCellEditor extends AbstractCellEditor
            implements TableCellEditor {

        final JSpinner spinner = new JSpinner();

        // Initializes the spinner.
        public DiscountDVPCellEditor() {
            spinner.setModel(new SpinnerNumberModel(0, 0, 5, 1));
        }

        // Prepares the spinner component and returns it.
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {

            double maxDesc = ((ArrayList<PedidoVentaDetalle>) getApplicationLogic().
                    getSession().getPedidoVenta().getPedidoVentaDetalleCollection()).get(row).
                    getProducto().getFactorMaxDesc();
            ((SpinnerNumberModel) spinner.getModel()).setMaximum(new Integer((int) Math.round(maxDesc * 100.0)));
            logger.debug("->:getTableCellEditorComponent(" + row + "," + column + "): spinner.setValue(value=" + value + " class " + value.getClass() + "):  maxDesc=" + maxDesc);
            spinner.setValue(new Integer(value.toString()));
            new Thread() {

                public void run() {
                    logger.debug("->:getTableCellEditorComponent() shuld requesting fucking  foucs ?" + (!spinner.isFocusOwner()));
                    for (int ifo = 0; ifo < 10 && !spinner.isFocusOwner(); ifo++) {
                        logger.debug("->:getTableCellEditorComponent() requesting foucs [" + (ifo + 1) + "/10]");
                        spinner.requestFocus();
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            }.start();

            return spinner;
        }

        public boolean isCellEditable(EventObject evt) {
            logger.debug("-> DiscountDVPCellEditor: event " + evt.getClass() + " = " + evt);
            if (evt instanceof MouseEvent) {
                return ((MouseEvent) evt).getClickCount() >= 2;
            } else if (evt instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) evt;
                boolean canEdit = keyEvent.getKeyCode() == KeyEvent.VK_F2;
                logger.debug("-> DiscountDVPCellEditor: EDIT Cell Render ?" + canEdit);
                return canEdit;
            } else if (evt instanceof ActionEvent) {
                ActionEvent actionEvent = (ActionEvent) evt;

            }
            return false;
        }

        // Returns the spinners current value.
        public Object getCellEditorValue() {
            return spinner.getValue();
        }
    }

    void refreshClienteInfo() {
        List<Cliente> listCliente = null;

        try {
            listCliente = basicInfoDAO.getClientesList();
        } catch (Exception ex) {
            listCliente = new ArrayList<Cliente>();
            logger.error("", ex);
        }

        principalForm.getCliente().setModel(new ClienteComboBoxModel(listCliente));
        principalForm.getClientesTable().setModel(new ClientesTableModel(listCliente));
        principalForm.getClientesTable().updateUI();
        principalForm.getCliente().updateUI();

    }

    public void updateDetallePedidoTable() {
        int sizeDetail = applicationLogic.getSession().getPedidoVenta().getPedidoVentaDetalleCollection().size();
        principalForm.getReiniciarPedidoBtn().setEnabled(sizeDetail > 0);
        principalForm.getAvoidPedidoMenu().setEnabled(sizeDetail > 0);
        principalForm.getDetallePedidoTable().updateUI();
        if (sizeDetail > 0) {
            boolean esFiscal = principalForm.getEsFiscal().isSelected();
            double st = 0.0;
            double si = 0.0;
            double sd = 0.0;

            applicationLogic.getSession().getPedidoVenta().setEsFiscal(new Integer(esFiscal ? 1 : 0));

            for (PedidoVentaDetalle dvp : applicationLogic.getSession().getPedidoVenta().getPedidoVentaDetalleCollection()) {
                si += dvp.getCantidad() * dvp.getPrecioBase();
                sd +=
                        dvp.getDescuentoAplicado() * (dvp.getCantidad() * dvp.getPrecioBase());
                //logger.debug("\t->DetallePedido: DescuentoAplicado=" + dvp.getDescuentoAplicado() + ", FactorMaxDesc=" + dvp.getProducto().getFactorMaxDesc()+", sd="+sd);
            }

            double osi = 0.0;
            double osd = 0.0;
            double otax = 0.0;
            double ostt = 0.0;

            if (esFiscal) {
                osi = si;
                osd = sd;
                otax = (si - sd) * applicationLogic.getFactorIVA();
                ostt = (osi - osd) + otax;
            } else {
                osi = si * (1.0 + applicationLogic.getFactorIVA());
                osd = sd * (1.0 + applicationLogic.getFactorIVA());
                otax = 0.0;
                ostt = (osi - osd);
            }

            principalForm.getSubtotal().setText(ApplicationInfo.formatToCurrency(osi));
            principalForm.getTax().setText(ApplicationInfo.formatToCurrency(otax));
            principalForm.getDiscount().setText(ApplicationInfo.formatToCurrency(osd));
            principalForm.getTotal().setText(ApplicationInfo.formatToCurrency(ostt));
            principalForm.getLabel3().setText(ApplicationInfo.getLocalizedMessage("LABEL_ITEMS_IN_PEDIDOACTUAL") + sizeDetail
                    + "      " + ApplicationInfo.getLocalizedMessage("LBL_PEDIDO_TOTAL") + ApplicationInfo.formatToCurrency(ostt));
        } else {
            principalForm.getSubtotal().setText("");
            principalForm.getTax().setText("");
            principalForm.getDiscount().setText("");
            principalForm.getTotal().setText("");
            principalForm.getEsFiscal().setSelected(false);
            principalForm.getLabel3().setText(ApplicationInfo.getLocalizedMessage("LABEL_ITEMS_IN_PEDIDOACTUAL") + sizeDetail);
        }

    }

    void toFrontPanel(String nombre) {
        CardLayout cl = (CardLayout) (principalForm.getCardPanel().getLayout());

        logger.debug("->toFrontPanel: nombre=" + nombre + ", principalModel.getFrontPanel()=" + principalModel.getFrontPanel()
                + ", vpmMarcaDeLinea.getProductoList()=" + vpmMarcaDeLinea.getProductoList()
                + ", vpmMarcaDeEmpresa.getProductoList()=" + vpmMarcaDeEmpresa.getProductoList());
        if (nombre.equals(principalModel.getFrontPanel())) {
            return;
        }

        if (nombre.equals("visorDeMarcaPorLinea")
                && vpmMarcaDeLinea.getProductoList() != null
                && vpmMarcaDeLinea.getProductoList().size() > 0) {
            principalForm.getAddProductoMenu().setEnabled(true);
            principalForm.getAddCajaMenu().setEnabled(true);
        } else if (nombre.equals("visorDeMarcaPorEmpresa")
                && vpmMarcaDeEmpresa.getProductoList() != null
                && vpmMarcaDeEmpresa.getProductoList().size() > 0) {
            principalForm.getAddProductoMenu().setEnabled(true);
            principalForm.getAddCajaMenu().setEnabled(true);
        } else {
            //principalForm.getAddProductoMenu().setEnabled(false);
            //principalForm.getAddCajaMenu().setEnabled(false);
        }
        principalModel.setFrontPanel(nombre);
        cl.show(principalForm.getCardPanel(), nombre);
    }

    void updateImageForDisplayInPanel(int index, VisorDeProductosDefaultModel dpdm, VisorDeProductosDefaultPanel vpdp) {
        BufferedImage bi = null;
        Producto prod = null;
        if (index != -1) {
            prod = dpdm.getSelected(index);
            bi = dpdm.getImageForDisplay(index);
            vpdp.setImageForPanelDisplayer(index);
        }

        vpdp.getVisorDeAtributos().updateLabels(prod);
    }
    List<Producto> listProductosSinSurtido;

    void updateProductosLineaMarca(Marca marcaInNode) {
        principalForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        System.gc();
        List<Producto> listProductos = null;

        if (marcaInNode == null) {
            listProductos = new ArrayList<Producto>();
            principalForm.getAddProductoMenu().setEnabled(false);
            principalForm.getAddCajaMenu().setEnabled(false);
            vpmMarcaDeLinea.setProductoList(listProductos);
            prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().setModel(vpmMarcaDeLinea.getProductoTableModel());
            prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getColumnModel().
                    getColumn(0).setCellRenderer(new ImageCellRender());
            prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().setRowHeight(VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE + 2);
            prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getColumnModel().
                    getColumn(0).setPreferredWidth(VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE);
            prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getColumnModel().
                    getColumn(1).setPreferredWidth(VisorDeProductosDefaultPanel.TABLE_PREFSIZE_WIDTH - VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE);

            prodsPorMarcaDeLineaPnl.getInfo().setText(
                    ApplicationInfo.getLocalizedMessage("CAT_PRODUCTOS_BY_LINEA")
                    + " "
                    + ApplicationInfo.getLocalizedMessage("CAT_PRODUCTOS_BY_MARCA"));
            prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().updateUI();
        } else {
            Marca marcaActual = applicationLogic.getSession().getMarcaPorLinea();
            if (marcaActual == null || !marcaActual.getId().equals(marcaInNode.getId())) {
                applicationLogic.getSession().setMarcaPorLinea(marcaInNode);
                listProductos =
                        basicInfoDAO.getProductoByMarca(marcaInNode.getId());
                listProductosSinSurtido = new ArrayList<Producto>();
                for (Producto p : listProductos) {
                    logger.debug("===>>>>" + p.getNombre() + "(" + p.getPresentacion() + ")");
                    if (!p.getPresentacion().toLowerCase().equals("surtido")) {
                        logger.debug("\t===>>>>OK ADD to show list");
                        listProductosSinSurtido.add(p);
                    } else {
                        logger.debug("\t===>>>> soory it can't be added!");
                    }
                }
                if (listProductosSinSurtido.size() > 0) {
                    principalForm.getAddProductoMenu().setEnabled(true);
                    principalForm.getAddCajaMenu().setEnabled(true);
                    vpmMarcaDeLinea.setProductoList(listProductosSinSurtido);
                } else {
                    principalForm.getAddProductoMenu().setEnabled(false);
                    principalForm.getAddCajaMenu().setEnabled(false);
                    vpmMarcaDeLinea.setProductoList(new ArrayList<Producto>());
                }

                prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().setModel(vpmMarcaDeLinea.getProductoTableModel());

                prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getColumnModel().
                        getColumn(0).setCellRenderer(new ImageCellRender());
                prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().setRowHeight(VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE + 2);
                prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getColumnModel().
                        getColumn(0).setPreferredWidth(VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE);
                prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getColumnModel().
                        getColumn(1).setPreferredWidth(VisorDeProductosDefaultPanel.TABLE_PREFSIZE_WIDTH - VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE);

                prodsPorMarcaDeLineaPnl.getInfo().setText(
                        ApplicationInfo.getLocalizedMessage("CAT_PRODUCTOS_BY_LINEA") + marcaInNode.getLinea().getNombre().toUpperCase() + " "
                        + ApplicationInfo.getLocalizedMessage("CAT_PRODUCTOS_BY_MARCA") + marcaInNode.getNombre().toUpperCase());
                if (prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getRowCount() > 0) {
                    prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().setRowSelectionInterval(0, 0);
                }
            }

            prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().updateUI();
            prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().requestFocus();
        }

        prodsPorMarcaDeLineaPnl.setAllImages(vpmMarcaDeLinea.getAllImageForDisplay());
        System.gc();
        principalForm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    void updateProductosEmpresaMarca(Marca marcaInNode) {
        principalForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        System.gc();
        List<Producto> listProductos = null;

        if (marcaInNode == null) {
            listProductos = new ArrayList<Producto>();
            principalForm.getAddProductoMenu().setEnabled(false);
            principalForm.getAddCajaMenu().setEnabled(false);
            vpmMarcaDeEmpresa.setProductoList(listProductos);
            prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().setModel(vpmMarcaDeEmpresa.getProductoTableModel());
            prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getColumnModel().
                    getColumn(0).setCellRenderer(new ImageCellRender());
            prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().setRowHeight(VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE + 2);
            prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getColumnModel().
                    getColumn(0).setPreferredWidth(VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE);
            prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getColumnModel().
                    getColumn(1).setPreferredWidth(VisorDeProductosDefaultPanel.TABLE_PREFSIZE_WIDTH - VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE);

            prodsPorMarcaDeEmpresaPnl.getInfo().setText(
                    ApplicationInfo.getLocalizedMessage("CAT_PRODUCTOS_BY_EMPRESA")
                    + " "
                    + ApplicationInfo.getLocalizedMessage("CAT_PRODUCTOS_BY_MARCA"));
            prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().updateUI();

        } else {
            Marca marcaActual = applicationLogic.getSession().getMarcaPorEmpresa();
            if (marcaActual == null || !marcaActual.getId().equals(marcaInNode.getId())) {
                applicationLogic.getSession().setMarcaPorEmpresa(marcaInNode);
                listProductos =
                        basicInfoDAO.getProductoByMarca(marcaInNode.getId());
                listProductosSinSurtido = new ArrayList<Producto>();
                for (Producto p : listProductos) {
                    logger.debug("===>>>>" + p.getNombre() + "(" + p.getPresentacion() + ")");
                    if (!p.getPresentacion().toLowerCase().equals("surtido")) {
                        logger.debug("\t===>>>>OK ADD to show list");
                        listProductosSinSurtido.add(p);
                    } else {
                        logger.debug("\t===>>>> soory it can't be added!");
                    }
                }
                if (listProductosSinSurtido.size() > 0) {

                    principalForm.getAddProductoMenu().setEnabled(true);
                    principalForm.getAddCajaMenu().setEnabled(true);
                    vpmMarcaDeEmpresa.setProductoList(listProductosSinSurtido);
                } else {
                    principalForm.getAddProductoMenu().setEnabled(false);
                    principalForm.getAddCajaMenu().setEnabled(false);
                    vpmMarcaDeEmpresa.setProductoList(new ArrayList<Producto>());
                }


                prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().setModel(vpmMarcaDeEmpresa.getProductoTableModel());
                prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getColumnModel().
                        getColumn(0).setCellRenderer(new ImageCellRender());
                prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().setRowHeight(VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE + 2);
                prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getColumnModel().
                        getColumn(0).setPreferredWidth(VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE);
                prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getColumnModel().
                        getColumn(1).setPreferredWidth(VisorDeProductosDefaultPanel.TABLE_PREFSIZE_WIDTH - VisorDeProductosDefaultModel.DEFAULT_MIN_IMAGESIZE);

                prodsPorMarcaDeEmpresaPnl.getInfo().setText(
                        ApplicationInfo.getLocalizedMessage("CAT_PRODUCTOS_BY_EMPRESA") + marcaInNode.getEmpresa().getNombre().toUpperCase() + " "
                        + ApplicationInfo.getLocalizedMessage("CAT_PRODUCTOS_BY_MARCA") + marcaInNode.getNombre().toUpperCase());
                prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().updateUI();
                prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().requestFocus();
                if (prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getRowCount() > 0) {
                    prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().setRowSelectionInterval(0, 0);
                }
            }

            prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().updateUI();
            prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().requestFocus();
        }

        prodsPorMarcaDeEmpresaPnl.setAllImages(vpmMarcaDeEmpresa.getAllImageForDisplay());
        System.gc();
        principalForm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    void verCatLinea() {
        toFrontPanel("visorDeMarcaPorLinea");
        vmpLinea.getMarcasJTree().requestFocus();
    }

    void verCatEmpresa() {
        toFrontPanel("visorDeMarcaPorEmpresa");
        vmpEmpresa.getMarcasJTree().requestFocus();
    }

    void verDetalle() {
        toFrontPanel("visorDePedidoActual");
        principalForm.getNombreBuscar().requestFocus();
    }

    void verPedidos() {
        toFrontPanel("visorDePedidosGuardados");
        principalForm.getDetallePedidoTable().requestFocus();
    }

    void verClientes() {
        toFrontPanel("visorDeClientes");
        principalForm.getClientesTable().requestFocus();
    }

    void agregarProducto() {
        logger.debug("->agregarProducto");
        Producto prod = null;
        if (principalModel.getFrontPanel().equals("visorDeMarcaPorLinea")
                && prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getSelectedRow() != -1) {
            logger.debug("\t->agregarProducto: visorDeMarcaPorLinea");
            prod =
                    vpmMarcaDeLinea.getSelected(prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getSelectedRow());
        } else if (principalModel.getFrontPanel().equals("visorDeMarcaPorEmpresa")
                && prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getSelectedRow() != -1) {
            logger.debug("\t->agregarProducto: visorDeMarcaPorEmpresa");
            prod =
                    vpmMarcaDeEmpresa.getSelected(prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getSelectedRow());
        } else {
            throw new IllegalStateException("Panel for Marca is not visible,but may be in detail");
        }

        logger.debug("\t\t->agregarProducto: prod=" + prod.getNombre());
        applicationLogic.addProductoToCurrentPedidoVenta(prod);
        updateDetallePedidoTable();

    }

    void agregarNProducto() {
        logger.debug("->agregarNProducto");
        int cantPedida = 0;
        Producto productoAgregar = null;
        try {
            productoAgregar = applicationLogic.getSession().getProductoBuscadoActual();
            cantPedida = Integer.parseInt(getPrincipalForm().getCantidadPedida().getText().trim());
            if (cantPedida > 0 && productoAgregar != null) {
                logger.debug("->add N");

                if (productoAgregar.getPresentacion().toLowerCase().equals("surtido")) {
                    applicationLogic.addProductoSurtidoNToCurrentPedidoVenta(productoAgregar, cantPedida);
                } else {
                    applicationLogic.addProductoNToCurrentPedidoVenta(productoAgregar, cantPedida);
                }
                updateDetallePedidoTable();
                getPrincipalForm().getNombreBuscar().setText("");
                getPrincipalForm().getNombreBuscar().requestFocus();
                VisorDeAtributosProductosPanel2 atribProd =
                        (VisorDeAtributosProductosPanel2) getPrincipalForm().getAtributosProductoSeleccionadoPanel();
                atribProd.updateLabels(null);
                atribProd.updateUI();
            } else {
                throw new Exception();
            }
        } catch (NumberFormatException nfe) {

            if (getPrincipalForm().getCantidadPedida().getText().trim().matches("[0-9]+[cC]")) {
                agregarDirectNCajas();
            } else {
                getPrincipalForm().getCantidadPedida().setText("");
            }
        } catch (Exception e) {
        }
    }

    void agregarDirectNCajas() {
        logger.debug("->agregarDirectNCajas !");
        int cantPedida = 0;
        Producto productoAgregar = null;
        try {
            productoAgregar = applicationLogic.getSession().getProductoBuscadoActual();
            cantPedida = Integer.parseInt(getPrincipalForm().getCantidadPedida().getText().trim().substring(0, getPrincipalForm().getCantidadPedida().getText().trim().length() - 1))
                    * productoAgregar.getUnidadesPorCaja();
            if (cantPedida > 0 && productoAgregar != null) {
                logger.debug("->add N cajas");

                if (productoAgregar.getPresentacion().toLowerCase().equals("surtido")) {
                    applicationLogic.addProductoSurtidoNToCurrentPedidoVenta(productoAgregar, cantPedida);
                } else {
                    applicationLogic.addProductoNToCurrentPedidoVenta(productoAgregar, cantPedida);
                }
                updateDetallePedidoTable();
                getPrincipalForm().getNombreBuscar().setText("");
                getPrincipalForm().getNombreBuscar().requestFocus();
                VisorDeAtributosProductosPanel2 atribProd =
                        (VisorDeAtributosProductosPanel2) getPrincipalForm().getAtributosProductoSeleccionadoPanel();
                atribProd.updateLabels(null);
                atribProd.updateUI();
            } else {
                throw new Exception();
            }
        } catch (NumberFormatException nfe) {
            getPrincipalForm().getCantidadPedida().setText("");
        } catch (Exception e) {
        }
    }

    void agregarCaja() {
        logger.debug("->agregarCaja");
        Producto prod = null;
        if (principalModel.getFrontPanel().equals("visorDeMarcaPorLinea")
                && prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getSelectedRow() != -1) {
            logger.debug("\t->agregarCaja: visorDeMarcaPorLinea");
            prod =
                    vpmMarcaDeLinea.getSelected(prodsPorMarcaDeLineaPnl.getProductosDeMarcaTable().getSelectedRow());
        } else if (principalModel.getFrontPanel().equals("visorDeMarcaPorEmpresa")
                && prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getSelectedRow() != -1) {
            logger.debug("\t->agregarCaja: visorDeMarcaPorEmpresa");
            prod =
                    vpmMarcaDeEmpresa.getSelected(prodsPorMarcaDeEmpresaPnl.getProductosDeMarcaTable().getSelectedRow());
        } else {
            throw new IllegalStateException("Panel for Marca|Empresa is not visible");
        }

        logger.debug("\t\t->agregarCaja: prod=" + prod.getNombre());
        applicationLogic.addProductoCajaToCurrentPedidoVenta(prod);
        updateDetallePedidoTable();

    }

    void verifyAllSelections() {
        int sizeDetail = applicationLogic.getSession().
                getPedidoVenta().getPedidoVentaDetalleCollection().size();
        logger.debug("->verifyAllSelections(): sizeDetail=" + sizeDetail);
        Cliente clienteSelected = null;
        FormaDePago formaDePagoSelected = null;

        if ((ClienteItemList) principalForm.getCliente().getSelectedItem() != null) {
            clienteSelected = ((ClienteItemList) principalForm.getCliente().getSelectedItem()).getCliente();
            if (clienteSelected.getId() == null) {
                clienteSelected = null;
            }
        } else {
            clienteSelected = null;
        }

        if (((FormaDePagoItemList) principalForm.getFormaDePago().getSelectedItem()) != null) {
            formaDePagoSelected = ((FormaDePagoItemList) principalForm.getFormaDePago().getSelectedItem()).getFormaDePago();
            if (formaDePagoSelected == null) {
                formaDePagoSelected = null;
            }
        } else {
            formaDePagoSelected = null;
        }

        if (sizeDetail > 0
                && principalForm.getCliente().getSelectedIndex() != 0
                && principalForm.getFormaDePago().getSelectedIndex() != 0) {
            principalForm.getProcederPedidoBtn().setEnabled(true);
        } else {
            principalForm.getProcederPedidoBtn().setEnabled(false);
        }

        applicationLogic.setClienteToCurrentPedidoVenta(clienteSelected);
        applicationLogic.setFormaDePagoToCurrentPedidoVenta(formaDePagoSelected);
    }

    void procederPedido() {
        logger.debug("->procederPedido(): confirm ?");

        int confirm = JOptionPane.showConfirmDialog(principalForm, ApplicationInfo.getLocalizedMessage("LABEL_PROCED_CONFIRMATION"),
                ApplicationInfo.getLocalizedMessage("LABEL_PROCED"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.NO_OPTION) {
            return;
        }

        try {
            applicationLogic.persistCurrentPedidoVenta();

            String pseudoNumeroPedido = ApplicationInfo.getNumPseudoPedido(
                    applicationLogic.getSession().getPedidoVenta());

            JOptionPane.showMessageDialog(principalForm,
                    ApplicationInfo.getLocalizedMessage("LABEL_PEDIDO_SAVED") + pseudoNumeroPedido,
                    ApplicationInfo.getLocalizedMessage("LABEL_PROCED"),
                    JOptionPane.INFORMATION_MESSAGE);


            Icon printIcon = null;
            try {
                printIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/imgs/Zebra_MZ_320.jpg")));
            } catch (IOException ex) {
                logger.error("", ex);
            }

            int confirmPrint = JOptionPane.showConfirmDialog(principalForm,
                    ApplicationInfo.getLocalizedMessage("LABEL_PRINT_CONFIRMATION"),
                    ApplicationInfo.getLocalizedMessage("LABEL_PRINT_TICKET"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    printIcon);

            try {
                applicationLogic.printTicketPedido(
                        confirmPrint == JOptionPane.YES_OPTION);
            } catch (BusinessException ex) {
                JOptionPane.showMessageDialog(principalForm, ex.getMessage(),
                        ApplicationInfo.getLocalizedMessage("LABEL_PROCED"),
                        JOptionPane.ERROR_MESSAGE);
            }
            logger.debug("->procederPedido(): ok estado inicial");
            estadoInicial();
        } catch (BusinessException ex) {
            JOptionPane.showMessageDialog(principalForm, ex.getMessage(),
                    ApplicationInfo.getLocalizedMessage("LABEL_PROCED"),
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    void refreshPedidosGuardados() {
        List<PedidoVenta> listPedidoVenta = null;
        try {
            listPedidoVenta = basicInfoDAO.getPedidoVentaList();
        } catch (Exception ex) {
            listPedidoVenta = new ArrayList<PedidoVenta>();
            logger.error("", ex);
        }

        if (listPedidoVenta.size() == 0) {
            principalForm.getEnviarPedidosBtn().setEnabled(false);
        } else {
            principalForm.getEnviarPedidosBtn().setEnabled(true);
        }
        logger.debug("->refreshPedidosGuardados: listPedidoVenta.size=" + listPedidoVenta.size());
        if (listPedidoVenta != null) {
            logger.debug("=>feching all objets to use: ->listPedidoVenta.size=" + listPedidoVenta.size());
            for (PedidoVenta pv : listPedidoVenta) {
                logger.debug("\t feching all objets for PedidoVenta to use: ->pv: pv.getPedidoVentaEstadoCollection().size()=" + pv.getPedidoVentaEstadoCollection().size());
            }
        }
        principalForm.getPedidosGuardadosTable().setModel(
                new PedidosGuardadosTableModel(listPedidoVenta));
    }

    void testPrinter() {
        try {
            applicationLogic.testPrinter();
        } catch (BusinessException ex) {
            JOptionPane.showMessageDialog(principalForm, ex.getMessage(),
                    ApplicationInfo.getLocalizedMessage("APP_MENU_TEST_PRINT"),
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    void reiniciarPedido() {
        logger.debug("->reiniciarPedido()");
        int confirm = JOptionPane.showConfirmDialog(principalForm, ApplicationInfo.getLocalizedMessage("LABEL_RESET"),
                ApplicationInfo.getLocalizedMessage("RESET_PEDIDO_CONFIRMATION"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            estadoInicial();
        }

    }

    void eliminarProductoDePedido() {
        logger.debug("->eliminarProductoDePedido()");
        if (principalForm.getDetallePedidoTable().getSelectedRowCount() > 0) {
            int prodToDelete = principalForm.getDetallePedidoTable().getSelectedRow();
            applicationLogic.deleteProductoFromCurrentPedidoVenta(prodToDelete);
            updateDetallePedidoTable();

        }


    }

    void createCliente() {
        logger.debug("->createCliente()");
        edicionClientesControl.crearCliente();
        if (edicionClientesControl.getExitStatus() == JOptionPane.YES_OPTION) {
            refreshClienteInfo();
        }

    }

    Cliente getSelectedCliente() {
		int cteSel = principalForm.getClientesTable().getSelectedRow();
		Cliente cs = null;
		if (cteSel != -1) {
			cs = ((ClientesTableModel)principalForm.getClientesTable().getModel()).getAt(cteSel);
        }
        return cs;				 
    }

    void editCliente() {
        Cliente c = getSelectedCliente();
        logger.debug("->editCliente():" + c);
		logger.debug("\t->editCliente(): problemas con CuentaBancariaCollection ??");
		c.getCuentaBancariaCollection();
        edicionClientesControl.editarCliente(c);
        if (edicionClientesControl.getExitStatus() == JOptionPane.YES_OPTION) {
            refreshClienteInfo();
        }

    }

    void deleteCliente() {
        Cliente c = getSelectedCliente();
        logger.debug("->deleteCliente():" + c);

        try {
            int confirm = JOptionPane.showConfirmDialog(principalForm,
                    ApplicationInfo.getLocalizedMessage("DLG_DELETE_CLIENTE_CONFIRMATION"),
                    ApplicationInfo.getLocalizedMessage("DLG_DELETE_CLIENTE_MSG_TITLE"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                applicationLogic.removeCliente(c);
            }

            refreshClienteInfo();
        } catch (BusinessException ex) {
            JOptionPane.showMessageDialog(principalForm,
                    ex.getMessage(),
                    ex.getTitle(),
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            logger.error("", ex);
            JOptionPane.showMessageDialog(principalForm,
                    BusinessException.getLocalizedMessage("APP_LOGIC_CLIENTE_NOT_DELETED"),
                    ApplicationInfo.getLocalizedMessage("DLG_DELETE_CLIENTE_MSG_TITLE"),
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    void exitByClick() {
        logger.debug("->exitByClick()");
        try {
            applicationLogic.exit();
            principalForm.setVisible(false);
            principalForm.dispose();
            logger.debug("->exitByClick(): OK normal exit!");
            System.exit(0);
        } catch (BusinessException ex) {
            int confirm = JOptionPane.showConfirmDialog(principalForm,
                    ex.getMessage() + ApplicationInfo.getLocalizedMessage("EXIT_CONFIRMATION"),
                    ApplicationInfo.getLocalizedMessage("APP_MENU_EXIT"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                principalForm.setVisible(false);
                principalForm.dispose();
                System.exit(0);
            }

        }
    }

    void exit() {
        logger.debug("->exit()");
        try {
            applicationLogic.exit();
            principalForm.setVisible(false);
            principalForm.dispose();
            logger.debug("->exit(): OK normal exit!");
            System.exit(0);

        } catch (BusinessException ex) {
            int confirm = JOptionPane.showConfirmDialog(principalForm,
                    ex.getMessage() + ApplicationInfo.getLocalizedMessage("EXIT_CONFIRMATION"),
                    ApplicationInfo.getLocalizedMessage("APP_MENU_EXIT"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                principalForm.setVisible(false);
                principalForm.dispose();
                System.exit(0);
            }

        }
    }

    void enviarPedidos() {
        logger.debug("->enviarPedidos()");

        if (applicationLogic.getSession().getPedidoVenta().getPedidoVentaDetalleCollection().size() > 0) {
            JOptionPane.showMessageDialog(principalForm,
                    ApplicationInfo.getLocalizedMessage("SAVE_BEFORE_SEND"),
                    ApplicationInfo.getLocalizedMessage("SEND_ALL_PEDIDOS"),
                    JOptionPane.QUESTION_MESSAGE);
        }

        try {
            applicationLogic.sendPedidosAndDelete(new ProgressProcessListener() {

                public void updateProgress(int prog, String msg) {
                    getPrincipalForm().getLabel2().setText("Envio:" + prog + " %");
                }

                public int getProgress() {
                    return 0;
                }
            });
            refreshPedidosGuardados();

            JOptionPane.showMessageDialog(principalForm, ApplicationInfo.getLocalizedMessage("SEND_ALL_PEDIDOS_OK"),
                    ApplicationInfo.getLocalizedMessage("SEND_ALL_PEDIDOS"),
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(principalForm, ex.getMessage(),
                    ApplicationInfo.getLocalizedMessage("SEND_ALL_PEDIDOS"),
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            principalForm.getLabel2().setText("");
        }
    }

    public void estadoInicial() {
        logger.debug("\t->Estado Inicial");
        applicationLogic.startNewPedidoVentaSession();
        refreshPedidosGuardados();

        principalForm.getLabel1().setText(ApplicationInfo.getLocalizedMessage("USUARIOACTUAL")
                + applicationLogic.getSession().getUsuario().getNombreCompleto());
        //principalForm.setExtendedState(principalForm.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        ////principalForm.getDeleteItemPedidoMenu().setEnabled(false);
        principalForm.getCliente().setSelectedIndex(0);
        principalForm.getFormaDePago().setSelectedIndex(0);

        vmpEmpresa.getMarcasJTree().setCellRenderer(new MyMarcaTreeNodeRenderer());
        vmpLinea.getMarcasJTree().setCellRenderer(new MyMarcaTreeNodeRenderer());
        vmpEmpresa.getMarcasJTree().putClientProperty("JTree.lineStyle", "Angled");
        vmpLinea.getMarcasJTree().putClientProperty("JTree.lineStyle", "Angled");


        vmpEmpresa.getMarcasJTree().setModel(
                marcaTreeModelBuilder.createModelForEmpresa());
        vmpLinea.getMarcasJTree().setModel(
                marcaTreeModelBuilder.createModelForLineas());

        verifyAllSelections();

        PedidoVentaDetalleTableModel pedidoVentaDetalleTableModel = new PedidoVentaDetalleTableModel(
                applicationLogic.getSession().getPedidoVenta().getPedidoVentaDetalleCollection());

        pedidoVentaDetalleTableModel.setPrincipalControl(this);
        pedidoVentaDetalleTableModel.setApplicationLogic(applicationLogic);
        //======================================================================
        principalForm.getDetallePedidoTable().setModel(pedidoVentaDetalleTableModel);
        final PedidoVentaDetalle pedidoVentaDetalleZero = new PedidoVentaDetalle();
        pedidoVentaDetalleZero.setCantidad(0);
        pedidoVentaDetalleZero.setDescuentoAplicado(1.0);
        pedidoVentaDetalleZero.setPrecioBase(0.0);
        pedidoVentaDetalleZero.setProducto(new Producto(0));

        applicationLogic.getSession().getPedidoVenta().getPedidoVentaDetalleCollection().add(pedidoVentaDetalleZero);

        TableColumn column = null;
        TableColumnModel columnModel = principalForm.getDetallePedidoTable().getColumnModel();
        int tableWidth = principalForm.getDetallePedidoTable().getWidth();
        for (int i = 0; i < 5; i++) {            
            column = columnModel.getColumn(i);
            if (i == 2) {
                column.setPreferredWidth((tableWidth*30)/100); //third column is bigger
            } else {
                column.setPreferredWidth((tableWidth*10)/100);
            }
        }
        principalForm.getDetallePedidoTable().getColumnModel().getColumn(4).
                setCellEditor(new DiscountDVPCellEditor());

        principalForm.getDetallePedidoTable().updateUI();

        applicationLogic.getSession().getPedidoVenta().getPedidoVentaDetalleCollection().clear();

        principalForm.getDetallePedidoTable().updateUI();;
        //======================================================================
        updateProductosLineaMarca(null);
        updateProductosEmpresaMarca(null);

        toFrontPanel("defaultPanel");
        if (!principalForm.isVisible()) {
            principalForm.setVisible(true);
        }

    }

    void updateProductoSelected(ProductoFastDisplayModel selDisplayProducto) {
        logger.debug("\t->itemStateChanged:" + selDisplayProducto + ", n Prod. surtidos=" + (listProductosSinSurtido != null ? listProductosSinSurtido.size() : null));

        if (selDisplayProducto != null) {
            applicationLogic.getSession().setProductoBuscadoActual(selDisplayProducto.getProducto());

            getPrincipalForm().getProductoCBEncontrados().setEnabled(true);
            VisorDeAtributosProductosPanel2 atribProd =
                    (VisorDeAtributosProductosPanel2) getPrincipalForm().getAtributosProductoSeleccionadoPanel();
            atribProd.updateLabels(selDisplayProducto.getProducto());
            atribProd.updateUI();
            getPrincipalForm().getCantidadPedida().setText("1");
            getPrincipalForm().getCantidadPedida().setSelectionStart(0);
            getPrincipalForm().getCantidadPedida().setSelectionEnd(1);
            getPrincipalForm().getCantidadPedida().setEnabled(true);
        } else {
            applicationLogic.getSession().setProductoBuscadoActual(null);
            getPrincipalForm().getProductoCBEncontrados().setEnabled(false);
            getPrincipalForm().getCantidadPedida().setText("");
            getPrincipalForm().getCantidadPedida().setEnabled(false);
        }

    }

    void updateProductosFound() {
        logger.debug("->updateProductosFound(): currentTextToSearch=" + currentTextToSearch);
        getPrincipalForm().getProductoCBEncontrados().setModel(new DefaultComboBoxModel(
                applicationLogic.getProducto4Display(currentTextToSearch)));
        getPrincipalForm().getProductoCBEncontrados().updateUI();
        if (getPrincipalForm().getProductoCBEncontrados().getModel().getSize() > 0) {
            updateProductoSelected((ProductoFastDisplayModel) getPrincipalForm().
                    getProductoCBEncontrados().getModel().getElementAt(0));
            getPrincipalForm().getProductoCBEncontrados().setPopupVisible(true);
        } else {
            updateProductoSelected(null);
            getPrincipalForm().getProductoCBEncontrados().setPopupVisible(false);
        }

    }

    /**
     * @return the principalForm
     */
    public PrincipalForm getPrincipalForm() {
        return principalForm;
    }
}
