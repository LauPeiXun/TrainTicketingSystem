/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author celyn
 */
public class Report {

    private String daily;
    private String monthly;

    public Report() {
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    public boolean daily(String cmpDate) {
       return cmpDate.equals(getDaily());
    }
    
    
    public boolean monthly(String cmpMonth) {
       return cmpMonth.equalsIgnoreCase(getMonthly()) || "--ALL--".equals(getMonthly());
    }
}
