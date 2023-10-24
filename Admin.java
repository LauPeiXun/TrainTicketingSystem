
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author celyn
 */
public class Admin extends Human implements adminFeatures {

    private String adminID;
    private String adminPassword;

    public Admin(String name, String identityCard, String gender, String phoneNum) {
        super(name, identityCard, gender, phoneNum);
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public boolean register() {
        String fileP = "admin.txt";
        File file = new File(fileP);

        if (file.exists()) {
            try (FileWriter w = new FileWriter(fileP, true)) {
                String dataToWrite = getAdminID() + "|" + getAdminPassword() + "|" + super.getName() + "|" + super.getIdentityCard() + "|" + super.getGender() + "|" + super.getPhoneNum() + "\n";
                w.write(dataToWrite);
                return true;

            } catch (IOException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public void searchTrain() {
        TrainScheduleInterface viewSchedule = TrainScheduleInterface.createInstance();
        viewSchedule.setVisible(true);
    }

    @Override
    public void generateReport() {
        ReportInterface generate = new ReportInterface();
        generate.setVisible(true);
    }

    @Override
    public boolean login(String id, String pass) {
        return adminID.equals(id) && getAdminPassword().equals(pass);
    }

}
