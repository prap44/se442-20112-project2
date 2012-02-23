/* $Id$
 * 
 * VitalDisplayGrid.java
 *
 * Created on Feb 17, 2012, 3:31:16 PM
 */
package hms.nursingstation.gui;

import hms.nursingstation.MonitorProxy;
import hms.nursingstation.events.DataReceivedEvent;
import hms.nursingstation.listeners.DataReceivedListener;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class VitalDisplayGrid extends javax.swing.JPanel implements Scrollable {

    private MonitorProxy monitor;
    private Map<String, Integer> data;
    private List<VitalDisplayPanel> panels = new ArrayList<VitalDisplayPanel>();
    private int previousColumnCount = 0;
    private int defaultVDPanelMinWidth = (new VitalDisplayPanel().getMinimumSize().width);
    private int defaultVDPanelHeight = (new VitalDisplayPanel().getPreferredSize().height);
    private DataReceivedListener dataReceivedListener = new DataReceivedListener() {

        @Override
        public void dataReceived(final DataReceivedEvent event) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    VitalDisplayGrid.this.setData(event.getVitals());
                }
            });
        }
    };
    private Runnable arrangeGridInvoker = new Runnable() {

        @Override
        public void run() {
            VitalDisplayGrid.this.arrangeGrid();
        }
    };
    private boolean arrangeGridInvokeWaiting = false;
    private boolean arrangeGridInProgress = false;

    /** Creates new form VitalDisplayGrid */
    public VitalDisplayGrid(MonitorProxy monitor) {
        initComponents();
        this.setMonitor(monitor);
    }

    public VitalDisplayGrid() {
        initComponents();
    }

    public final void setMonitor(MonitorProxy monitor) {
        if (this.monitor != null) {
            this.monitor.removeDataReceivedListener(dataReceivedListener);
        }
        this.monitor = monitor;
        if (this.monitor != null) {
            this.monitor.addDataReceivedListener(this.dataReceivedListener);
        }
        this.setData(new HashMap<String, Integer>());
    }

    public void setData(Map<String, Integer> data) {
        this.data = data;
        invoke_arrangeGrid();
    }

    private void invoke_arrangeGrid() {
        if (!this.arrangeGridInvokeWaiting) {
            this.arrangeGridInvokeWaiting = true;
            SwingUtilities.invokeLater(this.arrangeGridInvoker);
        }
    }

    private void arrangeGrid() {
        if (!arrangeGridInProgress) {
            this.arrangeGridInProgress = true;
            this.arrangeGridInvokeWaiting = false;
            int index = 0;
            Insets insets = this.getInsets();
            int width = this.getWidth() - insets.left - insets.right;
            int columnCount = width / defaultVDPanelMinWidth;

            if (data != null) {
                Set<String> keys = this.data.keySet();
                Set<String> knownKeys = new TreeSet<String>();

                columnCount = Math.min(columnCount, this.data.size());
                columnCount = Math.max(columnCount, 1);

                boolean panelListChanged = false;
                boolean columnCountChanged = columnCount != this.previousColumnCount;
                this.previousColumnCount = columnCount;

                /* Build column boundary array */
                int[] columnBounds = new int[columnCount + 1];
                for (int i = 0; i < columnBounds.length; i++) {
                    columnBounds[i] = width * i / columnCount;
                }

                /* Cull unused panels and update existing ones */
                while (index < this.panels.size()) {
                    VitalDisplayPanel panel = this.panels.get(index);
                    if (!data.containsKey(panel.getVitalName())) {
                        /* Remove from list and display panel */
                        this.panels.remove(index);
                        this.remove(panel);
                        panelListChanged = true;
                    } else {
                        int row = index / columnCount;
                        int col = index % columnCount;

                        /* Update panel data */
                        panel.setVitalValue(data.get(panel.getVitalName()).toString());

                        /* Update panel position parameters*/
                        panel.setBounds(insets.left + columnBounds[col], insets.top + row * this.defaultVDPanelHeight, columnBounds[col + 1] - columnBounds[col], this.defaultVDPanelHeight);

                        /* Update index */
                        index++;

                        /* Add to set of known keys */
                        knownKeys.add(panel.getVitalName());
                    }
                }

                /* Add new panels to the end of the list */
                for (String key : keys) {
                    if (!knownKeys.contains(key)) {
                        int row = index / columnCount;
                        int col = index % columnCount;

                        /* Add a new panel */
                        VitalDisplayPanel panel = new VitalDisplayPanel(key, this.data.get(key).toString());
                        this.panels.add(panel);
                        this.add(panel);
                        panel.setBounds(insets.left + columnBounds[col], insets.top + row * this.defaultVDPanelHeight, columnBounds[col + 1] - columnBounds[col], this.defaultVDPanelHeight);

                        /* Update index */
                        index++;
                    }
                }
            } else {
                if (!this.panels.isEmpty()) {
                    /* Clear grid */
                    this.removeAll();
                    this.panels.clear();
                }
            }

            Dimension size = this.getPreferredSize();
            if (this.data != null && !this.data.isEmpty()) {
                size.height = ((this.data.size() - 1) / columnCount + 1) * this.defaultVDPanelHeight;
                size.height += insets.top + insets.bottom;
            } else {
                size.height = insets.top + insets.bottom;
            }
            this.setPreferredSize(size);

            this.validate();
            this.repaint();
            this.arrangeGridInProgress = false;
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

        setBackground(new java.awt.Color(105, 105, 105));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        this.invoke_arrangeGrid();
    }//GEN-LAST:event_formComponentResized
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return this.getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
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
