/* $Id$
 * 
 * EditSensorDialog.java
 *
 * Created on Feb 18, 2012, 10:34:49 PM
 */
package hms.bedsidemonitor.gui;

import hms.common.Sensor;
import hms.common.gui.filters.NumericDocumentFilter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class EditSensorDialog extends javax.swing.JDialog {
    
    private boolean okPushed = false;
    
    /** Creates new form EditSensorDialog */
    public EditSensorDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        try {
            /* Use NumericDocumentFilters to force user to enter valid input for
             * sensor offset and scale fields */
            ((PlainDocument)this.sensorOffsetField.getDocument()).setDocumentFilter(
                    new NumericDocumentFilter(this.sensorOffsetField.getDocument(), true, true));
            ((PlainDocument)this.sensorScaleField.getDocument()).setDocumentFilter(
                    new NumericDocumentFilter(this.sensorScaleField.getDocument(), true, true));
            ((PlainDocument)this.sensorHighLimitField.getDocument()).setDocumentFilter(
                    new NumericDocumentFilter(this.sensorScaleField.getDocument(), false, true));
            ((PlainDocument)this.sensorLowLimitField.getDocument()).setDocumentFilter(
                    new NumericDocumentFilter(this.sensorScaleField.getDocument(), false, true));
        } catch(Throwable t) {
        }
    }
    
    /**
     * Opens a this as an "edit" dialog. If the user selects "OK", the given
     * {@code Sensor} is edited accordingly and this function returns
     * {@code true}. If "Cancel" is pressed, the {@code Sensor} is unmodified
     * and {@code false} is returned.
     * 
     * @param sensor the sensor to be modified.
     * @return {@code true} if the sensor was modified, {@code false}
     *   otherwise.
     * @throws NullPointerException if the given {@code Sensor} is {@code null}.
     */
    public boolean showEditDialog(Sensor sensor) throws NullPointerException {
        if(sensor == null) {
            throw new NullPointerException();
        }
        
        okPushed = false;
        this.setTitle("Edit Sensor Properties");
        this.sensorNameField.setText(sensor.getName());
        this.sensorOffsetField.setText(Double.toString(sensor.getOffset()));
        this.sensorScaleField.setText(Double.toString(sensor.getScalar()));
        this.sensorHighLimitField.setText(Integer.toString(sensor.getHighLimit()));
        this.sensorLowLimitField.setText(Integer.toString(sensor.getLowLimit()));
        this.setModal(true);
        this.setLocationRelativeTo(this.getParent());
        this.setVisible(true);
        
        if(okPushed) {
            double sensorScale = this.getSensorScale();
            if(sensorScale == 0) {
                sensorScale = 1.0;
            }
            
            sensor.setName(this.sensorNameField.getText());
            sensor.setOffset(this.getSensorOffset());
            sensor.setScalar(sensorScale);
            sensor.setHighLimit(this.getSensorHighLimit());
            sensor.setLowLimit(this.getSensorLowLimit());
        }
        
        return okPushed;
    }
    
    /**
     * Opens a this as an "add" dialog. If the "OK" button is pushed, a new
     * {@code Sensor} is created and returned, otherwise {@code null} is
     * returned.
     * 
     * @return a new {@code Sensor} object if the user pressed "OK", otherwise
     *   {@code null}.
     */
    public Sensor showAddDialog() {
        Sensor sensor = null;
        
        okPushed = false;
        this.setTitle("Add Sensor");
        this.sensorNameField.setText("");
        this.sensorOffsetField.setText("0");
        this.sensorScaleField.setText("1");
        this.setModal(true);
        this.setLocationRelativeTo(this.getParent());
        this.setVisible(true);
        
        if(okPushed) {
            double sensorScale = this.getSensorScale();
            if(sensorScale == 0) {
                sensorScale = 1.0;
            }
            
            sensor = new Sensor(this.sensorNameField.getText(),
                    sensorScale,
                    this.getSensorOffset(),
                    getSensorHighLimit(),
                    getSensorLowLimit(),
                    50);
        }
        
        return sensor;
    }
    
    private double getSensorOffset() {
        if(this.sensorOffsetField.getText().isEmpty()) {
            return 0.0;
        }
        
        return Double.parseDouble(this.sensorOffsetField.getText());
    }
    
    private double getSensorScale() {
        if(this.sensorScaleField.getText().isEmpty()) {
            return 0.0;
        }
        
        return Double.parseDouble(this.sensorScaleField.getText());
    }
    
    private int getSensorHighLimit() {
        if(this.sensorHighLimitField.getText().isEmpty()) {
            return 0;
        }
        
        return Integer.parseInt(this.sensorHighLimitField.getText());
    }
    
    private int getSensorLowLimit() {
        if(this.sensorLowLimitField.getText().isEmpty()) {
            return 0;
        }
        
        return Integer.parseInt(this.sensorLowLimitField.getText());
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
        sensorNameLabel = new javax.swing.JLabel();
        sensorNameField = new javax.swing.JTextField();
        sensorOffsetLabel = new javax.swing.JLabel();
        sensorOffsetField = new javax.swing.JTextField();
        sensorScaleLabel = new javax.swing.JLabel();
        sensorScaleField = new javax.swing.JTextField();
        sensorHighLimitLabel = new javax.swing.JLabel();
        sensorHighLimitField = new javax.swing.JTextField();
        sensorLowLimitLabel = new javax.swing.JLabel();
        sensorLowLimitField = new javax.swing.JTextField();
        buttonPanel = new javax.swing.JPanel();
        buttonPanelSpacer = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Sensor Properties");
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        basePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        basePanel.setMinimumSize(new java.awt.Dimension(200, 0));
        basePanel.setLayout(new java.awt.GridBagLayout());

        sensorNameLabel.setText("Sensor Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        basePanel.add(sensorNameLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        basePanel.add(sensorNameField, gridBagConstraints);

        sensorOffsetLabel.setText("Offset:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        basePanel.add(sensorOffsetLabel, gridBagConstraints);

        sensorOffsetField.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        basePanel.add(sensorOffsetField, gridBagConstraints);

        sensorScaleLabel.setText("Scale:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        basePanel.add(sensorScaleLabel, gridBagConstraints);

        sensorScaleField.setText("1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        basePanel.add(sensorScaleField, gridBagConstraints);

        sensorHighLimitLabel.setText("High Limit:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        basePanel.add(sensorHighLimitLabel, gridBagConstraints);

        sensorHighLimitField.setText("90");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        basePanel.add(sensorHighLimitField, gridBagConstraints);

        sensorLowLimitLabel.setText("Low Limit:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        basePanel.add(sensorLowLimitLabel, gridBagConstraints);

        sensorLowLimitField.setText("10");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        basePanel.add(sensorLowLimitField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        getContentPane().add(basePanel, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        buttonPanelSpacer.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout buttonPanelSpacerLayout = new javax.swing.GroupLayout(buttonPanelSpacer);
        buttonPanelSpacer.setLayout(buttonPanelSpacerLayout);
        buttonPanelSpacerLayout.setHorizontalGroup(
            buttonPanelSpacerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );
        buttonPanelSpacerLayout.setVerticalGroup(
            buttonPanelSpacerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        buttonPanel.add(buttonPanelSpacer, gridBagConstraints);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(okButton, new java.awt.GridBagConstraints());

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(cancelButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(buttonPanel, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-433)/2, (screenSize.height-258)/2, 433, 258);
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        this.okPushed = true;
        this.setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.okPushed = false;
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditSensorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditSensorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditSensorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditSensorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                EditSensorDialog dialog = new EditSensorDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
//                dialog.setVisible(true);
                
                Sensor sensor = dialog.showAddDialog();
                if(sensor != null) {
                if(dialog.showEditDialog(sensor)) {
                    System.out.println("OK Pushed: name = " + sensor.getName() + ", offset = " + sensor.getOffset() + ", scale = " + sensor.getScalar());
                } else {
                    System.out.println("Cancel Pushed: name = " + sensor.getName() + ", offset = " + sensor.getOffset() + ", scale = " + sensor.getScalar());
                }
                } else {
                    System.out.println("Cancel pushed: null returned");
                }
                
                System.exit(0);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel buttonPanelSpacer;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField sensorHighLimitField;
    private javax.swing.JLabel sensorHighLimitLabel;
    private javax.swing.JTextField sensorLowLimitField;
    private javax.swing.JLabel sensorLowLimitLabel;
    private javax.swing.JTextField sensorNameField;
    private javax.swing.JLabel sensorNameLabel;
    private javax.swing.JTextField sensorOffsetField;
    private javax.swing.JLabel sensorOffsetLabel;
    private javax.swing.JTextField sensorScaleField;
    private javax.swing.JLabel sensorScaleLabel;
    // End of variables declaration//GEN-END:variables
}
