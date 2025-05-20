package org.example.projectpegasi.DomainModels;

import java.sql.Date;

public class Match
{
    private int matchID;
    private int profileAID;
    private int profileBID;
    private int stateID;
    private Date matchDate;
    private Date matchResponseDate;
    private Date requestResponseDate;
    private Date swapResponseDate;

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public int getProfileAID() {
        return profileAID;
    }

    public void setProfileAID(int profileAID) {
        this.profileAID = profileAID;
    }

    public int getProfileBID() {
        return profileBID;
    }

    public void setProfileBID(int profileBID) {
        this.profileBID = profileBID;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public java.sql.Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDAte) {
        this.matchDate = matchDAte;
    }

    public java.sql.Date getMatchResponseDate() {
        return matchResponseDate;
    }

    public void setMatchResponseDate(Date matchResponseDate) {
        this.matchResponseDate = matchResponseDate;
    }

    public Date getRequestResponseDate() {
        return requestResponseDate;
    }

    public void setRequestResponseDate(Date requestResponseDate) {
        this.requestResponseDate = requestResponseDate;
    }

    public Date getSwapResponseDate() {
        return swapResponseDate;
    }

    public void setSwapResponseDate(Date swapResponseDate) {
        this.swapResponseDate = swapResponseDate;
    }


}
