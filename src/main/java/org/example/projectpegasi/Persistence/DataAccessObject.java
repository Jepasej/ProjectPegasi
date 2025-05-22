package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.*;
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
        CallableStatement stmt = conn.prepareCall("{call ReadMatchByID(?)}");
        stmt.setInt(1, matchID);
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
     * Saves a swap request or a swap accept as a new entry in our database
     * when a profile has accepted a match
     * @param request The swap request to be saved
     * @throws Exception if database connection fails
     */
    public void saveSwapRequestAndSwapAccept(SwapRequest request) throws Exception
    {
        Connection conn = DBConnection.getInstance().getConnection();
        CallableStatement stmt = conn.prepareCall("{call SaveSwapRequest(?,?,?,?,?)}");
        stmt.setInt(1, request.getMatchId());
        stmt.setInt(2, request.getProfileAId());
        stmt.setInt(3, request.getProfileBId());
        stmt.setDate(5, request.getMatchDate());
        stmt.setDate(6, request.getMatchDateResponse());
        stmt.executeUpdate();
        conn.close();
    }

    /**
     * Deletes a match entry or jobSwapRequest from the UI based on the given match ID
     * @param matchID the ID match to decline
     * @throws Exception if database access error happens
     */
    public void declineMatchAndRequest(int matchID) throws Exception{
        Connection conn = DBConnection.getInstance().getConnection();
        CallableStatement stmt = conn.prepareCall("{call DeclineMatchByID(?)}");
        stmt.setInt(1, matchID);
        stmt.executeUpdate();
        conn.close();
    }

    /**
     * Deletes a request from the database based on the given matchID
     * @param matchID The ID match to be deleted
     * @throws Exception if data access error happens
     */
    public void deleteRequest(int matchID) throws Exception{
        Connection conn = DBConnection.getInstance().getConnection();
        CallableStatement stmt = conn.prepareCall("{call DeleteRequestByMatchID(?)}");
        stmt.setInt(1, matchID);
        stmt.executeUpdate();
        conn.close();
    }

    /**
     * Gets all matches for the logged in profile
     * @param profileID The profile for which to get matches for
     * @return all the matches for this profile
     * @throws Exception if data aceess error happens
     */
    public List<Match> getMatchesForProfile(int profileID) throws Exception{
        List<Match> matches = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();
        CallableStatement stmt = conn.prepareCall("{call GetMatchesForProfile(?)}");
        stmt.setInt(1, profileID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            Match match = new Match();
            match.setMatchID(rs.getInt(1));
            match.setProfileAID(rs.getInt(2));
            match.setProfileBID(rs.getInt(3));
            match.setStateID(rs.getInt(4));
            matches.add(match);

        }
        conn.close();
        return matches;
    }

    public Profile getAttributesForMatchView(int profileID) throws Exception{
        Connection conn = DBConnection.getInstance().getConnection();
        CallableStatement stmt = conn.prepareCall("{call GetAttributesForMatchView(?)}");
        stmt.setInt(1, profileID);
        ResultSet rs = stmt.executeQuery();
        Profile profile = null;
        while (rs.next()){
            profile = new Profile();
            profile.setJobTitle(rs.getString(1));

            int companyID = rs.getInt(2);
            Company company = new Company();
            company.setID(companyID);
            profile.setCompany(company);
        }
        return profile;
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
