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
package hms.common.gui;

import hms.common.Monitor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class MonitorDisplayPanelList extends javax.swing.JPanel {

    private List<Monitor> monitors = null;
    private List<MonitorDisplayPanel> panels = new ArrayList<MonitorDisplayPanel>();
    
    /** Creates new form PatientDisplayPanelList */
    public MonitorDisplayPanelList() {
        initComponents();
    }
    
    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
    }
    
    private void arrangeList() {
        
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

        basePanel.setLayout(new java.awt.GridBagLayout());

        listPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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
        gridBagConstraints.weighty = 1.0;
        basePanel.add(spacerPanel, gridBagConstraints);

        scrollPanel.setViewportView(basePanel);

        add(scrollPanel);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private javax.swing.JPanel listPanel;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JPanel spacerPanel;
    // End of variables declaration//GEN-END:variables
}
