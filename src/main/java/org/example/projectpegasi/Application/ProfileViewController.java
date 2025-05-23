package org.example.projectpegasi.Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.Foundation.DBConnection;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DAO;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

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

    @FXML
    public void initialize()
    {
        int userID = MainViewController.getCurrentUserID();
        if(userID != 0)
        {
            getProfileInformation(userID);
        }
    }

    @FXML
    protected void editProfileButtOnAction()
    {
        //Go to edit view
        HelloApplication.changeScene(ControllerNames.EditProfileView);
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

        if(isUpdated)
        {
            swappingStatusLbl.setText(newSwappingStatus ? "Interested" : "Not Interested");
        }
        else
        {
            System.out.println("Failed to update swapping status");
        }
    }

    private void loadRecentMatchesInListView()
    {
        //Use stored procedure to sort by match date and show the 2 most recent

    }

    private void loadRecentRequestsInListView()
    {
        //Use stored procedure to sort by requested date and show the 2 most recent

    }

    /**
     * Reads the profile information from our database with a Callable statement
     * the script joins job function and company tables to get all the information
     */
    private void getProfileInformation(int userID)
    {
        DAO dao = new DataAccessObject();

        int profileID = dao.getProfileID(userID);
        List<String> profileInfo = dao.getProfileInformation(profileID);

        if (!profileInfo.isEmpty()) {
            // Set data from the list into the labels
            profileNameLbl.setText(profileInfo.get(0));
            jobTitleLbl.setText(profileInfo.get(1));
            //jobFunctionLbl.setText(profileInfo.get(2));
            //companyNameLbl.setText(profileInfo.get(3));
            homeAddressLbl.setText(profileInfo.get(2));
            wageLbl.setText(profileInfo.get(3));
            payPrefLbl.setText(profileInfo.get(4));
            distPrefLbl.setText(profileInfo.get(5));

            int swappingStatusBit = Integer.parseInt(profileInfo.get(6));
            //Shorthand notation If-else statement - (Condition) ? If : Else
            String swappingStatusText = (swappingStatusBit == 1) ? "Interested" : "Not Interested";
            swappingStatusLbl.setText(swappingStatusText);
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
}
