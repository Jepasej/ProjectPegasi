package org.example.projectpegasi.DomainModels;

import java.util.Date;

public class Match
{
    private int matchID;
    private int profileAID;
    private int profileBID;
    private int stateID;
    private Date matchDAte;
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

    public Date getMatchDAte() {
        return matchDAte;
    }

    public void setMatchDAte(Date matchDAte) {
        this.matchDAte = matchDAte;
    }

    public Date getMatchResponseDate() {
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
