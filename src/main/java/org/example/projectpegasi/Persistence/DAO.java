package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.User;

import java.util.ArrayList;
import java.util.List;

public interface DAO
{
    //TO BE UPDATED!
    void create(Object object);
    void read(Object object);
    ArrayList readAll(Object object);
    void update(Object object);
    void delete(Object object);
    void createUser(User user);
    boolean checkUsernameIsUnique(String name);
    boolean verifyUser(User user);

    int getUserID(String userName);

    int getCompanyID(String companyName);

    int getProfileID(int userID);

    List<String> getProfileInformation(int profileID);
}
