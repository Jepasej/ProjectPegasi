package org.example.projectpegasi.DomainModels;

/**
 * Class modelling a User of the system.
 */
public class User
{
    private String userName;
    private String password;
    private Profile profile;
    private int userID;

    public User(String userName, String password, Profile profile)
    {
        this.userName = userName;
        this.password = password;
        this.profile = profile;
    }

    public User(String userName, String password)
    {
        this.userName = userName;
        this.password = password;
    }

    public User()
    {

    }

    //region GettersSetters
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    //endregion
}
