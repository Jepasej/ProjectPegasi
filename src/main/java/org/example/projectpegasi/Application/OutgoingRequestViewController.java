package org.example.projectpegasi.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.BusinessService.SwapRequestManager;
import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.MatchDetails;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.JobSwapApplication;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the "Outgoing Requests" view.
 * This controller handles the display of the user's sent swap requests
 * and navigation between different scenes in the application.
 */
public class OutgoingRequestViewController
{
    @FXML
    private TableView<MatchDetails> requestTable;

    @FXML
    private TableColumn<SwapRequest, String> jobTitleColumnMatch, companyColumnMatch, deleteRequestColumn;

    @FXML
    private Button goToMatchesButton, outgoingRequestButton, incomingRequestButton, backToProfileButton;

    /**
     * Initializes the Outgoing Requests view.
     * Loads all outgoing swap requests, retrieves associated profile data,
     * and adds a delete button to each row for request cancellation.
     */
    @FXML
    public void initialize() {


        SwapRequestManager srManager = new SwapRequestManager();

        // Creates a column with a "✘" button that allows the user to delete a request.
        // When clicked, the request is removed from the UI and database.
        TableColumn<MatchDetails, Void> deleteRequestcolumnMatch = new TableColumn<>("Delete Request");
        deleteRequestcolumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button deleteRequestButton = new Button("✘");
            {
                deleteRequestButton.setOnAction(event -> {
                    MatchDetails details = getTableView().getItems().get(getIndex());
                    try {
                        srManager.deleteSwapRequest(details.getMatchID());
                        requestTable.getItems().remove(details);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                setGraphic(deleteRequestButton); //Show button in cell
            }
        });

        // Load all requests for the current user
        int LoginProfileID = MainViewController.getCurrentProfileID();
        DataAccessObject dao = new DataAccessObject();
        List<Match> outgoingRequests = dao.getOutgoingRequests(LoginProfileID, LoginProfileID);

        List<MatchDetails> matchDetails = new ArrayList<>();

        // Retrieve job title and company info for the other profile in each request
        for (Match match : outgoingRequests) {
            int othersProfileID = (match.getProfileAID() == LoginProfileID) ? match.getProfileBID() : match.getProfileAID();
            //Get profileinformation
            Profile profile = dao.getAttributesForMatchView(othersProfileID);
            MatchDetails details = new MatchDetails(profile.getJobTitle(), profile.getCompany().getName(), match.getMatchID());

            matchDetails.add(details);
        }
        // Convert list to an observable list for TableView
        ObservableList<MatchDetails> observableList = FXCollections.observableArrayList(matchDetails);

        // Bind columns to MatchDetails fields
        jobTitleColumnMatch.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        companyColumnMatch.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        requestTable.setItems(observableList);

        // Remove existing button columns if anyone present
        requestTable.getColumns().removeIf(col ->
                col.getText().equals("Delete Request"));

        // Add button columns only if there are requests
        if (!observableList.isEmpty()) {
            requestTable.getColumns().addAll(deleteRequestcolumnMatch);
        }
    }


    /**
     * Handles the action when the "Matches" button is clicked.
     * Navigates to the MatchView.
     */
    @FXML
    public void onMatchesButtonClick()
    {
        JobSwapApplication.changeScene(ControllerNames.MatchView);
    }

    /**
     * Handles the action when the "Incoming Requests" button is clicked.
     * Navigates to the IncomingRequestView.
     */
    @FXML
    public void onIncomingRequestButtonClick()
    {
        JobSwapApplication.changeScene(ControllerNames.IncomingRequestView);
    }

    /**
     * Handles the action when the "Back to Profile" button is clicked.
     * Navigates to the ProfileView.
     */
    @FXML
    public void onBackToProfileButtonClick()
    {
        JobSwapApplication.changeScene(ControllerNames.ProfileView);
    }
}
