package org.example.projectpegasi;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Holds the data to be shown in the MatchView table which contains the job title and company name
 * of the other profile in each match for the logged-in user.
 * Used for binding to TableView columns in the MatchView.fxml.
 */
public class MatchDetails {

    private final String jobTitle;
    private final String companyName;
    private final int matchID;

    /**
     * Constructor to create a MatchDetails object.
     * @param jobTitle The job title of the other profile in the match.
     * @param companyName The company name of the other profile.
     * @param matchID The match ID that links the two profiles.
     */
    public MatchDetails(String jobTitle, String companyName, int matchID) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.matchID = matchID;
    }

    /**
     * Returns the match ID of this match.
     * @return match ID as an int.
     */
    public int getMatchID() {
        return matchID;
    }

    /**
     * Returns the job title of the other profile in this match.
     * @return job title as a String.
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Returns the company name of the other profile in this match.
     * @return company name as a String.
     */
    public String getCompanyName() {
        return companyName;
    }
}
