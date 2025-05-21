package org.example.projectpegasi.Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.Foundation.DBConnection;
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
    protected void editProfileButtOnAction()
    {
        //Go back to main view

    }

    @FXML
    public void swapStatusButtOnAction()
    {
        //Change the Swapping Status to true/false depending on what it is currently set to
    }

    public void setUserID()
    {
        this.userID = userID;
        getProfileInformation();
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
    private void getProfileInformation()
    {
        DAO dao = new DataAccessObject();
        String query = "{call ReadProfileByID(?)}"; // JDBC Escape Syntax

        try{
            List<String> profileInfo = dao.getProfileInformation(userID);

        }catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }



}
