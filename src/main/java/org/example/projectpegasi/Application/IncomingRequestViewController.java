package org.example.projectpegasi.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projectpegasi.BusinessService.ControllerNames;
import org.example.projectpegasi.BusinessService.SwapRequestManager;
import org.example.projectpegasi.DomainModels.Match;
import org.example.projectpegasi.DomainModels.MatchDetails;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.HelloApplication;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IncomingRequestViewController
{
    @FXML
    private TableView<MatchDetails> incomingRequestTable;

    @FXML
    private TableColumn<Match, String> jobTitleColumnMatch, companyColumnMatch;

    @FXML
    private Button goToMatchesButton, outgoingRequestButton, incomingRequestButton, backToProfileButton;


    @FXML
    public void initialize() {
        SwapRequestManager srManager = new SwapRequestManager();

        /*
         Creates a column with a "✔" button that allows the user to accept a request.
         When clicked, it creates a jobswap using the match's ID.
         In future releases, this should be extended to notify HR of a potential jobSwap opportunity.
         */
        TableColumn<MatchDetails, Void> acceptRequestColumnMatch = new TableColumn<>("Accept Request");
        acceptRequestColumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button acceptRequestButton = new Button("✔");

            {
                acceptRequestButton.setOnAction(event -> {
                    MatchDetails details = getTableView().getItems().get(getIndex());
                    try {
                        srManager.acceptSwapRequest(details.getMatchID());
                        incomingRequestTable.getItems().remove(getIndex());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                setGraphic(acceptRequestButton); //Show button in cell
            }
        });

        // Creates a column with a "✘" button that allows the user to decline a request.
        // When clicked, the match is removed from the UI, but remains in the database and
        // changes it's state to "denied".
        TableColumn<MatchDetails, Void> declineRequestcolumnMatch = new TableColumn<>("Decline Request");
        declineRequestcolumnMatch.setCellFactory(col -> new TableCell<>() {
            private final Button declineRequestButton = new Button("✘");

            {
                declineRequestButton.setOnAction(event -> {
                    MatchDetails details = getTableView().getItems().get(getIndex());
                    try {
                        srManager.declineMatchAndRequest(details.getMatchID());
                        incomingRequestTable.getItems().remove(getIndex());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                setGraphic(declineRequestButton); //Show button in cell
            }
        });

        // Load all incoming requests for the current user
        int LoginProfileID = MainViewController.getCurrentProfileID();
        DataAccessObject dao = new DataAccessObject();
        List<Match> incomingRequests = dao.getIncomingRequests(LoginProfileID, LoginProfileID);

        List<MatchDetails> matchDetails = new ArrayList<>();

        // Retrieve job title and company info for the other profile in each request
        for (Match match : incomingRequests) {
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
        incomingRequestTable.setItems(observableList);

        // Remove existing button columns if anyone present
        incomingRequestTable.getColumns().removeIf(col ->
                col.getText().equals("Delete Request"));

        // Add button columns only if there are requests
        if (!observableList.isEmpty()) {
            incomingRequestTable.getColumns().addAll(acceptRequestColumnMatch, declineRequestcolumnMatch);
        }    }

    public void onMatchesButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.MatchView);
    }

    public void onOutgoingRequestsButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.OutgoingRequestView);
    }

    public void onBackToProfileButtonClick()
    {
        HelloApplication.changeScene(ControllerNames.ProfileView);
    }
}
