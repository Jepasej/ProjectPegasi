package org.example.projectpegasi.BusinessService;

import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.sql.Date;
import java.time.LocalDate;

/**
 * A class that handles logic related to swap, requests and matches based on user interaction in the UI
 * This class calls the DAO methods to update or retrieve match data in the database,
 * depending on whether a user accepts, declines, or deletes a request.
 */
public class SwapRequestManager {
    /**
     * Creates a swap request when a user accepts a match.
     * It updates the match state in the database to 'Request' (state = 2)
     * and sets the current date as the match response date.
     * It also sets the user which accepted the match to the sender of the request to be shown in either outgoing
     * request or incoming requests view.
     * @param matchID The ID of the match to update
     * @param senderProfileID The ID of the profile that accepted the match and initiated the swap request
     * @throws Exception If a database error occurs
     */
    public void createSwapRequest (int matchID, int senderProfileID) throws Exception {
        DataAccessObject dao = new DataAccessObject();
        Match match = dao.readAMatchID(matchID);

        if( match!= null)
        {
            SwapRequest swapRequest = new SwapRequest();
            swapRequest.setMatchId(match.getMatchID());
            swapRequest.setProfileAId(match.getProfileAID());
            swapRequest.setProfileBId(match.getProfileBID());
            swapRequest.setStateId(2);
            swapRequest.setSenderProfileId(senderProfileID);
            swapRequest.setMatchDate(match.getMatchDate());
            // Set today's date as the response date when user clicks 'accept'
            swapRequest.setMatchDateResponse(java.sql.Date.valueOf(LocalDate.now()));
            dao.saveSwapRequestAndSwapAccept(swapRequest);
        }
        else{
            System.out.println("Match not found");
        }
    }

    /**
     * Declines a match by updating its state to 'Denied' (state = 4) in the database.
     * Used when the user clicks '✘' to decline a suggested match.
     * @param matchID The ID of the match to decline
     * @throws Exception If a database error occurs
     */
    public void declineMatchAndRequest (int matchID) throws Exception {
        DataAccessObject dao = new DataAccessObject();
        dao.declineMatchAndRequest(matchID);
    }

    /**
     * Accepts an incoming SwapRequest and changes it into a jobswap.
     * Updating it's state to "swap" (state = 3) in the database.
     * This is when both users agree to swap jobs.
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
            swapRequest.setRequestDateResponse(Date.valueOf(LocalDate.now()));
            dao.saveSwapRequestAndSwapAccept(swapRequest);
        }
        else{
            System.out.println("Request not found");
        }
    }

    /**
     * Deletes an outgoing request from the system.
     * Used when a user regrets their send request and clicks 'delete'.
     * @param matchID The ID of the match/request to delete
     * @throws Exception If a database error occurs
     */
    public void deleteSwapRequest (int matchID) throws Exception {
        DataAccessObject dao = new DataAccessObject();
        dao.deleteRequest(matchID);
    }
}