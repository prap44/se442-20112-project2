/* $Id$
 * 
 * VitalDisplayGrid.java
 *
 * Created on Feb 17, 2012, 3:31:16 PM
 */
package hms.common.gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class VitalDisplayGrid extends javax.swing.JPanel {
    
    private Map<String, Integer> data;
    private ArrayList<VitalDisplayPanel> panels = new ArrayList<VitalDisplayPanel>();
    private int previousColumnCount = 0;
    private int defaultVDPanelMinWidth = (new VitalDisplayPanel().getMinimumSize().width);
    private int defaultVDPanelMinHeight = (new VitalDisplayPanel().getPreferredSize().height);
    
    /* Used to reduce number of redundant "invokeWait()" calls to
     * arrangeGrid() when resizing/etc */
    private boolean gridArrangeInvokeWaiting = true;

    /** Creates new form VitalDisplayGrid */
    public VitalDisplayGrid() {
        initComponents();
    }
    
    public void setData(Map<String, Integer> data) {
        this.data = data;
        this.arrangeGrid();
    }
    
    public boolean hasElements() {
        return this.data != null && !this.data.isEmpty();
    }
    
    public int getExpectedHeight() {
        int columnCount = Math.max(this.basePanel.getWidth() / defaultVDPanelMinWidth, 1);
        int rowCount = this.data.size() / columnCount + 1;
        return rowCount * defaultVDPanelMinHeight;
    }
    
    private void arrangeGrid() {
        this.gridArrangeInvokeWaiting = false;
        
        if(data != null) {
            Set<String> keys = this.data.keySet();
            Set<String> knownKeys = new TreeSet<String>();
            
            GridBagConstraints gbContraints = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
            int columnCount = Math.max(this.basePanel.getWidth() / defaultVDPanelMinWidth, 1);
            int index = 0;
            
            boolean panelListChanged = false;
            boolean columnCountChanged = columnCount != this.previousColumnCount;
            this.previousColumnCount = columnCount;
            
            
            /* Cull unused panels and update existing ones */
            for(VitalDisplayPanel panel : this.panels) {
                if(!data.containsKey(panel.getVitalName())) {
                    /* Remove from list and display panel */
                    this.panels.remove(panel);
                    this.basePanel.remove(panel);
                    panelListChanged = true;
                } else {
                    /* Update panel data */
                    panel.setVitalValue(data.get(panel.getVitalName()).toString());
                    
                    /* Update panel position parameters (if necessary) */
                    if(panelListChanged || columnCountChanged) {
                        boolean hasFocus = panel.hasFocus();
                        this.basePanel.remove(panel);
                        this.basePanel.add(panel, gbContraints);
                        if(hasFocus) {
                            panel.requestFocus();
                        }
                    }
                    
                    /* Update contraints object */
                    index++;
                    gbContraints.gridx = index % columnCount;
                    gbContraints.gridy = index / columnCount;
                    
                    /* Add to set of known keys */
                    knownKeys.add(panel.getVitalName());
                }
            }
            
            /* Add new panels to the end of the list */
            for(String key : keys) {
                if(!knownKeys.contains(key)) {
                    /* Add a new panel */
                    VitalDisplayPanel panel = new VitalDisplayPanel(key, this.data.get(key).toString());
                    this.panels.add(panel);
                    this.basePanel.add(panel, gbContraints);
                    
                    /* Update contraints object */
                    index++;
                    gbContraints.gridx = index % columnCount;
                    gbContraints.gridy = index / columnCount;
                }
            }
        } else {
            if(!this.panels.isEmpty()) {
                /* Clear grid */
                this.basePanel.removeAll();
                this.panels.clear();
            }
        }
        
//        synchronized(VitalDisplayGrid.this.getTreeLock()) {
//            VitalDisplayGrid.this.validateTree();
//        }
        this.validate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        basePanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout(1, 0));

        basePanel.setBackground(java.awt.SystemColor.controlDkShadow);
        basePanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                basePanelComponentResized(evt);
            }
        });
        basePanel.setLayout(new java.awt.GridBagLayout());
        add(basePanel);
    }// </editor-fold>//GEN-END:initComponents

    private void basePanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_basePanelComponentResized
        if(!this.gridArrangeInvokeWaiting) {
            this.gridArrangeInvokeWaiting = true;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    VitalDisplayGrid.this.arrangeGrid();
                }
            });
        }
    }//GEN-LAST:event_basePanelComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    // End of variables declaration//GEN-END:variables
}
