package org.example.projectpegasi.DomainModels;

/**
 * Class modelling the profile of a user
 */
public class Profile
{
    private String fullName;
    private String jobTitle;
    private String homeAddress;
    private int wage;
    private int payPref;
    private String distPref;
    private boolean swappingStatus;
    private int profileID;
    private Company company;
    private String jobFunction; //Tilføjet her for at gemme jobfunctions man vælger

    public Profile(String fullName, String jobTitle, String homeAddress, int wage, int payPref, String distPref, Company company)
    {
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.homeAddress = homeAddress;
        this.wage = wage;
        this.payPref = payPref;
        this.distPref = distPref;
        this.company = company;
    }

    public Profile(String fullName, String jobTitle, String homeAddress, int wage, int payPref, String distPref, int profileID, boolean swappingStatus)
    {
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.homeAddress = homeAddress;
        this.wage = wage;
        this.payPref = payPref;
        this.distPref = distPref;
        this.swappingStatus = swappingStatus;
        this.profileID = profileID;
    }

    public Profile(int profileID,String jobFunction, int payPref)
    {
        this.profileID = profileID;
        this.jobFunction = jobFunction;
        this.payPref = payPref;
    }

    public Profile()
    {

    }

    public Company getCompany()
    {
        return company;
    }

    public void setCompany(Company company)
    {
        this.company = company;
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

    public boolean isSwappingStatus() {
        return swappingStatus;
    }

    public void setSwappingStatus(boolean swappingStatus) {
        this.swappingStatus = swappingStatus;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public String getJobFunction() { return jobFunction; }

    public void setJobFunction(String jobFunction) { this.jobFunction = jobFunction; }
}
