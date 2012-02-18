/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/* $Id$
 * 
 * EditMonitorDialog.java
 *
 * Created on Feb 18, 2012, 1:42:54 AM
 */
package hms.nursingstation.gui;

import hms.common.Monitor;
import java.awt.Dimension;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class EditMonitorDialog extends javax.swing.JDialog {

    /** Creates new form EditMonitorDialog */
    public EditMonitorDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Map<String, Integer> data = new TreeMap<String, Integer>();
        data.put("Vital Stat 1", 50);
        data.put("Vital Stat 2", 9000);
        data.put("Vital Stat 3", 2);
        data.put("Vital Stat 4", 50);
        data.put("Vital Stat 5", 9000);
        data.put("Vital Stat 6", 2);
        data.put("Vital Stat 7", 50);
        data.put("Vital Stat 8", 9000);
        data.put("Vital Stat 9", 2);
        data.put("Vital Stat 10", 50);
        data.put("Vital Stat 11", 9000);
        data.put("Vital Stat 12", 2);
        this.vitalDisplayGrid.setData(data);
    }
    
    void showDialogModal(Monitor monitor) {
        this.setModal(true);
        this.pack();
        this.setVisible(true);
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
        monitorConfigurationPanel = new javax.swing.JPanel();
        monitorIDLabel = new javax.swing.JLabel();
        monitorIDField = new javax.swing.JTextField();
        monitorAddressLabel = new javax.swing.JLabel();
        monitorAddressField = new javax.swing.JTextField();
        patientConfigurationPanel = new javax.swing.JPanel();
        patientAssignedCheckbox = new javax.swing.JCheckBox();
        patientFirstNameLabel = new javax.swing.JLabel();
        patientFirstNameField = new javax.swing.JTextField();
        patientMiddleNameLabel = new javax.swing.JLabel();
        patientMiddleNameField = new javax.swing.JTextField();
        patientLastNameLabel = new javax.swing.JLabel();
        patientLastNameField = new javax.swing.JTextField();
        vitalStatisticsPanel = new javax.swing.JPanel();
        vitalDisplayScrollPanel = new javax.swing.JScrollPane();
        vitalDisplayViewpanel = new javax.swing.JPanel();
        vitalDisplayGrid = new hms.nursingstation.gui.VitalDisplayGrid();
        vitalDisplayViewpanelSpanner = new javax.swing.JPanel();
        buttonPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        basePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        basePanel.setLayout(new java.awt.GridBagLayout());

        monitorConfigurationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Bedside Monitor Settings"));
        monitorConfigurationPanel.setLayout(new java.awt.GridBagLayout());

        monitorIDLabel.setText("ID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        monitorConfigurationPanel.add(monitorIDLabel, gridBagConstraints);

        monitorIDField.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        monitorConfigurationPanel.add(monitorIDField, gridBagConstraints);

        monitorAddressLabel.setText("Address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        monitorConfigurationPanel.add(monitorAddressLabel, gridBagConstraints);

        monitorAddressField.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        monitorConfigurationPanel.add(monitorAddressField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        basePanel.add(monitorConfigurationPanel, gridBagConstraints);

        patientConfigurationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Patient Settings"));
        patientConfigurationPanel.setLayout(new java.awt.GridBagLayout());

        patientAssignedCheckbox.setSelected(true);
        patientAssignedCheckbox.setText("Assigned");
        patientAssignedCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientAssignedCheckboxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        patientConfigurationPanel.add(patientAssignedCheckbox, gridBagConstraints);

        patientFirstNameLabel.setText("First Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        patientConfigurationPanel.add(patientFirstNameLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        patientConfigurationPanel.add(patientFirstNameField, gridBagConstraints);

        patientMiddleNameLabel.setText("Middle Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        patientConfigurationPanel.add(patientMiddleNameLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        patientConfigurationPanel.add(patientMiddleNameField, gridBagConstraints);

        patientLastNameLabel.setText("Last Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        patientConfigurationPanel.add(patientLastNameLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        patientConfigurationPanel.add(patientLastNameField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        basePanel.add(patientConfigurationPanel, gridBagConstraints);

        vitalStatisticsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Vital Statistics"));
        vitalStatisticsPanel.setMinimumSize(new java.awt.Dimension(124, 144));
        vitalStatisticsPanel.setName(""); // NOI18N
        vitalStatisticsPanel.setLayout(new java.awt.GridBagLayout());

        vitalDisplayScrollPanel.setMinimumSize(new java.awt.Dimension(112, 122));
        vitalDisplayScrollPanel.setPreferredSize(new java.awt.Dimension(112, 122));
        vitalDisplayScrollPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                vitalDisplayScrollPanelComponentResized(evt);
            }
        });

        vitalDisplayViewpanel.setMinimumSize(new java.awt.Dimension(0, 0));
        vitalDisplayViewpanel.setPreferredSize(new java.awt.Dimension(0, 0));
        vitalDisplayViewpanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        vitalDisplayViewpanel.add(vitalDisplayGrid, gridBagConstraints);

        vitalDisplayViewpanelSpanner.setMinimumSize(new java.awt.Dimension(0, 0));
        vitalDisplayViewpanelSpanner.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout vitalDisplayViewpanelSpannerLayout = new javax.swing.GroupLayout(vitalDisplayViewpanelSpanner);
        vitalDisplayViewpanelSpanner.setLayout(vitalDisplayViewpanelSpannerLayout);
        vitalDisplayViewpanelSpannerLayout.setHorizontalGroup(
            vitalDisplayViewpanelSpannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 447, Short.MAX_VALUE)
        );
        vitalDisplayViewpanelSpannerLayout.setVerticalGroup(
            vitalDisplayViewpanelSpannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        vitalDisplayViewpanel.add(vitalDisplayViewpanelSpanner, gridBagConstraints);

        vitalDisplayScrollPanel.setViewportView(vitalDisplayViewpanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        vitalStatisticsPanel.add(vitalDisplayScrollPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        basePanel.add(vitalStatisticsPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        getContentPane().add(basePanel, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        buttonPanel.add(jPanel1, gridBagConstraints);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        buttonPanel.add(okButton, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        buttonPanel.add(cancelButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(buttonPanel, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-489)/2, (screenSize.height-492)/2, 489, 492);
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // Close and apply changes
        this.setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // Close and do not apply changes
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void patientAssignedCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientAssignedCheckboxActionPerformed
        boolean arePatientFieldsEnabled = this.patientAssignedCheckbox.isSelected();
        this.patientFirstNameField.setEnabled(arePatientFieldsEnabled);
        this.patientMiddleNameField.setEnabled(arePatientFieldsEnabled);
        this.patientLastNameField.setEnabled(arePatientFieldsEnabled);
    }//GEN-LAST:event_patientAssignedCheckboxActionPerformed

    private void vitalDisplayScrollPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_vitalDisplayScrollPanelComponentResized
        Dimension size = this.vitalDisplayScrollPanel.getViewport().getSize();
        Dimension maxSize = new Dimension(size.width, Integer.MAX_VALUE);
        this.vitalDisplayViewpanel.setMinimumSize(size);
        this.vitalDisplayViewpanel.setMaximumSize(maxSize);
        
        synchronized(this.getTreeLock()) {
            this.validateTree();
        }
    }//GEN-LAST:event_vitalDisplayScrollPanelComponentResized

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
            java.util.logging.Logger.getLogger(EditMonitorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditMonitorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditMonitorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditMonitorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                EditMonitorDialog dialog = new EditMonitorDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField monitorAddressField;
    private javax.swing.JLabel monitorAddressLabel;
    private javax.swing.JPanel monitorConfigurationPanel;
    private javax.swing.JTextField monitorIDField;
    private javax.swing.JLabel monitorIDLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JCheckBox patientAssignedCheckbox;
    private javax.swing.JPanel patientConfigurationPanel;
    private javax.swing.JTextField patientFirstNameField;
    private javax.swing.JLabel patientFirstNameLabel;
    private javax.swing.JTextField patientLastNameField;
    private javax.swing.JLabel patientLastNameLabel;
    private javax.swing.JTextField patientMiddleNameField;
    private javax.swing.JLabel patientMiddleNameLabel;
    private hms.nursingstation.gui.VitalDisplayGrid vitalDisplayGrid;
    private javax.swing.JScrollPane vitalDisplayScrollPanel;
    private javax.swing.JPanel vitalDisplayViewpanel;
    private javax.swing.JPanel vitalDisplayViewpanelSpanner;
    private javax.swing.JPanel vitalStatisticsPanel;
    // End of variables declaration//GEN-END:variables
}
