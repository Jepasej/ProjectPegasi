package org.example.projectpegasi.Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.projectpegasi.BusinessService.ControllerNames;
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
    private TableView<SwapRequest> requestTable;

    @FXML
    private TableColumn<SwapRequest, String> jobTitleColumnMatch, companyColumnMatch, deleteRequestColumn;

    @FXML
    private Button goToMatchesButton, outgoingRequestButton, incomingRequestButton, backToProfileButton;

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
