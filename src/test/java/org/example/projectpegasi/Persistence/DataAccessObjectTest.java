package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataAccessObjectTest
{

    @Test
    void verifyUserInOn()
    {
        //arrange - using username Mads and password mads123 from the Database
        User user = new User();
        user.setUserName("Mads");
        user.setPassword("mads123");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertTrue(result);
    }

    @Test
    void verifyUserPasswordOffSpelling()
    {
        //arrange - using username Mads and password mads123 from the Database
        //d in mads123 is changed to c. Password is now spelled wrong.
        User user = new User();
        user.setUserName("Mads");
        user.setPassword("macs123");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserPasswordOffLong()
    {
        //arrange - using username Mads and password mads123 from the Database
        //4 is appended to password, password is now too long.
        User user = new User();
        user.setUserName("Mads");
        user.setPassword("mads1234");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserPasswordOffShort()
    {
        //arrange - using username Mads and password mads123 from the Database
        //3 is removed from password, password is now too short.
        User user = new User();
        user.setUserName("Mads");
        user.setPassword("mads12");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserPasswordOffCapitalLetter()
    {
        //arrange - using username Mads and password mads123 from the Database
        //a in mads123 is capitalized. Password is now spelled wrong, but with the right letter.
        User user = new User();
        user.setUserName("Mads");
        user.setPassword("mAds123");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserPasswordOffWhiteSpace()
    {
        //arrange - using username Mads and password mads123 from the Database
        //a space is appended to the password
        User user = new User();
        user.setUserName("Mads");
        user.setPassword("mads123 ");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserPasswordOut()
    {
        //arrange - using username Mads and password mads123 from the Database
        //a completely wrong password is used.
        User user = new User();
        user.setUserName("Mads");
        user.setPassword("UlrIkkE49!9");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserUsernameOffSpelling()
    {
        //arrange - using username Mads and password mads123 from the Database
        //d in Mads is changed to c. Username is now spelled wrong.
        User user = new User();
        user.setUserName("Macs");
        user.setPassword("mads123");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserUsernameOffLong()
    {
        //arrange - using username Mads and password mads123 from the Database
        //4 is appended to username, username is now too long.
        User user = new User();
        user.setUserName("Mads4");
        user.setPassword("mads123");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserUsernameOffShort()
    {
        //arrange - using username Mads and password mads123 from the Database
        //s is removed from username, username is now too short.
        User user = new User();
        user.setUserName("Mad");
        user.setPassword("mads123");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserUsernameOffCapitalLetter()
    {
        //arrange - using username Mads and password mads123 from the Database
        //d in Mads is capitalized. Username is now spelled wrong, but with the right letter.
        User user = new User();
        user.setUserName("MaDs");
        user.setPassword("mads123");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserUsernameOffWhiteSpace()
    {
        //arrange - using username Mads and password mads123 from the Database
        //a space is appended to the username
        User user = new User();
        user.setUserName("Mads ");
        user.setPassword("mads123");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }

    @Test
    void verifyUserUsernameOut()
    {
        //arrange - using username Mads and password mads123 from the Database
        //a completely wrong username is used.
        User user = new User();
        user.setUserName("ulRiKKe");
        user.setPassword("mads123");

        //act
        DataAccessObject dao = new DataAccessObject();
        boolean result = dao.verifyUser(user);

        //assert
        assertFalse(result);
    }
}