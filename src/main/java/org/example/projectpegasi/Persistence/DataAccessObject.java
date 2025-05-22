package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.DomainModels.User;
import org.example.projectpegasi.Foundation.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject implements DAO
{
    /**
     * Retrieves a match from our database based on the given match ID
     * @param matchID ID of the match to retrieve
     * @return a match object if found or null if none found.
     * @throws Exception if a database access error occurs.
     */
    public Match readAMatchID(int matchID) throws Exception
    {
        Connection conn = DBConnection.getInstance().getConnection();
        CallableStatement stmt = conn.prepareCall("{call readAMatchID(?)}");
        stmt.setInt(1, matchID);
        stmt.setInt(2, 1);
        ResultSet rs = stmt.executeQuery();

        boolean hasMatch = false;
        Match match = null;
        //If match found
        while (rs.next())
        {
            match = new Match();
            hasMatch = true;
            match.setMatchID(rs.getInt(1));
            match.setProfileAID(rs.getInt(2));
            match.setProfileBID(rs.getInt(3));
            match.setStateID(rs.getInt(4));
            match.setMatchDate(rs.getDate(5));
            match.setMatchResponseDate(rs.getDate(6));
        }

        //If match not found
        if (!hasMatch)
        {
            System.out.println("No match found");
        }

        return match;
    }

    /**
     * Saves a swap request as a new entry in our database when a profile has accepted a match
     * @param request The swap request to be saved
     * @throws Exception if database connection fails
     */
    public void saveSwapRequest(SwapRequest request) throws Exception
    {
        Connection conn = DBConnection.getInstance().getConnection();
        CallableStatement stmt = conn.prepareCall("{call saveSwapRequest(?,?,?,?,?,?)}");
        stmt.setInt(1, request.getMatchId());
        stmt.setInt(2, request.getProfileAId());
        stmt.setInt(3, request.getProfileBId());
        stmt.setInt(4, 2);
        stmt.setDate(5, request.getMatchDate());
        stmt.setDate(6, request.getMatchDateResponse());
        stmt.executeUpdate();
        conn.close();
    }

    /**
     * Deletes a match entry from the database based on the given match ID
     * @param matchID the ID match to delete
     * @throws Exception if database access happens
     */
    public void declineMatch(int matchID) throws Exception{
        Connection conn = DBConnection.getInstance().getConnection();
        CallableStatement stmt = conn.prepareCall("{call DeclineMatchByID(?)}");
        stmt.setInt(1, 4);
        stmt.setInt(2, matchID);
        stmt.executeUpdate();
        conn.close();
    }

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
    public void createUser(User u)
    {
        String sql = " { call NewUser(?,?) } ";
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, u.getUserName());
            cs.setString(2, u.getPassword());
            cs.executeQuery();

            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }

   //     createUserProfile(u.getProfile());
    }

//    private void createUserProfile(Profile p)
//    {
//        String sql = " { call NewUserProfile(?,?,?,?,?,?,?) } ";
//
//        try
//        {
//            Connection conn = DBConnection.getInstance().getConnection();
//
//            CallableStatement cs = conn.prepareCall(sql);
//            cs.setString(1, );
//            cs.setString(2, );
//            cs.executeQuery();
//
//            conn.close();
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//        catch(ClassNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean checkUsernameIsUnique(String name)
    {
        String sql = " { call UserNameUniqeness(?) } ";
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, name);
            ResultSet rs = cs.executeQuery();

            rs.next();
            String result = rs.getString(1);

            conn.close();

            if(result.equalsIgnoreCase("true"))
                return true;
            else if(result.equalsIgnoreCase("false"))
                return false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method for verification of a user against the database records.
     * @param user object holding a username and a password.
     * @return true if username and password match, false if not.
     */
    @Override
    public boolean verifyUser(User user)
    {
        String sql = " { call CheckPassword(?,?) } ";
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, user.getUserName());
            cs.setString(2, user.getPassword());
            ResultSet rs = cs.executeQuery();

            rs.next();
            String result = rs.getString(1);

            conn.close();

            if(result.equalsIgnoreCase("true"))
                return true;
            else if(result.equalsIgnoreCase("false"))
                return false;


        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method for getting a profiles information from the profileID
     * @param profileID associated to existing profiles
     * @return a List of all the desired records
     */
    @Override
    public List<String> getProfileInformation(int profileID)
    {
        List<String> profileInfo = new ArrayList<>();
        String query = "{call ReadProfileByID(?)}"; // JDBC Escape Syntax

        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement clStmt = conn.prepareCall(query);

            clStmt.setInt(1, profileID);

            ResultSet rs = clStmt.executeQuery();

            while (rs.next())
            {
                // Get data from result set
                String fullName = rs.getString("fldFullName");
                String jobTitle = rs.getString("fldJobTitle");
                String jobFunction = rs.getString("fldFunction");
                String companyName = rs.getString("fldCompanyName");
                String homeAddress = rs.getString("fldHomeAddress");
                String wage = rs.getString("fldWage");
                String payPref = rs.getString("fldPayPref");
                String distPref = rs.getString("fldDistPref");
                String swappingStatus = rs.getString("fldSwappingStatus");

                // Set data from result set into the labels
                profileInfo.add(fullName);
                profileInfo.add(jobTitle);
                profileInfo.add(jobFunction);
                profileInfo.add(companyName);
                profileInfo.add(homeAddress);
                profileInfo.add(wage);
                profileInfo.add(payPref);
                profileInfo.add(distPref);
                profileInfo.add(swappingStatus);
            }
            conn.close();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return profileInfo;
    }

    /**
     * Method for getting the users ID through the username
     * @param userName reads the inputted username
     * @return the userID
     */
    @Override
    public int getUserID(String userName)
    {
        String query = "{call GetUserID(?)}";

        try{
            Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement clStmt = conn.prepareCall(query);

            clStmt.setString(1, userName);

            ResultSet rs = clStmt.executeQuery();

            if(rs.next())
            {
                return rs.getInt("fldUserID");
            }

        } catch (SQLException| ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * Method for getting the ProfileID from the userID
     * @param userID takes the userID
     * @return the profileID
     */
    @Override
    public int getProfileID(int userID)
    {
        String query = "{call GetProfileID(?)}";

        try{
            Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement clStmt = conn.prepareCall(query);

            clStmt.setInt(1, userID);

            ResultSet rs = clStmt.executeQuery();

            if(rs.next())
            {
                return rs.getInt("fldUserID");
            }

        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

}
