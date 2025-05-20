package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataAccessObject implements DAO
{
    @Override
    public void create(Object object)
    {

    }

    @Override
    public void read(Object object)
    {

    }

    @Override
    public void readALl(Object object)
    {

    }

    @Override
    public void update(Object object)
    {

    }

    @Override
    public void delete(Object object)
    {

    }

    @Override
    public boolean verifyUser(User user)
    {
        //TO BE IMPLEMENTED
        //take user
        //run stored provedure
        //return whether input matches record. CheckPassword

        //SKAL OPDATERES TIL VORES SINGLETON CONNECTION.
        Connection conn = null;

        String sql = "{call CheckPassword(?,?)}";
        try
        {

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, user.getUserName());
            cs.setString(2, user.getPassword());
            ResultSet rs = cs.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
