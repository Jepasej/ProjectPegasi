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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     *
     */
    private void getProfileInformation()
    {
        Profile profile = new Profile();

        int profileID = profile.getProfileID(); // get profile ID from profile model

        String query = "{call ReadProfileByID(?)}"; // JDBC Escape Syntax

        try{
            Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement stmt = conn.prepareCall(query);

            stmt.setInt(1, profileID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                // Get data from result set
                String fullName = rs.getString("fldFullName");
                String jobTitle = rs.getString("fldJobTitle");
                String jobFunction = rs.getString("fldJobFunction");
                String companyName = rs.getString("fldCompanyName");
                String homeAddress = rs.getString("fldHomeAddress");
                String wage = rs.getString("fldWage");
                String payPref = rs.getString("fldPayPref");
                String distPref = rs.getString("fldDistPref");
                String swappingStatus = rs.getString("fldSwappingStatus");

                // Set data from result set in labels
                profileNameLbl.setText(fullName);
                jobTitleLbl.setText(jobTitle);
                jobFunctionLbl.setText(jobFunction);
                companyNameLbl.setText(companyName);
                homeAddressLbl.setText(homeAddress);
                wageLbl.setText(wage);
                payPrefLbl.setText(payPref);
                distPrefLbl.setText(distPref);
                swappingStatusLbl.setText(swappingStatus);
            }

        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void onShowMoreMatchesButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.MatchView);
    }

    public void onShowMoreRequestButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.IncomingRequestView);
    }

}
