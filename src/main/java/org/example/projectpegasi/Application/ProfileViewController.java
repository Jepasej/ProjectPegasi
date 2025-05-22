package org.example.projectpegasi.Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.Foundation.DBConnection;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DAO;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ProfileViewController
{

    //region FX Containers/Controls
    @FXML
    private Button exitProfileViewButt, showMoreMatchesButt, showMoreRequestButt;

    @FXML
    private Label profileNameLbl, jobTitleLbl, companyNameLbl, jobFunctionLbl, homeAddressLbl, wageLbl, payPrefLbl, distPrefLbl, swappingStatusLbl;

    @FXML
    private ListView<String> recentMatchesLV;

    @FXML
    private ListView<String> recentRequestLV;

    //endregion

    private int userID;

    @FXML
    public void swapStatusButtOnAction()
    {
        //Change the Swapping Status to true/false depending on what it is currently set to
    }


    /**
     * Sets the current user ID and initializes profile information and the display
     * of the most recent matches in the ListView.
     *
     * @param userID the ID of the currently logged-in user
     */
    public void setUserID(int userID)
    {
        this.userID = userID;
        getProfileInformation();
        loadRecentMatchesInListView();
    }

    private List<Match> recentMatchesList = new ArrayList<>();

    /**
     * Loads the two most recent matches and displays their job titles in the ListView.
     */
    private void loadRecentMatchesInListView()
    {
        // Use stored procedure to get 2 most recent matches
        DataAccessObject dao = new DataAccessObject();
        recentMatchesList = dao.getTwoNewestMatches();

        recentMatchesLV.getItems().clear();

        // Get job title for each match and add it to the ListView
        for (Match match : recentMatchesList)
        {
            String jobTitle = dao.getJobTitleByProfileID(match.getProfileBID());
            recentMatchesLV.getItems().add(jobTitle);
        }
    }

    private void loadRecentRequestsInListView()
    {
        //Use stored procedure to sort by requested date and show the 2 most recent

    }

    /**
     * Reads the profile information from our database with a Callable statement
     * the script joins job function and company tables to get all the information
     */
    private void getProfileInformation() {
        DAO dao = new DataAccessObject();

        int profileID = dao.getProfileID(userID);
        List<String> profileInfo = dao.getProfileInformation(profileID);

        if (!profileInfo.isEmpty()) {
            // Set data from the list into the labels
            profileNameLbl.setText(profileInfo.get(0));
            jobTitleLbl.setText(profileInfo.get(1));
            jobFunctionLbl.setText(profileInfo.get(2));
            companyNameLbl.setText(profileInfo.get(3));
            homeAddressLbl.setText(profileInfo.get(4));
            wageLbl.setText(profileInfo.get(5));
            payPrefLbl.setText(profileInfo.get(6));
            distPrefLbl.setText(profileInfo.get(7));
            swappingStatusLbl.setText(profileInfo.get(8));
        }

    }
    /**
     * Change scene to incoming view
     */
    public void onShowMoreRequestButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.IncomingRequestView);
    }

    /**
     * Change scene to match view
     */
    public void onShowMoreMatchesButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.MatchView);
    }

    /**
     * Go to edit view
     */
    @FXML
    protected void editProfileButtOnAction()
    {
        HelloApplication.changeScene(ControllerNames.EditProfileView);
    }
}
