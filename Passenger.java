
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author HP
 */
public class Passenger extends Human implements BookingSystem {

    String placeStr = "";
    String time = "";
    String date = "";
    String trainNo = "";
    double price;

    public Passenger(String name, String identityCard, String gender, String phoneNum) {
        super(name, identityCard, gender, phoneNum);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPlaceStr() {
        return placeStr;
    }

    public void setPlaceStr(String placeStr) {
        this.placeStr = placeStr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    @Override
    public void bookTicket(String placeStr, String time, String date, String trainNo, double price) {
        double discount = 0.00;
        double totalAmount = price - (price * discount);
        FileWriter writer = null;
        try {
            writer = new FileWriter("passenger.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            writer.write(super.getName() + "|" + super.getIdentityCard() + "|" + super.getGender() + "|" + super.getPhoneNum() + "|" + totalAmount + "|" + time + "|" + placeStr + "|" + date + "|" + trainNo + "\n");
        } catch (IOException ex) {
            Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Success");
    }

    public void staffBookTicket(String placeStr, String time, String date, String trainNo, double price) {
        double discount = 0.10;
        double totalAmount = price - (price * discount);
        FileWriter writer = null;
        try {
            writer = new FileWriter("passenger.txt", true);
        } catch (IOException ex) {
            Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            writer.write(super.getName() + "|" + super.getIdentityCard() + "|" + super.getGender() + "|" + super.getPhoneNum() + "|" + totalAmount + "|" + time + "|" + placeStr + "|" + date + "|" + trainNo + "\n");
        } catch (IOException ex) {
            Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Success");

    }
}
