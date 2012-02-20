/* $Id$
 * 
 * MonitorNotificationDialog.java
 *
 * Created on Feb 20, 2012, 5:06:07 AM
 */
package hms.nursingstation.gui;

import hms.nursingstation.MonitorProxy;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class MonitorNotificationDialog extends javax.swing.JDialog {
    
    public enum NotificationType {
        ALARM, REQUEST
    }
    
    private class Notification {
        public MonitorProxy monitor;
        public NotificationType type;
        public String vital;
        public String description;
        
        public Notification(MonitorProxy monitor, NotificationType type, String vital, String description) {
            this.monitor = monitor;
            this.type = type;
            this.vital = vital;
            this.description = description;
        }
        
        public String generateEntry() {
            String entry = this.monitor.getMonitorID() + ": ";
            switch(this.type) {
                case ALARM:
                    entry += "Alarm: ";
                    entry += vital + ": ";
                    break;
                case REQUEST:
                    entry += "Request: ";
                    break;
            }
            entry += this.description;
            return entry.trim();
        }
    }
    
    private List<Notification> notifications = new ArrayList<Notification>();
    private DefaultListModel notificationListModel = new DefaultListModel();

    /** Creates new form MonitorNotificationDialog */
    public MonitorNotificationDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.notificationList.setModel(notificationListModel);
        this.notificationList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateButtonStatus();
            }
        });
        updateButtonStatus();
    }
    
    public void addNotification(MonitorProxy monitor, NotificationType type, String vital, String description) {
        Notification notification = new Notification(monitor, type, vital, description);
        this.notifications.add(notification);
        this.notificationListModel.addElement(notification.generateEntry());
        if(!this.isVisible()) {
            this.setVisible(true);
            this.setLocationRelativeTo(this.getParent());
        }
        updateButtonStatus();
    }
    
    public void removeNotification(MonitorProxy monitor, NotificationType type, String vital) {
        for(int i = 0; i < this.notifications.size(); i++) {
            Notification n = this.notifications.get(i);
            if(n.monitor.equals(monitor) && n.type.equals(type) &&
                    (n.vital == null) == (vital == null) && (vital == null || n.vital.equals(vital))) {
                this.notifications.remove(i);
                this.notificationList.remove(i);
            }
        }
        updateButtonStatus();
        
        if(this.notifications.isEmpty()) {
            this.setVisible(false);
        }
    }
    
    private void updateButtonStatus() {
        boolean messageSelected = this.notificationList.getSelectedIndex() >= 0;
        this.acknowledgeButton.setEnabled(messageSelected);
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
        detailsPanel = new javax.swing.JPanel();
        notificationScrolPanel = new javax.swing.JScrollPane();
        notificationList = new javax.swing.JList();
        buttonPanel = new javax.swing.JPanel();
        acknowledgeButton = new javax.swing.JButton();
        acknowledgeAllButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        basePanel.setLayout(new java.awt.GridBagLayout());

        detailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()), "Outstanding Notificiations"));
        detailsPanel.setLayout(new java.awt.GridBagLayout());

        notificationScrolPanel.setViewportView(notificationList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        detailsPanel.add(notificationScrolPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        basePanel.add(detailsPanel, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        acknowledgeButton.setText("Acknowledge");
        acknowledgeButton.setEnabled(false);
        acknowledgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acknowledgeButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(acknowledgeButton, new java.awt.GridBagConstraints());

        acknowledgeAllButton.setText("Acknowledge All");
        acknowledgeAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acknowledgeAllButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(acknowledgeAllButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        basePanel.add(buttonPanel, gridBagConstraints);

        getContentPane().add(basePanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void acknowledgeAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acknowledgeAllButtonActionPerformed
        this.notifications.clear();
        this.notificationListModel.clear();
        this.setVisible(false);
        this.updateButtonStatus();
    }//GEN-LAST:event_acknowledgeAllButtonActionPerformed

    private void acknowledgeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acknowledgeButtonActionPerformed
        int selectedIndex = this.notificationList.getSelectedIndex();
        if(selectedIndex >= 0) {
            this.notificationListModel.remove(selectedIndex);
            this.notifications.remove(selectedIndex);
        }
        
        if(this.notifications.isEmpty()) {
            this.setVisible(false);
        }
        
        this.updateButtonStatus();
    }//GEN-LAST:event_acknowledgeButtonActionPerformed

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
            java.util.logging.Logger.getLogger(MonitorNotificationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MonitorNotificationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MonitorNotificationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonitorNotificationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                MonitorNotificationDialog dialog = new MonitorNotificationDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton acknowledgeAllButton;
    private javax.swing.JButton acknowledgeButton;
    private javax.swing.JPanel basePanel;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel detailsPanel;
    private javax.swing.JList notificationList;
    private javax.swing.JScrollPane notificationScrolPanel;
    // End of variables declaration//GEN-END:variables
}
