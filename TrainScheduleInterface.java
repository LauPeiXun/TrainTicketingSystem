/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author peixun doneeee
 */

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TrainScheduleInterface extends javax.swing.JFrame {
    
    TrainSchedule trainSchedule = new TrainSchedule();

    private TrainScheduleInterface() {
        initComponents();
    }

    public static TrainScheduleInterface createInstance() {
        return new TrainScheduleInterface();
    }

    private void createTrainFile() {
        try {
            File train = new File("TrainInfo.txt");
            if (train.createNewFile()) {
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    } //done

    private void writeTrainToFile() {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        trainSchedule.setTrainID(trainIDfield.getText());
        trainSchedule.setTrainType((String) trainTypeField.getSelectedItem());
        trainSchedule.setFrom(fromField.getText());
        trainSchedule.setTo(toField.getText());
        trainSchedule.setDate(dateFormat.format(trainDate.getDate()));
        trainSchedule.setStartTime(startTimeField.getText());
        trainSchedule.setEndTime(endTimeField.getText());
        trainSchedule.setSeat(seatsField.getText());
        trainSchedule.setAdultPrice(adultPriceField.getText());
        trainSchedule.setChildrenElderPrice(childrenElderPriceField.getText());
        trainSchedule.setAdminID(adminIDfield.getText());
        trainSchedule.setAdminName(adminNameField.getText());

        boolean isDuplicate = false;
        boolean adminMatch = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("TrainInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\\|");
                if (columns.length == 13) {
                    String existID = columns[0];
                    if (existID.equals(trainSchedule.getTrainID())) {
                        isDuplicate = true;
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while reading from the TrainInfo file.");
        }

        try (BufferedReader adminReader = new BufferedReader(new FileReader("admin.txt"))) {
            String line;
            while ((line = adminReader.readLine()) != null) {
                String[] columns = line.split("\\|");
                if (columns.length >= 3 && columns[0].equals(trainSchedule.getAdminID()) && columns[2].equals(trainSchedule.getAdminName())) {
                    adminMatch = true;
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while reading from the admin file.");
        }

        if (!isDuplicate && adminMatch) {
            String trainID = trainSchedule.getTrainID();
            String trainType = trainSchedule.getTrainType();
            String from = trainSchedule.getFrom();
            String to = trainSchedule.getTo();
            String date = trainSchedule.getDate();
            String startTime = trainSchedule.getStartTime();
            String endTime = trainSchedule.getEndTime();
            String seat = trainSchedule.getSeat();
            String adultPrice = trainSchedule.getAdultPrice();
            String childrenElderPrice = trainSchedule.getChildrenElderPrice();
            String adminID = trainSchedule.getAdminID();
            String adminName = trainSchedule.getAdminName();  

        try (FileWriter writeTrain = new FileWriter("TrainInfo.txt", true)) {
                String dataToWrite = trainID + "|" + trainType + "|" + from + "|" + to + "|" + date + "|" + startTime + "|" + endTime + "|" + seat + "|" + adultPrice + "|" + childrenElderPrice + "|" + adminID + "|" + adminName + "|" + seat + "\n";
                writeTrain.write(dataToWrite);
                JOptionPane.showMessageDialog(null, "Train Created Successfully!");
                System.out.println("Successfully wrote to the TrainInfo file.");
                clearText();
            } catch (IOException ex) {
                System.out.println("An error occurred while writing to the TrainInfo file.");
            }
        } else {
            if (isDuplicate) {
                JOptionPane.showMessageDialog(null, "Train code already exists.");
            } else if (!adminMatch) {
                JOptionPane.showMessageDialog(null, "Admin ID and Name do not match the records.");
            }
        }
    } //done
    
    private void clearText() {
        trainIDfield.setText("");
        trainIDfield.setEditable(true);
        trainTypeField.setSelectedItem("----select----");
        fromField.setText("");
        toField.setText("");
        trainDate.setDate(null);
        startTimeField.setText("");
        endTimeField.setText("");
        seatsField.setText("");
        adultPriceField.setText("");
        childrenElderPriceField.setText("");
        adminIDfield.setText("");
        adminNameField.setText("");
        seatsField.setBackground(Color.white);
        seatsField.setEditable(true);
    } //done

    private void displayTrainInfoInTable() {
        DefaultTableModel trainTablex = (DefaultTableModel) trainTable.getModel();
        trainTablex.setRowCount(0);

        boolean recordsFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("TrainInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\\|");
                if (columns.length == 13) {
                    trainTablex.addRow(columns);
                    recordsFound = true;
                }
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while reading from the file.");
        }
        if (!recordsFound) {
            JOptionPane.showMessageDialog(null, "No records found.");
        }
    } //done

    private void whenClickTable() {
        DefaultTableModel trainTablex = (DefaultTableModel) trainTable.getModel();
        int selectedRow = trainTable.getSelectedRow();

        if (selectedRow >= 0) {
            String trainIDfieldx = trainTablex.getValueAt(selectedRow, 0).toString();
            String trainTypeFieldx = trainTablex.getValueAt(selectedRow, 1).toString();
            String fromFieldx = trainTablex.getValueAt(selectedRow, 2).toString();
            String toFieldx = trainTablex.getValueAt(selectedRow, 3).toString();
            String dateFieldx = trainTablex.getValueAt(selectedRow, 4).toString();
            String startTimeFieldx = trainTablex.getValueAt(selectedRow, 5).toString();
            String endTimeFieldx = trainTablex.getValueAt(selectedRow, 6).toString();
            String seatsFieldx = trainTablex.getValueAt(selectedRow, 7).toString();
            String adultPrice = trainTablex.getValueAt(selectedRow, 8).toString();
            String childrenElderPrice = trainTablex.getValueAt(selectedRow, 9).toString();

            TrainSchedule selectedTrain = new TrainSchedule();
            selectedTrain.setTrainID(trainIDfieldx);
            selectedTrain.setTrainType(trainTypeFieldx);
            selectedTrain.setFrom(fromFieldx);
            selectedTrain.setTo(toFieldx);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date selectedDate = dateFormat.parse(dateFieldx);
                selectedTrain.setDate(dateFormat.format(selectedDate));
            } catch (ParseException e) {
                System.out.println("Error date");
            }

            selectedTrain.setStartTime(startTimeFieldx);
            selectedTrain.setEndTime(endTimeFieldx);
            selectedTrain.setSeat(seatsFieldx);
            selectedTrain.setAdultPrice(adultPrice);
            selectedTrain.setChildrenElderPrice(childrenElderPrice);

            trainSchedule = selectedTrain;

            trainIDfield.setText(trainSchedule.getTrainID());
            trainIDfield.setEditable(false);
            trainTypeField.setSelectedItem(trainSchedule.getTrainType());
            fromField.setText(trainSchedule.getFrom());
            toField.setText(trainSchedule.getTo());

            String selectedDateStr = trainSchedule.getDate();
            if (selectedDateStr != null) {
                try {
                    Date selectedDate = dateFormat.parse(selectedDateStr);
                    trainDate.setDate(selectedDate);
                } catch (ParseException e) {
                    System.out.println("Error parsing date");
                }
            }

            startTimeField.setText(trainSchedule.getStartTime());
            endTimeField.setText(trainSchedule.getEndTime());
            seatsField.setText(trainSchedule.getSeat());
            seatsField.setBackground(Color.LIGHT_GRAY);
            seatsField.setEditable(false);
            adultPriceField.setText(trainSchedule.getAdultPrice());
            childrenElderPriceField.setText(trainSchedule.getChildrenElderPrice());
        }
    } //done

    private void deleteTrainInfo() {
        int selectedRow = trainTable.getSelectedRow();

        if (selectedRow >= 0) {
            int confirmPane = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirmPane == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) trainTable.getModel();
                model.removeRow(selectedRow);

                try {
                    List<String> lines = Files.readAllLines(Paths.get("TrainInfo.txt"));

                    lines.remove(selectedRow);

                    Files.write(Paths.get("TrainInfo.txt"), lines, StandardCharsets.UTF_8);

                    JOptionPane.showMessageDialog(null, "Record Deleted Successfully!");
                    clearText();
                } catch (IOException ex) {
                    System.out.println("An error occurred while deleting the record.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to delete.");
        }
    } //done

    private void modifyTrainInfo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String trainIDToUpdate = trainIDfield.getText();
        trainIDfield.setEditable(true);

        boolean modified = false;
        boolean adminMatch = false;

        TrainSchedule modifiedTrainSchedule = new TrainSchedule();

        try (BufferedReader reader = new BufferedReader(new FileReader("TrainInfo.txt"))) {
            StringBuilder trainInfoFile = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 13 && parts[0].equals(trainIDToUpdate)) {
                    modifiedTrainSchedule.setTrainID(trainIDToUpdate);
                    modifiedTrainSchedule.setTrainType((String) trainTypeField.getSelectedItem());
                    modifiedTrainSchedule.setFrom(fromField.getText());
                    modifiedTrainSchedule.setTo(toField.getText());
                    modifiedTrainSchedule.setDate(dateFormat.format(trainDate.getDate()));
                    modifiedTrainSchedule.setStartTime(startTimeField.getText());
                    modifiedTrainSchedule.setEndTime(endTimeField.getText());
                    modifiedTrainSchedule.setSeat(seatsField.getText());
                    modifiedTrainSchedule.setAdultPrice(adultPriceField.getText());
                    modifiedTrainSchedule.setChildrenElderPrice(childrenElderPriceField.getText());
                    modifiedTrainSchedule.setAdminID(adminIDfield.getText());
                    modifiedTrainSchedule.setAdminName(adminNameField.getText());

                    // Convert modifiedTrainSchedule back to a string representation
                    line = modifiedTrainSchedule.getTrainID() + "|" +
                           modifiedTrainSchedule.getTrainType() + "|" +
                           modifiedTrainSchedule.getFrom() + "|" +
                           modifiedTrainSchedule.getTo() + "|" +
                           modifiedTrainSchedule.getDate() + "|" +
                           modifiedTrainSchedule.getStartTime() + "|" +
                           modifiedTrainSchedule.getEndTime() + "|" +
                           modifiedTrainSchedule.getSeat() + "|" +
                           modifiedTrainSchedule.getAdultPrice() + "|" +
                           modifiedTrainSchedule.getChildrenElderPrice() + "|" +
                           modifiedTrainSchedule.getAdminID() + "|" +
                           modifiedTrainSchedule.getAdminName() + "|" +
                           modifiedTrainSchedule.getSeat();

                    modified = true;
                }
                trainInfoFile.append(line).append("\n");
            }

            try (BufferedReader adminReader = new BufferedReader(new FileReader("admin.txt"))) {
                String adminLine;
                while ((adminLine = adminReader.readLine()) != null) {
                    String[] adminParts = adminLine.split("\\|");
                    if (adminParts.length >= 3 && adminParts[0].equals(adminIDfield.getText()) && adminParts[2].equals(adminNameField.getText())) {
                        adminMatch = true;
                        break;
                    }
                }
            } catch (IOException ex) {
                System.out.println("An error occurred while reading from the admin file.");
            }

            if (modified && adminMatch) {
                try (FileWriter writeTrain = new FileWriter("TrainInfo.txt")) {
                    writeTrain.write(trainInfoFile.toString());
                    JOptionPane.showMessageDialog(null, "Train Modified Successfully!");
                    clearText();
                    System.out.println("Successfully modified the file.");
                } catch (IOException ex) {
                    System.out.println("An error occurred.");
                }
            } else {
                if (!adminMatch) {
                    JOptionPane.showMessageDialog(null, "Admin ID and Name do not match the records.");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row from the table.");
                }
            }
        } catch (IOException ex) {
            System.out.println("An error occurred.");
        }
    } //done

    private boolean trainValidation() {
        List<String> invalidFields = new ArrayList<>();

        String regexTrainID = "^[A-Za-z]{2}\\d{4}$";
        if (!trainIDfield.getText().matches(regexTrainID)) {
            invalidFields.add("Train Code (example: PK1234)");
        }

        String trainType = (String) trainTypeField.getSelectedItem();
        if ("----select----".equals(trainType)) {
            invalidFields.add("Train Type");
        }

        String regexFrom = "^[A-Za-z]{2}[A-Za-z]*$";
        if (!fromField.getText().matches(regexFrom)) {
            invalidFields.add("Start Destination");
        }

        String regexTo = "^[A-Za-z]{2}[A-Za-z]*$";
        if (!toField.getText().matches(regexTo)) {
            invalidFields.add("End Destination");
        }

        Date date = trainDate.getDate();
        if (date == null) {
            invalidFields.add("Date");
        }

        String regexTime = "^(1[0-2]|0?[1-9]):[0-5][0-9][apAP][mM]$";
        Pattern timePattern = Pattern.compile(regexTime);

        if (!timePattern.matcher(startTimeField.getText()).matches()) {
            invalidFields.add("Start Time (example: 5:00am or 5:00PM)");
        }

        if (!timePattern.matcher(endTimeField.getText()).matches()) {
            invalidFields.add("End Time (example: 12:00am or 12:00PM)");
        }

        String seatsInput = seatsField.getText();
        if (!seatsInput.matches("\\d+") || Integer.parseInt(seatsInput) > 100) {
            invalidFields.add("Number of Seats (1-100)");
        }

        String adultPriceInput = adultPriceField.getText();
        if (!adultPriceInput.matches("^\\d+\\.\\d{2}$")) {
            invalidFields.add("Adult Price (example: 50.00)");
        }

        String childrenElderPriceInput = childrenElderPriceField.getText();
        if (!childrenElderPriceInput.matches("^\\d+\\.\\d{2}$")) {
            invalidFields.add("Children/Elder Price (example: 30.00)");
        }

        String adminIDInput = adminIDfield.getText();
        if (!adminIDInput.matches("^[A-Z]{2}\\d{4}$")) {
            invalidFields.add("Admin Code (example: AC1234)");
        }

        String regexAdminName = "^[A-Za-z-' ]+$";
        if (!adminNameField.getText().matches(regexAdminName)) {
            invalidFields.add("Admin Name");
        }
        if (!invalidFields.isEmpty()) {
            StringBuilder message = new StringBuilder("Please fill in valid values for the following fields:\n");
            for (int i = 0; i < invalidFields.size(); i++) {
                message.append(i + 1).append(". ").append(invalidFields.get(i)).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString());
            return false;
        }
        return true;
    } //done

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        trainIDfield = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        fromField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        toField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        trainDate = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        startTimeField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        endTimeField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        seatsField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        adultPriceField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        childrenElderPriceField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        adminIDfield = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        adminNameField = new javax.swing.JTextField();
        createButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        trainTypeField = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        trainTable = new javax.swing.JTable();
        modifyButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        view = new javax.swing.JButton();
        back = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("Train Details");

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel2.setText("Train ID                     :");

        trainIDfield.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        trainIDfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trainIDfieldMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setText("Train Type                 :");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel8.setText("From                          :");

        fromField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel5.setText("To                               :");

        toField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel9.setText("Date                           :");

        trainDate.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel10.setText("Start time                   :");

        startTimeField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel11.setText("End time                     :");

        endTimeField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel12.setText("Total Seats                  : ");

        seatsField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel13.setText("Adult Price                  :");

        adultPriceField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel14.setText("Children/Elder Price    :");

        childrenElderPriceField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel6.setText("Created By");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel4.setText("Admin ID                   :");

        adminIDfield.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel7.setText("Admin Name             :");

        adminNameField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        createButton.setText("Create");
        createButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear");
        clearButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        trainTypeField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----select----", "LRT", "MRT", "KTM", "Monorial" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(trainIDfield, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(41, 41, 41)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(toField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fromField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(adultPriceField, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                            .addComponent(childrenElderPriceField)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(trainTypeField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(endTimeField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(startTimeField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(trainDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(adminIDfield, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(adminNameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(seatsField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(72, 72, 72))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(trainIDfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(trainTypeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(fromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel5))
                    .addComponent(toField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trainDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(startTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(endTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(seatsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(adultPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(childrenElderPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(adminIDfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(adminNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel2.getAccessibleContext().setAccessibleName("");
        jLabel3.getAccessibleContext().setAccessibleName("");
        jLabel8.getAccessibleContext().setAccessibleName("");
        jLabel5.getAccessibleContext().setAccessibleName("");
        jLabel9.getAccessibleContext().setAccessibleName("");
        jLabel10.getAccessibleContext().setAccessibleName("");
        jLabel11.getAccessibleContext().setAccessibleName("");
        jLabel12.getAccessibleContext().setAccessibleName("");
        jLabel13.getAccessibleContext().setAccessibleName("");
        jLabel14.getAccessibleContext().setAccessibleName("");

        trainTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Train ID", "Train Type", "From", "To", "Date", "Start time", "End time", "Seats", "Adult Price RM", "Child/Elder Price RM"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        trainTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        trainTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trainTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(trainTable);
        if (trainTable.getColumnModel().getColumnCount() > 0) {
            trainTable.getColumnModel().getColumn(0).setMinWidth(60);
            trainTable.getColumnModel().getColumn(0).setMaxWidth(60);
            trainTable.getColumnModel().getColumn(1).setMinWidth(60);
            trainTable.getColumnModel().getColumn(1).setMaxWidth(60);
            trainTable.getColumnModel().getColumn(2).setMinWidth(50);
            trainTable.getColumnModel().getColumn(2).setMaxWidth(50);
            trainTable.getColumnModel().getColumn(3).setMinWidth(40);
            trainTable.getColumnModel().getColumn(3).setMaxWidth(40);
            trainTable.getColumnModel().getColumn(4).setMinWidth(80);
            trainTable.getColumnModel().getColumn(4).setMaxWidth(80);
            trainTable.getColumnModel().getColumn(5).setMinWidth(70);
            trainTable.getColumnModel().getColumn(5).setMaxWidth(70);
            trainTable.getColumnModel().getColumn(6).setMinWidth(70);
            trainTable.getColumnModel().getColumn(6).setMaxWidth(70);
            trainTable.getColumnModel().getColumn(7).setMinWidth(50);
            trainTable.getColumnModel().getColumn(7).setMaxWidth(50);
            trainTable.getColumnModel().getColumn(8).setMinWidth(90);
            trainTable.getColumnModel().getColumn(8).setMaxWidth(90);
            trainTable.getColumnModel().getColumn(9).setMinWidth(120);
            trainTable.getColumnModel().getColumn(9).setMaxWidth(120);
        }

        modifyButton.setText("Modify");
        modifyButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        view.setText("View");
        view.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        view.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewMouseClicked(evt);
            }
        });

        back.setText("Back");
        back.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(modifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(view, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(view, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(modifyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 58, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        createTrainFile();
        if (trainValidation()) {
            writeTrainToFile();
        }
    }//GEN-LAST:event_createButtonActionPerformed

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
        if (trainValidation()) {
            modifyTrainInfo();
        }
    }//GEN-LAST:event_modifyButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        deleteTrainInfo();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearText();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void trainTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trainTableMouseClicked
        whenClickTable();
    }//GEN-LAST:event_trainTableMouseClicked

    private void trainIDfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trainIDfieldMouseClicked
        trainIDfield.setFocusable(true);
    }//GEN-LAST:event_trainIDfieldMouseClicked

    private void viewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewMouseClicked
        displayTrainInfoInTable();
    }//GEN-LAST:event_viewMouseClicked

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        TrainTicketingSystem next = new TrainTicketingSystem();
        next.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adminIDfield;
    private javax.swing.JTextField adminNameField;
    private javax.swing.JTextField adultPriceField;
    private javax.swing.JButton back;
    private javax.swing.JTextField childrenElderPriceField;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton createButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField endTimeField;
    private javax.swing.JTextField fromField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton modifyButton;
    private javax.swing.JTextField seatsField;
    private javax.swing.JTextField startTimeField;
    private javax.swing.JTextField toField;
    private com.toedter.calendar.JDateChooser trainDate;
    private javax.swing.JTextField trainIDfield;
    private javax.swing.JTable trainTable;
    private javax.swing.JComboBox<String> trainTypeField;
    private javax.swing.JButton view;
    // End of variables declaration//GEN-END:variables
}
