package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.DomainModels.User;

import java.util.ArrayList;
import java.util.List;

import java.sql.SQLException;

/**
 * DAO interface for database access
 * Provides methods for CRUD operations and specific functionality
 * such as user management, profile data handling and match logic.
 */
public interface DAO
{
    void create(Object object);
    void read(Object object);
    ArrayList readAll(Object object);
    void update(Object object);
    void delete(Object object);
    void createUser(User user);
    boolean checkUsernameIsUnique(String name);


    String getPassword(int UserID);

    void changePassword(String string, int UserID) throws SQLException, ClassNotFoundException;

    void SafeEditProfileData(int userID, String newFullName, String jobTitle, String homeAddress, String company, String minSalary, String distancePref) throws SQLException, ClassNotFoundException;

    boolean verifyUser(User user);

    int getUserID(String userName);

    int getCompanyID(String companyName);

    int getProfileID(int userID);

    List<String> getProfileInformation(int profileID);

    List<Match> getTwoNewestMatchesByProfileID(int profileID);

    String getJobTitleByProfileID(int profileID);

    List<Match>getTwoNewestRequestsByProfileID(int profileID);

    boolean updateSwappingStatus(int profileID, Boolean swappingStatus);

    List<Profile> getAllProfiles();

    void saveMatch(int profileAID, int profileBID);
}
