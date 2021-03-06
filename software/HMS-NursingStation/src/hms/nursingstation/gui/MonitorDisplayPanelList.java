/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/* $Id$
 * 
 * PatientDisplayPanelList.java
 *
 * Created on Feb 17, 2012, 8:43:19 PM
 */
package hms.nursingstation.gui;

import hms.nursingstation.MonitorProxy;
import hms.nursingstation.NursingStationImpl;
import hms.nursingstation.events.MonitorStatusChangedEvent;
import hms.nursingstation.gui.MonitorDisplayPanel.DeletePanelEvent;
import hms.nursingstation.gui.MonitorDisplayPanel.DisplayExpandedEvent;
import hms.nursingstation.gui.MonitorDisplayPanel.EditPanelEvent;
import hms.nursingstation.listeners.MonitorStatusChangedListener;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class MonitorDisplayPanelList extends javax.swing.JPanel implements Scrollable {

    private MonitorDisplayPanel.DeletePanelListener deletePanelListener = new MonitorDisplayPanel.DeletePanelListener() {

        @Override
        public void deletePanelButtonPressed(DeletePanelEvent event) {
            if (MonitorDisplayPanelList.this.nursingStation != null) {
                MonitorDisplayPanelList.this.nursingStation.removeMonitor(event.getPanel().getMonitor());
                MonitorDisplayPanelList.this.invoke_refreshList();
            }
        }
    };
    private MonitorDisplayPanel.EditPanelListener editPanelListener = new MonitorDisplayPanel.EditPanelListener() {

        @Override
        public void editPanelButtonPressed(EditPanelEvent event) {
            if (MonitorDisplayPanelList.this.editMonitorDialog.showEditDialogModal(event.getPanel().getMonitor())) {
                MonitorDisplayPanelList.this.invoke_refreshList();
            }
        }
    };
    private MonitorDisplayPanel.DisplayExpandedListener displayExpandedListener = new MonitorDisplayPanel.DisplayExpandedListener() {

        @Override
        public void displayExpaned(DisplayExpandedEvent event) {
            MonitorDisplayPanelList.this.invoke_refreshList();
        }
    };
    private PropertyChangeListener panelPropertyChangeListener = new PropertyChangeListener() {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("preferredSize")) {
                MonitorDisplayPanelList.this.invoke_refreshList();
            }
        }
    };
    private NursingStationImpl nursingStation;
    private EditMonitorDialog editMonitorDialog;
    private List<MonitorDisplayPanel> panels = new ArrayList<MonitorDisplayPanel>();
    private boolean invokeRefreshWaiting = false;
    private boolean refreshRunning = false;

    /** Creates new form PatientDisplayPanelList */
    public MonitorDisplayPanelList(NursingStationImpl nursingStation) {
        initComponents();
        editMonitorDialog = new EditMonitorDialog((Frame) this.getTopLevelAncestor(), true);
        this.setNursingStation(nursingStation);
    }

    public MonitorDisplayPanelList() {
        initComponents();
        editMonitorDialog = new EditMonitorDialog((Frame) this.getTopLevelAncestor(), true);
        this.nursingStation = new NursingStationImpl();
        this.setNursingStation(this.nursingStation);
    }

    public final void setNursingStation(NursingStationImpl nursingStation) {
        this.nursingStation = nursingStation;
        this.nursingStation.addMonitorStatusChangedListener(new MonitorStatusChangedListener() {

            @Override
            public void monitorStatusChanged(MonitorStatusChangedEvent event) {
                MonitorDisplayPanelList.this.refreshList();
            }
        });
    }

    private int findPanel(MonitorProxy monitor) {
        for (int i = 0; i < this.panels.size(); i++) {
            if (this.panels.get(i).getMonitor() == monitor) {
                return i;
            }
        }

        return -1;
    }

    public void addMonitorRequest() {
        MonitorProxy monitor = this.editMonitorDialog.showAddDialogModal();
        if (monitor != null) {
            this.nursingStation.addMonitor(monitor);
            this.refreshList();
        }
    }
    
    private void invoke_refreshList() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if(!MonitorDisplayPanelList.this.invokeRefreshWaiting) {
                    MonitorDisplayPanelList.this.invokeRefreshWaiting = true;
                    MonitorDisplayPanelList.this.refreshList();
                }
            }
        });
    }

    private void refreshList() {
        if(!this.refreshRunning) {
            this.refreshRunning = true;
            this.invokeRefreshWaiting = false;
            Insets insets = this.getInsets();

            if (this.nursingStation != null) {
                int yAccum = 0;

                /* Cull unnecessary panels */
                for (int i = 0; i < this.panels.size(); i++) {
                    MonitorDisplayPanel panel = this.panels.get(i);
                    if (!this.nursingStation.containsMonitor(panel.getMonitor())) {
                        this.panels.remove(panel);
                        this.remove(panel);
                        panel.removeDeletePanelListener(this.deletePanelListener);
                        panel.removeEditPanelListener(this.editPanelListener);
                        panel.removeDisplayExpandedListener(this.displayExpandedListener);
                        panel.removePropertyChangeListener(this.panelPropertyChangeListener);
                        i--;
                    }
                }

                /* Add new panels and sort existing ones */
                for (int i = 0; i < this.nursingStation.getMonitorCount(); i++) {
                    MonitorProxy monitor = this.nursingStation.getMonitor(i);
                    MonitorDisplayPanel panel;

                    /* Check if panel exists */
                    int panelIndex = this.findPanel(monitor);
                    if (panelIndex >= 0) {
                        panel = this.panels.get(panelIndex);

                        /* Check if panel is in the correct list location */
                        if (panelIndex != i) {
                            /* Swap panel at index 'i' with panel at index 'panelIndex' */
                            this.panels.set(panelIndex, this.panels.get(i));
                            this.panels.set(i, panel);
                            this.add(panel);
                        }

                        /* Position and size panel */
                        panel.validate();
                        panel.setBounds(insets.left, yAccum + insets.top, this.getWidth() - insets.right, panel.getPreferredSize().height);
                        yAccum += panel.getHeight() + 1;
                    } else {
                        /* Panel does not exist, add it to the list */
                        panel = new MonitorDisplayPanel(monitor);
                        panel.addDeletePanelListener(this.deletePanelListener);
                        panel.addEditPanelListener(this.editPanelListener);
                        panel.addDisplayExpandedListener(this.displayExpandedListener);
                        panel.addPropertyChangeListener(this.panelPropertyChangeListener);
                        this.panels.add(i, panel);
                        this.add(panel);

                        /* Position and size panel */
                        panel.validate();
                        panel.setBounds(insets.left, yAccum + insets.top, this.getWidth() - insets.right, panel.getPreferredSize().height);
                        yAccum += panel.getHeight() + 1;
                    }
                }

                Dimension viewSize = this.getPreferredSize();
                viewSize.height = yAccum + insets.top + insets.bottom;
                this.setPreferredSize(viewSize);

                this.validate();
            }
            this.refreshRunning = false;
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

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        this.invoke_refreshList();
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return this.getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 20;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 20;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
