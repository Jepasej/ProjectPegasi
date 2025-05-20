package org.example.projectpegasi.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.projectpegasi.Foundation.DBConnection;

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
    private Label profileNameLbl, jobTitleLbl, jobFunctionLbl, companyNameLbl, homeAddressLbl, wageLbl, payPrefLbl, distPrefLbl, swappingStatusLbl;

    @FXML
    private ListView<String> recentMatchesLV;

    @FXML
    private ListView<String> recentRequestLV;

    //endregion


    @FXML
    protected void exitProfileButtOnAction()
    {
        //Go back to main view

    }


    private void loadRecentMatchesInListView()
    {
        //Use stored procedure to sort by match date and show the 2 most recent

    }

    private void loadRecentRequestsInListView()
    {
        //Use stored procedure to sort by requested date and show the 2 most recent

    }

    private void getProfileInformation()
    {
        //Use stored procedure to fill labels with database information

        int profileID = getProfileID(); // get profile ID from profile model

        String query = "{call ReadProfileByID(?)}";

        try{
            Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement stmt = conn.prepareCall(query);

            stmt.setInt(1, profileID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                // Get data from result set
                String profileName = rs.getString("fldProfileName");
                String jobTitle = rs.getString("fldJobTitle");
                String jobFunction = rs.getString("fldJobFunction");
                String companyName = rs.getString("fldCompanyName");
                String homeAddress = rs.getString("fldHomeAddress");
                String wage = rs.getString("fldWage");
                String payPref = rs.getString("fldPayPref");
                String distPref = rs.getString("fldDistPref");
                String swappingStatus = rs.getString("fldSwappingStatus");

                // Set data from result set in labels
                profileNameLbl.setText(profileName);
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


}
