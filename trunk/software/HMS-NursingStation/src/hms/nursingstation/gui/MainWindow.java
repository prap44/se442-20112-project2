/*
 * MainWindow.java
 *
 * Created on Feb 17, 2012, 5:12:33 PM
 */
package hms.nursingstation.gui;

import hms.nursingstation.MonitorProxy;
import hms.nursingstation.NursingStationImpl;
import hms.nursingstation.events.AlarmReceivedEvent;
import hms.nursingstation.events.AlarmResetEvent;
import hms.nursingstation.events.CallButtonReceivedEvent;
import hms.nursingstation.events.CallButtonResetEvent;
import hms.nursingstation.events.DataReceivedEvent;
import hms.nursingstation.events.InformationChangeReceivedEvent;
import hms.nursingstation.events.MonitorStatusChangedEvent;
import hms.nursingstation.listeners.AlarmReceivedListener;
import hms.nursingstation.listeners.AlarmResetListener;
import hms.nursingstation.listeners.CallButtonReceivedListener;
import hms.nursingstation.listeners.CallButtonResetListener;
import hms.nursingstation.listeners.DataReceivedListener;
import hms.nursingstation.listeners.InformationChangeReceivedListener;
import hms.nursingstation.listeners.MonitorStatusChangedListener;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class MainWindow extends javax.swing.JFrame {

    private NursingStationImpl nursingStation = null;
    private DefaultListModel loggingListModel = new DefaultListModel();
    private MonitorNotificationDialog notificationDialog;
    
    public MainWindow(NursingStationImpl nursingStation) {
        initComponents();
        this.loggingList.setModel(loggingListModel);
        this.notificationDialog = new MonitorNotificationDialog(this, true);
        this.setNursingStation(nursingStation);
    }

    /** Creates new form MainWindow */
    public MainWindow() {
        initComponents();
        this.loggingList.setModel(loggingListModel);
        this.notificationDialog = new MonitorNotificationDialog(this, true);
        this.setNursingStation(new NursingStationImpl());
    }
    
    private void setNursingStation(NursingStationImpl nursingStation) {
        if (nursingStation == null) {
            this.nursingStation = new NursingStationImpl();
        } else {
            this.nursingStation = nursingStation;
        }

        this.nursingStation.addMonitorStatusChangedListener(new MonitorStatusChangedListener() {
            @Override
            public void monitorStatusChanged(final MonitorStatusChangedEvent event) {
            	SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						MonitorProxy monitor = event.getMonitor();
		                switch (event.getOperation()) {
		                    case ADDED:
		                        monitor.addAlarmReceivedListener(new AlarmReceivedListener() {
		                            @Override
		                            public void alarmReceived(AlarmReceivedEvent event) {
		                                /* Log alarm */
		                                MainWindow.this.loggingListModel.addElement("Alarm received");
		                                MainWindow.this.notificationDialog.addNotification(event.getMonitor(), MonitorNotificationDialog.NotificationType.ALARM, event.getVital(), event.generateMessage());
		                            }
		                        });

		                        monitor.addAlarmResetListener(new AlarmResetListener() {
		                            @Override
		                            public void alarmReset(AlarmResetEvent event) {
		                                /* Log reset */
		                                MainWindow.this.loggingListModel.addElement("Alarm reset");
		                                MainWindow.this.notificationDialog.removeNotification(event.getMonitor(), MonitorNotificationDialog.NotificationType.ALARM, event.getVital());
		                            }
		                        });
		                        
		                        monitor.addCallButtonReceivedListener(new CallButtonReceivedListener() {
		                            @Override
		                            public void callButtonRequestReceived(CallButtonReceivedEvent event) {
		                                /* Log call button push */
		                                MainWindow.this.loggingListModel.addElement("Call button pushed");
		                                MainWindow.this.notificationDialog.addNotification(event.getMonitor(), MonitorNotificationDialog.NotificationType.REQUEST, null, event.generateMessage());
		                            }
		                        });
		                        
		                        monitor.addCallButtonResetListener(new CallButtonResetListener() {
		                            @Override
		                            public void callButtonRequestReset(CallButtonResetEvent event) {
		                                /* Log call button reset */
		                                MainWindow.this.loggingListModel.addElement("Call button reset");
		                                MainWindow.this.notificationDialog.removeNotification(event.getMonitor(), MonitorNotificationDialog.NotificationType.REQUEST, null);
		                            }
		                        });

		                        monitor.addDataReceivedListener(new DataReceivedListener() {
		                            @Override
		                            public void dataReceived(DataReceivedEvent event) {
		                            }
		                        });
		                        
		                        monitor.addInformationChangeReceivedListener(new InformationChangeReceivedListener() {
		                            @Override
		                            public void informationChangeReceived(InformationChangeReceivedEvent event) {
		                                
		                            }
		                        });
		                        break;
		                    case REMOVED:
		                        break;
		                }
					}
				});
            }
        });
        
//        try {
//            nursingStation.addMonitor(new MonitorProxy());
//            nursingStation.addMonitor(new MonitorProxy());
//        } catch (IOException ex) {
//            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }

        this.monitorDisplayPanelList.setNursingStation(this.nursingStation);
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
        basePanelSpliltPanel = new javax.swing.JSplitPane();
        monitoringBasePanel = new javax.swing.JPanel();
        monitorDisplayPanelList = new hms.nursingstation.gui.MonitorDisplayPanelList();
        monitoringButtonPanel = new javax.swing.JPanel();
        addMonitorButton = new javax.swing.JButton();
        monitoringButtonPanelSpacer = new javax.swing.JPanel();
        loggingBasePanel = new javax.swing.JPanel();
        loggingScrollPanel = new javax.swing.JScrollPane();
        loggingList = new javax.swing.JList();

        setTitle("Nursing Station");
        setMinimumSize(new java.awt.Dimension(300, 200));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        basePanel.setMinimumSize(new java.awt.Dimension(400, 400));
        basePanel.setPreferredSize(new java.awt.Dimension(600, 400));
        basePanel.setLayout(new java.awt.GridBagLayout());

        basePanelSpliltPanel.setDividerLocation(300);
        basePanelSpliltPanel.setResizeWeight(0.5);

        monitoringBasePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Monitor"));
        monitoringBasePanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        monitoringBasePanel.add(monitorDisplayPanelList, gridBagConstraints);

        monitoringButtonPanel.setLayout(new java.awt.GridBagLayout());

        addMonitorButton.setText("Add");
        addMonitorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMonitorButtonActionPerformed(evt);
            }
        });
        monitoringButtonPanel.add(addMonitorButton, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        monitoringButtonPanel.add(monitoringButtonPanelSpacer, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        monitoringBasePanel.add(monitoringButtonPanel, gridBagConstraints);

        basePanelSpliltPanel.setLeftComponent(monitoringBasePanel);

        loggingBasePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "System Log"));
        loggingBasePanel.setPreferredSize(new java.awt.Dimension(150, 152));
        loggingBasePanel.setLayout(new java.awt.GridBagLayout());

        loggingScrollPanel.setViewportView(loggingList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        loggingBasePanel.add(loggingScrollPanel, gridBagConstraints);

        basePanelSpliltPanel.setRightComponent(loggingBasePanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        basePanel.add(basePanelSpliltPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(basePanel, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
    }// </editor-fold>//GEN-END:initComponents

    private void addMonitorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMonitorButtonActionPerformed
        this.monitorDisplayPanelList.addMonitorRequest();
    }//GEN-LAST:event_addMonitorButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        for(int i = 0; i < this.nursingStation.getMonitorCount(); i++) {
            try {
                this.nursingStation.getMonitor(i).disconnectFromMonitor();
            } catch (RemoteException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Initialize RMI interface */
        System.setSecurityManager(new RMISecurityManager());
        
        /* Initialize nursing station */
        final NursingStationImpl nursingStation = new NursingStationImpl();
        
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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow(nursingStation);
                window.setState(JFrame.MAXIMIZED_BOTH);
                window.setVisible(true);

                synchronized (window.getTreeLock()) {
                    window.validateTree();
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMonitorButton;
    private javax.swing.JPanel basePanel;
    private javax.swing.JSplitPane basePanelSpliltPanel;
    private javax.swing.JPanel loggingBasePanel;
    private javax.swing.JList loggingList;
    private javax.swing.JScrollPane loggingScrollPanel;
    private hms.nursingstation.gui.MonitorDisplayPanelList monitorDisplayPanelList;
    private javax.swing.JPanel monitoringBasePanel;
    private javax.swing.JPanel monitoringButtonPanel;
    private javax.swing.JPanel monitoringButtonPanelSpacer;
    // End of variables declaration//GEN-END:variables
}
