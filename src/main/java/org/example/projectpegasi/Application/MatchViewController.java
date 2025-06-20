package org.example.projectpegasi.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.BusinessService.SwapRequestManager;
import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.JobSwapApplication;
import org.example.projectpegasi.DomainModels.MatchDetails;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Match View.
 * Displays all current matches for the logged-in user, allowing them to accept or decline matches.
 * Accepting a match creates a swap request; declining updates the match state to denied.
 */
public class MatchViewController {
    @FXML
    private TableView<MatchDetails> matchTable;

    @FXML
    private TableColumn<MatchDetails, String> jobTitleColumnMatch, companyColumnMatch;

    @FXML
    private Button goToMatchesButton, outgoingRequestButton, incomingRequestButton, backToProfileButton;

    /**
     * Initializes the MatchView scene when the view is opened.
     * Loads all active matches (state = 1) for the currently logged-in user
     * Retrieves job title and company name for the other profile in each match
     * Displays match data in the table
     * Adds a ✔ button to accept a match (creates a swap request)
     * Adds a ✘ button to decline a match (updates state to 'denied')
     */
    @FXML
    public void initialize() throws Exception {

        SwapRequestManager srManager = new SwapRequestManager();

        // Creates a column with a "✔" button used to accept a match
        TableColumn<MatchDetails, Void> requestSwapColumnMatch = new TableColumn<>("Request swap");
        requestSwapColumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button requestSwapButton = new Button("✔");

            {
                requestSwapButton.setOnAction(event -> {
                    MatchDetails details = getTableView().getItems().get(getIndex());
                    try {
                        int senderProfileID = MainViewController.getCurrentProfileID();
                        srManager.createSwapRequest(details.getMatchID(), senderProfileID);
                        matchTable.getItems().remove(getIndex());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                setGraphic(requestSwapButton); //Show button in cell
            }
        });

        // Creates a column with a "✘" button used to decline a match
        TableColumn<MatchDetails, Void> declineMatchcolumnMatch = new TableColumn<>("Decline match");
        declineMatchcolumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button declineMatchButton = new Button("✘");

            {
                declineMatchButton.setOnAction(event -> {
                    MatchDetails details = getTableView().getItems().get(getIndex());
                    try {
                        srManager.declineMatchAndRequest(details.getMatchID());
                        matchTable.getItems().remove(getIndex());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                setGraphic(declineMatchButton); //Show button in cell
            }
        });

        // Load all matches for the current user
        int LoginProfileID = MainViewController.getCurrentProfileID();
        DataAccessObject dao = new DataAccessObject();
        List<Match> matches = dao.getMatchesForProfile(LoginProfileID);

        List<MatchDetails> matchDetails = new ArrayList<>();

        // Retrieve job title and company info for the other profile in each match
        for (Match match : matches) {
            int othersProfileID = (match.getProfileAID() == LoginProfileID) ? match.getProfileBID() : match.getProfileAID();
            //Get profileinformation
            Profile profile = dao.getAttributesForMatchView(othersProfileID);
            //
            MatchDetails details = new MatchDetails(profile.getJobTitle(), profile.getCompany().getName(), match.getMatchID());

            matchDetails.add(details);
        }
        // Convert list to an observable list for TableView
        ObservableList<MatchDetails> observableList = FXCollections.observableArrayList(matchDetails);

        // Bind columns to MatchDetails fields
        jobTitleColumnMatch.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        companyColumnMatch.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        matchTable.setItems(observableList);

        // Remove existing button columns if anyone present
        matchTable.getColumns().removeIf(col ->
                col.getText().equals("Request swap") || col.getText().equals("Decline match"));

        // Add button columns only if there are matches
        if (!observableList.isEmpty()) {
            matchTable.getColumns().addAll(requestSwapColumnMatch, declineMatchcolumnMatch);
        }
    }


    /**
     * Navigates the user to the Outgoing Request view.
     */
        public void onOutgoingRequestButtonClick ()
        {
            JobSwapApplication.changeScene(ControllerNames.OutgoingRequestView);
        }

    /**
     * Navigates the user to the Incoming Request view.
     */
        public void onIncomingRequestButtonClick ()
        {
            JobSwapApplication.changeScene(ControllerNames.IncomingRequestView);
        }

    /**
     * Returns the user to their profile view.
     */
        public void onBackToProfileButtonClick ()
        {
            JobSwapApplication.changeScene(ControllerNames.ProfileView);
        }
    }
