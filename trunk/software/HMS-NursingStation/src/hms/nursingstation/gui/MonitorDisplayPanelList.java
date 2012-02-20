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
import hms.nursingstation.gui.MonitorDisplayPanel.DisplayExpandedEvent;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.peer.ComponentPeer;
import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;
import sun.awt.AWTAccessor.ComponentAccessor;
import sun.awt.AppContext;
import sun.awt.CausedFocusEvent.Cause;
import sun.awt.RequestFocusController;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class MonitorDisplayPanelList extends javax.swing.JPanel {
    
    private class DeletePanelEventHandler implements MonitorDisplayPanel.DeletePanelListener {
        @Override
        public void deletePanelButtonPressed(MonitorDisplayPanel.DeletePanelEvent event) {
            if(MonitorDisplayPanelList.this.monitors != null) {
                MonitorDisplayPanelList.this.monitors.remove(event.getPanel().getMonitor());
                MonitorDisplayPanelList.this.arrangeList();
            }
        }
    }
    
    private class EditPanelEventHandler implements MonitorDisplayPanel.EditPanelListener {
        @Override
        public void editPanelButtonPressed(MonitorDisplayPanel.EditPanelEvent event) {
            if(MonitorDisplayPanelList.this.editMonitorDialog.showEditDialogModal(event.getPanel().getMonitor())) {
                MonitorDisplayPanelList.this.arrangeList();
            }
        }
    }

    private EditMonitorDialog editMonitorDialog;
    private List<MonitorProxy> monitors = null;
    private List<MonitorDisplayPanel> panels = new ArrayList<MonitorDisplayPanel>();
    
    /** Creates new form PatientDisplayPanelList */
    public MonitorDisplayPanelList() {
        initComponents();
        
        editMonitorDialog = new EditMonitorDialog((Frame)this.getTopLevelAncestor(), true);
    }
    
    public void setMonitors(List<MonitorProxy> monitors) {
        this.monitors = monitors;
        this.arrangeList();
    }
    
    private int findPanel(MonitorProxy monitor) {
        for(int i = 0; i < this.panels.size(); i++) {
            if(this.panels.get(i).getMonitor() == monitor) {
                return i;
            }
        }
        
        return -1;
    }
    
    public void requestAddPanel() {
        MonitorProxy monitor = this.editMonitorDialog.showAddDialogModal();
        if(monitor != null) {
            this.monitors.add(monitor);
            this.arrangeList();
        }
    }
    
    private void arrangeList() {
        this.validate();
        
        if(this.monitors != null) {
            int yAccum = 0;
            
            /* Cull unnecessary panels */
            for(int i = 0; i < this.panels.size(); i++) {
                MonitorDisplayPanel panel = this.panels.get(i);
                if(!this.monitors.contains(panel.getMonitor())) {
                    this.panels.remove(panel);
                    this.basePanel.remove(panel);
                    i--;
                }
            }
            
            /* Add new panels and sort existing ones */
            for(int i = 0; i < this.monitors.size(); i++) {
                MonitorProxy monitor = this.monitors.get(i);
                MonitorDisplayPanel panel;
                
                /* Check if panel exists */
                int panelIndex = this.findPanel(monitor);
                if(panelIndex >= 0) {
                    panel = this.panels.get(panelIndex);
                    
                    /* Check if panel is in the correct list location */
                    if(panelIndex != i) {
                        /* Swap panel at index 'i' with panel at index 'panelIndex' */
                        this.panels.set(panelIndex, this.panels.get(i));
                        this.panels.set(i, panel);
                        this.basePanel.add(panel);
                    }
                    
                    /* Position and size panel */
                    panel.validate();
                    panel.setBounds(0, yAccum, this.scrollPanel.getViewport().getWidth(), panel.getPreferredSize().height);
                    yAccum += panel.getHeight() + 1;
                } else {
                    /* Panel does not exist, add it to the list */
                    panel = new MonitorDisplayPanel(monitor);
                    panel.addDeletePanelListener(new DeletePanelEventHandler());
                    panel.addEditPanelListener(new EditPanelEventHandler());
                    panel.addDisplayExpandedListener(new MonitorDisplayPanel.DisplayExpandedListener() {
                        @Override
                        public void displayExpaned(DisplayExpandedEvent event) {
                            MonitorDisplayPanelList.this.arrangeList();
                        }
                    });
                    this.panels.add(i, panel);
                    this.basePanel.add(panel);
                    
                    /* Position and size panel */
                    panel.validate();
                    panel.setBounds(0, yAccum, this.scrollPanel.getViewport().getWidth(), panel.getPreferredSize().height);
                    yAccum += panel.getHeight() + 1;
                }
            }
            
            Dimension viewSize = new Dimension(this.scrollPanel.getViewport().getWidth(), yAccum);
            this.basePanel.setSize(viewSize);
            this.basePanel.setPreferredSize(viewSize);
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

        scrollPanel = new javax.swing.JScrollPane();
        basePanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout(1, 0));

        scrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                scrollPanelComponentResized(evt);
            }
        });

        basePanel.setLayout(null);
        scrollPanel.setViewportView(basePanel);

        add(scrollPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void scrollPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_scrollPanelComponentResized
        int viewportWidth = this.scrollPanel.getViewport().getWidth();
        
        for(MonitorDisplayPanel panel : this.panels) {
            panel.setMinimumSize(new Dimension(viewportWidth, panel.getPreferredSize().height));
        }
        
        this.basePanel.setPreferredSize(new Dimension(viewportWidth, this.basePanel.getPreferredSize().height));
        this.basePanel.setMaximumSize(new Dimension(viewportWidth, Integer.MAX_VALUE));
        
        this.arrangeList();
        
        this.validate();
    }//GEN-LAST:event_scrollPanelComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private javax.swing.JScrollPane scrollPanel;
    // End of variables declaration//GEN-END:variables
}
