package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.DomainModels.User;
import org.example.projectpegasi.Foundation.DBConnection;

import java.sql.*;

public class DataAccessObject implements DAO
{
    /**
     * Retrieved a match from our database based on the given match ID
     * @param AmatchID ID of the match to retrieve
     * @return a match object if found or null if none found.
     * @throws Exception if a database access error occurs.
     */
    public Match getAMatchID(int AmatchID) throws Exception
    {
        int matchID = 0;

        // SQL query to retrieve the match ID info based on the match ID
        String sql = "SELECT * FROM tblMatches WHERE fldMatchID=?";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, AmatchID);
        ResultSet rs = pstm.executeQuery();

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
        String sql = "Insert into tblMatches (fldMatchID, fldProfileAID, fldProfileBID, fldStateID, fldMatchDate, fldMatchResponseDate) values(?,?,?,?,?,?)";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, request.getMatchId());
        pstm.setInt(2, request.getProfileAId());
        pstm.setInt(3, request.getProfileBId());
        pstm.setInt(4, request.getStateId());
        pstm.setDate(5, request.getMatchDate());
        pstm.setDate(6, request.getMatchDateResponse());
        pstm.executeUpdate();
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

    @Override
    public String getPassword(int UserID)
    {
        String sql = " { call sp_GetUserPasswordByID(?,?) } ";
        String password = null;

        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, UserID);
            cs.registerOutParameter(2, java.sql.Types.NVARCHAR);

            cs.execute();

            password = cs.getString(2);

            return password;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void changePassword(String text, int UserID) throws SQLException, ClassNotFoundException
    {
        try
        {
            String query = "{call spUpdatePassword(?,?)}";
            Connection conn = DBConnection.getInstance().getConnection();

            CallableStatement stmt = conn.prepareCall(query);
            System.out.println("Connected to change");

            stmt.setInt(1, UserID);
            System.out.println("Change password from " + UserID);
            stmt.setString(2, text);
            System.out.println("Change password to " + text);
            stmt.execute();

            System.out.println("Executed statement");

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }


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
}
