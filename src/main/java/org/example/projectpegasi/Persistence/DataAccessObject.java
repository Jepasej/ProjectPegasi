package org.example.projectpegasi.Persistence;

import org.example.projectpegasi.DomainModels.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataAccessObject
{
    //Hent data fra match via matchId
    public Match readAMatchID(int AmatchID) throws Exception
    {
        int matchID = 0;


        String sql = "SELECT * FROM tblMatches WHERE fldMatchID=?";
        Connection conn = ;//Mangler metoden fra DBConnection
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        Match match = new Match();
        match.setMatchID(rs.getInt(1));

        return Match;
    }
}
