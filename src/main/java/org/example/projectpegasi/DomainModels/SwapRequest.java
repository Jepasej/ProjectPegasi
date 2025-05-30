package org.example.projectpegasi.DomainModels;

import java.sql.Date;

/**
 * Represents a swaprequest when a user has accepted a match
 */
public class SwapRequest
{
    private int profileAId;
    private int profileBId;
    private int matchId;
    private int senderProfileId;
    private int stateId;
    private Date matchDate;
    private Date matchDateResponse;
    private Date requestDateResponse;


    public int getProfileAId() {
        return profileAId;
    }

    public void setProfileAId(int profileAId) {
        this.profileAId = profileAId;
    }

    public int getProfileBId() {
        return profileBId;
    }

    public void setProfileBId(int profileBId) {
        this.profileBId = profileBId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getSenderProfileId() {
        return senderProfileId;
    }

    public void setSenderProfileId(int senderProfileId) {
        this.senderProfileId = senderProfileId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public Date getMatchDateResponse() {
        return matchDateResponse;
    }

    public void setMatchDateResponse(Date matchDateResponse) {
        this.matchDateResponse = matchDateResponse;
    }

    public Date getRequestDateResponse() {
        return requestDateResponse;
    }

    public void setRequestDateResponse(Date requestDateResponse) {
        this.requestDateResponse = requestDateResponse;
    }
}
