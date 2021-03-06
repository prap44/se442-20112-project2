/* $Id$
 * 
 * PatientDisplayPanel.java
 *
 * Created on Feb 17, 2012, 7:22:02 PM
 */
package hms.nursingstation.gui;

import hms.common.Patient;
import hms.nursingstation.MonitorProxy;
import hms.nursingstation.MonitorProxy.MonitorDisconnectedException;
import hms.nursingstation.events.InformationChangeReceivedEvent;
import hms.nursingstation.listeners.InformationChangeReceivedListener;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class MonitorDisplayPanel extends javax.swing.JPanel {

    public class DeletePanelEvent {

        private MonitorDisplayPanel panel;

        public DeletePanelEvent(MonitorDisplayPanel panel) {
            this.panel = panel;
        }

        public MonitorDisplayPanel getPanel() {
            return this.panel;
        }
    }

    public interface DeletePanelListener extends EventListener {

        public void deletePanelButtonPressed(DeletePanelEvent event);
    }

    public class EditPanelEvent {

        private MonitorDisplayPanel panel;

        public EditPanelEvent(MonitorDisplayPanel panel) {
            this.panel = panel;
        }

        public MonitorDisplayPanel getPanel() {
            return this.panel;
        }
    }

    public interface EditPanelListener extends EventListener {

        public void editPanelButtonPressed(EditPanelEvent event);
    }

    public class DisplayExpandedEvent {

        private MonitorDisplayPanel panel;

        public DisplayExpandedEvent(MonitorDisplayPanel panel) {
            this.panel = panel;
        }

        public MonitorDisplayPanel getPanel() {
            return this.panel;
        }
    }

    public interface DisplayExpandedListener extends EventListener {

        public void displayExpaned(DisplayExpandedEvent event);
    }
    private MonitorProxy monitor = null;
    private InformationChangeReceivedListener infoChangedListener = new InformationChangeReceivedListener() {

        @Override
        public void informationChangeReceived(InformationChangeReceivedEvent event) {
            try {
                MonitorDisplayPanel.this.updateIdentifyingData();
            } catch (RemoteException ex) {
                Logger.getLogger(MonitorDisplayPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    /** Creates new form PatientDisplayPanel */
    public MonitorDisplayPanel(MonitorProxy monitor) {
        initComponents();

        this.setMonitor(monitor);
        this.postInit();
    }

    public MonitorDisplayPanel() {
        initComponents();

        try {
            this.setMonitor(new MonitorProxy());
        } catch (IOException ex) {
            Logger.getLogger(MonitorDisplayPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.postInit();
    }

    private void postInit() {
        this.vitalDisplayGrid.setVisible(false);

//        Map<String, Integer> data = new TreeMap<String, Integer>();
//        data.put("Vital Stat 1", 50);
//        data.put("Vital Stat 2", 9000);
//        data.put("Vital Stat 3", 2);
//        data.put("Vital Stat 4", 50);
//        data.put("Vital Stat 5", 9000);
//        data.put("Vital Stat 6", 2);
//        data.put("Vital Stat 7", 50);
//        data.put("Vital Stat 8", 9000);
//        data.put("Vital Stat 9", 2);
//        data.put("Vital Stat 10", 50);
//        data.put("Vital Stat 11", 9000);
//        data.put("Vital Stat 12", 2);
//        this.vitalDisplayGrid.setData(data);

        try {
            /* Set the monitor's ID and patient's name from monitor reference */
            this.updateIdentifyingData();
        } catch (RemoteException ex) {
            Logger.getLogger(MonitorDisplayPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateIdentifyingData() throws RemoteException {
        if (this.monitor != null && this.monitor.isConnected()) {
            try {
                this.monitorIDValueLabel.setText(monitor.getMonitorID());
                this.monitorIDValueLabel.setFont(this.monitorIDValueLabel.getFont().deriveFont(Font.PLAIN));

                Patient patient = this.monitor.getPatient();
                if (this.monitor.getPatient() != null) {
                    this.patientNameValueLabel.setText((patient.getPatientLastName() + ", " + patient.getPatientFirstName() + " " + patient.getPatientMiddleName()).trim());
                    this.patientNameValueLabel.setFont(this.patientNameValueLabel.getFont().deriveFont(Font.PLAIN));
                } else {
                    this.patientNameValueLabel.setText("None Assigned");
                    this.patientNameValueLabel.setFont(this.patientNameValueLabel.getFont().deriveFont(Font.ITALIC));
                }
            } catch (MonitorDisconnectedException ex) {
                Logger.getLogger(MonitorDisplayPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.monitorIDValueLabel.setText("Disconnected");
            this.monitorIDValueLabel.setFont(this.monitorIDValueLabel.getFont().deriveFont(Font.ITALIC));
            this.patientNameValueLabel.setText("Unknown");
            this.patientNameValueLabel.setFont(this.patientNameValueLabel.getFont().deriveFont(Font.ITALIC));
        }
    }

    public final void setMonitor(MonitorProxy monitor) {
        if (this.monitor != null) {
            /* TODO: Remove listeners when monitor is changed */
        }

        this.monitor = monitor;
        this.vitalDisplayGrid.setMonitor(monitor);

        this.monitor.addInformationChangeReceivedListener(this.infoChangedListener);
    }

    public final MonitorProxy getMonitor() {
        return this.monitor;
    }

    public void addDeletePanelListener(DeletePanelListener l) {
        this.listenerList.add(DeletePanelListener.class, l);
    }

    public void removeDeletePanelListener(DeletePanelListener l) {
        this.listenerList.remove(DeletePanelListener.class, l);
    }

    private void raiseDeletePanelEvent(DeletePanelEvent e) {
        for (DeletePanelListener l : this.listenerList.getListeners(DeletePanelListener.class)) {
            l.deletePanelButtonPressed(e);
        }
    }

    public void addEditPanelListener(EditPanelListener l) {
        this.listenerList.add(EditPanelListener.class, l);
    }

    public void removeEditPanelListener(EditPanelListener l) {
        this.listenerList.remove(EditPanelListener.class, l);
    }

    private void raiseEditPanelEvent(EditPanelEvent e) {
        for (EditPanelListener l : this.listenerList.getListeners(EditPanelListener.class)) {
            l.editPanelButtonPressed(e);
        }
    }

    public void addDisplayExpandedListener(DisplayExpandedListener l) {
        this.listenerList.add(DisplayExpandedListener.class, l);
    }

    public void removeDisplayExpandedListener(DisplayExpandedListener l) {
        this.listenerList.remove(DisplayExpandedListener.class, l);
    }

    private void raiseDisplayExpandedEvent(DisplayExpandedEvent e) {
        for (DisplayExpandedListener l : this.listenerList.getListeners(DisplayExpandedListener.class)) {
            l.displayExpaned(e);
        }
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
        patientDataPanel = new javax.swing.JPanel();
        monitorIDLabel = new javax.swing.JLabel();
        monitorIDValueLabel = new javax.swing.JLabel();
        patientNameLabel = new javax.swing.JLabel();
        patientNameValueLabel = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        expandButton = new javax.swing.JToggleButton();
        vitalDisplayGrid = new hms.nursingstation.gui.VitalDisplayGrid();

        setLayout(new java.awt.GridLayout(1, 0));

        basePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        basePanel.setLayout(new java.awt.GridBagLayout());

        patientDataPanel.setLayout(new java.awt.GridBagLayout());

        monitorIDLabel.setText("Monitor ID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        patientDataPanel.add(monitorIDLabel, gridBagConstraints);

        monitorIDValueLabel.setText("bsm3020a");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        patientDataPanel.add(monitorIDValueLabel, gridBagConstraints);

        patientNameLabel.setText("Patient Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        patientDataPanel.add(patientNameLabel, gridBagConstraints);

        patientNameValueLabel.setText("Doe, John M.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        patientDataPanel.add(patientNameValueLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        basePanel.add(patientDataPanel, gridBagConstraints);

        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        basePanel.add(editButton, gridBagConstraints);

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        basePanel.add(removeButton, gridBagConstraints);

        expandButton.setText(">>");
        expandButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expandButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        basePanel.add(expandButton, gridBagConstraints);

        vitalDisplayGrid.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        vitalDisplayGrid.setData(null);
        vitalDisplayGrid.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                vitalDisplayGridComponentResized(evt);
            }
        });
        vitalDisplayGrid.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                vitalDisplayGridPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        basePanel.add(vitalDisplayGrid, gridBagConstraints);

        add(basePanel);
    }// </editor-fold>//GEN-END:initComponents

    private void expandButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expandButtonActionPerformed
        this.vitalDisplayGrid.setVisible(this.expandButton.isSelected());
        this.revalidate();
        this.raiseDisplayExpandedEvent(new DisplayExpandedEvent(this));
    }//GEN-LAST:event_expandButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        this.raiseEditPanelEvent(new EditPanelEvent(this));
    }//GEN-LAST:event_editButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        this.raiseDeletePanelEvent(new DeletePanelEvent(this));
    }//GEN-LAST:event_removeButtonActionPerformed
    private void vitalDisplayGridComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_vitalDisplayGridComponentResized
        revalidate();
    }//GEN-LAST:event_vitalDisplayGridComponentResized

    private void vitalDisplayGridPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_vitalDisplayGridPropertyChange
        if (evt.getPropertyName().equals("preferredSize")) {
            revalidate();
            for(PropertyChangeListener l : this.getPropertyChangeListeners()) {
                l.propertyChange(new PropertyChangeEvent(this, "preferredSize", this.getPreferredSize(), this.getPreferredSize()));
            }
        }
    }//GEN-LAST:event_vitalDisplayGridPropertyChange
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private javax.swing.JButton editButton;
    private javax.swing.JToggleButton expandButton;
    private javax.swing.JLabel monitorIDLabel;
    private javax.swing.JLabel monitorIDValueLabel;
    private javax.swing.JPanel patientDataPanel;
    private javax.swing.JLabel patientNameLabel;
    private javax.swing.JLabel patientNameValueLabel;
    private javax.swing.JButton removeButton;
    private hms.nursingstation.gui.VitalDisplayGrid vitalDisplayGrid;
    // End of variables declaration//GEN-END:variables
}
