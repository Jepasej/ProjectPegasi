package org.example.projectpegasi.BusinessService;

import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.Persistence.DataAccessObject;

/**
 *
 */
public class SwapRequestManager {

    public void createSwapRequest (int matchID) throws Exception {
        DataAccessObject dao = new DataAccessObject();
        Match match = dao.getAMatchID(matchID);

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

        }
        else{
            System.out.println("Match not found");
        }
    }
}
