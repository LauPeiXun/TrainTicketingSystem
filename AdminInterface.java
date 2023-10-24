/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author celyn
 */
public class AdminInterface extends javax.swing.JFrame {

    public AdminInterface() {
        initComponents();
    }

    private boolean validationReg() {
        boolean check = true; // Initialize check to true

        String adminIdPattern = "^[A-Z]{2}\\d{4}$";
        String icPattern = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{6}$";
        String phoneNumPattern = "^(0)(1)(0|2|6|7|8|9)[0-9]{7}$";

        if (!adminID.getText().matches(adminIdPattern)) {
            JOptionPane.showMessageDialog(this, "Please fill in valid admin code example: AC1234 ");
            check = false; // Set check to false, but continue checking other conditions
        }

        if (adminPass.getText().length() > 10) {
            JOptionPane.showMessageDialog(this, "No more than 10 characters");
            check = false; // Set check to false, but continue checking other conditions
        }

        if (adminName.getText().length() > 40) {
            JOptionPane.showMessageDialog(this, "No more than 40 characters");
            check = false; // Set check to false, but continue checking other conditions
        }

        if (!adminIC.getText().matches(icPattern)) {
            JOptionPane.showMessageDialog(this, "Please fill in valid identity card number example: xxxxxxxxxxxx");
            check = false; // Set check to false, but continue checking other conditions
        }

        if (adminGender.getSelectedItem().equals("----select----")) {
            JOptionPane.showMessageDialog(this, "Please select your gender");
            check = false; // Set check to false, but continue checking other conditions
        }

        if (!adminPhoneNum.getText().matches(phoneNumPattern)) {
            JOptionPane.showMessageDialog(this, "Please fill in a valid phone number example: 01xxxxxxxx");
            check = false; // Set check to false, but continue checking other conditions
        }

        return check; // Return the final result after checking all conditions
    }

    private boolean checkExist() {
        try (BufferedReader r = new BufferedReader(new FileReader("admin.txt"))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] columns = line.split("\\|"); // Split the line into columns
                if (columns.length == 6 && adminID.getText().equals(columns[0])) {
                    JOptionPane.showMessageDialog(this, "This admin ID already exist ");
                    return true;
                }
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while reading from the file.");
        }
        return false;
    }

    private boolean checkBlank() {
        if (adminID.getText().isEmpty() || adminPass.getText().isEmpty() || adminName.getText().isEmpty() || adminIC.getText().isEmpty()
                || adminGender.getSelectedItem().equals("----select----") || adminPhoneNum.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Log in or register .");
            return true;
        } else if (adminID.getText().isEmpty() || adminPass.getText().isEmpty() && !(adminName.getText().isEmpty() || adminIC.getText().isEmpty()
                || adminGender.getSelectedItem().equals("----select----") || adminPhoneNum.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Please login before view schedule or generate report");
            return true;
        }
        return false;
    }

    private void view() {
        if (checkBlank() == false) {
            String id = adminID.getText();
            String password = adminPass.getText();
            String nam = adminName.getText();
            String ic = adminIC.getText();
            String gend = (String) adminGender.getSelectedItem();
            String phoneN = adminPhoneNum.getText();
            Admin admin = new Admin(nam, ic, gend, phoneN);
            admin.setAdminID(id);
            admin.setAdminPassword(password);
            admin.searchTrain();
            this.dispose();

        }
    }

    private void gReport() {
        if (checkBlank() == false) {
            String id = adminID.getText();
            String password = adminPass.getText();
            String nam = adminName.getText();
            String ic = adminIC.getText();
            String gend = (String) adminGender.getSelectedItem();
            String phoneN = adminPhoneNum.getText();
            Admin admin = new Admin(nam, ic, gend, phoneN);
            admin.setAdminID(id);
            admin.setAdminPassword(password);
            admin.generateReport();
            this.dispose();
        }
    }

    private void login(String adminID, String adminPass) {
        int count = 0;
        if (adminID.equals("") || adminPass.equals("")) {
            JOptionPane.showMessageDialog(this, "Please fill in the admin ID and Password");
        } else {
            try (BufferedReader r = new BufferedReader(new FileReader("admin.txt"))) {
                String line;
                while ((line = r.readLine()) != null) {
                    String[] columns = line.split("\\|"); // Split the line into columns
                    Admin a1 = new Admin(columns[2], columns[3], columns[4], columns[5]);
                    a1.setAdminID(columns[0]);
                    a1.setAdminPassword(columns[1]);
                    boolean success = a1.login(adminID, adminPass);

                    if (success == true) {
                        JOptionPane.showMessageDialog(this, "Successfully login HI " + adminID);
                        adminName.setText(a1.getName());
                        adminIC.setText(a1.getIdentityCard());
                        adminGender.setSelectedItem(a1.getGender());
                        adminPhoneNum.setText(a1.getPhoneNum());
                        count++;
                    }
                }
                if (count == 0) {
                    JOptionPane.showMessageDialog(this, "No admin found ");
                }
            } catch (IOException ex) {
                System.out.println("An error occurred while reading from the file.");
            }
        }
    }

    private void clear() {
        adminID.setText("");
        adminPass.setText("");
        adminName.setText("");
        adminIC.setText("");
        adminGender.setSelectedItem("----select----");
        adminPhoneNum.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        l_train_code = new javax.swing.JLabel();
        adminID = new javax.swing.JTextField();
        l_train_type = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        adminPass = new javax.swing.JTextField();
        view = new javax.swing.JButton();
        back = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        name = new javax.swing.JLabel();
        adminName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        adminIC = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        adminPhoneNum = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        adminGender = new javax.swing.JComboBox<>();
        register = new javax.swing.JButton();
        logIn = new javax.swing.JButton();
        generateReport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        title.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("Admin Details");

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        l_train_code.setText("Admin ID:");

        l_train_type.setText("Admin Password:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(l_train_code)
                    .addComponent(adminID)
                    .addComponent(l_train_type, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminPass, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(l_train_code)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminID, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_train_type)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminPass, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1))
        );

        view.setBackground(java.awt.Color.gray);
        view.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        view.setForeground(new java.awt.Color(255, 255, 255));
        view.setText("Train Schedule");
        view.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        view.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewMouseClicked(evt);
            }
        });

        back.setBackground(java.awt.Color.gray);
        back.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        back.setForeground(new java.awt.Color(255, 255, 255));
        back.setText("Back");
        back.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        name.setText("Name:");

        jLabel2.setText("Identity Card:");

        jLabel3.setText("Gender:");

        jLabel4.setText("Phone Number:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        jLabel5.setText("*Fill in this form only if want register");

        adminGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----select----", "Female", "Male", " " }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminIC)
                    .addComponent(adminName)
                    .addComponent(adminPhoneNum)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(0, 149, Short.MAX_VALUE))
                    .addComponent(adminGender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(name)
                .addGap(5, 5, 5)
                .addComponent(adminName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminIC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adminPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        register.setBackground(java.awt.Color.gray);
        register.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        register.setForeground(new java.awt.Color(255, 255, 255));
        register.setText("Register");
        register.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerMouseClicked(evt);
            }
        });

        logIn.setBackground(java.awt.Color.gray);
        logIn.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        logIn.setForeground(new java.awt.Color(255, 255, 255));
        logIn.setText("Log In");
        logIn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        logIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logInMouseClicked(evt);
            }
        });

        generateReport.setBackground(java.awt.Color.gray);
        generateReport.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        generateReport.setForeground(new java.awt.Color(255, 255, 255));
        generateReport.setText("Report");
        generateReport.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        generateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(title)
                        .addContainerGap(492, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(register, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addComponent(view, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(back, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(logIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(generateReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(view)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(generateReport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logIn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(register)
                        .addGap(18, 18, 18)
                        .addComponent(back))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        TrainTicketingSystem next = new TrainTicketingSystem();
        next.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backActionPerformed

    private void viewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewMouseClicked
        view();
    }//GEN-LAST:event_viewMouseClicked

    private void registerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseClicked
        if (validationReg() == true) {
            if (checkBlank() == false) {
                if (checkExist() == false) {
                    String id = adminID.getText();
                    String password = adminPass.getText();
                    Admin admin = new Admin(adminName.getText(), adminIC.getText(), (String) adminGender.getSelectedItem(), adminPhoneNum.getText());
                    admin.setAdminID(id);
                    admin.setAdminPassword(password);
                    if (admin.register() == true) {
                        JOptionPane.showMessageDialog(this, "successfully registred, Hi " + admin.getName());
                        clear();
                    }
                } else {
                    adminID.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fully fill in the form.");
            }
        }
    }//GEN-LAST:event_registerMouseClicked

    private void logInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logInMouseClicked
        String id = adminID.getText();
        String password = adminPass.getText();
        login(id, password);
    }//GEN-LAST:event_logInMouseClicked

    private void generateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateReportActionPerformed
        gReport();
    }//GEN-LAST:event_generateReportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> adminGender;
    private javax.swing.JTextField adminIC;
    private javax.swing.JTextField adminID;
    private javax.swing.JTextField adminName;
    private javax.swing.JTextField adminPass;
    private javax.swing.JTextField adminPhoneNum;
    private javax.swing.JButton back;
    private javax.swing.JButton generateReport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel l_train_code;
    private javax.swing.JLabel l_train_type;
    private javax.swing.JButton logIn;
    private javax.swing.JLabel name;
    private javax.swing.JButton register;
    private javax.swing.JLabel title;
    private javax.swing.JButton view;
    // End of variables declaration//GEN-END:variables
}
