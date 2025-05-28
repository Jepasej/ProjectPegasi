package org.example.projectpegasi.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.BusinessService.SwapRequestManager;
import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.SwapRequest;
import org.example.projectpegasi.HelloApplication;

/**
 * Controller for the "Outgoing Requests" view.
 * This controller handles the display of the user's sent swap requests
 * and navigation between different scenes in the application.
 */
public class OutgoingRequestViewController
{
    @FXML
    private TableView<Match> requestTable;

    @FXML
    private TableColumn<SwapRequest, String> jobTitleColumnMatch, companyColumnMatch, deleteRequestColumn;

    @FXML
    private Button goToMatchesButton, outgoingRequestButton, incomingRequestButton, backToProfileButton;

    @FXML
    public void initialize() {


        SwapRequestManager srManager = new SwapRequestManager();

        // Creates a column with a "✘" button that allows the user to delete a request.
        // When clicked, the request is removed from the UI and database.
        TableColumn<Match, Void> deleteRequestcolumnMatch = new TableColumn<>("Delete Request");
        deleteRequestcolumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button deleteRequestButton = new Button("✘");

            {
                deleteRequestButton.setOnAction(event -> {
                    Match match = getTableView().getItems().get(getIndex());
                    try {
                        srManager.deleteSwapRequest(match.getMatchID());
                        requestTable.getItems().remove(match);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                setGraphic(deleteRequestButton); //Show button in cell
            }
        });
        requestTable.getColumns().add(deleteRequestcolumnMatch); // Add the column to table

        //Testkode for at tjekke om knapper duer, skal fjernes når view outgoing requests virker
        ObservableList<Match> testMatches = FXCollections.observableArrayList();

        Match testMatch = new Match();
        testMatch.setMatchID(123);
        testMatch.setProfileAID(2);
        testMatch.setProfileBID(3);
        testMatch.setStateID(1);
        testMatches.add(testMatch);
        requestTable.setItems(testMatches);
    }


    /**
     * Handles the action when the "Matches" button is clicked.
     * Navigates to the MatchView.
     */
    @FXML
    public void onMatchesButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.MatchView);
    }

    /**
     * Handles the action when the "Incoming Requests" button is clicked.
     * Navigates to the IncomingRequestView.
     */
    @FXML
    public void onIncomingRequestButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.IncomingRequestView);
    }

    /**
     * Handles the action when the "Back to Profile" button is clicked.
     * Navigates to the ProfileView.
     */
    @FXML
    public void onBackToProfileButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.ProfileView);
    }
}
