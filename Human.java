/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author celyn
 */
public class Human {

    private String name;
    private String identityCard;
    private String gender;
    private String phoneNum;

    //constructor
    public Human(String name, String identityCard, String gender, String phoneNum) {
        this.name = name;
        this.identityCard = identityCard;
        this.gender = gender;
        this.phoneNum = phoneNum;
    }

    //getter and setter name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //getter and setter identity card
    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    //getter and setter gender
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //getter and setter phone number
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    //method for search train which jump to Train_schedule class
    public void searchTrain() {
        TrainScheduleInterface viewSchedule = TrainScheduleInterface.createInstance();
        viewSchedule.setVisible(true);
    }
}
