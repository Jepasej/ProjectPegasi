package org.example.projectpegasi;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Holds the data to be shown in the MatchView table which contains the job title and company name
 * of the other profile in each match for the logged-in user.
 * Used for binding to TableView columns in the MatchView.fxml.
 */
public class MatchDetails {

    private SimpleIntegerProperty matchID;
    private SimpleStringProperty jobTitle;
    private SimpleStringProperty companyName;

    public MatchDetails(String jobTitle, String companyName, int matchID) {
        this.jobTitle = new SimpleStringProperty(jobTitle);
        this.companyName = new SimpleStringProperty(companyName);
        this.matchID = new SimpleIntegerProperty(matchID);
    }

    public int getMatchID() {
        return matchID.get();
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getJobTitle() {
        return jobTitle.get();
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle.set(jobTitle);
    }

    public SimpleStringProperty jobTitleProperty() {
        return jobTitle;
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public SimpleIntegerProperty matchIDProperty() {
        return matchID;
    }

}
