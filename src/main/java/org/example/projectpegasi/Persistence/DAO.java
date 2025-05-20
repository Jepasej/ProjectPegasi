package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.User;

public interface DAO
{
    //TO BE UPDATED!
    void create(Object object);
    void read(Object object);
    void readALl(Object object);
    void update(Object object);
    void delete(Object object);
    boolean verifyUser(User user);
}
