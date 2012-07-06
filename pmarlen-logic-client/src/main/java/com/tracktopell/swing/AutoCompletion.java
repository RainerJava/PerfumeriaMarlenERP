package com.tracktopell.swing;

import java.awt.Color;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.text.*;

public class AutoCompletion extends PlainDocument {
    JComboBox comboBox;
    ComboBoxModel model;
    JTextComponent editor;
    Color    newBackColor;
    boolean selecting=false;
    boolean hidePopupOnFocusLoss;
    boolean hitBackspace=false;
    boolean hitBackspaceOnSelection;
    //int     eventComboBox;
    KeyListener editorKeyListener;
    FocusListener editorFocusListener;
    
    
    AutoCompletion(final JComboBox comboBox,final Color newBackColor) {
        this.comboBox    = comboBox;
        //this.eventComboBox = eCB;
        this.newBackColor  = newBackColor;
        model = comboBox.getModel();
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!selecting) highlightCompletedText(0);
            }
        });
        comboBox.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (e.getPropertyName().equals("editor")) 
                    configureEditor((ComboBoxEditor) e.getNewValue());
                if (e.getPropertyName().equals("model")) 
                    model = (ComboBoxModel) e.getNewValue();
            }
        });
        editorKeyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {                
                hitBackspace=false;                
                switch (e.getKeyCode()) {
                    // determine if the pressed key is backspace (needed by the remove method)
                    case KeyEvent.VK_BACK_SPACE :
                        hitBackspace=true;
                        hitBackspaceOnSelection=editor.getSelectionStart()!=editor.getSelectionEnd();
                        break;
                    
                    case KeyEvent.VK_ENTER:
                        e.consume();
                        if (comboBox.isDisplayable())
                            comboBox.setPopupVisible(false);                                                                        
                        break;
                    case KeyEvent.VK_TAB:
                        e.consume();       
                        if (comboBox.isDisplayable())
                            comboBox.setPopupVisible(false);                                                                        
                        break;    
                    // ignore delete key
                    case KeyEvent.VK_DELETE :
                        e.consume();
                        comboBox.getToolkit().beep();
                        break;
                        // ignore delete key
                    case KeyEvent.VK_ESCAPE :
                        System.out.println("-> ESCAPE COMBOBOX.");
                        comboBox.setSelectedIndex(0);
                        e.consume();
                        if (comboBox.isDisplayable())
                            comboBox.setPopupVisible(false);
                        break;
                    case KeyEvent.VK_ALT:
                        e.consume();                        
                        break;
                    case KeyEvent.VK_CONTROL:
                        e.consume();                        
                        break;
                    case KeyEvent.VK_SHIFT:
                        e.consume();                        
                        break;    
                    default:
                        if (comboBox.isDisplayable())
                            comboBox.setPopupVisible(true);
                }
            }
        };
        // Bug 5100422 on Java 1.5: Editable JComboBox won't hide popup when tabbing out
        hidePopupOnFocusLoss=System.getProperty("java.version").startsWith("1.5");
        // Highlight whole text when gaining focus
        editorFocusListener = new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                highlightCompletedText(0);                
            }
            public void focusLost(FocusEvent e) {
                // Workaround for Bug 5100422 - Hide Popup on focus loss
                if (hidePopupOnFocusLoss) 
                    comboBox.setPopupVisible(false);
                //if(secondIndex != null)
                //    secondIndex.fireSelected();
            }
        };
        configureEditor(comboBox.getEditor());
        // Handle initially selected object
        Object selected = comboBox.getSelectedItem();
        if (selected!=null) setText(selected.toString());
        highlightCompletedText(0);
    }
    
    public static void enable(JComboBox comboBox,Color newBackColor){
        // has to be editable
        comboBox.setEditable(true);
        // change the editor's document
        new AutoCompletion(comboBox,newBackColor);
    }
    
    void configureEditor(ComboBoxEditor newEditor) {
        if (editor != null) {
            editor.removeKeyListener(editorKeyListener);
            editor.removeFocusListener(editorFocusListener);
        }
        
        if (newEditor != null) {
            editor = (JTextComponent) newEditor.getEditorComponent();
            editor.addKeyListener(editorKeyListener);
            editor.addFocusListener(editorFocusListener);
            editor.setDocument(this);
            if(newBackColor!=null){
                editor.setBackground(newBackColor);
            }
        }
    }
    
    public void remove(int offs, int len) throws BadLocationException {
        // return immediately when selecting an item
        if (selecting) return;
        if (hitBackspace) {
            // user hit backspace => move the selection backwards
            // old item keeps being selected
            if (offs>0) {
                if (hitBackspaceOnSelection) 
                    offs--;
            } else {
                // User hit backspace with the cursor positioned on the start => beep
                comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
            }
            highlightCompletedText(offs);
        } else {
            super.remove(offs, len);
        }
    }
    
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        // return immediately when selecting an item
        if (selecting) return;
        // insert the string into the document
        super.insertString(offs, str, a);
        // lookup and select a matching item
        int objectFound = search(getText(0, getLength()));
        
        //System.out.println("->insertString("+offs+", "+str+", <a>):objectFound="+objectFound);
        
        Object item; 
        //Object item = lookupItem(getText(0, getLength()));
        if (objectFound != -1) {
            item = model.getElementAt(objectFound);
            setSelectedItem(objectFound);
        } else {
            // keep old item selected if there is no match
            item = comboBox.getSelectedItem();
            // imitate no insert (later on offs will be incremented by str.length(): selection won't move forward)
            offs = offs-str.length();
            // provide feedback to the user that his input has been received but can not be accepted
            comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
        }
        setText(item.toString());
        // select the completed part
        highlightCompletedText(offs+str.length());
    }
    
    private void setText(String text) {
        try {
            // remove all text and insert the completed string
            super.remove(0, getLength());
            super.insertString(0, text, null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e.toString());
        }
    }
    
    private void highlightCompletedText(int start) {
        editor.setCaretPosition(getLength());
        editor.moveCaretPosition(start);
    }
    
    private void setSelectedItem(int toSelect) {
        Object item;
        item = model.getElementAt(toSelect);
        selecting = true;
        model.setSelectedItem(item);
        selecting = false;        
    }
    
    private int search(String pattern) {
        Object selectedItem = model.getSelectedItem();
        
        if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern)) {
            return comboBox.getSelectedIndex();
        } else {
            
            // iterate over all items
            for (int i=0, n=model.getSize(); i < n; i++) {
                Object currentItem = model.getElementAt(i);
                // current item starts with the pattern?
                if (currentItem != null && startsWithIgnoreCase(currentItem.toString(), pattern)) {
                    return i;
                }
            }
        }
        // no item starts with the pattern => return null
        return -1;
    }
    
    
    // checks if str1 starts with str2 - ignores case
    private boolean startsWithIgnoreCase(String str1, String str2) {
        return str1.toUpperCase().startsWith(str2.toUpperCase());
    }
    
    public int binarySearch(String s){
        int mid;
        int low = 0;
        int hi  = this.model.getSize()-1;
        int cmp = 0;
        
        cmp = s.compareTo(this.model.getElementAt(0).toString());
        
        if(cmp < 0 && !this.model.getElementAt(0).toString().startsWith(s)){
            return -1;
        }
        cmp = s.compareTo(this.model.getElementAt(hi).toString());
        
        if(cmp > 0 && !this.model.getElementAt(hi).toString().startsWith(s)){        
            return -1;
        }
        
        while(low <= hi ){
            mid = (low +hi)/2; 
            cmp = s.compareTo(this.model.getElementAt(mid).toString());

            if(cmp == 0) 
                return mid; 
            else if(cmp < 0)
                hi = mid -1; 
            else
                low = mid +1; 
        }
        
        if(low!= -1 && !this.model.getElementAt(low).toString().startsWith(s)){            
            return -1;
        }
        return low;
    }

}
