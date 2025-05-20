package org.example.projectpegasi.BusinessService;

public class SwapRequestManager {
    //Hent match, opret request, gem i db
    int MatchId;

    public void createSwapRequest (int MatchId){
        this.MatchId = MatchId;
    }

}
