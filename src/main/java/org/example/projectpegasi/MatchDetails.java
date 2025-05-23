package org.example.projectpegasi;

/**
 * Holds the data to be shown in the MatchView table which contains the job title and company name
 * of the other profile in each match for the logged-in user.
 * Used for binding to TableView columns in the MatchView.fxml.
 */
public class MatchDetails {

    private int matchID;
    private String jobTitle;
    private String companyName;

    public MatchDetails(String jobTitle, String companyName, int matchID) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.matchID = matchID;
    }

    public int getMatchID() {
        return matchID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
