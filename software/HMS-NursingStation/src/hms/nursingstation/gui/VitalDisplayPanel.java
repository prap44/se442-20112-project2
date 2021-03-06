/* $Id$
 * 
 * VitalDisplay.java
 *
 * Created on Feb 17, 2012, 3:12:30 PM
 */
package hms.nursingstation.gui;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class VitalDisplayPanel extends javax.swing.JPanel {
    
    public VitalDisplayPanel(String name, String value) {
        this();
        this.setVitalName(name);
        this.setVitalValue(value);
    }
    
    /** Creates new form VitalDisplay */
    public VitalDisplayPanel() {
        initComponents();
    }
    
    public final void setVitalName(String name) {
        this.vitalNameLabel.setText(name);
    }
    
    public final String getVitalName() {
        return this.vitalNameLabel.getText();
    }
    
    public final void setVitalValue(String value) {
        this.vitalValueField.setText(value);
    }
    
    public final String getVitalValue() {
        return this.vitalValueField.getText();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        basePanel = new javax.swing.JPanel();
        vitalNameLabel = new javax.swing.JLabel();
        vitalValueField = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMinimumSize(new java.awt.Dimension(140, 28));
        setPreferredSize(new java.awt.Dimension(140, 28));
        setLayout(new java.awt.GridLayout(1, 0));

        basePanel.setMinimumSize(new java.awt.Dimension(140, 32));
        basePanel.setPreferredSize(new java.awt.Dimension(140, 32));
        basePanel.setLayout(new java.awt.GridBagLayout());

        vitalNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        vitalNameLabel.setText("Vital Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        basePanel.add(vitalNameLabel, gridBagConstraints);

        vitalValueField.setEditable(false);
        vitalValueField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        vitalValueField.setText("9999.9");
        vitalValueField.setMinimumSize(new java.awt.Dimension(46, 25));
        vitalValueField.setPreferredSize(new java.awt.Dimension(46, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        basePanel.add(vitalValueField, gridBagConstraints);

        add(basePanel);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private javax.swing.JLabel vitalNameLabel;
    private javax.swing.JTextField vitalValueField;
    // End of variables declaration//GEN-END:variables
}
