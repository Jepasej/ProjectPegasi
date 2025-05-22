package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.*;
import org.example.projectpegasi.Foundation.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject implements DAO
{
    /**
     * Retrieved a match from our database based on the given match ID
     * @param AmatchID ID of the match to retrieve
     * @return a match object if found or null if none found.
     * @throws Exception if a database access error occurs.
     */
    public Match readAMatchID(int AmatchID) throws Exception
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

    /**
     * Deletes a match entry from the database based on the given match ID
     * @param matchID the ID match to delete
     * @throws Exception if database access happens
     */
    public void declineMatch(int matchID) throws Exception{
        String sql = "DELETE FROM tblMatches WHERE fldMatchID=?";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, matchID);
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
    public ArrayList readAll(Object object)
    {
        ArrayList list = new ArrayList();

        if(object instanceof JobFunction)
        {
            String sql = " { call spReadAllJobFunctions() } ";
            try
            {
                Connection conn = DBConnection.getInstance().getConnection();

                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery();

                while(rs.next())
                {
                    JobFunction jobFunction = new JobFunction();
                    jobFunction.setJobFunction(rs.getString(1));
                    list.add(jobFunction);
                }

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

            return list;
        }
        if(object instanceof Company)
        {
            String sql = " { call spReadAllCompanies() } ";
            try
            {
                Connection conn = DBConnection.getInstance().getConnection();

                CallableStatement cs = conn.prepareCall(sql);
                ResultSet rs = cs.executeQuery();

                while(rs.next())
                {
                    Company c = new Company();
                    c.setID(rs.getInt(1));
                    c.setName(rs.getString(2));
                    c.setAddress(rs.getString(3));

                    list.add(c);
                }

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

            return list;
        }

        return list;
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
            cs.execute();

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

        createUserProfile(u);
    }

    private void createUserProfile(User u)
    {
        Profile p = u.getProfile();
        int userID = getUserID(u.getUserName());
        int companyID = getCompanyID(p.getCompany().getName());

        String sql = " { call spCreateProfile(?,?,?,?,?,?,?,?) } ";

        try
        {
            Connection conn = DBConnection.getInstance().getConnection();

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, p.getFullName());
            cs.setString(2, p.getJobTitle());
            cs.setString(3, p.getJobTitle());
            cs.setInt(4, companyID);
            cs.setInt(5, p.getWage());
            cs.setInt(6, p.getPayPref());
            cs.setString(7, p.getDistPref());
            cs.setInt(8, userID);
            cs.execute();

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
    }

    @Override
    public boolean checkUsernameIsUnique(String name)
    {
        String sql = " { call UserNameUniqueness(?) } ";
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

        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement clStmt = conn.prepareCall(query);

            clStmt.setString(1, userName);

            ResultSet rs = clStmt.executeQuery();

            if(rs.next())
            {
                return rs.getInt("fldUserID");
            }
            conn.close();
        }
        catch (SQLException| ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Method for getting the company's ID through the company's name
     * @param companyName reads the inputted company name
     * @return the companyID
     */
    @Override
    public int getCompanyID(String companyName)
    {
        String query = "{call GetCompanyID(?)}";

        try{
            Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement cs = conn.prepareCall(query);

            cs.setString(1, companyName);

            ResultSet rs = cs.executeQuery();

            if(rs.next())
            {
                return rs.getInt("fldCompanyID");
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

        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement clStmt = conn.prepareCall(query);

            clStmt.setInt(1, userID);

            ResultSet rs = clStmt.executeQuery();

            if(rs.next())
            {
                int profileID = rs.getInt("fldProfileID");
                return profileID;
            }
            conn.close();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean updateSwappingStatus(int profileID, Boolean swappingStatus)
    {
        String query = "{call UpdateSwappingStatus(?,?)}";

        try{
            Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement clStmt = conn.prepareCall(query);

            clStmt.setInt(1, profileID);
            clStmt.setBoolean(2, swappingStatus);

            int rowsAffected = clStmt.executeUpdate();
            conn.close();
            return rowsAffected > 0;


        }catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
