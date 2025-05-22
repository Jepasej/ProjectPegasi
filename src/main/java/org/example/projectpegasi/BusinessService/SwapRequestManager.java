package org.example.projectpegasi.BusinessService;

import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.Persistence.DataAccessObject;


public class SwapRequestManager {
    /**
     * Creates a swaprequest when a user has accepted a match
     * Gets match data and saves the request using the DataAccessObject.
     */
    public void createSwapRequest (int matchID) throws Exception {
        DataAccessObject dao = new DataAccessObject();
        Match match = dao.readAMatchID(matchID);

        if( match!= null)
        {
            SwapRequest swapRequest = new SwapRequest();

            swapRequest.setMatchId(matchID);
            swapRequest.setProfileAId(match.getProfileAID());
            swapRequest.setProfileBId(match.getProfileBID());
            swapRequest.setStateId(match.getStateID());
            swapRequest.setMatchDateResponse(match.getMatchDate());
            swapRequest.setMatchDate(match.getMatchResponseDate());
            dao.saveSwapRequest(swapRequest);
            //Skal her skifte state i DB til Request
        }
        else{
            System.out.println("Match not found");
        }
    }

    /**
     * Deletes a swaprequest from UI when a user has declined a match
     * Gets match data and saves the declined match usind DataAccessObject.
     */
    public void declineMatch (int matchID) throws Exception {
        DataAccessObject dao = new DataAccessObject();
        dao.declineMatch( matchID);
    }

    // Her skal laves metoder til hvad der skal ske når man i
    // Incoming requests:
    // 1. Trykker på accept request - Hent MatchID fra tblMatches i DB og sørg for det bliver opdateret til
    // et jobswap - Ændre state til Swap? Sendes til HR?
    // 2. Trykker på Decline request - Hent MatchID fra tbl Matches i DB og gem entry i DB med state Denied.
    //
    // Outgoing Requests:
    // 1. Trykker på delete request - Hent MatchID fra tbl Matches i DB og slet entry i DB.
}