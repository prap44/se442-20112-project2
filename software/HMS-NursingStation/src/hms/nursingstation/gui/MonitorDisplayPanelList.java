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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class MonitorDisplayPanelList extends javax.swing.JPanel {

    private List<MonitorProxy> monitors = null;
    private List<MonitorDisplayPanel> panels = new ArrayList<MonitorDisplayPanel>();
    
    /** Creates new form PatientDisplayPanelList */
    public MonitorDisplayPanelList() {
        initComponents();
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
    
    private void arrangeList() {
        if(this.monitors != null) {
            GridBagConstraints gbConstraints = new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
            
            /* Cull unnecessary panels */
            for(MonitorDisplayPanel panel : this.panels) {
                if(!this.monitors.contains(panel.getMonitor())) {
                    this.panels.remove(panel);
                    this.listPanel.remove(panel);
                }
            }
            
            /* Add new panels and sort existing ones */
            for(int i = 0; i < this.monitors.size(); i++) {
                MonitorProxy monitor = this.monitors.get(i);
                MonitorDisplayPanel panel;
                gbConstraints.gridy = i;
                
                /* Check if panel exists */
                int panelIndex = this.findPanel(monitor);
                if(panelIndex >= 0) {
                    panel = this.panels.get(panelIndex);
                    
                    /* Check if panel is in the correct list location */
                    if(panelIndex != i) {
                        /* Swap panel at index 'i' with panel at index 'panelIndex' */
                        this.panels.set(panelIndex, this.panels.get(i));
                        this.panels.set(i, panel);
                        this.listPanel.add(panel, gbConstraints);
                    }
                    
                    /* TODO: Update the panel's display */
                } else {
                    /* Panel does not exist, add it to the list */
                    panel = new MonitorDisplayPanel(monitor);
                    this.panels.add(i, panel);
                    this.listPanel.add(panel, gbConstraints);
                }
            }
        }
        
        this.basePanel.validate();
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
        listPanel = new javax.swing.JPanel();
        spacerPanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout(1, 0));

        scrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                scrollPanelComponentResized(evt);
            }
        });

        basePanel.setLayout(new java.awt.GridBagLayout());

        listPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        basePanel.add(listPanel, gridBagConstraints);

        spacerPanel.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout spacerPanelLayout = new javax.swing.GroupLayout(spacerPanel);
        spacerPanel.setLayout(spacerPanelLayout);
        spacerPanelLayout.setHorizontalGroup(
            spacerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );
        spacerPanelLayout.setVerticalGroup(
            spacerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        basePanel.add(spacerPanel, gridBagConstraints);

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
        
        this.validate();
    }//GEN-LAST:event_scrollPanelComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private javax.swing.JPanel listPanel;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JPanel spacerPanel;
    // End of variables declaration//GEN-END:variables
}
