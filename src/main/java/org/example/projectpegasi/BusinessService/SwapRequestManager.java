package org.example.projectpegasi.BusinessService;

import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.Persistence.DataAccessObject;


public class SwapRequestManager {
    /**
     * Creates a swaprequest when a user has accepted a match and sets state to "Request" (State = 2) in database
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
            swapRequest.setStateId(2);
            swapRequest.setMatchDate(match.getMatchDate());
            swapRequest.setMatchDateResponse(match.getMatchResponseDate());
            dao.saveSwapRequestAndSwapAccept(swapRequest);
        }
        else{
            System.out.println("Match not found");
        }
    }

    /**
     * Declines a match by updating its state to 'Declined' (state ID = 4) in the database.
     * Gets match data and updates the declined match using DataAccessObject.
     */
    public void declineMatchAndRequest (int matchID) throws Exception {
        DataAccessObject dao = new DataAccessObject();
        dao.declineMatchAndRequest(matchID);
    }

    /**
     * Accepts an incoming SwapRequest and makes it into a jobswap.
     * Updating it's state to "swap" (state = 3) in the database.
     * Gets match data and saves the jobswap using the DataAccessObject.
     */
    public void acceptSwapRequest (int matchID) throws Exception {
        DataAccessObject dao = new DataAccessObject();
        Match match = dao.readAMatchID(matchID);

        if( match!= null)
        {
            SwapRequest swapRequest = new SwapRequest();

            swapRequest.setMatchId(matchID);
            swapRequest.setProfileAId(match.getProfileAID());
            swapRequest.setProfileBId(match.getProfileBID());
            swapRequest.setStateId(3);
            swapRequest.setMatchDate(match.getMatchDate());
            swapRequest.setMatchDateResponse(match.getMatchResponseDate());
            dao.saveSwapRequestAndSwapAccept(swapRequest);
        }
        else{
            System.out.println("Request not found");
        }
    }

    /**
     * Deletes an entry from tbl Matches when a user deletes their own outgoing requests
     * Gets request data and deletes it using the DataAccessObject.
     */
    public void deleteSwapRequest (int matchID) throws Exception {
        DataAccessObject dao = new DataAccessObject();
        dao.deleteRequest(matchID);
    }
}