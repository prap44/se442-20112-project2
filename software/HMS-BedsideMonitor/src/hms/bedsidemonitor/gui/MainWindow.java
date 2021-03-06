/*
 * MainWindow.java
 *
 * Created on Feb 18, 2012, 4:14:05 PM
 */
package hms.bedsidemonitor.gui;

import hms.bedsidemonitor.MonitorImpl;
import hms.bedsidemonitor.PatientImpl;
import hms.common.Patient;
import hms.common.events.PatientDataEvent;
import hms.common.listeners.PatientDataListener;
import hms.common.events.PatientInformationChangedEvent;
import hms.common.Sensor;
import hms.common.events.PatientAlarmEvent;
import hms.common.events.PatientCallButtonEvent;
import hms.common.listeners.PatientInformationChangedListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jackson Lamp
 */
public class MainWindow extends javax.swing.JFrame {

    private EditSensorDialog editSensorDialog;
    private List<Sensor> sensorList = new ArrayList<Sensor>();
    private DefaultTableModel sensorTableModel;
    private MonitorImpl monitor = null;
    private PatientImpl patient = new PatientImpl();
    private boolean suppressSensorTableModelSelectionEvents = false;
    private boolean callNurseActive = false;

    public MainWindow(MonitorImpl monitor) throws RemoteException {
        initComponents();

        if (monitor != null) {
            this.monitor = monitor;
        } else {
            this.monitor = new MonitorImpl();
        }

        try {
            this.postInit();
        } catch (Throwable t) {
        }
    }

    /** Creates new form MainWindow */
    public MainWindow() {
        initComponents();

        this.monitor = new MonitorImpl();

        try {
            this.postInit();
        } catch (Throwable t) {
        }
    }

    private void postInit() throws RemoteException {
        /* Create the edit sensor dialog */
        this.editSensorDialog = new EditSensorDialog(this, true);

        if (monitor != null) {
            this.sensorList = this.monitor.getSensorList();
            this.monitor.addPatientDataListener(new PatientDataListener() {

                private Runnable updateTableRunnable = new Runnable() {

                    @Override
                    public void run() {
                        MainWindow.this.updateTableValues();
                        MainWindow.this.updateButtonStatus();
                    }
                };

                @Override
                public void patientDataReceived(PatientDataEvent event) throws RemoteException {
                    SwingUtilities.invokeLater(this.updateTableRunnable);
                }
            });
            
            this.monitor.addPatientInformationChangedListener(new PatientInformationChangedListener() {
                @Override
                public void patientInformationChanged(PatientInformationChangedEvent event) throws RemoteException {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                boolean patientAssigned = MainWindow.this.monitor.getPatient() != null;
                                MainWindow.this.patientAssignedCheckbox.setSelected(patientAssigned);
                                if (patientAssigned) {
                                    MainWindow.this.patientFirstNameField.setText(MainWindow.this.monitor.getPatient().getPatientFirstName());
                                    MainWindow.this.patientMiddleNameField.setText(MainWindow.this.monitor.getPatient().getPatientMiddleName());
                                    MainWindow.this.patientLastNameField.setText(MainWindow.this.monitor.getPatient().getPatientLastName());
                                }
                                MainWindow.this.updatePatientFieldStatus();
                            } catch (RemoteException ex) {
                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
            });
            
            this.monitorIDField.setText(this.monitor.getID());
            this.monitorAddressField.setText("127.0.0.1:1099");
        }

        this.sensorTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!MainWindow.this.suppressSensorTableModelSelectionEvents) {
                    MainWindow.this.updateButtonStatus();
                }
            }
        });

        /* Get the sensor's table model for easy access */
        this.sensorTableModel = (DefaultTableModel) this.sensorTable.getModel();

        /* Make sure everything is up to date */
        this.updateTable();
        this.updateButtonStatus();
        this.updatePatientFieldStatus();
    }

    private void updateTable() {
        int selectedRow = this.sensorTable.getSelectedRow();
        this.sensorTableModel.setRowCount(0);

        for (Sensor s : this.sensorList) {
            Object[] row = new Object[6];
            row[0] = s.getName();
            row[1] = s.convert(s.getCurrentValue());
            row[2] = s.getCurrentValue();
            row[3] = s.getLowLimit();
            row[4] = s.getHighLimit();
            row[5] = s.getAlarmState();
            this.sensorTableModel.addRow(row);
        }

        if(selectedRow < this.sensorTableModel.getRowCount()) {
            this.sensorTable.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
        } else {
            selectedRow = this.sensorTableModel.getRowCount() - 1;
            this.sensorTable.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
        }
        this.sensorTable.validate();
    }

    private void updateTableValues() {
        this.suppressSensorTableModelSelectionEvents = true;

        for (int i = 0; i < this.sensorList.size(); ++i) {
            Sensor s = this.sensorList.get(i);
            this.sensorTableModel.setValueAt(s.getName(), i, 0);
            this.sensorTableModel.setValueAt(s.convert(s.getCurrentValue()), i, 1);
            this.sensorTableModel.setValueAt(s.getCurrentValue(), i, 2);
            this.sensorTableModel.setValueAt(s.getLowLimit(), i, 3);
            this.sensorTableModel.setValueAt(s.getHighLimit(), i, 4);
            this.sensorTableModel.setValueAt(s.getAlarmState(), i, 5);
        }

        this.sensorTable.repaint();
        this.suppressSensorTableModelSelectionEvents = false;
    }

    private void updateButtonStatus() {
        int selectedRow = this.sensorTable.getSelectedRow();
        boolean sensorButtonsEnabled = selectedRow >= 0 && !this.sensorList.isEmpty();
        boolean alarmResetButtonEnabled = false;
        if (selectedRow < this.sensorList.size()) {
            alarmResetButtonEnabled = sensorButtonsEnabled
                    && this.sensorList.get(selectedRow).getAlarmState();
        }
        this.editSensorButton.setEnabled(sensorButtonsEnabled);
        this.removeSensorButton.setEnabled(sensorButtonsEnabled);
        this.resetAlarmButton.setEnabled(alarmResetButtonEnabled);
        this.callNurseButton.setEnabled(!callNurseActive);
        this.resetCallNurseButton.setEnabled(callNurseActive);
    }

    private void updatePatientFieldStatus() {
        boolean patientFieldsEnabled = this.patientAssignedCheckbox.isSelected();
        this.patientFirstNameField.setEnabled(patientFieldsEnabled);
        this.patientMiddleNameField.setEnabled(patientFieldsEnabled);
        this.patientLastNameField.setEnabled(patientFieldsEnabled);
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
        monitorInfoPanel = new javax.swing.JPanel();
        monitorIDLabel = new javax.swing.JLabel();
        monitorIDField = new javax.swing.JTextField();
        monitorAddressLabel = new javax.swing.JLabel();
        monitorAddressField = new javax.swing.JTextField();
        monitorInfoButtonPanel = new javax.swing.JPanel();
        monitorInfoButtonSpacerPanel = new javax.swing.JPanel();
        monitorInfoApplyButton = new javax.swing.JButton();
        monitorInfoResetButton = new javax.swing.JButton();
        patientInfoPanel = new javax.swing.JPanel();
        patientAssignedCheckbox = new javax.swing.JCheckBox();
        patientFirstNameLabel = new javax.swing.JLabel();
        patientFirstNameField = new javax.swing.JTextField();
        patientMiddleNameLabel = new javax.swing.JLabel();
        patientMiddleNameField = new javax.swing.JTextField();
        patientLastNameLabel = new javax.swing.JLabel();
        patientLastNameField = new javax.swing.JTextField();
        patientInfoButtonPanel = new javax.swing.JPanel();
        patientInfoButtonSpacerPanel = new javax.swing.JPanel();
        patientInfoApplyButton = new javax.swing.JButton();
        patientInfoResetButton = new javax.swing.JButton();
        sensorPanel = new javax.swing.JPanel();
        sensorScrollPanel = new javax.swing.JScrollPane();
        sensorTable = new javax.swing.JTable();
        sensorButtonPanel = new javax.swing.JPanel();
        addSensorButton = new javax.swing.JButton();
        removeSensorButton = new javax.swing.JButton();
        editSensorButton = new javax.swing.JButton();
        resetAlarmButton = new javax.swing.JButton();
        patientInterfacePanel = new javax.swing.JPanel();
        callNurseButton = new javax.swing.JButton();
        resetCallNurseButton = new javax.swing.JButton();

        setTitle("Bedside Monitor");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        basePanel.setPreferredSize(new java.awt.Dimension(464, 500));
        basePanel.setLayout(new java.awt.GridBagLayout());

        monitorInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Monitor Information"));
        monitorInfoPanel.setLayout(new java.awt.GridBagLayout());

        monitorIDLabel.setText("ID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        monitorInfoPanel.add(monitorIDLabel, gridBagConstraints);

        monitorIDField.setEditable(false);
        monitorIDField.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        monitorInfoPanel.add(monitorIDField, gridBagConstraints);

        monitorAddressLabel.setText("Address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        monitorInfoPanel.add(monitorAddressLabel, gridBagConstraints);

        monitorAddressField.setEditable(false);
        monitorAddressField.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        monitorInfoPanel.add(monitorAddressField, gridBagConstraints);

        monitorInfoButtonPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        monitorInfoButtonPanel.add(monitorInfoButtonSpacerPanel, gridBagConstraints);

        monitorInfoApplyButton.setText("Apply");
        monitorInfoApplyButton.setEnabled(false);
        monitorInfoApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monitorInfoApplyButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        monitorInfoButtonPanel.add(monitorInfoApplyButton, gridBagConstraints);

        monitorInfoResetButton.setText("Reset");
        monitorInfoResetButton.setEnabled(false);
        monitorInfoResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monitorInfoResetButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        monitorInfoButtonPanel.add(monitorInfoResetButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        monitorInfoPanel.add(monitorInfoButtonPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        basePanel.add(monitorInfoPanel, gridBagConstraints);

        patientInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Patient Information"));
        patientInfoPanel.setLayout(new java.awt.GridBagLayout());

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
        patientInfoPanel.add(patientAssignedCheckbox, gridBagConstraints);

        patientFirstNameLabel.setText("First Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        patientInfoPanel.add(patientFirstNameLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        patientInfoPanel.add(patientFirstNameField, gridBagConstraints);

        patientMiddleNameLabel.setText("Middle Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        patientInfoPanel.add(patientMiddleNameLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        patientInfoPanel.add(patientMiddleNameField, gridBagConstraints);

        patientLastNameLabel.setText("Last Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 4, 4);
        patientInfoPanel.add(patientLastNameLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        patientInfoPanel.add(patientLastNameField, gridBagConstraints);

        patientInfoButtonPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        patientInfoButtonPanel.add(patientInfoButtonSpacerPanel, gridBagConstraints);

        patientInfoApplyButton.setText("Apply");
        patientInfoApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientInfoApplyButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        patientInfoButtonPanel.add(patientInfoApplyButton, gridBagConstraints);

        patientInfoResetButton.setText("Reset");
        patientInfoResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientInfoResetButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        patientInfoButtonPanel.add(patientInfoResetButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 4);
        patientInfoPanel.add(patientInfoButtonPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        basePanel.add(patientInfoPanel, gridBagConstraints);

        sensorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Sensors"));
        sensorPanel.setLayout(new java.awt.GridBagLayout());

        sensorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Vital", "Value", "Raw Value", "Low Thresh.", "High Thresh.", "Alarm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sensorTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        sensorTable.getTableHeader().setReorderingAllowed(false);
        sensorScrollPanel.setViewportView(sensorTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        sensorPanel.add(sensorScrollPanel, gridBagConstraints);

        sensorButtonPanel.setLayout(new java.awt.GridBagLayout());

        addSensorButton.setText("Add");
        addSensorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSensorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        sensorButtonPanel.add(addSensorButton, gridBagConstraints);

        removeSensorButton.setText("Remove");
        removeSensorButton.setEnabled(false);
        removeSensorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSensorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        sensorButtonPanel.add(removeSensorButton, gridBagConstraints);

        editSensorButton.setText("Edit");
        editSensorButton.setEnabled(false);
        editSensorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSensorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        sensorButtonPanel.add(editSensorButton, gridBagConstraints);

        resetAlarmButton.setText("Reset");
        resetAlarmButton.setEnabled(false);
        resetAlarmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetAlarmButtonActionPerformed(evt);
            }
        });
        sensorButtonPanel.add(resetAlarmButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        sensorPanel.add(sensorButtonPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        basePanel.add(sensorPanel, gridBagConstraints);

        patientInterfacePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Patient Interface"));
        patientInterfacePanel.setLayout(new java.awt.GridBagLayout());

        callNurseButton.setText("Call Nurse");
        callNurseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callNurseButtonActionPerformed(evt);
            }
        });
        patientInterfacePanel.add(callNurseButton, new java.awt.GridBagConstraints());

        resetCallNurseButton.setText("Reset");
        resetCallNurseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetCallNurseButtonActionPerformed(evt);
            }
        });
        patientInterfacePanel.add(resetCallNurseButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        basePanel.add(patientInterfacePanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(basePanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void patientAssignedCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientAssignedCheckboxActionPerformed
        updatePatientFieldStatus();
    }//GEN-LAST:event_patientAssignedCheckboxActionPerformed

    private void addSensorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSensorButtonActionPerformed
        Sensor sensor = this.editSensorDialog.showAddDialog();

        if (sensor != null) {
            sensorList.add(sensor);
            this.updateTable();
        }
        this.updateButtonStatus();
    }//GEN-LAST:event_addSensorButtonActionPerformed

    private void removeSensorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSensorButtonActionPerformed
        int selectedIndex = this.sensorTable.getSelectedRow();
        if (selectedIndex >= 0) {
            this.sensorList.remove(selectedIndex);
            this.updateTable();
        }
        this.updateButtonStatus();
    }//GEN-LAST:event_removeSensorButtonActionPerformed

    private void editSensorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSensorButtonActionPerformed
        int selectedIndex = this.sensorTable.getSelectedRow();
        if (selectedIndex >= 0) {
            Sensor sensor = this.sensorList.get(selectedIndex);

            if (this.editSensorDialog.showEditDialog(sensor)) {
                this.updateTable();
            }
        }
        this.updateButtonStatus();
    }//GEN-LAST:event_editSensorButtonActionPerformed

    private void patientInfoApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientInfoApplyButtonActionPerformed
        try {
            if (this.patientAssignedCheckbox.isSelected()) {
                this.patient.setPatientFirstName(this.patientFirstNameField.getText());
                this.patient.setPatientMiddleName(this.patientMiddleNameField.getText());
                this.patient.setPatientLastName(this.patientLastNameField.getText());
                this.monitor.setPatient(this.patient);
            } else {
                this.monitor.setPatient(null);
                this.patient.setPatientFirstName(this.patientFirstNameField.getText());
                this.patient.setPatientMiddleName(this.patientMiddleNameField.getText());
                this.patient.setPatientLastName(this.patientLastNameField.getText());
            }
            this.monitor.raisePatientInformationChangedEvent(new PatientInformationChangedEvent());
        } catch (RemoteException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_patientInfoApplyButtonActionPerformed

    private void patientInfoResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientInfoResetButtonActionPerformed
        try {
            Patient localPatient = this.monitor.getPatient();
            if (localPatient == null) {
                this.patientAssignedCheckbox.setSelected(false);
                this.updatePatientFieldStatus();
                this.patientFirstNameField.setText(this.patient.getPatientFirstName());
                this.patientMiddleNameField.setText(this.patient.getPatientMiddleName());
                this.patientLastNameField.setText(this.patient.getPatientLastName());
            } else {
                if (localPatient instanceof PatientImpl) {
                    this.patient = (PatientImpl) localPatient;
                }
                this.patientAssignedCheckbox.setSelected(true);
                this.updatePatientFieldStatus();
                this.patientFirstNameField.setText(this.patient.getPatientFirstName());
                this.patientMiddleNameField.setText(this.patient.getPatientMiddleName());
                this.patientLastNameField.setText(this.patient.getPatientLastName());
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_patientInfoResetButtonActionPerformed

    private void monitorInfoApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monitorInfoApplyButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monitorInfoApplyButtonActionPerformed

    private void monitorInfoResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monitorInfoResetButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monitorInfoResetButtonActionPerformed

    private void callNurseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_callNurseButtonActionPerformed
        try {
            this.callNurseActive = true;
            this.updateButtonStatus();
            this.monitor.raisePatientCallButtonEvent(new PatientCallButtonEvent(true));
        } catch (RemoteException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_callNurseButtonActionPerformed

    private void resetCallNurseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetCallNurseButtonActionPerformed
        try {
            this.callNurseActive = false;
            this.updateButtonStatus();
            this.monitor.raisePatientCallButtonEvent(new PatientCallButtonEvent(false));
        } catch (RemoteException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_resetCallNurseButtonActionPerformed

    private void resetAlarmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetAlarmButtonActionPerformed
        if (this.sensorTable.getSelectedRow() >= 0) {
            Sensor sensor = this.sensorList.get(this.sensorTable.getSelectedRow());
            sensor.setAlarmState(false);
            this.updateTableValues();
            try {
                this.monitor.raisePatientAlarmEvent(new PatientAlarmEvent(sensor.getName(), false));
            } catch (RemoteException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.updateButtonStatus();
        }
    }//GEN-LAST:event_resetAlarmButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            this.monitor.unbind();
        } catch (RemoteException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Initialize RMI interface */
        System.setSecurityManager(new RMISecurityManager());
        final MonitorImpl monitor = new MonitorImpl();

        try {
            UnicastRemoteObject.exportObject(monitor);
            monitor.rebind("hms.bedsidemonitor");
        } catch (MalformedURLException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

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

            public void run() {
                try {
                    MainWindow window = new MainWindow(monitor);
                    window.setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSensorButton;
    private javax.swing.JPanel basePanel;
    private javax.swing.JButton callNurseButton;
    private javax.swing.JButton editSensorButton;
    private javax.swing.JTextField monitorAddressField;
    private javax.swing.JLabel monitorAddressLabel;
    private javax.swing.JTextField monitorIDField;
    private javax.swing.JLabel monitorIDLabel;
    private javax.swing.JButton monitorInfoApplyButton;
    private javax.swing.JPanel monitorInfoButtonPanel;
    private javax.swing.JPanel monitorInfoButtonSpacerPanel;
    private javax.swing.JPanel monitorInfoPanel;
    private javax.swing.JButton monitorInfoResetButton;
    private javax.swing.JCheckBox patientAssignedCheckbox;
    private javax.swing.JTextField patientFirstNameField;
    private javax.swing.JLabel patientFirstNameLabel;
    private javax.swing.JButton patientInfoApplyButton;
    private javax.swing.JPanel patientInfoButtonPanel;
    private javax.swing.JPanel patientInfoButtonSpacerPanel;
    private javax.swing.JPanel patientInfoPanel;
    private javax.swing.JButton patientInfoResetButton;
    private javax.swing.JPanel patientInterfacePanel;
    private javax.swing.JTextField patientLastNameField;
    private javax.swing.JLabel patientLastNameLabel;
    private javax.swing.JTextField patientMiddleNameField;
    private javax.swing.JLabel patientMiddleNameLabel;
    private javax.swing.JButton removeSensorButton;
    private javax.swing.JButton resetAlarmButton;
    private javax.swing.JButton resetCallNurseButton;
    private javax.swing.JPanel sensorButtonPanel;
    private javax.swing.JPanel sensorPanel;
    private javax.swing.JScrollPane sensorScrollPanel;
    private javax.swing.JTable sensorTable;
    // End of variables declaration//GEN-END:variables
}
