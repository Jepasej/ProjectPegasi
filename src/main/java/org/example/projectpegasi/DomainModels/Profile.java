package org.example.projectpegasi.DomainModels;

public class Profile
{
    private String fullName;
    private String jobTitle;
    private String homeAddress;
    private int wage;
    private int payPref;
    private String distPref;
    private String about;
    private boolean swappingStatus;

    public Profile(String fullName, String jobTitle, String homeAddress, int wage, int payPref, String distPref)
    {
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.homeAddress = homeAddress;
        this.wage = wage;
        this.payPref = payPref;
        this.distPref = distPref;
        this.about = "";
        this.swappingStatus = false;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
    }

    public int getPayPref() {
        return payPref;
    }

    public void setPayPref(int payPref) {
        this.payPref = payPref;
    }

    public String getDistPref() {
        return distPref;
    }

    public void setDistPref(String distPref) {
        this.distPref = distPref;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isSwappingStatus() {
        return swappingStatus;
    }

    public void setSwappingStatus(boolean swappingStatus) {
        this.swappingStatus = swappingStatus;
    }

}
