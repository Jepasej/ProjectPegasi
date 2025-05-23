package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DAO;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.ArrayList;
import java.util.List;


public class ProfileViewController
{

    //region FX Containers/Controls
    @FXML
    private Button exitProfileViewButt, showMoreMatchesButt, showMoreRequestButt, swapStatusButt;

    @FXML
    private Label profileNameLbl, jobTitleLbl, companyNameLbl,
            jobFunctionLbl, homeAddressLbl, wageLbl,
            payPrefLbl, distPrefLbl, swappingStatusLbl;

    @FXML
    private ListView<String> recentMatchesLV;

    @FXML
    private ListView<String> recentRequestLV;

    //endregion

    private List<Match> recentMatchesList = new ArrayList<>();

    @FXML
    public void initialize()
    {
        int userID = MainViewController.getCurrentUserID();
        loadRecentMatchesInListView();
        loadRecentRequestsInListView();
        if(userID != 0)
        {
            getProfileInformation(userID);
        }
    }

    @FXML
    public void swapStatusButtOnAction()
    {
        //Change the Swapping Status to true/false depending on what it is currently set to
        DAO dao = new DataAccessObject();
        int userID = MainViewController.getCurrentUserID();
        int profileID = dao.getProfileID(userID);

        boolean currentSwappingStatus = swappingStatusLbl.getText().equals("Interested");
        boolean newSwappingStatus = !currentSwappingStatus;

        boolean isUpdated = dao.updateSwappingStatus(profileID, newSwappingStatus);

        if (isUpdated)
        {
            //Shorthand notation If-else statement - (Condition) ? If : Else
            swappingStatusLbl.setText(newSwappingStatus ? "Interested" : "Not Interested");
        } else
        {
            System.out.println("Failed to update swapping status");
        }
    }

    /**
     * Loads the two most recent matches and displays their job titles in the ListView.
     */
    private void loadRecentMatchesInListView ()
    {
        // Use stored procedure to get 2 most recent matches
        int userID = MainViewController.getCurrentUserID();
        DataAccessObject dao = new DataAccessObject();
        recentMatchesList = dao.getTwoNewestMatchesByProfileID(userID);

        recentMatchesLV.getItems().clear();

        // Get job title for each match and add it to the ListView
        for (Match match : recentMatchesList)
        {
            String jobTitle = dao.getJobTitleByProfileID(match.getProfileBID());
            recentMatchesLV.getItems().add(jobTitle);
        }
    }

    /**
     * Loads the two most recent requests and displays their job titles in the ListView.
     */
    private void loadRecentRequestsInListView ()
    {
        // Use stored procedure to get 2 most recent requests
        int userID = MainViewController.getCurrentUserID();
        DataAccessObject dao = new DataAccessObject();
        List<Match> recentRequestsList = dao.getTwoNewestRequestsByProfileID(userID);
        recentRequestLV.getItems().clear();

         // Get job title for each request and add it to the ListView
        for (Match match : recentRequestsList)
        {
            String jobTitle = dao.getJobTitleByProfileID(match.getProfileAID());
            recentRequestLV.getItems().add(jobTitle);
        }
    }

    /**
     * Reads the profile information from our database with a Callable statement
     * the script joins job function and company tables to get all the information
     */
    private void getProfileInformation ( int userID)
    {
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

            int swappingStatusBit = Integer.parseInt(profileInfo.get(8));
            //Shorthand notation If-else statement - (Condition) ? If : Else
            String swappingStatusText = (swappingStatusBit == 1) ? "Interested" : "Not Interested";
            swappingStatusLbl.setText(swappingStatusText);
        }
    }

    /**
     * Change scene to incoming view
     */
    public void onShowMoreRequestButtonClick ()
    {
        HelloApplication.changeScene(ControllerNames.IncomingRequestView);
    }

    /**
     * Change scene to match view
     */
    public void onShowMoreMatchesButtonClick ()
    {
        HelloApplication.changeScene(ControllerNames.MatchView);
    }

    /**
     * Go to edit view
     */
    @FXML
    protected void editProfileButtOnAction ()
    {
        HelloApplication.changeScene(ControllerNames.EditProfileView);
    }
}
