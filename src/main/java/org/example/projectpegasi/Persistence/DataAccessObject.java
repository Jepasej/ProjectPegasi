package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.*;
import org.example.projectpegasi.Foundation.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Central DAO class responsible for all database communication in the system.
 * Handles user login, profile data, matches, swap requests, and administrative operations.
 * Uses stored procedures via CallableStatement and applies a singleton DBConnection.
 */
public class DataAccessObject implements DAO
{
    /**
     * Retrieves a match from our database based on the given match ID
     * @param matchID ID of the match to retrieve
     * @return a Match object if found, otherwise null.
     */
    public Match readAMatchID(int matchID)
    {
        Match match = null;
        try
        {
            CallableStatement stmt = DBConnection.getInstance().prepareCall("{call ReadMatchByID(?)}");
            stmt.setInt(1, matchID);
            ResultSet rs = stmt.executeQuery();

            boolean hasMatch = false;
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
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return match;
    }

    /**
     * Updates an existing match or request entry in the database based on an accept has been made.
     * Sets the match and response dates and updates the state.
     * @param request The swapRequest with updated match info.
     */
    public void saveSwapRequestAndSwapAccept(SwapRequest request)
    {
        try
        {
            CallableStatement stmt = DBConnection.getInstance().prepareCall("{call SaveSwapRequest(?,?,?,?,?,?,?,?)}");
            stmt.setInt(1, request.getMatchId());
            stmt.setInt(2, request.getProfileAId());
            stmt.setInt(3, request.getProfileBId());
            stmt.setInt(4, request.getStateId());
            stmt.setDate(5, request.getMatchDate());
            stmt.setDate(6, request.getMatchDateResponse());
            stmt.setDate(7, request.getRequestDateResponse());
            stmt.setInt(8, request.getSenderProfileId());
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the match in the database to state 4 (denied) based on the given match ID.
     * Triggered when a user declines a match in the UI.
     * @param matchID the ID of the match that has been decline
     */
    public void declineMatchAndRequest(int matchID) {
        try
        {
            CallableStatement stmt = DBConnection.getInstance().prepareCall("{call DeclineMatchByID(?)}");
            stmt.setInt(1, matchID);
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Permanently deletes a request from the database based on the given matchID
     * @param matchID The ID match to be deleted
     */
    public void deleteRequest(int matchID) {
        try
        {
            CallableStatement stmt = DBConnection.getInstance().prepareCall("{call DeleteRequestByMatchID(?)}");
            stmt.setInt(1, matchID);
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves all matches for the logged-in profile from the database (state 1 = Match).
     * @param profileID The ID of the logged-in profile.
     * @return all the matches for this profile with state 1 in the database
     */
    public List<Match> getMatchesForProfile(int profileID) {
        List<Match> matches = new ArrayList<>();
        try
        {
            CallableStatement stmt = DBConnection.getInstance().prepareCall("{call GetMatchesForProfile(?)}");
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

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return matches;
    }

    /**
     * Retrieves all incoming requests for the logged-in profile from the database (state 2 = request).
     * Which has been sent to the logged-in user from other profiles.
     * @param profileID The ID of the logged-in profile.
     * @return all the incoming requests from other profiles for the logged in profile
     * with state 2 in the database.
     */
    public List<Match> getIncomingRequests(int profileID, int senderProfileID) {
        List<Match> matches = new ArrayList<>();
        try
        {
            CallableStatement stmt = DBConnection.getInstance().prepareCall("{call GetIncomingRequestsForProfile(?,?)}");
            stmt.setInt(1, profileID);
            stmt.setInt(2, senderProfileID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Match match = new Match();
                // Map each column by name – ensures correctness even if column order changes
                match.setMatchID(rs.getInt("fldMatchID"));
                match.setProfileAID(rs.getInt("fldProfileAID"));
                match.setProfileBID(rs.getInt("fldProfileBID"));
                match.setStateID(rs.getInt("fldStateID"));
                match.setSenderProfileID(rs.getInt("fldSenderProfileID"));
                match.setMatchDate(rs.getDate("fldMatchDate"));
                match.setMatchResponseDate(rs.getDate("fldMatchResponseDate"));
                match.setRequestResponseDate(rs.getDate("fldRequestResponseDate"));
                match.setSwapResponseDate(rs.getDate("fldSwapResponseDate"));
                matches.add(match);

            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        { try
        {
            DBConnection.getInstance().close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        }
        return matches;
    }

    /**
     * Retrieves all outgoing requests for the logged-in profile from the database (state 2 = request)
     * which the logged-in user has sent to others.
     * @param profileID The ID of the logged-in profile.
     * @param senderProfileID gets the requests that has been sent by the logged-in user
     * @return all the outgoing requests from the logged in profile with state 2 in the database
     */
    public List<Match> getOutgoingRequests(int profileID, int senderProfileID) {

        List<Match> matches = new ArrayList<>();
        try
        {
            CallableStatement stmt = DBConnection.getInstance().prepareCall("{call GetOutgoingRequestsForProfile(?,?)}");
            stmt.setInt(1, profileID);
            stmt.setInt(2, senderProfileID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Match match = new Match();
                // Map each column by name – ensures correctness even if column order changes
                match.setMatchID(rs.getInt("fldMatchID"));
                match.setProfileAID(rs.getInt("fldProfileAID"));
                match.setProfileBID(rs.getInt("fldProfileBID"));
                match.setStateID(rs.getInt("fldStateID"));
                match.setSenderProfileID(rs.getInt("fldSenderProfileID"));
                match.setMatchDate(rs.getDate("fldMatchDate"));
                match.setMatchResponseDate(rs.getDate("fldMatchResponseDate"));
                match.setRequestResponseDate(rs.getDate("fldRequestResponseDate"));
                match.setSwapResponseDate(rs.getDate("fldSwapResponseDate"));
                matches.add(match);
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        { try
        {
            DBConnection.getInstance().close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        }
        return matches;
    }

    /**
     *  Retrieves job title and company information for a profile, used to display match info in the UI.
     * @param profileID the ID of the profile to get information for
     * @return a Profile object containing job title and associated company name.
     */
    public Profile getAttributesForMatchView(int profileID) {
        // Creates a Profile object and links a Company object with only its ID set
        Profile profile = null;

        try
        {
            CallableStatement stmt = DBConnection.getInstance().prepareCall("{call GetAttributesForMatchView(?)}");
            stmt.setInt(1, profileID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                profile = new Profile();
                profile.setJobTitle(rs.getString("fldJobTitle"));
                int companyID = rs.getInt("fldCompanyID");
                String companyName = rs.getString("fldCompanyName");
                Company company = new Company();
                company.setID(companyID);
                company.setName(companyName);
                profile.setCompany(company);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
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

    /**
     * Reads all records of a specific type from the database using a stored procedure.
     * @param object
     * @return
     */
    @Override
    public ArrayList readAll(Object object)
    {
        ArrayList list = new ArrayList();

        if(object instanceof JobFunction)
        {
            String sql = " { call spReadAllJobFunctions() } ";
            try
            {
                CallableStatement cs = DBConnection.getInstance().prepareCall(sql);
                ResultSet rs = cs.executeQuery();

                while(rs.next())
                {
                    JobFunction jobFunction = new JobFunction();
                    jobFunction.setJobFunction(rs.getString(1));
                    list.add(jobFunction);
                }

            }
            catch (SQLException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally
            {
                try
                {
                    DBConnection.getInstance().close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            return list;
        }
        if(object instanceof Company)
        {
            String sql = " { call spReadAllCompanies() } ";
            try
            {
                CallableStatement cs = DBConnection.getInstance().prepareCall(sql);
                ResultSet rs = cs.executeQuery();

                while(rs.next())
                {
                    Company c = new Company();
                    c.setID(rs.getInt(1));
                    c.setName(rs.getString(2));
                    c.setAddress(rs.getString(3));

                    list.add(c);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally
            {
                try
                {
                    DBConnection.getInstance().close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
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

    /**
     * Creates a new User in our DB through Stored Procedure "NewUser"
     * @param u
     */
    @Override
    public void createUser(User u)
    {
        String sql = " { call NewUser(?,?) } ";
        try
        {
            CallableStatement cs = DBConnection.getInstance().prepareCall(sql);
            cs.setString(1, u.getUserName());
            cs.setString(2, u.getPassword());
            cs.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        createUserProfile(u);
    }

    /**
     * Creates a new PROFILE in our database with the specified values, User and Profile are diffrent from eachother
     * While Users are our Login credentials, Profile are the stored information on the users.
     * @param u
     */
    private void createUserProfile(User u)
    {
        Profile p = u.getProfile();
        int userID = getUserID(u.getUserName());
        int companyID = getCompanyID(p.getCompany().getName());

        String sql = " { call spCreateProfile(?,?,?,?,?,?,?,?,?) } ";

        try
        {
            CallableStatement cs = DBConnection.getInstance().prepareCall(sql);
            cs.setString(1, p.getFullName());
            cs.setString(2, p.getJobTitle());
            cs.setString(3, p.getHomeAddress());
            cs.setInt(4, companyID);
            cs.setInt(5, p.getWage());
            cs.setInt(6, p.getPayPref());
            cs.setString(7, p.getDistPref());
            cs.setInt(8, userID);
            cs.setString(9, p.getJobFunction());
            cs.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks through our database if the username the user has choosen is unique.
     * @param name
     * @return
     */
    @Override
    public boolean checkUsernameIsUnique(String name)
    {
        String sql = " { call UserNameUniqueness(?) } ";
        try
        {
            CallableStatement cs = DBConnection.getInstance().prepareCall(sql);
            cs.setString(1, name);
            ResultSet rs = cs.executeQuery();

            rs.next();
            String result = rs.getString(1);

            if(result.equalsIgnoreCase("true"))
                return true;
            else if(result.equalsIgnoreCase("false"))
                return false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Method to get the password from the database, gets used in EditProfile to compare the actual password with the
     * user.
     * @param UserID Uses this to know which user to get the data from
     * @return password for later use.
     */
    @Override
    public String getPassword(int UserID)
    {
        String sql = " { call sp_GetUserPasswordByID(?,?) } ";
        String password;
        try
        {
            CallableStatement cs = DBConnection.getInstance().prepareCall(sql);
            cs.setInt(1, UserID);
            cs.registerOutParameter(2, Types.NVARCHAR);
            cs.execute();
            password = cs.getString(2);
            return password;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Changes the password in our database, gets used in EditProfileViewController
     * @param text
     * @param UserID
     */
    @Override
    public void changePassword(String text, int UserID)
    {
        try
        {
            String query = "{call spUpdatePassword(?,?)}";

            CallableStatement stmt = DBConnection.getInstance().prepareCall(query);
            //System.out.println("Connected to change");
            stmt.setInt(1, UserID);
            //System.out.println("Change password from " + UserID);
            stmt.setString(2, text);
            //System.out.println("Change password to " + text);
            stmt.execute();
            //System.out.println("Executed statement");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Safes the data the user has given in the EditProfileView to our database.
     * @param userID
     * @param newFullName
     * @param jobTitle
     * @param homeAddress
     * @param company
     * @param minSalary
     * @param distancePref
     */
    @Override
    public void SafeEditProfileData(int userID, String newFullName, String jobTitle, String homeAddress, String company, String minSalary, String distancePref)
    {
        try
        {
            String query = "{call spUpdateProfileData(?,?,?,?,?,?,?)}";
            CallableStatement cs = DBConnection.getInstance().prepareCall(query);
            cs.setInt(1, userID);
            cs.setString(2, newFullName);
            cs.setString(3, jobTitle);
            cs.setString(4, homeAddress);
            cs.setString(5, company);
            cs.setString(6, minSalary);
            cs.setString(7, distancePref);
            cs.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
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
            CallableStatement cs = DBConnection.getInstance().prepareCall(sql);
            cs.setString(1, user.getUserName());
            cs.setString(2, user.getPassword());
            ResultSet rs = cs.executeQuery();

            rs.next();
            String result = rs.getString(1);

            if(result.equalsIgnoreCase("true"))
                return true;
            else if(result.equalsIgnoreCase("false"))
                return false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
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
            CallableStatement clStmt = DBConnection.getInstance().prepareCall(query);

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
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
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
            CallableStatement clStmt = DBConnection.getInstance().prepareCall(query);

            clStmt.setString(1, userName);

            ResultSet rs = clStmt.executeQuery();

            if(rs.next())
            {
                return rs.getInt("fldUserID");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
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

        try
        {
            CallableStatement cs = DBConnection.getInstance().prepareCall(query);

            cs.setString(1, companyName);

            ResultSet rs = cs.executeQuery();

            if(rs.next())
            {
                return rs.getInt("fldCompanyID");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
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
            CallableStatement clStmt = DBConnection.getInstance().prepareCall(query);

            clStmt.setInt(1, userID);

            ResultSet rs = clStmt.executeQuery();

            if(rs.next())
            {
                int profileID = rs.getInt("fldProfileID");
                return profileID;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * Updates the swappingStatus in our database so our algorithm know to match or not to match the profile
     * with other profiles.
     * @param profileID
     * @param swappingStatus
     * @return
     */
    @Override
    public boolean updateSwappingStatus(int profileID, Boolean swappingStatus)
    {
        String query = "{call UpdateSwappingStatus(?,?)}";

        try{
            CallableStatement clStmt = DBConnection.getInstance().prepareCall(query);

            clStmt.setInt(1, profileID);
            clStmt.setBoolean(2, swappingStatus);

            int rowsAffected = clStmt.executeUpdate();
            return rowsAffected > 0;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    //The following three methods are an theoretical implementation of creating a database user, granting the user
    //admin rights and revoking the same rights again.
    /**
     * Creates a user in our database with admin access, so that employees and/or administrators can edit/change the db
     * For future use to create
     * @param adminName
     * @param adminPassword
     */
    public void createDBUserAdmin(String adminName, String adminPassword)
    {
        String query = "{call CreateJobSwapAdmin(?,?)}";
        String Username = adminName;
        String Password = adminPassword;
        try
        {
            CallableStatement cs = DBConnection.getInstance().prepareCall(query);

            cs.setString(1, Username);
            cs.setString(2, Password);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void grantDBAdminRoles(String username)
    {
        String query = "{GrantAdminRoles(?)}";
        String Username = username;

        try
        {
            CallableStatement cs = DBConnection.getInstance().prepareCall(query);

            cs.setString(1, Username);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void revokeDBAdminRights(String adminName)
    {
        String query = "{call RevokeJobSwapAdminRoles(?)}";
        String nameToBeRevoked = adminName;
        try
        {
            CallableStatement cs = DBConnection.getInstance().prepareCall(query);

            cs.setString(1, nameToBeRevoked);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        } finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves the two most recent requests for the given profile.
     * where the user has received an invitation to swap.
     *
     * @param profileID The ID of the profile for which to retrieve requests.
     * @return A list containing up to two request objects representing incoming requests.
     */
    @Override
    public List<Match>getTwoNewestRequestsByProfileID(int profileID)
    {
        List<Match> requests = new ArrayList<>();
        String query = "{call GetTwoNewestRequestsByProfileID(?)}";

        try
        {
            Connection conn = DBConnection.getInstance();
            CallableStatement clStmt = conn.prepareCall(query);
            clStmt.setInt(1, profileID);
            ResultSet rs = clStmt.executeQuery();

            // Process the result set and build Match object
            while (rs.next())
            {
                Match match = new Match();
                match.setMatchID(rs.getInt("fldMatchID"));
                match.setProfileAID(rs.getInt("fldProfileAID"));
                match.setProfileBID(rs.getInt("fldProfileBID"));
                match.setStateID(rs.getInt("fldStateID"));
                match.setMatchDate(rs.getDate("fldMatchDate"));
                match.setMatchResponseDate(rs.getDate("fldMatchResponseDate"));
                requests.add(match);
            }
            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
        return requests;
    }

    /**
     * Retrieves the two most recent matches for the given profile.
     * @return A list containing up to two Match objects or an empty list if no matches are found.
     * @param profileID The ID of the profile for which to retrieve matches.
     */
    @Override
    public List<Match> getTwoNewestMatchesByProfileID(int profileID)
    {
        List<Match> matches = new ArrayList<>();
        String query = "{call GetTwoNewestMatchesByProfileID(?)}";

        try
        {
            Connection conn = DBConnection.getInstance();
            CallableStatement clStmt = conn.prepareCall(query);
            clStmt.setInt(1, profileID);
            ResultSet rs = clStmt.executeQuery();

            while(rs.next())
            {
                Match match = new Match();
                match.setMatchID(rs.getInt("fldMatchID"));
                match.setProfileAID(rs.getInt("fldProfileAID"));
                match.setProfileBID(rs.getInt("fldProfileBID"));
                match.setStateID(rs.getInt("fldStateID"));
                match.setMatchDate(rs.getDate("fldMatchDate"));
                match.setMatchResponseDate(rs.getDate("fldMatchResponseDate"));
                match.setSenderProfileID(rs.getInt("fldSenderProfileID"));
                matches.add(match);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return matches;
    }

    /**
     * Retrieves the job title of a profile based on the given profile ID.
     * Calls the stored procedure: GetJobTitleByProfileID
     * @param profileID the ID of the profile whose job title should be retrieved
     * @return the job title as a String, or null if no job title is found
     */
    @Override
    public String getJobTitleByProfileID(int profileID){
        String jobTitle = null;
        String query = "{call GetJobTitleByProfileID(?)}";

        try
        {
            Connection conn = DBConnection.getInstance();
            CallableStatement clStmt = conn.prepareCall(query);
            clStmt.setInt(1, profileID);
            ResultSet rs = clStmt.executeQuery();

            if(rs.next()) {
                jobTitle = rs.getString("fldJobTitle");
            }
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return jobTitle;
    }

    /**
     * Method to get all information on a profile and
     * extracts the job function and the pay preference
     *
     * @return a List of profiles with the specific information
     */
    @Override
    public List<Profile> getAllProfiles()
    {
        List<Profile> profiles = new ArrayList<>();
        String query = "{Call ReadAllProfilesWithJobFunctions()}";

        try{
            Connection conn = DBConnection.getInstance();
            CallableStatement clStmt = conn.prepareCall(query);
            ResultSet rs = clStmt.executeQuery();

            while(rs.next())
            {
                int profileID = rs.getInt("fldProfileID");
                String jobFunction = rs.getString("fldFunction");
                int payPref = rs.getInt("fldPayPref");

                Profile profile = new Profile(profileID, jobFunction, payPref);
                profiles.add(profile);
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return profiles;
    }

    /**
     * Method to save the match the algorithm makes.
     * @param profileAID First profile ID
     * @param profileBID Second Profile ID
     */
    @Override
    public void saveMatch(int profileAID, int profileBID)
    {
        String query = "{call SaveMatch(?, ?, 1, ?)}";

        try{
            Connection conn = DBConnection.getInstance();
            CallableStatement clStmt = conn.prepareCall(query);

            clStmt.setInt(1, profileAID);
            clStmt.setInt(2, profileBID);
            clStmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));

            clStmt.execute();

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                DBConnection.getInstance().close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
