package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.DomainModels.User;
import org.example.projectpegasi.Foundation.DBConnection;

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
    public boolean verifyUser(User user)
    {
        //TO BE IMPLEMENTED
        //take user
        //run stored provedure
        //return whether input matches record.
        return false;
    }
}
