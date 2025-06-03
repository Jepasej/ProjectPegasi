package org.example.projectpegasi.DomainModels;

/**
 * Class modelling the profile of a user.
 * A profile holds basic information about a user's job, location, and preferences,
 * which is used for creating and evaluating job swap matches.
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
    private String jobFunction;

    /**
     * Constructor used when creating a new profile from the UI with all needed input.
     * @param fullName The user's full name
     * @param jobTitle The title of the current job
     * @param homeAddress The user's home address
     * @param wage The current wage
     * @param payPref The user's salary preference
     * @param distPref The user's preferred distance to work
     * @param company The company the user works for
     */
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

    /**
     * Constructor used when creating a profile from the database that already has an ID and swap status.
     * @param fullName The user's full name
     * @param jobTitle The title of the current job
     * @param homeAddress The user's home address
     * @param wage The current wage
     * @param payPref The user's salary preference
     * @param distPref The user's preferred distance to work
     * @param profileID The ID of the profile
     * @param swappingStatus True if the user is open to job swap, false otherwise
     */
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

    /**
     * Constructor used mainly for matching logic when only a few profile values are needed.
     * @param profileID The profile ID
     * @param jobFunction The job function of the user
     * @param payPref The salary preference of the user
     */
    public Profile(int profileID,String jobFunction, int payPref)
    {
        this.profileID = profileID;
        this.jobFunction = jobFunction;
        this.payPref = payPref;
    }

    /**
     * Empty constructor used when values are set manually later
     */
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
