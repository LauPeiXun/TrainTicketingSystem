/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author HP
 */
public class PassengerInterface extends javax.swing.JFrame {

    public PassengerInterface() {
        initComponents();
        fillplace();
    }

    @SuppressWarnings("unchecked")

    private void fillplace() {
        // Initialize selectedPlace as an empty string
        placeComboBox.removeAllItems();
        int count = 0;
        String filePath = "TrainInfo.txt";
        File file = new File(filePath);

        if (priceComboBox.getSelectedItem() != null) {
            String selectedOption = priceComboBox.getSelectedItem().toString();

            if ("Adult".equals(selectedOption)) {
                if (file.exists() && file.length() > 0) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        Object[] tableLines = br.lines().toArray();

                        for (Object tableLine : tableLines) {
                            String line = tableLine.toString().trim();
                            String[] dataRow = line.split("\\|");
                            if (dataRow.length >= 10) {
                                String trainNo = dataRow[0];
                                String date = dataRow[4];
                                String placeFrom = dataRow[2];
                                String placeTo = dataRow[3];
                                String timeFrom = dataRow[5];
                                String timeTo = dataRow[6];
                                String priceStr = dataRow[8];
                                String seatStr = dataRow[12];
                                String place = placeFrom + " to " + placeTo;
                                String time = timeFrom + "-" + timeTo;
                                double price = Double.parseDouble(priceStr);
                                int seat = Integer.parseInt(seatStr);
                                String choose = place + ", " + date + "," + time + ", " + price + ", " + "Seat Avaliable " + "," + seat + "," + trainNo;
                                // Add the combined place to the combo box
                                placeComboBox.addItem(choose);
                                count++;
                            }
                        }

                        if (count == 0) {
                            JOptionPane.showMessageDialog(this, "No Record Found");
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PassengerInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if ("Children/Elders".equals(selectedOption)) {
                if (file.exists() && file.length() > 0) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        Object[] tableLines = br.lines().toArray();

                        for (Object tableLine : tableLines) {
                            String line = tableLine.toString().trim();
                            String[] dataRow = line.split("\\|");
                            if (dataRow.length >= 10) {
                                String trainNo = dataRow[0];
                                String date = dataRow[4];
                                String placeFrom = dataRow[2];
                                String placeTo = dataRow[3];
                                String timeFrom = dataRow[5];
                                String timeTo = dataRow[6];
                                String priceStr = dataRow[9];
                                String seatStr = dataRow[12];
                                String place = placeFrom + " to " + placeTo;
                                String time = timeFrom + "-" + timeTo;
                                double price = Double.parseDouble(priceStr);
                                int seat = Integer.parseInt(seatStr);
                                String choose = place + ", " + date + "," + time + ", " + price + ", " + "Seat Avaliable " + "," + seat + "," + trainNo;

                                placeComboBox.addItem(choose);
                                count++;
                            }
                        }

                        if (count == 0) {
                            JOptionPane.showMessageDialog(this, "No Record Found");
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PassengerInterface.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private void fillprice() {

        String place = "";
        double discount = 0.00;
        String accNum = "";
        String bankList = "";

        if (placeComboBox.getSelectedItem() != null) {
            place = placeComboBox.getSelectedItem().toString();
        }

        String[] partsA = place.split(",");
        if (partsA.length == 7) {
            String placeInfo = partsA[0].trim();
            String timeInfo = partsA[2].trim();
            String priceInfo = partsA[3].trim();
            String seatInfo = partsA[5].trim();
            String dateInfo = partsA[1].trim();
            String trainInfo = partsA[6].trim();
            int seat = Integer.parseInt(seatInfo);
            double price = Double.parseDouble(priceInfo);

            String[] placeParts = placeInfo.split(" to ");
            if (placeParts.length == 2) {
                String placeFrom = placeParts[0].trim();
                String placeTo = placeParts[1].trim();
                placeLabel.setText(placeFrom + " to " + placeTo);
                timeLabel.setText(timeInfo);
                priceLabel.setText(priceInfo);
                dateLabel.setText(dateInfo);
                seatLabel.setText(seatInfo);
                trainNo.setText(trainInfo);
                discount = discountCheck();
                double totalAmount = price - (price * discount);
                totalLabel.setText(String.valueOf(totalAmount));
            }
        }
    }

    private void valitation() {
        String name = txtName.getText();
        String identityCard = txtIdentityCard.getText();
        String phoneText = txtPhoneNum.getText();
        String gender = ""; // Initialize gender here
        String place = "";
        String placeStr = "";
        String time = "";
        String date = "";
        int seat = 0;
        String placeTo = "";
        String placeFrom = "";
        String trainNo = "";
        String seatInfo = "";
        double price = 0;
        String filePath = "TrainInfo.txt";
        File file = new File(filePath);
        boolean hasErrors = false;
        double discount = discountCheck();

        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Your Name !");
            hasErrors = true;
        } else if (txtName.getText().length() > 70 || !txtName.getText().matches("[^\\d]+")) {
            JOptionPane.showMessageDialog(this, "Please enter up to characters with no digits.");
        }

        if (identityCard.isEmpty()) {
            hasErrors = true;
            JOptionPane.showMessageDialog(null, "Please enter your Identity Card Number!");
        }

        if (genderComboBox.getSelectedItem() == "Null") {
            JOptionPane.showMessageDialog(null, "Please select a gender!");
            hasErrors = true;
        } else {
            gender = genderComboBox.getSelectedItem().toString();
        }

        if (phoneText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your Phone Number!");
            hasErrors = true;
        } else {
            String regexHP = "^(0)(1)(0|2|6|7|8|9)[0-9]{7}$";
            Pattern patternHP = Pattern.compile(regexHP);
            Matcher matcherHP = patternHP.matcher(txtPhoneNum.getText());
            if (!matcherHP.matches()) {
                JOptionPane.showMessageDialog(this, "Please fill in valid phone number example: 01xxxxxxxx");
                return;
            }
        }
        Passenger passenger = new Passenger(name, identityCard, gender, phoneText);

        // check place
        if (placeComboBox.getSelectedItem() != null) {
            place = placeComboBox.getSelectedItem().toString();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a place!");
            hasErrors = true;
        }
        String[] partsA = place.split(",");
        if (partsA.length == 7) {
            String placeInfo = partsA[0].trim();
            String timeInfo = partsA[2].trim();
            String priceInfo = partsA[3].trim();
            seatInfo = partsA[5].trim();
            String dateInfo = partsA[1].trim();
            trainNo = partsA[6].trim();
            seat = Integer.parseInt(seatInfo);
            price = Double.parseDouble(priceInfo);

            String[] placeParts = placeInfo.split(" to ");
            if (placeParts.length == 2) {
                placeFrom = placeParts[0].trim();
                placeTo = placeParts[1].trim();
                placeStr = placeFrom + " to " + placeTo;
                time = timeInfo;
                date = dateInfo;
            }
        }
        if (accNum.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your Account Number!");
            hasErrors = true;
        } else if (!accNum.getText().matches("\\d{12}")) {
            JOptionPane.showMessageDialog(this, "The account number should be 12 digit and can only contain digit !");
            hasErrors = true;
        }

        if (bankList.getSelectedItem() == "Null") {
            JOptionPane.showMessageDialog(null, "Please select a bank for make payment!");
            hasErrors = true;
        }

        if (!hasErrors) {
            try (BufferedReader br = new BufferedReader(new FileReader("passenger.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] dataRow = line.split("\\|");
                    if (dataRow.length >= 9 && identityCard.equals(dataRow[1]) && trainNo.equals(dataRow[8])) {
                        JOptionPane.showMessageDialog(null, "You have already booked a ticket for this train.");
                        return;
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while checking existing bookings.");
            }
            if (seat > 0) {
                try {
                    List<String> trainInfoLines = new ArrayList<>();
                    if (file.exists() && file.length() > 0) {
                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                trainInfoLines.add(line);
                            }
                        }

                        boolean updated = false;
                        for (int i = 0; i < trainInfoLines.size(); i++) {
                            String line = trainInfoLines.get(i);
                            String[] dataRow = line.split("\\|");
                            if (dataRow.length >= 10 && trainNo.equals(dataRow[0])) {
                                int currentSeat = Integer.parseInt(dataRow[12]);
                                if (currentSeat >= 1) {
                                    currentSeat--;
                                    dataRow[12] = String.valueOf(currentSeat);
                                    trainInfoLines.set(i, String.join("|", dataRow));
                                    updated = true;
                                    break;
                                }
                            }
                        }
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Traininfo.txt"))) {
                            for (String updatedLine : trainInfoLines) {
                                bw.write(updatedLine + "\n");
                            }
                        }

                        if (!updated) {
                            JOptionPane.showMessageDialog(null, "Ticket Sold Out !");
                        }
                    }
                    if (discount == 0.00) {
                        passenger.bookTicket(placeStr, time, date, trainNo, price);
                        clear();
                    } else {
                        passenger.staffBookTicket(placeStr, time, date, trainNo, price);
                        clear();
                    }
                } catch (HeadlessException | IOException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ticket Sold Out !");
            }

        }
    }

    private void search() {
        String identityCard = txtIdentityCard.getText();
        int count = 0;

        DefaultTableModel recordTablex = (DefaultTableModel) recordTable.getModel();
        recordTablex.setRowCount(0);
        recordTablex.fireTableDataChanged();
        if (identityCard.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your Identity Card Number!");
        } else {

            try (BufferedReader br = new BufferedReader(new FileReader("passenger.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split("\\|");
                    String[] dataRow = line.split("\\|");
                    if (columns.length == 9) {
                        if (identityCard.equals(dataRow[1])) {
                            recordTablex.addRow(columns);
                            count++;
                        }

                    }
                }
                if (count == 0) {
                    JOptionPane.showMessageDialog(this, "No Record Found");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while reading from the file: " + ex.getMessage());
            }
        }
    }

    private int WhenClickTable() {
        DefaultTableModel recordTablex = (DefaultTableModel) recordTable.getModel();
        int selectedRow = recordTable.getSelectedRow();

        if (selectedRow >= 0) {
            String nameFieldx = recordTablex.getValueAt(selectedRow, 0).toString();
            String icFieldx = recordTablex.getValueAt(selectedRow, 1).toString();
            String genderFieldx = recordTablex.getValueAt(selectedRow, 2).toString();
            String phoneFieldx = recordTablex.getValueAt(selectedRow, 3).toString();
            String priceFieldx = recordTablex.getValueAt(selectedRow, 4).toString();
            String timeFieldx = recordTablex.getValueAt(selectedRow, 5).toString();
            String placeFieldx = recordTablex.getValueAt(selectedRow, 6).toString();
            String dateFieldx = recordTablex.getValueAt(selectedRow, 7).toString();
            String trainNoFieldx = recordTablex.getValueAt(selectedRow, 8).toString();

            txtName.setText(nameFieldx);
            txtIdentityCard.setText(icFieldx);
            genderComboBox.setSelectedItem(genderFieldx);
            txtPhoneNum.setText(phoneFieldx);
            priceLabel.setText(priceFieldx);
            timeLabel.setText(timeFieldx);
            placeLabel.setText(placeFieldx);
            dateLabel.setText(dateFieldx);
            trainNo.setText(trainNoFieldx);
        }
        return selectedRow;
    }

    private void cancel() {
        String enteredIdentityCard = txtIdentityCard.getText().trim();
        String enteredTrainNo = trainNo.getText().trim();

        try {
            List<String> bookingLines = Files.readAllLines(Paths.get("passenger.txt"));
            List<String> trainInfoLines = Files.readAllLines(Paths.get("TrainInfo.txt"));
            boolean found = false;

            for (int i = bookingLines.size() - 1; i >= 0; i--) {
                String bookingLine = bookingLines.get(i);
                String[] bookingParts = bookingLine.split("\\|");

                if (bookingParts.length >= 2) {
                    String bookingIdentityCard = bookingParts[1].trim();
                    String bookingTrainNo = bookingParts[8].trim(); // Assuming train number is in the ninth column

                    if (enteredIdentityCard.equals(bookingIdentityCard) && enteredTrainNo.equals(bookingTrainNo)) {
                        int confirmPane = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

                        if (confirmPane == JOptionPane.YES_OPTION) {
                            bookingLines.remove(i);
                            Files.write(Paths.get("passenger.txt"), bookingLines, StandardCharsets.UTF_8);

                            // Find the corresponding TrainInfo entry and update seatAvailable
                            for (int j = 0; j < trainInfoLines.size(); j++) {
                                String trainInfoLine = trainInfoLines.get(j);
                                String[] trainInfoParts = trainInfoLine.split("\\|");

                                if (trainInfoParts.length >= 1) {
                                    String trainInfoTrainNo = trainInfoParts[0].trim();

                                    if (enteredTrainNo.equals(trainInfoTrainNo)) {
                                        int seatAvailable = Integer.parseInt(trainInfoParts[trainInfoParts.length - 1].trim()); // Assuming seatAvailable is in the last column
                                        seatAvailable++;
                                        trainInfoParts[trainInfoParts.length - 1] = Integer.toString(seatAvailable);
                                        trainInfoLines.set(j, String.join("|", trainInfoParts));
                                        Files.write(Paths.get("TrainInfo.txt"), trainInfoLines, StandardCharsets.UTF_8);

                                        JOptionPane.showMessageDialog(null, "Record Deleted Successfully!");
                                        found = true;
                                        clear();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "No matching record found for the given Identity Card and Train No.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error!");
        }
    }

    private void clear() {
        txtName.setText("");
        txtIdentityCard.setText("");
        genderComboBox.setSelectedItem("Null");
        txtPhoneNum.setText("");
        priceComboBox.setSelectedItem("Null");
        accNum.setText("");
        bankList.setSelectedItem("Null");
        reference.setText("");
        totalLabel.setText("");
        priceLabel.setText("");
        timeLabel.setText("");
        placeLabel.setText("");
        dateLabel.setText("");
        trainNo.setText("");
        seatLabel.setText("");
        DefaultTableModel model = (DefaultTableModel) recordTable.getModel();
        model.setRowCount(0);
    }

    public double discountCheck() {
        String inputIdentityCard = txtIdentityCard.getText().trim(); // Trim the input identity card
        double discount = 0.0;

        // Check if the identity card is valid
        if (!isValidIdentityCard(inputIdentityCard)) {
            JOptionPane.showMessageDialog(this, "Please fill in a valid identity card number (e.g., xxxxxxxxxxxx)");
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader("admin.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] dataRow = line.split("\\|");
                    if (dataRow.length >= 6) {
                        String storedIdentityCard = dataRow[3].trim(); // Assuming identity card is in the fourth column
                        if (inputIdentityCard.equals(storedIdentityCard)) {
                            discount = 0.10; // Set the discount to 10% if a match is found
                            txtName.setText(dataRow[2]);
                            txtName.setEditable(true);
                            txtPhoneNum.setText(dataRow[5]);
                            txtPhoneNum.setEditable(true);
                            if (dataRow[4].equals("Female")) {
                                genderComboBox.setSelectedItem("Female");
                                genderComboBox.setEditable(true);
                            } else {
                                genderComboBox.setSelectedItem("Male");
                                genderComboBox.setEditable(true);
                            }

                            break; // Exit the loop since we found a match
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while reading from the file: " + ex.getMessage());
            }
        }

        if (discount > 0.0) {
            totalLabel.setText("You can get a 10% discount!");
        } else {
            totalLabel.setText("Not an admin");
        }

        return discount;
    }

    private boolean isValidIdentityCard(String identityCard) {
        String regex = "^([0-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{2}[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(identityCard);
        return matcher.matches();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtIdentityCard = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPhoneNum = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        search = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        priceComboBox = new javax.swing.JComboBox<>();
        placeComboBox = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        trainNo = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        placeLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        back = new javax.swing.JButton();
        Book = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        recordTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        bankList = new javax.swing.JComboBox<>();
        l_acc_num = new javax.swing.JLabel();
        accNum = new javax.swing.JTextField();
        l_bank = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        l_reference = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        reference = new javax.swing.JTextArea();
        l_acc_num2 = new javax.swing.JLabel();
        totalLabel = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        seatLabel = new javax.swing.JLabel();
        Cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Passenger");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 170, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("IC Number");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 80, -1));

        txtIdentityCard.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdentityCardFocusLost(evt);
            }
        });
        jPanel5.add(txtIdentityCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 170, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Gender");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Record View :");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 130, 30));
        jPanel5.add(txtPhoneNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 170, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Train No. :");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, 70, -1));

        search.setBackground(new java.awt.Color(102, 102, 102));
        search.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        search.setForeground(new java.awt.Color(255, 255, 255));
        search.setText("Search Record Here !");
        search.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        jPanel5.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 150, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Name");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Ticket");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, -1));

        priceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Null", "Adult", "Children/Elders" }));
        priceComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceComboBoxActionPerformed(evt);
            }
        });
        jPanel5.add(priceComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 130, 20));

        placeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeComboBoxActionPerformed(evt);
            }
        });
        jPanel5.add(placeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 480, 20));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Seat Avaliable :");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 80, 110, -1));

        trainNo.setText("train no.");
        jPanel5.add(trainNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 110, 80, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Time :");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 110, 76, -1));

        priceLabel.setText("price");
        jPanel5.add(priceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 110, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Ticket Price (RM) :");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 130, -1));

        timeLabel.setText("time");
        jPanel5.add(timeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 110, 110, -1));

        placeLabel.setText("place");
        jPanel5.add(placeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, 120, -1));

        dateLabel.setText("date");
        jPanel5.add(dateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, 110, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Place :");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, 76, -1));

        back.setBackground(new java.awt.Color(102, 102, 102));
        back.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        back.setForeground(new java.awt.Color(255, 255, 255));
        back.setText("Back !");
        back.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jPanel5.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 440, 140, 30));

        Book.setBackground(new java.awt.Color(102, 102, 102));
        Book.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Book.setForeground(new java.awt.Color(255, 255, 255));
        Book.setText("Book !");
        Book.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookActionPerformed(evt);
            }
        });
        jPanel5.add(Book, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 340, 140, 30));

        recordTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "IC", "Gender", "Phone Number", "Price", "Time", "Place", "Date", "Train Code"
            }
        ));
        recordTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recordTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(recordTable);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 940, 110));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Phone Number");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 110, -1));

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, null));
        jPanel3.setForeground(new java.awt.Color(51, 51, 51));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bankList.setForeground(new java.awt.Color(51, 51, 51));
        bankList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Null", "Public Bank", "Hong Leong Bank", "CIMB Bank", "MayBank", "OCBC Bank" }));
        bankList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(bankList, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 205, -1));

        l_acc_num.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        l_acc_num.setText("Total Amount (RM) :");
        jPanel3.add(l_acc_num, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        accNum.setForeground(new java.awt.Color(51, 51, 51));
        accNum.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.add(accNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 205, -1));

        l_bank.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        l_bank.setText("Select Preferred Bank :");
        jPanel3.add(l_bank, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 93, -1, -1));

        l_reference.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        l_reference.setText("Reference(optional) :");
        jPanel3.add(l_reference, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        reference.setColumns(20);
        reference.setForeground(new java.awt.Color(51, 51, 51));
        reference.setRows(6);
        reference.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setViewportView(reference);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 290, 40));

        l_acc_num2.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        l_acc_num2.setText("Account Number :");
        jPanel3.add(l_acc_num2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        totalLabel.setText("total amount");
        jPanel3.add(totalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 200, -1));

        jPanel5.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 480, 160));

        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Null", "Female", "Male" }));
        jPanel5.add(genderComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 100, 170, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Date :");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 53, -1));

        seatLabel.setText("seat ");
        jPanel5.add(seatLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, 80, -1));

        Cancel.setBackground(new java.awt.Color(102, 102, 102));
        Cancel.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        Cancel.setForeground(new java.awt.Color(255, 255, 255));
        Cancel.setText("Cancel !");
        Cancel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });
        jPanel5.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 390, 140, 30));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 960, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 981, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void placeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeComboBoxActionPerformed
        fillprice();
    }//GEN-LAST:event_placeComboBoxActionPerformed

    private void priceComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceComboBoxActionPerformed
        fillplace();
    }//GEN-LAST:event_priceComboBoxActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        search();
    }//GEN-LAST:event_searchActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        TrainTicketingSystem next = new TrainTicketingSystem();
        next.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backActionPerformed

    private void BookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookActionPerformed
        valitation();
    }//GEN-LAST:event_BookActionPerformed

    private void recordTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordTableMouseClicked
        WhenClickTable();
    }//GEN-LAST:event_recordTableMouseClicked

    private void txtIdentityCardFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdentityCardFocusLost
        discountCheck();
    }//GEN-LAST:event_txtIdentityCardFocusLost

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
        cancel();
    }//GEN-LAST:event_CancelActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Book;
    private javax.swing.JButton Cancel;
    private javax.swing.JTextField accNum;
    private javax.swing.JButton back;
    private javax.swing.JComboBox<String> bankList;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel l_acc_num;
    private javax.swing.JLabel l_acc_num2;
    private javax.swing.JLabel l_bank;
    private javax.swing.JLabel l_reference;
    private javax.swing.JComboBox<String> placeComboBox;
    private javax.swing.JLabel placeLabel;
    private javax.swing.JComboBox<String> priceComboBox;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JTable recordTable;
    private javax.swing.JTextArea reference;
    private javax.swing.JButton search;
    private javax.swing.JLabel seatLabel;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JLabel trainNo;
    private javax.swing.JTextField txtIdentityCard;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhoneNum;
    // End of variables declaration//GEN-END:variables

}
